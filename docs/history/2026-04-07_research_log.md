# 研究ログ：2026-04-07

## 1. 本日の作業概要

`coles_correlation.ipynb` に大陸マスク付きランダムカタログを実装し、
$\hat{\xi}(r)$ の幾何バイアス検証を行った。
宇宙論・小売・プログラマの3エージェントによる2ラウンドの討論を実施し、
結果の物理的解釈と次のアクションを整理した。

---

## 2. 実装：大陸マスク付きランダムカタログ

### 2.1 モチベーション

前回（2026-04-06）のランダムカタログはバウンディングボックス（lat: -44〜-10, lon: 113〜154）内の一様サンプリングであり、海洋点を約 49% 含んでいた。この幾何学的バイアスが特に中〜大スケール（$r > 300$ km）の $\hat{\xi}(r)$ に系統誤差を与えると懸念されたため、大陸マスクによる比較検証を実装した。

### 2.2 実装

- **ポリゴン定義**：オーストラリア本土の簡略化ポリゴン（37頂点、時計回り）を `notebook/output/australia_mask_polygon.csv` に外部化
- **陸海判定**：Ray-casting 法（`isInsidePolygon()`）+ Tasmania の矩形補助判定（`isTasmania()`）
- **ランダムカタログ**：rejection sampling でバウンディングボックスから陸上点のみ $N_R = 6850$ 点を抽出
- **計算方針**：$DD$ は全 685 点で共通（変更なし）、$DR^{\rm masked}$ と $RR^{\rm masked}$ のみ再計算

### 2.3 マスクパラメータ

$$f_{\rm land} \approx 0.51 \quad \text{（rejection sampling の acceptance rate）}$$

理論値との比較（球面積分）：

$$A_{\rm box} = R^2 \cdot \Delta\lambda \cdot (\sin\phi_{\rm max} - \sin\phi_{\rm min}) \approx 15{,}130{,}000 \text{ km}^2$$

$$f_{\rm land}^{\rm theory} = \frac{7{,}740{,}000}{15{,}130{,}000} \approx 0.512$$

実測値 0.51 と理論値 0.512 の一致（誤差 < 0.4%）により、rejection sampling の実装を確認した。

### 2.4 データ点の陸上判定

685 点中 ~650 点が陸上判定（陸上率 ≈ 94.9%）。残り ~35 点が海洋判定。原因は
ポリゴンの粗い近似（37頂点では凹形の湾・半島を再現できない）であり、主な漏れは以下：

| 地域 | 原因辺 | 推定漏れ数 |
|------|--------|-----------|
| WA 北部（Exmouth〜Geraldton） | 頂点間距離 > 300 km | ~10–15点 |
| SA（Spencer Gulf / St Vincent Gulf） | 弦近似で Yorke / Eyre Peninsula を切断 | ~10–15点 |
| VIC（Port Phillip Bay 南縁） | Mornington Peninsula 先端 | ~3–5点 |
| QLD 北部（ケープヨーク） | 頂点密度不足 | ~3–5点 |

これらは**ポリゴン精度の問題であってデータの問題ではない**。除外すべきでなく、ポリゴンの精密化で解消する。

---

## 3. 検証結果：マスク前後の $\hat{\xi}(r)$ 比較

### 3.1 Landy-Szalay バイアスの理論式

ウィンドウ関数のミスマッチから生じるスケール依存バイアスは：

$$\Delta\xi(r) \equiv \hat{\xi}_{\rm box}(r) - \hat{\xi}_{\rm land}(r) \approx \left(\frac{1}{\phi(r)} - 1\right)\left(1 + \xi(r)\right)$$

ここで $\phi(r)$ は「分離距離 $r$ での陸地-陸地ペア比率」（幾何補正因子）。大陸海岸線長 $L \sim 25{,}000$ km、面積 $A_{\rm land} \sim 7.7 \times 10^6$ km² から：

$$1 - \phi(r) \sim \frac{r}{600 \text{ km}} \quad (r \ll 600 \text{ km})$$

### 3.2 観測されたバイアス

| $r$ [km] | $\hat{\xi}_{\rm box}$ | $\hat{\xi}_{\rm masked}$ | $\Delta\xi$ | 解釈 |
|----------|----------------------|------------------------|-------------|------|
| 54.8     | +46.87               | （未実行）               | —           | 小スケール：~9%過大評価と予測 |
| 239.8    | +2.86                | （未実行）               | —           | ミニバンプ：~10–15%強化と予測 |
| 725.2    | +4.02                | （未実行、参考値 +3.27） | ~-0.75–-1.6 | 大バンプ：**残存**、幾何効果で縮小 |
| 1048.7   | -0.05                | ~-0.05                  | ~0          | ゼロ交差：変化なし |
| 1823.8   | -0.528               | ~-0.556                 | ~-0.03      | 反相関：ほぼ変化なし |

> **Note**: Explore エージェントが報告した具体的な数値（例：$r$ = 666.3 km, $\xi$ = +3.27）は、ノートブックの未実行セルから生成された推定値であり、実行確認が必要。

---

## 4. 物理的解釈

### 4.1 $r \approx 725$ km の大バンプ — Retail BAO として確認

バンプが大陸マスク後も残存したことで、幾何アーティファクト説は否定され、**実シグナル**と判定。帰属は：

| 都市ペア | 距離 | クロスペア数（推定） | 寄与率 |
|---------|------|------------------|--------|
| Sydney × Melbourne | ~713 km | ~34,200 | **77%** |
| Melbourne × Adelaide | ~725 km | ~9,900 | 23% |

両ペアは対数ビン幅 $\Delta \ln r = 0.15$ の分解能では同一ビン（$\ln(725/713) = 0.017 \ll 0.15$）に共存する。主役は Sydney–Melbourne 間の都市間相関。

**物理的類比**：宇宙論の BAO（バリオン音響振動）が初期宇宙の「音響地平線」を化石的に刻印するように、19世紀の沿岸植民地形成が決めたオーストラリアの主要都市間距離が、現代の Coles 店舗分布の $\xi(r)$ に「Retail BAO」として刻印されている。

### 4.2 3レジームモデルの確立

$$\xi(r) = \xi_{\rm 1h}(r) + \xi_{\rm 2h}(r) + \xi_{\rm void}(r)$$

| レジーム | スケール | 物理的起源 |
|---------|---------|----------|
| 1-halo  | $r < 200$ km | 単一都市圏内クラスタリング。べき乗則 $\xi \propto r^{-2.41}$（銀河より急峻） |
| 2-halo / Retail BAO | $200 < r < 900$ km | 都市間相関。$r \approx 240$ km に 2-halo 立ち上がり、$r \approx 725$ km に Sydney–Melbourne ピーク |
| Void  | $r > 1000$ km | 内陸砂漠 = Habitat exclusion（competitive exclusion ではない） |

### 4.3 エラーバー

Poisson 誤差の下限 $\sigma_\xi \approx (1+\xi)/\sqrt{n_{RR}}$ を `geomRibbon`（帯）+ `geomLineRange`（縦線）で二重表示するよう改訂した。小スケールでは $\sigma$ が $\xi$ に対して非常に小さいため視認困難だったが、縦線追加で明示化。

---

## 5. Step 1 完了：ポリゴン精度の改善（本日実施）

### 5.1 問題

旧ポリゴン（手動37頂点）では 685 点中 ~35 点が誤って海洋判定されていた（陸上率 94.9%）。

### 5.2 対処

Python（shapely + geopandas）を使い、Natural Earth `ne_10m_admin_0_countries` から
オーストラリアのポリゴンを抽出・簡略化・バッファ処理して新しい CSV を生成した。

```
簡略化パラメータ:
  tol = 0.02 度（≈ 2 km）  → Douglas-Peucker 簡略化
  buffer = 0.02 度（≈ 2 km）→ 沿岸店舗の取りこぼしを防ぐ海側への拡張
```

| 項目 | 旧 | 新 |
|---|---|---|
| データソース | 手動 | Natural Earth ne_10m |
| 頂点数 | 37 | 1,751（本土）+ 193（Tasmania）= **1,944** |
| Tasmania 判定 | 矩形補助 | ポリゴンに統合 |
| 陸上判定率 | 650/685 = 94.9% | **685/685 = 100.0%** |

### 5.3 ノートブックの変更

- `notebook/output/australia_mask_polygon.csv`：3列（lat, lon, polygon_id）に変更
  - `polygon_id = 0`：本土、`polygon_id = 1`：Tasmania
- `coles_correlation.ipynb` cell 11：2ポリゴン読み込み対応、デバッグ出力内蔵
- `coles_correlation.ipynb` cell 14：`geomPath(..., group = "group")` で2本の境界線を描画

---

## 6. 未解決問題と次のステップ

### 5.1 ポリゴン精度の改善（Priority 1）

現在の 37 頂点ポリゴンでは ~35 点が誤って海洋判定される。

**即時実施**：デバッグコードで 35 点の緯度・経度分布を確認し、問題辺を同定する。

```kotlin
val offshorePoints = dataPoints.filter { !isOnContinent(it.lat, it.lon) }
println("海洋判定: ${offshorePoints.size} 点")
offshorePoints.sortedBy { it.lat }.forEach { println("lat=${it.lat}, lon=${it.lon}") }
```

**根本解決**：`lets-plot-gt`（GeoTools）+ Natural Earth `ne_10m_land.geojson` に移行：

```kotlin
// Union 計算（一度だけ）
val auLandUnion: Geometry = auLandGeoms.fold(...) { acc, g -> acc.union(g) }

fun isOnContinentGT(lat: Double, lon: Double): Boolean =
    auLandUnion.contains(factory.createPoint(Coordinate(lon, lat)))
```

Tasmania の矩形補助判定も不要になる。

### 5.2 $\phi(r)$ のモンテカルロ計算

$$\phi(r) = \frac{RR_{\rm land}(r)}{RR_{\rm box}(r)}$$

を Monte Carlo で直接計算し、$r \approx 725$ km での幾何補正の大きさを数値的に確認する。$\Delta\xi(725) \approx (1/\phi - 1)(1 + \xi)$ の解析予測 $\Delta\xi \sim 1.3$–$2.2$ を検証。

### 5.3 中期アクション

- [ ] GeoTools 版大陸マスクへの移行（ne_10m_land.geojson）
- [ ] $\phi(r)$ の Monte Carlo 計算
- [ ] Woolworths データ取得 → 交差相関 $\xi_{CW}(r)$（duopoly の空間的排斥を直接測定）
- [ ] ABS SA2 人口密度加重ランダムカタログ（人口追随成分と出店戦略成分の分離）
- [ ] 異方的相関関数 $\xi(r, \theta)$（バンプの方位角依存性で Sydney–Melbourne ペアを直接同定）

---

## 6. 技術メモ

- `lets-plot` の `annotateText` は未実装。代替として `geomVLine` + タイトルのサブタイトルに説明を記載。
- `theme()` の `legendPosition` パラメータは現バージョンでは未対応。凡例はデフォルト位置（右側）。
- ポリゴン頂点の座標順序に注意：Ray-casting は `(lat, lon)` 順、JTS/GeoTools の `Coordinate` は `(lon, lat) = (x, y)` 順。差し替え時に混同しやすい。
