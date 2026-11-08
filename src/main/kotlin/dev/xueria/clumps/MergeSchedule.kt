package dev.xueria.clumps

data class MergeSchedule(
    val intervalTicks: Int,
    val spawnDelayTicks: Int
) {
    init {
        require(intervalTicks >= 1)
        require(spawnDelayTicks >= 1)
    }

    fun shouldRun(state: WorldMergeState): Boolean =
        state.ticksSinceRun >= thresholdFor(state)

    private fun thresholdFor(state: WorldMergeState): Int =
        if (state.dirty) minOf(intervalTicks, spawnDelayTicks) else intervalTicks
}
