# Testausdokumentti

## Yksikkö -ja integraatiotestaus

Ohjelman metodeita on testattu JUnitilla tehdyillä yksikkö- ja integraatiotesteillä. Testeissä on pyritty käymään läpi esimerkinomaisesti 
kaikki siirrot ja pelitilanteet. Nappuloilla, joilla on useita siirtomahdollisuuksia, on asetettu kriteeriksi mahdollisten
siirtojen oikea määrä, eikä jokaista generoitua pelilauta-asetelmaa ole testattu erikseen.

## Järjestelmätason testit

Järjestelmätason testit on suoritettu manuaalisesti ja automaattisesti xboard-ohjelmalla siten, että toisena pelaajana on ollut
tekoäly. Automaaattisessa testauksessa on hyödynnetty xboardin fairymax-tekoälyä ja manuaalista testausta ovat tehneet tekijä
ja tekijän 7-vuotias poika, joka on ikäisekseen taitava shakinpelaaja.
