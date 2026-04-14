# 研究ログ：2026-04-08

## 1. 本日の作業概要

`woolworths_location.ipynb` を実行して Woolworths 店舗 CSV を取得し、
`coles_correlation.ipynb` の交差相関セル（Cell 17–29）を実行した。
3エージェント討論（2ラウンド）の合意事項に基づき、以下の定量解析を実施：

1. $\xi_{CC} - \xi_{CW}$ の全スケールゼロ交差点の精密推定
2. $r \approx 114$ km・239 km バンプの都市ペア帰属
3. Woolworths 店舗数の確認

---

## 2. Woolworths 店舗データ

- **取得件数: 1,039 店舗**（Coles 685 店舗の **151.7%**）
- OSM タグ: `shop=supermarket` + `brand=Woolworths`
- 出力: `notebook/output/woolworths_locations.csv`

---

## 3. $\xi_{CC}$ vs $\xi_{CW}$：全スケールゼロ交差

$\delta(r) \equiv \xi_{CC}(r) - \xi_{CW}(r)$ のゼロ交差点（線形補間）：

| ゼロ交差 | $r$ [km] | 方向 | 物理的意味 |
|---|---|---|---|
| 1st | 8.2 | $\xi_{CC} < \xi_{CW}$ → $>$ | アンカー共存から同種クラスタリングへの移行 |
| **2nd** | **71.3** | $\xi_{CC} > \xi_{CW}$ → $<$ | **ブランド勢力圏の外縁（主要ゼロ交差）** |
| 3rd | 107.3 | $\xi_{CW} > \xi_{CC}$ → $<$ | 114 km バンプに伴う局所復帰 |
| 4th | 121.2 | $\xi_{CC} > \xi_{CW}$ → $<$ | 同上（急峻な局所ピーク）|
| **5th** | **594.9** | $\xi_{CW} > \xi_{CC}$ → $<$ | **Retail BAO ピーク直前** |
| 6th | 757.3 | $\xi_{CC} > \xi_{CW}$ → $<$ | BAO 崩壊後の回復 |
| 7th | 1827.9 | $\xi_{CW} < \xi_{CC}$ → $>$ | 大スケール反相関の収束 |

### 「Duopoly Debye Length」$r_D \approx 71.3$ km

$r < 71.3$ km では $\xi_{CC} > \xi_{CW}$：**同種クラスタリングが異種を上回る**（ブランド勢力圏内）。
$r > 71.3$ km では $\xi_{CW} \geq \xi_{CC}$：**両チェーンが同一人口密度場を追随**（都市間スケール）。

$r_D \approx 71.3$ km はオーストラリア主要都市の大都市圏半径（Sydney: ~80 km、Melbourne: ~70 km、Brisbane: ~70 km）と一致する。**ブランド勢力圏 = 都市圏のスケール**という解釈が成立する。

---

## 4. $r \approx 114$ km バンプの帰属

Coles 店舗ペアを地理的都市クラスター（半径 100 km）に割り当てて分析：

| 都市ペア | ペア数 | 割合 | 物理的距離 |
|---|---|---|---|
| Regional（Sydney 圏外）–Sydney | 1,944 | **37.1%** | Sydney–Newcastle: **116.7 km** |
| Brisbane–Regional | 847 | 16.2% | Brisbane–Sunshine Coast: 91.3 km |
| Melbourne–Regional | 712 | 13.6% | Melbourne–Ballarat: 100.7 km |
| Brisbane–Brisbane | 639 | 12.2% | Brisbane 郊外内 |
| Sydney–Sydney | 420 | 8.0% | Sydney 広域内 |

**主役: Sydney–Newcastle 軸（116.7 km）**。"Regional" に分類されたニューカッスル近郊店舗と Sydney 店舗のペアが全体の約 37% を占める。副役は Brisbane–Sunshine Coast（91.3 km）と Melbourne–Ballarat（100.7 km）。

$$r_{\rm bump, 114} \approx r_{\rm Sydney\text{-}Newcastle} = 116.7 \text{ km}$$

---

## 5. $r \approx 239$ km バンプの帰属

| 都市ペア | ペア数 | 割合 | 物理的距離 |
|---|---|---|---|
| Melbourne–Regional | 1,439 | **25.9%** | メルボルン圏外地域 |
| Canberra–Sydney | 1,409 | **25.3%** | **Sydney–Canberra: 246.7 km** |
| Regional–Sydney | 1,072 | 19.3% | — |
| Brisbane–Regional | 613 | 11.0% | — |

**主役: Sydney–Canberra 軸（246.7 km）**。Canberra は連邦首都であり Coles・Woolworths ともに 15 店舗以上を有する。Sydney との都市ペア距離 247 km が 239 km ビンに落ちる。

$$r_{\rm bump, 239} \approx r_{\rm Sydney\text{-}Canberra} = 246.7 \text{ km}$$

---

## 6. Retail BAO の構造：「都市間距離スペクトル」

現在確認された $\xi_{CC}$ のバンプ群を統合すると：

| バンプ # | $r_{\rm center}$ [km] | $\xi_{CC}$ | 帰属都市ペア | ビン確認距離 |
|---|---|---|---|---|
| 1 | 114 | 5.26 | Sydney–Newcastle | 116.7 km |
| 2 | 239 | 1.84 | Sydney–Canberra | 246.7 km |
| **BAO 主ピーク** | **666** | **3.23** | **Sydney–Melbourne** | **713.4 km** |
| BAO 第2 | 1386 | 0.49 | Melbourne–Brisbane | 1374.5 km |

主ピークが 666 km であって 713 km でない理由：ビン幅 $\Delta \ln r \approx 0.15$ のビン中心 666 km は $[618, 718]$ km をカバーし、Sydney–Melbourne（713 km）はビン上端近傍に入る。ペア密度が Melbourne–Adelaide（653 km）にも寄与するため、ビン重心が 666 km 側に引き寄せられる。

$\xi_{CW}$ も全バンプで対応するピークを持ち（例: BAO 主ピークで $\xi_{CW} = 3.07$）、両チェーンが同一の都市間距離スペクトルを共有している。

---

## 7. 主要発見のまとめ

### 7.1 全スケールで $\xi_{CW} > 0$：競合排除の不在

測定された全スケール（$r = 1$–$1858$ km）で $\xi_{CW} > 0$。Coles と Woolworths は**空間的排斥を示さない**。オーストラリア duopoly は「競合回避型」ではなく「**協調的共存（cooperative colocation）型**」と結論づけられる。

### 7.2 3スケール構造

$$\xi_{CW}(r) \text{ vs } \xi_{CC}(r):$$

| スケール | 条件 | 物理的解釈 |
|---|---|---|
| $r < 71.3$ km | $\xi_{CC} > \xi_{CW}$ | **ブランド勢力圏**（ロジスティクス・配送圏単位のクラスタリング）|
| $71.3 < r < 595$ km | $\xi_{CW} > \xi_{CC}$ | **人口密度追随**（両社が同一都市の人口集積を追う）|
| $r \approx 595$–$757$ km | $\xi_{CC} > \xi_{CW}$ | **Retail BAO**（Coles が Sydney–Melbourne 軸に Woolworths より集中）|

### 7.3 Retail BAO の統計的有意性（主ピーク）

$r = 666$ km: $\xi_{CC} - \xi_{CW} = 3.231 - 3.072 = 0.159$

$$S/N = \frac{0.159}{\sqrt{0.00464^2 + 0.00368^2}} = \frac{0.159}{0.0059} \approx 27\sigma$$

Coles の Sydney–Melbourne 軸への集中は Woolworths を **$27\sigma$** で有意に上回る（Poisson 誤差基準）。

---

## 8. 次のステップ

- [ ] $\xi_{WW}(r)$（Woolworths 自己相関）の計算：$\xi_{CC} > \xi_{WW}$ なら pure micro-partitioning、$\xi_{WW} \approx \xi_{CC}$ なら Woolworths の密度超過効果
- [ ] ABS SA2 人口密度加重ランダムカタログ：$\xi_{CW} > \xi_{CC}$（$r \approx 74$–$595$ km）が人口密度勾配の混入か純粋な共存かを分離
- [ ] ジャックナイフ誤差評価：Poisson 誤差 $27\sigma$ の真の有意性を確認
- [ ] 異方的相関関数 $\xi(r, \theta)$：Retail BAO ピークの方位角依存性（Sydney–Melbourne 軸の方向性）

---

## 9. Bias モデル実装・検証（追記）

### 9.1 幾何平均予測との比較

$\xi_{CW}^{\rm pred}(r) = \sqrt{\xi_{CC}(r) \cdot \xi_{WW}(r)}$ を全ビンで計算し残差を評価。

主要な発見：

| スケール | 残差 $\Delta\xi_{CW}$ | S/N | 解釈 |
|---|---|---|---|
| $r < 74$ km | $-1.4$ 〜 $+5.7$ | $\leq 2.5\sigma$ | bias モデルがほぼ成立 |
| $r \approx 153$–$575$ km | $-0.039$ 〜 $-0.004$ | $-5$ 〜 $-22\sigma$ | モデル下回る（弱い repulsive）|
| **$r = 666$ km (Retail BAO)** | **$-0.0036$** | **$-0.97\sigma$** | **モデルが完全一致** |

Retail BAO スケール（$r = 666$ km）での残差は $-0.97\sigma$——bias モデルが統計的に完璧に成立。
両社は Sydney–Melbourne 軸を「相互作用なしに独立して」トレースしている。

$r = 370$–$575$ km での弱い負残差（$S/N \sim -15$ 〜 $-22\sigma$）は、
この距離帯でわずかな repulsive interaction（互いを避ける傾向）が存在する可能性を示唆するが、
積分制約バイアスや人口密度非一様性の影響と切り分けが必要。

### 9.2 追加ノートブックセル（Cell 35–40）

- Cell 35 (md)：bias モデルの理論説明
- Cell 36：bias 計算ロジック（`BiasBin` data class）
- Cell 37：ξ_CW 実測 vs 幾何平均予測の log-log プロット
- Cell 38：残差 $\Delta\xi_{CW}(r)$ + $b_W/b_C(r)$ プロット（2画面）
- Cell 39：$b_W/b_C$ プロット表示
- Cell 40：`xi_bias_analysis.csv` 出力

### 9.3 docs 更新

`docs/06_au_oligopoly_analysis.md` §5 に以下を追記：
- 線形 bias モデルの数式定義
- スケール依存バイアス比の表
- Retail BAO でのモデル検証結果
- Duopoly Debye Length の定義
- 都市間距離スペクトルの帰属表

