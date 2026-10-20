# Graph Report - Diacounter  (2026-10-20)

## Corpus Check
- 18 files · ~3,525 words
- Verdict: corpus is large enough that graph structure adds value.
- Unclassified: 6 file(s) not represented in the graph (top: (none) 4, .properties 1, .bat 1)

## Summary
- 147 nodes · 253 edges · 12 communities (3 shown, 9 thin omitted)
- Extraction: 100% EXTRACTED · 0% INFERRED · 0% AMBIGUOUS · INFERRED: 1 edges (avg confidence: 0.85)
- Token cost: 0 input · 0 output

## Community Hubs (Navigation)
- BossHealthOverlayMixin.java
- CounterConfig.java
- DiacounterClient.java
- CounterMenu
- .get
- CLAUDE.md
- .claude/CLAUDE.md
- Lang.java

## God Nodes (most connected - your core abstractions)
1. `CounterMenu` - 12 edges
2. `CounterConfig` - 9 edges
3. `LocalAccess` - 9 edges
4. `DiacounterClient` - 8 edges
5. `Lang` - 8 edges
6. `Language` - 8 edges
7. `CounterHud` - 7 edges
8. `MultiPlayerGameModeMixin` - 6 edges
9. `BossHealthOverlayMixin` - 5 edges
10. `BossBarSync` - 4 edges

## Surprising Connections (you probably didn't know these)
- `CounterConfig` --references--> `Language`  [EXTRACTED]
  src/client/java/de/jan/diacounter/client/CounterConfig.java → src/client/java/de/jan/diacounter/client/Lang.java

## Import Cycles
- None detected.

## Communities (12 total, 9 thin omitted)

### Community 1 - "BossHealthOverlayMixin.java"
Cohesion: 0.14
Nodes (3): BossBarSync, BossHealthOverlayMixin, MultiPlayerGameModeMixin

### Community 2 - "CounterConfig.java"
Cohesion: 0.14
Nodes (5): CounterConfig, Mode, BOSSBAR, HOTBAR, OFF

### Community 11 - "Lang.java"
Cohesion: 0.16
Nodes (5): Lang, Language, AUTO, DE, EN

## Knowledge Gaps
- **6 isolated node(s):** `HOTBAR`, `BOSSBAR`, `OFF`, `AUTO`, `DE` (+1 more)
  These have ≤1 connection - possible missing edges or undocumented components. (Counts symbols only; 54 node(s) total have ≤1 connection when file, concept and rationale nodes are included.)
- **9 thin communities (<3 nodes) omitted from report** — run `graphify query` to explore isolated nodes.

## Suggested Questions
_Questions this graph is uniquely positioned to answer:_

- **Why does `CounterConfig` connect `CounterConfig.java` to `Lang.java`, `.get`?**
  _High betweenness centrality (0.125) - this node is a cross-community bridge._
- **What connects `HOTBAR`, `BOSSBAR`, `OFF` to the rest of the system?**
  _6 weakly-connected nodes found - possible documentation gaps or missing edges._
- **Should `CounterMenu.java` be split into smaller, more focused modules?**
  _Cohesion score 0.07881773399014778 - nodes in this community are weakly interconnected._
- **Why does `CounterMenu` connect `CounterMenu` to `CounterMenu.java`?**
  _High betweenness centrality (0.058) - this node is a cross-community bridge._
- **Should `BossHealthOverlayMixin.java` be split into smaller, more focused modules?**
  _Cohesion score 0.14245014245014245 - nodes in this community are weakly interconnected._
- **Why does `Language` connect `Lang.java` to `CounterConfig.java`?**
  _High betweenness centrality (0.054) - this node is a cross-community bridge._
- **Should `CounterConfig.java` be split into smaller, more focused modules?**
  _Cohesion score 0.13725490196078433 - nodes in this community are weakly interconnected._