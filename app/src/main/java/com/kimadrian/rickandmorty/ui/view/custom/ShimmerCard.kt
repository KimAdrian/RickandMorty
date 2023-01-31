package com.kimadrian.rickandmorty.ui.view.custom

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kimadrian.rickandmorty.ui.view.ui.theme.BackgroundColor
import com.kimadrian.rickandmorty.ui.view.ui.theme.ColorCard

@Composable
fun AnimatedShimmer(){
    val shimmerColors = listOf(
        BackgroundColor.copy(alpha = 0.6f),
        BackgroundColor.copy(alpha = 0.2f),
        BackgroundColor.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, y = translateAnimation.value)
    )

    ShimmerCardItem(brush = brush)
}

//Similar to CharacterCard but replace the composable with spacer
// and set background color to brush
@Composable
fun ShimmerCardItem(modifier: Modifier = Modifier, brush: Brush){
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
                    .background(brush)
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
                        .background(brush),
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
                            .background(brush),
                    )
                    //This represents the characterStatus text
                    Spacer(
                        modifier = modifier
                            .height(40.dp)
                            .width(80.dp)
                            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                            .background(brush),
                    )
                    //This represents the characterSpecies text
                    Spacer(
                        modifier = modifier
                            .height(40.dp)
                            .width(80.dp)
                            .padding(top = 4.dp, start = 4.dp, end = 8.dp)
                            .background(brush),
                    )
                }
                //This represents the last location label
                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(180.dp)
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .background(brush),
                )
                //This represents the last location text
                Spacer(
                    modifier = modifier
                        .height(40.dp)
                        .width(200.dp)
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp)
                        .background(brush),
                )

            }


        }

    }

}

@Preview(showBackground = true)
@Composable
fun Preview(){
    ShimmerCardItem(brush = Brush.linearGradient(
        listOf(
            BackgroundColor.copy(alpha = 0.6f),
            BackgroundColor.copy(alpha = 0.2f),
            BackgroundColor.copy(alpha = 0.6f)
        )
    ))
}