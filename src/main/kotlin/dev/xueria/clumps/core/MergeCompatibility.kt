package dev.xueria.clumps.core

fun interface MergeCompatibility {
    fun canMerge(a: OrbSnapshot, b: OrbSnapshot): Boolean
}
