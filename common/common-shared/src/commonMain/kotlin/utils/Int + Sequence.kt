package utils

fun Int?.toSequence(): String {
    if (this == null) return "0"
    if (this == 0) return "0"

    val builder = StringBuilder()

    for (i in 0..this) {
        if (i == this) {
            builder.append(i)
        } else {
            builder.append(i).append(" -> ")
        }
    }

    return builder.toString()
}