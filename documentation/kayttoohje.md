# Käyttöohje

## Asentaminen

Projektipohja, johon shakin säännöt ja tekoäly on tehty, toimii `XBoard` ja `Lichess` -alustoilla. Katso pohjan alkuperäisten tekijöiden [ohjeet](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/templateDocuments/Beginners_guide.md)
saadaksesi ohjelman toimimaan.

Projektin voi ladata omalle koneelle repositorion oikeassa yläreunassa olevasta `clone` -painikkeesta.

## Käyttäminen

Pelaamiseen liittyviä toiminnallisuuksia voi käyttää vain `XBoard` ja `Lichess` -alustoilla, eikä tämän repositorion ohjelmakoodi sisällä käyttöliittymää. Nopea tapa saada ohjelma käyttöön on ladata jokin [releasen](https://github.com/jp-tulijoki/Lastenshakki/releases) .jar-tiedosto ja käyttää sitä `XBoardissa` yllä olevan linkin ohjeiden mukaisesti.

Muut toiminnallisuudet vaativat toimiakseen Gradlen. Gradle-pluginin voi asentaa oman ohjelmointiympäristön kautta tai sen voi hakea [täältä](https://gradle.org/releases/).

Komentoriviltä voi ajaa mm. seuraavia toimintoja:
* `./gradlew build` (.jar-tiedoston generointi /build/libs -kansioon)
* `./gradlew test` (testien ajaminen)
* `./gradlew JacocoTestReport` (testikattavuusraporttien generointi /build/reports/jacoco/test -kansioon)
* `./gradlew check` (checkstyle-raportti)

## Asetusten muuttaminen

Tekoälyn asetukset annetaan TrainerBot-olioon liitetyssä MoveSelectorissa. MoveSelectorin ensimmäinen parametri on siihen liitetty Game-olio. Tälle ei tule tehdä 
mitään. Toinen parametri on minimaxin syvyys. Käytännössä tarkoituksenmukaisia syvyyksiä ovat esimerkiksi 2 tai 3, joista 2 on hieman nopeampi, mutta 3 hieman parempi. Myös syvyys 4 toimii kohtuullisessa ajassa, mutta laskennan nopeus hidastuu joihinkin kymmeniin sekunteihin per siirto. Seuraava boolean-arvo määrittää, antaako valkoinen botti tasoitusta vai ei. Boolean-arvoa seuraavalla numeroarvolla on merkitystä vain, jos botti antaa tasoitusta. Arvo määrittää ns. parhaan sallitun siirron. Valkoisen arvo annetaan positiivisena liukulukuna. Esim. arvo 7.0 tarkoittaa käytännössä, että jos botti on syönyt pelinalussa vastustajan molemmat lähetit, se ei syö enempää nappuloita ennen kuin itse menettää niitä. Tasoitus ei kuitenkaan estä bottia tekemästä shakkimattia tai torjumasta shakkia syömällä nappulan, vaikka tasoitus ei muuten antaisi myöden.

Jälkimmäinen boolean-arvo määrittää, antaako musta tasoitusta. Vastaavasti boolean-arvoa seuraava parametri määrittää, paljonko tasoitusta musta antaa. Peruslogiikka on sama kuin valkoisella. Ainoa ero on, että mustan tasoituksen arvo annetaan negatiivisena liukulukuna.

Alla olevassa esimerkissä on asetettu valkoiselle 7.0 yksikön tasoitus. Mustalla tasoitusta ei ole.

![tasoitus](https://github.com/jp-tulijoki/Lastenshakki/blob/master/documentation/pics/setHandicap.jpg)
