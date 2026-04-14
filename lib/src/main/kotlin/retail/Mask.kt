package retail

/**
 * Ray-casting 法によるポリゴン内判定
 *
 * 座標順: (lat, lon) — JTS/GeoTools の (lon, lat) と逆なので注意。
 * @param poly 頂点リスト [(lat, lon), ...] 任意巻き順
 */
fun isInsidePolygon(lat: Double, lon: Double, poly: List<Pair<Double, Double>>): Boolean {
    var inside = false
    var j = poly.size - 1
    for (i in poly.indices) {
        val (latI, lonI) = poly[i]
        val (latJ, lonJ) = poly[j]
        if ((latI > lat) != (latJ > lat)) {
            val lonCross = (lonJ - lonI) * (lat - latI) / (latJ - latI) + lonI
            if (lon < lonCross) inside = !inside
        }
        j = i
    }
    return inside
}

/**
 * CSV からポリゴン頂点を読み込む
 *
 * 期待フォーマット: ヘッダ行 "lat,lon,polygon_id" の後にデータ行
 * '#' 始まりの行はコメントとしてスキップ
 *
 * @return polygon_id → [(lat, lon), ...] のマップ
 */
fun loadPolygons(csvPath: String): Map<Int, List<Pair<Double, Double>>> {
    val lines = java.io.File(csvPath).readLines().filter { !it.startsWith("#") }
    val header = lines.first().split(",")
    val latIdx = header.indexOf("lat")
    val lonIdx = header.indexOf("lon")
    val idIdx  = header.indexOf("polygon_id")

    return lines.drop(1)
        .map { line ->
            val cols = line.split(",")
            Triple(cols[latIdx].toDouble(), cols[lonIdx].toDouble(), cols[idIdx].toInt())
        }
        .groupBy { it.third }
        .mapValues { (_, rows) -> rows.map { Pair(it.first, it.second) } }
}

/**
 * いずれかのポリゴンに (lat, lon) が含まれれば true
 *
 * @param polygons loadPolygons() の戻り値
 */
fun isOnContinent(lat: Double, lon: Double, polygons: Map<Int, List<Pair<Double, Double>>>): Boolean =
    polygons.values.any { isInsidePolygon(lat, lon, it) }
