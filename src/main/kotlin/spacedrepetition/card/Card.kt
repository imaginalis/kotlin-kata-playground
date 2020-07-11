package spacedrepetition.card

import java.time.LocalDateTime

data class Card(
        val frontSide: String,
        val backSide: String,
        val nextRepetition: LocalDateTime = LocalDateTime.now(),
        val repetitions: Int = 0,
        val easinessFactor: Float = 2.5.toFloat(),
        val interval: Int = 1
) {
    fun withUpdatedRepetitionProperties(
            newRepetitions: Int,
            newEasinessFactor: Float,
            newNextRepetitionDate: LocalDateTime,
            newInterval: Int
    ) = copy(repetitions = newRepetitions,
            easinessFactor = newEasinessFactor,
            nextRepetition = newNextRepetitionDate,
            interval = newInterval
    )
}
