package dev.xueria.clumps

import dev.xueria.clumps.core.MergeCompatibility
import dev.xueria.clumps.core.OrbGroup
import dev.xueria.clumps.core.OrbGrouper
import dev.xueria.clumps.core.OrbSnapshot

class GridOrbGrouper(
    private val compatibility: MergeCompatibility,
    private val constraints: GroupingConstraints
) : OrbGrouper {

    override fun group(orbs: List<OrbSnapshot>): List<OrbGroup> {
        val index = OrbSpatialIndex(constraints.radius)
        orbs.forEach(index::add)

        val visited = HashSet<Int>()
        val groups = ArrayList<OrbGroup>()

        for (seed in orbs.sortedWith(seedOrder)) {
            if (!visited.add(seed.entityId)) continue
            buildGroup(seed, index, visited)?.let(groups::add)
        }
        return groups
    }

    private fun buildGroup(
        seed: OrbSnapshot,
        index: OrbSpatialIndex,
        visited: MutableSet<Int>
    ): OrbGroup? {
        if (seed.value <= 0 || seed.value > constraints.maxValue) return null

        val members = ArrayList<OrbSnapshot>()
        members += seed
        var total = seed.value.toLong()

        for (candidate in index.nearby(seed.position, constraints.radius)) {
            if (members.size >= constraints.maxGroupSize) break
            if (candidate.entityId in visited) continue
            if (!isCompatibleWithAll(members, candidate)) continue
            if (total + candidate.value > constraints.maxValue) continue

            members += candidate
            total += candidate.value
            visited += candidate.entityId
        }
        return if (members.size >= 2) OrbGroup(members) else null
    }

    private fun isCompatibleWithAll(
        members: List<OrbSnapshot>,
        candidate: OrbSnapshot
    ): Boolean = members.all { compatibility.canMerge(it, candidate) }

    private val seedOrder: Comparator<OrbSnapshot> =
        compareByDescending<OrbSnapshot> { it.age }.thenBy { it.entityId }
}
