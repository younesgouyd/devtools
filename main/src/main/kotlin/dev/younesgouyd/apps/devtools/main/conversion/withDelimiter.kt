package dev.younesgouyd.apps.devtools.main.conversion

fun String.withDelimiter(partLength: Int, oldDelimiter: String, newDelimiter: String): String {
    val result = StringBuilder()
    var index = 0
    while (index < this.length) {
        result.append(this.substring(index, index + partLength))
        index += partLength
        if (index + oldDelimiter.length < this.length) {
            result.append(newDelimiter)
            if (this.substring(index, index + oldDelimiter.length) == oldDelimiter) {
                index += oldDelimiter.length
            }
        }
    }
    return result.toString()
}