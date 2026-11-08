package dev.xueria.clumps.core

fun interface MergePlanFactory {
    fun create(group: OrbGroup): MergePlan?
}
