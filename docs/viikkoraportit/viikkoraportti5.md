
# Viikko 5

Tällä viikolla tein regexin preprosessointiin lisää toiminnallisuutta, yritin saada sulkeet toimimaan regexissä ja korjasin bugeja nfa:n konstruktoinnista. Lisäksi refaktoroin hiukan koodia ja tein vähän suorituskykytestejä. 

Ohjelma olisi voinut edistyä hiukan nopeammin, mutta toisaalta työ on nyt sellaisessa kohtaa, että suurinosa ajasta menee bugien metsästämiseen nfa:n konstruktoinnista, mikä nyt vaan on hidasta. 

Tällä viikolla opin että NFAConstructor luokka on aika hirveää koodia. Vaikka olen itse sen kirjoittanut ihan viime aikoina ei sitä paikoin ole helppo seurata + siellä on ihan liikaa spagettia ja rumaa koodia. Rehellisesti sanoen tekisi mieli tehdä se uusiksi, mutta ei mulle ole aikaa sille. Pitää nyt vaan jaksaa tapella sen kanssa loppuun saakka.

Debugaaminen on tuottanut paljon päänsärkyä. Uskon kyllä, että saan ohjelman lopulta toimimaan, mutta aikaa vaan tuntuu olevan liian vähän. 

Seuraavaksi jatkan bugien korjaamista ja sulkeiden implementointia. Olisi myös kiva implementoida merkeille escape -ominaisuus ( \\*, \\[ jne). Ohjelmasta voisi myös tehdä jossain välissä komentoriviltä ajettava version. 

