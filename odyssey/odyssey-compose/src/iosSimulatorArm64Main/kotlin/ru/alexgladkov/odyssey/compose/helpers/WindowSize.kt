package ru.alexgladkov.odyssey.compose.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.interop.LocalUIViewController
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIContentSizeCategoryAccessibilityExtraExtraExtraLarge
import platform.UIKit.UIContentSizeCategoryAccessibilityExtraExtraLarge
import platform.UIKit.UIContentSizeCategoryAccessibilityExtraLarge
import platform.UIKit.UIContentSizeCategoryAccessibilityLarge
import platform.UIKit.UIContentSizeCategoryAccessibilityMedium
import platform.UIKit.UIContentSizeCategoryExtraExtraExtraLarge
import platform.UIKit.UIContentSizeCategoryExtraExtraLarge
import platform.UIKit.UIContentSizeCategoryExtraLarge
import platform.UIKit.UIContentSizeCategoryExtraSmall
import platform.UIKit.UIContentSizeCategoryLarge
import platform.UIKit.UIContentSizeCategoryMedium
import platform.UIKit.UIContentSizeCategorySmall
import platform.UIKit.UIContentSizeCategoryUnspecified
import kotlin.math.roundToInt

// https://github.com/JetBrains/compose-multiplatform-core/blob/dd86b0b699a1a7d2e7e24fa08af86d4cce4dcff5/compose/ui/ui/src/uikitMain/kotlin/androidx/compose/ui/window/ComposeWindow.uikit.kt#L361C41-L361C41
private val uiContentSizeCategoryToFontScaleMap = mapOf(
    UIContentSizeCategoryExtraSmall to 0.8f,
    UIContentSizeCategorySmall to 0.85f,
    UIContentSizeCategoryMedium to 0.9f,
    UIContentSizeCategoryLarge to 1f, // default preference
    UIContentSizeCategoryExtraLarge to 1.1f,
    UIContentSizeCategoryExtraExtraLarge to 1.2f,
    UIContentSizeCategoryExtraExtraExtraLarge to 1.3f,

    // These values don't work well if they match scale shown by
    // Text Size control hint, because iOS uses non-linear scaling
    // calculated by UIFontMetrics, while Compose uses linear.
    UIContentSizeCategoryAccessibilityMedium to 1.4f, // 160% native
    UIContentSizeCategoryAccessibilityLarge to 1.5f, // 190% native
    UIContentSizeCategoryAccessibilityExtraLarge to 1.6f, // 235% native
    UIContentSizeCategoryAccessibilityExtraExtraLarge to 1.7f, // 275% native
    UIContentSizeCategoryAccessibilityExtraExtraExtraLarge to 1.8f, // 310% native

    // UIContentSizeCategoryUnspecified
)

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun windowSize(): IntSize {
    val viewControllerView = LocalUIViewController.current.view
    val contentSizeCategory =
        viewControllerView.traitCollection.preferredContentSizeCategory
            ?: UIContentSizeCategoryUnspecified
    val fontScale = uiContentSizeCategoryToFontScaleMap[contentSizeCategory] ?: 1.0f
    val density = Density(
        viewControllerView.contentScaleFactor.toFloat(),
        fontScale
    )
    val scale = density.density
    val size = viewControllerView.frame.useContents {
        IntSize(
            width = (size.width * scale).roundToInt(),
            height = (size.height * scale).roundToInt()
        )
    }

    return size
}

@Composable
actual fun extractWindowHeight(): Int = windowSize().height