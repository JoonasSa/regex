# Testaus dokumentaatio

## Yleistä

Projektia on testattu JUnit yksikkö-, integraatio- ja suorituskykytesteillä. Yksikkötestejä on luotu sitä mukaa kun uusia luokkia ja ominaisuuksia on toteutettu, mikä on johtanut lähes TDD tyylin kehittämiseen (tosin testit on tehty jälkeenpäin). Ohjelman hyödyllisin testaaminen tapahtuu integraatiotesteissä, joissa ajetaan input koko ohjelman läpi ja tarkistetaan output. Testit saa siis toistettua ajamalla kaikki projektin test -hakemistosta löytyvät testit. 

Tein kehittämisen avuksi NFAState luokalle muutamia ylimääräisiä ominaisuuksia, joilla saan niistä tietoja ulos. Esimerkiksi NFAState oliolle voi antaa nimen, mikä on yllättävän hyödyllistä debugatessa koodia tulostein. Nämä ominaisuudet poistetaan valmiista ohjelmasta viimeisessä palautuksessa, mutta nyt ne toimivat hyödyllisenä empiirisen testaamisen työvälineenä.

Testit saa toistettua ajamalla kaikki `src/test` kansiossa olevat java-tiedostot. 

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

Niitä on monenlaisia: 
1. Koko ohjelman integraatiotesti
2. RegexPreprocessor testi
3. NFAConstructor testi
4. NFAMatcher testi
5. Minun implementaationa vastaan Javan regex (syötteenä joko merkkijono tai tiedoston sijainti)

Testille annetaan syötteenä regex ja/tai input, sekä mahdollisesti toistojen määrä n. Testi toistetaan n-kertaa ja tuloksena saadaan kokonaisaika ja n-ajon keskiarvo millisekuntteina. Testit joissa luetaan tiedostosta suoritetaan vain kerran per ajo.

## Benchmark measurements

All the measurements were made on my computer which is old, therefore the relative time differences between different regex runtimes are the interesting results (not the exact runtimes).

You can read from [here](usage.md) how to run the benchmark tests.

#### Whole program

regex: `abcdefghijkl`, input: `abcdefghijkl`

| Runtime (ms) | Times run |
| ----- | ----- |
| 119 | 10000 |
| 276 | 100000 |
| 1271 | 1000000 |
| 10743 | 10000000 |

regex: `(ab)*`, input: `abababab`

| Runtime (ms) | Times run |
| ----- | ----- |
| 96 | 10000 |
| 151 | 100000 |
| 968 | 1000000 |
| 8922 | 10000000 |

regex: `(abcdefghi|abcdefghij)`, input: `abcdefghij`

| Runtime (ms) | Times run |
| ----- | ----- |
| 130 | 10000 |
| 416 | 100000 |
| 2593 | 1000000 |
| 26205 | 10000000 |

#### Matching

regex: `abcdefghijkl`, input: `abcdefghijkl`

| Runtime (ms) | Times run |
| ----- | ----- |
| 12 | 10000 |
| 46 | 100000 |
| 292 | 1000000 |
| 2379 | 10000000 |

regex: `(ab)*`, input: `abababab`

| Runtime (ms) | Times run |
| ----- | ----- |
| 23 | 10000 |
| 68 | 100000 |
| 441 | 1000000 |
| 4312 | 10000000 |

regex: `(abcdefghi|abcdefghij)`, input: `abcdefghij`

| Runtime (ms) | Times run |
| ----- | ----- |
| 22 | 10000 |
| 67 | 100000 |
| 426 | 1000000 |
| 3305 | 10000000 |

#### Contructing

regex: `abcdefghijkl`

| Runtime (ms) | Times run |
| ----- | ----- |
| 15 | 10000 |
| 27 | 100000 |
| 130 | 1000000 |
| 881 | 10000000 |

regex: `(ab)*`

| Runtime (ms) | Times run |
| ----- | ----- |
| 16 | 10000 |
| 62 | 100000 |
| 158 | 1000000 |
| 1112 | 10000000 |

regex: `(abcdefghi|abcdefghij)`

| Runtime (ms) | Times run |
| ----- | ----- |
| 61 | 10000 |
| 127 | 100000 |
| 673 | 1000000 |
| 6729 | 10000000 |

#### Whole process: my implementation vs Java implementation

regex: `abcdefgh`, input: `abcdefgh`

| My implementation runtime (ms) | Java implementation runtime (ms) | Times run |
| ----- | ----- | ----- |
| 26 | 16 | 1000 |
| 50 | 24 | 10000 |
| 139 | 98 | 100000 |
| 1113 | 314 | 1000000 |
| 9366 | 3327 | 10000000 |

regex: `(ab)*`, input: `abababab`

| My implementation runtime (ms) | Java implementation runtime (ms) | Times run |
| ----- | ----- | ----- |
| 44 | 25 | 1000 |
| 69 | 35 | 10000 |
| 187 | 135 | 100000 |
| 1156 | 324 | 1000000 |
| 9210 | 3367 | 10000000 |

regex: `(abcdefg|abcdefgh)`, input: `abcdefgh`

| My implementation runtime (ms) | Java implementation runtime (ms) | Times run |
| ----- | ----- | ----- |
| 43 | 24 | 1000 |
| 109 | 49 | 10000 |
| 254 | 58 | 100000 |
| 2259 | 483 | 1000000 |
| 26921 | 6386 | 10000000 |

#### Matching substrings from file

I used a file with [10000](../loremipsum1k.txt) and 100000 lines of lorem ipsum as the input. 

Word by word with 100k line file

| Regex | My implementation runtime (ms) | Java implementation runtime (ms) |
| ----- | ----- | ----- |
| `lorem` | 922 | 506 |
| `(lorem\|ipsum)` | 1024 | 577 |
| `.*a.*` | 2222 | 829 |
| `(tor)+` | 698 | 501 |

Line by line with variable file sizes _(non-anchored)_

| Regex | Input size | My implementation runtime (ms) | Java implementation runtime (ms) |
| ----- | ----- | ----- | ----- |
| `lorem` | 100k | 1339 | 138 |
| `ean tortor ips` | 100k | 2465 | 114 |
| `(tor)+` | 100k | 7256 | 327 |
| `(lorem)*` | 10k | 3072 | 95 |
