
# Viikko 4

Tällä viikolla suurinosa ajasta meni debugaamiseen. Testit paljastivat ikäviä bugeja NFA:n konstruktoinnista ja kielten tunnistamisesta NFAMatcherilla. Oikeastaan kaikessa toiminnallisuudessa oli joitakin bugeja... NFAMatcher toimii nyt kait oikein, mutta konstruktointi ei vieläkään. Tiedän onneksi mitkä asiat ei toimi, niin ei tarvitse enää metsästää bugeja. Tein myös regexin esiprosessointiin hiukan lisää toiminnallisuutta, kun lisäsin ohjelmaan . villikortti merkin. Muiden lisämerkkien pariin en vielä ehtinyt, koska useat niistä vaativat sulkeita ja NFAconstructor ei vielä osaa lukea niitä oikein. Aloitin myös tekemään suorituskykytestausta.

Ohjelma edistyi vähän hitaammin kuin olin toivonut. Tavoitteena oli tällä viikolla saada sulkeet toimimaan, jotta haave NFA -> DFA muutoksesta pysyisi vielä hengissä. En enää usko että ehdin toteuttaa sitä, koska pelkällä NFA:llakin hommassa on tosi paljon duunia. Onneksi sain useamman bugin ratkottua niin ei ainakaan tarte niistä enää murehtia...

Tällä viikolla opin että testini olivat liian yksipuolisia ja siksi ne eivät olleet huomanneet ennen tätä viikkoa joitakin bugeja joita ohjelmassa oli ollut jo toisesta viikosta lähtien. Opin myös kärsivällisyyttä kun tuijotin ruutua tunti kaupalla yrittäen ratkaista joitain typeriä bugeja.

Jatkoa ajatellen epäselvyyksiä ei onneksi ole, mutta kaiken maailman bugit tulevat varmaan aiheuttamaan harmaita hiuksia. Pitää varmaan vähän miettiä jossain välissä kuinka tuota suorituskykytestaamista on fiksuinta tehdä, jotta saan jossain välissä tuloksia visualisoitua.

Projektin parissa aikaa kului tällä viikolla jotain n. 14 tuntia. 

Ensi viikolla jatkan bugien metsästystä, sulkeiden implementointia, regexien preprosessointia ja suorituskykytestaamisen jatkamista.
