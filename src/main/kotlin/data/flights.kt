package org.example.data

fun flight(
    id: Int, originAirportId: Int, destinationAirportId: Int, year: Int, month: Int, day: Int, hour: Int, minute: Int
): MutableMap<String, Any> {

    return mutableMapOf(
        "id" to id,
        "originAirportId" to originAirportId,
        "destinationAirportId" to destinationAirportId,
        "year" to year,
        "month" to month,
        "day" to day,
        "hour" to hour,
        "minute" to minute
    )
}

fun flights(): MutableList<MutableMap<String, Any>> {
    return mutableListOf(
        flight(1, 8, 7, 2024, 7, 28, 17, 50),
        flight(2, 2, 8, 2024, 7, 30, 12, 44),
        flight(3, 1, 8, 2024, 7, 26, 13, 56),
        flight(4, 1, 8, 2024, 7, 28, 9, 28),
        flight(5, 8, 3, 2024, 7, 27, 11, 45),
        flight(6, 8, 5, 2024, 7, 28, 13, 49),
        flight(7, 8, 1, 2024, 7, 30, 18, 5),
        flight(8, 8, 2, 2024, 7, 30, 20, 56),
        flight(9, 2, 8, 2024, 7, 27, 16, 48),
        flight(10, 8, 4, 2024, 7, 30, 13, 55),
        flight(11, 8, 12, 2024, 7, 30, 13, 7),
        flight(12, 2, 8, 2024, 7, 30, 18, 57),
        flight(13, 3, 8, 2024, 7, 26, 15, 37),
        flight(14, 5, 8, 2024, 7, 26, 12, 8),
        flight(15, 8, 1, 2024, 7, 27, 7, 54),
        flight(16, 8, 2, 2024, 7, 26, 20, 44),
        flight(17, 8, 4, 2024, 7, 28, 20, 16),
        flight(18, 8, 6, 2024, 7, 29, 16, 0),
        flight(19, 2, 8, 2024, 7, 28, 21, 15),
        flight(20, 6, 8, 2024, 7, 28, 15, 22),
        flight(21, 3, 8, 2024, 7, 26, 17, 58),
        flight(22, 1, 8, 2024, 7, 28, 12, 51),
        flight(23, 8, 11, 2024, 7, 29, 12, 15),
        flight(24, 7, 8, 2024, 7, 30, 16, 27),
        flight(25, 5, 8, 2024, 7, 27, 14, 33),
        flight(26, 2, 8, 2024, 7, 27, 13, 32),
        flight(27, 5, 8, 2024, 7, 28, 8, 35),
        flight(28, 3, 8, 2024, 7, 26, 22, 49),
        flight(29, 8, 11, 2024, 7, 27, 14, 48),
        flight(30, 8, 1, 2024, 7, 26, 7, 16),
        flight(31, 8, 3, 2024, 7, 30, 20, 21),
        flight(32, 5, 8, 2024, 7, 27, 19, 20),
        flight(33, 6, 8, 2024, 7, 28, 17, 58),
        flight(34, 8, 5, 2024, 7, 28, 17, 20),
        flight(35, 8, 4, 2024, 7, 28, 16, 16),
        flight(36, 8, 4, 2024, 7, 29, 8, 20),
        flight(37, 8, 3, 2024, 7, 27, 7, 37),
        flight(38, 8, 6, 2024, 7, 26, 14, 35),
        flight(39, 5, 8, 2024, 7, 27, 22, 37),
        flight(40, 7, 8, 2024, 7, 28, 13, 47),
        flight(41, 3, 8, 2024, 7, 28, 14, 14),
        flight(42, 4, 8, 2024, 7, 30, 13, 22),
        flight(43, 8, 1, 2024, 7, 29, 9, 30),
        flight(44, 8, 3, 2024, 7, 30, 13, 19),
        flight(45, 8, 2, 2024, 7, 27, 13, 35),
        flight(46, 8, 10, 2024, 7, 26, 15, 31),
        flight(47, 4, 8, 2024, 7, 30, 9, 46),
        flight(48, 5, 8, 2024, 7, 30, 18, 28),
        flight(49, 8, 6, 2024, 7, 27, 8, 5),
        flight(50, 8, 6, 2024, 7, 26, 9, 16),
        flight(51, 4, 8, 2024, 7, 28, 18, 3),
        flight(52, 3, 8, 2024, 7, 27, 22, 19),
        flight(53, 8, 9, 2024, 7, 29, 15, 20),
        flight(54, 8, 5, 2024, 7, 30, 10, 19),
        flight(55, 8, 4, 2024, 7, 26, 21, 44),
        flight(56, 4, 8, 2024, 7, 26, 18, 24),
        flight(57, 3, 8, 2024, 7, 30, 14, 30),
        flight(58, 3, 8, 2024, 7, 30, 11, 35)
    )
}
