package com.kimadrian.rickandmorty.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import androidx.viewbinding.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
import com.skydoves.landscapist.glide.GlideImage
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
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    MyApp(characters)
                }
            }
        }
    }

}

@Composable
fun MyApp(characters: LazyPagingItems<Result>) {
//    if (characters.loadState.refresh is LoadState.Loading) {
//
//    } else if {
//        val error = when {
//            characters.loadState.prepend is LoadState.Error -> characters.loadState.prepend as LoadState.Error
//            characters.loadState.append is LoadState.Error -> characters.loadState.append as LoadState.Error
//            characters.loadState.refresh is LoadState.Error -> characters.loadState.refresh as LoadState.Error
//            else -> null
//        }
//        error?.let {
//            Timber.e(it.error)
//        }
//    } else if {
        LazyColumn {
            itemsIndexed(characters) { _, character ->
                character?.let {
                    CharacterCard(character = it)
                }
            }
        }
   // }



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
            GlideImage(
                imageModel = { character.image },
                modifier = modifier
                    .width(150.dp)
                    .height(200.dp),
                requestBuilder = {
                    Glide
                        .with(LocalContext.current)
                        .asBitmap()
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                        .centerCrop()
                },
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
                            .padding(top = 4.dp, start = 8.dp, end = 4.dp)
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
                            .padding(top = 4.dp, start = 4.dp, end = 8.dp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickandMortyTheme {
        //CharacterCard()
    }
}