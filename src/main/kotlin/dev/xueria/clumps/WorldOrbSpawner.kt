package dev.xueria.clumps

import dev.xueria.clumps.core.MergePlan
import dev.xueria.clumps.port.OrbSpawner
import net.minecraft.EntityXPOrb
import net.minecraft.World

class WorldOrbSpawner : OrbSpawner {
    override fun spawn(world: World, plan: MergePlan): Boolean {
        if (plan.value <= 0) return false
        val orb = EntityXPOrb(
            world,
            plan.position.x,
            plan.position.y,
            plan.position.z,
            plan.value
        )
        orb.xpOrbAge = plan.age
        orb.xpColor = plan.color
        orb.motionX = plan.motion.x
        orb.motionY = plan.motion.y
        orb.motionZ = plan.motion.z
        orb.prevPosX = orb.posX
        orb.prevPosY = orb.posY
        orb.prevPosZ = orb.posZ
        orb.lastTickPosX = orb.posX
        orb.lastTickPosY = orb.posY
        orb.lastTickPosZ = orb.posZ
        if (plan.owner != null) orb.setPlayerThisBelongsTo(plan.owner)
        if (plan.bottle) orb.setCreatedByBottleOfEnchanting()
        if (!world.spawnEntityInWorld(orb)) {
            orb.setDead()
            return false
        }
        return true
    }
}
