package com.althurdinok.fromandwith.ui.screen.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.ui.theme.DarkColorScheme
import com.althurdinok.fromandwith.ui.theme.LightColorScheme
import kotlin.math.roundToInt

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val fabHeight = 88.dp
    val fabHeightPx = with(LocalDensity.current) {
        fabHeight.roundToPx().toFloat()
    }
    var fabOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = fabOffsetHeightPx + delta
                fabOffsetHeightPx = newOffset.coerceIn(-fabHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            items(12) {
                PostItem(
                    username = "Althurdinok",
                    avatar = R.drawable.avatar,
                    time = "3分钟前",
                    thumbUpCount = 66,
                    commentCount = 32,
                    message = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco"
                )
            }
        }
        Box(modifier = Modifier.padding(32.dp)) {
            androidx.compose.material.ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "发个帖子",
                        color = if (isSystemInDarkTheme()) DarkColorScheme.onPrimary else LightColorScheme.onPrimary
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                onClick = { },
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = -fabOffsetHeightPx.roundToInt()
                    )
                })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostItem(
    username: String,
    avatar: Int,
    time: String,
    thumbUpCount: Int,
    commentCount: Int,
    message: String
) {
    var thumbUpChecked by remember {
        mutableStateOf(false)
    }
    var mThumbUpCount by remember {
        mutableStateOf(thumbUpCount)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(top = 20.dp, bottom = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            // 发帖人信息
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .shadow(
                            elevation = 1.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .size(42.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        username,
                        fontSize = 17.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = time,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Text(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(CornerSize(4.dp)))
                    .clickable { }
                    .padding(4.dp),
                lineHeight = 1.35.em
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconTextButton(
                    icon = Icons.Default.ThumbUp,
                    text = mThumbUpCount.toString(),
                    iconTint = if (thumbUpChecked) MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.72f
                    ) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f)
                ) {
                    mThumbUpCount += if (thumbUpChecked) -1 else 1
                    thumbUpChecked = !thumbUpChecked
                }
                IconTextButton(
                    icon = R.drawable.ic_baseline_message_24,
                    text = commentCount.toString()
                ) { }
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    icon: Any,
    text: String,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.72f),
    textColor: Color = iconTint,
    textSize: TextUnit = TextUnit.Unspecified,
    onClick: () -> Unit
) {
    IconButton(modifier = modifier.defaultMinSize(minWidth = 64.dp), onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
            val iconModifier = Modifier.size(20.dp)
            when (icon) {
                is ImageVector -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = iconModifier
                )
                is Int -> Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconTint,
                    modifier = iconModifier
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, color = textColor, fontSize = textSize)
        }
    }
}


@Preview
@Composable
fun HomeScreenTest() {
    val fabHeight = 88.dp
    val fabHeightPx = with(LocalDensity.current) {
        fabHeight.roundToPx().toFloat()
    }
    var fabOffsetHeightPx by remember { mutableStateOf(0f) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val newOffset = fabOffsetHeightPx + delta
                fabOffsetHeightPx = newOffset.coerceIn(-fabHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
            items(12) {
                PostItem(
                    username = "Althurdinok",
                    avatar = R.drawable.avatar,
                    time = "3分钟前",
                    thumbUpCount = 66,
                    commentCount = 32,
                    message = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco"
                )
            }
        }
        Surface(modifier = Modifier.padding(32.dp)) {
            ExtendedFloatingActionButton(
                text = {
                    Text(text = "发个帖子")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                onClick = { },
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = -fabOffsetHeightPx.roundToInt()
                    )
                })
        }
    }
}