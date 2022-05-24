package com.althurdinok.fromandwith.ui.screen.main.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.ui.app.FromAndWithViewModel

var viewModel: FromAndWithViewModel? = null

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, fromAndWithViewModel: FromAndWithViewModel) {
    viewModel = fromAndWithViewModel
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            ProfileCover()
            ProfileLearningDetail()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileCover() {
    val coverHeight = (LocalConfiguration.current.screenHeightDp * 0.28f).dp
    val avatarSize = 96.dp
    val currentDarkMode = viewModel?.getThemeMode() ?: isSystemInDarkTheme()

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
        // 背景图
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data("https://w.wallhaven.cc/full/57/wallhaven-57o619.png")
                .crossfade(true)
                .build(),
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        // 背景图蒙版
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(coverHeight)
                .background(
                    Brush.verticalGradient(
                        0f to MaterialTheme.colorScheme.background,
                        0.05f to MaterialTheme.colorScheme.background.copy(alpha = 0.92f),
                        0.1f to MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                        0.15f to MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                        0.25f to MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        0.5f to MaterialTheme.colorScheme.background.copy(alpha = 0.2f),
                        0.75f to MaterialTheme.colorScheme.background.copy(alpha = 0.1f),
                        1f to MaterialTheme.colorScheme.background.copy(alpha = 0f)
                    )
                ),
            color = Color.Transparent
        ) {}
        // 右上角图标按钮
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row {
                IconButton(onClick = {
                    viewModel?.setThemeMode(!currentDarkMode)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_brightness_4_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        // 头像
        Box(
            modifier = Modifier
                .padding(top = coverHeight / 2 - avatarSize / 2)
                .zIndex(100f),
            contentAlignment = Alignment.TopCenter
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data("https://w.wallhaven.cc/full/md/wallhaven-mdxjzy.jpg")
                    .placeholder(R.drawable.avatar_1)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,
                        shape = CircleShape,
                        clip = true
                    )
                    .clip(CircleShape)
                    .size(96.dp),
                contentDescription = "Avatar",
                contentScale = ContentScale.FillBounds
            )
        }
        // 信息卡片
        Box(
            modifier = Modifier
                .padding(top = coverHeight / 2, start = 24.dp, end = 24.dp)
                .background(
                    Color.Transparent
                )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = coverHeight),
                elevation = CardDefaults.elevatedCardElevation(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = avatarSize / 2 + 12.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 昵称
                    Text(
                        text = "飞鸟",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 26.sp,
                        maxLines = 1,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // 简介
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "我的白昼已尽\n我像一只漂泊在海滩上的小船\n谛听着晚潮跳舞的乐声",
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp,
                            fontSize = 14.sp,
                            maxLines = 5,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    // 数据
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        LearningStatusBlock("已学习", "10")
                        LearningStatusBlock("发帖数", "28")
                        LearningStatusBlock("已收藏", "8")
                    }
                }
            }
        }
    }
}

@Composable
fun LearningStatusBlock(title: String, data: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .defaultMinSize(minWidth = 72.dp, minHeight = 72.dp)
            .clip(RoundedCornerShape(CornerSize(4.dp)))
            .clickable { }
            .indication(
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.primary.copy(
                        ContentAlpha.medium
                    )
                ),
                interactionSource = remember { MutableInteractionSource() }
            )) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.85f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = data,
            fontSize = 28.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun ProfileLearningDetail() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 24.dp, end = 24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "学习中（3）",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    LearningDetailCard(
                        cover = "https://w.wallhaven.cc/full/qd/wallhaven-qdl8e7.jpg",
                        title = "互相学",
                        subtitle = "SpringBoot - Python",
                        detail = "与李华一起"
                    )
                    LearningDetailCard(
                        cover = "https://w.wallhaven.cc/full/0w/wallhaven-0wlqp7.png",
                        title = "一起学",
                        subtitle = "Android开发基础",
                        detail = "与王二、张三等一起"
                    )
                    LearningDetailCard(
                        cover = "https://w.wallhaven.cc/full/4g/wallhaven-4g9ev7.jpg",
                        title = "一起学",
                        subtitle = "书法：行楷",
                        detail = "与尼古拉斯赵四一起"
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningDetailCard(cover: String, title: String, subtitle: String, detail: String) {
    Box(Modifier.padding(end = 8.dp)) {
        Card(
            elevation = CardDefaults.cardElevation(),
            onClick = { }
        ) {
            Column(Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(cover)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .alpha(0.8f)
                        .clip(RoundedCornerShape(CornerSize(12.dp)))
                        .fillMaxWidth()
                        .width(200.dp)
                        .height(112.dp),
                    contentDescription = "Learning",
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = subtitle, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = detail, fontSize = 14.sp)
                }
            }
        }
    }
}