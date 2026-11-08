package dev.xueria.clumps

data class WorldMergeState(
    val ticksSinceRun: Int = 0,
    val dirty: Boolean = false
) {
    fun advance(): WorldMergeState = copy(ticksSinceRun = ticksSinceRun + 1)

    fun markDirty(): WorldMergeState = copy(dirty = true)
}
