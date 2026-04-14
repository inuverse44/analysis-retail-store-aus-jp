# 研究ログ：2026-04-10

## 1. 本日の作業概要

2026-04-08 の次のステップとして、**空間ジャックナイフ誤差評価**を実装した。

- `coles_correlation.ipynb` に Cell 41–46 を追加
- `docs/04_spatial_analysis_methods.md` §1.1 にジャックナイフ推定量の数学的形式化を追記
- 実行は次セッション（ノートブックを順次実行して `output/xi_jackknife.csv` を生成）

---

## 2. 実装：空間ジャックナイフ（Cell 41–46）

### 2.1 動機

2026-04-08 での Retail BAO の有意性評価（$S/N \approx 27\sigma$）は Poisson 誤差に基づく下限値であった。
クラスター点過程の真の分散には 4 点相関関数 $\zeta$ の寄与が加わり、Poisson 誤差は系統的に小さすぎる。
特に Retail BAO スケール（$r \approx 666$ km）では、シグナルの大部分が Sydney–Melbourne ペアに由来するため、
1–2 パッチの除外でも $\hat{\xi}$ が大きく揺らぐ——これが宇宙分散の本質である。

### 2.2 パッチ設計

- $K = 8$ 経度スライス：$113°$–$154°$ を等分割（$\Delta\mathrm{lon} \approx 5.125°$/パッチ）
- 各パッチ $k$ に対して：データ点 AND ランダムカタログの双方からパッチ内点を除外
- DD, DR, RR を leave-one-out セットで再計算、Landy-Szalay 推定量 $\hat{\xi}_k(r)$ を算出

### 2.3 ジャックナイフ $\sigma$ の公式

$$\sigma_{\rm JK}^2(r) = \frac{K-1}{K} \sum_{k=0}^{K-1} \left[ \hat{\xi}_k(r) - \bar{\xi}_{\rm JK}(r) \right]^2$$

### 2.4 実装セル構成

| Cell | 内容 |
|---|---|
| 41 (md) | ジャックナイフの理論説明 |
| 42 | パッチ割り当て（`lonToPatch`）+ 分布確認 |
| 43 | leave-one-out ループ + $\sigma_{\rm JK}$ 計算 + 比較表印刷 |
| 44 | ξ_CC エラーバー比較プロット（Poisson vs Jackknife）|
| 45 | $\sigma_{\rm JK}/\sigma_{\rm Poisson}$ 比率プロット |
| 46 | `output/xi_jackknife.csv` 出力 + Retail BAO 要約 |

---

## 3. 予測される結果

### 3.1 スケール依存誤差比

| スケール | $\sigma_{\rm JK}/\sigma_{\rm Poisson}$ の予測 | 物理的解釈 |
|---|---|---|
| $r < 30$ km | $\approx 1$ | ショットノイズ支配 |
| $30$–$200$ km | $1$–$5\times$ | 都市内クラスタリング分散 |
| $r \approx 666$ km（BAO 主ピーク） | $\gg 1$（$10$–$100\times$ の可能性） | Sydney–Melbourne ペアが 1–2 パッチに集中 |
| $r > 1000$ km | $2$–$10\times$ | 積分制約 + 大スケール分散 |

### 3.2 Retail BAO の真の有意性

Poisson $S/N = 27\sigma$ から $\sigma_{\rm JK}$ に基づく真の $S/N$ に修正することで、
Retail BAO シグナルの統計的頑健性を評価できる。

$S/N_{\rm JK} = \xi_{CC}(666\,\mathrm{km}) / \sigma_{\rm JK}(666\,\mathrm{km})$

予測：$S/N_{\rm JK} \sim 2$–$5\sigma$（実行後に確認）。

シグナルが消えるわけではなく、「1 調査領域からは宇宙分散を平均化できない」という
銀河 BAO 解析と同じ原理的限界を反映する。

---

## 4. docs 更新

`docs/04_spatial_analysis_methods.md` §1.1「分散の過小評価」に以下を追記：
- 空間ジャックナイフ推定量の数式
- 実装詳細（$K=8$, 経度スライス）
- スケール依存誤差比の予測表
- BAO スケールでの宇宙分散の物理的解釈

---

## 5. 次のステップ

- [ ] `coles_correlation.ipynb` Cell 41–46 を実行し `xi_jackknife.csv` を生成
- [ ] $\sigma_{\rm JK}(r)$ の実測値で Retail BAO の真の $S/N$ を確定
- [ ] ABS SA2 人口密度加重ランダムカタログ（人口密度追随 vs 出店戦略の分離）
- [ ] 異方的相関関数 $\xi(r, \theta)$（BAO ピークの方位角依存性）
- [ ] 積分制約バイアス $C_{\rm IC}$ の数値積分評価

---

## 追記：3エージェント討論 + 次のアクション実施

### 討論結果（`xi_all_wide.csv` 三者比較）

#### 発見 1：bias 比 $b_W/b_C$ の3段階反転

| スケール | 順序 | $b_W/b_C$ |
|---|---|---|
| $r < 71$ km | $\xi_{CC} > \xi_{WW}$ | $< 1$ |
| $74$–$600$ km | $\xi_{WW} > \xi_{CC}$ | $> 1$ |
| $r \approx 666$ km | $\xi_{CC} > \xi_{WW}$ | $0.952$ |

#### 発見 2：chain 固有バンプの非対称性

- $r = 114.8$ km：Coles 固有バンプ（Woolworths には不在）→ Newcastle 圏への Coles 特異集中仮説
- $r = 238.8$ km：$\xi_{WW}$ 最大 → Canberra での Woolworths 優位仮説

#### 発見 3：BAO 非対称の起源

Coles Melbourne 発祥（1914年）× Woolworths Sydney 発祥（1924年）による
非対称な都市間 bias が $\xi_{CC} > \xi_{WW}$ at 666 km を駆動するという仮説を定式化。

#### 発見 4：中間スケールの系統的負残差

$\xi_{CW} < \sqrt{\xi_{CC} \cdot \xi_{WW}}$ が $r \approx 100$–$600$ km で系統的に成立。
人口密度勾配混入 vs 真の弱い repulsive interaction の切り分けは ABS SA2 カタログ待ち。

### 実装内容

- `coles_correlation.ipynb` Cell 47–50 追加：都市別 Coles/Woolworths 比較
  - Cell 47 (md)：仮説定義
  - Cell 48：都市バウンディングボックス定義 + 店舗数カウント
  - Cell 49：C/W 比バープロット
  - Cell 50：仮説 A/B/C の検証サマリー
- `docs/06_au_oligopoly_analysis.md` §5.6–5.7 追記（bias 逆転・BAO 非対称・ジャックナイフ予測）

### 更新後の次のステップ

- [ ] Cell 41–50 を実行（ジャックナイフ + 都市別比較の結果取得）
- [ ] Melbourne/Sydney/Canberra/Newcastle の C/W 比で仮説 A/B/C を検証
- [ ] $\sigma_{\rm JK}$ 実測値で Retail BAO の真の $S/N$ を確定
- [ ] ABS SA2 加重ランダムカタログ
- [ ] 異方的相関関数 $\xi(r, \theta)$
