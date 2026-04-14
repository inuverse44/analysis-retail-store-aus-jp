# オーストラリア小売市場の寡占化メカニズム

ColesとWoolworths（通称：Duopoly）が、なぜオーストラリアにおいて圧倒的なシェア（計60-70%）を維持できているのかを、物理学的アナロジーと先行研究に基づき解析する。

## 1. 物理的制約：距離の暴虐 (Tyranny of Distance)
オーストラリアの地理的特徴は、寡占化を促進する「環境メトリック」として働いている。

- **高額な物流インフラ:** 大都市（パース、アデレード、メルボルン、シドニー、ブリスベン）間が数千km離れており、全土をカバーするコールドチェーン（低温物流網）の構築には天文学的なコストがかかる。
- **物流のJeans不安定性:** 
    - 初期段階で一定の「臨界質量（店舗数・資金力）」を超えたプレイヤーだけが、自社DC（配送センター）を維持できる。
    - 中小プレイヤーは、物流コストという「粘性」によって系の外へ追いやられ、巨大な「構造（Coles/Woolworths）」だけが生き残る。

## 2. 相空間の占有：土地銀行 (Land Banking)
リテール市場における「相空間」は、店舗立地そのものである。

- **立地制約:** オーストラリアの都市計画法では、大型商業施設の立地が厳しく制限されている。
- **先行者の優位性:** ColesとWoolworthsは、将来的な競合の出店を阻止するために、当面出店予定のない土地であっても「土地銀行」として確保し、相空間における「空領域」を意図的に消滅させてきた（ACCCによる指摘）。

## 3. 経済的重力：バイヤーパワー
質量の増大は、周囲のポテンシャルを歪め、さらなる質量（資本）を惹きつける。

- **サプライヤーへの圧力:** 圧倒的な販売網を人質に、サプライヤーから極限まで低い仕入れ価格を引き出す。これにより、消費者に「低価格」を提供し、さらに顧客を吸引するという正のフィードバック（重力増大）ループが完成する。
- **プライベートブランド (PB) 戦略:** 自社ブランド率を高めることで、サプライヤーのブランド（外部粒子）を排除し、系内部のエネルギー循環を完結させる。

## 4. 先行研究の参照
- **ACCC Supermarket Inquiry (2008, 2024):** 
    オーストラリア競争・消費者委員会による公式報告書。価格設定、土地銀行、サプライヤーとの不公正な取引について詳述されている。
- **"The Duopoly" 関連の研究:** 
    オーストラリアの経済学者たちは、この2社の存在が、ドイツのALDI（後発のディスカウンター）の参入によってどう変化したか（あるいはしなかったか）を盛んに研究している。

## 物理学的結論
AUリテール系は、初期条件としての「広大な距離」と「都市への人口集中」が、少数の巨大な「Retail Singularity（小売の特異点）」を形成することを必然づけている系であると言える。

---

## 5. 2点相関関数による空間的 duopoly 解析（実証結果）

Coles 685 店舗・Woolworths 1,039 店舗の OSM データに Landy-Szalay 推定量を適用して得られた
$\hat{\xi}_{CC}(r)$、$\hat{\xi}_{CW}(r)$、$\hat{\xi}_{WW}(r)$ の三者比較から、以下の空間構造が実証された。

### 5.1 線形 bias モデル

Coles 密度場 $\delta_C$ と Woolworths 密度場 $\delta_W$ が共通の「人口密度場」$\delta_m$ に対して
線形バイアスを持つと仮定する：

$$\delta_C(\mathbf{x}) = b_C \, \delta_m(\mathbf{x}), \quad \delta_W(\mathbf{x}) = b_W \, \delta_m(\mathbf{x})$$

このとき各相関関数は：

$$\xi_{CC}(r) = b_C^2 \, \xi_{mm}(r), \quad \xi_{WW}(r) = b_W^2 \, \xi_{mm}(r), \quad \xi_{CW}(r) = b_C b_W \, \xi_{mm}(r)$$

交差相関の幾何平均予測：

$$\xi_{CW}^{\rm pred}(r) = \sqrt{\xi_{CC}(r) \cdot \xi_{WW}(r)}$$

実測値との一致度を残差 $\Delta\xi_{CW}(r) \equiv \xi_{CW}(r) - \xi_{CW}^{\rm pred}(r)$ で評価する。

### 5.2 スケール依存バイアス比 $b_W(r)/b_C(r)$

$$\frac{b_W(r)}{b_C(r)} = \sqrt{\frac{\xi_{WW}(r)}{\xi_{CC}(r)}}$$

| スケール | $b_W/b_C$ | 物理的解釈 |
|---|---|---|
| $r < 71.3$ km（近隣） | $0.88$–$0.98$ | **Coles 優位**：郊外型密集クラスタリング |
| $r \approx 71$–$575$ km（都市間） | $1.04$–$1.35$ | **Woolworths 優位**：都市中心への高バイアス（Metro 戦略）|
| $r \approx 666$ km（Retail BAO） | $0.952$ | **Coles 優位**：Sydney–Melbourne 歴史軸への集中 |

### 5.3 Retail BAO でのモデル検証

$r = 666$ km での残差 $\Delta\xi_{CW} = -0.0036$（$S/N = -0.97$）：
**幾何平均モデルが Retail BAO スケールで完全に成立**。
両社は同一の都市間距離構造（Sydney–Melbourne 軸）を独立にトレースしており、
相互作用ではなく共通ポテンシャル場への追随が BAO シグナルの起源であることが確認される。

### 5.4 Duopoly Debye Length $r_D \approx 71.3$ km

$$r_D = \text{argzero}[\xi_{CC}(r) - \xi_{CW}(r)] \approx 71.3 \text{ km}$$

$r < r_D$：同種クラスタリング優位（ブランド勢力圏 = 大都市圏半径）
$r > r_D$：人口密度共同追随（duopoly が協調的に共存）

競合他社の存在が立地の正当性を相互に証明する「協調的共存（cooperative colocation）」構造が
全スケールで $\xi_{CW} > 0$ として確認される。

### 5.5 都市間距離スペクトル（Retail BAO 系列）

$\xi_{CC}(r)$ に検出された局所バンプの帰属：

| $r_{\rm peak}$ [km] | $\xi_{CC}$ | 帰属都市ペア | 実距離 [km] |
|---|---|---|---|
| 114 | 5.26 | Sydney–Newcastle | 116.7 |
| 239 | 1.84 | Sydney–Canberra | 246.7 |
| 666 | 3.23 | Sydney–Melbourne（+ Melbourne–Adelaide） | 713 / 653 |
| 1386 | 0.49 | Melbourne–Brisbane | 1374.5 |

植民地期（19世紀）の主要港湾都市設立間隔が現代の店舗分布 $\xi(r)$ に「化石」として刻印されている。

### 5.6 $\xi_{WW}$ との三者比較：スケール依存 bias 逆転

$\xi_{CC}$, $\xi_{CW}$, $\xi_{WW}$ の全スケール比較（`xi_all_wide.csv`, nD=685, nW=1039）から、
bias 比 $b_W/b_C = \sqrt{\xi_{WW}/\xi_{CC}}$ が3段階の符号反転を示すことが判明した。

| スケール | 順序 | $b_W/b_C$ | 物理的解釈 |
|---|---|---|---|
| $r < 71$ km | $\xi_{CC} > \xi_{CW} > \xi_{WW}$ | $< 1$ | Coles の tight micro-clustering（郊外ハブ型配置）|
| $74 < r < 600$ km | $\xi_{WW} > \xi_{CW} > \xi_{CC}$ | $> 1$ | Woolworths Metro 戦略（都市圏スケール高バイアス）|
| $r \approx 666$ km | $\xi_{CC} > \xi_{CW} > \xi_{WW}$ | $0.952$ | Coles の Sydney–Melbourne 歴史軸優位（**再反転**）|

これは銀河の「色依存 bias（color-dependent bias）」と構造的に同じ現象である。赤色銀河が
density field のピーク（filament）に集中するように、Coles が植民地期に確立された歴史軸に
Woolworths より強くバイアスされている。

#### 局所バンプの chain 非対称性

$r = 114.8$ km（Sydney–Newcastle 軸）：

- $r = 99$ km：$\xi_{WW}(5.808) > \xi_{CC}(5.293)$（Woolworths 優位）
- $r = 114.8$ km：$\xi_{CC}(5.259) > \xi_{WW}(5.102)$（**Coles 固有バンプ**）
- $r = 133$ km：$\xi_{WW}(4.145) > \xi_{CC}(3.611)$（Woolworths 優位に復帰）

Woolworths にはない Coles 固有の 115 km バンプ——Newcastle 圏への Coles 特異集中の証拠。

$r = 238.8$ km（Sydney–Canberra 軸）：$\xi_{WW}(2.011) > \xi_{CW}(1.889) > \xi_{CC}(1.840)$

Canberra で Woolworths が Coles を上回る非対称な出店密度が原因（仮説 C：Cell 47–50 で検証）。

#### BAO 非対称の起源仮説

$r = 666$ km で $\xi_{CC} > \xi_{WW}$ となる理由：

- Coles は Victoria 州発祥（1914年メルボルン創業）→ Melbourne 圏で Coles 相対優位
- Woolworths は NSW 発祥（1924年シドニー創業）→ Sydney 圏で Woolworths 相対優位
- 666 km ペアは「Sydney 側 × Melbourne 側」の積で決まる → $n_{\rm Mel,C} > n_{\rm Mel,W}$ が $\xi_{CC} > \xi_{WW}$ を駆動

**検証**（`coles_correlation.ipynb` Cell 47–50）：Melbourne・Sydney・Canberra・Newcastle の
都市別 Coles/Woolworths 比を計算し、上記仮説を数値的に支持または否定する。

### 5.7 ジャックナイフ誤差評価：真の統計的有意性

空間ジャックナイフ（$K = 8$ 経度スライス）の実行結果（`xi_jackknife.csv`）：

$$\sigma_{\rm Poisson}(666\,\rm km) = 0.00464 \quad \Rightarrow \quad \sigma_{\rm JK}(666\,\rm km) = 1.312 \quad \left(\frac{\sigma_{\rm JK}}{\sigma_{\rm Poisson}} = 283\times\right)$$

$$\boxed{S/N_{\rm JK}^{\rm BAO} = \frac{3.231}{1.312} = 2.46\sigma}$$

#### $S/N_{\rm JK}$ スペクトル：「物理スケール検出器」

| $r$ [km] | $\xi_{CC}$ | $\sigma_{\rm JK}$ | $S/N_{\rm JK}$ | 物理スケール |
|---|---|---|---|---|
| $< 30$ | 79–264 | 73–236 | $1.0$–$1.3$ | 都市固有（非普遍）|
| $73.9$ | 11.22 | 5.16 | $2.17$ | Duopoly Debye Length |
| $85.6$ | 7.75 | 3.62 | $2.14$ | 同右翼 |
| $238.8$ | 1.84 | 0.930 | $1.98$ | Sydney–Canberra 軸 |
| $\mathbf{666.3}$ | $\mathbf{3.231}$ | $\mathbf{1.312}$ | $\mathbf{2.46}$ | **Retail BAO — 全スケール最高** |
| $320$・$497$・$893$ | 0.24–0.63 | 0.36–0.74 | $< 1$ | 検出不能 void |

BAO が全スケール中で最も統計的に有意な feature であることが確認された。

#### 2dFGRS BAO との比較

$$S/N_{\rm Retail\,BAO}^{\rm JK} = 2.46\sigma \approx S/N_{\rm 2dFGRS\,BAO} = 2.4\sigma \quad \text{(Cole et al. 2005)}$$

Retail BAO の統計的有意性は、宇宙論的 BAO の歴史的初検出（2dFGRS, 2005年）と同水準に達した。

#### 小スケール clustering の非普遍性

$r < 30$ km の $S/N_{\rm JK} \approx 1.1$ は、tight clustering が都市固有現象であることを示す。
Perth（低密度広域分散）と Sydney（高密度街区集中）のパッチを除外すると
$\hat{\xi}_k$ が $\xi$ と同程度変動する——すなわち**普遍的な小スケール出店戦略は存在しない**。

$S/N_{\rm JK}$ を「物理スケール検出器」として読むと：
- $r_D \approx 74$ km（Debye Length）と $r_{\rm BAO} \approx 666$ km だけが全都市で普遍的に成立する物差し
- 中間 void（$r = 320$–$500$ km）は K=8 パッチ設計のアーティファクトの可能性あり（要検証）
