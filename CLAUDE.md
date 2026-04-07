# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **retail physics analysis research project** comparing Australian (Coles, Woolworths) and Japanese (TRIAL) retail markets through the lens of statistical mechanics and fluid dynamics. The project owner holds a PhD in cosmology and frames retail phenomena using physical analogies (gravitational potential, particle density, viscosity, order parameter $k$, etc.).

## Tech Stack

- **Language:** Kotlin
- **Environment:** Kotlin Notebook (`.ipynb` files only — `.kts` files are prohibited)
- **Libraries:** `kotlinx-dataframe`, `lets-plot`, `lets-plot-gt` (GeoTools)
- **Tool versions:** managed via `mise.toml` (Java OpenJDK 21, Kotlin 2.2, Gradle 9.1)
- **Data sources:**
  - AU: OpenStreetMap via Overpass API
  - JP: TRIAL internal data

## Running Notebooks

```bash
# Install tool versions
mise install

# Notebooks are run interactively in a Kotlin Notebook environment
# Open .ipynb files in a Kotlin-capable Jupyter environment
```

## Repository Structure

- **`docs/`** — Theory, hypotheses, and analysis status (numbered by category):
  - `00-09`: Background, objectives, theoretical framework
  - `10-19`: Data observation and cleaning status
  - `20-29`: Analysis methods and scaling laws
  - `30-39`: Validation results and comparison reports
- **`notebook/`** — Experimental computation and visualization (`.ipynb`)
  - `memo/` — scratch notebooks
  - `output/` — generated plots and CSVs
- **`refs/`** — Academic reference papers

## Research Methodology

The core methods used in this project:
1. **Two-point correlation function $\xi(r)$** — cluster analysis of store locations
2. **Voronoi Tessellation** — effective phase-space area calculation per store
3. **Gamma distribution fitting** — quantifying order parameter $k$ for JP vs AU comparison
4. **Huff Model** — gravitational trade area model, treating stores as mass sources and consumers as test particles

## Notebook Execution Order

Notebooks have data dependencies:
1. `coles_location.ipynb` — fetches from Overpass API, writes `notebook/output/coles_locations.csv`
2. `coles_correlation.ipynb` — reads `coles_locations.csv`, computes $\xi(r)$
3. `coles_visualization.ipynb` — reads the same CSV for spatial plots

## Known Library Constraints

- **`lets-plot` color strings:** Only hex codes (`"#4682B4"`) are accepted. CSS named colors (`"steelblue"`, `"gray"`) throw a `RuntimeException` at render time.
- **`kotlinx-dataframe` CSV read:** `DataFrame.readCSV(path)` resolves paths relative to the notebook file's location.

## Conventions

- Analysis logic lives entirely in `.ipynb` notebooks. Do not extract logic into `.kts` scripts.
- Successful notebook logic should be promoted to `docs/` as mathematical formulations.
- Research sessions are logged in `docs/history/YYYY-MM-DD_research_log.md`.
- Use physical language: prefer "potential well depth" over "customer concentration", "stress release" over "market disruption", etc.
- Explanations should be at PhD level — full mathematical formalism is welcome.

## Analysis Design Conventions

- **Distance metric:** Haversine (great-circle) distance in km for all spatial calculations.
- **Correlation function bins:** Log-spaced. Set `rMin = meanNN / 2` (half the mean nearest-neighbor distance computed from data) and `nBins = ⌈ln(rMax/rMin) / 0.15⌉` to achieve $\Delta \ln r \approx 0.15$ per bin.
- **Random catalog:** Currently uniform Poisson sampling within Australia's bounding box (`lat: -44…-10`, `lon: 113…154`) with $N_R = 10 N_D$. Population-density-weighted sampling (ABS SA2 grid) is the planned next step.
