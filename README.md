# Instructions for starting app

1. First open this app as **Maven** project through IDE such as **Apache Netbeans** etc.
2. Build the app so the dependencies and libraries specified in [pom.xml](/star-wars-media-content-management/pom.xml) **are downloaded**
3. Run the file representing the class called [ApplicationRunner](/star-wars-media-content-management/src/main/java/ui/ApplicationRunner.java), **located in the package ui**

# Project assignment

## Motivation

From the age of 8 I have become very interested in Star Wars in all aspects.

It started with building only Star Wars LEGO sets, these sets were placed on display all over my bedroom. I currently have about 20 sets on display, but I'm sure I've built more than 20, there just wasn't the space available to display them, so after a while I had to disassemble some kits and put them in a box of sorted LEGOs located in my bedroom.

Next I became interested in TV shows, movies, books, music and comics. Later, as I got older, I started playing PC games, with 95% of all games being Star Wars games. At the same time I started collecting lightsabers (Hasbro, indie manufacturers) of various types (plastic, LED).

Lastly, I attended the screenings of the new Star Wars movies with my family in 2015, 2016, 2017, 2018 and 2019. The atmosphere was amazing, only after a while I realized that the scripts of the 2015, 2017 and 2019 movies were terrible, while the scripts of the 2016 and 2018 movies were pretty OK.

So, to sum up. Star Wars is a special interest of mine, and it seemed like a good idea to use programming as a tool to create a sort of "helper" for Star Wars shows and movies.

## Problem description

The *Star Wars Media Content Management* application should serve as **the inventory of media content (TV shows, movies) within the Star Wars universe only**.

The app is designed for users who are **advanced Star Wars fans (geeks)**, but is also suitable for **beginner fans**. **For the normal user**, this app **is useless**.

The goal of the app is to allow the fan to:
- Organize stored media content into **chronological periods within the Star Wars universe**
- Organize stored media content by whether it is **watched**, **unwatched** or **announced**
- Store media content in **input/output files**, which represent a **database**, for **reading data from the files the next time the application is run**
- Perform manipulative operations with the stored media content such as:
    - Multiple and individual addition of stored media content via **input files**
    - Multiple and individual deletion of stored media content
    - Modification of **one** selected stored media content using **input file**
    - List the details of one selected stored media content
    - Rate stored media content, **even repeatedly**
- Perform manipulative operations with data files such as:
    - List the contents of input/output files in the application
    - List the contents of input files in the application
    - Choice to work with text or binary files in the application
- Provide aggregate/statistical data based on stored media content **in individual chronological periods** or **in individual TV shows** such as:
    - Count of media content
        - Divided into total, watched and unwatched count
    - Duration of media content
        - Divided into total, watched and unwatched duration
    - Average rating of media content
    - Average duration of media content
        - Divided into total, watched and unwatched average duration
- Simplify and plan **movies or TV shows marathon of unseen media content** based on different criteria:
    - Repeatable sending of an email **containing a formatted listing** of unwatched media content **with hyperlinks intended for watching specific media content**
        - Allowing sending an email with movies sorted **by oldest release date** or **by chronological periods**
        - The email subject is **standardized**
    - Allowing to list **sorted** media content **in a selected chronological era** by:
        - Alphabetically by name
        - As of the latest release date
        - From the longest duration
    - Providing statistical data **for each chronological era** or **for each TV show** to **better visualize marathon progress**
- Search for stored media content **by name**
- List media content according to **rankings** such as:
    - Most popular
    - Longest
    - Latest

# Solution

## Functional specifications

In terms of user functions, the application is divided into the following three parts/menus:

### Data directory setup menu

- It is displayed at the beginning of the application, then no longer
- Used to set/configure the path to the **data** directory located on the disk 
    - The directory contains the data input and input/output files specified [here](#description-of-the-structure-of-input-and-output-files)
- It is possible to exit the application using **function with number 0**

The user functions themselves are as follows:

---

1. Zadat cestu k data adresáři

---

### Input/output files loading menu

- Appears after [data directory setup menu](#data-directory-setup-menu), then no more
- Used to read existing/stored data into the application from input/output files
- The purpose is that the application continuously stores its new data in input/output files, so that the data can be easily restored the next time the application is run without having to insert the data again
- It is possible to exit the application using **function with number 0**

The user functions themselves are as follows:

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

### Main menu

- Appears after [input/output files loading menu](#inputoutput-files-loading-menu) and repeatedly during application runtime
- The main menu is multi-level, so the functions in the list are indented according to the hiearchical level in which they are located
- Sublevels/submenus of the main menu are also displayed repeatedly
- In the main menu it is possible to exit the application using **function with number 0**
- Each submenu has a **function with number 0** that allows to return from the current submenu/sublevel to the parent level/menu

The user functions themselves are as follows:


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


## Description of the structure of input and output files

The following media content database can be used to retrieve individual input data https://www.imdb.com/

### Input text file with movies

- Must be created manually or from some other external source
- Used to add new movies to the database or modification of an existing movie in the database

#### Requirements

- The file name must be **input_movies.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input movie** looks like this:

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

- The names of individual data/attributes are used in [file structure description](#file-structure-description)
- The descriptions of each data/attributes names are as follows:
    - ***runtimeInSeconds*** – Expresses the length/duration of the movie in seconds
        - This is the **long** data type, which is an **integer**
        - **Not mandatory**
            - If **not specified**, the value **must be equal to or less than 0**
            - If **is specified**, the value **must be in the range of 1 or more**
    - ***name*** – Expresses the title of the movie
        - This is the **String** data type, which is a **text**
        - **Is mandatory**
            - It is not acceptable to have the value **blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters in the name is **60**
    - ***percentageRating*** – Expresses the percentage rating of the movie
        - This is the **int** data type, which is an **integer**
        - **Not mandatory**
            - If **not specified**, value **must be less than 0**
                - When not specified, the movie is identified as **unwatched**
            - If **is specified**, the value **must be in the range 0 to 100**
                - When is specified, the movie is identified as **watched**
    - ***hyperlinkForContentWatch*** – Expresses the URL link to watch the movie
        - This is the **String** data type, which is a **text**
        - **Not mandatory**
            - If **not specified**, the value **must be blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters of a URL link is **180**
    - ***shortContentSummary*** – Expresses a brief summary of the movie's content
        - This is the **String** data type, which is a **text**
        - **Not mandatory**
            - If **not specified**, the value **must be blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters in the summary is **1000**
    - ***releaseDateInEpochSeconds*** – Expresses the release date of the movie, specified in epoch seconds
        - This is the **long** data type, which is an **integer**
        - **Not mandatory**
            - If **not specified**, the value **must be less than 0**
                - When not specified, the movie is identified as **announced**
            - If **is specified**, the value **must be in the range of 0 or more**
                - When is specified, the movie is identified as **released**
        - To convert date to epoch seconds and vice versa, this converter can be used https://www.epochconverter.com/
            - When converting, it is required to select **GMT/UTC** as the time zone
    - ***eraCodeDesignation*** – Expresses the codename of the selected chronological Star Wars era for the movie
        - This is the **String** data type, which is a **text**
        - **Is mandatory**
            - It is not acceptable to have the value **blank ("") or filled with blank spaces or missing in the file**
        - The value **must** have one of the following code designations:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - The chronological eras are described more directly **in the form of a user function in the app** or on this website https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- It is not possible for two movies to have **the same name and the same release date**
- It is not possible for two movies to have **the same URL link for watching or the same summary of content**

#### File structure description

- The file should look something like this for **one input movie**:

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

- There can be **more than one input movie** in a file
    - Just **after the *\[Values]* section of the previous movie** place **again the *\[Attributes]* section and then again the *\[Values]* section of the next movie**
    - ***\[End\]*** remains unchanged, which means that in the file only once and at the end of the file
- ***\[Attributes\]*** - Expresses a control character for detecting a section with individual data/attributes names and link numbers
    - **Must be in the file**
    - ***Order:*** – Expresses the order of the movie in terms of its position in the file
        - **Doesn't have to be in the file**, information purpose only
    - **Individual data/attributes names with linking numbers in the *\[Attributes\]*** section represent the input data of the movie
        - **Doesn't have to be in the file**, information purpose only
        - The individual data/attributes names are specified in the [file data structure description](#file-data-structure-description)
        - The data/attributes names themselves with linking numbers in the *\[Attributes\]* section express the pattern/prescription **how to separate data/attributes values in the *\[Values\]*** section, which is:
            - **Data/attribute name** – The file line starts with the value of a specific data/attribute
            - **Space** – Expresses the separator between the data/attribute value and the link number
            - **Link number** – Expresses the connection to which specific data/attribute the value should be assigned
- ***\[Values\]*** – Expresses a control character for detecting a section with individual data/attributes values and link numbers
    - **Must be in the file**
    - The separation of data/attributes values with link numbers follows the pattern/prescription **in section *\[Attributes\]***
    - The data types of data/attributes values are specified in the [file data structure description](#file-data-structure-description)
    - It is possible to write a value with a linking number on **multiple lines**, but after reading the file such a value from **multiple lines will be concatenated into one line**
        - The exception is the value of the attribute/data ***shortContentSummary***, when after reading the file the value **from multiple rows will be concatenated again into multiple rows**
- ***\[End\]*** – Expresses a control character for detecting the end of reading input data
    - If there is any text after ***\[End\]***, it will be ignored
        - This mechanism can be used when **editing** the data of an existing movie, where there may be 20 movies in the file and the ***\[End\]*** character is placed between the 1st and 2nd movie, so that only the 1st movie is read, the rest is ignored

### Input binary file with movies

- Must be created manually or from some other external source
- Used to add new movies to the database or edit an existing movie in the database

#### Requirements

- The file name must be **input_movies.bin**
- Since input files can be from external sources, it is required that this file is created by converting from [input text file with movies](#input-text-file-with-movies)
    - When converting, it is required to select the encoding as **UTF-8**
    - You can use this converter to convert https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### File data structure description

- The structure is exactly the same as in [data structure description of input text file with movies](#file-data-structure-description)

#### File structure description

- The structure is exactly the same as in [structure description of input text file with movies](#file-structure-description)

### Input/output text file with movies

- The file is created automatically by the application
- Used as a storage of already existing/stored movies for loading into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_movies.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input/output movie** looks like this:

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

- The names of the individual data/attributes are used in the [file structure description](#file-structure-description-2)
- The descriptions of the individual data/attributes names are identical to [data structure description of the input text file with movies](#file-data-structure-description)
- But there are the following differences:
    - Data/attributes of data type **String** **are changed** to **char[]**, but **it is still text**
    - **Newly** limits on the maximum count of characters for selected attributes no longer **exist**
    - **Newly** there is an attribute ***id***
        - Expresses the identifier of the movie within the database
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - There is **newly** the rule that there cannot be two movies that have **the same identifier**

#### File structure description

- The file should look something like this for **one input/output movie**:

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

- The description of the file structure is the same as in [structure description of input text file with movies](#file-structure-description)
- Values data types and data/attributes names are specified in [file data structure description](#file-data-structure-description-2)
- But there are the following differences:
    - In the *\[Attributes\]* section, ***Order*** **changes** to ***Identificator***
        - This represents the identifier of the movie in the database
        - **Doesn't have to be in the file**, information purpose only
    - **Newly** **it is not possible** to use the control character ***\[End\]*** to **edit** the data of one existing movie
        - The input text file with movies can be used for **editing** 
    - **Newly** movies in the file are **sorted in ascending order based on the movie identifier**

### Input/output binary file with movies

- The file is created automatically by the application
- Used as a storage of already existing/stored movies for loading into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_movies.bin**

#### File data structure description

- The structure is exactly the same as in [data structure description of input/output text file with movies](#file-data-structure-description-2)
- To read text/string values of movie attributes from a binary file, all such values are set to a fixed length before writing to the file:
    - ***name*** – 60 characters
    - ***hyperlinkForContentWatch*** – 180 characters
    - ***shortContentSummary*** – 1000 characters
    - ***eraCodeDesignation*** – 60 characters

#### File structure description

- The individual data for each movie are written and read in the following order:

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

- The text/string values of the movie attributes are written on a character-by-character basis
- This order will be repeated for multiple movies

### Input text file with TV shows

- Must be created manually or from some other external source
- Used to add new TV shows to the database or edit an existing TV show in the database

#### Requirements

- The file name must be **input_tvShows.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input TV show** looks like this:

```java
public class TVShowInput 
{
    private final String name;
    
    private final long releaseDateInEpochSeconds;
    
    private final String eraCodeDesignation;
}
```

- The names of individual data/attributes are used in [file structure description](#file-structure-description-4)
- The descriptions of each data/attributes names are as follows:
    - ***name*** – Expresses the title of the TV show
        - This is the **String** data type, which is a **text**
        - **Is mandatory**
            - It is not acceptable to have the value **blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters in the name is **60**
    - ***releaseDateInEpochSeconds*** – Expresses the release date of the TV show, specified in epoch seconds
        - This is the **long** data type, which is an **integer**
        - **Not mandatory**
            - If **not specified**, the value **must be less than 0**
                - When not specified, the TV show is identified as **announced**
            - If **is specified**, the value **must be in the range of 0 or more**
                - When is specified, the TV show is identified as **released**
        - To convert date to epoch seconds and vice versa, this converter can be used https://www.epochconverter.com/
            - When converting, it is required to select **GMT/UTC** as the time zone
    - ***eraCodeDesignation*** – Expresses the codename of the selected chronological Star Wars era for a given TV show
        - This is **String** data type, which is **text**
        - **Is mandatory**
            - It is not acceptable to have the value **blank ("") or filled with blank spaces or missing in the file**
        - The value **must** have one of the following code designations:
            - DAWN_OF_THE_JEDI
            - THE_OLD_REPUBLIC
            - THE_HIGH_REPUBLIC
            - FALL_OF_THE_JEDI
            - REIGN_OF_THE_EMPIRE
            - AGE_OF_REBELLION
            - THE_NEW_REPUBLIC
            - RISE_OF_THE_FIRST_ORDER
            - NEW_JEDI_ORDER
        - The chronological eras are described more directly **in the form of a user function in the app** or on this website https://www.screengeek.net/2023/04/07/star-wars-timeline-eras
- It's not possible for there to be two TV shows that have **the same name and the same release date**

#### File structure description

- The file should look something like this for **one input TV show**:

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

- There can be **more than one input TV show** in the file
    - Just **after the *\[Values\]* section of the previous TV show** place **again the *\[Attributes\]* section and then again the *\[Values\]* section of the following TV show**
    - ***\[End\]*** remains unchanged, which means that in the file only once and at the end of the file
- ***\[Attributes\]*** - Expresses a control character for detecting a section with individual data/attributes names and link numbers
    - **Must be in the file**
    - ***Order:*** – Expresses the order of the TV show in terms of its position in the file
        - **Doesn't have to be in the file**, information purpose only
    - **Individual data/attributes names with linking numbers in the *\[Attributes\]*** section represent the input data of the TV show
        - **Doesn't have to be in the file**, information purpose only
        - The individual data/attributes names are specified in [file data structure description](#file-data-structure-description-4)
        - The data/attributes names themselves with linking numbers in the *\[Attributes\]* section express the pattern/prescription **how to separate data/attributes values in the *\[Values\]*** section, which is:
            - **Data/attribute name** – The file line starts with the value of a specific data/attribute
            - **Space** – Expresses the separator between the data/attribute value and the link number
            - **Link number** – Expresses the connection to which specific data/attribute the value should be assigned
- ***\[Values\]*** - Expresses a control character for detecting a section with individual data/attributes values and link numbers
    - **Must be in the file**
    - The separation of data/attributes values with link numbers follows the pattern/prescription **in section *\[Attributes\]***
    - The data types of data/attributes values are specified in the [file data structure description](#file-data-structure-description-4)
    - It is possible to write a value with a linking number on **multiple lines**, but after reading the file such a value from **multiple lines will be concatenated into one line**
- ***\[End\]*** – Expresses a control character for detecting the end of reading input data
    - If there is any text after ***\[End\]***, it will be ignored
        - This mechanism can be used when **editing** the data of an existing TV show, where there may be 20 TV shows in the file and the ***\[End\]*** character is placed between the 1st and 2nd TV show, so that only the 1st TV show is read, the rest is ignored

### Input binary file with TV shows

- Must be created manually or from some other external source
- Used to add new TV shows to the database or edit an existing TV show in the database

#### Requirements

- The file name must be **input_tvShows.bin**
- Since input files can be from external sources, it is required that this file is created by converting from [input text file with TV shows](#input-text-file-with-tv-shows)
    - When converting, it is required to select the encoding as **UTF-8**
    - You can use this converter to convert https://www.rapidtables.com/convert/number/ascii-to-binary.html
 
#### File data structure description

- The structure is exactly the same as in [data structure description of input text file with TV shows](#file-data-structure-description-4)

#### File structure description

- The structure is exactly the same as in [structure description of input text file with TV shows](#file-structure-description-4)

### Input/output text file with TV shows

- The file is created automatically by the application
- Used as a storage of already existing/stored TV shows for loading into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvShows.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input/output TV show** looks like this:

```java
public class TVShowInputOutput
{
    private final int id;
    
    private final char[] name;
    
    private final long releaseDateInEpochSeconds;
    
    private final char[] eraCodeDesignation;
}
```

- The names of the individual data/attributes are used in the [file structure description](#file-structure-description-6)
- The descriptions of the individual data/attributes names are identical to [data structure description of the input text file with TV shows](#file-data-structure-description-4)
- But there are the following differences:
    - Data/attributes of data type **String** **are changed** to **char[]**, but **it is still text**
    - **Newly** limits on the maximum count of characters for selected attributes no longer **exist**
    - **Newly** there is an attribute ***id***
        - Expresses the identifier of the TV show within the database
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - There is **newly** the rule that there cannot be two TV shows that have **the same identifier**

#### File structure description

- The file should look something like this for **one input/output TV show**:

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

- The description of the file structure is the same as in [structure description of input text file with TV shows](#file-structure-description-4)
- Values data types and data/attributes names are specified in [file data structure description](#file-data-structure-description-6)
- But there are the following differences:
    - In the *\[Attributes\]* section, ***Order*** **changes** to ***Identificator***
        - This represents the identifier of the TV show in the database
        - **Doesn't have to be in the file**, information purpose only
    - **Newly** **it is not possible** to use the control character ***\[End\]*** to **edit** the data of one existing TV show
        - The input text file with TV shows can be used for **editing**
    - **Newly** TV shows in the file are **sorted in ascending order based on the TV show identifier**

### Input/output binary file with TV shows

- The file is created automatically by the application
- Used as a storage of already existing/stored TV shows for loading into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvShows.bin**

#### File data structure description

- The structure is exactly the same as in [data structure description of input/output text file with TV shows](#file-data-structure-description-6)
- To read text/string values of TV show attributes from a binary file, all such values are set to a fixed length before writing to the file:
    - ***name*** – 60 characters
    - ***eraCodeDesignation*** – 60 characters

#### File structure description

- The individual data for each TV show are written and read in the following order:

---

1. *id*
2. *name*
3. *releaseDateInEpochSeconds*
4. *eraCodeDesignation*

---

- The text/string values of the TV show attributes are written on a character-by-character basis
- This order will be repeated for multiple TV shows

### Input text file with TV seasons

- Must be created manually or from some other external source
- Used to add new TV seasons for a selected TV show to the database or edit an existing TV season in the database

#### Requirements

- File name must be **input_tvSeasons.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input TV season** looks like this:

```java
public class TVSeasonInput 
{    
    private final int orderInTVShow;
}
```

- The names of individual data/attributes are used in [file structure description](#file-structure-description-8)
- The descriptions of each data/attributes names are as follows:
    - ***orderInTVShow*** – Expresses the order of the TV season within the respective TV show
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - Value **must be in the range of 1 or more**
- It's not possible for there to be two TV seasons within the same TV show that have **the same order**

#### File structure description

- The file should look something like this for **one input TV season**:

```
[Attributes]

Order: 1

orderInTVShow 1

[Values]

1 1

[End]
```

- There can be **more than one input TV season** in a file
    - Just **after the *\[Values]* section of the previous TV season** place **again the *\[Attributes]* section and then again the *\[Values]* section of the following TV season**
    - ***\[End\]*** remains unchanged, which means that in the file only once and at the end of the file
- ***\[Attributes\]*** – Expresses a control character for detecting a section with individual data/attributes names and link numbers
    - **Must be in the file**
    - ***Order:*** – Expresses the order of the TV season in terms of its position in the file
        - **Doesn't have to be in the file**, information purpose only
    - **The individual data/attributes names with linking numbers in the *\[Attributes\]*** section represent the input data of the TV season
        - **Doesn't have to be in the file**, information purpose only
        - The individual data/attributes names are specified in [file data structure description](#file-data-structure-description-8)
        - The data/attributes names themselves with linking numbers in the *\[Attributes\]* section express the pattern/prescription **how to separate data/attributes values in the *\[Values\]*** section, which is:
            - **Data/attribute name** – The file line starts with the value of a specific data/attribute
            - **Space** – Expresses the separator between the data/attribute value and the link number
            - **Link number** – Expresses the connection to which specific data/attribute the value should be assigned
- ***\[Values\]*** – Expresses a control character for detecting a section with individual data/attributes values and link numbers
    - **Must be in the file**
    - The separation of data/attributes values with link numbers follows the pattern/prescription **in section *\[Attributes\]***
    - The data types of data/attributes values are specified in [file data structure description](#file-data-structure-description-8)
    - It is possible to write a value with a linking number on **multiple lines**, but after reading the file such a value from **multiple lines will be concatenated into one line**
- ***\[End\]*** – Expresses a control character for detecting the end of reading input data
    - If there is any text after ***\[End\]***, it will be ignored
        - This mechanism can be used when **editing** the data of an existing TV season, where there may be 20 TV seasons in the file and the ***\[End\]*** character is placed between the 1st and 2nd TV season, so that only the 1st TV season is read, the rest is ignored

### Input binary file with TV seasons

- Must be created manually or from some other external source
- Used to add new TV seasons for a selected TV show to the database or edit an existing TV season in the database

#### Requirements

- The file name must be **input_tvSeasons.bin**
- Since input files can be from external sources, it is required that this file is created by converting from [input text file with TV seasons](#input-text-file-with-tv-seasons)
    - When converting, it is required to select the encoding as **UTF-8**
    - You can use this converter to convert https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### File data structure description

- The structure is exactly the same as in [data structure description of input text file with TV seasons](#file-data-structure-description-8)

#### File structure description

- The structure is exactly the same as in [structure description of input text file with TV seasons](#file-structure-description-8)

### Input/output text file with TV seasons

- The file is created automatically by the application
- Used as a storage of already existing/stored TV seasons from different TV shows to be loaded into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvSeasons.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input/output TV season** looks like this:

```java
public class TVSeasonInputOutput
{    
    private final int id;
    
    private final int orderInTVShow;
    
    private final int tvShowId;
}
```

- The names of the individual data/attributes are used in the [file structure description](#file-structure-description-10)
- The descriptions of the individual data/attributes names are identical to [data structure description of input text file with TV seasons](#file-data-structure-description-8)
- But there are the following differences:
    - **Newly** there is an attribute ***id***
        - Expresses the identifier of the TV season within the database
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - **Newly** there is an attribute ***tvShowId***
        - Expresses the identifier of the respective TV show for the given TV season
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - There is **newly** the rule that there cannot be two TV seasons that have **the same identifier**
    - There is **newly** the rule that there cannot be the TV season whose **TV show identifier does not refer to any existing TV show**

#### File structure description

- The file should look something like this for **one input/output TV season**:

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

- The description of the file structure is the same as in [structure description of input text file with TV seasons](#file-structure-description-8)
- Values data types and data/attributes names are specified in [file data structure description](#file-data-structure-description-10)
- But there are the following differences:
    - In the *\[Attributes\]* section, ***Order*** **changes** to ***Identificator***
        - This represents the identifier of the TV season in the database
        - **Doesn't have to be in the file**, information purpose only
    - **Newly** **it is not possible** to use the control character ***\[End\]*** to **edit** the data of one existing TV season
        - The input text file with TV seasons can be used for **editing**
    - **Newly** TV seasons in the file are **sorted in ascending order based on the TV season identifier**

### Input/output binary file with TV seasons

- The file is created automatically by the application
- Used as a storage of already existing/stored TV seasons from different TV shows to be loaded into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvSeasons.bin**

#### File data structure description

- The structure is exactly the same as in [data structure description of input/output text file with TV seasons](#file-data-structure-description-10)

#### File structure description

- The individual data for each TV season are written and read in the following order:

---

1. *id*
2. *orderInTVShow*
3. *tvShowId*

---

- This order will be repeated for multiple TV seasons

### Input text file with TV episodes

- Must be created manually or from some other external source
- Used to add new TV episodes for a selected TV season to the database or edit an existing TV episode in the database

#### Requirements

- The file name must be **input_tvEpisodes.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input TV episode** looks like this:

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

- The names of individual data/attributes are used in [file structure description](#file-structure-description-12)
- The descriptions of each data/attributes names are as follows:
    - ***runtimeInSeconds*** – Expresses the length/duration of the TV episode in seconds
        - This is the **long** data type, which is an **integer**
        - **Is mandatory**
        - The value **must be in the range of 1 or more**
    - ***name*** – Expresses the title of the TV episode
        - This is the **String** data type, which is a **text**
        - **Not mandatory**
            - If **not specified**, the value **must be blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters in the name is **60**
    - ***percentageRating*** – Expresses the percentage rating of the TV episode
        - This is the **int** data type, which is an **integer**
        - **Not mandatory**
            - If **not specified**, value **must be less than 0**
                - When not specified, the TV episode is identified as **unwatched**
            - If **is specified**, the value **must be in the range 0 to 100**
                - When is specified, the TV episode is identified as **watched**
    - ***hyperlinkForContentWatch*** –  Expresses the URL link to watch the TV episode
        - This is the **String** data type, which is a **text**
        - **Not mandatory**
            - If **not specified**, the value **must be blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters of a URL link is **180**
    - ***shortContentSummary*** – Expresses a brief summary of the TV episode's content
        - This is the **String** data type, which is a **text**
        - **Not mandatory**
            - If **not specified**, the value **must be blank ("") or filled with blank spaces or missing in the file**
        - The maximum count of characters in the summary is **1000**
    - ***orderInTVShowSeason*** – Expresses the order of the TV episode within the respective TV season
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **must be in the range of 1 or more**
- It is not possible for two TV episodes within the same TV season to have **the same order**
- It is not possible for two TV episodes to have **the same URL link for watching or the same summary of content**

#### File structure description

- The file should look something like this for **one input TV episode**:

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

- There can be **more than one input TV episode** in a file
    - Just **after the *\[Values]* section of the previous TV episode** place **again the *\[Attributes]* section and then again the *\[Values]* section of the next TV episode**
    - ***\[End\]*** remains unchanged, which means that in the file only once and at the end of the file
- ***\[Attributes\]*** –⁠ Expresses a control character for detecting a section with individual data/attributes names and link numbers
    - **Must be in the file**
    - ***Order:*** –⁠ Expresses the order of the TV episode in terms of its position in the file
        - **Doesn't have to be in the file**, information purpose only
    - **Individual data/attributes names with linking numbers in the *\[Attributes\]*** section represent the input data of the TV episode
        - **Doesn't have to be in the file**, information purpose only
        - The individual data/attributes names are specified in the [file data structure description](#file-data-structure-description-12)
        - The data/attributes names themselves with linking numbers in the *\[Attributes\]* section express the pattern/prescription **how to separate data/attributes values in the *\[Values\]*** section, which is:
            - **Data/attribute name** – The file line starts with the value of a specific data/attribute
            - **Space** – Expresses the separator between the data/attribute value and the link number
            - **Link number** – Expresses the connection to which specific data/attribute the value should be assigned
- ***\[Values\]*** –⁠ Expresses a control character for detecting a section with individual data/attributes values and link numbers
    - **Must be in the file**
    - The separation of data/attributes values with link numbers follows the pattern/prescription **in section *\[Attributes\]***
    - The data types of data/attributes values are specified in the [file data structure description](#file-data-structure-description-12)
    - It is possible to write a value with a linking number on **multiple lines**, but after reading the file such a value from **multiple lines will be concatenated into one line**
        - The exception is the value of the attribute/data ***shortContentSummary***, when after reading the file the value **from multiple rows will be concatenated again into multiple rows**
- ***\[End\]*** –⁠ Expresses a control character for detecting the end of reading input data
    - If there is any text after ***\[End\]***, it will be ignored
        - This mechanism can be used when **editing** the data of an existing TV episode, where there may be 20 TV episodes in the file and the ***\[End\]*** character is placed between the 1st and 2nd TV episode, so that only the 1st TV episode is read, the rest is ignored

### Input binary file with TV episodes

- Must be created manually or from some other external source
- Used to add new TV episodes for a selected TV season to the database or edit an existing TV episode in the database

#### Requirements

- The file name must be **input_tvEpisodes.bin**
- Since input files can be from external sources, it is required that this file is created by converting from [input text file with TV episodes](#input-text-file-with-tv-episodes)
    - When converting, it is required to select the encoding as **UTF-8**
    - You can use this converter to convert https://www.rapidtables.com/convert/number/ascii-to-binary.html

#### File data structure description

- The structure is exactly the same as in [data structure description of input text file with TV episodes](#file-data-structure-description-12)

#### File structure description

- The structure is exactly the same as in [structure description of input text file with TV episodes](#file-structure-description-12)

### Input/output text file with TV episodes

- The file is created automatically by the application
- Used as a storage of already existing/stored TV episodes from different TV seasons to be loaded into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvEpisodes.txt**
- File encoding must be **UTF-8**

#### File data structure description

- The data of **one input/output TV episode** looks like this:

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

- The names of the individual data/attributes are used in the [file structure description](#file-structure-description-14)
- The descriptions of the individual data/attributes names are identical to [data structure description of input text file with TV episodes](#file-data-structure-description-12)
- But there are the following differences:
    - Data/attributes of data type **String** **are changed** to **char[]**, but **it is still text**
    - **Newly** limits on the maximum count of characters for selected attributes no longer **exist**
    - **Newly** there is an attribute ***id***
        - Expresses the identifier of the TV episode within the database
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - **Newly** there is an attribute ***tvSeasonId***
        - Expresses the identifier of the respective TV season for the given TV episode
        - This is the **int** data type, which is an **integer**
        - **Is mandatory**
        - The value **should be in the range of 1 or more**
    - There is **newly** the rule that there cannot be two TV episodes that have **the same identifier**
    - There is **newly** the rule that there cannot be the TV episode whose **TV season identifier does not refer to any existing TV season**

#### File structure description

- The file should look something like this for **one input/output TV episode**:

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

- The description of the file structure is the same as in [structure description of input text file with TV episodes](#file-structure-description-12)
- Values data types and data/attributes names are specified in [file data structure description](#file-data-structure-description-14)
- But there are the following differences:
    - In the *\[Attributes\]* section, ***Order*** **changes** to ***Identificator***
        - This represents the identifier of the TV episode in the database
        - **Doesn't have to be in the file**, information purpose only
    - **Newly** **it is not possible** to use the control character ***\[End\]*** to **edit** the data of one existing TV episode
        - The input text file with TV episodes can be used for **editing**
    - **Newly** TV episodes in the file are **sorted in ascending order based on the TV episode identifier**

### Input/output binary file with TV episodes

- The file is created automatically by the application
- Used as a storage of already existing/stored TV episodes from different TV seasons to be loaded into the database the next time the application is run

#### Requirements

- The file name must be **inputOutput_tvEpisodes.bin**

#### File data structure description

- The structure is exactly the same as in [data structure description of input/output text file with TV episodes](#file-data-structure-description-14)
- To read text/string values of TV episode attributes from a binary file, all such values are set to a fixed length before writing to the file:
    - ***name*** –⁠ 60 characters
    - ***hyperlinkForContentWatch*** –⁠ 180 characters
    - ***shortContentSummary*** –⁠ 1000 characters

#### File structure description

- The individual data for each TV episode are written and read in the following order:

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

- The text/string values of the TV episode attributes are written on a character-by-character basis
- This order will be repeated for multiple TV episodes

## Class diagram

- This is the object oriented design for this application
- The object oriented design was created using the *Mermaid* tool, available at https://mermaid.js.org/

```mermaid

---
title: Class diagram of Star Wars Media Content Management application
---

classDiagram

direction RL

note for MovieInput "Included in the package app.models.input"
note for TVEpisodeInput "Included in the package app.models.input"
note for TVSeasonInput "Included in the package app.models.input"
note for TVShowInput "Included in the package app.models.input"

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

note for MovieInputOutput "Included in the package app.models.inputoutput"
note for TVEpisodeInputOutput "Included in the package app.models.inputoutput"
note for TVSeasonInputOutput "Included in the package app.models.inputoutput"
note for TVShowInputOutput "Included in the package app.models.inputoutput"

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

note for PrimaryKey "Included in the package app.models.data"
note for DatabaseRecord "Included in the package app.models.data"
note for MediaContent "Included in the package app.models.data"
note for Movie "Included in the package app.models.data"
note for TVEpisode "Included in the package app.models.data"
note for TVSeason "Included in the package app.models.data"
note for TVShow "Included in the package app.models.data"
note for Era "Included in the package app.models.data"

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

note for DataConversionException "Included in the package utils.exceptions (checked exceptions)"
note for DatabaseException "Included in the package utils.exceptions (checked exceptions)"
note for FileEmptyException "Included in the package utils.exceptions (checked exceptions)"
note for FileParsingException "Included in the package utils.exceptions (checked exceptions)"

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

note for MovieDataConverter "Included in the package utils.helpers"
note for TVEpisodeDataConverter "Included in the package utils.helpers"
note for TVSeasonDataConverter "Included in the package utils.helpers"
note for TVShowDataConverter "Included in the package utils.helpers"

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

note for DataStore "Included in the package app.logic.datastore (data layer)"

note for IDataTable "Included in the package utils.interfaces"
note for DataContextAccessor "Included in the package app.logic.datacontext (data layer)"
note for MoviesTable "Included in the package app.logic.datacontext (data layer)"
note for TVEpisodesTable "Included in the package app.logic.datacontext (data layer)"
note for TVSeasonsTable "Included in the package app.logic.datacontext (data layer)"
note for TVShowsTable "Included in the package app.logic.datacontext (data layer)"

MoviesTable --|> IDataTable : Implements
TVEpisodesTable --|> IDataTable : Implements
TVSeasonsTable --|> IDataTable : Implements
TVShowsTable --|> IDataTable : Implements

DataContextAccessor "Is part of" *--* "Contains" MoviesTable
DataContextAccessor "Is part of" *--* "Contains" TVEpisodesTable
DataContextAccessor "Is part of" *--* "Contains" TVSeasonsTable
DataContextAccessor "Is part of" *--* "Contains" TVShowsTable

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

note for EmailSender "Included in the package utils.emailsender (external library)"

note for IDataFileManager "Included in the package utils.interfaces"
note for FileManagerAccessor "Included in the package app.logic.filemanager"
note for MoviesFileManager "Included in the package app.logic.filemanager"
note for TVEpisodesFileManager "Included in the package app.logic.filemanager"
note for TVSeasonsFileManager "Included in the package app.logic.filemanager"
note for TVShowsFileManager "Included in the package app.logic.filemanager"

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

note for DataSorting "Included in the package app.logic.controllers"
note for DataType "Included in the package app.logic.controllers"
note for MoviesController "Included in the package app.logic.controllers (business logic)"
note for TVEpisodesController "Included in the package app.logic.controllers (business logic)"

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

note for ApplicationRunner "Included in the package ui"
note for ConsoleUI "Included in the package ui (presentation layer)"
note for MoviesSubUI "Included in the package ui (presentation layer)"
note for TVShowsSubUI "Included in the package ui (presentation layer)"
note for TVSeasonsSubUI "Included in the package ui (presentation layer)"
note for TVEpisodesSubUI "Included in the package ui (presentation layer)"

ConsoleUI ..> MoviesController : Depends on
ConsoleUI ..> TVEpisodesController : Depends on

ConsoleUI "Is part of" *--* "Contains" MoviesSubUI
ConsoleUI "Is part of" *--* "Contains" TVShowsSubUI
ConsoleUI "Is part of" *--* "Contains" TVSeasonsSubUI
ConsoleUI "Is part of" *--* "Contains" TVEpisodesSubUI

    class ApplicationRunner{
        +main(String[] args)$
    }
    class ConsoleUI{
        -boolean isDataDirectorySet$
        -boolean isDatabaseFromFilesLoaded$
        -Scanner scanner
        -List~String~ breadcrumbItems
        -TVEpisodesController tvEpisodesController
        -MoviesController moviesController
        -MoviesSubUI moviesSubUI
        -TVEpisodesSubUI tvEpisodesSubUI
        -TVSeasonsSubUI tvSeasonsSubUI
        -TVShowsSubUI tvShowsSubUI
        -ConsoleUI(MoviesController moviesController, TVEpisodesController tvEpisodesController)
        +getInstance(MoviesController moviesController, TVEpisodesController tvEpisodesController)$ ConsoleUI
        #getScanner() Scanner
        #getMoviesController() MoviesController
        #getTVEpisodesController() TVEpisodesController
        #getTVSeasonsSubUI() TVSeasonsSubUI
        #getTVEpisodesSubUI() TVEpisodesSubUI
        -initializeConsoleUI()
        +start()
        -displayDataDirectoryPathMenu()
        -displayLoadingInputOutputFilesMenu()
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
        -loadAllInputOutputDataFrom(boolean fromBinary)
        #displayChosenDataFileContent(String fileName, DataType dataType)
        -printInformationsAboutChronologicalEras()
    }
    class MoviesSubUI{
        -ConsoleUI consoleUI
        #MoviesSubUI(ConsoleUI consoleUI)
        #handleDisplayMoviesManagementSubmenu()
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
        -displayPrintMoviesInputOutputFilesContentsSubmenu()
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
        -handlePrintMoviesInputOutputFilesContentsSubmenu()
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
    class TVShowsSubUI{
        -ConsoleUI consoleUI
        #TVShowsSubUI(ConsoleUI consoleUI)
        #handleDisplayTVShowsManagementSubmenu()
        -displayTVShowsManagementSubmenu()
        -displayLoadTVShowsFromInputFileSubmenu()
        -displayPrintFoundTVShowsByNameSubmenu()
        -displayPrintErasWithAnnouncedTVShowsSubmenu()
        -displayPrintAnnouncedTVShowsInAlphabeticalOrderByEraSubmenu(Era chosenEra)
        -displayPrintErasWithReleasedTVShowsSubmenu()
        -displayPrintReleasedTVShowsByEraSubmenu(Era chosenEra)
        -displayDetailAboutTVShowSubmenu(TVShow chosenTVShow)
        -displayEditChosenTVShowSubmenu(TVShow chosenTVShow)
        -displayPrintReleasedNewestTVShowsSubmenu()
        -displayPrintDataInputOutputFilesContentsSubmenu(DataType dataType)
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
        -handleDisplayPrintReleasedNewestTVShowsSubmenu()
        -printReleasedNewestTVShows(List~TVShow~ releasedNewestTVShows)
        -handlePrintDataInputOutputFilesContentsSubmenu(DataType dataType)
        -deleteChosenTVShows(List~TVShow~ chosenTVShows)
    }
    class TVSeasonsSubUI{
        -ConsoleUI consoleUI
        #TVSeasonsSubUI(ConsoleUI consoleUI)
        #handleDisplayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow)
        -displayPrintChosenTVShowSeasonsSubmenu(TVShow chosenTVShow)
        -displayLoadTVSeasonsFromInputFileSubmenu()
        -displaySendTVEpisodesByEmailSubmenu()
        -displayPrintTVShowSortedTVEpisodesSubmenu(TVShow chosenTVShow, DataSorting dataSorting)
        -displayDetailAboutTVSeasonSubmenu(TVSeason chosenTVSeason)
        -displayEditChosenTVSeasonSubmenu(TVSeason chosenTVSeason)
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
    }
    class TVEpisodesSubUI{
        -ConsoleUI consoleUI
        #TVEpisodesSubUI(ConsoleUI consoleUI)
        #handleDisplayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason)
        -displayPrintChosenTVSeasonEpisodesSubmenu(TVSeason chosenTVSeason)
        -displayLoadTVEpisodesFromInputFileSubmenu()
        -displayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting)
        -displayDetailAboutTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason)
        -displayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason)
        -printChosenTVSeasonEpisodes(List~TVEpisode~ chosenTVSeasonEpisodes, TVSeason chosenTVSeason)
        -handleLoadTVEpisodesFromInputFileSubmenu(TVSeason chosenTVSeason)
        -loadTVEpisodesFromInputFile(PrimaryKey tvSeasonPrimaryKey, boolean fromBinary)
        #deleteChosenTVEpisodes(List~TVEpisode~ chosenTVEpisodes)
        -handleDisplayPrintTVSeasonSortedTVEpisodesSubmenu(TVSeason chosenTVSeason, DataSorting dataSorting)
        -printTVSeasonSortedTVEpisodes(List~TVEpisode~ tvSeasonSortedTVEpisodes, TVSeason chosenTVSeason, DataSorting dataSorting)
        #handleDisplayDetailAboutTVEpisodeSubmenu(List~TVEpisode~ chosenTVEpisodes)
        -loadChosenTVEpisodeFromUser() int
        -printTVEpisodeDetail(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason, boolean isInEditMode)
        -deleteChosenTVEpisode(PrimaryKey tvEpisodePrimaryKey) boolean
        -handleDisplayEditChosenTVEpisodeSubmenu(TVEpisode chosenTVEpisode, TVSeason chosenTVEpisodeParentSeason) boolean
        -editTVEpisodeFromInputFile(PrimaryKey existingTVEpisodePrimaryKey, PrimaryKey existingTVSeasonPrimaryKey, boolean fromBinary) boolean
        -rateTVEpisode(TVEpisode chosenTVEpisode) boolean
        -loadTVEpisodePercentageRatingFromUser() int
    }

```

# Testing

- Testing is divided into two parts, **unit tests** and **acceptance tests**

## Unit tests

- During the development of the application, tests of individual parts/modules of the application were continuously carried out
- The tests themselves can be found in the following classes of the application:
    - [ConsoleUITest](/star-wars-media-content-management/src/main/java/tests/mainmethods/ConsoleUITest.java)
    - [DataContextAccessorTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataContextAccessorTest.java)
    - [DataConvertersTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataConvertersTest.java)
    - [DataModelsTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/DataModelsTest.java)
    - [EmailSenderTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/EmailSenderTest.java)
    - [FileManagerAccessorTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/FileManagerAccessorTest.java)
    - [MoviesControllerTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/MoviesControllerTest.java)
    - [InputOutputModelsTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/InputOutputModelsTest.java)
    - [TVEpisodesControllerTest](/star-wars-media-content-management/src/main/java/tests/mainmethods/TVEpisodesControllerTest.java)

## Acceptance tests

- In this repository there are predefined input files with test data in [data directory](/star-wars-media-content-management/data/)
- Acceptance tests results are available in the form of **tables** and in the form of **console outputs of application user functions for adding new data into the application**

### Input file with movies

- Represents the text file [input_movies.txt](/star-wars-media-content-management/data/input_movies.txt)
- The binary file [input_movies.bin](/star-wars-media-content-management/data/input_movies.bin) has exactly the same content as the text file
- Here is the table of movies:

| **Order of the movie in the file** | **Test type** | **Expected result** | **Real result** | **Passed (yes/no)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 2 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 3 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 4 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 5 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 6 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 7 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 8 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 9 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 10 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 11 | Limit state | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 12 | Limit state | Successful upload from file but failed to add to database due to missing movie name | Successful upload from file but failed to add to database due to missing movie name | yes |
| 13 | Limit state | Successful upload from file but failed add to database due to too many epoch seconds for movie release date | Successful upload from file but failed add to database due to too many epoch seconds for movie release date | yes |
| 14 | Invalid input | Successful upload from file but failed add to database due to unselected/invalid chronological era | Successful upload from file but failed add to database due to unselected/invalid chronological era | yes |
| 15 | Invalid input | Failed to upload from file due to movie length in seconds not specified as an integer | Failed to upload from file due to movie length in seconds not specified as an integer | yes |
| 16 | Invalid input | Failure to upload from file due to a percentage rating that is not specified as an integer | Failure to upload from file due to a percentage rating that is not specified as an integer | yes |
| 17 | Limit state | Successful upload from file but unsuccessful addition to database due to exceeding character limit for URL link to watch movie | Successful upload from file but unsuccessful addition to database due to exceeding character limit for URL link to watch movie | yes |
| 18 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 19 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 20 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |

- Here is the console output of the user function for adding new movies:

```
Informační zpráva: Celkově se podařilo nahrát 14 filmů do databáze a naopak se nepodařilo nahrát 4 filmů
Chybový stav filmu s pořadím 12 v souboru input_movies.txt: Přidaný film musí mít název
Chybový stav filmu s pořadím 13 v souboru input_movies.txt: Příliš velký počet epoch sekund jako datum uvedení konvertovaného filmu
Chybový stav filmu s pořadím 14 v souboru input_movies.txt: Chronologické období přidaného filmu musí být vybráno
Chybový stav filmu s pořadím 17 v souboru input_movies.txt: Odkaz ke zhlédnutí přidaného filmu nesmí mít délku větší než 180 znaků
```

### Input file with TV shows

- Represents the text file [input_tvShows.txt](/star-wars-media-content-management/data/input_tvShows.txt)
- The binary file [input_tvShows.bin](/star-wars-media-content-management/data/input_tvShows.bin) has exactly the same content as the text file
- Here is the table of TV shows:

| **Order of the TV show in the file** | **Test type** | **Expected result** | **Real result** | **Passed (yes/no)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 2 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 3 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 4 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 5 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 6 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 7 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 8 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 9 | Limit state | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 10 | Limit state | Successful upload from file but failed to add to database due to data duplication (same title and same release date as TV show with order 8) | Successful upload from file but failed to add to database due to data duplication (same title and same release date as TV show with order 8) | yes |
| 11 | Limit state | Successful upload from file but failed to add to database due to missing TV show name | Successful upload from file but failed to add to database due to missing TV show name | yes |
| 12 | Limit state | Successful upload from file but unsuccessful addition to database due to too many epoch seconds for TV show release date | Successful upload from file but unsuccessful addition to database due to too many epoch seconds for TV show release date | yes |
| 13 | Invalid input | Successful upload from file but failed to add to database due to unselected/invalid chronological era | Successful upload from file but failed to add to database due to unselected/invalid chronological era | yes |
| 14 | Invalid input | Failure to upload from file due to a release date in epoch seconds that is not specified as an integer | Failure to upload from file due to a release date in epoch seconds that is not specified as an integer | yes |
| 15 | Invalid input | Failed to upload from file due to the release date in epoch seconds not being specified as an integer because it is missing | Failed to upload from file due to the release date in epoch seconds not being specified as an integer because it is missing | yes |
| 16 | Invalid input | Successful upload from file but failed to add to database due to missing TV show name | Successful upload from file but failed to add to database due to missing TV show name | yes |
| 17 | Invalid input | Successful upload from file but failed add to database due to unselected/invalid chronological era | Successful upload from file but failed add to database due to unselected/invalid chronological era | yes |
| 18 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 19 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 20 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |

- Here is the console output of the user function for adding new TV shows:

```
Informační zpráva: Celkově se podařilo nahrát 12 seriálů do databáze a naopak se nepodařilo nahrát 6 seriálů
Chybový stav seriálu s pořadím 10 v souboru input_tvShows.txt: Data přidaného seriálu jsou duplicitní
Chybový stav seriálu s pořadím 11 v souboru input_tvShows.txt: Přidaný seriál musí mít název
Chybový stav seriálu s pořadím 12 v souboru input_tvShows.txt: Příliš velký počet epoch sekund jako datum uvedení konvertovaného seriálu
Chybový stav seriálu s pořadím 13 v souboru input_tvShows.txt: Chronologické období přidaného seriálu musí být vybráno
Chybový stav seriálu s pořadím 16 v souboru input_tvShows.txt: Přidaný seriál musí mít název
Chybový stav seriálu s pořadím 17 v souboru input_tvShows.txt: Chronologické období přidaného seriálu musí být vybráno
```

### Input file with TV seasons

- Represents the text file [input_tvSeasons.txt](/star-wars-media-content-management/data/input_tvSeasons.txt)
- The binary file [input_tvSeasons.bin](/star-wars-media-content-management/data/input_tvSeasons.bin) has exactly the same content as the text file
- Here is the table of TV seasons:

| **Order of the TV season in the file** | **Test type** | **Expected result** | **Real result** | **Passed (yes/no)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 2 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 3 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 4 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 5 | Limit state | Successful upload from file but failed to add to database due to data duplication (same order of this TV season within TV show as TV season with order 4 in file) | Successful upload from file but failed to add to database due to data duplication (same order of this TV season within TV show as TV season with order 4 in file) | yes |
| 6 | Invalid input | Successful upload from file but failed to add to database due to invalid negative or zero order of TV season | Successful upload from file but failed to add to database due to invalid negative or zero order of TV season | yes |
| 7 | Invalid input | Failure to upload from file due to TV season order within TV show not being specified as an integer | Failure to upload from file due to TV season order within TV show not being specified as an integer | yes |

- Here is the console output of the user function for adding new TV seasons for the selected TV show:

```
Informační zpráva: Celkově se podařilo nahrát 4 sezón vybraného seriálu do databáze a naopak se nepodařilo nahrát 2 sezón vybraného seriálu
Chybový stav sezóny vybraného seriálu s pořadím 5 v souboru input_tvSeasons.txt: Data přidané sezóny seriálu jsou duplicitní
Chybový stav sezóny vybraného seriálu s pořadím 6 v souboru input_tvSeasons.txt: Pořadí přidané sezóny seriálu musí být větší než nula
```

### Input file with TV episodes

- Represents the text file [input_tvEpisodes.txt](/star-wars-media-content-management/data/input_tvEpisodes.txt)
- The binary file [input_tvEpisodes.bin](/star-wars-media-content-management/data/input_tvEpisodes.bin) has exactly the same content as the text file
- Here is the table of TV episodes:

| **Order of the TV episode in the file** | **Test type** | **Expected result** | **Real result** | **Passed (yes/no)** |
|:---:|:---:|:---:|:---:|:---:|
| 1 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 2 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 3 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 4 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 5 | Invalid input | Successful upload from file but unsuccessful addition to database due to TV episode percentage rating exceeding 100 | Successful upload from file but unsuccessful addition to database due to TV episode percentage rating exceeding 100 | yes |
| 6 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 7 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 8 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 9 | Limit state | Successful upload from file but failed to add to database due to data duplication (same order of this TV episode within TV season as TV episode with order 8 in file) | Successful upload from file but failed to add to database due to data duplication (same order of this TV episode within TV season as TV episode with order 8 in file) | yes |
| 10 | Limit state | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 11 | Limit state | Successful upload from file but failed to add to database due to TV episode length in seconds being negative, while specifying the length is mandatory | Successful upload from file but failed to add to database due to TV episode length in seconds being negative, while specifying the length is mandatory | yes |
| 12 | Limit state | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 13 | Limit state | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 14 | Invalid input | Failed to upload from file due to TV episode length in seconds not specified as an integer | Failed to upload from file due to TV episode length in seconds not specified as an integer | yes |
| 15 | Invalid input | Failed to upload from file due to TV episode percentage rating not being specified as an integer | Failed to upload from file due to TV episode percentage rating not being specified as an integer | yes |
| 16 | Invalid input | Failed upload from file due to TV episode order within a TV season not being specified as an integer | Failed upload from file due to TV episode order within a TV season not being specified as an integer | yes |
| 17 | Invalid input | Failure to upload from file due to TV episode order within TV season not specified | Failure to upload from file due to TV episode order within TV season not specified | yes |
| 18 | Ordinary value | Successful upload from file and then successful addition to database | Successful upload from file and then successful addition to database | yes |
| 19 | Limit state | Successful upload from file but failed to add to database due to duplicate data (this TV episode has a duplicate URL link to watching as TV episode with order 18 in the file) | Successful upload from file but failed to add to database due to duplicate data (this TV episode has a duplicate URL link to watching as TV episode with order 18 in the file) | yes |
| 20 | Limit state | Successful upload from file but failed to add to database due to data duplication (this TV episode has duplicate short content summary as TV episode with order 18 in file) | Successful upload from file but failed to add to database due to data duplication (this TV episode has duplicate short content summary as TV episode with order 18 in file) | yes |

- Here is the console output of the user function for adding new TV episodes for the selected TV season:

```
Informační zpráva: Celkově se podařilo nahrát 11 epizod vybrané sezóny do databáze a naopak se nepodařilo nahrát 5 epizod vybrané sezóny
Chybový stav epizody vybrané sezóny s pořadím 5 v souboru input_tvEpisodes.txt: Procentuální hodnocení zhlédnuté přidané epizody sezóny musí být v rozsahu 0 - 100
Chybový stav epizody vybrané sezóny s pořadím 9 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
Chybový stav epizody vybrané sezóny s pořadím 11 v souboru input_tvEpisodes.txt: Přidaná epizoda sezóny musí mít délku trvání
Chybový stav epizody vybrané sezóny s pořadím 19 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
Chybový stav epizody vybrané sezóny s pořadím 20 v souboru input_tvEpisodes.txt: Data přidané epizody sezóny jsou duplicitní
```

# Description of the external library functioning

- This is the *Apache Commons Email* library
- It allows the following things:
    - Simplifies the existing *Java Mail API*
    - Authenticate to the *SMTP* server if needed
    - For communication over the *SMTP* protocol, allows to choose between *SSL* and *STARTTLS* encryption protocols based on port number
    - Setting the encoding of email content
    - Setting the recipient, subject and content of the email
    - Setting the email to which undeliverable emails will be sent
    - Sending simple text emails
    - Sending emails formatted in *HTML*
    - Sending emails with attachments
    - Sending *HTML* emails with inline images (image data from an external *URL* link is downloaded and inserted directly into the email)
- In the application, the library is used to send data from the database using *HTML* email for three reasons:
    - Better formatted output
    - Ability to filter and sort incoming emails differently in the email client because they have a standardized email subject
    - Allow data with a URL link to be stored directly in an email, e.g. as a list of unwatched movies
- In the application, the library is implemented in the class [EmailSender](/star-wars-media-content-management/src/main/java/utils/emailsender/EmailSender.java)

## Library implementation details in EmailSender class

1. First it is needed to set the configuration data:

```java
public class EmailSender 
{   
    private final int smtpPort = 465;
    
    private final String hostName = "smtp.googlemail.com";
    
    private final String randomGeneratedAppToken = "qnaadtxcznjyvzln";
    
    private final String appId = "honzaswtor";
}
```

- ***smtpPort*** –⁠ Expresses the SMTP protocol port number within the transport layer
    - The value was set to ***465*** to allow the ***SSL*** encryption protocol to be used later in the implementation
- ***hostName*** –⁠ Expresses the *SMTP* server that will be responsible for sending emails
    - Depending on the *SMTP* server that is chosen, it may be needed to authenticate
    - The specific authentication method is specified by each *SMTP* server individually
    - In the case of the selected **Google SMTP server**, the authentication data written in the *appId* and *randomGeneratedAppToken* attributes were used
        - **G-mail account** is used for authentication
        - For security reasons, an explanation of how to log in to the Google SMTP server and a description of the authentication data is not provided

2. Then it is needed to compose an email:

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

- ***HtmlEmail*** –⁠ Expresses an instance that supports formatting email as **HTML**
- ***setHostName(hostName)*** –⁠ Sets the outgoing SMTP server specified in **Step 1 of the implementation with configuration data**
- ***setCharset(org.apache.commons.mail.EmailConstants.UTF_8)*** –⁠ Sets the HTML encoding of the email content as UTF-8 so that **data with diacritics is correctly displayed**
- ***setSmtpPort(smtpPort)*** –⁠ Sets the port number specified in **Step 1 of the implementation with configuration data**
    - The number will also determine the supported encryption protocol
- ***setAuthenticator(new DefaultAuthenticator(appId, randomGeneratedAppToken))*** –⁠ Sets the authentication data specified in **Step 1 of the implementation with configuration data** for the  chosen SMTP server
- ***setSSLOnConnect(true)*** –⁠ Sets the encryption protocol **SSL**
    - SSL is set because the port number was specified as **465**, so without encryption sending emails would be unsafe
- ***setFrom(DataStore.getAppCreator())*** –⁠ Sets the sender of the email as the creator of the application, which is **honzaswtor@gmail.com**
    - If the sender is specified as **non-valid email**, the **email of the authenticated account to the SMTP server** is used
- ***setSubject(subject)*** –⁠ Sets the subject of the email
- ***addTo(recipientEmailAddress)*** –⁠ Sets the recipient of the email
    - If a network error occurs when sending an email or the recipient's email is invalid, an **EmailException** is thrown
    - Exception originates from **external library**
- ***setHtmlMsg(message.toString())*** –⁠ Sets the content of the email and interprets it as **HTML**
- ***send()*** –⁠ Sends a built email
