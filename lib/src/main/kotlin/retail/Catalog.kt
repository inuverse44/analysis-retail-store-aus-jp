package retail

import kotlin.random.Random

/**
 * 大陸マスク済みランダムカタログの生成 (rejection sampling)
 *
 * バウンディングボックス内を一様サンプリングし、isOnContinent が true の点だけを採用。
 * CC / WW / CW で同一カタログを共有することで RR の分散源を統一する。
 *
 * @param n        目標点数 (通常 10 × max(N_C, N_W))
 * @param polygons loadPolygons() の戻り値
 * @param latMin/latMax/lonMin/lonMax  バウンディングボックス（デフォルト: オーストラリア全域）
 * @param seed     乱数シード（再現性のため固定推奨）
 * @return         陸上ランダム点のリスト（長さ = n）
 */
fun generateMaskedCatalog(
    n: Int,
    polygons: Map<Int, List<Pair<Double, Double>>>,
    latMin: Double = -44.0,
    latMax: Double = -10.0,
    lonMin: Double = 113.0,
    lonMax: Double = 154.0,
    seed: Long = 42L
): List<Point> {
    val rng = Random(seed)
    return generateSequence {
        Point(
            lat = latMin + rng.nextDouble() * (latMax - latMin),
            lon = lonMin + rng.nextDouble() * (lonMax - lonMin)
        )
    }
    .filter { isOnContinent(it.lat, it.lon, polygons) }
    .take(n)
    .toList()
}

/**
 * 陸上率 (f_land) を Monte Carlo で推定
 *
 * @param trials サンプル数（デフォルト 100_000）
 * @return       陸上判定率 [0, 1]
 */
fun estimateLandFraction(
    polygons: Map<Int, List<Pair<Double, Double>>>,
    latMin: Double = -44.0, latMax: Double = -10.0,
    lonMin: Double = 113.0, lonMax: Double = 154.0,
    trials: Int = 100_000,
    seed: Long = 99L
): Double {
    val rng = Random(seed)
    val accepted = (0 until trials).count {
        isOnContinent(
            latMin + rng.nextDouble() * (latMax - latMin),
            lonMin + rng.nextDouble() * (lonMax - lonMin),
            polygons
        )
    }
    return accepted.toDouble() / trials
}
