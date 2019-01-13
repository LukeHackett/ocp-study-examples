# Chapter 10 - JDBC

JDBC stands for Java Database Connectivity, and for the exam you only need to know the key interfaces for how to connect, perform queries, and process the results using JDBC.

You are not expected to determine if SQL statements are correct, spot syntax errors in SQL statements or write SQL statements. Notice

## Introducing the Interfaces of JDBC

For the exam you need to know four key interfaces of JDBC that are provided as part of the Java JDK. The concrete classes that implement the four key interfaces are found in each JDBC driver implementation. The four JDBC interfaces are:

- **Driver** - knows how to get a connection to the database
- **Connection** - knows how to communicate with the database
- **Statement** - knows how to run the SQL
- **ResultSet** - knows what was returned by a SELECT query

An example using three of the four above interfaces is shown below.

```java
String url = "jdbc:derby:zoo";
try (
    Connection conn = DriverManager.getConnection(url);
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery("select name from animal")
) {
	while (rs.next()) {
        System.out.println(rs.getString(1));
    }
}
```

## Connecting to a Database

### Building a JDBC URL

A JDBC URL has a variety of formats, but they have three parts in common.  The first piece is the protocol, which is always _jdbc_. The second part is the name of the database such as *derby*, *mysql*, or *postgres*. The third part is “the rest of it,” which is a database-specific format. Examples include:

- `jdbc:derby:zoo`
- `jdbc:postgresql://localhost/zoo`
- `jdbc:oracle:thin:@123.123.123.123:1521:zoo`
- `jdbc:mysql://localhost:3306/zoo?profileSQL=true`

You only need to know about the three main parts, and you are not required to memorise the vendor-specific parts.

### Getting a Database Connection

The `DriverManager` class is part of the JDK, and uses the factory pattern, which means that you call a static `getConnection` method to get a Connection. The `DriverManager.getConnection` method signatures are:

```java
Connection getConnection(String uri) throws SQLException;
Connection getConnection(String uri, String username, String password) throws SQLException;
```

An example use of the `getConnection` method is shown below

```java
import java.sql.*;
public class TestConnect {
	public static void main(String[] args) throws SQLException { 
        Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
        System.out.println(conn);
	}
}
```

*Note:* if the derby JDBC driver is not on the classpath, an `SQLException` is thrown

When using JDBC v4.0 or higher, Java will automatically try to find the correct Driver and user it, based upon the connection String. Alternatively the `Class.forName("org.my.jdbc.Driver");`  can be used before calling `getConnection` to set which driver to use. This is required when using JDBC 3.0 or earlier, but is not required when using JDBC 4.0 or higher.

## Obtaining a Statement

In order to run SQL, you need to create a `Statement`. A `Statement` represents an SQL statement that you want to run using the `Connection`. Getting a `Statement` from a `Connection` can be achieved in two ways.

```java
Statement stmt1 = conn.createStatement();
Statement stmt2 = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
```

The first will create a statement based upon the driver's default settings, whilst the second takes two parameters. The first is the `ResultSet` type, and the other is the `ResultSet` concurrency mode.

### Choosing a *ResultSet* Type

By default, a `ResultSet` is in `TYPE_FORWARD_ONLY` mode. This is what you need most of the time. You can go through the data once in the order in which it was retrieved.

Two other modes that you can request when creating a `Statement` are `TYPE_SCROLL_INSENSITIVE` and `TYPE_SCROLL_SENSITIVE`. Both allow you to go through the data in any order (both forwards and backwards), but only `TYPE_SCROLL_SENSITIVE` supports returning the latest data. For example, if a record was updated while scrolling through a list of records, then the update would be visible when using `TYPE_SCROLL_SENSITIVE`, but the update would not be shown if either  `TYPE_FORWARD_ONLY` or `TYPE_SCROLL_INSENSITIVE` modes were being used.

Most databases and database drivers don’t actually support the `TYPE_SCROLL_SENSITIVE` mode, but it is and objective on the OCP exam.

| ResultSet Type                       | Can go Backwards | See Latest Data from Database Table | Supported by Most Drivers |
| ------------------------------------ | :--------------: | :---------------------------------: | :-----------------------: |
| `ResultSet.TYPE_FORWARD_ONLY`        |        No        |                 No                  |            Yes            |
| `ResultSet.TYPE_ SCROLL_INSENSITIVE` |       Yes        |                 No                  |            Yes            |
| `ResultSet.TYPE_ SCROLL_SENSITIVE`   |       Yes        |                 Yes                 |            No             |

### Choosing a *ResultSet* Concurrency Mode

By default, a `ResultSet` is in `CONCUR_READ_ONLY` mode, meaning that you can’t update the result set. When a `ResultSet` is in `CONCUR_UPDATABLE` mode, it will allow updating the result set.

| ResultSet Type               | Can Read Data | Can Update Data | Supported by All Drivers |
| ---------------------------- | :-----------: | :-------------: | :----------------------: |
| `ResultSet.CONCUR_READ_ONLY` |      Yes      |       No        |           Yes            |
| `ResultSet.CONCUR_UPDATABLE` |      Yes      |       No        |            No            |

## Executing a Statement

The way you run SQL varies depending on what kind of SQL statement it is - *SELECT*, *INSERT*, *UPDATE* or *DELETE*.

The `executeUpdate(String sql)` method is used to modify data within a database, and thus only supports *INSERT*, *UPDATE* or *DELETE* statements. It returns an `integer` which denotes the number of records than have been inserted, updated or deleted depending upon the query.

```java
Statement stmt = conn.createStatement(); 

int result = stmt.executeUpdate("insert into species values(10, 'Deer', 3)");
System.out.println(result); // 1

result = stmt.executeUpdate("update species set name = '' where name = 'None'");
System.out.println(result); // 0

result = stmt.executeUpdate("delete from species where id = 10");
System.out.println(result); // 1
```

The `executeQuery(String sql)` method is used to obtain data from a database, and thus only supports *SELECT* statements. It returns a `ResultSet` object that contains the results of the query.

```java
ResultSet rs = stmt.executeQuery("select * from species");
```

The `execute(String sql)` method can run any type of SQL query, including *SELECT*, *INSERT*, *UPDATE* or *DELETE* statements. It returns a `boolean` as to denote wether or not there is a `ResultSet`, to which this can be used to obtain the correct return value.

```java
boolean isResultSet = stmt.execute(sql); 
if (isResultSet) {
	ResultSet rs = stmt.getResultSet();
	System.out.println("ran a SELECT query"); 
} else {
	int result = stmt.getUpdateCount();
	System.out.println("ran an INSERT, UPDATE or DELETE query"); 
}
```

If the wrong type of SQL String is supplied to a method - for example performing a *SELECT* query using the `executeUpdate` method - then an `SQLException` is thrown.

The table below shows the return types of the three execute methods.

| Method                 | Return Type | What Is Returned for<br />SELECT | What Is Returned for<br />INSERT, UPDATE, DELETE |
| ---------------------- | ----------- | -------------------------------- | ------------------------------------------------ |
| `stmt.execute()`       | `boolean`   | `true`                           | `false`                                          |
| `stmt.executeQuery()`  | `ResultSet` | The rows and columns returned    | n/a - `SQLException` thrown                      |
| `stmt.executeUpdate()` | `int`       | n/a - `SQLException` thrown      | Number of rows added, changed or removed         |

## Getting Data from a ResultSet

### Reading a ResultSet

When working with a forward-only `ResultSet`, most of the time you will write a loop to look at each row, for example:

```java
Map<Integer, String> idToNameMap = new HashMap<>();

ResultSet rs = stmt.executeQuery("select id, name from species"); 
while(rs.next()) {
	int id = rs.getInt("id");            // could also use rs.getInt(1);
	String name = rs.getString("name");  // could also use rs.getString(0);
    idToNameMap.put(id, name);
}

System.out.println(idToNameMap);  // {1=African Elephant, 2=Zebra}
```

Rather than using the column names to access the data, the column number could be used instead, however these are less readable and *start at 1 rather than 0*.

The following scenarios will result in an `SQLException` to be thrown:

- Failure to check if data is available - i.e. forgetting to call `rs.next()`
- Trying to obtain a column's value that doesn't exist (either by name or index) 
- Trying to access a result set when there were not results (e.g. an empty query)

### Getting Data for a Column

There are lots of get methods on the `ResultSet` interface, but the ones that are on the exam, are shown in the table below

| Method Name    | Return Type          | Example Database Type |
| -------------- | -------------------- | --------------------- |
| `getBoolean`   | `boolean`            | `BOOLEAN`             |
| `getDouble`    | `double`             | `DOUBLE`              |
| `getInt`       | `int`                | `INTEGER`             |
| `getLong`      | `long`               | `LONG`                |
| `getObject`    | `Object`             | `OBJECT`              |
| `getString`    | `String`             | `STRING`              |
| `getDate`      | `java.sql.Date`      | `DATE`                |
| `getTime`      | `java.sql.Time`      | `TIME`                |
| `getTimestamp` | `java.sql.Timestamp` | `TIMESTAMP`           |

When working with `Date`, `Time` and `Timestamp` these live within the `java.sql` package, but can be converted back to `LocalDate`, `LocalTime` and `LocalDateTime` using the helper methods, as shown below:

```java
java.sql.Date sqlDate = rs.getDate(1); 
LocalDate localDate = sqlDate.toLocalDate();

java.sql.Time sqlTime = rs.getTime(2); 
LocalTime localTime = sqlTime.toLocalTime();

java.sql.Timestamp sqlTimeStamp = rs.getTimestamp(2); 
LocalDateTime localDateTime = sqlTimeStamp.toLocalDateTime();
```

### Scrolling *ResultSet*

A scrollable `ResultSet` allows you to position the cursor at any row. 

A scrollable `ResultSet` supports the `previous()` method, which moves the cursor backwards by one row and returns true if pointing to a valid row of data. If the `previous()` method is called on a non-scrollable `ResultSet`, then an `SQLException` is thrown.

```java
Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

ResultSet rs = stmt.executeQuery("select id from species order by id"); 
rs.afterLast();
System.out.println(rs.previous());  // true 
System.out.println(rs.getInt(1));   // 2
System.out.println(rs.previous());  // true 
System.out.println(rs.getInt(1));   // 1
System.out.println(rs.last());      // true 
System.out.println(rs.getInt(1));   // 2
System.out.println(rs.first());     // true
System.out.println(rs.getInt(1));   // 1
rs.beforeFirst();
System.out.println(rs.getInt(1));   // throws SQLException
```

There are also methods to start at the beginning and end of the `ResultSet`. The `first()` and `last()` methods return a boolean for whether they were successful at finding a row. The example below shows both the `first()` and `last()` methods returning false, meaning that there are no records in the result set.

```java
Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
ResultSet rs = stmt.executeQuery("select id from species where id = -99"); System.out.println(rs.first());  // false
System.out.println(rs.last());   // false
```

The `beforeFirst()` and `afterLast()` methods have a return type of void, since it is always possible to get to a spot that doesn’t have data.

The `absolute()` takes the row number to which you want to move the cursor as a parameter. A positive number moves the cursor to that numbered row. Zero moves the cursor to a location immediately before the first row - the equivalent of calling `beforeFirst()`. A negative number means to start counting from the end of the `ResultSet` rather than from the beginning. It returns a boolean if the cursor is pointing to a row with data.

```java
Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
ResultSet rs = stmt.executeQuery("select id from animal order by id");
System.out.println(rs.absolute(2));   // returns true (points at record #2)
System.out.println(rs.absolute(0));   // returns false (points before the first record)
System.out.println(rs.absolute(5));   // returns false (if total number of records < 5)
System.out.println(rs.absolute(-2));  // returns true (points at 2nd from last record)
```

*Remember:* 1 represents the first row, rather than 0 which represents the "headers".

Finally, there is a `relative()` method that moves forward or backward the requested number of rows relative to the current position. It returns a boolean if the cursor is pointing to a row with data.

```java
Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
ResultSet rs = stmt.executeQuery("select id from animal order by id");
System.out.println(rs.next());           // returns true
System.out.println(rs.getString("id"));  // prints first record id (e.g. 1)
System.out.println(rs.relative(2));      // returns true (moves forward by 2 records)
System.out.println(rs.getString("id"));  // prints third record id (e.g. 3)
System.out.println(rs.relative(-1));     // returns true (moves backwards by 1 record)
System.out.println(rs.getString("id"));  // prints second record id (e.g. 2)
System.out.println(rs.relative(4));      // returns false (if total number of records < 6)
```

The methods that you can use when traversing a `ResultSet` are listed in the table below.

| Method                       | Description                                                  | Requires Scrollable *ResultSet* |
| ---------------------------- | ------------------------------------------------------------ | ------------------------------- |
| `boolean absolute(int rowNum)` | Move cursor to the specified row number                      | Yes                             |
| `void afterLast()`             | Move cursor to a location immediately after the last row     | Yes                             |
| `void beforeFirst()`           | Move cursor to a location immediately before the first row   | Yes                             |
| `boolean first()`              | Move cursor to the first row                                 | Yes                             |
| `boolean last()`               | Move cursor to the last row                                  | Yes                             |
| `boolean next()`               | Move cursor one row forward                                  | No                              |
| `boolean previous()`           | Move cursor one row backward                                 | Yes                             |
| `boolean relative(int rowNum)` | Move cursor forward or backward the specified number of rows | Yes                             |

## Closing Database Resources

The `Connection`, `Statement` and `ResultSet` all implement the `AutoCloseable` interface and thus will be closed automatically if created within a `try-with-resources` block, e.g.

```java
try (
    Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery("select name from animal")
) {
	while (rs.next()) System.out.println(rs.getString(1));
}
```

The resources are closed in reverse order from which they were opened. This means that the `ResultSet` is closed first, followed by the `Statement`, and then the `Connection`. This is the standard order to close resources.

It isn't strictly necessary to close all three resources, as closing a JDBC resource should close any resources that it created. In particular, the following are true: 

- Closing a `Connection` also closes the `Statement` and `ResultSet`.
- Closing a `Statement` also closes the `ResultSet`. 

It is also worth noting that JDBC automatically closes a `ResultSet` when you run another SQL statement from the same `Statement`.

## Dealing with Exceptions

A checked `SQLException` might be thrown by any JDBC method, and the `SQLException` class contains some additional methods that are used exclusively to obtain specific errors raised by a database.

```java
try (
    Connection conn = DriverManager.getConnection("jdbc:derby:zoo");
	Statement stmt = conn.createStatement();
	ResultSet rs = stmt.executeQuery("select not_a_column from animal")
) {
	while (rs.next()) 
        System.out.println(rs.getString(1));

} catch (SQLException e) { 
	System.out.println(e.getMessage());    // prints ERROR: column "not_a_column" does not exist
    System.out.println(e.getSQLState());   // prints 42703
    System.out.println(e.getErrorCode());  // prints 0
}
```

The `getMessage()` method returns a human-readable message as to what went wrong. The `getSQLState()`method returns a code as to what went wrong, whilst `getErrorCode()` is a database-specific code.

*Note:* On the exam, either you will be told the names of the columns in a table or you can assume that they are correct. Similarly, you can assume that allSQL is correct.