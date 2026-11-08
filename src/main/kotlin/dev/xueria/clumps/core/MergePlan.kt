package dev.xueria.clumps.core

data class MergePlan(
    val value: Int,
    val age: Int,
    val position: Vector3d,
    val motion: Vector3d,
    val owner: String?,
    val bottle: Boolean,
    val color: Int,
    val sourceEntityIds: List<Int>
)
