# Viikkoraportti 5

Tämänkin viikon työajasta meni paljon korotusbugin metsästämiseen, mutta uskon viimein paikantaneeni ja korjanneeni sen (ainakin
sain onnistuneita pelejä pelattua). Tämän lisäksi toteutin ne omat tietorakenteet, joita tiedän tarvitsemani: Tein matematiikka-
toiminnallisuudet (min, max ja abs) sekä taulukko-olion pelilautatilanteiden tallentamista varten. Tarkemman miettimisen myötä
tajusin että en tarvitse kaikki ArrayListin toiminnallisuuksia, joten toteutin vain ne, joita varmasti tiedän tarvitsevani.
Lisäksi aloitin suorituskykytestauksen. Koska osa bugeista johtui laskennan raskaudesta ja stack-overlowsta, suunnittelin bugeja
etsiessäni kevyempiä (mutta staattisempia) arviointimetodeja, joten nämä ovat vaihtoehtona, kun viimeistelen ohjelmaa (eivät näy
githubissa tällä hetkellä, jotta koodi ei mene liian sekavaksi).

Ohjelmasta on valmiina tällä hetkellä suhteellisen stabiili versio, jota kuitenkin pitää vielä testata lisää, jotta varmasti toimii.
Lisäksi ohjelmaan on luotu omat tietorakenteet ja ensimmäinen versio suorituskykytestistä.

Opin ainakin luovuutta bugien etsinnässä, kun kaikki ongelmat eivät ole näkyneet testeissä, eikä ulkopuolisesta alustasta ole
saanut normaalia stack tracea.

Valmis suorituskykytestipohja ei minulle ainakaan ilman ohjeita auennut, joten kirjoitin ekan version ainakin nyt alusta asti 
itse.

Ensi viikolla otan omat tietorakenteet käyttöön, jatkan testejä ja kirjoitan lisää dokumentaatiota. Lisäksi botin parseMove-
metodiin tarvitsee vielä lisätä erityistapauksena ohestalyönti.

Viikon työmäärä oli 23 tuntia.
