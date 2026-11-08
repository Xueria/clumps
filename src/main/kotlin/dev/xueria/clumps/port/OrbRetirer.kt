package dev.xueria.clumps.port

import net.minecraft.World

fun interface OrbRetirer {
    fun retire(world: World, entityIds: List<Int>)
}
