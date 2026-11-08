package dev.xueria.clumps

import dev.xueria.clumps.core.OrbSnapshot
import dev.xueria.clumps.core.Vector3d
import dev.xueria.clumps.port.OrbSource
import net.minecraft.EntityXPOrb
import net.minecraft.World

class WorldOrbSource : OrbSource {
    override fun snapshot(world: World): List<OrbSnapshot> {
        val result = ArrayList<OrbSnapshot>()
        for (element in world.loadedEntityList) {
            val orb = element as? EntityXPOrb ?: continue
            if (orb.isDead || orb.worldObj !== world) continue
            result += snapshotOf(orb)
        }
        return result
    }

    private fun snapshotOf(orb: EntityXPOrb): OrbSnapshot = OrbSnapshot(
        entityId = orb.entityId,
        value = orb.getXpValue(),
        age = orb.xpOrbAge,
        owner = orb.player_this_belongs_to?.takeIf { it.isNotEmpty() },
        bottle = orb.created_by_bottle_of_enchanting,
        position = Vector3d(orb.posX, orb.posY, orb.posZ),
        motion = Vector3d(orb.motionX, orb.motionY, orb.motionZ),
        color = orb.xpColor
    )
}
