# Exceptions and Assertions

## Reviewing Exceptions

* All java exceptions extend from the `java.lang.Throwable` class
* A _runtime exception_ may optionally be caught with a `try-catch` block, and can be optionally defined on the method signature. All _runtime exceptions_ extend the `java.lang.RuntimeException` class.
* A _checked exception_ is any class that extends `Exception` but is not a `RuntimeException`. A _checked exception_ must follow the _handle_ or _declare_ rule, where they are either caught or thrown to the caller.
* An _error_ is a fatal JVM exception, and directly extends the `java.lang.Throwable` class. It can be caught, and it does not need to be declared within method signatures. It is recommended not to handle these exceptions.

### Exceptions on the OCP

**Checked Exceptions**
* `java.text.ParseException` - thrown when converting a String to a number
* `java.io.IOException`, `java.io.FileNotFoundException`, `java.io.NotSerializableException` - covers all IO and NIO.2 exceptions. Note: `IOException` is the parent class
* `java.sql.SQLException` - covers all database issues. Note: `SQLException` is the parent class

**Runtime Exceptions**
* `ArithmeticException` - when trying to divide by zero
* `ArrayIndexOutOfBoundsException` - illegal index to access an array
* `ClassCastException` - casting an object to a sublcass of which it is not an instance.
* `IllegalArgumentException` - indicates that a method has been passed an illegal or inappropriate argument
* `NullPointerException` - indicates that there is a null reference where an object is required
* `NumberFormatException` - thrown when attempting to convert a string to a numeric type that does not have the appropriate format

* `java.lang.ArrayStoreException` - Trying to store the wrong data type in an array

* `java.time.DateTimeException` - Receiving an invalid format string for a date

* `java.util.MissingResourceException` - Trying to access a key or resource bundle that does not exist
* `java.lang.IllegalStateException`, `java.lang.UnsupportedOperationException` - Attempting to run an invalid operation in collections and concurrency

### _Try_ Statement

* The _try_ statement consists of a mandatory `try` clause

* It can include one or more `catch` clauses to handle the exceptions that are thrown

* It can include one or none `finally` clause which is run regardless of whether or not an exception was thrown.

* The _try_ statement must have either or both of the `catch` and `finally` clauses for the code to compile.

* Catching a subclass exception in  a `catch` block that is lower down in the list than a super class exception is illegal, as java detects is as unreachable code, e.g:

```java
public class NotFoundException extends RuntimeException {}

try {
    throw new RuntimeException();
} catch (RuntimeException e) {
    System.out.println(e.getMessage());
} catch (NotFoundException e) {  // DOES NOT COMPILE
    System.out.println(e.getMessage());
}
```

* Catching a checked exception that is not thrown within the `try` block is also illegal, as java detects is as unreachable code, e.g:

```java
try {
    throw new IOException();
} catch (IOException e) {
    System.out.println(e.getMessage());
} catch (SQLException e) {  // DOES NOT COMPILE
    System.out.println(e.getMessage());
}
```

### _Throw_ vs _Throws_

* `throw` is used to throw a new `Exception`, e.g. `throw new UnsupportedOperation();`

* `throws` is used to declare that a method throws an Exception, and the call may need to handle the exception. e.g.

```java
public String getDataFromDatabase() throws SQLException {
    throw new UnsupportedOperationException();
}
```

The method above may or may not throw an `SQLException` , to which the calling method should handle (or throw itself). The method throws `UnsupportedOperationException` which is a runtime exception, and hence does not need to be declared.

## Creating Custom Exceptions

* All core Java exception classes can be extended, and it is most common to extend `Exception` for checked exceptions or `RuntimeException` for unchecked exceptions.

## Using Multi-catch

* The _multi-catch_ block was added in Java 7, and was designed to reduce duplication of Exception handling code.
* The syntax follows the regular _try-catch_ clause, exception two or more exception types are specified separated by a pipe (the same as the "or" operator).

```java
public static void main(String[] args) { 
    try {
        Path path = Paths.get("dolphinsBorn.txt");
        String text = new String(Files.readAllBytes(path)); 
        LocalDate date = LocalDate.parse(text); 
        System.out.println(date);
	} catch (DateTimeParseException | IOException e) { 
        e.printStackTrace();
        throw new RuntimeException(e);
	}
}
```

* The exam may trick you with various versions of the above, e.g.

```java
catch(Exception1 e | Exception2 e | Exception3 e)       // DOES NOT COMPILE
catch(Exception1 e1 | Exception2 e2 | Exception3 e3)    // DOES NOT COMPILE
```

* As with regular _try_ blocks, java prevents redundant catches

```java
try {
    throw new FileNotFoundException();
} catch(FileNotFoundException | IOException e) {   // DOES NOT COMPILE 
    e.printStackTrace();
}

try {
    throw new RuntimeException();
} catch(RuntimeException | IOException e) {   // DOES NOT COMPILE
    e.printStackTrace();
}

try {
    throw new IOException();
} catch(RuntimeException | IOException | Exception e) {   // DOES NOT COMPILE
    e.printStackTrace();
}
```

## Using Try-With-Resources

* The _try-with-resources_ block automatically closes all resources opened within the `try` clause. This is known as _automatic resource management_, because Java automatically takes care of the closing of the resource.
* The _try-with-resources_ block does not have to define either the `catch` or `finally` blocks, as with the regular `try` block.

```java
// Without the optional "catch" or "finally" blocks
try (BufferedReader in = Files.newBufferedReader(path1);
     BufferedWriter out = Files.newBufferedWriter(path2)) {

	out.write(in.readLine()); 
}

// With the optional "catch" and "finally" blocks
try (BufferedReader in = Files.newBufferedReader(path1);
	BufferedWriter out = Files.newBufferedWriter(path2)) {

	out.write(in.readLine()); 
} catch (IOException e) {
    // Exception handler
    // Resources are not accessable here, and are still automatically closed
} finally {
    // finally block
    // Resources are not accessable here, and are still automatically closed
}
```

### AutoCloseable

* Only classes that implement the `AutoCloseable` interface can be instantiated within the _try-with-resource_ block. If a class that does implement the `AutoCloseable` interface is within the block, the code will not compile.
* When dealing with multiple resources within a _try-with-resources_ block, Java will close them in **reverse** order!

```java
public class TurkeyCage implements AutoCloseable { 
	public void close() {
		System.out.println("Close gate"); 
    }
    
    public static void main(String[] args) {   // DOES NOT COMPILE 
    	try (TurkeyCage t = new TurkeyCage()) {
			System.out.println("put turkeys in"); 
        }
	}
}
```
* When the `AutoCloseable` implementation throws a checked exception, it must be caught or declared. 
* The example below shows that an exception may be thrown, but neither the  _try-with-resources_ block handles the exception, or the  `main` method does not declare an exception may be thrown. 

```java
public class JammedTurkeyCage implements AutoCloseable { 
	public void close() throws Exception {
		System.out.println("Cage door does not close"); 
    }
    
    public static void main(String[] args) {   // DOES NOT COMPILE 
    	try (JammedTurkeyCage t = new JammedTurkeyCage()) {
			System.out.println("put turkeys in"); 
        }
	}
}
```

## Rethrowing Exceptions

* Java supports re-throwing exceptions, by throwing the caught exception. This is a common pattern when you would like to log the error before handing it off to the caller, e.g:

```java
public void multiCatch() throws SQLException, DateTimeParseException { 
    try {
        parseData();
    } catch (SQLException | DateTimeParseException e) {
        System.err.println(e);
        throw e; 
    } 
}
```

* Since Java 7, it is possible to catch an Exception, and declare that the method throws specific types. This ensures that code duplication is removed, for example:

```java
public void multiCatch() throws SQLException, DateTimeParseException { 
    try {
        parseData();
    } catch (Exception e) {
        System.err.println(e);
        throw e; 
    } 
}
```

## Working with Assertions

* An _assertion_ is a boolean expression that you place at a point in code where you expect something to be true.
* An `assert` statement contains a `boolean` expression and an optional `String` message.
* Either or both of the `boolean_expression` and/or the `error_message` can be enclosed within parenthesis.

```java
assert boolean_expression;
assert boolean_expression: error_message;

assert (boolean_expression);
assert (boolean_expression): error_message;
assert boolean_expression: (error_message);
assert (boolean_expression): (error_message);
```

* When the `boolean_expression` evaluates to `false` a `java.lang.AssertionError` is thrown, halting execution of the program, and printing the optional message.
* Assertions can be enabled by passing the JVM argument `-enableassertions` or `-ea`. Assertions can also be enabled for specific classes or packages.
* When assertions are disabled, Java will skip executing all assertions.

```bash
nested
java -ea:com.foo.bar... my.program.Main

# enable assertions for a specific class
java -ea:com.foo.bar.TestColors my.program.Main

# You can disable assertions using the -disableassertions (or -da) flag 
# for a specific class or package that was previously enabled. 
java -ea:com.foo.bar... -da:com.foo.bar.TestColors my.program.Main
```
