TodoApp implemented with usage of Clean Architecture guidelines, Jetpack Compose, Room database, Retrofit and Firebase Realtime database, Dagger-Hilt.

If you want to use this project, create new Firebase project -> Realtime database, copy THIS reference url from it (https://todoapp-...firebaseio.com/ link like this in menu), add one element named "todo" in it (i did it just for testing purpose, coz its easier to
delete whole table than each element) and add link that you copied from Realtime database in TodoModule.kt retrofit provider .baseUrl().
