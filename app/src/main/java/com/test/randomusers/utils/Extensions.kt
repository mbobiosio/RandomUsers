package com.test.randomusers.utils

import java.util.*

fun String.toSentenceCase(): String {
    if (this.isEmpty()) return ""

    val stringBuilder = StringBuilder()
    for (str in trim().replace(Regex("\\s+"), " ").split(" ")) {
        stringBuilder.append(str.substring(0, 1).uppercase(Locale.ENGLISH))
        stringBuilder.append(str.substring(1).lowercase(Locale.ENGLISH))
        stringBuilder.append(" ")
    }
    return stringBuilder.toString().trim()
}
