# Toteutusdokumentti

## Rakenne

Ohjelma koostuu kurssia varten luodusta projektipohjasta, joka tarjoaa rungon shakkitekoälyn käyttöön xboard ja lichess 
sovelluksilla.

Tässä projektissa toteutettu shakkitekoäly koostuu pakkauksen `datastructureproject` luokista sekä pakkaukseen `chess.bot`
lisätystä luokasta TrainerBot. Pakkauksen `datastructureproject` luokkien sisällöt ovat seuraavat:
* Game: pelilautaolio, joka säilyttää pelin kannalta olennaiset tiedot eli nappuloiden sijainnit laudalla sekä tornitukseen ja 
ohestalyöntiin liittyvät tilanteet
* MoveSelector: työkalut pelilaudan arvon laskemiseen sekä minimax-algoritmin alpha-beta-pruningilla siirron valitsemiseen
* Piece: pelinappulaolio, jolla on tyyppi ja väri
* Type: enum, joka sisältää kaikki pelinappulatyypit suhteellisine arvoineen sekä tyhjän ruudun.
* PerformanceTest: suorituskykytesti

Lähteet:

https://www.chessprogramming.org/Evaluation

https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning

https://en.wikipedia.org/wiki/Minimax#Minimax_algorithm_with_alternate_moves
