package dev.xueria.clumps

import dev.xueria.clumps.core.MergePlan
import dev.xueria.clumps.core.MergePlanFactory
import dev.xueria.clumps.core.OrbGroup

internal class MergeTransaction(
    private val planFactory: MergePlanFactory,
    private val spawn: (MergePlan) -> Boolean,
    private val retire: (List<Int>) -> Unit
) {

    fun execute(group: OrbGroup): Boolean {
        val plan = planFactory.create(group) ?: return false
        if (!spawn(plan)) return false

        retire(plan.sourceEntityIds)
        return true
    }
}
