
# Viikko 2

Tällä viikolla tein ohjelmarungon valmiiksi regexien NFA:lla tunnistamiselle. Syötetystä regexistö luodaan NFA ja sillä tunnistetaan annettu syöte. Tein myös varsin kattavat testit luoduille luokille. Huomasin että integraatiotestien tekeminen ohjelmalle on todella helppoa, mikä on hyvä juttu. Tein myös regexien esiprosessorista sellaisen ruman alfa version. Luulen että sen toiminnallisuuksia lisätään viimeisenä, joten nykyinen versio on ihan ok. 
 
Ohjelma edistyi ihan hyvin. Pohja on nyt valmiina niin voin alkaa keskittyä pääasiaan eli nfa luomiseen. Harmi vaan että tällä viikolla oli niin paljon muuta tekemistä että en lopulta ehtinyt käyttää projektiin aikaa kuin n. 8 tuntia, mikä on aivan liian vähän. Jatkossa olisi tarkoitus tehdä joka viikko vähintään 12 tuntia työtä projektin eteen.

Opin että \*-operaattoria implementointi naiivisti ei onnistu, koska regexissä on mahdollista olla useita erikoismerkkejä (\*,+,| jne.) sisäkkäin. NFAMatcher oli yllättävän helppo tehdä. Toivottavasti se toimii oikein monimutkaisemmilla regexeillä.

En tiedä jäikö mikään niinkään epäselväksi. Lähinnä NFA:n konstruktoinnin käytännön toteutus aiheuttaa päänvaivaa, mutta löysin ihan hyvän esimerkin asialle niin uskon selviäväni hommasta ihan kunnialla.

Ensiviikolla refaktoroin NFAConstructorin toimimaan rekursiivisesti ja alan implementoimaan \*, + ja | -operaattoreita varmaankin tuossa järjestyksessä. Toisaalta pitää miettiä olisiko |-operaattori viisainta tehdä ensin, koska se on varmaankin helppo tehdä rekursiivisella strategialla.

Jostain syystä testcovin kanssa on jotain ongelmia. Pitäiskö noi testi tulokset puskea repoon että testcov osaa tehdä reportin? Pitänee korjata myös se jossai vaiheessa...
