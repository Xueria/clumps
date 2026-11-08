package dev.xueria.clumps.port

import dev.xueria.clumps.core.OrbSnapshot
import net.minecraft.World

fun interface OrbSource {
    fun snapshot(world: World): List<OrbSnapshot>
}
