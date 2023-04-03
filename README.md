# Food ordering app

This is a small university project which uses Java Servlets for web communication. It simulates an app which would be used by a organisation for delivering food to programmers. Programmers can choose their dishes of choice for every day by going to the http://localhost:8080/demo_war_exploded/ url and clicking on the button.
It will take them to a menu where they can choose from dishes for every day of the week. The dishes are recorded in the resources folder in the .txt files. Once one programmer chooses his meals for the week he cannot choose again until the supplier clears the orders for that week. The supplier can access a page where he can see the amount of each dish he needs to order for the week by going to the http://localhost:8080/demo_war_exploded/odabrana-jela?lozinka=albatroz link. The lozinka=albatroz is a parameter for the password which can be adjusted in the password.txt file in the resources folder.
Once the supplier clears the meals for the week, the sessions for all the programmers will be cleared and they can order again. 

The full homework specification can be found in the Web Domaći 4 file.
