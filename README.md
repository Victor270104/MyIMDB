Am creeat clasele, interfetele, enumerarile si exceptiile asa cum au fost descrise in instructiuni. Am facut din bonus-uri: salvarea modificarilor, posibilitatea de a sorta actorii/productiile de mai mult de 5 ori, posibilitatea ca actorii sa primeasca review-uri si posibilitatea de a viziona un trailer.
Dupa ce se da run la cod prin clasa „xx”, se incarca datele si suntem rugati sa selectam cum vrem sa folosim aplicatia. Ambele cazuri sunt similare, asa ca o sa prezint in mod general.
„View productions details”: programul ne intreba daca dorim sa sortam filmele si serialele intr-un mod anume (dupa titlu, dupa data de lansare, dupa numarul de actori/regizori, dupa review-uri sau numarul acestora), apoi afiseaza detalii despre acestea, ordinea lor poate sa fie selectata (ascendent sau descendent). La interfata grafica se afiseaza si poza, iar daca apasam pe poza se vor arata toate detaliile despre productie, cat si trailer-ul.
„View actors details”: programul ne intreaba daca dorim sa sortam actorii intr-un mod anume (dupa nume, numarul de filme jucate, numar de ratinguri sau dupa cel mai mare rating), apoi afiseaza detalii despre acestia, ordinea lor poate sa fie selectata (ascendent sau descendent).
„View notifications”: programul afiseaza toate notificarile ramase/primate si ofera posibilitatea de a sterge notificarile actuale.
“Search for actor/production”: se introduce numele actrorului sau numele productiei, si se cauta rezultatele, urmand sa fie primit un mesaj corespunzator. Daca se gaseste se afiseaza detaliile, daca nu se afiseaza un mesaj de tip eroare.
“Add/Delete actor/production to/from favorites”: se selecteaza daca se doreste stergerea, daca se doreste modificarea actorilor sau productiilor. Daca se selecteaza delete, se afiseaza toate posibilitatile, apoi se alege, urmand sa se stearga automat obiectul ales. Daca se selecteaza adaugarea, se cer detalii rand pe rand,particularizate  pentru fiecare tip de obiect, verificate pentru a nu avea erori de introducre de date, in cazul in care se introduce ceva gresit, se arunca exceptia potrivita.
“Search for a user”: se introduce numele sau email-ul, se cauta utilizatorul si se afiseaza detaliile sale, in caz contrar se afiseaza eroare.
“Add/Delete a rating”: se selecteaza ce se doreste. Daca se doreste delete, programul afiseaza ce ratinguri poti sa stergi(ale tale), dupa ce selectezi ce doresti, se sterge ratingul. Daca se doreste add, se selecteaza obiectul la care vrei sa dai un review, apoi introduci nota, cat si un comentariu.
“Add/Delete a request”: asemenea cu rating, numai ca request-urile de tip DELETE_ACCOUNT se pot face o singura data, dar pot sa fie sterse si readaugate.
“Add/Delete actor/production to/from IMDB”: se selecteaza ce se doreste. Daca se doreste delete, programul afiseaza ce obiecte se pot sterge (adaugate de tine), dupa ce selectezi, se sterege automat. Daca se doreste add, se introduce numele si se selecteaza tipul de obiect pe care doresti sa-l adaugi, urmand sa fie urmati pasii.
“View and solve requests”: se afiseaza requesturile pe care toata echipa trebuie sa le resolve, cat si cele personale, selectezi unul din ele, apare optiunea de accept/respingere, apoi daca accepti iti apar mai multe optiuni, depinde de ce se alege.
“Update actor/production”: asemanator cu delete/add, se selecteaza din lista de obiecte adaugate, apoi se urmeaza pasii.
“Add/Delete a user to/from IMDB”: daca se allege delete se afiseaza lista cu userii care pot sa fie stersi, se selecteaza iar apoi se primeste un mesaj de confirmare. Daca se doreste adaugarea, se selecteaza tipul de user dorit, apoi se urmeaza pasii. Parola este generata automat si este afisata dupa crearea userului.
“Logout”: se delogeaza userul, si se ajunge la meniul de log in.
“Exit”: se salveaza fisierele in locul dorit, apoi se inchide programul.

S-a lucrat la design ul interfetei grafice dar si la design ul terminalui, atat cu tipuri de font si marimi ale textului cat si cu diferite culori sugestive.
