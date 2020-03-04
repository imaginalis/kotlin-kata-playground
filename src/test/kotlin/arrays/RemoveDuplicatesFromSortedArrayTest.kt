package arrays

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RemoveDuplicatesFromSortedArrayTest {

    private val findDuplicates = RemoveDuplicatesFromSortedArray()

    @Test
    fun `should return an array of size 2`() {
        val input = intArrayOf(1, 1, 2, 2)

        val result = findDuplicates.removeDuplicates(input)

        assertEquals(2, result)
    }

    @Test
    fun `should return an array of size 4`() {
        val input = intArrayOf(1, 1, 2, 3, 3, 4, 4, 4, 4, 4, 4)

        val result = findDuplicates.removeDuplicates(input)

        assertEquals(4, result)
    }

    @Test
    fun `should return an array of size 5`() {
        val input = intArrayOf(0, 0, 1, 1, 1, 2, 2, 3, 3, 4)

        val result = findDuplicates.removeDuplicates(input)

        assertEquals(5, result)
    }

    @Test
    fun `should return an array of size 2 for large input array`() {
        var input = intArrayOf()
        repeat(100) {
            input += intArrayOf(1)
        }
        repeat(100) {
            input += intArrayOf(2)
        }

        val result = findDuplicates.removeDuplicates(input)

        assertEquals(2, result)
    }

    @Test
    fun `should return empty array size`() {
        val input = intArrayOf()

        val result = findDuplicates.removeDuplicates(input)

        assertEquals(0, result)
    }
}
