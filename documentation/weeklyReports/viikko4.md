# Viikkoraportti 4

Olen toteuttanut minimax-algoritmin, jossa ei kuitenkaan ole vielä lapsille sopeutusta. Lisäksi olen parannellut pelilautaolion
ja botin toiminnallisuuksia sekä aloittanut toteutus- ja testausdokumenttien kirjoittamisen. Iso osa ajasta on kuitenkin 
valitettavasti mennyt viime raportissa kuvaamieni ongelmien selvittelyyn. Aivan viikon lopussa sain selville, että sotilaan
korottaminen sekä ainakin tietty aloitussiirto aiheuttavat stack overflow errorin, mutta en ole vielä ehtinyt asiaan paneutua
tarkemmin. Ongelmista johtuen en ehtinyt aloittamaan tuota omaa lista-luokkaa tällä viikolla.

Ohjelman ydintoiminta on tuota bugien korjaamista ja lapsille sopeutusta vaille valmis. Tekoäly pelaa ihan kohtuullista shakkia
etenkin alkuvaiheessa, hyödyntää ihmispelaajan virheet ja osaa tilaisuuden tullen tehdä myös shakkimatin.  

Opin tällä viikolla tuon minimaxin koodaamisen javalla sekä sain taas lisäkokemusta virheiden selvittelystä.

Hankalin asia on ehdottomasti ollut bugien korjaaminen ja sen hahmottaminen, mistä se johtuu, kun xboardin virheilmoitukset 
eivät kerro java-ohjelman toiminnasta. 

Ensi viikolla jatkan bugien korjaamista. Helppoa se ei tunnu olevan, mutta ainakin tuo löydetty stack overflow auttaa vähän.
Aloitan lisäksi tuon listan koodaamisen ja mietin tuota lapsille sopeutusta. Lisäksi minimax voi tarvita vielä hiomista.

Viikon työaika oli 19 tuntia.
