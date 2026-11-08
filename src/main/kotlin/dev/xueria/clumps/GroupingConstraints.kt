package dev.xueria.clumps

data class GroupingConstraints(
    val radius: Double,
    val maxGroupSize: Int,
    val maxValue: Int
) {
    init {
        require(radius.isFinite() && radius > 0.0) {
            "radius must be finite and positive"
        }
        require(maxGroupSize >= 2) {
            "maxGroupSize must be at least 2"
        }
        require(maxValue in 1..Limits.MAX_PACKET_VALUE) {
            "maxValue must be in 1..${Limits.MAX_PACKET_VALUE}"
        }
    }
}
