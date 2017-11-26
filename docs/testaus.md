# Testaus dokumentaatio

## Yleistä

Projektia on testattu JUnit yksikkö-, integraatio- ja suorituskykytesteillä. Yksikkötestejä on luotu sitä mukaa kun uusia luokkia ja ominaisuuksia on toteutettu, mikä on johtanut lähes TDD tyylin kehittämiseen (tosin testit on tehty jälkeenpäin). Ohjelman hyödyllisin testaaminen tapahtuu integraatiotesteissä, joissa ajetaan input koko ohjelman läpi ja tarkistetaan output. Testit saa siis toistettua ajamalla kaikki projektin test -hakemistosta löytyvät testit. 

Tein kehittämisen avuksi NFAState luokalle muutamia ylimääräisiä ominaisuuksia, joilla saan niistä tietoja ulos. Esimerkiksi NFAState oliolle voi antaa nimen, mikä on yllättävän hyödyllistä debugatessa koodia tulostein. Nämä ominaisuudet poistetaan valmiista ohjelmasta viimeisessä palautuksessa, mutta nyt ne toimivat hyödyllisenä empiirisen testaamisen työvälineenä.

## Yksikkötestit

Ohjelmassa on aika vähän suoria riippuvuussuhteita luokkien välillä, joten yksikkötestien tekeminen oli varsin mielekästä. Siispä lähes joka luokalla on joitakin yksikkötestejä, missä on pyritty kattavasti testaamaan lähes kaikki toiminnallisuus. 

Yksikkötestaamisessa oli muutamia ongelmia: Luokkien yksityisten metodien testaaminen onnistuisi reflektion avulla, mutta se on vähän hankalaa. Ja vaikka koodi onkin hyvin modulaarista ei esim. NFAMatcherin yksikkötestauksessa ole erityisen paljon järkeä, koska sitä varten pitäisi rakentaa kokonainen NFA syötteeksi. Onneksi nämä ongelmat ratkeaa integraatiotestauksella...

## Integraatiotestit

Integraatiotestit sijaitsevat `RegexTest.java` tiedostossa. Ohjelman integraatiotestaaminen on hyvin helppoa: ajetaan ohjelma jollain syötteellä ja tarkistetaan palautus arvo. Pohjimmiltaanhan ohjelma ei muuta tee kuin palauta true tai false jokaisella syötteellä.

Integraatiotesti:
1. Annetaan testisyötteinä regex ja input merkkijonot
2. Esiprosessoidaan regex
3. Rakennetaan esiprosessoidun regexin pohjalta NFA
4. Palautetaan totuusarvo: rakennettu NFA tunnistaa inputin
5. Verrataan oikeasti palautettua totuusarvoa ja haluttua totuusarvoa

Integraatiotesteissä on tarkoitus testata mahdollisimman erilaisia (helppoja ja hankalia) regexejä ja merkkijonoja. 
Esimerkiksi helppo: `regex: ab, merkkijono: ab, expect: true`, hankalampi: `regex: a*b*|c*b*, merkkijono: acc, expect: true`.

## Suorituskykytestit

Suorituskykytestit löytyvät tiedostosta `RegexBenchmarkTest.java`. 

Niitä on neljänlaisia: 
1. Koko ohjelman integraatiotesti
2. RegexPreprocessor testi
3. NFAConstructor testi
4. NFAMatcher testi

Testille annetaan syötteenä regex ja/tai input, sekä toistojen määrä n. Testi toistetaan n-kertaa ja tuloksena saadaan n-ajon keskiarvo millisekuntteina.

