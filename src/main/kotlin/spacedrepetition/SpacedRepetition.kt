package spacedrepetition

import spacedrepetition.card.Card
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.logging.Logger
import kotlin.math.roundToInt

class SpacedRepetition {

    fun calculateRepetition(card: Card, quality: Int): Card {
        validateQualityFactorInput(quality)

        val easiness = calculateEasinessFactor(card.easinessFactor, quality)
        val repetitions = calculateRepetitions(quality, card.repetitions)
        val interval = calculateInterval(repetitions, card.interval, easiness)

        val cardAfterRepetition = card.withUpdatedRepetitionProperties(
                newRepetitions = repetitions,
                newEasinessFactor = easiness,
                newNextRepetitionDate = calculateNextPracticeDate(interval),
                newInterval = interval
        )
        log.info(cardAfterRepetition.toString())
        return cardAfterRepetition
    }

    private fun validateQualityFactorInput(quality: Int) {
        log.info("Input quality: $quality")
        if (quality < 0 || quality > 5) {
            throw IllegalArgumentException("Provided quality value is invalid ($quality)")
        }
    }

    private fun calculateEasinessFactor(easiness: Float, quality: Int) =
            Math.max(1.3, easiness + 0.1 - (5.0 - quality) * (0.08 + (5.0 - quality) * 0.02)).toFloat()


    private fun calculateRepetitions(quality: Int, cardRepetitions: Int) = if (quality < 3) {
        0
    } else {
        cardRepetitions + 1
    }

    private fun calculateInterval(repetitions: Int, cardInterval: Int, easiness: Float) = when {
        repetitions <= 1 -> 1
        repetitions == 2 -> 6
        else -> (cardInterval * easiness).roundToInt()
    }

    private fun calculateNextPracticeDate(interval: Int): LocalDateTime {
        val now = System.currentTimeMillis()
        val nextPracticeDate = now + dayInMs * interval
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(nextPracticeDate), ZoneId.systemDefault())
    }

    private companion object {
        private val dayInMs = Duration.ofDays(1).toMillis()
        private val log: Logger = Logger.getLogger(SpacedRepetition::class.java.name)
    }

}
