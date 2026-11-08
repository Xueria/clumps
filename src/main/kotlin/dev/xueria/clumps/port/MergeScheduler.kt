package dev.xueria.clumps.port

import net.minecraft.World

fun interface MergeScheduler {
    fun tick(world: World)
}
