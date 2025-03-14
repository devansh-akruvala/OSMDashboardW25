package de.storchp.opentracks.osmplugin.map

import org.oscim.backend.canvas.Color
import kotlin.math.floor


private const val GOLDEN_RATIO_CONJUGATE: Double = 0.618033988749895

/**
 * Creates continues distinguished colors via golden ration.
 * Adapted from: [...](https://github.com/dennisguse/TheKarte/blob/master/src/StyleColorCreator.js)
 * Code for color generation was taken partly from [...](https://martin.ankerl.com/2009/12/09/how-to-create-random-colors-programmatically/)
 */
class StyleColorCreator(private var h: Double = GOLDEN_RATIO_CONJUGATE / 2) {

    /**
     * @noinspection SameParameterValue
     */
    private fun convertHSVtoColorRGB(
        hue: Double,
        saturation: Double = 0.99,
        value: Double = 0.99
    ): Int {
        val i = floor(hue * 6)
        val f = hue * 6 - i
        val p = value * (1 - saturation)
        val q = value * (1 - f * saturation)
        val t = value * (1 - (1 - f) * saturation)
        var red = 0.0
        var green = 0.0
        var blue = 0.0
        when ((i % 6).toInt()) {
            0 -> {
                red = value
                green = t
                blue = p
            }

            1 -> {
                red = q
                green = value
                blue = p
            }

            2 -> {
                red = p
                green = value
                blue = t
            }

            3 -> {
                red = p
                green = q
                blue = value
            }

            4 -> {
                red = t
                green = p
                blue = value
            }

            5 -> {
                red = value
                green = p
                blue = q
            }
        }

        return Color.get((red * 255).toInt(), (green * 255).toInt(), (blue * 255).toInt())
    }

    /**
     * Go to next color.
     *
     * @return The color.
     */
    fun nextColor(): Int {
        h += GOLDEN_RATIO_CONJUGATE
        h %= 1.0

        return convertHSVtoColorRGB(h)
    }

}
