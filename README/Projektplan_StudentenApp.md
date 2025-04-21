
# 📘 Projektplan: Studentenregistrierungs- und Verwaltungssystem

## 🗂️ Aufgabenliste – Schritt-für-Schritt-Plan

### ✅ A. Grundlegende Einrichtung und Konfiguration

- [x] Überprüfen, ob Java und Maven installiert sind
- [x] Spring Boot Projekt über [https://start.spring.io](https://start.spring.io) generieren
- [x] Projekt mit IntelliJ IDEA öffnen
- [x] `application.properties` konfigurieren
- [x] Abhängigkeiten in `pom.xml` einfügen (Spring Web, Thymeleaf, Spring Data JPA, H2)

---

### ✅ B. Startseite und Grundstruktur

- [x] `index.html` Datei im Ordner `templates/` anlegen
- [x] `MainController.java` erstellen
- [x] `/`-Route definieren und `index.html` anzeigen
- [x] Einfaches Navigationsmenü hinzufügen (Start, Registrierung, Liste)

---

### 🧾 C. Studentenregistrierung

- [x] `Student` Modellklasse erstellen (id, vorname, nachname, email, geburtsdatum usw.)
- [ ] `StudentRepository` (JPA Interface) erstellen
- [ ] `StudentService`-Schicht erstellen
- [ ] `StudentController` erstellen
- [ ] `register.html` mit Formular gestalten
- [ ] Formulardaten speichern und in Datenbank einfügen

---

### 📄 D. Studentenanzeige und Details

- [ ] `list.html` erstellen
- [ ] Alle gespeicherten Studenten in einer Tabelle anzeigen
- [ ] „Details“ oder „Löschen“-Buttons einfügen
- [ ] Löschfunktion implementieren
- [ ] Detailseite zur Anzeige von Studentendaten erstellen

---

### ⚙️ E. Erweiterte Funktionen (optional)

- [ ] Suchfunktion nach Namen
- [ ] Pagination einbauen
- [ ] Anmeldung mit Spring Security
- [ ] Rollenbasierte Autorisierung (Admin, Benutzer)
- [ ] Migration auf MySQL oder PostgreSQL

---

📌 *Dieses Dokument dient als technische Übersicht und Fahrplan für das Projekt.*
