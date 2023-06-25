
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
            - Pokud **nemá být zadán**, hodnota *runtimeInSeconds* **musí být rovno nebo menší než 0**
            - Pokud **je zadán**, hodnota **musí být v rozsahu 1 a více**
    - ***name*** - Vyjadřuje název filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
        - Maximální počet znaků jména je **60**
    - ***percentageRating*** - Vyjadřuje procentuální hodnocení filmu
        - Jedná se o datový typ **int**, tedy **celé číslo** 
        - **Není povinný**
            - Pokud **nemá být zadán**, hodnota *percentageRating* **musí být menší než 0**
                - Při nezadání se film identifikuje jako **nezhlédnutý**
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 až 100**
                - Při zadání se film identifikuje jako **zhlédnutý** 
    - ***hyperlinkForContentWatch*** - Vyjadřuje URL odkaz ke zhlédnutí filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **nemá být zadán**, hodnota *hyperlinkForContentWatch* **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků URL odkazu je **180**
    - ***shortContentSummary*** - Vyjadřuje krátké shrnutí obsahu filmu
        - Jedná se o datový typ **String**, tedy **text** 
        - **Není povinný**
            - Pokud **nemá být zadán**, hodnota *shortContentSummary* **musí být prázdná ("") nebo vyplněná prázdnými mezerami nebo chybějící v souboru**
        - Maximální počet znaků shrnutí je **1000**
    - ***releaseDateInEpochSeconds*** - Vyjadřuje datum vydání/uvedení filmu, vyjádřeného v epoch sekundách
        - Jedná se o datový typ **long**, tedy **celé číslo** 
        - **Není povinný**,
            - Pokud **nemá být zadán**, hodnota *shortContentSummary* **musí být menší než 0**
                - Při nezadání se film identifikuje jako **oznámený** 
            - Pokud **je zadán**, hodnota **musí být v rozsahu 0 a více**
                - Při zadání se film identifikuje jako **vydaný** 
        - Pro převod datumu na epoch sekundy a opačně je možné použít tento konverter https://www.epochconverter.com/
            - Při převodu je požadováno zvolit jako časovou zónu **GMT/UTC**
    - ***eraCodeDesignation*** - Vyjadřuje kódové označení vybrané chronologické star wars éry pro daný film
        - Jedná se o datový typ **String**, tedy **text** 
        - **Je povinný**
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
- Není možné, aby existovaly dva filmy, které mají **stejný název filmu a zároveň stejné datum vydání**
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
    - Stačí **za sekci *\[Values\]* předcházejícího filmu** umístit **zase sekci *\[Attributes\]* a pak zase sekci *\[Values\]***
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
        - Tento mechanismus je možné použít při **editaci/úpravě** dat nějakého existujícího filmu, kdy v souboru může být třeba 20 filmů a znak ***\[End\]*** se umístí mezi 1. a 2. film, takže dojde k přečtení pouze 1. fimu, zbytek se bude ignorovat

### Vstupní binární soubor s filmy

Požadavky:
- Název souboru musí být **input_movies.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s filmy](#vstupní-textový-soubor-s-filmy)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

### Výstupní textový soubor s filmy

Požadavky:
- Název souboru musí být **output_movies.txt**
- Kódování souboru musí být **UTF-8**

### Výstupní binární soubor s filmy

Požadavky:
- Název souboru musí být **output_movies.bin**

### Vstupní textový soubor s TV seriály

Požadavky:
- Název souboru musí být **input_tvShows.txt**
- Kódování souboru musí být **UTF-8**

### Vstupní binární soubor s TV seriály

Požadavky:
- Název souboru musí být **input_tvShows.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV seriály](#vstupní-textový-soubor-s-tv-seriály)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

### Výstupní textový soubor s TV seriály

Požadavky:
- Název souboru musí být **output_tvShows.txt**
- Kódování souboru musí být **UTF-8**

### Výstupní binární soubor s TV seriály

Požadavky:
- Název souboru musí být **output_tvShows.bin**

### Vstupní textový soubor s TV sezónami

Požadavky:
- Název souboru musí být **input_tvSeasons.txt**
- Kódování souboru musí být **UTF-8**

### Vstupní binární soubor s TV sezónami

Požadavky:
- Název souboru musí být **input_tvSeasons.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV sezónami](#vstupní-textový-soubor-s-tv-sezónami)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

### Výstupní textový soubor s TV sezónami

Požadavky:
- Název souboru musí být **output_tvSeasons.txt**
- Kódování souboru musí být **UTF-8**

### Výstupní binární soubor s TV sezónami

Požadavky:
- Název souboru musí být **output_tvSeasons.bin**

### Vstupní textový soubor s TV epizodami

Požadavky:
- Název souboru musí být **input_tvEpisodes.txt**
- Kódování souboru musí být **UTF-8**

### Vstupní binární soubor s TV epizodami

Požadavky:
- Název souboru musí být **input_tvEpisodes.bin**
- Protože vstupní soubory můžou být z externích zdrojů, je vyžadováno, aby tento soubor vznikl převodem ze [vstupního textového souboru s TV epizodami](#vstupní-textový-soubor-s-tv-epizodami)
    - Při převodu je vyžadováno zvolit kódování jako **UTF-8**
    - Na převod je možné použít tento konverter https://www.rapidtables.com/convert/number/ascii-to-binary.html

### Výstupní textový soubor s TV epizodami

Požadavky:
- Název souboru musí být **output_tvEpisodes.txt**
- Kódování souboru musí být **UTF-8**

### Výstupní binární soubor s TV epizodami

Požadavky:
- Název souboru musí být **output_tvEpisodes.bin**

## Class diagram

# Testování

# Popis fungování externí knihovny
