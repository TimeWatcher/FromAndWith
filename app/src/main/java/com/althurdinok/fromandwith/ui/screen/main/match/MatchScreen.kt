package com.althurdinok.fromandwith.ui.screen.main.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.althurdinok.fromandwith.R
import com.althurdinok.fromandwith.ui.common.ColoredIconLabel


@Composable
fun MatchScreen(matchViewModel: MatchViewModel) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MatchTopBar()
            MatchBody()
        }
    }
}

@Preview
@Composable
fun MatchTopBar() {
    var checked by remember { mutableStateOf(false) }
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Column(
            modifier = Modifier
                .padding(start = 36.dp, top = 48.dp)
        ) {
            Text(
                text = "匹配其他行者",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "匹配行者，与你结伴一同前行",
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp
            )
        }
        IconToggleButton(
            checked = checked,
            onCheckedChange = { checked = !checked },
            modifier = Modifier.padding(top = 48.dp, end = 32.dp)
        ) {
            Icon(
                painter =
                if (checked)
                    painterResource(id = R.drawable.ic_baseline_double_arrow_24)
                else painterResource(id = R.drawable.ic_baseline_sync_alt_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}

@Preview
@Composable
fun MatchBody() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MatchCard()
            MatchFloatingActionButtons()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchCard() {
    val cardHeight = (LocalConfiguration.current.screenHeightDp * 0.64).dp
    val coverHeight = cardHeight * 0.16f
    Card(
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(),
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(start = 42.dp, end = 42.dp, bottom = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart
        ) {
            // 背景图
            AsyncImage(
                model = "https://img.1ppt.com/uploads/allimg/2203/1_220316153431_1.JPG",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(coverHeight),
                contentScale = ContentScale.FillWidth
            )
            // 蒙版
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(coverHeight)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            ) {}
            // 头像
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 0.dp, top = coverHeight - 48.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.avatar)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .size(96.dp)
                        .clip(CircleShape)
                )
            }
            // 资料
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = coverHeight + 48.dp + 8.dp)
            ) {
                MatchPersonalInfo()
            }
        }
    }
}

@Composable
fun MatchPersonalInfo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Althurdinok",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 26.sp,
            maxLines = 1,
            fontWeight = FontWeight.Black,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "Stay passionate, stay young",
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.Center
        ) {
            ColoredIconLabel(icon = Icons.Default.Person, text = "男") { }
            ColoredIconLabel(icon = R.drawable.ic_baseline_calendar_today_24, text = "20岁") { }
            ColoredIconLabel(icon = R.drawable.ic_baseline_self_improvement_24, text = "100") { }
        }
        Spacer(modifier = Modifier.height(16.dp))
        MatchPreferenceInfo()
    }
}

@Composable
fun MatchPreferenceInfo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "我想学",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "C++，Java，Python",
            fontSize = 16.sp,
        )
    }
}

@Composable
fun MatchFloatingActionButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        FloatingActionButton(shape = CircleShape, onClick = {}) {
            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
        }
        Spacer(modifier = Modifier.width(36.dp))
        FloatingActionButton(shape = CircleShape, onClick = {}) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_message_24),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(36.dp))
        FloatingActionButton(shape = CircleShape, onClick = {}) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = null)
        }
    }
}