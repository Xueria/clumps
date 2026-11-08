package dev.xueria.clumps.core

data class OrbSnapshot(
    val entityId: Int,
    val value: Int,
    val age: Int,
    val owner: String?,
    val bottle: Boolean,
    val position: Vector3d,
    val motion: Vector3d,
    val color: Int
)
