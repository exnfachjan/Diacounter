# Diacounter

Clientseitige Fabric-Mod für Minecraft **26.3**, die abgebautes Diamanterz (normal und Deepslate) zählt.

Die Mod muss nur auf dem Client installiert sein und funktioniert deshalb auch auf Servern, auf denen nichts installiert ist.

## Funktionen

- Zählt jedes Diamanterz (`diamond_ore` und `deepslate_diamond_ore`), das du selbst abbaust.
- Anzeige über der Hotbar (mit Erz-Symbol) oder als Bossbar. Die Bossbar ist bei 100, 200, 300 usw. voll und beginnt danach neu.
- Alle Einstellungen im Menü `/counter` (Dialog-Bildschirm von Minecraft):
  - Anzeige: Hotbar, Bossbar oder aus
  - Symbol an/aus (Hotbar und Bossbar)
  - Höhe der Hotbar-Anzeige
  - Bossbar-Farbe
  - Sprache: Spielsprache, Deutsch oder English (unabhängig von der Spielsprache)
  - Zähler zurücksetzen
- Der Zählerstand zählt nur, was du abbaust, während die Mod installiert ist. Er gilt global und nicht pro Welt oder Server.

Gespeichert wird in `config/diacounter.json`.

## Voraussetzungen

- Minecraft 26.3
- [Fabric Loader](https://fabricmc.net/use/) 0.19.5 oder neuer
- [Fabric API](https://modrinth.com/mod/fabric-api)
- Java 25

## Entwickeln

```bash
./gradlew runClient   # Minecraft mit der Mod starten
./gradlew build       # Jar nach build/libs/ bauen
./gradlew vscode      # VSCode-Startkonfiguration erzeugen
```

In VSCode startest du danach über „Run and Debug“ → „Minecraft Client“.

### Aufbau

Quellcode: `src/client/java/de/jan/diacounter/client/`

| Datei | Aufgabe |
| --- | --- |
| `DiacounterClient` | Einstiegspunkt, Befehl `/counter`, HUD-Registrierung |
| `CounterMenu` | Einstellungsmenü als lokal gebauter Dialog |
| `CounterHud` | Anzeige über der Hotbar |
| `BossBarSync` | Bossbar im Boss-Overlay |
| `CounterConfig` | Einstellungen und Zählerstand (JSON) |
| `Lang` | Eigene Übersetzungen, unabhängig von der Spielsprache |
| `mixin/MultiPlayerGameModeMixin` | Erkennt abgebautes Diamanterz |
| `mixin/BossHealthOverlayMixin` | Hängt die Bossbar ins Overlay |

Übersetzungen: `src/main/resources/assets/diacounter/lang/`

## Lizenz

CC0-1.0
