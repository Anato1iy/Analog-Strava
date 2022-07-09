package com.example.utils

import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs


class CubeInDepthTransformation : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.cameraDistance = 20000F
        when {
            position < -1 -> {
                page.alpha = 0F
            }
            position <= 0 -> {
                page.alpha = 1F
                page.pivotX = page.getWidth().toFloat()
                page.rotationY = 90 * Math.abs(position)
            }
            position <= 1 -> {
                page.alpha = 1F
                page.pivotX = 0F
                page.rotationY = -90 * Math.abs(position)
            }
            else -> {
                page.alpha = 0F
            }
        }
        if (abs(position) <= 0.5) {
            page.scaleY = .4f.coerceAtLeast(1 - abs(position))
        } else if (abs(position) <= 1) {
            page.scaleY = .4f.coerceAtLeast(1 - abs(position))
        }
    }
}