package com.kimadrian.rickandmorty.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import androidx.viewbinding.BuildConfig
import coil.compose.AsyncImage
import com.kimadrian.rickandmorty.R
import com.kimadrian.rickandmorty.data.model.characters.Result
import com.kimadrian.rickandmorty.data.repository.Repository
import com.kimadrian.rickandmorty.ui.view.ui.theme.BackgroundColor
import com.kimadrian.rickandmorty.ui.view.ui.theme.ColorCard
import com.kimadrian.rickandmorty.ui.view.ui.theme.LabelTextColor
import com.kimadrian.rickandmorty.ui.view.ui.theme.RickandMortyTheme
import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModel
import com.kimadrian.rickandmorty.ui.viewmodel.CharactersViewModelFactory
import com.kimadrian.rickandmorty.utils.CharacterStatusColor
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class CharacterActivity : ComponentActivity() {

    lateinit var viewModel: CharactersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        setContent {
            val characterRepository = Repository()
            val viewModelFactory = CharactersViewModelFactory(characterRepository)
            viewModel = ViewModelProvider(this, viewModelFactory)[CharactersViewModel::class.java]
            val characters = viewModel.getCharacters().collectAsLazyPagingItems(Dispatchers.IO)
            RickandMortyTheme {
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState =scaffoldState,
                ) { padding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        color = BackgroundColor
                    ) {
                        MyApp(characters, scaffoldState.snackbarHostState)
                    }
                }
            }
        }
    }

}

@Composable
fun MyApp(characters: LazyPagingItems<Result>, snackbarHostState: SnackbarHostState) {

    if (characters.loadState.refresh is LoadState.Loading) {
        Column {
            repeat(4){
                LoadingShimmerCard()
            }
        }
    }  else {
        val error = when {
            characters.loadState.prepend is LoadState.Error -> characters.loadState.prepend as LoadState.Error
            characters.loadState.append is LoadState.Error -> characters.loadState.append as LoadState.Error
            characters.loadState.refresh is LoadState.Error -> characters.loadState.refresh as LoadState.Error
            else -> null
        }
        if (error != null){
            error.let {
                Timber.e(it.error)
                ErrorScreen(modifier = Modifier, snackbarHostState)
            }
        } else {
            LazyColumn(state = rememberLazyListState()) {
                itemsIndexed(characters) { _, character ->
                    character?.let {
                        CharacterCard(character = it)
                    }
                }
            }
        }

    }

}

@Composable
fun CharacterCard(modifier: Modifier = Modifier, character: Result) {
    Card(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = ColorCard
    ) {
        Row(
            modifier = modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = character.image, 
                contentDescription = stringResource(id = R.string.character_image),
                modifier = modifier
                    .width(150.dp)
                    .height(200.dp),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Text(
                    text = character.name,
                    modifier = modifier
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Card(
                        modifier = modifier
                            .padding(start = 8.dp, top = 12.dp)
                            .height(12.dp)
                            .width(12.dp),
                        shape = RoundedCornerShape(10.dp),
                        backgroundColor = CharacterStatusColor.statusColor(character.status)
                    ){}
                    Text(
                        text = character.status,
                        modifier = modifier
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Text(
                        text = stringResource(id = R.string.dash),
                        modifier = modifier
                            .padding(top = 4.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Text(
                        text = character.species,
                        modifier = modifier
                            .padding(top = 4.dp, start = 8.dp, end = 4.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    
                }
                Text(
                    text = stringResource(id = R.string.last_known_location),
                    modifier = modifier
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .wrapContentHeight()
                        .wrapContentWidth(),
                    color = LabelTextColor,
                    fontSize = 18.sp
                )
                Text(
                    text = character.location.name,
                    modifier = modifier
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    color = Color.White,
                    fontSize = 18.sp
                )

            }


        }

    }

}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState){
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.doofus_rick),
            contentDescription = stringResource(id = R.string.error_image),
            modifier = modifier
                .size(350.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )
        LaunchedEffect(key1 = 1){
            snackbarHostState.showSnackbar(
                message = "Something went wrong. Check your internet connection",
                duration = SnackbarDuration.Long
            )
        }
    }


}

@Composable
fun LoadingShimmerCard(modifier: Modifier = Modifier){
    Card(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        backgroundColor = ColorCard
    ) {
        Row(
            modifier = modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            // This represents the image
            Spacer(
                modifier = modifier
                    .width(150.dp)
                    .height(200.dp)
                    .shimmer()
                    .background(BackgroundColor)
            )
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                //This represents the character name
                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(200.dp)
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                        .shimmer()
                        .background(BackgroundColor)
                )
                Row(
                    modifier = modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    //This represents the characterStatus card color
                    Spacer(
                        modifier = modifier
                            .padding(top = 12.dp, start = 8.dp)
                            .height(12.dp)
                            .width(12.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .shimmer()
                            .background(BackgroundColor)
                    )
                    //This represents the characterStatus text
                    Spacer(
                        modifier = modifier
                            .height(40.dp)
                            .width(80.dp)
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                            .shimmer()
                            .background(BackgroundColor)
                    )
                    //This represents the characterSpecies text
                    Spacer(
                        modifier = modifier
                            .height(40.dp)
                            .width(80.dp)
                            .padding(top = 4.dp, start = 4.dp, end = 8.dp)
                            .shimmer()
                            .background(BackgroundColor)
                    )
                }
                //This represents the last location label
                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(180.dp)
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .shimmer()
                        .background(BackgroundColor)
                )
                //This represents the last location text
                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(200.dp)
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .shimmer()
                        .background(BackgroundColor)
                )

            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickandMortyTheme {
        //CharacterCard()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorPreview(){
   // ErrorScreen()
}

@Preview(showBackground = true)
@Composable
fun ShimmerPreview(){
    LoadingShimmerCard()
}
