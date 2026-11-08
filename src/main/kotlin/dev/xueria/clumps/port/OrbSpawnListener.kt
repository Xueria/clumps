package dev.xueria.clumps.port

import net.minecraft.World

fun interface OrbSpawnListener {
    fun onOrbSpawned(world: World)
}
