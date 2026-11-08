package dev.xueria.clumps

import dev.xueria.clumps.core.MergePlanFactory
import dev.xueria.clumps.core.OrbGroup
import dev.xueria.clumps.port.GroupMerger
import dev.xueria.clumps.port.OrbRetirer
import dev.xueria.clumps.port.OrbSpawner
import net.minecraft.World

class DefaultGroupMerger(
    private val planFactory: MergePlanFactory,
    private val spawner: OrbSpawner,
    private val retirer: OrbRetirer
) : GroupMerger {

    override fun merge(world: World, group: OrbGroup): Boolean =
        MergeTransaction(
            planFactory = planFactory,
            spawn = { spawner.spawn(world, it) },
            retire = { retirer.retire(world, it) }
        ).execute(group)
}
