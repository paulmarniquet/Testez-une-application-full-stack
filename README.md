# Testez une application fullstack

Ce projet consistait à mettre en place des tests pour une application fullstack.
Le but était ainsi de tester les différentes couches de l'application, à savoir le frontend et le backend et cela, sur des tests d'intégration, unitaires et end-to-end.

## Installation

### Frontend
1. Récupérez le code source du frontend depuis [le dépôt GitHub du projet parent](https://github.com/OpenClassrooms-Student-Center/Testez-une-application-full-stack).
2. Lancez un terminal et rendez-vous dans le répertoire du frontend.
3. Exécutez la commande suivante pour installer les dépendances :
   ```
   npm install
   ```
4. Démarrez le serveur de développement en exécutant :
   ```
   ng serve
   ```
   Le frontend sera accessible à l'adresse [localhost:4200](http://localhost:4200/).

### Backend
1. Lancez un terminal et rendez-vous dans le répertoire du backend.
2. Exécutez la commande suivante pour installer les dépendances :
   ```
   mvn install
   ```
3. Démarrez le serveur backend avec la commande :
   ```
   mvn spring-boot:run
   ```

### Base de données

1. Créez une nouvelle base de données MySQL.
2. Exécutez le script SQL qui se trouve dans le répertoire GitHub du projet parent : ressources/sql/script.sql.


## Tests

### Frontend

1. Lancez un terminal et rendez-vous dans le répertoire du frontend.
2. Exécutez la commande suivante pour lancer les tests unitaires :
   ```
   ng test
   ```
3. Exécutez la commande suivante pour lancer les tests end-to-end :
   ```
    npm run cypress:run
    ```

Pour générer le rapport de couverture de code, exécutez la commande suivante :
```
ng test --coverage
```

Pour ce qui est du coverage des tests end-to-end, il est possible de le générer en exécutant dans un premier temps la commande suivante :
```
npm run cypress:run
```
Puis, en exécutant la commande suivante :
```
npm run e2e:coverage
```

### Backend

1. Lancez un terminal et rendez-vous dans le répertoire du backend.
2. Exécutez la commande suivante pour lancer les tests unitaires :
   ```
   mvn test
   ```
   
Pour générer le rapport de couverture de code, exécutez la commande suivante :
```
mvn test jacoco:report
```

Pour regarder le rapport de couverture de code, ouvrez le fichier index.html qui se trouve dans le répertoire target/site/jacoco du backend.


## Technologies utilisées

* Java
* Spring Boot
* jUnit
* Mockito
---
* Angular
* Jest
* Cypress

---

### Contact

Pour toute question ou commentaire, n'hésitez pas à me contacter sur mon mail : [paul.marniquet@gmail.com](mailto:paul.marniquet@email.com).

---
