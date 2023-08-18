
# Zadání práce

## Motivace

Už od 8 let jsem se začal velmi intenzivně zajímat o Star Wars ve všech ohledech.

Začalo to stavěním výhradně LEGO stavebnic pouze ze Star Wars, tyto stavebnice jsem si pak vystavoval po celém pokoji. V současnosti mám vystavených cca. 20 stavebnic, ale určitě jsem postavil víc než 20, jenom nebyl k dispozici prostor pro vystavení, takže jsem nějaké stavebnice po čase musel rozložit a dát do krabice s tříděným LEGEM, umístěným v mém pokoji.

Dále jsem se začal zajímat o seriály, filmy, knížky, hudbu a komiksy. Později, když jsem byl starší, tak jsem začal hrát PC hry, přičemž 95 % všech her tvořily Star Wars hry. Zároveň jsem začal sbírat světelné meče (Hasbro, nezávislí výrobci) různého typu (plastové, s LED zářivkou).

V poslední řadě jsem se zúčastnil s rodinou předpremiér nových Star Wars filmů v letech 2015, 2016, 2017, 2018 a 2019. Atmosféra byla úžasná, jenom po nějaké době jsem si uvědomil, že scénáře filmů z roku 2015, 2017 a 2019 byly příšerné, naopak scénáře filmů z roků 2016 a 2018 byly docela OK.

Tedy shrnutím. Star Wars je můj special interest a přišlo mi jako dobrý nápad využít programování jako nástroj pro vytvoření jakéhosi „pomocníka“ na Star Wars seriály a filmy.

## Popis problému

Aplikace *Star Wars Content Media Management* by měla sloužit jako evidence mediálního obsahu (seriály, filmy) v rámci výhradně Star Wars univerza.

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
    - Adresář obsahuje datové vstupní a vstupní/výstupní soubory specifikované [zde](#popis-struktury-vstupních-a-výstupních-souborů)
- Je umožněno ukončit aplikaci pomocí **funkce s číslem 0**

Samotné uživatelské funkce vypadají následovně:

---

1. Zadat cestu k data adresáři

---

### Menu načítání vstupních/výstupních souborů

- Zobrazuje se po [menu nastavování adresáře data](#menu-nastavování-adresáře-data), potom již ne
- Slouží k načtení existujících/evidovaných dat do aplikace z vstupních/výstupních souborů
- Účelem je to, že aplikace průběžně ukládá svá nová data do vstupních/výstupních souborů, aby se dala při příštím spuštění aplikace snadno obnovit a nevkládat je znova
- Je umožněno ukončit aplikaci pomocí **funkce s číslem 0**

Samotné uživatelské funkce vypadají následovně:

---

1. Načíst z textových souborů (dojde případně k automatickému vytvoření daných souborů)
2. Načíst z binárních souborů (dojde případně k automatickému vytvoření daných souborů)
3. Vypsat obsah textového souboru s filmy (diagnostika chyby při načítání)
4. Vypsat obsah textového souboru s TV seriály (diagnostika chyby při načítání)
5. Vypsat obsah textového souboru s TV sezónami (diagnostika chyby při načítání)
6. Vypsat obsah textového souboru s TV epizodami (ddiagnostika chyby při načítání)
7. Vypsat obsah binárního souboru s filmy (diagnostika chyby při načítání)
8. Vypsat obsah binárního souboru s TV seriály (diagnostika chyby při načítání)
9. Vypsat obsah binárního souboru s TV sezónami (diagnostika chyby při načítání)
10. Vypsat obsah binárního souboru s TV epizodami (diagnostika chyby při načítání)

---

### Hlavní menu

- Zobrazuje se po [menu načítání vstupních/výstupních souborů](#menu-načítání-vstupníchvýstupních-souborů) a opakovaně během běhu aplikace
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
          <li>Načíst z textového souboru</li>
          <li>Načíst z binárního souboru</li>
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
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
                  <li>Upravit film pomocí vstupního textového souboru</li>
                  <li>Upravit film pomocí vstupního binárního souboru</li>
                  <li>Vypsat obsah vstupního textového souboru</li>
                  <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit film pomocí vstupního textového souboru</li>
                      <li>Upravit film pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                  <li>Upravit film pomocí vstupního textového souboru</li>
                  <li>Upravit film pomocí vstupního binárního souboru</li>
                  <li>Vypsat obsah vstupního textového souboru</li>
                  <li>Vypsat obsah vstupního binárního souboru</li>
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
                  <li>Upravit film pomocí vstupního textového souboru</li>
                  <li>Upravit film pomocí vstupního binárního souboru</li>
                  <li>Vypsat obsah vstupního textového souboru</li>
                  <li>Vypsat obsah vstupního binárního souboru</li>
                </ol>
              </li>
              <li>Ohodnotit film</li>
            </ol>
          </li>
        </ol>
      </li>
      <li>
        Vypsat obsahy vstupních/výstupních souborů filmů
        <ol type="1">
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
        </ol>
      </li>
    </ol>
  </li>
  <li>
    Spravovat TV seriály
    <ol type="1">
      <li>
        Přidat TV seriály ze vstupního souboru
        <ol type="1">
          <li>Načíst z textového souboru</li>
          <li>Načíst z binárního souboru</li>
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
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
                  <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                  <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                  <li>Vypsat obsah vstupního textového souboru</li>
                  <li>Vypsat obsah vstupního binárního souboru</li>
                </ol>
              </li>
              <li>
                Vypsat TV sezóny <strong><i>V tomto případě je uživatelská funkce dostupná pouze pro vydaný TV seriál</i></strong>
                <ol type="1">
                  <li>
                    Přidat TV sezóny ze vstupního souboru
                    <ol type="1">
                      <li>Načíst z textového souboru</li>
                      <li>Načíst z binárního souboru</li>
                      <li>Vypsat obsah textového souboru</li>
                      <li>Vypsat obsah binárního souboru</li>
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
                          <li>Upravit TV sezónu pomocí vstupního textového souboru</li>
                          <li>Upravit TV sezónu pomocí vstupního binárního souboru</li>
                          <li>Vypsat obsah vstupního textového souboru</li>
                          <li>Vypsat obsah vstupního binárního souboru</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat TV epizody
                        <ol type="1">
                          <li>
                            Přidat TV epizody ze vstupního souboru
                            <ol type="1">
                              <li>Načíst z textového souboru</li>
                              <li>Načíst z binárního souboru</li>
                              <li>Vypsat obsah textového souboru</li>
                              <li>Vypsat obsah binárního souboru</li>
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
                                  <li>Upravit TV epizodu pomocí vstupního textového souboru</li>
                                  <li>Upravit TV epizodu pomocí vstupního binárního souboru</li>
                                  <li>Vypsat obsah vstupního textového souboru</li>
                                  <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru</li>
                          <li>Načíst z binárního souboru</li>
                          <li>Vypsat obsah textového souboru</li>
                          <li>Vypsat obsah binárního souboru</li>
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
                              <li>Upravit TV sezónu pomocí vstupního textového souboru</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru</li>
                              <li>Vypsat obsah vstupního textového souboru</li>
                              <li>Vypsat obsah vstupního binárního souboru</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru</li>
                                  <li>Načíst z binárního souboru</li>
                                  <li>Vypsat obsah textového souboru</li>
                                  <li>Vypsat obsah binárního souboru</li>
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
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru</li>
                                      <li>Vypsat obsah vstupního textového souboru</li>
                                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru</li>
                          <li>Načíst z binárního souboru</li>
                          <li>Vypsat obsah textového souboru</li>
                          <li>Vypsat obsah binárního souboru</li>
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
                              <li>Upravit TV sezónu pomocí vstupního textového souboru</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru</li>
                              <li>Vypsat obsah vstupního textového souboru</li>
                              <li>Vypsat obsah vstupního binárního souboru</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru</li>
                                  <li>Načíst z binárního souboru</li>
                                  <li>Vypsat obsah textového souboru</li>
                                  <li>Vypsat obsah binárního souboru</li>
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
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru</li>
                                      <li>Vypsat obsah vstupního textového souboru</li>
                                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                      <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                      <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                      <li>Vypsat obsah vstupního textového souboru</li>
                      <li>Vypsat obsah vstupního binárního souboru</li>
                    </ol>
                  </li>
                  <li>
                    Vypsat TV sezóny
                    <ol type="1">
                      <li>
                        Přidat TV sezóny ze vstupního souboru
                        <ol type="1">
                          <li>Načíst z textového souboru</li>
                          <li>Načíst z binárního souboru</li>
                          <li>Vypsat obsah textového souboru</li>
                          <li>Vypsat obsah binárního souboru</li>
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
                              <li>Upravit TV sezónu pomocí vstupního textového souboru</li>
                              <li>Upravit TV sezónu pomocí vstupního binárního souboru</li>
                              <li>Vypsat obsah vstupního textového souboru</li>
                              <li>Vypsat obsah vstupního binárního souboru</li>
                            </ol>
                          </li>
                          <li>
                            Vypsat TV epizody
                            <ol type="1">
                              <li>
                                Přidat TV epizody ze vstupního souboru
                                <ol type="1">
                                  <li>Načíst z textového souboru</li>
                                  <li>Načíst z binárního souboru</li>
                                  <li>Vypsat obsah textového souboru</li>
                                  <li>Vypsat obsah binárního souboru</li>
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
                                      <li>Upravit TV epizodu pomocí vstupního textového souboru</li>
                                      <li>Upravit TV epizodu pomocí vstupního binárního souboru</li>
                                      <li>Vypsat obsah vstupního textového souboru</li>
                                      <li>Vypsat obsah vstupního binárního souboru</li>
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
                  <li>Upravit TV seriál pomocí vstupního textového souboru</li>
                  <li>Upravit TV seriál pomocí vstupního binárního souboru</li>
                  <li>Vypsat obsah vstupního textového souboru</li>
                  <li>Vypsat obsah vstupního binárního souboru</li>
                </ol>
              </li>
              <li>
                Vypsat TV sezóny
                <ol type="1">
                  <li>
                    Přidat TV sezóny ze vstupního souboru
                    <ol type="1">
                      <li>Načíst z textového souboru</li>
                      <li>Načíst z binárního souboru</li>
                      <li>Vypsat obsah textového souboru</li>
                      <li>Vypsat obsah binárního souboru</li>
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
                          <li>Upravit TV sezónu pomocí vstupního textového souboru</li>
                          <li>Upravit TV sezónu pomocí vstupního binárního souboru</li>
                          <li>Vypsat obsah vstupního textového souboru</li>
                          <li>Vypsat obsah vstupního binárního souboru</li>
                        </ol>
                      </li>
                      <li>
                        Vypsat TV epizody
                        <ol type="1">
                          <li>
                            Přidat TV epizody ze vstupního souboru
                            <ol type="1">
                              <li>Načíst z textového souboru</li>
                              <li>Načíst z binárního souboru</li>
                              <li>Vypsat obsah textového souboru</li>
                              <li>Vypsat obsah binárního souboru</li>
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
                                  <li>Upravit TV epizodu pomocí vstupního textového souboru</li>
                                  <li>Upravit TV epizodu pomocí vstupního binárního souboru</li>
                                  <li>Vypsat obsah vstupního textového souboru</li>
                                  <li>Vypsat obsah vstupního binárního souboru</li>
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
        Vypsat obsahy vstupních/výstupních souborů TV epizod
        <ol type="1">
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
        </ol>
      </li>
      <li>
        Vypsat obsahy vstupních/výstupních souborů TV sezón
        <ol type="1">
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
        </ol>
      </li>
      <li>
        Vypsat obsahy vstupních/výstupních souborů TV seriálů
        <ol type="1">
          <li>Vypsat obsah textového souboru</li>
          <li>Vypsat obsah binárního souboru</li>
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

### Vstupní/výstupní textový soubor s filmy

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných filmů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_movies.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho vstupního/výstupního filmu** vypadají takto:

```java
public class MovieInputOutput
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

- Soubor by měl vypadat nějak takto pro **jeden vstupní/výstupní film**:

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

- V souboru může být **více než jeden vstupní/výstupní film**
    - **Za sekcí *\[Values\]* předcházejícího filmu** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následujícího filmu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor filmu v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní/výstupní data filmu
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
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních/výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- Filmy v souboru jsou **řazeny vzestupně na základě identifikátoru**

### Vstupní/výstupní binární soubor s filmy

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných filmů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_movies.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního/výstupního textového souboru s filmy](#popis-struktury-dat-souboru-2)
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

### Vstupní/výstupní textový soubor s TV seriály

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvShows.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jednoho vstupního/výstupního TV seriálu** vypadají takto:

```java
public class TVShowInputOutput
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

- Soubor by měl vypadat nějak takto pro **jeden vstupní/výstupní TV seriál**:

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

- V souboru může být **více než jeden vstupní/výstupní TV seriál**
    - **Za sekcí *\[Values\]* předcházejícího TV seriálu** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následujícího TV seriálu**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV seriálu v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní/výstupní data TV seriálu
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
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních/výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV seriály v souboru jsou **řazeny vzestupně na základě identifikátoru**

### Vstupní/výstupní binární soubor s TV seriály

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvShows.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního/výstupního textového souboru s TV seriály](#popis-struktury-dat-souboru-6)
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

### Vstupní/výstupní textový soubor s TV sezónami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV sezón z různých TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvSeasons.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné vstupní/výstupní TV sezóny** vypadají takto:

```java
public class TVSeasonInputOutput
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

- Soubor by měl vypadat nějak takto pro **jednu vstupní/výstupní TV sezónu**:

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

- V souboru může být **více než jedna vstupní/výstupní TV sezóna**
    - **Za sekcí *\[Values\]* předcházející TV sezóny** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následující TV sezóny**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV sezóny v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní/výstupní data TV sezóny
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
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních/výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV sezóny v souboru jsou **řazeny vzestupně na základě identifikátoru TV sezóny**

### Vstupní/výstupní binární soubor s TV sezónami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV sezón z různých TV seriálů pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvSeasons.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního/výstupního textového souboru s TV sezónami](#popis-struktury-dat-souboru-10)

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

### Vstupní/výstupní textový soubor s TV epizodami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV epizod z různých TV sezón pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvEpisodes.txt**
- Kódování souboru musí být **UTF-8**

#### Popis struktury dat souboru

- Data **jedné vstupní/výstupní TV epizody** vypadají takto:

```java
public class TVEpisodeInputOutput
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

- Soubor by měl vypadat nějak takto pro **jednu vstupní/výstupní TV epizodu**:

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

- V souboru může být **více než jedna vstupní/výstupní TV epizoda**
    - **Za sekcí *\[Values\]* předcházející TV epizody** může být umístěna **zase sekce *\[Attributes\]* a pak zase sekce *\[Values\]* následující TV epizody**
    - ***\[End\]*** zůstane beze změny, tedy v souboru pouze jednou a na konci
- ***\[Attributes\]*** - Vyjadřuje kontrolní znak pro detekci sekce s jednotlivými názvy dat/atributů a propojovacími čísly
    - **Musí být v souboru**
    - ***Identificator:*** - Vyjadřuje identifikátor TV epizody v databázi
        - **Nemusí být v souboru**, pouze informační účel
    - **Jednotlivé názvy dat/atributů s propojovacími čísly v sekci *\[Attributes\]*** vyjadřují vstupní/výstupní data TV epizody
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
- ***\[End\]*** - Vyjadřuje kontrolní znak pro detekci konce čtení vstupních/výstupních dat
    - Pokud bude nějaký text za ***\[End\]***, bude ignorován
- TV epizody v souboru jsou **řazeny vzestupně na základě identifikátoru TV epizody**

### Vstupní/výstupní binární soubor s TV epizodami

- Soubor je vytvořen automaticky aplikací
- Používá se jako uložiště již existujících/evidovaných TV epizod z různých TV sezón pro načtení do databáze při příštím spuštění aplikace

#### Požadavky

- Název souboru musí být **inputOutput_tvEpisodes.bin**

#### Popis struktury dat souboru

- Struktura je úplně totožná jako u [popisu struktury dat vstupního/výstupního textového souboru s TV epizodami](#popis-struktury-dat-souboru-14)
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

- Zde je objektový návrh pro tuto aplikaci
- Objektový návrh byl vytvořen pomocí nástroje *Mermaid*, dostupného na webové stránce https://mermaid.js.org/

```mermaid

---
title: Class diagram aplikace Star Wars Media Content Management
---

classDiagram

direction RL

note for MovieInput "Součást package app.models.input"
note for TVEpisodeInput "Součást package app.models.input"
note for TVSeasonInput "Součást package app.models.input"
note for TVShowInput "Součást package app.models.input"

    class MovieInput{
        -long runtimeInSeconds
        -String name
        -int percentageRating
        -String hyperlinkForContentWatch
        -String shortContentSummary
        -long releaseDateInEpochSeconds
        -String eraCodeDesignation
        +MovieInput(long runtimeInSeconds, String name, int percentageRating, String hyperlinkForContentWatch, String shortContentSummary, long releaseDateInEpochSeconds, String eraCodeDesignation)
        +toString() String
    }
    class TVEpisodeInput{
        -long runtimeInSeconds
        -String name
        -int percentageRating
        -String hyperlinkForContentWatch
        -String shortContentSummary
        -int orderInTVShowSeason
        +TVEpisodeInput(long runtimeInSeconds, String name, int percentageRating, String hyperlinkForContentWatch, String shortContentSummary, int orderInTVShowSeason)
        +toString() String
    }
    class TVSeasonInput{
        -int orderInTVShow
        +TVSeasonInput(int orderInTVShow)
        +toString() String
    }
    class TVShowInput{
        -String name
        -long releaseDateInEpochSeconds
        -String eraCodeDesignation
        +TVShowInput(String name, long releaseDateInEpochSeconds, String eraCodeDesignation)
        +toString() String
    }

note for MovieInputOutput "Součást package app.models.inputoutput"
note for TVEpisodeInputOutput "Součást package app.models.inputoutput"
note for TVSeasonInputOutput "Součást package app.models.inputoutput"
note for TVShowInputOutput "Součást package app.models.inputoutput"

    class MovieInputOutput{
        +int ATTRIBUTE_NAME_LENGTH$
        +int ATTRIBUTE_HYPERLINK_LENGTH$
        +int ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH$
        +int ATTRIBUTE_SUMMARY_LENGTH$
        -int ATTRIBUTE_ID_BYTES$
        -int ATTRIBUTE_RUNTIME_BYTES$
        -int ATTRIBUTE_RATING_BYTES$
        -int ATTRIBUTE_RELEASEDATE_BYTES$
        +int MOVIE_RECORD_SIZE$
        -int id
        -long runtimeInSeconds
        -char[] name
        -int percentageRating
        -char[] hyperlinkForContentWatch
        -char[] shortContentSummary
        -long releaseDateInEpochSeconds
        -char[] eraCodeDesignation
        +MovieInputOutput(int id, long runtimeInSeconds, String name, int percentageRating, String hyperlinkForContentWatch, String shortContentSummary, long releaseDateInEpochSeconds, String eraCodeDesignation)
        +getName() String
        +getHyperlinkForContentWatch() String
        +getShortContentSummary() String
        +getEraCodeDesignation() String
        +toString() String
    }
    class TVEpisodeInputOutput{
        +int ATTRIBUTE_NAME_LENGTH$
        +int ATTRIBUTE_HYPERLINK_LENGTH$
        +int ATTRIBUTE_SUMMARY_LENGTH$
        -int ATTRIBUTE_ID_BYTES$
        -int ATTRIBUTE_RUNTIME_BYTES$
        -int ATTRIBUTE_RATING_BYTES$
        -int ATTRIBUTE_ORDERTVSEASON_BYTES$
        -int ATTRIBUTE_TVSEASONID_BYTES$
        +int TV_EPISODE_RECORD_SIZE$
        -int id
        -long runtimeInSeconds
        -char[] name
        -int percentageRating
        -char[] hyperlinkForContentWatch
        -char[] shortContentSummary
        -int orderInTVShowSeason
        -int tvSeasonId
        +TVEpisodeInputOutput(int id, long runtimeInSeconds, String name, int percentageRating, String hyperlinkForContentWatch, String shortContentSummary, int orderInTVShowSeason, int tvSeasonId)
        +getName() String
        +getHyperlinkForContentWatch() String
        +getShortContentSummary() String
        +toString() String
    }
    class TVSeasonInputOutput{
        -int ATTRIBUTE_ID_BYTES$
        -int ATTRIBUTE_ORDERTVSHOW_BYTES$
        -int ATTRIBUTE_TVSHOWID_BYTES$
        +int TV_SEASON_RECORD_SIZE$
        -int id
        -int orderInTVShow
        -int tvShowId
        +TVSeasonInputOutput(int id, int orderInTVShow, int tvShowId)
        +toString() String
    }
    class TVShowInputOutput{
        +int ATTRIBUTE_NAME_LENGTH$
        +int ATTRIBUTE_ERA_CODE_DESIGNATION_LENGTH$
        -int ATTRIBUTE_ID_BYTES$
        -int ATTRIBUTE_RELEASEDATE_BYTES$
        +int TV_SHOW_RECORD_SIZE$
        -int id
        -char[] name
        -long releaseDateInEpochSeconds
        -char[] eraCodeDesignation
        +TVShowInputOutput(int id, String name, long releaseDateInEpochSeconds, String eraCodeDesignation)
        +getName() String
        +getEraCodeDesignation() String
        +toString() String
    }

note for PrimaryKey "Součást package app.models.data"
note for DatabaseRecord "Součást package app.models.data"
note for MediaContent "Součást package app.models.data"
note for Movie "Součást package app.models.data"
note for TVEpisode "Součást package app.models.data"
note for TVSeason "Součást package app.models.data"
note for TVShow "Součást package app.models.data"
note for Era "Součást package app.models.data"

DatabaseRecord "1" --> "1" PrimaryKey : Has
MediaContent --|> DatabaseRecord : Extends
TVEpisode --|> MediaContent : Extends
Movie --|> MediaContent : Extends
TVSeason --|> DatabaseRecord : Extends
TVShow --|> DatabaseRecord : Extends

TVShow "1..1" o-- "0..n" TVSeason
TVSeason "1..1" o-- "0..n" TVEpisode

    class PrimaryKey{
        -int id
        +PrimaryKey(int id)
        +equals(Object obj) boolean
        +hashCode() int
        +toString() String
    }
    class DatabaseRecord{
        <<Abstract>>
        -PrimaryKey primaryKey
        #DatabaseRecord(PrimaryKey primaryKey)
        +equals(Object obj) boolean
        +hashCode() int
        +compareTo(DatabaseRecord o) int
        +toString() String
    }
    class MediaContent{
        <<Abstract>>
        -Duration runtime
        -String name
        -int percentageRating
        -boolean wasWatched
        -String hyperlinkForContentWatch
        -String shortContentSummary
        #MediaContent(PrimaryKey primaryKey, Duration runtime, String name, int percentageRating, boolean wasWatched, String hyperlinkForContentWatch, String shortContentSummary)
    }
    class Movie{
        -LocalDate releaseDate
        -Era era
        +Movie(PrimaryKey primaryKey, Duration runtime, String name, int percentageRating, boolean wasWatched, String hyperlinkForContentWatch, String shortContentSummary, LocalDate releaseDate, Era era)
    }
    class TVEpisode{
        -int orderInTVShowSeason
        -PrimaryKey tvSeasonForeignKey
        +TVEpisode(PrimaryKey primaryKey, Duration runtime, String name, int percentageRating, boolean wasWatched, String hyperlinkForContentWatch, String shortContentSummary, int orderInTVShowSeason, PrimaryKey tvSeasonForeignKey)
    }
    class TVSeason{
        -int orderInTVShow
        -PrimaryKey tvShowForeignKey
        +TVSeason(PrimaryKey primaryKey, int orderInTVShow, PrimaryKey tvShowForeignKey)
    }
    class TVShow{
        -String name
        -LocalDate releaseDate
        -Era era
        +TVShow(PrimaryKey primaryKey, String name, LocalDate releaseDate, Era era)
    }
    class Era{
        <<Enumeration>>
        -String displayName
        -String description
        -Era(String displayName)
        DAWN_OF_THE_JEDI
        THE_OLD_REPUBLIC
        THE_HIGH_REPUBLIC
        FALL_OF_THE_JEDI
        REIGN_OF_THE_EMPIRE
        AGE_OF_REBELLION
        THE_NEW_REPUBLIC
        RISE_OF_THE_FIRST_ORDER
        NEW_JEDI_ORDER
    }

note for DataConversionException "Součást package utils.exceptions (kontrolované výjimky)"
note for DatabaseException "Součást package utils.exceptions (kontrolované výjimky)"
note for FileEmptyException "Součást package utils.exceptions (kontrolované výjimky)"
note for FileParsingException "Součást package utils.exceptions (kontrolované výjimky)"

    class DataConversionException{
        +DataConversionException(String message)
        +DataConversionException()
    }
    class DatabaseException{
        +DatabaseException(String message)
        +DatabaseException()
    }
    class FileEmptyException{
        +FileEmptyException(String message)
        +FileEmptyException()
    }
    class FileParsingException{
        +FileParsingException(String message)
        +FileParsingException()
    }

note for MovieDataConverter "Součást package utils.helpers"
note for TVEpisodeDataConverter "Součást package utils.helpers"
note for TVSeasonDataConverter "Součást package utils.helpers"
note for TVShowDataConverter "Součást package utils.helpers"

    class MovieDataConverter{
        -MovieDataConverter()
        +convertToInputOutputDataFrom(Movie data)$ MovieOutput
        +convertToDataFrom(MovieInput inputData)$ Movie throws DataConversionException
        +convertToDataFrom(MovieInputOutput inputOutputData)$ Movie throws DataConversionException
    }
    class TVEpisodeDataConverter{
        -TVEpisodeDataConverter()
        +convertToInputOutputDataFrom(TVEpisode data)$ TVEpisodeOutput
        +convertToDataFrom(TVEpisodeInput inputData, PrimaryKey tvSeasonForeignKey)$ TVEpisode
        +convertToDataFrom(TVEpisodeInputOutput inputOutputData)$ TVEpisode
    }
    class TVSeasonDataConverter{
        -TVSeasonDataConverter()
        +convertToInputOutputDataFrom(TVSeason data)$ TVSeasonOutput
        +convertToDataFrom(TVSeasonInput inputData, PrimaryKey tvShowForeignKey)$ TVSeason
        +convertToDataFrom(TVSeasonInputOutput inputOutputData)$ TVSeason
    }
    class TVShowDataConverter{
        -TVShowDataConverter()
        +convertToInputOutputDataFrom(TVShow data)$ TVShowOutput
        +convertToDataFrom(TVShowInput inputData)$ TVShow throws DataConversionException
        +convertToDataFrom(TVShowInputOutput inputOutputData)$ TVShow throws DataConversionException
    }

note for DataStore "Součást package app.logic.datastore (datová vrstva)"

note for IDataTable "Součást package utils.interfaces"
note for DataContextAccessor "Součást package app.logic.datacontext (datová vrstva)"
note for MoviesTable "Součást package app.logic.datacontext (datová vrstva)"
note for TVEpisodesTable "Součást package app.logic.datacontext (datová vrstva)"
note for TVSeasonsTable "Součást package app.logic.datacontext (datová vrstva)"
note for TVShowsTable "Součást package app.logic.datacontext (datová vrstva)"

MoviesTable --|> IDataTable : Implements
TVEpisodesTable --|> IDataTable : Implements
TVSeasonsTable --|> IDataTable : Implements
TVShowsTable --|> IDataTable : Implements

DataContextAccessor --* MoviesTable : Contains
DataContextAccessor --* TVEpisodesTable : Contains
DataContextAccessor --* TVSeasonsTable : Contains
DataContextAccessor --* TVShowsTable : Contains

MoviesTable --* DataContextAccessor : Is part of
TVEpisodesTable --* DataContextAccessor : Is part of
TVSeasonsTable --* DataContextAccessor : Is part of
TVShowsTable --* DataContextAccessor : Is part of

    class DataStore{
        <<Service>>
        -DataStore()
        -String appName$
        -String appCreator$
        -String dataDirectoryName$
        -String textInputMoviesFilename$
        -String textInputTVShowsFilename$
        -String textInputTVSeasonsFilename$
        -String textInputTVEpisodesFilename$
        -String binaryInputMoviesFilename$
        -String binaryInputTVShowsFilename$
        -String binaryInputTVSeasonsFilename$
        -String binaryInputTVEpisodesFilename$
        -String textInputOutputMoviesFilename$
        -String textInputOutputTVShowsFilename$
        -String textInputOutputTVSeasonsFilename$
        -String textInputOutputTVEpisodesFilename$
        -String binaryInputOutputMoviesFilename$
        -String binaryInputOutputTVShowsFilename$
        -String binaryInputOutputTVSeasonsFilename$
        -String binaryInputOutputTVEpisodesFilename$
        -Collator czechCollator$
        -Map~String_String~ erasDescriptions$
        +loadEraDescription(String era)$ String
    }
    class IDataTable~T extends DatabaseRecord~{
        <<Interface>>
        +addFrom(T inputData) throws DatabaseException
        +loadFrom(T inputOutputData) throws DatabaseException
        +deleteBy(PrimaryKey primaryKey) throws DatabaseException
        +editBy(PrimaryKey primaryKey, T editedExistingData) boolean throws DatabaseException
        +getBy(PrimaryKey primaryKey) T
        +getAll() List~T~
        +filterBy(Predicate~T~ condition) List~T~
        +sortBy(Comparator~T~ comparator, List~T~ sourceList)
        +sortByPrimaryKey(List~T~ sourceList)
        +clearData()
    }
    class DataContextAccessor{
        <<Service>>
        -DataContextAccessor dataContextAccessor$
        -IDataTable~TVSeason~ tvSeasonsTable
        -IDataTable~TVShow~ tvShowsTable
        -IDataTable~TVEpisode~ tvEpisodesTable
        -IDataTable~Movie~ moviesTable
        -DataContextAccessor()
        +getInstance()$ DataContextAccessor
        -initializeDataContextAccessor()
        #generatePrimaryKey(IDataTable dataTable, Random dataTablePrimaryKeysGenerator) PrimaryKey
    }
    class MoviesTable{
        -IDataTable~Movie~ moviesTable$
        -List~Movie~ moviesData
        -DataContextAccessor dbContext
        -Random primaryKeysGenerator
        -MoviesTable(DataContextAccessor dbContext)
        #getInstance(DataContextAccessor dbContext)$ IDataTable~Movie~
    }
    class TVEpisodesTable{
        -IDataTable~TVEpisode~ tvEpisodesTable$
        -List~TVEpisode~ tvEpisodesData
        -DataContextAccessor dbContext
        -Random primaryKeysGenerator
        -TVEpisodesTable(DataContextAccessor dbContext)
        #getInstance(DataContextAccessor dbContext)$ IDataTable~TVEpisode~
    }
    class TVSeasonsTable{
        -IDataTable~TVSeason~ tvSeasonsTable$
        -List~TVSeason~ tvSeasonsData
        -DataContextAccessor dbContext
        -Random primaryKeysGenerator
        -TVSeasonsTable(DataContextAccessor dbContext)
        #getInstance(DataContextAccessor dbContext)$ IDataTable~TVSeason~
    }
    class TVShowsTable{
        -IDataTable~TVShow~ tvShowsTable$
        -List~TVShow~ tvShowsData
        -DataContextAccessor dbContext
        -Random primaryKeysGenerator
        -TVShowsTable(DataContextAccessor dbContext)
        #getInstance(DataContextAccessor dbContext)$ IDataTable~TVShow~
    }

note for EmailSender "Součást package utils.emailsender (externí knihovna)"

note for IDataFileManager "Součást package utils.interfaces"
note for FileManagerAccessor "Součást package app.logic.filemanager"
note for MoviesFileManager "Součást package app.logic.filemanager"
note for TVEpisodesFileManager "Součást package app.logic.filemanager"
note for TVSeasonsFileManager "Součást package app.logic.filemanager"
note for TVShowsFileManager "Součást package app.logic.filemanager"

MoviesFileManager --|> IDataFileManager : Implements
TVEpisodesFileManager --|> IDataFileManager : Implements
TVSeasonsFileManager --|> IDataFileManager : Implements
TVShowsFileManager --|> IDataFileManager : Implements

FileManagerAccessor --* MoviesFileManager : Contains
FileManagerAccessor --* TVEpisodesFileManager : Contains
FileManagerAccessor --* TVSeasonsFileManager : Contains
FileManagerAccessor --* TVShowsFileManager : Contains

    class EmailSender{
        <<Service>>
        -EmailSender emailSender$
        -int smtpPort
        -String hostName
        -String randomGeneratedAppToken
        -String appId
        -EmailSender()
        +getInstance()$ EmailSender
        +sendEmail(String recipientEmailAddress, String subject, StringBuilder message) throws EmailException
    }
    class IDataFileManager~T_S~{
        <<Interface>>
        +getTextInputOutputFileContent() StringBuilder throws FileNotFoundException, IOException, FileEmptyException
        +getBinaryInputOutputFileContent() StringBuilder throws FileNotFoundException, IOException, FileEmptyException
        +getTextInputFileContent() StringBuilder throws FileNotFoundException, IOException, FileEmptyException
        +getBinaryInputFileContent() StringBuilder throws throws FileNotFoundException, IOException, FileEmptyException
        +loadInputOutputDataFrom(boolean fromBinary) List~S~ throws IOException, FileParsingException
        +tryDeleteInputOutputDataFilesCopies()
        +transferBetweenInputOutputDataAndCopyFiles(boolean fromCopyFiles) throws IOException
        +saveInputOutputDataIntoFiles(List~S~ newInputOutputData) throws IOException
        +loadInputDataFrom(boolean fromBinary) Map~Integer_T~ throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
    }
    class FileManagerAccessor{
        <<Service>>
        -String fileSeparator$
        -String textFileEndMarking$
        -String textFileValuesSectionMarking$
        -String textFileAttributesSectionMarking$
        -FileManagerAccessor fileManagerAccessor$
        -File dataDirectory$
        -IDataFileManager~MovieInput_MovieInputOutput~ moviesFileManager
        -IDataFileManager~TVShowInput_TVShowInputOutput~ tvShowsFileManager
        -IDataFileManager~TVSeasonInput_TVSeasonInputOutput~ tvSeasonsFileManager
        -IDataFileManager~TVEpisodeInput_TVEpisodeInputOutput~ tvEpisodesFileManager
        -FileManagerAccessor()
        +getInstance()$ FileManagerAccessor
        #getFileSeparator()$ String
        #getTextFileEndMarking()$ String
        #getTextFileValuesSectionMarking()$ String
        #getTextFileAttributesSectionMarking()$ String
        +getDataDirectoryPath()$ String
        +setDataDirectory(String directoryFullPath)$
    }
    class MoviesFileManager{
        -IDataFileManager~MovieInput_MovieInputOutput~ moviesFileManager$
        -MoviesFileManager()
        #getInstance()$ IDataFileManager~MovieInput_MovieInputOutput~
        -parseInputData(Map~String_StringBuilder~ movieInputFieldsValues, Map~Integer_MovieInput~ parsedMovies, Field[] movieInputFields, int inputMovieOrder)
        -parseInputOutputDataFromTextFile(Map~String_StringBuilder~ movieInputOutputFieldsValues, List~MovieInputOutput~ parsedMovies, Field[] movieInputOutputFields)
        -createInputOutputDataTextRepresentation(List~MovieInputOutput~ newInputOutputMovies) StringBuilder
    }
    class TVEpisodesFileManager{
        -IDataFileManager~TVEpisodeInput_TVEpisodeInputOutput~ tvEpisodesFileManager$
        -TVEpisodesFileManager()
        #getInstance()$ IDataFileManager~TVEpisodeInput_TVEpisodeInputOutput~
        -parseInputData(Map~String_StringBuilder~ tvEpisodeInputFieldsValues, Map~Integer_TVEpisodeInput~ parsedTVEpisodes, Field[] tvEpisodeInputFields, int inputTVEpisodeOrder) 
        -parseInputOutputDataFromTextFile(Map~String_StringBuilder~ tvEpisodeInputOutputFieldsValues, List~TVEpisodeInputOutput~ parsedTVEpisodes, Field[] tvEpisodeInputOutputFields)
        -createInputOutputDataTextRepresentation(List~TVEpisodeInputOutput~ newInputOutputTVEpisodes) StringBuilder
    }
    class TVSeasonsFileManager{
        -IDataFileManager~TVSeasonInput_TVSeasonInputOutput~ tvSeasonsFileManager$
        -TVSeasonsFileManager()
        #getInstance()$ IDataFileManager~TVSeasonInput_TVSeasonInputOutput~
        -parseInputData(Map~String_StringBuilder~ tvSeasonInputFieldsValues, Map~Integer_TVSeasonInput~ parsedTVSeasons, Field[] tvSeasonInputFields, int inputTVSeasonOrder) 
        -parseInputOutputDataFromTextFile(Map~String_StringBuilder~ tvSeasonInputOutputFieldsValues, List~TVSeasonInputOutput~ parsedTVSeasons, Field[] tvSeasonInputOutputFields)
        -createInputOutputDataTextRepresentation(List~TVSeasonInputOutput~ newInputOutputTVSeasons) StringBuilder
    }
    class TVShowsFileManager{
        -IDataFileManager~TVShowInput_TVShowInputOutput~ tvShowsFileManager$
        -TVShowsFileManager()
        #getInstance()$ IDataFileManager~TVShowInput_TVShowInputOutput~
        -parseInputData(Map~String_StringBuilder~ tvShowInputFieldsValues, Map~Integer_TVShowInput~ parsedTVShows, Field[] tvShowInputFields, int inputTVShowOrder) 
        -parseInputOutputDataFromTextFile(Map~String_StringBuilder~ tvShowInputOutputFieldsValues, List~TVShowInputOutput~ parsedTVShows, Field[] tvShowInputOutputFields)
        -createInputOutputDataTextRepresentation(List~TVShowInputOutput~ newInputOutputTVShows) StringBuilder
    }

note for DataSorting "Součást package app.logic.controllers"
note for DataType "Součást package app.logic.controllers"
note for MoviesController "Součást package app.logic.controllers (business logika)"
note for TVEpisodesController "Součást package app.logic.controllers (business logika)"

MoviesController ..> DataContextAccessor : Depends on
MoviesController ..> EmailSender : Depends on
MoviesController ..> FileManagerAccessor : Depends on

TVEpisodesController ..> DataContextAccessor : Depends on
TVEpisodesController ..> EmailSender : Depends on
TVEpisodesController ..> FileManagerAccessor : Depends on
    
    class DataSorting{
        <<Enumeration>>
        LONGEST
        NEWEST
        BY_NAME
        FAVORITE
    }
    class DataType{
        <<Enumeration>>
        MOVIE
        TV_SHOW
        TV_SEASON
        TV_EPISODE
    }
    class MoviesController{
        -MoviesController movieController$
        -DataContextAccessor dbContext
        -EmailSender emailSender
        -FileManagerAccessor fileManagerAccessor
        -Collator czechCollator
        -Comparator~Movie~ BY_LONGEST_DURATION_MOVIE
        -Comparator~Movie~ BY_NAME_ALPHABETICALLY_MOVIE
        -Comparator~Movie~ BY_DATE_NEWEST_MOVIE
        -Comparator~Movie~ BY_DATE_OLDEST_MOVIE
        -Comparator~Movie~ BY_PERCENTAGE_RATING_HIGHEST_MOVIE
        -MoviesController(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor fileManagerAccessor)
        +getInstance(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor fileManagerAccessor)$ MoviesController
        +sendUnwatchedOldestMoviesWithHyperlinksByEmail(String recipientEmailAddress) throws EmailException
        +sendUnwatchedMoviesWithHyperlinksInChronologicalErasByEmail(String recipientEmailAddress) throws EmailException
        +getTotalRuntimeOfAllReleasedMoviesByEra(Era era, boolean onlyWatched) Duration
        +getAverageRuntimeOfAllReleasedMoviesByEra(Era era, boolean onlyWatched) Duration
        +getAverageRatingOfAllReleasedMoviesByEra(Era era) float
        +getAnnouncedMoviesCountByEra(Era era) int
        +getReleasedMoviesWithRuntimeSetCountByEra(Era era, boolean onlyWatched) int
        +getReleasedMoviesCountByEra(Era era, boolean onlyWatched) int
        +getReleasedLongestMoviesByEra(Era era, boolean onlyWatched) List~Movie~
        +getReleasedMoviesInAlphabeticalOrderByEra(Era era, boolean onlyWatched) List~Movie~
        +getReleasedNewestMoviesByEra(Era era, boolean onlyWatched) List~Movie~
        +getReleasedFavoriteMoviesByEra(Era era) List~Movie~
        +getAnnouncedMoviesInAlphabeticalOrderByEra(Era era) List~Movie~
        +getReleasedFavoriteMoviesOfAllTime() List~Movie~
        +getReleasedNewestMovies() List~Movie~
        +rateMovie(Movie existingMovie, int percentageRating) boolean throws DatabaseException, IOException
        +searchForMovie(String name) List~Movie~
        +getChosenMoviesFileContent(String fileName) StringBuilder throws IOException, FileNotFoundException, FileEmptyException
        +loadAllInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException, DataConversionException, DatabaseException, Exception
        +addMoviesFrom(boolean fromBinary) StringBuilder throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
        +deleteMovieBy(PrimaryKey moviePrimaryKey) throws IOException, DatabaseException
        +deleteMovies(List~Movie~ chosenMovies) throws IOException
        +editMovieBy(PrimaryKey existingMoviePrimaryKey, boolean fromBinary) boolean throws IOException, FileNotFoundException, FileEmptyException, DataConversionException, DatabaseException, FileParsingException
        -updateMoviesInputOutputFilesWithExistingData() throws IOException
        -updateMoviesInputOutputFilesWithNewChanges() throws IOException
        +getCurrentDate()$ LocalDate
    }
    class TVEpisodesController{
        -TVEpisodesController tvEpisodesController$
        -DataContextAccessor dbContext
        -EmailSender emailSender
        -FileManagerAccessor fileManagerAccessor
        -Collator czechCollator
        -Comparator~TVEpisode~ BY_LONGEST_DURATION_EPISODE
        -Comparator~TVShow~ BY_NAME_ALPHABETICALLY_SHOW
        -Comparator~TVEpisode~ BY_PERCENTAGE_RATING_HIGHEST_EPISODE
        -Comparator~TVEpisode~ BY_ORDER_ASCENDING_EPISODE
        -Comparator~TVSeason~ BY_ORDER_ASCENDING_SEASON
        -Comparator~TVShow~ BY_DATE_NEWEST_SHOW
        -TVEpisodesController(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor fileManagerAccessor)
        +getInstance(DataContextAccessor dbContext, EmailSender emailSender, FileManagerAccessor fileManagerAccessor)$ TVEpisodesController
        +sendUnwatchedEpisodesWithHyperlinksInTVShowByEmail(String recipientEmailAddress, PrimaryKey tvShowPrimaryKey) throws EmailException, DatabaseException
        +getTotalRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) Duration
        +getTotalRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) Duration
        +getAverageRuntimeOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) Duration
        +getAverageRuntimeOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) Duration
        +getAverageRatingOfAllReleasedEpisodesInTVShow(PrimaryKey tvShowPrimaryKey) float
        +getAverageRatingOfAllReleasedEpisodesInTVShowSeason(PrimaryKey tvShowSeasonPrimaryKey) float
        +getAnnouncedTVShowsCountByEra(Era era) int
        +getReleasedTVShowsCountByEra(Era era) int
        +getReleasedTVShowEpisodesCount(PrimaryKey tvShowPrimaryKey, boolean onlyWatched) int
        +getReleasedTVShowSeasonEpisodesCount(PrimaryKey tvShowSeasonPrimaryKey, boolean onlyWatched) int
        +getReleasedTVShowsInAlphabeticalOrderByEra(Era era) List~TVShow~
        +getReleasedNewestTVShowsByEra(Era era) List~TVShow~
        +getReleasedLongestTVShowsByEra(Era era) List~TVShow~
        +getReleasedTVShowLongestEpisodes(PrimaryKey tvShowPrimaryKey) List~TVEpisode~
        +getReleasedTVShowSeasonLongestEpisodes(PrimaryKey tvShowSeasonPrimaryKey) List~TVEpisode~
        +getReleasedTVShowFavoriteTVEpisodes(PrimaryKey tvShowPrimaryKey) List~TVEpisode~
        +getReleasedTVShowSeasonFavoriteTVEpisodes(PrimaryKey tvShowSeasonPrimaryKey) List~TVEpisode~
        +getReleasedTVShowSeasonsByOrder(PrimaryKey tvShowPrimaryKey) List~TVSeason~
        +getReleasedTVShowSeasonEpisodesByOrder(PrimaryKey tvShowSeasonPrimaryKey) List~TVEpisode~
        +getTVSeasonDetail(PrimaryKey chosenTVSeasonPrimaryKey) TVSeason
        +getAnnouncedTVShowsInAlphabeticalOrderByEra(Era era) List~TVShow~
        +getReleasedNewestTVShows() List~TVShow~
        +rateTVEpisode(TVEpisode existingEpisode, int percentageRating) boolean throws DatabaseException, IOException
        +searchForTVShow(String name) List~TVShow~
        +getChosenTVShowsFileContent(String fileName) StringBuilder throws IOException, FileNotFoundException, FileEmptyException
        +getChosenTVSeasonsFileContent(String fileName) StringBuilder throws IOException, FileNotFoundException, FileEmptyException
        +getChosenTVEpisodesFileContent(String fileName) StringBuilder throws IOException, FileNotFoundException, FileEmptyException
        +loadAllInputOutputDataFrom(boolean fromBinary) throws IOException, FileParsingException, DataConversionException, DatabaseException, Exception
        +addTVShowsFrom(boolean fromBinary) StringBuilder throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
        +addTVSeasonsFrom(PrimaryKey chosenTVShowPrimaryKey, boolean fromBinary) StringBuilder throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
        +addTVEpisodesFrom(PrimaryKey chosenTVSeasonPrimaryKey, boolean fromBinary) StringBuilder throws IOException, FileNotFoundException, FileEmptyException, FileParsingException
        +deleteTVShowBy(PrimaryKey tvShowPrimaryKey) throws IOException, DatabaseException
        +deleteTVSeasonBy(PrimaryKey tvSeasonPrimaryKey) throws IOException, DatabaseException
        +deleteTVEpisodeBy(PrimaryKey tvEpisodePrimaryKey) throws IOException, DatabaseException
        +deleteTVShows(List~TVShow~ chosenTVShows) throws IOException
        +deleteTVSeasons(List~TVSeason~ chosenTVSeasons) throws IOException
        +deleteTVEpisodes(List~TVEpisode~ chosenTVEpisodes) throws IOException
        +editTVShowBy(PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) boolean throws IOException, FileNotFoundException, FileEmptyException, DataConversionException, DatabaseException, FileParsingException
        +editTVSeasonBy(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey tvShowForeignKey, boolean fromBinary) boolean throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException
        +editTVEpisodeBy(PrimaryKey existingTVEpisodePrimaryKey, PrimaryKey tvSeasonForeignKey, boolean fromBinary) boolean throws IOException, FileNotFoundException, FileEmptyException, DatabaseException, FileParsingException
        -updateTVShowsInputOutputFilesWithExistingData() throws IOException
        -updateTVShowsInputOutputFilesWithNewChanges() throws IOException
        -updateTVSeasonsInputOutputFilesWithExistingData() throws IOException
        -updateTVSeasonsInputOutputFilesWithNewChanges() throws IOException
        -updateTVEpisodesInputOutputFilesWithExistingData() throws IOException
        -updateTVEpisodesInputOutputFilesWithNewChanges() throws IOException
        +getCurrentDate()$ LocalDate
    }

note for ApplicationRunner "Součást package ui"
note for ConsoleUI "Součást package ui (prezentační vrstva)"
note for MoviesUI "Součást package ui (prezentační vrstva)"
note for TVEpisodesUI "Součást package ui (prezentační vrstva)"

ConsoleUI ..> MoviesController : Depends on
ConsoleUI ..> TVEpisodesController : Depends on

ConsoleUI --* MoviesUI : Contains
ConsoleUI --* TVEpisodesUI : Contains

MoviesUI --* ConsoleUI : Is part of
TVEpisodesUI --* ConsoleUI : Is part of

    class ApplicationRunner{
        +main(String[] args)$
    }
    class ConsoleUI{
        -boolean isDataDirectorySet$
        -boolean isDatabaseFromFilesLoaded$
        -Scanner scanner
        -boolean wasInitialized
        -List~String~ breadcrumbItems
        -TVEpisodesController tvEpisodesController
        -MoviesController moviesController
        -MoviesUI moviesUI
        -TVEpisodesUI tvEpisodesUI
        +ConsoleUI(MoviesController moviesController, TVEpisodesController tvEpisodesController)
        -initializeConsoleUI()
        #getScanner() Scanner
        #getMoviesController() MoviesController
        #getTVEpisodesController() TVEpisodesController
        +start()
        -displayDataDirectoryPathMenu()
        -displayLoadingOutputFilesMenu()
        -displayIntroduction()
        -displayMainMenu()
        #createDividingHorizontalLineOf(String heading) StringBuilder
        #createHeadingWithHorizontalLines(int size, String heading) StringBuilder
        #createMenuNameWithHorizontalLines(int size, String menuName) StringBuilder
        #addBreadcrumbItem(String title)
        #removeLastBreadcrumbItem()
        #displayBreadcrumb()
        #displayErrorMessage(String message)
        #displayInfoMessage(String message)
        #advanceToNextInput()
        #loadChosenEraOrderFromUser() int
        #loadEmailFromUser() String
        #loadChoiceFromSubmenu() int
        -loadChoiceFromMenu() int
        -setDataDirectoryPath()
        -loadDataDirectoryPath() String
        -loadAllOutputDataFrom(boolean fromBinary)
        #displayDataChosenFileContent(String fileName, DataType dataType)
        -printInformationsAboutChronologicalEras()
    }
    class MoviesUI{
        -ConsoleUI consoleUI
        #MoviesUI(ConsoleUI consoleUI)
        #start()
        -displayMoviesManagementSubmenu()
        -displayLoadMoviesFromInputFileSubmenu()
        -displayPrintFoundMoviesByNameSubmenu()
        -displaySendMoviesByEmailSubmenu()
        -displayPrintErasWithAnnouncedMoviesSubmenu()
        -displayPrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu(Era chosenEra)
        -displayPrintErasWithUnwatchedMoviesSubmenu()
        -displayPrintUnwatchedMoviesByEraSubmenu(Era chosenEra)
        -displayPrintErasWithWatchedMoviesSubmenu()
        -displayPrintWatchedMoviesByEraSubmenu(Era chosenEra)
        -displayPrintReleasedFavoriteMoviesOfAllTimeSubmenu()
        -displayPrintReleasedNewestMoviesSubmenu()
        -displayPrintMoviesOutputFilesContentsSubmenu()
        -displayDetailAboutMovieSubmenu(Movie chosenMovie)
        -displayEditChosenMovieSubmenu(Movie chosenMovie)
        -handleLoadMoviesFromInputFileSubmenu()
        -loadMoviesFromInputFile(boolean fromBinary)
        -handlePrintFoundMoviesByNameSubmenu()
        -loadMovieNameFromUser() String
        -printFoundMoviesByName(List~Movie~ foundMovies, String queriedName)
        -handleSendMoviesByEmailSubmenu()
        -sendUnwatchedMoviesFromOldestByEmail()
        -sendUnwatchedMoviesInChronologicalErasByEmail()
        -handlePrintErasWithAnnouncedMoviesSubmenu()
        -printErasWithAnnouncedMovies()
        -handlePrintAnnouncedMoviesInAlphabeticalOrderByEraSubmenu()
        -printAnnouncedMoviesInAlphabeticalOrderByEra(List~Movie~ announcedMoviesByChosenEra, Era chosenEra)
        -handlePrintErasWithUnwatchedMoviesSubmenu()
        -printErasWithUnwatchedMovies()
        -handlePrintUnwatchedMoviesByEraSubmenu(DataSorting dataSorting)
        -printUnwatchedMoviesByEra(List~Movie~ unwatchedMoviesByChosenEra, Era chosenEra, DataSorting dataSorting)
        -handlePrintErasWithWatchedMoviesSubmenu()
        -printErasWithWatchedMovies()
        -handlePrintWatchedMoviesByEraSubmenu(DataSorting dataSorting)
        -printWatchedMoviesByEra(List~Movie~ watchedMoviesByChosenEra, Era chosenEra, DataSorting dataSorting)
        -handlePrintReleasedFavoriteMoviesOfAllTimeSubmenu()
        -printReleasedFavoriteMoviesOfAllTime(List~Movie~ favoriteMovies)
        -handlePrintReleasedNewestMoviesSubmenu()
        -printReleasedNewestMovies(List~Movie~ releasedNewestMovies)
        -handlePrintMoviesOutputFilesContentsSubmenu()
        -handleDisplayDetailAboutMovieSubmenu(List~Movie~ chosenMovies)
        -loadChosenMovieFromUser() int
        -printMovieDetail(Movie chosenMovie, boolean isInEditMode)
        -deleteChosenMovie(PrimaryKey moviePrimaryKey) boolean
        -handleDisplayEditChosenMovieSubmenu(Movie chosenMovie) boolean
        -editMovieFromInputFile(PrimaryKey existingMoviePrimaryKey, boolean fromBinary) boolean
        -rateMovie(Movie chosenMovie) boolean
        -loadMoviePercentageRatingFromUser() int
        -deleteChosenMovies(List~Movie~ chosenMovies)
    }
    class TVEpisodesUI{
        -ConsoleUI consoleUI
        #TVEpisodesUI(ConsoleUI consoleUI)
        #start()
        -displayTVEpisodesManagementSubmenu()
        -displayLoadTVShowsFromInputFileSubmenu()
        -displayPrintFoundTVShowsByNameSubmenu()
        -displayPrintErasWithAnnouncedTVShowsSubmenu()
        -displayPrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu(Era chosenEra)
        -displayPrintErasWithReleasedTVShowsSubmenu()
        -displayPrintReleasedTVShowsByEraSubmenu(Era chosenEra)
        -displayDetailAboutTVShowSubmenu(TVShow chosenTVShow)
        -displayEditChosenTVShowSubmenu(TVShow chosenTVShow)
        -displayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow)
        -displayLoadTVSeasonsFromInputFileSubmenu()
        -displaySendTVEpisodesByEmailSubmenu()
        -displayPrintTVShowSortedTVEpisodesSubmenu(TVShow chosenTVShow, DataSorting dataSorting)
        -displayDetailAboutTVSeasonSubmenu(TVSeason chosenTVSeason)
        -displayEditChosenTVSeasonSubmenu(TVSeason chosenTVSeason)
        -displayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason)
        -displayLoadTVEpisodesFromInputFileSubmenu()
        -displayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting)
        -displayDetailAboutTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason)
        -displayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason)
        -displayPrintReleasedNewestTVShowsSubmenu()
        -displayPrintDataOutputFilesContentsSubmenu(DataType dataType)
        -handleLoadTVShowsFromInputFileSubmenu()
        -loadTVShowsFromInputFile(boolean fromBinary)
        -handlePrintFoundTVShowsByNameSubmenu()
        -loadTVShowNameFromUser() String
        -printFoundTVShowsByName(List~TVShow~ foundTVShows, String queriedName)
        -handlePrintErasWithAnnouncedTVShowsSubmenu()
        -printErasWithAnnouncedTVShows()
        -handlePrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu()
        -printAnnouncedTVShowsInAlphabeticalOrderByEra(List~TVShow~ announcedTVShowsByChosenEra, Era chosenEra)
        -handlePrintErasWithReleasedTVShowsSubmenu()
        -printErasWithReleasedTVShows()
        -handlePrintReleasedTVShowsByEraSubmenu(DataSorting dataSorting)
        -printReleasedTVShowsByEra(List~TVShow~ releasedTVShowsByChosenEra, Era chosenEra, DataSorting dataSorting)
        -handleDisplayDetailAboutTVShowSubmenu(List~TVShow~ chosenTVShows)
        -loadChosenTVShowFromUser() int
        -printTVShowDetail(TVShow chosenTVShow, boolean isInEditMode)
        -deleteChosenTVShow(PrimaryKey tvShowPrimaryKey) boolean
        -handleDisplayEditChosenTVShowSubmenu(TVShow chosenTVShow) boolean
        -editTVShowFromInputFile(PrimaryKey existingTVShowPrimaryKey, boolean fromBinary) boolean
        -handleDisplayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow)
        -printChosenTVShowSeasons(List~TVSeason~ chosenTVShowSeasons, TVShow chosenTVShow)
        -handleLoadTVSeasonsFromInputFileSubmenu(TVShow chosenTVShow)
        -loadTVSeasonsFromInputFile(PrimaryKey tvShowPrimaryKey, boolean fromBinary)
        -deleteChosenTVSeasons(List~TVSeason~ chosenTVSeasons)
        -handleSendTVEpisodesByEmailSubmenu(TVShow chosenTVShow)
        -sendUnwatchedTVEpisodesInTVShowByEmail(PrimaryKey tvShowPrimaryKey)
        -handleDisplayPrintTVShowSortedTVEpisodesSubmenu(TVShow chosenTVShow, DataSorting dataSorting)
        -printTVShowSortedTVEpisodes(List~TVEpisode~ tvShowSortedTVEpisodes, TVShow chosenTVShow, DataSorting dataSorting)
        -handleDisplayDetailAboutTVSeasonSubmenu(List~TVSeason~ chosenTVSeasons)
        -loadChosenTVSeasonFromUser() int
        -printTVSeasonDetail(TVSeason chosenTVSeason, boolean isInEditMode)
        -deleteChosenTVSeason(PrimaryKey tvSeasonPrimaryKey) boolean
        -handleDisplayEditChosenTVSeasonSubmenu(TVSeason chosenTVSeason) boolean
        -editTVSeasonFromInputFile(PrimaryKey existingTVSeasonPrimaryKey, PrimaryKey existingTVShowPrimaryKey ,boolean fromBinary) boolean
        -handleDisplayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason)
        -printChosenTVSeasonEpisodes(List~TVEpisode~ chosenTVSeasonEpisodes, TVSeason chosenTVSeason)
        -handleLoadTVEpisodesFromInputFileSubmenu(TVSeason chosenTVSeason)
        -loadTVEpisodesFromInputFile(PrimaryKey tvSeasonPrimaryKey, boolean fromBinary)
        -deleteChosenTVEpisodes(List~TVEpisode~ chosenTVEpisodes)
        -handleDisplayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting)
        -printTVSeasonSortedTVEpisodes(List~TVEpisode~ tvSeasonSortedTVEpisodes, TVSeason chosenTVSeason, DataSorting dataSorting)
        -handleDisplayPrintReleasedNewestTVShowsSubmenu()
        -printReleasedNewestTVShows(List~TVShow~ releasedNewestTVShows)
        -handlePrintDataOutputFilesContentsSubmenu(DataType dataType)
        -handleDisplayDetailAboutTVEpisodeSubmenu(List~TVEpisode~ chosenTVEpisodes)
        -loadChosenTVEpisodeFromUser() int
        -printTVEpisodeDetail(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason, boolean isInEditMode)
        -deleteChosenTVEpisode(PrimaryKey tvEpisodePrimaryKey) boolean
        -handleDisplayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason) boolean
        -editTVEpisodeFromInputFile(PrimaryKey existingTVEpisodePrimaryKey, PrimaryKey existingTVSeasonPrimaryKey, boolean fromBinary) boolean
        -rateTVEpisode(TVEpisode chosenTVEpisode) boolean
        -loadTVEpisodePercentageRatingFromUser() int
        -deleteChosenTVShows(List~TVShow~ chosenTVShows)
    }

```

# Testování

- Testování je rozděleno na dvě části, **unit testy** a **akceptační testy**

## Unit testy

- V průběhu vývoje aplikace se průběžně prováděly testy jednotlivých částí/modulů  aplikace
- Samotné testy se nacházejí v následujících třídách v aplikaci:
    - [ConsoleUITest](/star-wars-media-content-management/src/main/java/tests/mainmethods/ConsoleUITest.java)
    - [DataContextAccessorTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataContextAccessorTest.java)
    - [DataConvertersTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataConvertersTest.java)
    - [DataModelsTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataModelsTest.java)
    - [EmailSenderTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/EmailSenderTest.java)
    - [FileManagerAccessorTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/FileManagerAccessorTest.java)
    - [MoviesControllerTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/MoviesControllerTest.java)
    - [InputOutputModelsTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/InputOutputModelsTest.java)
    - [TVEpisodesControllerTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/TVEpisodesControllerTest.java)

## Akceptační testy

- V repozitáři jsou předpřipravené vstupní soubory s testovacími daty v [adresáři data](/star-wars-media-content-management/data/)
- Jsou k dispozici výsledky akceptačních testů ve formě **tabulek** a ve formě **konzolových výstupů uživatelských funkcí pro přidávání nových dat v aplikaci**

### Vstupní soubor s filmy

- Představuje textový soubor [input_movies.txt](/star-wars-media-content-management/data/input_movies.txt)
- Binární soubor [input_movies.bin](/star-wars-media-content-management/data/input_movies.bin) má úplně totožný obsah jako textový soubor
- Zde je uvedena tabulka pro filmy:

| **Pořadí filmu v souboru** | **Typ testu** | **Očekávaný výsledek** | **Skutečný výsledek** | **Prošel (ano/ne)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 2 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 3 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 4 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 5 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 6 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 7 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 8 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 9 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 10 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 11 | Limitní stav | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 12 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu filmu | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu filmu | ano |
| 13 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli příliš  velkému počtu epoch sekund u datumu vydání filmu | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli příliš  velkému počtu epoch sekund u datumu vydání filmu | ano |
| 14 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | ano |
| 15 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli délce filmu v sekundách,  která není zadána jako celé číslo | Neúspěšné nahrání ze souboru  kvůli délce filmu v sekundách,  která není zadána jako celé číslo | ano |
| 16 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli procentuálnímu hodnocení,  která není zadáné jako celé číslo | Neúspěšné nahrání ze souboru  kvůli procentuálnímu hodnocení,  která není zadáné jako celé číslo | ano |
| 17 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  překročení limit počtu znaků  pro URL odkaz ke zhlédnutí filmu  | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  překročení limit počtu znaků  pro URL odkaz ke zhlédnutí filmu  | ano |
| 18 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 19 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 20 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |

- Zde je konzolový výstup uživatelské funkce pro přidávání nových filmů:

```
Informační zpráva: Celkově se podařilo nahrát 14 filmů do databáze a naopak se nepodařilo nahrát 4 filmů
Chybový stav filmu s pořadím 12 v souboru input_movies.txt: Přidaný film musí mít název
Chybový stav filmu s pořadím 13 v souboru input_movies.txt: Příliš velký počet epoch sekund jako datum uvedení konvertovaného filmu
Chybový stav filmu s pořadím 14 v souboru input_movies.txt: Chronologické období přidaného filmu musí být vybráno
Chybový stav filmu s pořadím 17 v souboru input_movies.txt: Odkaz ke zhlédnutí přidaného filmu nesmí mít délku větší než 180 znaků
```

### Vstupní soubor s TV seriály

- Představuje textový soubor [input_tvShows.txt](/star-wars-media-content-management/data/input_tvShows.txt)
- Binární soubor [input_tvShows.bin](/star-wars-media-content-management/data/input_tvShows.bin) má úplně totožný obsah jako textový soubor
- Zde je uvedena tabulka pro TV seriály:

| **Pořadí TV seriálu v souboru** | **Typ testu** | **Očekávaný výsledek** | **Skutečný výsledek** | **Prošel (ano/ne)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 2 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 3 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 4 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 5 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 6 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 7 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 8 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 9 | Limitní stav | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 10 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejný název a stejné  datum vydání jako u TV  seriálu s pořadím 8) | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejný název a stejné  datum vydání jako u TV  seriálu s pořadím 8) | ano |
| 11 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu TV seriálu | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu TV seriálu | ano |
| 12 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli příliš  velkému počtu epoch sekund u datumu vydání TV seriálu | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli příliš  velkému počtu epoch sekund u datumu vydání TV seriálu | ano |
| 13 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | ano |
| 14 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli datumu vydání  v epoch sekundách,  které není zadáno jako celé číslo | Neúspěšné nahrání ze souboru  kvůli datumu vydání  v epoch sekundách,  které není zadáno jako celé číslo | ano |
| 15 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli datumu vydání  v epoch sekundách,  které není zadáno jako celé číslo, protože chybí | Neúspěšné nahrání ze souboru  kvůli datumu vydání  v epoch sekundách,  které není zadáno jako celé číslo, protože chybí | ano |
| 16 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu TV seriálu | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli chybějícímu názvu TV seriálu | ano |
| 17 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  nevybrané/neplatné  chronologické éře | ano |
| 18 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 19 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 20 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |

- Zde je konzolový výstup uživatelské funkce pro přidávání nových TV seriálů:

```
Informační zpráva: Celkově se podařilo nahrát 12 seriálů do databáze a naopak se nepodařilo nahrát 6 seriálů
Chybový stav seriálu s pořadím 10 v souboru input_tvShows.txt: Data přidaného seriálu jsou duplicitní
Chybový stav seriálu s pořadím 11 v souboru input_tvShows.txt: Přidaný seriál musí mít název
Chybový stav seriálu s pořadím 12 v souboru input_tvShows.txt: Příliš velký počet epoch sekund jako datum uvedení konvertovaného seriálu
Chybový stav seriálu s pořadím 13 v souboru input_tvShows.txt: Chronologické období přidaného seriálu musí být vybráno
Chybový stav seriálu s pořadím 16 v souboru input_tvShows.txt: Přidaný seriál musí mít název
Chybový stav seriálu s pořadím 17 v souboru input_tvShows.txt: Chronologické období přidaného seriálu musí být vybráno
```

### Vstupní soubor s TV sezónami

- Představuje textový soubor [input_tvSeasons.txt](/star-wars-media-content-management/data/input_tvSeasons.txt)
- Binární soubor [input_tvSeasons.bin](/star-wars-media-content-management/data/input_tvSeasons.bin) má úplně totožný obsah jako textový soubor
- Zde je uvedena tabulka pro TV sezóny:

| **Pořadí TV sezóny v souboru** | **Typ testu** | **Očekávaný výsledek** | **Skutečný výsledek** | **Prošel (ano/ne)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 2 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 3 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 4 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 5 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejné pořadí TV sezóny v rámci  TV seriálu  jako TV sezóny s pořadím 4 v souboru) | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejné pořadí TV sezóny v rámci  TV seriálu  jako TV sezóny s pořadím 4 v souboru) | ano |
| 6 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli neplatnému zápornému nebo nulovému pořadí | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli neplatnému zápornému nebo nulovému pořadí | ano |
| 7 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli pořadí TV sezóny v rámci TV seriálu,  které není zadáno jako celé číslo | Neúspěšné nahrání ze souboru  kvůli pořadí TV sezóny v rámci TV seriálu,  které není zadáno jako celé číslo | ano |

- Zde je konzolový výstup uživatelské funkce pro přidávání nových TV sezón pro vybraný TV seriál:

```
Informační zpráva: Celkově se podařilo nahrát 4 sezón vybraného seriálu do databáze a naopak se nepodařilo nahrát 2 sezón vybraného seriálu
Chybový stav sezóny vybraného seriálu s pořadím 5 v souboru input_tvSeasons.txt: Data přidané sezóny seriálu jsou duplicitní
Chybový stav sezóny vybraného seriálu s pořadím 6 v souboru input_tvSeasons.txt: Pořadí přidané sezóny seriálu musí být větší než nula
```

### Vstupní soubor s TV epizodami

- Představuje textový soubor [input_tvEpisodes.txt](/star-wars-media-content-management/data/input_tvEpisodes.txt)
- Binární [input_tvEpisodes.bin](/star-wars-media-content-management/data/input_tvEpisodes.bin) má úplně totožný obsah jako textový soubor
- Zde je uvedena tabulka pro TV epizody:

| **Pořadí TV sezóny v souboru** | **Typ testu** | **Očekávaný výsledek** | **Skutečný výsledek** | **Prošel (ano/ne)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 2 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 3 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 4 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 5 | Nevalidní vstup | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  procentuálnímu hodnocení TV epizody, které překračuje hranici 100 | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli  procentuálnímu hodnocení TV epizody, které překračuje hranici 100 | ano |
| 6 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 7 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 8 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 9 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejné pořadí TV epizody v rámci TV sezóny jako u TV  epizody s pořadím 8 v souboru) | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (stejný název a stejné  datum vydání jako u TV  seriálu s pořadím 8) | ano |
| 10 | Limitní stav | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 11 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli délce TV epizody v sekundách,  která je záporná, přičemž  zadání délky je povinné | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli délce TV epizody v sekundách,  která je záporná, přičemž  zadání délky je povinné | ano |
| 12 | Limitní stav | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 13 | Limitní stav | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 14 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli délce TV epizody v sekundách,  které není zadána jako celé číslo | Neúspěšné nahrání ze souboru  kvůli délce TV epizody v sekundách,  které není zadána jako celé číslo | ano |
| 15 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli procentuálnímu hodnocení TV epizody,  které není zadáno jako celé číslo | Neúspěšné nahrání ze souboru  kvůli procentuálnímu hodnocení TV epizody,  které není zadáno jako celé číslo | ano |
| 16 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli pořadí TV epizody v rámci TV sezóny,  které není zadáno jako celé číslo | Neúspěšné nahrání ze souboru  kvůli pořadí TV epizody v rámci TV sezóny,  které není zadáno jako celé číslo | ano |
| 17 | Nevalidní vstup | Neúspěšné nahrání ze souboru  kvůli pořadí TV epizody v rámci TV sezóny,  které není zadáno | Neúspěšné nahrání ze souboru  kvůli pořadí TV epizody v rámci TV sezóny,  které není zadáno | ano |
| 18 | Běžná hodnota | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | Úspěšné nahrání ze souboru a  následné úspěšné přidání do databáze | ano |
| 19 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (tato TV epizoda má duplicitní URL odkaz ke zhlédnutí jako  TV epizoda s pořadím 18 v souboru) | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (tato TV epizoda má duplicitní URL odkaz ke zhlédnutí jako  TV epizoda s pořadím 18 v souboru) | ano |
| 20 | Limitní stav | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (tato TV epizoda má duplicitní krátké shrnutí obsahu jako  TV epizoda s pořadím 18 v souboru) | Úspěšné nahrání ze souboru ale neúspěšné nahrání  do databáze kvůli duplicitě dat  (tato TV epizoda má duplicitní krátké shrnutí obsahu jako  TV epizoda s pořadím 18 v souboru) | ano |

- Zde je konzolový výstup uživatelské funkce pro přidávání nových TV epizod pro vybranou TV sezónu:

```
Informační zpráva: Celkově se podařilo nahrát 11 epizod vybrané sezóny do databáze a naopak se nepodařilo nahrát 5 epizod vybrané sezóny
Chybový stav epizody vybrané sezóny s pořadím 5 v souboru input_tvEpisodes.txt: Procentuální hodnocení zhlédnuté přidané epizody sezóny musí být v rozsahu 0 - 100
Chybový stav epizody vybrané sezóny s pořadím 9 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
Chybový stav epizody vybrané sezóny s pořadím 11 v souboru input_tvEpisodes.txt: Přidaná epizoda sezóny musí mít délku trvání
Chybový stav epizody vybrané sezóny s pořadím 19 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
Chybový stav epizody vybrané sezóny s pořadím 20 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
```

# Popis fungování externí knihovny

- Jedná se o knihovnu *Apache Commons Email*
- Umožňuje následující věci:
    - Zjednodušuje existující *Java Mail API*
    - Autentifikovat se k *SMTP* serveru, pokud je to potřeba
    - Pro komunikaci přes *SMTP* protokol umožňuje vybrat mezi *SSL* a *STARTTLS* šifrovacími protokoly na základě čísla portu
    - Nastavení kódování obsahu e-mailu
    - Nastavení příjemce, předmětu a obsahu e-mailu
    - Nastavení e-mailu, na který se budou posílat nedoručitelné e-maily
    - Posílání jednoduchých textových e-mailů
    - Posílání e-mailů formátovaných v *HTML*
    - Posílání e-mailů s přílohami
    - Posílání *HTML* e-mailů s inline obrázky (data obrázku z externího *URL* odkazu se stáhnou a vloží přímo do e-mailu)
- V aplikaci se knihovna používá pro odesílání dat z databáze pomocí *HTML* e-mailu a to ze tří důvodů:
    - Lepší formátovaný výpis
    - Možnost v e-mailovém klientovi různě filtrovat a řadit doručené e-maily, protože mají nastavený standardizovaný předmět zprávy
    - Umožnit data s URL odkazem mít uložený přímo v e-mailu např. jako seznam nezhlédnutých filmů
- V aplikaci je knihovna implementovaná ve třídě [EmailSender](/star-wars-media-content-management/src/main/java/utils/emailsender/EmailSender.java)

## Detaily implementace knihovny ve třídě EmailSender

1. Nejdříve je potřeba nastavit konfigurační údaje:

```java
public class EmailSender 
{   
    private final int smtpPort = 465;
    
    private final String hostName = "smtp.googlemail.com";
    
    private final String randomGeneratedAppToken = "qnaadtxcznjyvzln";
    
    private final String appId = "honzaswtor";
}
```

- ***smtpPort*** - Vyjadřuje číslo portu SMTP protokolu v rámci transportní vrstvy
    - Hodnota byla nastavena na ***465***, aby bylo umožněno později v implementaci použít šifrovací protokol ***SSL***
- ***hostName*** - Vyjadřuje *SMTP* server, který bude zodpovědný za odesílání e-mailů
    - V závislosti na zvoleném *SMTP* serveru existuje možnost, že se bude potřeba autentifikovat
    - Konkrétní způsob přihlašování si specifikuje každý *SMTP* server individuálně
    - V případě zvoleného **Google SMTP serveru** byly použity autentifikační údaje zapsané v atributech *appId* a *randomGeneratedAppToken*
        - K autentifikaci se používá **G-mail účet** 
        - Vysvětlení způsobu přihlašování k Google SMTP serveru a popis autentifikačních údajů není z bezpečnostního důvodu uveden

2. Poté je potřeba sestavit e-mail:

```java
public void sendEmail(String recipientEmailAddress, String subject, StringBuilder message) throws EmailException
{
    try 
    {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(hostName);
        email.setCharset(org.apache.commons.mail.EmailConstants.UTF_8);
        email.setSmtpPort(smtpPort);
        email.setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken));
        email.setSSLOnConnect(true);
        email.setFrom(DataStore.getAppCreator());
        email.setSubject(subject);
        email.addTo(recipientEmailAddress);
        email.setHtmlMsg(message.toString());
        email.send();  
    }
    catch (EmailException ex) 
    {
        throw new EmailException("Chyba při odesílání přes internet nebo evidentně neplatná e-mailová adresa příjemce");
    }
}
```

- ***HtmlEmail*** - Vyjadřuje instanci, která podporuje formátování e-mailu jako **HTML**
- ***setHostName(hostName)*** - Nastaví odesílací SMTP server specifikovaný v **1. kroku implementace s konfiguračními údaji**
- ***setCharset(org.apache.commons.mail.EmailConstants.UTF_8)*** - Nastaví kódování HTML obsahu zprávy jako UTF-8, aby **data s diaktritikou se správně vypisovala**
- ***setSmtpPort(smtpPort)*** - Nastaví číslo portu specifikované v **1. kroku implementace s konfiguračními údaji**
    - Číslo určí i podporovaný šifrovací protokol
- ***setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken))*** - Nastaví autentifikační údaje specifikované v **1. kroku implementace s konfiguračními údaji** pro daný SMTP server
- ***setSSLOnConnect(true)*** - Nastaví šifrovací protokol **SSL**
    - SSL je nastavováno, protože číslo portu bylo specifikováno jako **465**, takže bez šifrování by posílání e-mailů bylo nebezpečné
- ***setFrom(DataStore.getAppCreator())*** - Nastaví odesílatele e-mailu jako tvůrce aplikace, což je **honzaswtor@gmail.com**
    - Pokud se odesílatel specifikuje jako **neplatný e-mail**, tak se pouzije **e-mail autentifikovaného účtu k SMTP serveru**
- ***setSubject(subject)*** - Nastaví předmět zprávy
- ***addTo(recipientEmailAddress)*** - Nastaví příjemce zprávy
    - Pokud nastane chyba v síti při odesílání e-mailu nebo e-mail příjemce je neplatný, tak se vyhodí výjimka **EmailException**
    - Výjimka pochází z **externí knihovny**
- ***setHtmlMsg(message.toString())*** - Nastaví obsah zprávy e-mailu a bude ho interpretovat jako **HTML**
- ***send()*** - Odešle sestavenou zprávu
