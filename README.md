# Appathon-Project
Project for 8th semester course "Internet and Applications"

COVID-04: Εισαγωγή Άρθρων σε μία Σχεσιακή Βάση.
Η σχεσιακή Βάση θα είναι Mysql και η εφαρμογή θα γίνει με Java. Θα γινει και front-end σε JS για περαιτέρω οπτικοποίηση των περιεχομένων της βάσης. Συγκεκριμένα θα 
παρουσιάζονται charts ανάλογα με τα δεδομένα που υπάρχουν στη βάση, συμπεριλαμβανομένου των άρθρων ανα περιοδικό, άρθρα ανά χρονική περίοδο, κτλ. 


Οδηγίες εγκατάστασης εκτέλεσης του κώδικα:

Για την δημιουργία της βάσης δεδομένων κάνετε import το covid_articles.sql, έπειτα φορτώνετε στον πίνακα paper το csv με τα δεδομένα των άρθρων
το οποίο μπορείτε να προμηθευτείτε από το dataset https://ai2-semanticscholar-cord-19.s3-us-west-2.amazonaws.com/historical_releases.html

Για το σήκωμα του back-end στο Covid folder: gradlew bootRun
Για το frontend: npm start

Κατ' ελάχιστο δημιουργείται τον πίνακα Paper(sha(PK), title, journal, publish_time) στον οποίο και θα φορτωθεί το csv με τα δεδομένα.

Περιγραφή:

Η λειτουργικότητα της εφαρμογής όσον αφορά το upload των άρθεων βασίστηκε στο upload των json files, το csv θεωρώ οτι ειναι ήδη φορτωμένο στη 
βάση επειδή είναι πολύ πιο εύκολο να φορτωθεί εκτός εφαρμογής και ο σκοπός της εφαρμογής μου επιτυγχάνεται με το ανέβασμα των json.

Το Data-model της εφαρμογής μου αποτελείται από τις κλάσεις Paper, PublishYearCategory, Journal, Institution, Author οι οποίες έχουν αντιστοίχιση 
1 προς 1 με τους πίνακες της βάσης mysql. Οι κλάσεις DataSet, ChartData χρησιμοποιούνται μόνο για την απεικόνιση στο frontend διότι η η βιβλιοθήκη chart.js
θέλει σε συγκεκριμένη μορφή τα data και τα labels που απεικονίζει. Τέλος η κλάση AuthorPK προσδιορίζει ότι η κλάση Author έχει Composite key το 
(sha, author_id) και αυτό για να μπορούμε να αποθηκεύουμε και την πληροφορία για τους authors που έχουν γράψει παραπάνω από ένα άρθρα.

Τα endpoints του back-end φαίνονται στο MainController.java:
- findArticleFromTitle: δέχεται όρισμα String word, επιστρέφει όλες τις tuples του πίνακα paper που έχουν το word στον title τους ως Collection<Paper>.
- visualizeData: δεν δέχεται όρισμα,επιστρέφει LinkedList<ChartData> όπου κάθε ChartData δημιουργείται από έναν Iterator<Object> όπου Object ένα εκ των 
  Journal, Institution, PublishYearCategory, τους οποίους iterators παίρνουμε από τους αντίστοιχους πίνακες της βάσης δεδομένων.
- updatePaper: δέχεται όρισμα ένα String sha, επιστρέφει String τύπου "updated" ή "already exists" και ανανεώνει όλους τους πίνακες της βάσης μας
  παίρνοντας πληροφορία από το sha.json file.
- jsonToList: δέχεται όρισμα String sha, επιστρέφει LinkedList<String> με περιεχόμενα που διαβάζουμε από το json και είναι το bodytext, και πληροφορίες
  για τους authors.
