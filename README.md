# store-management-tool

The application is a web application developed in Java. 
It uses SpringBoot v3+, JDK 17, Maven v3.6+.

The application contains two modules: database and startup.
In order to run the application from the command line the following comand can be executed from the directory of the startup module:
    mvn spring-boot:run

A H2 databases is used in the memory to store the products.

The command to build the application: mvn clean install.

The available endpoints are:
1. GET: http://localhost:8080/store/v1/find-product?name=<PRODUCT-NAME>
2. GET: http://localhost:8080/store/v1/all-products
3. POST: http://localhost:8080/store/v1/save-product

The endpoints must have the header 'Authorization' with the value encoded with base64.

The following users can be used to authenticate and authorize the requests:
1. username=user, password=user
2. username=admin, password=admin
