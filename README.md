# HibernateProjectCRMsystem
A training project for small CRM application, developed using Java and Hibernate


This program allows to load the data into tables from files and allows to perform basic CRUD operations on the repository as well as perform complex operations (placing an order), pulling reports. The project consists of 3 layers (repository, data-to-object mapper, service layer). The control flow is based on the java console menu (no front-end layer). 
For the purpose of the validation of the data input, a BUILDER design pattern has been used. In order to create always only one DataBase connection pool, a SINGLETON design pattern has been implemented. This project relies heavily on the use of Java interfaces, generics, streams, collections, Optional class and Hibernate of course.

## How to start using the project

 1. Clone the repository to your desktop using `git clone` command with the link to this repo from your git CLI. Alternatively you may want to copy the files directly to your PC in a zip file.
 2. Open the project in the IDE of your choice (I recommend IntelliJ IDEA);
 3. Make sure that Maven has loaded all the dependencies.
 4. The project has been built with the use of MySQL database. In order to start Hibernate persistance API, you have to run MySQL server and instantiate a new DB schema (you can name it how you want to).
 5. Mark the resources folder (located in the main package) as a resources folder.
 6. Amend the persistence.xml file properly, so that Hibernate can connect to your database (prodive url to your db, db username and db password)
 7. Run the application;
 8. With the first start, choose the option to log in as an admin and subsequently to load the data from files. This will create respective tables in the DB necessary to run the application and load some sample data.
 9. With the second use of the application you might want to choose the customer path and process through all the steps and explore all the features, such as:
 
     * setting up a new account
     * logging in as a customer
     * placing an order
 
 The app will validate is the amount requested by you is in stock in the shop of your choice (you can choose a shop only if the product of your choice is exists in more than one shop);
 
 10. When again logged in as an admin you can:
 
     * again load the data from JSON files into the Database;
     * create a different kind of console reports (the options will be provided to you in the console)
     
 ###Enjoy! 
