Android application(API 21) for showing data from IoT reservoir backend system, which measures water level in a reserovir on a remote localtion.
Data is then send to RPi from where is uploaded to the database.
App requires login with username and password.
Main screen show today's measurements. In ApplicationBar are two buttons. First one is for refresing data and the second one opens screen with statistical data.
Screen with statistical data show graph for today's water level and min&max values for battery and water.
From this screen we have acces to all measurements from button in AppBar. All measures screen does exactly what you mean it does. Show all the measurements which are later stored in local SQlite db. All other data is live and downloaded from server.