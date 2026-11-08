package dev.xueria.clumps.port

import dev.xueria.clumps.core.MergePlan
import net.minecraft.World

fun interface OrbSpawner {
    fun spawn(world: World, plan: MergePlan): Boolean
}
