# Testaus dokumentaatio

## Yleistä

Projektia on testattu JUnit yksikkö- ja integraatiotesteillä. Yksikkötestejä on luotu sitä mukaa kun uusia luokkia ja ominaisuuksia on toteutettu, mikä on johtanut lähes TDD tyylin kehittämiseen (tosin testit on tehty jälkeenpäin). Ohjelman hyödyllisin testaaminen tapahtuu integraatiotesteissä, joissa ajetaan input koko ohjelman läpi ja tarkistetaan output. Testit saa siis toistettua ajamalla kaikki projektin test -hakemistosta löytyvät testit. 

Tein kehittämisen avuksi NFAState luokalle muutamia ylimääräisiä ominaisuuksia, joilla saan niistä tietoja ulos. Esimerkiksi NFAState oliolle voi antaa nimen, mikä on yllättävän hyödyllistä debugatessa koodia tulostein. Nämä ominaisuudet poistetaan valmiista ohjelmasta viimeisessä palautuksessa, mutta nt ne toimivat hyödyllisenä empiirisenä testaamisen työvälineenä.

## Yksikkötestit

Ohjelmassa on aika vähän suoria riippuvuussuhteita luokkien välillä, joten yksikkötestien tekeminen oli varsin mielkästä. Siis lähes joka luokalla on joitakin yksikkötestejä, joissa on pyritty kattavasti testaamaan lähes kaikki toiminnallisuus. Vaikka koodi onkin hyvin modulaarista ei esim. NFAMatcherin yksikkötestauksessa ole erityisen paljon järkeä, koska sitä varten pitäisi rakentaa kokonainen NFA mock-syötteenä. Siispä osa koodista testataan integraatiotestein...

more..

##Integraatiotestit

Integraatiotestit sijaitsevat RegexTest.java tiedostossa. Ohjelman integraatiotestaaminen on hyvin helppoa ajamalla ohjelma jollain syötteellä ja tarkistamalla palautus arvo. Pohjimmiltaanhan ohjelma ei muuta tee kuin palauta true tai false jokaisella syötteellä. Integraatiotestejä on siis todella helppo tehdä lisää.

Integraatiotesti:
1. Annetaan testi syötteenä regex ja merkkijono 
2. Prosessoidaan regex
3. Rakennetaan prosessoidun regexin pohjalta NFA
4. Palautetaan totuusarvo: rakennettu NFA tunnistaa merkkijonon
5. Verrataan oikeasti palautettua totuusarvoa ja haluttua totuusarvoa

Integraatiotesteissä on tarkoitus testata mahdollisimman erilaisia (helppoja ja hankalia) regexejä ja merkkijonoja. 
Esimerkiksi helppo: `regex: ab, merkkijono: ab`, hankala: `regex: a*b*|c*b*, merkkijono: acc`.
 
