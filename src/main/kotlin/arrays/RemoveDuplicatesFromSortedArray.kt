package arrays

class RemoveDuplicatesFromSortedArray {
    fun removeDuplicates(numbers: IntArray): Int {
        if (numbers.isEmpty()) return 0
        var i = 0

        for (j in 1 until numbers.size) {
            if (numbers[i] != numbers[j]) {
                i++
                numbers[i] = numbers[j]
            }
        }

        return i + 1
    }

}
