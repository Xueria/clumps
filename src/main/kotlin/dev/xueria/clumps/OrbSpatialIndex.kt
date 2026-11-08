package dev.xueria.clumps

import dev.xueria.clumps.core.OrbSnapshot
import dev.xueria.clumps.core.Vector3d
import kotlin.math.floor

internal class OrbSpatialIndex(
    private val cellSize: Double
) {
    private val cells = HashMap<GridCell, MutableList<OrbSnapshot>>()

    init {
        require(cellSize.isFinite() && cellSize > 0.0) {
            "cellSize must be finite and positive"
        }
    }

    fun add(orb: OrbSnapshot) {
        cells.getOrPut(cellOf(orb.position)) { ArrayList() }.add(orb)
    }

    fun nearby(position: Vector3d, radius: Double): List<OrbSnapshot> {
        val center = cellOf(position)
        val result = ArrayList<OrbSnapshot>()

        for (dx in -1..1) {
            for (dy in -1..1) {
                for (dz in -1..1) {
                    val cell = GridCell(
                        center.x + dx,
                        center.y + dy,
                        center.z + dz
                    )
                    cells[cell]?.forEach { orb ->
                        if (isWithin(orb.position, position, radius)) {
                            result += orb
                        }
                    }
                }
            }
        }
        return result
    }

    private fun cellOf(position: Vector3d): GridCell = GridCell(
        floor(position.x / cellSize).toInt(),
        floor(position.y / cellSize).toInt(),
        floor(position.z / cellSize).toInt()
    )

    private fun isWithin(
        candidate: Vector3d,
        origin: Vector3d,
        radius: Double
    ): Boolean {
        val dx = candidate.x - origin.x
        val dy = candidate.y - origin.y
        val dz = candidate.z - origin.z
        return dx * dx + dy * dy + dz * dz <= radius * radius
    }

    private data class GridCell(
        val x: Int,
        val y: Int,
        val z: Int
    )
}
