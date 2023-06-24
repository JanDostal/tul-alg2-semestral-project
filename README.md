
# Zadání práce

## Motivace

Už od 8 let jsem se začal velmi intenzivně zajímat o Star Wars ve všech ohledech.

Začalo to stavěním výhradně LEGO stavebnic pouze ze Star Wars, tyto stavebnice jsem si pak vystavoval po celém pokoji. V současnosti mám vystavených cca. 20 stavebnic, ale určitě jsem postavil víc než 20, jenom nebyl k dispozici prostor pro vystavení, takže jsem nějaké stavebnice po čase musel rozložit a dát do krabice s tříděným LEGEM, umístěným v mém pokoji.

Dále jsem se začal zajímat o seriály, filmy, knížky, hudbu a komiksy. Později, když jsem byl starší, tak jsem začal hrát PC hry, přičemž 95 % všech her tvořily Star Wars hry. Zároveň jsem začal sbírat světelné meče (Hasbro, nezávislí výrobci) různého typu (plastové, s LED zářivkou).

V poslední řadě jsem se zúčastnil s rodinou předpremiér nových Star Wars filmů v letech 2015, 2016, 2017, 2018 a 2019. Atmosféra byla úžasná, jenom po nějaké době jsem si uvědomil, že scénáře filmů z roku 2015, 2017 a 2019 byly příšerné, naopak scénáře filmů z roků 2016 a 2018 byly docela OK.

Tedy shrnutím. Star Wars je můj special interest a přišlo mi jako dobrý nápad využít programování jako nástroj pro vytvoření jakéhosi „pomocníka“ na Star Wars seriály a filmy.

## Popis problému

Aplikace by měla sloužit jako evidence mediálního obsahu (seriály, filmy) v rámci výhradně Star Wars univerza.

Aplikace je určena pro uživatele, kteří jsou pokročilí fanoušci Star Wars (geekové), ale hodí se i pro začínající fanoušky. Pro laika je tato aplikace bezpředmětná.

Cílem aplikace je fanouškovi umožnit:
- Zjednodušit naplánování si filmového či seriálového maratonu nezhlédnutého obsahu dle různých kritérií
-	Organizovat evidovaný obsah do chronologických období v rámci Star Wars univerza
-	Umožnit hodnotit zhlédnutý obsah, pro účely opakovaného zhlédnutí
-	Poskytovat souhrnné/statistické údaje na základě evidovaného obsahu

# Řešení

## Funkční specifikace

Aplikace je z hlediska uživatelských funkcí rozdělena na tyto tři části/menu:

### Menu nastavování adresáře data

- Zobrazuje se na začátku běhu aplikace, potom již ne
- Slouží k nastavení/konfiguraci cesty k adresáři **data** umístěném na disku 
    - Adresář obsahuje datové vstupní a výstupní soubory specifikované [zde](#popis-struktury-vstupních-a-výstupních-souborů)
- Je umožněno ukončit aplikaci pomocí **funkce s číslem 0**

Samotné uživatelské funkce vypadají následovně:

---

1. Zadat cestu k data adresáři

---

### Menu načítání výstupních souborů

- Zobrazuje se po [Menu nastavování adresáře data](#menu-nastavování-adresáře-data), potom již ne
- Slouží k načtení existujících/evidovaných dat do aplikace z výstupních souborů
- Účelem je to, že aplikace průběžně ukládá svá nová data do výstupních souborů, aby se dala při příštím spuštění aplikace snadno obnovit a nevkládat je znova
- Je umožněno ukončit aplikaci pomocí **funkce s číslem 0**

Samotné uživatelské funkce vypadají následovně:

---

1. Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)
2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)
3. Vypsat obsah textového souboru output_movies.txt (diagnostika chyby při načítání)
4. Vypsat obsah textového souboru output_tvShows.txt (diagnostika chyby při načítání)
5. Vypsat obsah textového souboru output_tvSeasons.txt (diagnostika chyby při načítání)
6. Vypsat obsah textového souboru output_tvEpisodes.txt (ddiagnostika chyby při načítání)
7. Vypsat obsah binárního souboru output_movies.bin (diagnostika chyby při načítání)
8. Vypsat obsah binárního souboru output_tvShows.bin (diagnostika chyby při načítání)
9. Vypsat obsah binárního souboru output_tvSeasons.bin (diagnostika chyby při načítání)
10. Vypsat obsah binárního souboru output_tvEpisodes.bin (diagnostika chyby při načítání)

---

### Hlavní menu

- Zobrazuje se po [Menu načítání výstupních souborů](#menu-načítání-výstupních-souborů) a opakovaně během běhu aplikace
- Hlavní Menu je víceúrovňové, takže funkce v seznamu jsou odsazeny podle hiearchické úrovně, ve které se nacházejí
- Podúrovně/podmenu hlavního menu se též zobrazují opakovaně
- V hlavním menu je umožněno ukončit aplikaci pomocí **funkce s číslem 0**
- Každé podmenu má **funkci s číslem 0**, která umožňuje vrátit se z aktuálního podmenu/podúrovně do nadřazené úrovně/menu

Samotné uživatelské funkce vypadají následovně:


---


<ol>
  <li>Vypsat informace o chronologických érách</li>
  <li>
    Spravovat filmy
    <ol type="1">
      <li>
        Přidat filmy ze vstupního souboru
        <ol type="1">
          <li>Načíst z textového souboru input_movies.txt</li>
          <li>Načíst z binárního souboru input_movies.bin</li>
          <li>Vypsat obsah textového souboru input_movies.txt</li>
          <li>Vypsat obsah binárního souboru input_movies.bin</li>
        </ol>
      </li>
      <li>
        Vyhledat film podle jména
        <ol type="1">
          <li>Smazat aktuálně vypsané nalezené filmy</li>
          <li>
            Vypsat detail vybraného nalezeného filmu
            <ol type="1">
              <li>Smazat film</li>
              <li>
                Upravit film
                <ol type="1">
                  <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                  <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                  <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                  <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                </ol>
              </li>
              <li>Ohodnotit film <strong><i>V tomto případě je uživatelská funkce dostupná pouze při rozpoznání jako vydaný film</i></strong></li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Poslat e-mailem filmy
        <ol type="1">
          <li>Poslat e-mailem nezhlédnuté filmy od nejstaršího</li>
          <li>Poslat e-mailem nezhlédnuté filmy v rámci chronologických ér</li>
        </ol>
      </li>
      <li>
        Vypsat oznámené filmy v jednotlivých érách
        <ol type="1">
          <li>
            Vypsat abecedně oznámené filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané oznámené filmy</li>
              <li>
                Vypsat detail vybraného oznámeného filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat vydané nezhlédnuté filmy v jednotlivých érách
        <ol type="1">
          <li>
            Vypsat abecedně nezhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané nezhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného nezhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejnovější nezhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané nezhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného nezhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejdelší nezhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané nezhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného nezhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat vydané zhlédnuté filmy v jednotlivých érách
        <ol type="1">
          <li>
            Vypsat abecedně zhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané zhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného zhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejnovější zhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané zhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného zhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejdelší zhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané zhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného zhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejoblíbenější zhlédnuté filmy vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané zhlédnuté filmy</li>
              <li>
                Vypsat detail vybraného zhlédnutého filmu
                <ol type="1">
                  <li>Smazat film</li>
                  <li>
                    Upravit film
                    <ol type="1">
                      <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                      <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                    </ol>
                  </li>
                  <li>Ohodnotit film</li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat nejoblíbenější filmy
        <ol type="1">
          <li>Smazat aktuálně vypsané nejoblíbenější filmy</li>
          <li>
            Vypsat detail vybraného zhlédnutého filmu
            <ol type="1">
              <li>Smazat film</li>
              <li>
                Upravit film
                <ol type="1">
                  <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                  <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                  <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                  <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                </ol>
              </li>
              <li>Ohodnotit film</li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat nejnovější již vydané filmy
        <ol type="1">
          <li>Smazat aktuálně vypsané nejnovější již vydané filmy</li>
          <li>
            Vypsat detail vybraného vydaného filmu
            <ol type="1">
              <li>Smazat film</li>
              <li>
                Upravit film
                <ol type="1">
                  <li>Upravit film pomocí vstupního textového souboru input_movies.txt</li>
                  <li>Upravit film pomocí vstupního binárního souboru input_movies.bin</li>
                  <li>Vypsat obsah vstupního textového souboru input_movies.txt</li>
                  <li>Vypsat obsah vstupního binárního souboru input_movies.bin</li>
                </ol>
              </li>
              <li>Ohodnotit film</li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat obsahy výstupních souborů filmů
        <ol type="1">
          <li>Vypsat obsah textového souboru output_movies.txt</li>
          <li>Vypsat obsah binárního souboru output_movies.bin</li>
        </ol>
      </li>
    </ol>
  </li>
  <li>
    Spravovat TV epizody
    <ol type="1">
      <li>
        Přidat TV seriály ze vstupního souboru
        <ol type="1">
          <li>Načíst z textového souboru input_tvShows.txt</li>
          <li>Načíst z binárního souboru input_tvShows.bin</li>
          <li>Vypsat obsah textového souboru input_tvShows.txt</li>
          <li>Vypsat obsah binárního souboru input_tvShows.bin</li>
        </ol>
      </li>
      <li>
        Vyhledat TV seriál podle jména
        <ol type="1">
          <li>Smazat aktuálně vypsané nalezené TV seriály</li>
          <li>
            Vypsat detail vybraného nalezeného TV seriálu
            <ol type="1">
              <li>Smazat TV seriál</li>
              <li>
                Upravit TV seriál
                <ol type="1">
                  <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                  <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                  <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                  <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                </ol>
              </li>
              <li>
                Vypsat TV sezóny <strong><i>V tomto případě je uživatelská funkce dostupná pouze tehdy, pokud se jedná o vydaný TV seriál</i></strong>
                <ol type="1">
                  <li>
                    Přidat TV sezóny ze vstupního souboru
                    <ol type="1">
                      <li>Načíst z textového souboru input_tvSeasons.txt</li>
                      <li>Načíst z binárního souboru input_tvSeasons.bin</li>
                      <li>Vypsat obsah textového souboru input_tvSeasons.txt</li>
                      <li>Vypsat obsah binárního souboru input_tvSeasons.bin</li>
                    </ol>
                  </li>
                  <li>Smazat aktuálně vypsané TV sezóny</li>
                  <li>
                    Poslat e-mailem TV epizody
                    <ol type="1">
                      <li>Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat nejdelší TV epizody tohoto seriálu
                    <ol type="1">
                      <li>Smazat aktuálně vypsané TV epizody</li>
                      <li>Vypsat detail vybrané TV epizody</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat nejoblíbenější TV epizody tohoto seriálu
                    <ol type="1">
                      <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                      <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat detail vybrané TV sezóny
                    <ol type="1">
                      <li>Smazat TV sezónu</li>
                      <li>
                        Upravit TV sezónu
                        <ol type="1">
                          <li>Upravit TV sezónu pomocí vstupního textového souboru input_tvSeasons.txt</li>
                          <li>Upravit TV sezónu pomocí vstupního binárního souboru input_tvSeasons.bin</li>
                          <li>Vypsat obsah vstupního textového souboru input_tvSeasons.txt</li>
                          <li>Vypsat obsah vstupního binárního souboru input_tvSeasons.bin</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat TV epizody
                        <ol type="1">
                          <li>
                            Přidat TV epizody ze vstupního souboru
                            <ol type="1">
                              <li>Načíst z textového souboru input_tvEpisodes.txt</li>
                              <li>Načíst z binárního souboru input_tvEpisodes.bin</li>
                              <li>Vypsat obsah textového souboru input_tvEpisodes.txt</li>
                              <li>Vypsat obsah binárního souboru input_tvEpisodes.bin</li>
                            </ol>
                          </li>
                          <li>Smazat aktuálně vypsané TV epizody</li>
                          <li>
                            Vypsat nejdelší TV epizody této sezóny
                            <ol type="1">
                              <li>Smazat aktuálně vypsané TV epizody</li>
                              <li>Vypsat detail vybrané TV epizody</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat nejoblíbenější TV epizody této sezóny
                            <ol type="1">
                              <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                              <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat detail vybrané TV epizody
                            <ol type="1">
                              <li>Smazat TV epizodu</li>
                              <li>
                                Upravit TV epizodu
                                <ol type="1">
                                  <li>Upravit TV epizodu pomocí vstupního textového souboru input_tvEpisodes.txt</li>
                                  <li>Upravit TV epizodu pomocí vstupního binárního souboru input_tvEpisodes.bin</li>
                                  <li>Vypsat obsah vstupního textového souboru input_tvEpisodes.txt</li>
                                  <li>Vypsat obsah vstupního binárního souboru input_tvEpisodes.bin</li>
                                </ol>
                              </li>
                              <li>Ohodnotit TV epizodu</li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat oznámené TV seriály v jednotlivých érách
        <ol type="1">
          <li>
            Vypsat abecedně oznámené TV seriály vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané oznámené TV seriály</li>
              <li>
                Vypsat detail vybraného oznámeného TV seriálu
                <ol type="1">
                  <li>Smazat TV seriál</li>
                  <li>
                    Upravit TV seriál
                    <ol type="1">
                      <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat vydané TV seriály v jednotlivých érách
        <ol type="1">
          <li>
            Vypsat abecedně vydané TV seriály vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané vydané TV seriály</li>
              <li>
                Vypsat detail vybraného vydaného TV seriálu
                <ol type="1">
                  <li>Smazat TV seriál</li>
                  <li>
                    Upravit TV seriál
                    <ol type="1">
                      <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru input_tvSeasons.txt</li>
                          <li>Načíst z binárního souboru input_tvSeasons.bin</li>
                          <li>Vypsat obsah textového souboru input_tvSeasons.txt</li>
                          <li>Vypsat obsah binárního souboru input_tvSeasons.bin</li>
                        </ol>
                      </li>
                      <li>Smazat aktuálně vypsané TV sezóny</li>
                      <li>
                        Poslat e-mailem TV epizody
                        <ol type="1">
                          <li>Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejdelší TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané TV epizody</li>
                          <li>Vypsat detail vybrané TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejoblíbenější TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                          <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat detail vybrané TV sezóny
                        <ol type="1">
                          <li>Smazat TV sezónu</li>
                          <li>
                            Upravit TV sezónu
                            <ol type="1">
                              <li>Upravit TV sezónu pomocí vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru input_tvSeasons.bin</li>
                              <li>Vypsat obsah vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Vypsat obsah vstupního binárního souboru input_tvSeasons.bin</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru input_tvEpisodes.txt</li>
                                  <li>Načíst z binárního souboru input_tvEpisodes.bin</li>
                                  <li>Vypsat obsah textového souboru input_tvEpisodes.txt</li>
                                  <li>Vypsat obsah binárního souboru input_tvEpisodes.bin</li>
                                </ol>
                              </li>
                              <li>Smazat aktuálně vypsané TV epizody</li>
                              <li>
                                Vypsat nejdelší TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané TV epizody</li>
                                  <li>Vypsat detail vybrané TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat nejoblíbenější TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                                  <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat detail vybrané TV epizody
                                <ol type="1">
                                  <li>Smazat TV epizodu</li>
                                  <li>
                                    Upravit TV epizodu
                                    <ol type="1">
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru input_tvEpisodes.bin</li>
                                      <li>Vypsat obsah vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Vypsat obsah vstupního binárního souboru input_tvEpisodes.bin</li>
                                    </ol>
                                  </li>
                                  <li>Ohodnotit TV epizodu</li>
                                </ol>
                              </li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejnovější vydané TV seriály vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané vydané TV seriály</li>
              <li>
                Vypsat detail vybraného vydaného TV seriálu
                <ol type="1">
                  <li>Smazat TV seriál</li>
                  <li>
                    Upravit TV seriál
                    <ol type="1">
                      <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru input_tvSeasons.txt</li>
                          <li>Načíst z binárního souboru input_tvSeasons.bin</li>
                          <li>Vypsat obsah textového souboru input_tvSeasons.txt</li>
                          <li>Vypsat obsah binárního souboru input_tvSeasons.bin</li>
                        </ol>
                      </li>
                      <li>Smazat aktuálně vypsané TV sezóny</li>
                      <li>
                        Poslat e-mailem TV epizody
                        <ol type="1">
                          <li>Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejdelší TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané TV epizody</li>
                          <li>Vypsat detail vybrané TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejoblíbenější TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                          <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat detail vybrané TV sezóny
                        <ol type="1">
                          <li>Smazat TV sezónu</li>
                          <li>
                            Upravit TV sezónu
                            <ol type="1">
                              <li>Upravit TV sezónu pomocí vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru input_tvSeasons.bin</li>
                              <li>Vypsat obsah vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Vypsat obsah vstupního binárního souboru input_tvSeasons.bin</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru input_tvEpisodes.txt</li>
                                  <li>Načíst z binárního souboru input_tvEpisodes.bin</li>
                                  <li>Vypsat obsah textového souboru input_tvEpisodes.txt</li>
                                  <li>Vypsat obsah binárního souboru input_tvEpisodes.bin</li>
                                </ol>
                              </li>
                              <li>Smazat aktuálně vypsané TV epizody</li>
                              <li>
                                Vypsat nejdelší TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané TV epizody</li>
                                  <li>Vypsat detail vybrané TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat nejoblíbenější TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                                  <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat detail vybrané TV epizody
                                <ol type="1">
                                  <li>Smazat TV epizodu</li>
                                  <li>
                                    Upravit TV epizodu
                                    <ol type="1">
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru input_tvEpisodes.bin</li>
                                      <li>Vypsat obsah vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Vypsat obsah vstupního binárního souboru input_tvEpisodes.bin</li>
                                    </ol>
                                  </li>
                                  <li>Ohodnotit TV epizodu</li>
                                </ol>
                              </li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
          <li>
            Vypsat nejdelší vydané TV seriály vybrané éry
            <ol type="1">
              <li>Smazat aktuálně vypsané vydané TV seriály</li>
              <li>
                Vypsat detail vybraného vydaného TV seriálu
                <ol type="1">
                  <li>Smazat TV seriál</li>
                  <li>
                    Upravit TV seriál
                    <ol type="1">
                      <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                      <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                      <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru input_tvSeasons.txt</li>
                          <li>Načíst z binárního souboru input_tvSeasons.bin</li>
                          <li>Vypsat obsah textového souboru input_tvSeasons.txt</li>
                          <li>Vypsat obsah binárního souboru input_tvSeasons.bin</li>
                        </ol>
                      </li>
                      <li>Smazat aktuálně vypsané TV sezóny</li>
                      <li>
                        Poslat e-mailem TV epizody
                        <ol type="1">
                          <li>Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejdelší TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané TV epizody</li>
                          <li>Vypsat detail vybrané TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat nejoblíbenější TV epizody tohoto seriálu
                        <ol type="1">
                          <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                          <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat detail vybrané TV sezóny
                        <ol type="1">
                          <li>Smazat TV sezónu</li>
                          <li>
                            Upravit TV sezónu
                            <ol type="1">
                              <li>Upravit TV sezónu pomocí vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru input_tvSeasons.bin</li>
                              <li>Vypsat obsah vstupního textového souboru input_tvSeasons.txt</li>
                              <li>Vypsat obsah vstupního binárního souboru input_tvSeasons.bin</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru input_tvEpisodes.txt</li>
                                  <li>Načíst z binárního souboru input_tvEpisodes.bin</li>
                                  <li>Vypsat obsah textového souboru input_tvEpisodes.txt</li>
                                  <li>Vypsat obsah binárního souboru input_tvEpisodes.bin</li>
                                </ol>
                              </li>
                              <li>Smazat aktuálně vypsané TV epizody</li>
                              <li>
                                Vypsat nejdelší TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané TV epizody</li>
                                  <li>Vypsat detail vybrané TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat nejoblíbenější TV epizody této sezóny
                                <ol type="1">
                                  <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                                  <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                                </ol>
                              </li>
                              <li>
                                Vypsat detail vybrané TV epizody
                                <ol type="1">
                                  <li>Smazat TV epizodu</li>
                                  <li>
                                    Upravit TV epizodu
                                    <ol type="1">
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru input_tvEpisodes.bin</li>
                                      <li>Vypsat obsah vstupního textového souboru input_tvEpisodes.txt</li>
                                      <li>Vypsat obsah vstupního binárního souboru input_tvEpisodes.bin</li>
                                    </ol>
                                  </li>
                                  <li>Ohodnotit TV epizodu</li>
                                </ol>
                              </li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat nejnovější již vydané TV seriály
        <ol type="1">
          <li>Smazat aktuálně vypsané nejnovější již vydané TV seriály</li>
          <li>
            Vypsat detail vybraného vydaného TV seriálu
            <ol type="1">
              <li>Smazat TV seriál</li>
              <li>
                Upravit TV seriál
                <ol type="1">
                  <li>Upravit TV seriál pomocí vstupního textového souboru input_tvShows.txt</li>
                  <li>Upravit TV seriál pomocí vstupního binárního souboru input_tvShows.bin</li>
                  <li>Vypsat obsah vstupního textového souboru input_tvShows.txt</li>
                  <li>Vypsat obsah vstupního binárního souboru input_tvShows.bin</li>
                </ol>
              </li>
              <li>
                Vypsat TV sezóny
                <ol type="1">
                  <li>
                    Přidat TV sezóny ze vstupního souboru
                    <ol type="1">
                      <li>Načíst z textového souboru input_tvSeasons.txt</li>
                      <li>Načíst z binárního souboru input_tvSeasons.bin</li>
                      <li>Vypsat obsah textového souboru input_tvSeasons.txt</li>
                      <li>Vypsat obsah binárního souboru input_tvSeasons.bin</li>
                    </ol>
                  </li>
                  <li>Smazat aktuálně vypsané TV sezóny</li>
                  <li>
                    Poslat e-mailem TV epizody
                    <ol type="1">
                      <li>Poslat e-mailem nezhlédnuté TV epizody vybraného seriálu</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat nejdelší TV epizody tohoto seriálu
                    <ol type="1">
                      <li>Smazat aktuálně vypsané TV epizody</li>
                      <li>Vypsat detail vybrané TV epizody</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat nejoblíbenější TV epizody tohoto seriálu
                    <ol type="1">
                      <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                      <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat detail vybrané TV sezóny
                    <ol type="1">
                      <li>Smazat TV sezónu</li>
                      <li>
                        Upravit TV sezónu
                        <ol type="1">
                          <li>Upravit TV sezónu pomocí vstupního textového souboru input_tvSeasons.txt</li>
                          <li>Upravit TV sezónu pomocí vstupního binárního souboru input_tvSeasons.bin</li>
                          <li>Vypsat obsah vstupního textového souboru input_tvSeasons.txt</li>
                          <li>Vypsat obsah vstupního binárního souboru input_tvSeasons.bin</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat TV epizody
                        <ol type="1">
                          <li>
                            Přidat TV epizody ze vstupního souboru
                            <ol type="1">
                              <li>Načíst z textového souboru input_tvEpisodes.txt</li>
                              <li>Načíst z binárního souboru input_tvEpisodes.bin</li>
                              <li>Vypsat obsah textového souboru input_tvEpisodes.txt</li>
                              <li>Vypsat obsah binárního souboru input_tvEpisodes.bin</li>
                            </ol>
                          </li>
                          <li>Smazat aktuálně vypsané TV epizody</li>
                          <li>
                            Vypsat nejdelší TV epizody této sezóny
                            <ol type="1">
                              <li>Smazat aktuálně vypsané TV epizody</li>
                              <li>Vypsat detail vybrané TV epizody</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat nejoblíbenější TV epizody této sezóny
                            <ol type="1">
                              <li>Smazat aktuálně vypsané zhlédnuté TV epizody</li>
                              <li>Vypsat detail vybrané zhlédnuté TV epizody</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat detail vybrané TV epizody
                            <ol type="1">
                              <li>Smazat TV epizodu</li>
                              <li>
                                Upravit TV epizodu
                                <ol type="1">
                                  <li>Upravit TV epizodu pomocí vstupního textového souboru input_tvEpisodes.txt</li>
                                  <li>Upravit TV epizodu pomocí vstupního binárního souboru input_tvEpisodes.bin</li>
                                  <li>Vypsat obsah vstupního textového souboru input_tvEpisodes.txt</li>
                                  <li>Vypsat obsah vstupního binárního souboru input_tvEpisodes.bin</li>
                                </ol>
                              </li>
                              <li>Ohodnotit TV epizodu</li>
                            </ol>
                          </li>
                        </ol>
                      </li>
                    </ol>
                  </li>
                </ol>
              </li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat obsahy výstupních souborů TV epizod
        <ol type="1">
          <li>Vypsat obsah textového souboru output_tvEpisodes.txt</li>
          <li>Vypsat obsah binárního souboru output_tvEpisodes.bin</li>
        </ol>
      </li>
      <li>
        Vypsat obsahy výstupních souborů TV sezón
        <ol type="1">
          <li>Vypsat obsah textového souboru output_tvSeasons.txt</li>
          <li>Vypsat obsah binárního souboru output_tvSeasons.bin</li>
        </ol>
      </li>
      <li>
        Vypsat obsahy výstupních souborů TV seriálů
        <ol type="1">
          <li>Vypsat obsah textového souboru output_tvShows.txt</li>
          <li>Vypsat obsah binárního souboru output_tvShows.bin</li>
        </ol>
      </li>
    </ol>
  </li>
</ol>

---


## Popis struktury vstupních a výstupních souborů

## Class diagram

# Testování

# Popis fungování externí knihovny
