# Toteutusdokumentti

## Rakenne

Ohjelma koostuu kurssia varten luodusta projektipohjasta, joka tarjoaa rungon shakkitekoälyn käyttöön `xboard` ja `lichess` 
-sovelluksilla.

Tässä projektissa toteutettu shakkitekoäly koostuu pakkauksen `datastructureproject` luokista sekä pakkaukseen `chess.bot`
lisätystä luokasta TrainerBot. Pakkauksen `datastructureproject` luokkien sisällöt ovat seuraavat:
* Game: Luokka sisältää metodit eri nappulatyyppien siirtoihin sekä säilyttää pelin kannalta olennaiset tiedot eli nappuloiden sijainnit laudalla sekä tornitukseen ja ohestalyöntiin liittyvät tilanteet. 
* MoveSelector: Luokka sisältää työkalut pelilaudan arvon laskemiseen sekä minimax-algoritmin alpha-beta-pruningilla siirron valitsemiseen.
* Piece: Pelinappula, jolla on tyyppi ja väri.
* Type: Enum, joka sisältää kaikki pelinappulatyypit suhteellisine arvoineen ja lyhenteineen sekä tyhjän ruudun.
* ChessboardList: Tietorakenne, joka sisältää toiminnot pelilaudan tallettamiseksi taulukkoon, järjestyksessä seuraavan pelilaudan haun sekä taulukon kasvattamisen, jos oletuskoko 40 pelilautaa ei riitä.
* MathUtils: Itse toteutetut min, max ja abs -toiminnot.
* PerformanceTest: Suorituskykytesti.

TrainerBot-luokka vastaa siitä, että siirtojen päivittäminen pelialustan ja Game-luokasta luodun peliolion välillä toimii. Luokka muuntaa Game-luokan pelilaudalla ilmaistuja siirtoja UCI:n (universal chess interface) mukaisiksi merkkijonoiksi sekä päivittää Game-luokan pelilautaa pelialustalta tulevien UCI-siirtojen mukaisiksi.

Luokkakaaviossa näkyy ainoastaan em. mainitut itse toteutetut luokat (ei projektipohjan luokkia).

![luokkakaavio](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/pics/classDiagramChess.jpg)

## Ohjelman aika- ja tilavaativuudet

Ohjelmassa oleva minimax-algoritmi perustuu alla olevaan pseudokoodiin (ohjelmassa ei ole erillistä perus-minimaxia ilman alpha-beta pruningia, mutta idea perustuu tähän):

```
function minimax(node, depth, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
        value := −∞
        for each child of node do
            value := max(value, minimax(child, depth − 1, FALSE))
        return value
    else (* minimizing player *)
        value := +∞
        for each child of node do
            value := min(value, minimax(child, depth − 1, TRUE))
        return value`

(* Initial call *)
minimax(origin, depth, TRUE)
```

Koodista nähdään, että perus-minimaxin aikavaativuus on O(b^d), missä b on kutakin pelitilannetta vastaava mahdollisten siirtojen määrä (branching factor) ja d on rekursion syvyys (depth).

Keskimääräinen shakkilaudan branching factor on 35, jolloin yhden yksikön lisääminen rekursion syvyyteen 35-kertaistaa laskenta ajan. [Suorituskykytestin](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/testausdokumentti.md) tulokset ovat suhteellisen hyvin linjassa oletetun aikavaativuuden kanssa: Jos ensimmäisen syvyyden laskentaan kului 6,42 s, tulisi toisen syvyyden laskentaan kulua 3 min 45 s (todellisuudessa 4 min 3 s) ja kolmannen syvyyden laskentaan 2 t 11 min (todellisuudessa 2 t 30 min). Toisaalta pelkkään nappuloiden arvon laskentaan perustuva minimax suorituu laskennasta oletettua nopeammin. Tämä selittynee ainakin osittain sillä, että pelkkiin nappuloiden arvoon perustuva laskenta tekee enemmän edestakaisia ja lyhyitä siirtoja, jolloin branching factor on oletettua pienempi.

Ohjelman alpha-beta-pruningia hyödyntävä minimax perustuu seuraavaan pseudokoodiin (ohjelmassa eriksee min ja max -metodit):

```
function alphabeta(node, depth, α, β, maximizingPlayer) is
    if depth = 0 or node is a terminal node then
        return the heuristic value of node
    if maximizingPlayer then
        value := −∞
        for each child of node do
            value := max(value, alphabeta(child, depth − 1, α, β, FALSE))
            α := max(α, value)
            if α ≥ β then
                break (* β cut-off *)
        return value
    else
        value := +∞
        for each child of node do
            value := min(value, alphabeta(child, depth − 1, α, β, TRUE))
            β := min(β, value)
            if β ≤ α then
                break (* α cut-off *)
        return value
        
(* Initial call *)
alphabeta(origin, depth, −∞, +∞, TRUE)
```

Pseudokoodista nähdään, että pahimman tapauksen aikavaativuus on edelleen O(b^d), joka tapahtuu tilanteessa, jossa pelilautatilanteet käydään jatkuvasti väärässä järjestyksessä läpi. Kuitenkin keskimääräinen aikavaativuus on [lähdemateriaalin](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) mukaan b/2^d eli branching factor ja samalla laskenta-aika puolittuisi. Testit osoittavat, että todellisuudessa ohjelman alpha-beta-pruning toimii tehokkaammin: Syvyydessä 2 pruning pienentää laskenta-ajan noin neljäsosaan perus-minimaxista ja syvyydessä 3 melkein kahdeksasosaan. Testeistä nähdään myös, että pelkkään nappuloiden arvon laskentaan perustuvaa minimaxia pruning parantaa huomattavasti vähemmän, mikä johtunee siitä, että siirtojen arvon vaihtelu on pienempää. 

Tilavaativuuksia ei ole erikseen testattu, mutta ne noudattelevat suunnilleen aikavaativuuksia, koska jokaista pelilautatilannetta varten luodaan mahdolliset seuraavat siirrot.

## Jatkokehitysaiheita

* tekoäly pelaa kohtuullisen hyvin ja pystyy tekemään shakkimatteja, mutta strategista puolta voisi lisätä. Nyt tekoäly valitsee vain kulloinkin parhaan siirron.
* vaikka pelilaudan arviointityökalu on oleellinen osa tekoäly, vaatii se melko paljon laskentaa, mikä hidastaa ohjelmaa. Laskentaa voisi nopeuttaa miettimällä kevyemmät metodit. Tämä oli kuitenkin ensimmäinen peleihin ja tekoälyyn liittyvä ohjelmointityöni ja kaiken kaikkiaan kolmas laajempi työ, joten halusin pitää asiat mahdollisimman yksinkertaisena.
* ohjelmassa on jonkin verran toisteista koodia ja pitkiä metodeita. Osa metodeista on mielestäni perustellusti pitkiä: esim. Trainer-botin metodien laajempi pilkkominen tekisi mielestäni koodista sekavampaa. Sen sijaan esim. tornin ja lähetin siirtojen koodin lyhentämistä voisi miettiä.

Lähteet:

https://en.wikipedia.org/wiki/Branching_factor

https://www.chessprogramming.org/Evaluation

https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning

https://en.wikipedia.org/wiki/Minimax#Minimax_algorithm_with_alternate_moves
