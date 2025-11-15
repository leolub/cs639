package com.example.mountainmarkers

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mountainmarkers.data.local.Mountain
import com.example.mountainmarkers.data.local.is14er
import com.google.android.gms.maps.model.Marker as GmsMarker
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberMarkerState

@Composable
@GoogleMapComposable
fun BasicMarkersMapContent(
    mountains: List<Mountain>,
    onMountainClick: (GmsMarker) -> Boolean = { false }
) {
    val context = LocalContext.current

    val mountainIcon = vectorToBitmap(
        context,
        BitmapParameters(
            id = R.drawable.baseline_filter_hdr_24,
            iconColor = MaterialTheme.colorScheme.secondary.toArgb(),
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer.toArgb(),
        )
    )

    val fourteenerIcon = vectorToBitmap(
        context,
        BitmapParameters(
            id = R.drawable.baseline_filter_hdr_24,
            iconColor = MaterialTheme.colorScheme.onPrimary.toArgb(),
            backgroundColor = MaterialTheme.colorScheme.primary.toArgb(),
        )
    )

    mountains.forEach { mountain ->
        val icon = if (mountain.is14er()) fourteenerIcon else mountainIcon
        Marker(
            state = rememberMarkerState(position = mountain.location),
            title = mountain.name,
            snippet = mountain.elevation.toString(),
            icon = icon,
            zIndex = if (mountain.is14er()) 5f else 2f,
            onClick = { marker ->
                onMountainClick(marker)
                false
            }
        )
    }
}

@Composable
@GoogleMapComposable
fun AdvancedMarkersMapContent(
    mountains: List<Mountain>,
    onMountainClick: (GmsMarker) -> Boolean = { false },
) {
    val context = LocalContext.current

    val normalIcon = vectorToBitmap(
        context,
        BitmapParameters(
            id = R.drawable.baseline_filter_hdr_24,
            iconColor = MaterialTheme.colorScheme.onSecondary.toArgb(),
            backgroundColor = MaterialTheme.colorScheme.secondary.toArgb(),
        )
    )

    val highlightIcon = vectorToBitmap(
        context,
        BitmapParameters(
            id = R.drawable.baseline_filter_hdr_24,
            iconColor = MaterialTheme.colorScheme.onPrimary.toArgb(),
            backgroundColor = MaterialTheme.colorScheme.primary.toArgb(),
        )
    )

    mountains.forEach { mountain ->
        val icon = if (mountain.is14er()) highlightIcon else normalIcon
        val z = if (mountain.is14er()) 6f else 3f

        Marker(
            state = rememberMarkerState(position = mountain.location),
            title = mountain.name,
            snippet = "Elevation: ${mountain.elevation}",
            icon = icon,
            zIndex = z,
            onClick = { marker ->
                onMountainClick(marker)
                false
            }
        )
    }
}

data class MountainClusterItem(
    val mountain: Mountain,
    val snippetString: String
) : ClusterItem {
    override fun getPosition() = mountain.location
    override fun getTitle() = mountain.name
    override fun getSnippet() = snippetString
    override fun getZIndex() = 0f
}

data class IconColor(
    val iconColor: Color,
    val backgroundColor: Color,
    val borderColor: Color,
)

@Composable
private fun SingleMountain(
    colors: IconColor,
) {
    Icon(
        painter = painterResource(id = R.drawable.baseline_filter_hdr_24),
        contentDescription = null,
        tint = colors.iconColor,
        modifier = Modifier
            .size(32.dp)
            .padding(1.dp)
            .drawBehind {
                drawCircle(color = colors.backgroundColor, style = Fill)
                drawCircle(color = colors.borderColor, style = Stroke(width = 3f))
            }
            .padding(4.dp)
    )
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
@GoogleMapComposable
fun ClusteringMarkersMapContent(
    mountains: List<Mountain>,
) {
    val items by remember(mountains) {
        mutableStateOf(
            mountains.map { m ->
                MountainClusterItem(
                    mountain = m,
                    snippetString = m.elevation.toString()
                )
            }
        )
    }

    val alpha = 0.6f

    val fourteenerColors = IconColor(
        iconColor = MaterialTheme.colorScheme.onPrimary,
        backgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = alpha),
        borderColor = MaterialTheme.colorScheme.primary
    )

    val otherColors = IconColor(
        iconColor = MaterialTheme.colorScheme.secondary,
        backgroundColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = alpha),
        borderColor = MaterialTheme.colorScheme.secondary
    )

    Clustering(
        items = items,
        clusterItemContent = { item ->
            val colors = if (item.mountain.is14er()) {
                fourteenerColors
            } else {
                otherColors
            }
            SingleMountain(colors)
        }
    )
}
