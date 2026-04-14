package retail

import kotlin.math.*

/** Landy-Szalay 推定量の1ビン */
data class XiBin(
    val rCenter: Double,   // ビン幾何平均中心 [km]
    val xi: Double,        // ξ 推定値
    val xiErr: Double,     // Poisson 誤差下限 (1+ξ)/√n_RR
    val nRR: Long          // 生の RR ペア数
)

/**
 * 対数ビン端点を生成
 *
 * CLAUDE.md 規約: rMin = meanNN/2, Δln r ≈ 0.15
 *
 * @param rMin   最小スケール [km]
 * @param rMax   最大スケール [km]
 * @param dLogR  目標対数ビン幅（デフォルト 0.15）
 * @return       長さ (nBins+1) の端点配列
 */
fun logBins(rMin: Double, rMax: Double = 2000.0, dLogR: Double = 0.15): DoubleArray {
    val nBins = ceil(ln(rMax / rMin) / dLogR).toInt()
    return DoubleArray(nBins + 1) { i -> rMin * (rMax / rMin).pow(i.toDouble() / nBins) }
}

/** ビン幾何平均中心 */
fun binCenters(bins: DoubleArray): DoubleArray =
    DoubleArray(bins.size - 1) { i -> sqrt(bins[i] * bins[i + 1]) }

/**
 * Landy-Szalay 推定量
 *
 * ξ̂(r) = (DD - 2DR + RR) / RR
 *
 * 各カウント nDD, nDR, nRR はビン毎の生カウント。
 * normDD, normDR, normRR は N*(N-1)/2 または N1*N2 形式の正規化係数。
 */
fun landySzalay(
    nDD: LongArray,
    nDR: LongArray,
    nRR: LongArray,
    normDD: Long,
    normDR: Long,
    normRR: Long,
    bins: DoubleArray
): List<XiBin> {
    val centers = binCenters(bins)
    return centers.indices.map { i ->
        val dd = nDD[i].toDouble() / normDD
        val dr = nDR[i].toDouble() / normDR
        val rr = if (normRR > 0L) nRR[i].toDouble() / normRR else 0.0
        val xi    = if (rr > 0.0) (dd - 2.0 * dr + rr) / rr else Double.NaN
        val xiErr = if (nRR[i] > 0L) (1.0 + xi) / sqrt(nRR[i].toDouble()) else Double.NaN
        XiBin(centers[i], xi, xiErr, nRR[i])
    }
}

/**
 * 空間ジャックナイフ誤差
 *
 * σ²_JK(r) = (K-1)/K × Σ_k [ξ_k(r) - ξ̄_JK(r)]²
 *
 * @param xiJK [patch_k][bin] の leave-one-out 推定量
 * @return     各ビンのジャックナイフ σ
 */
fun jackknifeSigma(xiJK: Array<DoubleArray>): DoubleArray {
    val K = xiJK.size
    val nBins = xiJK[0].size
    return DoubleArray(nBins) { b ->
        val vals = xiJK.mapNotNull { it[b].takeIf { v -> !v.isNaN() } }
        val n = vals.size
        if (n < 2) return@DoubleArray Double.NaN
        val mean = vals.average()
        sqrt((n - 1.0) / n * vals.sumOf { (it - mean) * (it - mean) })
    }
}
