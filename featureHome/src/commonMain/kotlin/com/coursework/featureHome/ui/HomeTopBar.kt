package com.coursework.featureHome.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.coursework.corePresentation.commonUi.IconButton
import lms.featurehome.generated.resources.app_icon
import lms.featurehome.generated.resources.home_title
import lms.featurehome.generated.resources.ic_logout
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import lms.featurehome.generated.resources.Res.drawable as Drawables
import lms.featurehome.generated.resources.Res.string as Strings

@Composable
internal fun HomeTopBar(
    onLogoutClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 8.dp,
                top = 8.dp,
                bottom = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(32.dp),
            imageVector = vectorResource(Drawables.app_icon),
            contentDescription = "App icon",
        )
        Text(
            modifier = Modifier
                .padding(end = 8.dp),
            text = stringResource(Strings.home_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(36.dp),
            iconSize = 22.dp,
            imageVector = vectorResource(Drawables.ic_logout),
            contentDescription = "Log out icon",
            tint = MaterialTheme.colorScheme.onBackground,
            onClick = onLogoutClick,
        )
    }
}