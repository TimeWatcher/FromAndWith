package com.althurdinok.fromandwith.ui.common

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.request.repeatCount
import com.althurdinok.fromandwith.FromAndWithApplication

fun ToastNotification(message: String) {
    Toast.makeText(FromAndWithApplication.context, message, Toast.LENGTH_LONG).show()
}

@Composable
fun GifImage(
    data: Any?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    repeatCount: Int = 0
) {
    val imgLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .repeatCount(repeatCount)
            .build(),
        imageLoader = imgLoader,
        contentDescription = "",
        modifier = modifier,
        alignment = alignment,
        contentScale = contentScale
    )
}

@Composable
fun LoadingProgressSpinner() {
    Surface(
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.72f),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .clickable(enabled = false) { }
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun OutlinedValidationTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = androidx.compose.material.MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    errorMessage: String? = null
) {
    Column {
        val realColors =
            TextFieldDefaults.outlinedTextFieldColors(
                errorLeadingIconColor = MaterialTheme.colorScheme.error,
                errorTrailingIconColor = MaterialTheme.colorScheme.error
            )
        val hasErrorMessage = errorMessage != null && errorMessage.isNotEmpty()
        OutlinedTextField(
            value,
            onValueChange,
            modifier,
            enabled,
            readOnly,
            textStyle,
            label,
            placeholder,
            leadingIcon,
            trailingIcon,
            if (hasErrorMessage) true else isError,
            visualTransformation,
            keyboardOptions,
            keyboardActions,
            singleLine,
            maxLines,
            interactionSource,
            shape,
            colors = realColors
        )
        if (hasErrorMessage)
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
    }
}


@Composable
fun ColoredIconLabel(
    modifier: Modifier = Modifier,
    icon: Any,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    Surface(
        color = backgroundColor,
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconModifier = Modifier.size(18.dp)
            when (icon) {
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = iconModifier
                )
                is Int -> Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = contentColor,
                    modifier = iconModifier
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, color = contentColor)
        }
    }
}