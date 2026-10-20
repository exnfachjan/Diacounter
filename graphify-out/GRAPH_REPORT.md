# Graph Report - Diacounter  (2026-10-20)

## Corpus Check
- 21 files · ~4,127 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .properties 1, .bat 1)

## Summary
- 173 nodes · 317 edges · 14 communities (2 shown, 12 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 3 edges (avg confidence: 0.92)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `a16638dc`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- CounterMenu.java
- MultiPlayerGameModeMixin.java
- CounterConfig
- DiacounterClient.java
- CounterMenu
- .get
- CLAUDE.md
- .claude/CLAUDE.md
- Lang.java
- PlacedOres.java
- BossHealthOverlayMixin.java

## God Nodes (most connected - your core abstractions)
1. `CounterMenu` - 11 edges
2. `PlacedOres` - 11 edges
3. `CounterConfig` - 10 edges
4. `DiacounterClient` - 10 edges
5. `LocalAccess` - 9 edges
6. `Lang` - 8 edges
7. `Language` - 8 edges
8. `CounterHud` - 7 edges
9. `MultiPlayerGameModeMixin` - 6 edges
10. `BossHealthOverlayMixin` - 5 edges

## Surprising Connections (you probably didn't know these)
- `Aufbau` --references--> `DiacounterClient`  [INFERRED]
  README.md → src/client/java/de/jan/diacounter/client/DiacounterClient.java
- `Aufbau` --references--> `CounterConfig`  [INFERRED]
  README.md → src/client/java/de/jan/diacounter/client/CounterConfig.java
- `CounterConfig` --references--> `Language`  [EXTRACTED]
  src/client/java/de/jan/diacounter/client/CounterConfig.java → src/client/java/de/jan/diacounter/client/Lang.java

## Import Cycles
- None detected.

## Communities (14 total, 12 thin omitted)

### Community 2 - "CounterConfig"
Cohesion: 0.12
Nodes (11): Aufbau, Diacounter, Entwickeln, Funktionen, Lizenz, Voraussetzungen, CounterConfig, Mode (+3 more)

### Community 11 - "Lang.java"
Cohesion: 0.17
Nodes (5): Lang, Language, AUTO, DE, EN

## Knowledge Gaps
- **6 isolated node(s):** `HOTBAR`, `BOSSBAR`, `OFF`, `AUTO`, `DE` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 61 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **12 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CounterConfig` connect `CounterConfig` to `Lang.java`, `PlacedOres.java`, `.get`?**
  _High betweenness centrality (0.143) - this node is a cross-community bridge._
- **What connects `HOTBAR`, `BOSSBAR`, `OFF` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `CounterMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.0896551724137931 - nodes in this community are weakly interconnected._
- **Why does `DiacounterClient` connect `DiacounterClient.java` to `MultiPlayerGameModeMixin.java`, `CounterConfig`?**
  _High betweenness centrality (0.086) - this node is a cross-community bridge._
- **Should `CounterConfig` be split into smaller, more focused modules?**
  _Cohesion score 0.12418300653594772 - nodes in this community are weakly interconnected._
- **Why does `Aufbau` connect `CounterConfig` to `DiacounterClient.java`?**
  _High betweenness centrality (0.080) - this node is a cross-community bridge._