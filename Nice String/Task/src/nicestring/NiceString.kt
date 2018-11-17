package nicestring

fun String.isNice(): Boolean {
    val result = listOf(this.nonBuBaBe(), this.atLeast3Vowels(), this.containsADoubleLetter())
    return result.filter { b -> b == true }.count() >= 2
}

fun String.nonBuBaBe(): Boolean = this.nonBu() && this.nonBa() && this.nonBe()
fun String.atLeast3Vowels(): Boolean = this.countVowels() >= 3
fun String.containsADoubleLetter(): Boolean {
    val tail = this.drop(1)
    val pair = this.zip(tail)
    return pair.filter { (a, b) -> a == b }.count() > 0
}

private fun String.nonBu(): Boolean = !this.contains("bu")
private fun String.nonBa(): Boolean = !this.contains("ba")
private fun String.nonBe(): Boolean = !this.contains("be")

private fun String.countVowels(): Int {
    return this.filter { c: Char -> (c in listOf('a', 'e', 'i', 'o', 'u')) }.count()
}

