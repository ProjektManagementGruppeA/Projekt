# Datenbank verbindung
MongoDB Driver JAR Version 4.11.0 von hier herunterladen:  
https://jar-download.com/artifacts/org.mongodb/mongodb-driver-sync

oder Falls ihr mit Maven arbeitet die nächsten Zeilen in pom.xml unter "dependencies" hinzufügen:
```
<groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-sync</artifactId>
    <version>4.11.0</version>
</dependency>
```

falls ihr die Daten manuell bearbeiten/anschauen wollt, könnt ihr auch eine Datenbankverbindung mit MongoDBCompass aufbauen, indem ihr unter New Connection -> URI folgendes eingibt:

`mongodb+srv://project:ycJ3EVslOFfiCoEr@projektmanagement.wgxeir5.mongodb.net/?retryWrites=true&w=majority`
