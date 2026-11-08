package dev.xueria.clumps.core

fun interface OrbGrouper {
    fun group(orbs: List<OrbSnapshot>): List<OrbGroup>
}
