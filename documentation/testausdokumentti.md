# Testausdokumentti

## Yksikkö -ja integraatiotestaus

Ohjelman metodeita on testattu JUnitilla tehdyillä yksikkö- ja integraatiotesteillä. Testeissä on pyritty käymään läpi esimerkinomaisesti kaikki siirrot ja pelitilanteet. Alla kuvaukset eri luokkien JUnit-testeistä. 

### Game-luokka

Game-luokan testit koostuvat lähtökohtaisesti eri pelinappuloiden siirroista. Jokainen siirtotyyppi on testattu. Nappuloilla, joilla on useita siirtomahdollisuuksia, on asetettu kriteeriksi mahdollisten siirtojen oikea määrä, eikä jokaista generoitua pelilauta-asetelmaa ole testattu erikseen.

### MoveSelector-luokka

MoveSelector-luokan testit testaavat pelilaudan arviointityökalua sekä siirron valinta -metodia. Siirronvalintatestit on suoritettu normaalilla minimax-algoritmilla, ts. sellaisella, joka ei anna tasoitusta. Siirrom valinnassa on testattu, että metodit palauttavat laillisia siirtoja eli eivät esim. anna vastustajalle mahdollisuutta syödä kuningasta seuraavalla siirrolla. Lisäksi on testattu ns. ongelmasiirtoja, jotka ovat jossain vaiheessa aiheuttaneet ohjelman kaatumisen asettamalla nappulat täsmälleen kaatumista edeltäneeseen asemaan.

### TrainerBot-luokka

TrainerBot-luokan testeissä on testattu sitä, että siirtojen muunto Game-luokan pelilaudasta UCI-muotoon ja toisinpäin toimii oikein. Perussiirtoja on testattu esimerkinomaisesti ja suurempi huomio on kiinnitetty erikoissiirtoihin (tornitus, ohestalyönti, korotus).

### ChessboardList-luokka

ChessboardList-luokan testeissä on testattu, että itse luotu taulukkotietorakenne palauttaa sinne tallennetun pelilaudan ja että taulukon kokoa kasvatetaan tarvittaessa.

### MathUtils-luokka

MathUtils-luokan testeissä on testattu että itse luodut min, max ja abs -metodit toimivat oikein.

## Järjestelmätason testit

Järjestelmätason testit on suoritettu manuaalisesti ja automaattisesti xboard-ohjelmalla siten, että toisena pelaajana on ollut tekoäly. Automaaattisessa testauksessa on hyödynnetty xboardin fairymax-tekoälyä ja manuaalista testausta ovat tehneet tekijä ja tekijän 7-vuotias poika, joka on ikäisekseen taitava shakinpelaaja.

Testauksen tarkoituksena on ollut varmistaa, että ohjelma toimii oikein. Erityisesti ongelmatilanteita on pyritty toistamaan useita kertoja, jotta on voitu varmistaa ohjelman toimivuus. Suurimmat esille tulleet ongelmat järjestelmätason testeissä ovat koskeneet erikoissiirtoja ja nämä on pyritty korjaamaan niiden esille tultua.

Lisäksi on testattu lasten mukautuksen vaikeustasoa.

## Suorituskykytestit

Suorituskykytesteissä testattiin laskentatyökalun nopeutta ja minimax-algoritmin nopeutta.

Laskentatyökalun nopeutta testattiin tekemällä 20 siirtoa (10 per puoli) ja laskemalla siirron jälkeisen pelilaudan arvo. Lopuksi laskettiin yhden laskentakerran nopeuden keskiarvo, joka oli 54162 ns.

Minimaxin nopeutta mitattiin tekemällä 100 siirtoa (50 per puoli) ja mittaamalla kokonaisaika. Testit tehtiin syvyyksillä 1, 2 ja 3 ilman alpha-beta-pruningia ja sen kanssa. Syvyyden 3 testi ilman alpha-beta-pruningia lyhennettiin 10 siirtoon per puoli, josta laskettiin arvio, mitä algoritmin suorittaminen olisi kestänyt 50 siirrolla per puoli. Testitulokset alla.

Syvyys | Ilman alpha-beta-pruningia | alpha-beta-pruningin kanssa
--- | --- | ---
1 | 7,12 s | 6,89 s
2 | 6 min 12 s | 1 min 18 s
3 | 2 t 31 min (arvio) | 18 min 57 s

## Testien toistettavuus

JUNit-testit ja suorituskykytestit ovat täysin toistettavissa ja niiden koodi löytyy ohjelman koodista. Manuaaliset järjestelmätason testitkin ovat periaatteessa toistettavissa, mutta testaamisessa tehtyjä siirtoja ei ole dokumentoitu muuten kuin ongelmia aiheuttaneet pelitilanteet, jotka löytyvät MoveSelector-luokan JUnit-testeistä.
