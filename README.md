
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
- Organizovat evidovaný obsah do chronologických období v rámci Star Wars univerza
- Umožnit hodnotit zhlédnutý obsah, pro účely opakovaného zhlédnutí
- Poskytovat souhrnné/statistické údaje na základě evidovaného obsahu

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

- Zobrazuje se po [menu nastavování adresáře data](#menu-nastavování-adresáře-data), potom již ne
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

- Zobrazuje se po [menu načítání výstupních souborů](#menu-načítání-výstupních-souborů) a opakovaně během běhu aplikace
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
              <li>Ohodnotit film <strong><i>V tomto případě je uživatelská funkce dostupná pouze pro vydaný film</i></strong></li>
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
                Vypsat TV sezóny <strong><i>V tomto případě je uživatelská funkce dostupná pouze pro vydaný TV seriál</i></strong>
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

Pro získávání jednotlivých vstupních dat je možné použít tuto databázi mediálního obsahu https://www.imdb.com/

### Vstupní textový soubor s filmy

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových filmů do databáze nebo editaci/úpravu již existujícího filmu v databázi

#### Požadavky

- Název souboru musí být **input_movies.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho vstupního filmu** vypadají takto:

```java
public class MovieInput
{
    private final long runtimeInSeconds;
    
    private final String name;
    
    private final int percentageRating;
    
    private final String hyperlinkForContentWatch;
    
    private final String shortContentSummary;
    
    private final long releaseDateInEpochSeconds;
    
    private final String eraCodeDesignation;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***runtimeInSeconds*** - Vyjadřuje délku/trvání filmu v sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo**
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být rovno nebo menší než 0**
            - Pokud **je zadán**, hodnota **musí být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků jména je **60**
    - ***percentageRating*** - Vyjadřuje procentuální hodnocení filmu
        - Jedná se o datový typ **int**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být menší než 0**
                - Při nezadání se film identifikuje jako **nezhlédnutý**
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 až 100**
                - Při zadání se film identifikuje jako **zhlédnutý** 
    - ***hyperlinkForContentWatch*** - Vyjadřuje URL odkaz ke zhlédnutí filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků URL odkazu je **180**
    - ***shortContentSummary*** - Vyjadřuje krátké shrnutí obsahu filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků shrnutí je **1000**
    - ***releaseDateInEpochSeconds*** - Vyjadřuje datum vydání/uvedení filmu, vyjádřeného v epoch sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být menší než 0**
                - Při nezadání se film identifikuje jako **oznámený** 
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 a více**
                - Při zadání se film identifikuje jako **vydaný** 
        - Pro převod datumu na epoch sekundy a opačně je možné použít tento konverter https://www.epochconverter.com/
            - Při převodu je požadováno zvolit jako časovou zónu **GMT/UTC**
    - ***eraCodeDesignation*** - Vyjadřuje kódové označení vybrané chronologické star wars éry pro daný film
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Hodnota **musí nabývat** jednoho z těchto kódových označení:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - Chronologické éry jsou více popsaný přímo **ve formě uživatelské funkce v aplikaci** nebo na těchto webových stránkách https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- Není možné, aby existovaly dva filmy, které mají **stejný název a zároveň stejné datum vydání**
- Není možné, aby existovaly dva filmy, které mají **stejný URL odkaz ke zhlédnutí nebo stejné shrnutí obsahu**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jeden vstupní film**:

```
[Attributes]

Order: 1

runtimeInSeconds 1
name 2
percentageRating 3
hyperlinkForContentWatch 4
shortContentSummary 5
releaseDateInEpochSeconds 6
eraCodeDesignation 7

[Values]

7860 1
Star Wars: Episode VI - Return of the Jedi 2
88 3
https://film.kukaj.io/star-wars-epizoda-vi-navrat-jediho-1983 4
As the evil Emperor Palpatine oversees the construction of the new Death Star by Darth Vader and the Galactic Empire, 5
smuggler Han Solo is rescued from the clutches of the vile gangster Jabba the Hutt by his friends, Luke Skywalker, 5
Princess Leia, Lando Calrissian, and Chewbacca. 5
 5
Leaving Luke Skywalker Jedi training with Master Yoda, 5
Solo returns to the Rebel fleet to prepare to complete his battle with the Empire. During the ensuing fighting, the newly returned Luke Skywalker is captured by Darth Vader. 5
422496000 6
AGE_OF_REBELLION 7

[End]
```

- V souboru může být **více než jeden vstupní film**
    - Stačí **za sekci *\[Values\]* předcházejícího filmu** umístit **zase sekci *\[Attributes\]* a pak zase sekci *\[Values\]* následujícího filmu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Order:*** - Vyjadřuje pořadí filmu z hlediska umístění v souboru
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní data filmu
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru)
    - Hodnotu s propojovacím číslem je možné zapsat na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
        - Vyjímkou je hodnota atributu/data ***shortContentSummary***, kdy po přečtení souboru bude hodnota **z více řádků spojena opět do více řádků**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
        - Tento mechanismus je možné použít při **editaci/úpravě** dat nějakého existujícího filmu, kdy v souboru může být třeba 20 filmů a znak ***\[End\]*** se umístí mezi 1. a 2. film, takže dojde k přečtení pouze 1. filmu, zbytek se bude ignorovat

### Vstupní binární soubor s filmy

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových filmů do databáze nebo editaci/úpravu již existujícího filmu v databázi

#### Požadavky

- Název souboru musí být **input_movies.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s filmy](#vstupní-textový-soubor-s-filmy)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního textového souboru s filmy](#popis-struktury-dat-souboru)

#### Popis struktury souboru

- Struktura je úplně totožná jako u [popisu struktury vstupního textového souboru s filmy](#popis-struktury-souboru)

### Výstupní textový soubor s filmy

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných filmů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_movies.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho výstupního filmu** vypadají takto:

```java
public class MovieOutput
{
    private final int id;
    
    private final long runtimeInSeconds;
    
    private final char[] name;
    
    private final int percentageRating;
    
    private final char[] hyperlinkForContentWatch;
    
    private final char[] shortContentSummary;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] eraCodeDesignation;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-2)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***id*** - Vyjadřuje identifikátor filmu v rámci databáze
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***runtimeInSeconds*** - Vyjadřuje délku/trvání filmu v sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo**
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být rovno nebo menší než 0**
            - Pokud **byl zadán**, hodnota **by měla být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název filmu
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
    - ***percentageRating*** - Vyjadřuje procentuální hodnocení filmu
        - Jedná se o datový typ **int**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být menší než 0**
                - Film se identifikuje jako **nezhlédnutý**
            - Pokud **byl zadán**, hodnota **by měla být v rozsahu 0 až 100**
                - Film se identifikuje jako **zhlédnutý** 
    - ***hyperlinkForContentWatch*** - Vyjadřuje URL odkaz ke zhlédnutí filmu
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
    - ***shortContentSummary*** - Vyjadřuje krátké shrnutí obsahu filmu
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
    - ***releaseDateInEpochSeconds*** - Vyjadřuje datum vydání/uvedení filmu, vyjádřeného v epoch sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být menší než 0**
                - Film se identifikuje jako **oznámený** 
            - Pokud **byl zadán**, hodnota **by měla být v rozsahu 0 a více**
                - Film se identifikuje jako **vydaný** 
        - Pro převod datumu na epoch sekundy a opačně je možné použít tento konverter https://www.epochconverter.com/
            - Při převodu je požadováno zvolit jako časovou zónu **GMT/UTC**
    - ***eraCodeDesignation*** - Vyjadřuje kódové označení vybrané chronologické star wars éry pro daný film
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Hodnota **by měla nabývat** jednoho z těchto kódových označení:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - Chronologické éry jsou více popsaný přímo **ve formě uživatelské funkce v aplikaci** nebo na těchto webových stránkách https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- Není možné, aby existovaly dva filmy, které mají **stejný název a zároveň stejné datum vydání**
- Není možné, aby existovaly dva filmy, které mají **stejný URL odkaz ke zhlédnutí nebo stejné shrnutí obsahu**
- Není možné, aby existovaly dva filmy, které mají **stejný identifikátor**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jeden výstupní film**:

```
[Attributes]

Identificator: 2442332

id 1
runtimeInSeconds 2
name 3
percentageRating 4
hyperlinkForContentWatch 5
shortContentSummary 6
releaseDateInEpochSeconds 7
eraCodeDesignation 8

[Values]

2442332 1
7860 2
Star Wars: Episode VI - Return of the Jedi 3
88 4
https://film.kukaj.io/star-wars-epizoda-vi-navrat-jediho-1983 5
As the evil Emperor Palpatine oversees the construction of the new Death Star by Darth Vader and the Galactic Empire, 6
smuggler Han Solo is rescued from the clutches of the vile gangster Jabba the Hutt by his friends, Luke Skywalker, 6
Princess Leia, Lando Calrissian, and Chewbacca. 6
 6
Leaving Luke Skywalker Jedi training with Master Yoda, 6
Solo returns to the Rebel fleet to prepare to complete his battle with the Empire. During the ensuing fighting, the newly returned Luke Skywalker is captured by Darth Vader. 6
422496000 7
AGE_OF_REBELLION 8

[End]
```

- V souboru může být **více než jeden výstupní film**
    - **Za sekcí *\[Values\]* předcházejícího filmu** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následujícího filmu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor filmu v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují výstupní data filmu
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-2)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-2)
    - Hodnota s propojovacím číslem může být zapsána na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
        - Vyjímkou je hodnota atributu/data ***shortContentSummary***, kdy po přečtení souboru bude hodnota **z více řádků spojena opět do více řádků**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- Filmy v souboru jsou **řazeny vzestupně na základě identifikátoru**

### Výstupní binární soubor s filmy

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných filmů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_movies.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat výstupního textového souboru s filmy](#popis-struktury-dat-souboru-2)
- Aby bylo možné číst textové/řetězcové hodnoty atributů filmu z binárního souboru, tak se všem takovým hodnotám nastaví před zapisováním do souboru pevná délka:
    -  ***name*** - 60 znaků
    -  ***hyperlinkForContentWatch*** - 180 znaků
    -  ***shortContentSummary*** - 1000 znaků
    -  ***eraCodeDesignation*** - 60 znaků

#### Popis struktury souboru

- Jednotlivá data pro každý film jsou zapsána a čtena v následujícím pořadí:

---

1. *id*
2. *runtimeInSeconds*
3. *name*
4. *percentageRating*
5. *hyperlinkForContentWatch*
6. *shortContentSummary*
7. *releaseDateInEpochSeconds*
8. *eraCodeDesignation*

---

- Textové/řetězcové hodnoty atributů filmu jsou zapsány po jednotlivých znacích
- Toto pořadí se v případě vícero filmů bude opakovat

### Vstupní textový soubor s TV seriály

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV seriálů do databáze nebo editaci/úpravu již existujícího TV seriálu v databázi

#### Požadavky

- Název souboru musí být **input_tvShows.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho vstupního TV seriálu** vypadají takto:

```java
public class TVShowInput 
{
    private final String name;
    
    private final long releaseDateInEpochSeconds;
    
    private final String eraCodeDesignation;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-4)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***name*** - Vyjadřuje název TV seriálu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků jména je **60**
    - ***releaseDateInEpochSeconds*** - Vyjadřuje datum vydání/uvedení TV seriálu, vyjádřeného v epoch sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být menší než 0**
                - Při nezadání se TV seriál identifikuje jako **oznámený** 
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 a více**
                - Při zadání se TV seriál identifikuje jako **vydaný** 
        - Pro převod datumu na epoch sekundy a opačně je možné použít tento konverter https://www.epochconverter.com/
            - Při převodu je požadováno zvolit jako časovou zónu **GMT/UTC**
    - ***eraCodeDesignation*** - Vyjadřuje kódové označení vybrané chronologické star wars éry pro daný TV seriál
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Hodnota **musí nabývat** jednoho z těchto kódových označení:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - Chronologické éry jsou více popsaný přímo **ve formě uživatelské funkce v aplikaci** nebo na těchto webových stránkách https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- Není možné, aby existovaly dva TV seriály, které mají **stejný název a zároveň stejné datum vydání**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jeden vstupní TV seriál**:

```
[Attributes]

Order: 1

name 1
releaseDateInEpochSeconds 2
eraCodeDesignation 3

[Values]

Star Wars: Tales of the Jedi 1
1666742400 2
FALL_OF_THE_JEDI 3

[End]
```

- V souboru může být **více než jeden vstupní TV seriál**
    - Stačí **za sekci *\[Values\]* předcházejícího TV seriálu** umístit **zase sekci *\[Attributes\]* a pak zase sekci *\[Values\]* následujícího TV seriálu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Order:*** - Vyjadřuje pořadí TV seriálu z hlediska umístění v souboru
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní data TV seriálu
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-4)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-4)
    - Hodnotu s propojovacím číslem je možné zapsat na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
        - Tento mechanismus je možné použít při **editaci/úpravě** dat nějakého existujícího TV seriálu, kdy v souboru může být třeba 20 TV seriálů a znak ***\[End\]*** se umístí mezi 1. a 2. TV seriál, takže dojde k přečtení pouze 1. TV seriálu, zbytek se bude ignorovat

### Vstupní binární soubor s TV seriály

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV seriálů do databáze nebo editaci/úpravu již existujícího TV seriálu v databázi

#### Požadavky

- Název souboru musí být **input_tvShows.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV seriály](#vstupní-textový-soubor-s-tv-seriály)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html
 
#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního textového souboru s TV seriály](#popis-struktury-dat-souboru-4)

#### Popis struktury souboru

- Struktura je úplně totožná jako u [popisu struktury vstupního textového souboru s TV seriály](#popis-struktury-souboru-4)

### Výstupní textový soubor s TV seriály

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvShows.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho výstupního TV seriálu** vypadají takto:

```java
public class TVShowOutput
{
    private final int id;
    
    private final char[] name;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] eraCodeDesignation;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-6)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***id*** - Vyjadřuje identifikátor TV seriálu v rámci databáze
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název TV seriálu
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
    - ***releaseDateInEpochSeconds*** - Vyjadřuje datum vydání/uvedení TV seriálu, vyjádřeného v epoch sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být menší než 0**
                - TV seriál se identifikuje jako **oznámený** 
            - Pokud **byl zadán**, hodnota **by měla být v rozsahu 0 a více**
                - TV seriál se identifikuje jako **vydaný** 
        - Pro převod datumu na epoch sekundy a opačně je možné použít tento konverter https://www.epochconverter.com/
            - Při převodu je požadováno zvolit jako časovou zónu **GMT/UTC**
    - ***eraCodeDesignation*** - Vyjadřuje kódové označení vybrané chronologické star wars éry pro daný TV seriál
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Je povinný**
            - Není akceptovatelné mít hodnotu **prázdnou ("") nebo vyplněnou prázdnými mezerami nebo chybějící v souboru**
        - Hodnota **by měla nabývat** jednoho z těchto kódových označení:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - Chronologické éry jsou více popsaný přímo **ve formě uživatelské funkce v aplikaci** nebo na těchto webových stránkách https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- Není možné, aby existovaly dva TV seriály, které mají **stejný název a zároveň stejné datum vydání**
- Není možné, aby existovaly dva TV seriály, které mají **stejný identifikátor**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jeden výstupní TV seriál**:

```
[Attributes]

Identificator: 23581

id 1
name 2
releaseDateInEpochSeconds 3
eraCodeDesignation 4

[Values]

23581 1
Star Wars: Tales of the Jedi 2
1666742400 3
FALL_OF_THE_JEDI 4

[End]
```

- V souboru může být **více než jeden výstupní TV seriál**
    - **Za sekcí *\[Values\]* předcházejícího TV seriálu** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následujícího TV seriálu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV seriálu v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují výstupní data TV seriálu
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-6)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-6)
    - Hodnota s propojovacím číslem může být zapsána na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV seriály v souboru jsou **řazeny vzestupně na základě identifikátoru**

### Výstupní binární soubor s TV seriály

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvShows.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat výstupního textového souboru s TV seriály](#popis-struktury-dat-souboru-6)
- Aby bylo možné číst textové/řetězcové hodnoty atributů TV seriálu z binárního souboru, tak se všem takovým hodnotám nastaví před zapisováním do souboru pevná délka:
    -  ***name*** - 60 znaků
    -  ***eraCodeDesignation*** - 60 znaků

#### Popis struktury souboru

- Jednotlivá data pro každý TV seriál jsou zapsána a čtena v následujícím pořadí:

---

1. *id*
2. *name*
3. *releaseDateInEpochSeconds*
4. *eraCodeDesignation*

---

- Textové/řetězcové hodnoty atributů TV seriálu jsou zapsány po jednotlivých znacích
- Toto pořadí se v případě vícero TV seriálů bude opakovat

### Vstupní textový soubor s TV sezónami

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV sezón pro vybraný TV seriál do databáze nebo editaci/úpravu již existující TV sezóny v databázi

#### Požadavky

- Název souboru musí být **input_tvSeasons.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné vstupní TV sezóny** vypadají takto:

```java
public class TVSeasonInput 
{    
    private final int orderInTVShow;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-8)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***orderInTVShow*** - Vyjadřuje pořadí TV sezóny v rámci příslušného TV seriálu
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **musí být v rozsahu 1 a více**
- Není možné, aby existovaly dvě TV sezóny v rámci stejného TV seriálu, které mají **stejné pořadí**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jednu vstupní TV sezónu**:

```
[Attributes]

Order: 1

orderInTVShow 1

[Values]

1 1

[End]
```

- V souboru může být **více než jedna vstupní TV sezóna**
    - Stačí **za sekci *\[Values\]* předcházející TV sezóny** umístit **zase sekci *\[Attributes\]* a pak zase sekci *\[Values\]* následující TV sezóny**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Order:*** - Vyjadřuje pořadí TV sezóny z hlediska umístění v souboru
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní data TV sezóny
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-8)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-8)
    - Hodnotu s propojovacím číslem je možné zapsat na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
        - Tento mechanismus je možné použít při **editaci/úpravě** dat nějaké existující TV sezóny, kdy v souboru může být třeba 20 TV sezón a znak ***\[End\]*** se umístí mezi 1. a 2. TV sezónu, takže dojde k přečtení pouze 1. TV sezóny, zbytek se bude ignorovat

### Vstupní binární soubor s TV sezónami

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV sezón pro vybraný TV seriál do databáze nebo editaci/úpravu již existující TV sezóny v databázi

#### Požadavky

- Název souboru musí být **input_tvSeasons.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV sezónami](#vstupní-textový-soubor-s-tv-sezónami)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního textového souboru s TV sezónami](#popis-struktury-dat-souboru-8)

#### Popis struktury souboru

- Struktura je úplně totožná jako u [popisu struktury vstupního textového souboru s TV sezónami](#popis-struktury-souboru-8)

### Výstupní textový soubor s TV sezónami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV sezón z různých TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvSeasons.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné výstupní TV sezóny** vypadají takto:

```java
public class TVSeasonOutput 
{    
    private final int id;
    
    private final int orderInTVShow;
    
    private final int tvShowId;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-10)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***id*** - Vyjadřuje identifikátor TV sezóny v rámci databáze
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***orderInTVShow*** - Vyjadřuje pořadí TV sezóny v rámci příslušného TV seriálu
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***tvShowId*** - Vyjadřuje identifikátor příslušného TV seriálu pro danou TV sezónu
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
- Není možné, aby existovaly dvě TV sezóny v rámci stejného TV seriálu, které mají **stejné pořadí**
- Není možné, aby existovaly dvě TV sezóny, které mají **stejný identifikátor**
- Není možné, aby existovala TV sezóna, jejíž **identifikátor TV seriálu neodkazuje na žádný existující TV seriál**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jednu výstupní TV sezónu**:

```
[Attributes]

Identificator: 23142

id 1
orderInTVShow 2
tvShowId 3

[Values]

23142 1
1 1
213 3

[End]
```

- V souboru může být **více než jedna výstupní TV sezóna**
    - **Za sekcí *\[Values\]* předcházející TV sezóny** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následující TV sezóny**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV sezóny v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují výstupní data TV sezóny
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-10)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-10)
    - Hodnota s propojovacím číslem může být zapsána na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV sezóny v souboru jsou **řazeny vzestupně na základě identifikátoru TV sezóny**

### Výstupní binární soubor s TV sezónami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV sezón z různých TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvSeasons.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat výstupního textového souboru s TV sezónami](#popis-struktury-dat-souboru-10)

#### Popis struktury souboru

- Jednotlivá data pro každou TV sezónu jsou zapsána a čtena v následujícím pořadí:

---

1. *id*
2. *orderInTVShow*
3. *tvShowId*

---

- Toto pořadí se v případě vícero TV sezón bude opakovat

### Vstupní textový soubor s TV epizodami

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV epizod pro vybranou TV sezónu do databáze nebo editaci/úpravu již existující TV epizody v databázi

#### Požadavky

- Název souboru musí být **input_tvEpisodes.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné vstupní TV epizody** vypadají takto:

```java
public class TVEpisodeInput
{
    private final long runtimeInSeconds;
    
    private final String name;
    
    private final int percentageRating;
    
    private final String hyperlinkForContentWatch;
    
    private final String shortContentSummary;
    
    private final int orderInTVShowSeason;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-12)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***runtimeInSeconds*** - Vyjadřuje délku/trvání TV epizody v sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **musí být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název TV epizody
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků jména je **60**
    - ***percentageRating*** - Vyjadřuje procentuální hodnocení TV epizody
        - Jedná se o datový typ **int**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být menší než 0**
                - Při nezadání se TV epizoda identifikuje jako **nezhlédnutá**
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 až 100**
                - Při zadání se TV epizoda identifikuje jako **zhlédnutá** 
    - ***hyperlinkForContentWatch*** - Vyjadřuje URL odkaz ke zhlédnutí TV epizody
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků URL odkazu je **180**
    - ***shortContentSummary*** - Vyjadřuje krátké shrnutí obsahu TV epizody
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **není zadán**, hodnota **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků shrnutí je **1000**
    - ***orderInTVShowSeason*** - Vyjadřuje pořadí TV epizody v rámci příslušné TV sezóny
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **musí být v rozsahu 1 a více**
- Není možné, aby existovaly dvě TV epizody v rámci stejné TV sezóny, které mají **stejné pořadí**
- Není možné, aby existovaly dvě TV epizody, které mají **stejný URL odkaz ke zhlédnutí nebo stejné shrnutí obsahu**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jednu vstupní TV epizodu**:

```
[Attributes]

Order: 1

runtimeInSeconds 1
name 2
percentageRating 3
hyperlinkForContentWatch 4
shortContentSummary 5
orderInTVShowSeason 6

[Values]

1320 1
Cloak of Darkness 2
93 3
https://serial.kukaj.io/star-wars-klonove-valky/S01E09 4
Luminara Unduli and Ahsoka Tano are transporting the captive Viceroy Nute Gunray to Coruscant to face trial. 5
Count Dooku deploys his apprentice Asajj Ventress to make sure Gunray is either recaptured or killed rather than have him spill Separatist secrets. 5
1 6

[End]
```

- V souboru může být **více než jedna vstupní TV epizoda**
    - Stačí **za sekci *\[Values\]* předcházející TV epizody** umístit **zase sekci *\[Attributes\]* a pak zase sekci *\[Values\]* následující TV epizody**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Order:*** - Vyjadřuje pořadí TV epizody z hlediska umístění v souboru
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní data TV epizody
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-12)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-12)
    - Hodnotu s propojovacím číslem je možné zapsat na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
        - Vyjímkou je hodnota atributu/data ***shortContentSummary***, kdy po přečtení souboru bude hodnota **z více řádků spojena opět do více řádků**
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
        - Tento mechanismus je možné použít při **editaci/úpravě** dat nějaké existující TV epizody, kdy v souboru může být třeba 20 TV epizod a znak ***\[End\]*** se umístí mezi 1. a 2. TV epizodu, takže dojde k přečtení pouze 1. TV epizody, zbytek se bude ignorovat

### Vstupní binární soubor s TV epizodami

- Musí být vytvořen ručně nebo z nějakého jiného externího zdroje
- Používá se na přidávání nových TV epizod pro vybranou TV sezónu do databáze nebo editaci/úpravu již existující TV epizody v databázi

#### Požadavky

- Název souboru musí být **input_tvEpisodes.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV epizodami](#vstupní-textový-soubor-s-tv-epizodami)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního textového souboru s TV epizodami](#popis-struktury-dat-souboru-12)

#### Popis struktury souboru

- Struktura je úplně totožná jako u [popisu struktury vstupního textového souboru s TV epizodami](#popis-struktury-souboru-12)

### Výstupní textový soubor s TV epizodami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV epizod z různých TV sezón pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvEpisodes.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné výstupní TV epizody** vypadají takto:

```java
public class TVEpisodeOutput
{ 
    private final int id;
    
    private final long runtimeInSeconds;
    
    private final char[] name;
    
    private final int percentageRating;
    
    private final char[] hyperlinkForContentWatch;
    
    private final char[] shortContentSummary;
    
    private final int orderInTVShowSeason;
    
    private final int tvSeasonId;
}
```

- Názvy jednotlivých dat/atributů se používají v [popisu struktury souboru](#popis-struktury-souboru-14)
- Popisy jednotlivých názvů dat/atributů jsou následující:
    - ***id*** - Vyjadřuje identifikátor TV epizody v rámci databáze
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***runtimeInSeconds*** - Vyjadřuje délku/trvání TV epizody v sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název TV epizody
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
    - ***percentageRating*** - Vyjadřuje procentuální hodnocení TV epizody
        - Jedná se o datový typ **int**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být menší než 0**
                - TV epizoda se identifikuje jako **nezhlédnutá**
            - Pokud **byl zadán**, hodnota **by měla být v rozsahu 0 až 100**
                - TV epizoda se identifikuje jako **zhlédnutá** 
    - ***hyperlinkForContentWatch*** - Vyjadřuje URL odkaz ke zhlédnutí TV epizody
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
    - ***shortContentSummary*** - Vyjadřuje krátké shrnutí obsahu TV epizody
        - Jedná se o datový typ **char[]**, tedy **text** 
        - **Není povinný**
            - Pokud **nebyl zadán**, hodnota **by měla být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
    - ***orderInTVShowSeason*** - Vyjadřuje pořadí TV epizody v rámci příslušné TV sezóny
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
    - ***tvSeasonId*** - Vyjadřuje identifikátor příslušné TV sezóny pro danou TV epizodu
        - Jedná se o datový typ **int**, tedy **celé číslo**
        - **Je povinný**
        - Hodnota **by měla být v rozsahu 1 a více**
- Není možné, aby existovaly dvě TV epizody v rámci stejné TV sezóny, které mají **stejné pořadí**
- Není možné, aby existovaly dvě TV epizody, které mají **stejný identifikátor**
- Není možné, aby existovaly dvě TV epizody, které mají **stejný URL odkaz ke zhlédnutí nebo stejné shrnutí obsahu**
- Není možné, aby existovala TV epizoda, jejíž **identifikátor TV sezóny neodkazuje na žádnou existující TV sezónu**

#### Popis struktury souboru

- Soubor by měl vypadat nějak takto pro **jednu výstupní TV epizodu**:

```
[Attributes]

Identificator: 6521

id 1
runtimeInSeconds 2
name 3
percentageRating 4
hyperlinkForContentWatch 5
shortContentSummary 6
orderInTVShowSeason 7
tvSeasonId 8

[Values]

6521 1
1320 2
Cloak of Darkness 3
93 4
https://serial.kukaj.io/star-wars-klonove-valky/S01E09 5
Luminara Unduli and Ahsoka Tano are transporting the captive Viceroy Nute Gunray to Coruscant to face trial. 6
Count Dooku deploys his apprentice Asajj Ventress to make sure Gunray is either recaptured or killed rather than have him spill Separatist secrets. 6
1 7
1311 8

[End]
```

- V souboru může být **více než jedna výstupní TV epizoda**
    - **Za sekcí *\[Values\]* předcházející TV epizody** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následující TV epizody**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV epizody v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují výstupní data TV epizody
        - **Nemusí být v souboru**, pouze informační účel
        - Jednotlivé názvy dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-14)
        - Samotné názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]* vyjadřují vzor/předpis, **jak se oddělují hodnoty dat/atributů v sekci *\[Values\]***, tedy:
            - **Název data/atributu** - Řádek souboru začíná hodnotou konkrétního data/atributu
            - **Mezera** - Vyjadřuje oddělovač mezi hodnotou data/atributu a propojovacím číslem
            - **Propojovací číslo** - Vyjadřuje spojení, k jakému konkrétnímu datu/atributu má být přiražena daná hodnota
- ***\[Values\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivý hodnotami dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - Oddělení hodnot dat/atributů s propojovacími čísly se řídí vzorem/předpisem **v sekci *\[Attributes\]***
    - Datové typy hodnot dat/atributů jsou specifikovány v [popisu struktury dat souboru](#popis-struktury-dat-souboru-14)
    - Hodnota s propojovacím číslem může být zapsána na **více řádků**, ale po přečtení souboru bude taková hodnota z **více řádků spojena do jednoho řádku**
        - Vyjímkou je hodnota atributu/data ***shortContentSummary***, kdy po přečtení souboru bude hodnota **z více řádků spojena opět do více řádků** 
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV epizody v souboru jsou **řazeny vzestupně na základě identifikátoru TV epizody**

### Výstupní binární soubor s TV epizodami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV epizod z různých TV sezón pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **output_tvEpisodes.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat výstupního textového souboru s TV epizodami](#popis-struktury-dat-souboru-14)
- Aby bylo možné číst textové/řetězcové hodnoty atributů TV epizody z binárního souboru, tak se všem takovým hodnotám nastaví před zapisováním do souboru pevná délka:
    -  ***name*** - 60 znaků
    -  ***hyperlinkForContentWatch*** - 180 znaků
    -  ***shortContentSummary*** - 1000 znaků

#### Popis struktury souboru

- Jednotlivá data pro každou TV epizodu jsou zapsána a čtena v následujícím pořadí:

---

1. *id*
2. *runtimeInSeconds*
3. *name*
4. *percentageRating*
5. *hyperlinkForContentWatch*
6. *shortContentSummary*
7. *orderInTVShowSeason*
8. *tvSeasonId*

---

- Textové/řetězcové hodnoty atributů TV epizody jsou zapsány po jednotlivých znacích
- Toto pořadí se v případě vícero TV epizod bude opakovat

## Class diagram

# Testování

# Popis fungování externí knihovny
