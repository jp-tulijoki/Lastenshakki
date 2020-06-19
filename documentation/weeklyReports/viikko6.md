# Viikkoraportti 6

Tällä viikolla otin omat tietorakenteet käyttöön, lisäsin parseMove-metodiin ohestalyönnin ja laajensin sotilaan korotustoiminnallisuutta
kattamaan myös muut nappulat kuin kuningattaren. Päädyin bugien korjaamisen jälkeen pitäytymään alkuperäisessä pelilaudan 
arviointityökalussa: vaikka siinä tehdään paljon laskentaa ja on täten hitaampi, ajattelen sen olevan monipuolisempi ja 
dynaamisempi. Muokkasin myös lasten mukautusta niin, että "vaikeustason" voi säätää ja muokkasin siirronvalinta-algoritmia niin,
että algoritmi karsii pois laittomat siirrot etukäteen ja palauttaa aina siirron (aiemmassa mukautuksessa oli ongelma, että
palautti laittoman siirron, jos shakin torjuminen edellytti uhkaavan nappulan syömistä, mutta "tasoitus" ei antanut myöden.
Tein myös suorituskykytestausta ja päivitin tulokset testausdokumenttiin.

Ohjelma on edennyt siihen pisteeseen, että siitä ei käsittääkseni puutu mitään shakin toiminnallisuuksia.

Viikon opittu asia oli suorituskykytestauksen alkeet ja tajusin konkreettisesti sen, kuinka paljon laskenta voi viedä aikaa, jos 
optimointeja ei tee (toki tiesin tämän teoriatasolla ennestään).

Isompia hankaluuksia ei tällä viikolla ilmennyt.

Ensi viikolla teen sen toisen vertaisarvion, kun siihen annettiin lisäaikaa. Lisäksi pitää vielä testailla lisää, että tuo uusi
korotustoiminnallisuus oikeasti toimii kaikissa tilanteissa ja muutenkin varmistaa, että mitään vakavia bugeja ei ole jäänyt.
Sitten ei ole enää muuta kuin lopun dokumentaation kirjoittaminen ja koodin hiominen lopulliseen versioon. Suorituskykytestauksen
teen uusiksi, koska muutin vielä ekojen testien jälkeen ohjelmakoodia.

Viikolla meni työaikaa 15 tuntia.
