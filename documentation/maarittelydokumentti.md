# Määrittelydokumentti

## Algoritmit ja tietorakenteet

Tekoälyä varten toteutetaan lähtökohtaisesti minimax ja alpha beta pruning -algoritmit. Lisäksi toteutetaan algoritmi, joka 
arvioi pelitilannetta sen perusteella, mitä nappuloita pelaajilla on jäljellä ja miten ne ovat sijoittuneet. Tavoitteena on,
että tekoäly voisi pelitilannearvion perusteella valita myös heikompia siirtoja, mikäli ihmispelaaja on pelissä häviöllä.

Lisäksi toteutetaan ohjelmakoodiin shakkipelin säännöt. Tietorakenteista on tarpeellista toteuttaa taulukkolista, koska
pelitilasta pidetään kirjaa listana siirroista. Mahdollisista toteutettavista tietorakenteista on muuten vaikea sanoa
projektin alkuvaiheessa muuta kuin, että projektin kannalta tarpeelliset tietorakenteet toteutetaan, mutta niiden määrä 
pyritään pitämään minimissä.

## Ratkaistava ongelma 

Ratkaistava ongelma on luoda shakkia aloitteleville lapsille sopivan vaikea tekoälyvastustaja, joka tekee jollain tapaa
perusteltuja siirtoja. Tarkoituksena on kuitenkin, että pelaajalla on realistinen mahdollisuus voittaa tekoälyvastustaja.

Minimax ja alpha beta pruning ovat yleisesti kahden pelaajan peleissä käytettyjä algoritmeja, joten kyseiset algoritmit olivat 
luonteva valinta. Taulukkolista taas on koodin toiminnan kannalta tarpeellinen tietorakenne.

## Syöte

Ohjelma saa syötteenä tiedon pelitilasta, joka sisältää vähintään tiedon siitä, kumpaa väriä tekoäly pelaa, kumman vuoro on, 
molempien jäljellä olevan ajan sekä listan siirroista. Syötettä käytetään sopivan siirron arvioimiseen.

## Aikavaativuus 

ALpha beta pruningin pahimman tapauksen aikavaativuus on O (b^d), missä b on haarautumiskerroin (keskimääräinen mahdollisten
siirtojen määrä) ja d on haun syvyys (montako vuoroa eteenpäin analyysiä tehdään). Tavoiteaikavaativuutta on tässä vaiheessa
melko hankalaa asettaa, mutta koska tavoitteena ei ole etsiä aina absoluuttisen parasta mahdollista siirtoa, voidaan hakua 
nopeuttaa.

Lähteet:

![Wikipedia: Alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning)

![Wikipedia: Minimax](https://en.wikipedia.org/wiki/Minimax#Minimax_algorithm_with_alternate_moves)

