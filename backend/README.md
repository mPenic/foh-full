HOW TO RUN PROJECT:
!!!
testni build se pokrece kroz: 

./mvnw clean install

aplikacija se pokrece kroz:

./mvnw -pl core-app spring-boot:run

!!!!
1. Imati instaliranu Javu (verzija 17+)
2. Provjeriti koja je verzija jave u pom.xml (mora biti ista kao instalirana)
3. U src/main/resources/application.properties konfigurirati sljedeće:
    spring.datasource.url
    spring.datasource.username
    spring.datasource.password
4. Pokrenuti projekt sa komandom ./mvnw spring-boot:run
5. Dodano u bazu kroz : INSERT INTO public.users (username, passwordhash, roleid)
VALUES ('admin', '$2a$12$0MkzIzpRpYsWv2krbwFeS.joHqWRH6AgM66gPTZhIpO10nc/jC2wC', 1);
user name admin lozinka ....>Koraci za unos podataka u users tablicu koristeći pgAdmin:
1. Pokreni pgAdmin i poveži se na svoju bazu podataka
Otvori pgAdmin.
Poveži se sa svojom PostgreSQL bazom podataka.
Odaberi svoju bazu podataka iz lijevog panela.
2. Generiraj šifriranu lozinku (bcrypt)
Budući da koristiš Spring Security, lozinke u tvojoj tablici users trebaju biti kriptirane pomoću BCrypt.
Ako nemaš šifriranu lozinku, možeš je generirati koristeći neki od ovih načina:
Online alat za bcrypt: Postoje besplatni online alati za generiranje bcrypt hashova, npr. bcrypt generator.
Spring-ov BCryptPasswordEncoder: Koristi kratki Java program kako bi generirao šifriranu lozinku pomoću BCryptPasswordEncoder, kao što sam ti već pokazao.


 Struktura projekta
Vaša trenutna struktura projekta prati standardnu organizaciju Spring Boot aplikacija. Evo kako su direktoriji organizirani i za što se koriste:
src/main/java/com/example/demo/config
Za konfiguracije aplikacije.
Ovdje možete postaviti sigurnosne konfiguracije (npr. Spring Security), bean konfiguracije i slične postavke.
src/main/java/com/example/demo/controller
Za kontrolere.
Ovdje se nalaze klase koje obrađuju HTTP zahtjeve i povezuju frontend (Thymeleaf) s backend logikom.
src/main/java/com/example/demo/entities
Za entitete.
Klase koje mapiraju tablice u bazi podataka (JPA entiteti).
src/main/java/com/example/demo/repository
Za pristup bazi podataka.
Interface-i koji omogućuju komunikaciju s bazom putem Spring Data JPA.
src/main/java/com/example/demo/services
Za poslovnu logiku.
Servisi koji implementiraju poslovne operacije i upravljaju podacima između kontrolera i repository sloja.
src/main/java/com/example/demo/vo
Za View Object (VO) klase.
Klase koje se koriste za prijenos podataka između backend-a i frontend-a (DTO - Data Transfer Objects).
src/main/resources/static
Za statičke resurse.
CSS, JavaScript i slike koje koristi frontend aplikacije.
src/main/resources/templates
Za HTML stranice.
Thymeleaf HTML predlošci koji se renderiraju na serveru i prikazuju korisnicima.



RezervacijeBackEnd (Spring Boot) - Pokretanje s SSL (HTTPS + WSS)
Ovaj projekt služi kao backend za rezervacije (i slične module) te pruža WebSocket STOMP endpoint. U nastavku su upute kako podesiti i pokrenuti aplikaciju s TLS/SSL konfiguracijom (tj. https i wss).

1) Generiranje self-signed certifikata (keystore.p12)
Otvori Terminal / PowerShell.
Pokreni keytool (dolazi s JDK) za izradu .p12 keystore datoteke:
keytool -genkeypair -alias tomcat -keyalg RSA -storetype PKCS12 \
    -keystore keystore.p12 -storepass lozinka -validity 365 -keysize 2048
Napomena za PowerShell: ili sve piši u jednoj liniji (bez \), ili koristi backtick (`) umjesto ^.
Odgovori na interaktivna pitanja (“first and last name”, “organizational unit”, itd.).
Nakon dovršetka, dobit ćeš datoteku keystore.p12.
2) Postavljanje keystore.p12 u projekt
Smjesti keystore.p12 u mapu core/src/main/resources/ (ili u re/src/main/resources/, ovisno kako ti je projekt strukturiran).
Provjeri da se datoteka zaista kopira u target/classes prilikom builda (zadano Spring Boot to radi automatski).
3) Konfiguracija application.properties
U core/src/main/resources/application.properties (ili application.yml), dodaj:
properties
Kopiraj kod
server.port=8443
server.ssl.key-store=classpath:keystore.p12
server.ssl.key-store-password=lozinka
server.ssl.keyStoreType=PKCS12
server.ssl.keyAlias=tomcat
server.port=8443 – sluša na 8443 umjesto na 8080.
server.ssl.key-store=classpath:keystore.p12 – putanja do .p12 u classpath-u.
server.ssl.key-store-password=lozinka – lozinka korištena prilikom generiranja.
server.ssl.keyAlias=tomcat – mora se poklapati s -alias iz keytool naredbe.
4) Pokretanje projekta
U korijenu projekta (gdje je pom.xml), npr. iz Command Prompt/PowerShell:
bash
.\mvnw.cmd spring-boot:run -pl core
ili u nekom IDE-u (IntelliJ/Eclipse) pokreni glavnu klasu CoreApplication.
U logu bi trebao vidjeti nešto poput:
Tomcat started on port 8443 (https) ...
Ako vidiš i poruku o SimpleBrokerMessageHandler, znači da je i WebSocket STOMP broker uspješno startao.
5) Spajanje na aplikaciju
5.1) HTTPS (REST ili Thymeleaf)
Odi u browser na:
https://localhost:8443/
Ako je self-signed cert, dobit ćeš upozorenje u browseru (“Your connection is not private”). Klikni “Proceed” ili “Advanced -> Accept” (ovisno o browseru).