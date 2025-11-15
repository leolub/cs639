package com.example.mountainmarkers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

data class BitmapParameters(
    @DrawableRes val id: Int,
    @ColorInt val iconColor: Int,
    @ColorInt val backgroundColor: Int? = null,
    val backgroundAlpha: Int = 168,
    val padding: Int = 16,
)

fun vectorToBitmap(context: Context, parameters: BitmapParameters): BitmapDescriptor {
    val drawable = ContextCompat.getDrawable(context, parameters.id)
        ?: return BitmapDescriptorFactory.defaultMarker()

    val iconWidth = drawable.intrinsicWidth.takeIf { it > 0 } ?: 64
    val iconHeight = drawable.intrinsicHeight.takeIf { it > 0 } ?: 64
    val size = maxOf(iconWidth, iconHeight) + parameters.padding * 2

    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    parameters.backgroundColor?.let { color ->
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.alpha = parameters.backgroundAlpha
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)
    }

    drawable.setBounds(
        parameters.padding,
        parameters.padding,
        size - parameters.padding,
        size - parameters.padding
    )
    drawable.setTint(parameters.iconColor)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
