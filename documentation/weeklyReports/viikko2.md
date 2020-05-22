# Viikkoraportti 2

Olen tehnyt tällä viikolla kaikkien nappuloiden perussiirrot eli sen, miten nappula voi liikkua suhteessa muihin nappuloihin ja
pelilaudan reunaan (välittämättä tässä vaiheessa pelilaudan kokonaistilanteesta). Tornitusta ja ohestalyöntiä en ole vielä
tehnyt. Lisäksi olen kirjoittanut jokaiselle nappulalle yksinkertaiset testit ja testannut toimivuutta. Olen myös testannut,
että saan ainakin ./gradlew-komennolla Jacocon ja Checkstylen raportit. Lisäksi olen kirjoittanut javadocia uusia metodeita 
lisätessäni (en gettereihin, settereihin yms. ilmeisiin metodeihin).

Ohjelma on siis käytännössä edistynyt noiden perussiirtojen verran.

Opin ainakin sen, että kaikkien mahdollisten liikkumiskombinaatioiden kattava testaaminen on haastavaa.

Alussa kesti jonkin aikaa hahmottaa pelilauta koodauksen näkökulmasta, mutta sen jälkeen koodaaminen on ollut melko suoraviivaista.
Hankalaa on kuitenkin ollut tässä vaiheessa hahmottaa, onko valitsemani lähestymistapa siirtojen koodaamiselle järkevä
kokonaisuuden kannalta.

Alan seuraavaksi koodaamaan enemmän kokonaisuutta, esim. miten teknisesti sallittujen siirtojen joukosta valitaan vain 
kokonaistilanteen kannalta sallittuja siirtoja (esim. käsitellään shakkitilanne ja suodatetaan pois siirrot, joilla kuningas 
jäisi suojattomaksi). Lisäksi teen nuo puuttuvat tornituksen ja ohestalyönnin. Mahdollisesti mietin myös siirronvalinta-
algoritmeja.

Käytetty tuntimäärä viikolla 2 oli 17 tuntia.
