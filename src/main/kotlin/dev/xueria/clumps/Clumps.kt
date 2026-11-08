package dev.xueria.clumps

import dev.xueria.clumps.port.MergeFailureListener
import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Clumps : ModInitializer {

    const val MOD_ID = "clumps"

    val logger: Logger = LogManager.getLogger(MOD_ID)

    var scheduler: WorldMergeScheduler? = null
        private set

    override fun onInitialize() {
        scheduler = buildScheduler()
        logger.info("Clumps initialized")
    }

    private fun buildScheduler(): WorldMergeScheduler {
        val compatibility = DefaultMergeCompatibility()
        val constraints = GroupingConstraints(
            radius = 3.0,
            maxGroupSize = 64,
            maxValue = Limits.MAX_PACKET_VALUE
        )
        val planFactory = DefaultMergePlanFactory(
            Limits.MAX_PACKET_VALUE,
            DefaultAgeMerge(AgeMergeMode.MIN)
        )
        val merger = DefaultGroupMerger(
            planFactory,
            WorldOrbSpawner(),
            WorldOrbRetirer()
        )
        return WorldMergeScheduler(
            source = WorldOrbSource(),
            grouper = GridOrbGrouper(compatibility, constraints),
            merger = merger,
            schedule = MergeSchedule(
                intervalTicks = 20,
                spawnDelayTicks = 2
            ),
            maxMergesPerRun = 128,
            failureListener = MergeFailureListener { _, group, error ->
                logger.error("Clumps merge failed for ${group.orbs.size} orbs", error)
            }
        )
    }
}
