package dev.xueria.clumps.port

import dev.xueria.clumps.core.OrbGroup
import net.minecraft.World

fun interface GroupMerger {
    fun merge(world: World, group: OrbGroup): Boolean
}
