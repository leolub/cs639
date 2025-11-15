package com.example.mountainmarkers



enum class Direction(val sign: Int) {
    NORTH(1),
    EAST(1),
    SOUTH(-1),
    WEST(-1),
}


data class DMS(
    val direction: Direction,
    val degrees: Double,
    val minutes: Double = 0.0,
    val seconds: Double = 0.0,
)


fun DMS.toDecimalDegrees(): Double {
    val decimal = degrees + (minutes / 60.0) + (seconds / 3600.0)
    return decimal * direction.sign
}
