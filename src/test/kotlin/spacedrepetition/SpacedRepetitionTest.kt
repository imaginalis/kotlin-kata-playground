package spacedrepetition

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import spacedrepetition.card.Card
import java.time.LocalDateTime

internal class SpacedRepetitionTest {

    private val spacedRepetition = SpacedRepetition()

    @Test
    fun `should send single answer with quality 5 and check returned card`() {
        val card = getCard()

        val response = spacedRepetition.calculateRepetition(card, 5)

        assertEquals(1, response.repetitions)
        assertEquals(1, response.interval)
        assertEquals(2.6.toFloat(), response.easinessFactor)
        assertTrue(response.nextRepetition.isAfter(LocalDateTime.now()))
    }

    @Test
    fun `should send single answer with quality 4 and check returned card`() {
        val card = getCard()

        val response = spacedRepetition.calculateRepetition(card, 4)

        assertEquals(1, response.repetitions)
        assertEquals(1, response.interval)
        assertEquals(2.5.toFloat(), response.easinessFactor)
        assertTrue(response.nextRepetition.isAfter(LocalDateTime.now()))
    }

    @Test
    fun `should send single answer with quality 3 and check returned card`() {
        val card = getCard()

        val response = spacedRepetition.calculateRepetition(card, 3)

        assertEquals(1, response.repetitions)
        assertEquals(1, response.interval)
        assertEquals(2.36.toFloat(), response.easinessFactor)
        assertTrue(response.nextRepetition.isAfter(LocalDateTime.now()))
    }

    @Test
    fun `should send single answer with quality 2 and check returned card`() {
        val card = getCard()

        val response = spacedRepetition.calculateRepetition(card, 2)

        assertEquals(0, response.repetitions)
        assertEquals(1, response.interval)
        assertEquals(2.18.toFloat(), response.easinessFactor)
        assertTrue(response.nextRepetition.isAfter(LocalDateTime.now()))
    }

    @Test
    fun `should send single answer with quality 1 and check returned card`() {
        val card = getCard()

        val response = spacedRepetition.calculateRepetition(card, 1)

        assertEquals(0, response.repetitions)
        assertEquals(1, response.interval)
        assertEquals(1.96.toFloat(), response.easinessFactor)
        assertTrue(response.nextRepetition.isAfter(LocalDateTime.now()))
    }

    @Test
    fun `should simulate successful cards repetition session and expect no more cards to repeat today`() {
        val deck = getDeckWithSixCards()

        val cardsAfterRepetition = deck.map { flashCard ->
            val repetition1 = spacedRepetition.calculateRepetition(flashCard, 1)
            val repetition2 = spacedRepetition.calculateRepetition(repetition1, 2)
            val repetition3 = spacedRepetition.calculateRepetition(repetition2, 3)
            val repetition4 = spacedRepetition.calculateRepetition(repetition3, 4)
            spacedRepetition.calculateRepetition(repetition4, 5)
        }

        val cardToRepeatToday = cardsAfterRepetition.filter { it.nextRepetition.isBefore(LocalDateTime.now()) }
        assertTrue(cardToRepeatToday.isEmpty())
    }

    @Test
    fun `should throw an exception if the user's quality of repetition response is invalid`() {
        val flashCard = getCard(repetitionDate = LocalDateTime.now().minusDays(1))

        Assertions.assertThrows(IllegalArgumentException::class.java) {
            spacedRepetition.calculateRepetition(flashCard, 6)
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            spacedRepetition.calculateRepetition(flashCard, -1)
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            spacedRepetition.calculateRepetition(flashCard, Int.MAX_VALUE)
        }
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            spacedRepetition.calculateRepetition(flashCard, Int.MIN_VALUE)
        }
    }

    private fun getCard(front: String = "🍎",
                        back: String = "Apple",
                        repetitionDate: LocalDateTime = LocalDateTime.now()) = Card(
            frontSide = front,
            backSide = back,
            nextRepetition = repetitionDate
    )

    private fun getDeckWithSixCards() = listOf(
            getCard(),
            getCard(repetitionDate = LocalDateTime.now().minusDays(1)),
            getCard(repetitionDate = LocalDateTime.now().minusDays(2)),
            getCard(repetitionDate = LocalDateTime.now().plusDays(2)),
            getCard(repetitionDate = LocalDateTime.now().plusDays(1)),
            getCard(repetitionDate = LocalDateTime.now().minusDays(3))
    )
}
