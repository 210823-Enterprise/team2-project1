# diyORM

## Project Description
A java based ORM for simplifying connecting to and from a postgres database without the need for SQL or connection management. Servlet demo - https://github.com/210823-Enterprise/team2-project1/demo

## Technologies Used

* PostgreSQL - version 42.2.18  
* Java - version 8.0  
* Apache commons - version 2.1.1  
* JUnit - version 4.12
* log4j - version 1.2.17

## Technologies Used In Demo

* javax.servlet - version 3.1.0
* fasterxml - version 2.8.9
* HTML5
* CSS3
* JavaScript - version ECMAScript 2021

## Features

List of features ready and TODOs for future development  
* Easy to use and straightforward user API.  
* No need for SQL, HQL, or any databse specific language.  
* Straightforward and simple Annotation based for ease of use. 
* Allow ORM to build table based on Annotations in Entities.
* Objects can have fields of type: String, int, Integer, long, Long, double, Double, float, Float, BigDecimal, char, Character, Date, boolean

To-do list: [`for future iterations`]
* Mapping of join columns inside of entities.
* Implement of aggregate functions.
* Uses getter and setter annotations to take in private fields
* Connect to different types of databases
* Allowing currency types
* Allow for custom queries

Known Issues
* Unable to read .properties file dynamically
* Unable to read private fields to get objects from the database

## Getting Started  
Currently project must be included as local dependency. to do so:
```shell
  git clone https://github.com/210823-Enterprise/team2-project1/diyORM.git
  cd diyORM
  mvn install
```
Next, place the following inside your project pom.xml file:
```XML
  <dependency>
    <groupId>com.revature</groupId>
    <artifactId>diyORM</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>

```

Finally, inside your project structure you need a application.proprties file. 
 (typically located src/main/resources/)
 ``` 
  url=path/to/database
  username=username/of/database
  password=password/of/database  
  ```
  
## Usage  
  ### Annotating classes  
  All classes which represent objects in database must be annotated.
   - #### @Entity(name = "table_name)  
      - Indicates that this class is associated with table 'table_name' 
   - #### @Id(name = "column_name") 
      - Indicates that the annotated field is the primary key for the table. 
   - #### @Column(name = "column_name)  
      - Indicates that the Annotated field is a column in the table with the name 'column_name'  
   - #### @Setter(name = "column_name")  
      - Indicates that the anotated method is a setter for 'column_name'.  
   - #### @Getter(name = "column_name")  
      - Indicates that the anotated method is a getter for 'column_name'.  
   - #### @SerialKey(name = "column_name") 
      - Indicates that the annotated field is a serial key.
      - To be implemented
   - #### @JoinColumn (To be implemented/stretch goal)

  ### User API  
  
  - #### `public static ConnectionFactory getInstance()`  
     - returns the singleton instance of the class. It is the starting point to calling any of the below methods.  
  - #### `public HashMap<Class<?>, HashSet<Object>> getCache()`  
     - returns the cache as a HashMap.  
  - #### `public boolean addTableToDb(Object obj, Connection conn)`  
     - Adds a table to the database using the fields as columns.  
  - #### `public boolean UpdateObjectInDB(final Object obj, final String update_columns, Connection conn)`  
     - Updates the given object in the databse. Update columns is a comma seperated list for all columns in the object which needs to be updated
  - #### `public boolean removeObjectFromDB(final Object obj, Connection conn)`  
     - Removes the given object from the database.  
  - #### `public boolean addObjectToDB(final Object obj, Connection conn)`  
     - Adds the given object to the database.  
  - #### `public List<Object> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions, Connection cn)`  
  - #### `public List<Object> getListObjectFromDB(final Class <?> clazz, final String columns, final String conditions,final String operators, Connection cn)`  
  - #### `public List<Object> getListObjectFromDB(final Class<?> clazz, Connection cn)`  
     - Gets a list of all objects in the database which match the included search criteria  
        - columns - comma seperated list of columns to search by.  
        - conditions - coma seperated list the values the columns should match to.  
        - operators - comma seperated list of operators to apply to columns (AND/OR) in order that they should be applied.  
  - #### `public void beginCommit(Connection cn)`  
     - Begin databse commit.
  - #### `public void Rollback(Connection cn)`  
     - Rollback to previous commit.
  - #### `public void Rollback(final String name, Connection cn)`  
     - Rollback to previous commit with given name.
  - #### `public void setSavepoint(final String name, Connection cn)`  
     - Set a savepoint with the given name.
  - #### `public void ReleaseSavepoint(final String name, Connection cn)`  
     - Release the savepoint with the given name.  
  - #### `public void enableAutoCommit(Connection cn)`  
     - Enable auto commits on the database.  
  - #### `public void setTransaction(Connection cn)`  
     - Start a transaction block.  
  - #### `public void addAllFromDBToCache(final Class<?> clazz, Connection cn)`  
     - Adds all objects currently in the databse of the given class type to the cache.  



## License

This project uses the following license: [GNU Public License 3.0](https://www.gnu.org/licenses/gpl-3.0.en.html).
