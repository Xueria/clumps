package dev.xueria.clumps

import dev.xueria.clumps.core.AgeMerge
import dev.xueria.clumps.core.MergePlan
import dev.xueria.clumps.core.MergePlanFactory
import dev.xueria.clumps.core.OrbGroup

class DefaultMergePlanFactory(
    maxValue: Int,
    private val ageMerge: AgeMerge
) : MergePlanFactory {

    private val maxValue: Int = maxValue.coerceIn(1, Limits.MAX_PACKET_VALUE)

    override fun create(group: OrbGroup): MergePlan? {
        if (group.orbs.any { it.value <= 0 }) return null
        val total = group.total
        if (total > maxValue) return null
        val seed = group.seed
        return MergePlan(
            value = total.toInt(),
            age = ageMerge.mergedAge(group.orbs),
            position = seed.position,
            motion = seed.motion,
            owner = seed.owner,
            bottle = seed.bottle,
            color = seed.color,
            sourceEntityIds = group.orbs.map { it.entityId }
        )
    }

}
