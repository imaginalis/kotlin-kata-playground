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

        // retrieve the stored values (default values if new cards)
        var repetitions: Int = card.repetitions
        var easiness: Float = card.easinessFactor
        var interval: Int = card.interval

        // easiness factor
        easiness = calculateEasinessFactor(easiness, quality)

        // repetitions
        if (quality < 3) {
            repetitions = 0
        } else {
            repetitions += 1
        }

        // interval
        interval = when {
            repetitions <= 1 -> 1
            repetitions == 2 -> 6
            else -> (interval * easiness).roundToInt()
        }
        log.info(
                "For card ${card.frontSide} calculated easiness = $easiness, " +
                        "interval = $interval, " +
                        "next repetition = ${calculateNextPracticeDate(interval)}," +
                        "rep total = $repetitions"
        )

        return card.withUpdatedRepetitionProperties(
                newRepetitions = repetitions,
                newEasinessFactor = easiness,
                newNextRepetitionDate = calculateNextPracticeDate(interval),
                newInterval = interval
        )
    }

    private fun calculateEasinessFactor(easiness: Float, quality: Int) =
            Math.max(1.3, easiness + 0.1 - (5.0 - quality) * (0.08 + (5.0 - quality) * 0.02)).toFloat()

    private fun validateQualityFactorInput(quality: Int) {
        log.info("Input quality: $quality")
        if (quality < 0 || quality > 5) {
            throw IllegalArgumentException("Provided quality value is invalid ($quality)")
        }
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
