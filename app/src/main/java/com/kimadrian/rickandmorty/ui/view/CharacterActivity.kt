package com.kimadrian.rickandmorty.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.viewbinding.BuildConfig
import com.kimadrian.rickandmorty.R
import com.kimadrian.rickandmorty.ui.view.ui.theme.BackgroundColor
import com.kimadrian.rickandmorty.ui.view.ui.theme.ColorCard
import com.kimadrian.rickandmorty.ui.view.ui.theme.LabelTextColor
import com.kimadrian.rickandmorty.ui.view.ui.theme.RickandMortyTheme
import timber.log.Timber

class CharacterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        setContent {
            RickandMortyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BackgroundColor
                ) {
                    CharacterCard()
                }
            }
        }
    }
}

@Composable
fun CharacterCard(modifier: Modifier = Modifier, ) {
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
            Image(
                painter = painterResource(id = R.drawable.rick_and_morty_icon),
                contentDescription = stringResource(id = R.string.character_image),
                modifier = modifier
                    .width(150.dp)
                    .height(200.dp)
            )
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.name),
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
                        backgroundColor = Color.Green
                    ){}
                    Text(
                        text = stringResource(id = R.string.alive),
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
                        text = stringResource(id = R.string.species),
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
                    text = stringResource(id = R.string.last_location),
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
fun Characters(){
    LazyColumn {
        item {
            CharacterCard()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RickandMortyTheme {
        CharacterCard()
    }
}