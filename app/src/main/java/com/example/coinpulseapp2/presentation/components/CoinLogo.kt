package com.example.coinpulseapp2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun CoinLogo(
    iconUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // ✅ Create an ImageLoader that can handle SVGs
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()

    // ✅ Load SVG or any image type directly from URL
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(iconUrl)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}