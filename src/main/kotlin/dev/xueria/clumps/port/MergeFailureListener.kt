package dev.xueria.clumps.port

import dev.xueria.clumps.core.OrbGroup
import net.minecraft.World

fun interface MergeFailureListener {
    fun onFailure(world: World, group: OrbGroup, error: Exception)
}
