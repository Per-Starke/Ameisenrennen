# All Pairs Shortest Path with Ants

## Annahmen

* Spielfelder sind stets rechteckig
* Bewegungen (Feld-zu-Feld) sind in alle 8 Moore-Richtungen möglich

## Grundideen

* Das Spielfeld `field` wird für die Zugriffe durch die einzelnen (Ameisen-) Threads synchronisiert, d.h. die notwendigen Methoden (mindestens `update`) werden mit `synchronized` versehen.


