package dev.xueria.clumps.core

class OrbGroup(
    val orbs: List<OrbSnapshot>
) {

    val seed: OrbSnapshot = orbs.first()

    val total: Long = orbs.sumOf { it.value.toLong() }

}
