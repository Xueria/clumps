package dev.xueria.clumps

import dev.xueria.clumps.core.AgeMerge
import dev.xueria.clumps.core.OrbSnapshot

class DefaultAgeMerge(
    private val mode: AgeMergeMode
) : AgeMerge {

    override fun mergedAge(orbs: List<OrbSnapshot>): Int {
        require(orbs.isNotEmpty()) { "orbs must not be empty" }

        return when (mode) {
            AgeMergeMode.MIN -> orbs.minOf { it.age }
            AgeMergeMode.MAX -> orbs.maxOf { it.age }
            AgeMergeMode.RESET -> 0
        }
    }

}
