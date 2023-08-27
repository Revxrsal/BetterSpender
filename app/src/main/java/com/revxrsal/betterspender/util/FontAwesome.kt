package com.revxrsal.betterspender.util

import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIconType.*
import com.guru.fontawesomecomposelib.FaIcons

val icons = buildList {
    for (iconField in FaIcons::class.java.declaredFields) {
        if (size > 700)
            break
        if (!FaIconType::class.java.isAssignableFrom(iconField.type))
            continue
        iconField.isAccessible = true
        val icon = iconField[FaIcons] as FaIconType
        if (icon is BrandIcon || icon is RegularIcon) continue
        add(icon to iconField.name.spaceLowercase())
    }
}

private fun String.spaceLowercase(): String {
    return buildString {
        for ((index, c) in this@spaceLowercase.withIndex()) {
            if (index == 0) {
                append(c.lowercase())
                continue
            }
            if (c.isUpperCase()) {
                append(' ')
                append(c.lowercase())
            } else {
                append(c)
            }
        }
    }
}
