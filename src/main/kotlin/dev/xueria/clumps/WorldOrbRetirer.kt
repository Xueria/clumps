package dev.xueria.clumps

import dev.xueria.clumps.port.OrbRetirer
import net.minecraft.EntityXPOrb
import net.minecraft.World

class WorldOrbRetirer : OrbRetirer {
    override fun retire(world: World, entityIds: List<Int>) {
        for (id in entityIds) {
            val orb = world.getEntityByID(id) as? EntityXPOrb ?: continue
            orb.setDead()
        }
    }
}
