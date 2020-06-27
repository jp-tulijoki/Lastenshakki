# Testausdokumentti

## Yksikkö -ja integraatiotestaus

Ohjelman metodeita on testattu JUnitilla tehdyillä yksikkö- ja integraatiotesteillä. Testeissä on pyritty käymään läpi esimerkinomaisesti kaikki siirrot ja pelitilanteet. Alla kuvaukset eri luokkien JUnit-testeistä. 

### Game-luokka

Game-luokan testit koostuvat lähtökohtaisesti eri pelinappuloiden siirroista. Jokainen siirtotyyppi on testattu. Nappuloilla, joilla on useita siirtomahdollisuuksia, on asetettu kriteeriksi mahdollisten siirtojen oikea määrä, eikä jokaista generoitua pelilauta-asetelmaa ole testattu erikseen.

### MoveSelector-luokka

MoveSelector-luokan testit testaavat pelilaudan arviointityökalua sekä siirron valinta -metodia. Siirronvalintatestit on suoritettu normaalilla minimax-algoritmilla, ts. sellaisella, joka ei anna tasoitusta. Siirron valinnassa on testattu, että metodit palauttavat laillisia siirtoja eli eivät esim. anna vastustajalle mahdollisuutta syödä kuningasta seuraavalla siirrolla. Lisäksi on testattu ns. ongelmasiirtoja, jotka ovat jossain vaiheessa aiheuttaneet ohjelman kaatumisen asettamalla nappulat täsmälleen kaatumista edeltäneeseen asemaan.

### TrainerBot-luokka

TrainerBot-luokan testeissä on testattu sitä, että siirtojen muunto Game-luokan pelilaudasta UCI-muotoon ja toisinpäin toimii oikein. Perussiirtoja on testattu esimerkinomaisesti ja suurempi huomio on kiinnitetty erikoissiirtoihin (tornitus, ohestalyönti, korotus).

### ChessboardList-luokka

ChessboardList-luokan testeissä on testattu, että itse luotu taulukkotietorakenne palauttaa sinne tallennetun pelilaudan ja että taulukon kokoa kasvatetaan tarvittaessa.

### MathUtils-luokka

MathUtils-luokan testeissä on testattu että itse luodut min, max ja abs -metodit toimivat oikein.

### Kattavuus ja toimenpiteet

Alla olevista kuvista näkyy TrainerBot-luokan sekä datastructureproject-pakkaukseen kuuluvien muiden luokkien testikattavuudet. Kattavuudessa on pyritty siihen, että kaikki toiminnallisuudet, pl. getterit, setterit yms., testataan, mutta täyteen rivi- ja haaraumakattavuuteen ei pyritä. Testeissä ilmi tulleet ongelmat on korjattu heti niiden tultua ilmi. 

![Trainerbot](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/pics/jacocoTrainerBot.jpg)
![Datastructureproject](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/pics/jacocoDatastructureproject.jpg)

## Järjestelmätason testit

Järjestelmätason testit on suoritettu manuaalisesti ja automaattisesti xboard-ohjelmalla siten, että toisena pelaajana on ollut tekoäly. Automaaattisessa testauksessa on hyödynnetty xboardin fairymax-tekoälyä sekä Lichessin stockfish-tekoälyä ja manuaalista testausta ovat tehneet tekijä ja tekijän 7-vuotias poika, joka on ikäisekseen taitava shakinpelaaja. Valtaosa testeistä on tehty XBoardilla ja Lichessin osalta on testattu lähinnä, että ohjelma toimii kaatumatta.

Testauksen tarkoituksena on ollut varmistaa, että ohjelma toimii oikein. Erityisesti ongelmatilanteita on pyritty toistamaan useita kertoja, jotta on voitu varmistaa ohjelman toimivuus. Suurimmat esille tulleet ongelmat järjestelmätason testeissä ovat koskeneet erikoissiirtoja ja nämä on pyritty korjaamaan niiden esille tultua.

Lisäksi on testattu lasten mukautuksen vaikeustasoa. Testaajan mukaan sopiva mukautuksen handicap-arvo oli |7.0|. Myös arvo |10.0| oli pelattavissa, mutta haastavampi. Ilman tasoitusta pelaaminen tuntui vaikealta. Kaikissa testeissä minimaxin rekursion syvyys oli 2.

## Suorituskykytestit

Suorituskykytesteissä testattiin laskentatyökalun nopeutta ja minimax-algoritmin nopeutta.

### Versio 1.0

Laskentatyökalun nopeutta testattiin tekemällä 20 siirtoa (10 per puoli) ja laskemalla siirron jälkeisen pelilaudan arvo. Lopuksi laskettiin yhden laskentakerran nopeuden keskiarvo, joka oli 54162 ns.

Minimaxin nopeutta mitattiin tekemällä 100 siirtoa (50 per puoli) ja mittaamalla kokonaisaika. Testit tehtiin syvyyksillä 1, 2 ja 3 ilman alpha-beta-pruningia ja sen kanssa. Syvyyden 3 testi ilman alpha-beta-pruningia lyhennettiin 10 siirtoon per puoli, josta laskettiin arvio, mitä algoritmin suorittaminen olisi kestänyt 50 siirrolla per puoli. Testitulokset alla.

Syvyys | Ilman alpha-beta-pruningia | alpha-beta-pruningin kanssa
--- | --- | ---
1 | 6,42 s | 6,90 s
2 | 4 min 3 s | 1 min 15 s
3 | 2 t 30 min (arvio) | 19 min 38 s

### Versio 1.1

Vaikka syvyyden 3 minimax oli suhteessa ihmispelaajan siirtoon käyttämään aikaan kohtuullinen, tuntui se harmittavan hitaalta. Tämän vuoksi muokkasin laskentatyökalua niin, että se käyttää liikkumisbonuksen laskentaan hyvin samanlaisia metodeita kuin siirtojen luonnissa käytetään, mutta mitään siirtoja ei luoda. 

Yhden laskentakerran nopeus keskimäärin 5109 ns.

Syvyys | Ilman alpha-beta-pruningia | alpha-beta-pruningin kanssa
--- | --- | ---
1 | 0,33 s | 0,35 s
2 | 5,43 s | 3,60 s
3 | 4 min 17 s | 1 min 6 s

## Testien toistettavuus

JUNit-testit ja suorituskykytestit ovat täysin toistettavissa ja niiden koodi löytyy ohjelman koodista. Suorituskykytestejä varten ei kuitenkaan ole luotu MoveSelector-luokkaan erikseen mitään testimetodeja, joista puuttuisi alpha-beta-pruning tai arviointityökalun metodeja, vaan muokkaukset voi nopeasti tehdä käsin. Manuaaliset järjestelmätason testitkin ovat periaatteessa toistettavissa, mutta testaamisessa tehtyjä siirtoja ei ole dokumentoitu muuten kuin ongelmia aiheuttaneet pelitilanteet, jotka löytyvät MoveSelector-luokan JUnit-testeistä.
