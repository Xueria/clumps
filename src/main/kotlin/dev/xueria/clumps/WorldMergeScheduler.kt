package dev.xueria.clumps

import dev.xueria.clumps.core.OrbGroup
import dev.xueria.clumps.port.GroupMerger
import dev.xueria.clumps.port.MergeFailureListener
import dev.xueria.clumps.port.MergeScheduler
import dev.xueria.clumps.port.OrbSource
import dev.xueria.clumps.port.OrbSpawnListener
import net.minecraft.World
import java.util.WeakHashMap

class WorldMergeScheduler(
    private val source: OrbSource,
    private val grouper: dev.xueria.clumps.core.OrbGrouper,
    private val merger: GroupMerger,
    private val schedule: MergeSchedule,
    private val maxMergesPerRun: Int = 128,
    private val failureListener: MergeFailureListener = MergeFailureListener { _, _, _ -> }
) : MergeScheduler, OrbSpawnListener {
    private val states = WeakHashMap<World, WorldMergeState>()

    override fun tick(world: World) {
        val state = advance(world)
        if (!schedule.shouldRun(state)) return
        try {
            runMerge(world)
        } finally {
            states[world] = WorldMergeState()
        }
    }

    override fun onOrbSpawned(world: World) {
        states[world] = stateOf(world).markDirty()
    }

    private fun advance(world: World): WorldMergeState {
        val next = stateOf(world).advance()
        states[world] = next
        return next
    }

    private fun stateOf(world: World): WorldMergeState =
        states[world] ?: WorldMergeState()

    private fun runMerge(world: World) {
        val groups = grouper.group(source.snapshot(world))
        val limit = maxMergesPerRun.coerceAtLeast(1)
        for (group in groups.take(limit)) {
            mergeSafely(world, group)
        }
    }

    private fun mergeSafely(world: World, group: OrbGroup) {
        try {
            merger.merge(world, group)
        } catch (error: Exception) {
            failureListener.onFailure(world, group, error)
        }
    }
}
