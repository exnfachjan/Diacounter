# Graph Report - Diacounter  (2026-10-20)

## Corpus Check
- 22 files · ~4,727 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .properties 1, .bat 1)

## Summary
- 194 nodes · 356 edges · 12 communities (4 shown, 8 thin omitted)
- Extraction: 99% EXTRACTED · 1% INFERRED · 0% AMBIGUOUS · INFERRED: 3 edges (avg confidence: 0.92)
- Token cost: 0 input · 0 output

## Graph Freshness
- Built from commit: `d5ef8bfa`
- Run `git rev-parse HEAD` and compare to check if the graph is stale.
- Run `graphify update .` after code changes (no API cost).

## Community Hubs (Navigation)
- CounterMenu.java
- MultiPlayerGameModeMixin.java
- CounterConfig
- DiacounterClient.java
- PlacedOres.java
- .get
- CLAUDE.md
- .claude/CLAUDE.md
- Lang.java

## God Nodes (most connected - your core abstractions)
1. `CounterTimer` - 12 edges
2. `CounterMenu` - 11 edges
3. `PlacedOres` - 11 edges
4. `CounterConfig` - 10 edges
5. `DiacounterClient` - 10 edges
6. `LocalAccess` - 9 edges
7. `Lang` - 8 edges
8. `Language` - 8 edges
9. `CounterHud` - 7 edges
10. `State` - 6 edges

## Surprising Connections (you probably didn't know these)
- `Aufbau` --references--> `DiacounterClient`  [INFERRED]
  README.md → src/client/java/de/jan/diacounter/client/DiacounterClient.java
- `Aufbau` --references--> `CounterConfig`  [INFERRED]
  README.md → src/client/java/de/jan/diacounter/client/CounterConfig.java
- `CounterConfig` --references--> `Language`  [EXTRACTED]
  src/client/java/de/jan/diacounter/client/CounterConfig.java → src/client/java/de/jan/diacounter/client/Lang.java

## Import Cycles
- None detected.

## Communities (12 total, 8 thin omitted)

### Community 1 - "MultiPlayerGameModeMixin.java"
Cohesion: 0.12
Nodes (4): BossBarSync, BlockItemMixin, BossHealthOverlayMixin, MultiPlayerGameModeMixin

### Community 2 - "CounterConfig"
Cohesion: 0.13
Nodes (12): Aufbau, Diacounter, Entwickeln, Funktionen, Lizenz, Timer, Voraussetzungen, CounterConfig (+4 more)

### Community 5 - ".get"
Cohesion: 0.09
Nodes (7): CounterHud, CounterTimer, State, FINISHED, IDLE, PAUSED, RUNNING

### Community 11 - "Lang.java"
Cohesion: 0.17
Nodes (5): Lang, Language, AUTO, DE, EN

## Knowledge Gaps
- **10 isolated node(s):** `HOTBAR`, `BOSSBAR`, `OFF`, `IDLE`, `RUNNING` (+5 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 70 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **8 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CounterConfig` connect `CounterConfig` to `Lang.java`, `PlacedOres.java`, `.get`?**
  _High betweenness centrality (0.129) - this node is a cross-community bridge._
- **What connects `HOTBAR`, `BOSSBAR`, `OFF` to the rest of the system?**
  _10 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `CounterMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07610993657505286 - nodes in this community are weakly interconnected._
- **Why does `DiacounterClient` connect `DiacounterClient.java` to `MultiPlayerGameModeMixin.java`, `CounterConfig`, `.get`?**
  _High betweenness centrality (0.100) - this node is a cross-community bridge._
- **Should `MultiPlayerGameModeMixin.java` be split into smaller, more focused modules?**
  _Cohesion score 0.12258064516129032 - nodes in this community are weakly interconnected._
- **Why does `Aufbau` connect `CounterConfig` to `DiacounterClient.java`?**
  _High betweenness centrality (0.079) - this node is a cross-community bridge._
- **Should `CounterConfig` be split into smaller, more focused modules?**
  _Cohesion score 0.13333333333333333 - nodes in this community are weakly interconnected._