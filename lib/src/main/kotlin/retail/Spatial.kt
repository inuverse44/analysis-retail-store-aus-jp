package retail

import kotlin.math.*

/** 地理座標 (WGS84 度単位) */
data class Point(val lat: Double, val lon: Double)

/**
 * Haversine 大円距離 [km]
 * 地球半径 R = 6371.0 km (平均)
 */
fun haversine(p1: Point, p2: Point): Double {
    val R = 6371.0
    val dLat = Math.toRadians(p2.lat - p1.lat)
    val dLon = Math.toRadians(p2.lon - p1.lon)
    val sinLat = sin(dLat / 2)
    val sinLon = sin(dLon / 2)
    val a = sinLat * sinLat +
            cos(Math.toRadians(p1.lat)) * cos(Math.toRadians(p2.lat)) * sinLon * sinLon
    return 2.0 * R * asin(sqrt(a))
}

/**
 * ペアカウント（対数ビン向け二分探索）
 *
 * points2 == null のとき自己ペア（i < j のみ）をカウント。
 * 異種ペア（cross-correlation）は points2 に相手のリストを渡す（全 i×j をカウント）。
 *
 * @param points1 第1点集合
 * @param points2 第2点集合（null → 自己相関）
 * @param bins    ビン端点の昇順配列（長さ nBins+1）
 * @return        各ビンのペア数（長さ nBins）
 */
fun pairCounts(
    points1: List<Point>,
    points2: List<Point>?,
    bins: DoubleArray
): LongArray {
    val counts = LongArray(bins.size - 1)
    val pts2 = points2 ?: points1
    val selfPairs = points2 == null

    for (i in points1.indices) {
        val jStart = if (selfPairs) i + 1 else 0
        for (j in jStart until pts2.size) {
            val d = haversine(points1[i], pts2[j])
            var lo = 0; var hi = bins.size - 1
            while (lo < hi - 1) {
                val mid = (lo + hi) / 2
                if (bins[mid] <= d) lo = mid else hi = mid
            }
            if (d >= bins[lo] && d < bins[hi]) counts[lo]++
        }
    }
    return counts
}
