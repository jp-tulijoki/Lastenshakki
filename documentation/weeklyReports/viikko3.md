# Viikkoraportti 3

Tällä viikolla olen tehnyt viime viikolta jääneet tornituksen ja ohestalyönnin. Lisäksi olen luonut oman shakkibotin (TrainerBot) sekä tehnyt botille toiminnallisuuden, jolla se generoi xboardille tai lichessille siirtoja UCI-muodossa ja päivittää pelioliota shakkialustalta tulevien siirtojen perusteella. Olen myös tehnyt ensimmäisen version pelilaudan arviointityökalusta, jota on tarkoitus käyttää tulevassa siirronvalinta-algoritmissa. Testejä ja javadocia olen pyrkinyt pitämään ajan tasalla.

Ohjelma on edistynyt aiemmin kuvatulla tavalla. Ohjelman tilanne raportin kirjoittamishetkellä on, että random-botti toimii muuten, mutta jää jumiin, kun ihmispelaaja korottaa sotilaan (botin korotustilanne toimii ok). Testeissä korotus tapahtuu oikein, joten en ole vielä keksinyt, mistä tämä johtuu.

Opin pelilaudan arviontityökalua tehdessäni eri tapoja arvioida pelitilanteita.

Hankalinta on ollut tällä viikolla hahmottaa tuota pelialustan ja oman pelilautaolioin välistä kommunikaatiota (eli nämä nextMove ja muut mitä botille pitää koodata). Varmaan tuo edellä kuvaamani korotustilanteen bugikin johtunee siitä. Testit siirtojen muokkaamisessa UCI-muotoon ja UCI:sta pelilautaolioon menevät kyllä läpi.

Seuraavaksi aloitan siirronvalinta-algoritmin tekemisen. Pyrin myös aloittamaan oman lista-luokan koodaamisen. Ja luonnollisesti yritän korjata tuon bugin.

Tämän viikon työaika oli 30 tuntia.

