# 研究ログ：2026-03-26

## 1. 議論の目的
オーストラリア（Coles/Woolworths）と日本（TRIAL）の比較解析を通じ、地理的・物理的制約（メトリック）から小売の普遍的な法則を再発見する。

## 2. 理論的進展 (Theoretical Progress)
初期宇宙論のアナロジーを用い、以下の概念をリテール解析に導入した。

- **平均自由行程 $\lambda$ と情報の粘性 $\eta$:**
    配送距離 $L$ が情報の書き換え（SKU入れ替え）に対する抵抗として働く。AU系は $L$ が長く、JP系は短いため、系の反応時間 $\tau$ に決定的な差が生じる。
- **ボロノイ秩序指数 $k$:**
    店舗分布を核（シード）としたテッセレーションにより、商圏の面積分布 $P(V)$ を算出。ガンマ分布 $f(V; k)$ へのフィッティングにより、市場の「結晶度（計画性）」を定量化する手法を確立。
- **需要応力とひび割れ:**
    出店を市場の需要ストレスに対する「応力解放（ひび割れ）」と見なし、破壊力学的な視点での成長モデルを構想。

## 3. 観測進捗 (Observational Progress)
- **データ取得:** Kotlin Notebook を用い、Overpass API から Coles の位置座標 685 件を取得。
- **品質評価:** 公式レポートの 860 件に対し約 80% の捕捉。主ポテンシャルの記述には十分な統計量と判断。
- **可視化:** `lets-plot` を用いたオーストラリア全土の店舗プロットを完了。

## 4. 経済物理学的背景 (Economic Physics)
AU市場の寡占（Duopoly）について、以下の要因を物理的に解釈。
- **距離の暴虐:** 物流構築の臨界質量を超えた者のみが生き残る Jeans 不安定性。
- **土地銀行:** 物理的な立地空間（相空間）の先行占有による競合粒子の排除。

## 5. 主要参考文献 (Bibliography)
- **Richards et al. (2012)**: 市場支配力とサプライヤー関係。 [DOI: 10.1002/agr.21287]
- **Smith & Wright (2010)**: 利益率と集中度の相関。 [DOI: 10.1111/j.1467-8454.2010.00391.x]
- **Beare & Szakiel (2009)**: ボロノイ多角形を用いた空間競争モデル。 [MODSIM09]
- **Pritchard (2000)**: 地理的再編と店舗フォーマット。 [DOI: 10.1080/00049180050001335]

## 6. 作成済みドキュメント
- `docs/00_background_and_objective.md`
- `docs/01_hypotheses_sku_dynamics.md`
- `docs/02_theoretical_framework.md`
- `docs/03_data_observation_status.md`
- `docs/04_spatial_analysis_methods.md`
- `docs/05_tessellation_and_scaling.md`
- `docs/06_au_oligopoly_analysis.md`
- `GEMINI.md`
