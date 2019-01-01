# IO

The `File` class is used to read information about existing files and directories, list the contents of a directory, and create/delete files and directories. An instance of a `File` class cannot read or write data, it is simply a reference to a file or directory on the file system.

## Introducing the `File` class

### Creating a `File` Object

A `File` can be created by using an _absolute_ or _relative_ path, with the `File` String constructor, e.g.

```java
File file1 = new File("/home/john/data/foo.txt");
System.out.println(file1.exists());

File file2 = new File("data/foo.txt");
System.out.println(file2.exists());
```

A `File` can also be created, but joining a path to a previous parent directory, for example:

```java
File parent = new File("/home/john");
File child = new File(parent, "data/foo.txt");
```

In the above example, if the parent object was null, java would ignore it, and use the `String` constructor.

Java provides two convenience properties for deducing the correct path denominator for the given host operating system. These can be obtained by using either the static property of the `File` class, or by obtaining the system property value, e.g:

```java
System.out.println(System.getProperty("line.separator"));
System.out.println(File.separator);
```

When working on Windows operating systems, remember that the file separator is a backslash `\` which is a reserved character in Java. To use the backslash in a file or directory path, it must be escaped with another backslash, for example `C:\\data\\foo.txt`.

### Working with a  `File` Object

The `File` class contains numerous useful methods for interacting with files and directories within the file system. The most most commonly used ones are shown in the table below. 

| Method | Description |
|--------|-------------|
| `exists()` | Returns true if the file or directory exists. |
| `getName()` | Returns the name of the file or directory denoted by this path. If it is a file, then the file extension will also be returned. |
| `getAbsolutePath()` | Returns the absolute pathname string of this path. |
| `isDirectory()` | Returns true if the file denoted by this path is a directory. |
| `isFile()` | Returns true if the file denoted by this path is a file. |
| `length()` | Returns the number of bytes in the file. For performance reasons, the file system may allocate more bytes on disk than the file actually uses. |
| `lastModified()` | Returns the number of milliseconds since the epoch as a `Long` when the file was last modified. |
| `delete()` | Deletes the file or directory. If this pathname denotes a directory, then the directory must be empty in order to be deleted. |
| `renameTo(File)` | Renames the file denoted by this path. |
| `mkdir()` | Creates the directory named by this path. |
| `mkdirs()` | Creates the directory named by this path including any nonexistent parent directories. |
| `getParent()` | Returns the abstract pathname of this abstract pathname’s parent or null if this pathname does not name a parent directory. |
| `listFiles()` | Returns a File[] array denoting the files in the directory. |

## Introducing Streams

The contents of a file may be accessed or written via a _stream_, which is a list of data elements presented sequentially. Streams should be conceptually thought of as a long, nearly never-ending "stream of water" with data presented on "wave" at a time.

Each type of stream segments data into a “wave” or “block” in a particular way. For example, some stream classes read or write data as individual byte values. Other stream classes read or write individual characters or strings of characters. On top of that, some stream classes read or write groups of bytes or characters at a time, specifically those with the word Buffered in their name.

The `java.io` API provides numerous classes for creating, accessing and manipulating streams. 

### Byte Streams vs Character Streams

The `java.io` API defines two sets of classes for reading and writing streams: those with Stream in their name and those with Reader/Writer in their name. For example, the`java.io` API defines both a `FileInputStream` class as well as a `FileReader` class, both of which define a stream that reads a file. The difference between the two classes is based on how the stream is read or written.

Differences between Streams and Readers/Writers:

1. The stream classes are used for inputting and outputting all types of binary or byte data. 

2. The reader and writer classes are used for inputting and outputting only character and String data. 

It is important to remember that even though readers/writers do not contain the word Stream in their class name, they are still in fact streams.

### Input and Output

Most `Input` stream classes have a corresponding `Output` class and vice versa. It follows, then, that most `Reader` classes have a corresponding `Writer` class.

There are exceptions to these rules. For the exam, you should know that `PrintWriter` has no accompanying `PrintReader` class. Likewise, the `PrintStream` class has no corresponding `InputStream` class.

### Low-Level vs. High-Level Streams

Another way that you can familiarise yourself with the `java.io` API is by segmenting streams into _low-level_ and _high-level_ streams. 

A _low-level_ stream connects directly with the source of the data, such as a file, an array, or a String. Low-level streams process the raw data or resource and are accessed in a direct and unfiltered manner. For example, a `FileInputStream` is a class that reads file data one byte at a time. 

Alternatively, a _high-level_ stream is built on top of another stream using wrapping. For example, the `BufferedReader` takes an instances of `FileReader`, the `FileReader` is a low-level API that is used by the wrapping high-level `BufferedReader` instance.

```java
try (
    BufferedReader bufferedReader = new BufferedReader(new FileReader("zoo-data.txt"))
) { 
    System.out.println(bufferedReader.readLine());
}
```

High-level streams can take other high-level streams as input, for example:

```java
try (
    ObjectInputStream objectStream = new ObjectInputStream(
        new BufferedInputStream(
            new FileInputStream("zoo-data.txt")
        )
    )
) { 
    System.out.println(objectStream.readObject());
}
```

For the exam, the only low-level stream classes you need to be familiar with are the ones that operate on files. The rest of the non-abstract stream classes are all high-level streams.

### Stream Base Classes

The `java.io` library defines four abstract classes that are the parents of all stream classes defined within the API: `InputStream`, `OutputStream`, `Reader`, and `Writer`. For convenience, the authors of the Java API include the name of the abstract parent class as the suffix of the child class. For example, `ObjectInputStream` ends with `InputStream`, meaning it has `InputStream` as an inherited parent class. Although most stream classes in java.io follow this pattern, `PrintStream`, which is an `OutputStream`, does not.

### Decoding Java I/O Class Names

The function of most stream classes can be understood by decoding the name of the class. These properties are summarised in the following list.

* A class with the word `InputStream` or `OutputStream` in its name is used for reading or writing __binary data__, respectively. 

* A class with the word `Reader` or `Writer` in its name is used for reading or writing __character or string data__, respectively. 

* Most, but not all, input classes have a corresponding output class. 

* A _low-level_ stream connects directly with the source of the data. 

* A _high-level_ stream is built on top of another stream using wrapping. 

* A class with `Buffered` in its name reads or writes data in groups of bytes or characters and often improves performance in sequential file systems. 

The table below describes those `java.io` streams you should be familiar with for the exam. Most of the information about each stream, such as whether it is an input or output stream or whether it accesses data using bytes or characters, can be decoded by the name alone.

| Class Name | Low/High Level | Description |
|------------|----------------|-------------|
| `InputStream` | N/A | The abstract class all `InputStream` classes inherit from |
| `OutputStream` | N/A | The abstract class all `OutputStream` classes inherit from |
| `Reader` | N/A | The abstract class all `Reader` classes inherit from  |
| `Writer` | N/A | The abstract class all `Writer` classes inherit from |
| `FileInputStream` | Low | Reads file data as bytes |
| `FileOutputStream` | Low | Writes file data as bytes |
| `FileReader` | Low | Reads file data as characters |
| `FileWriter` | Low | Writes file data as characters |
| `BufferedReader` | High | Reads character data from an existing `Reader` in a buffered manner, which improves efficiency and performance |
| `BufferedWriter` | High | Writes character data to an existing `Writer` in a buffered manner, which improves efficiency and performance |
| `ObjectInputStream` | High | Deserializes primitive Java data types and graphs of Java objects from an existing `InputStream` |
| `ObjectOutputStream` | High | Serializes primitive Java data types and graphs of Java objects to an existing `OutputStream` |
| `InputStreamReader` | High | Reads character data from an existing `InputStream` |
| `OutputStreamWriter` | High | Writes character data to an existing `OutputStream` |
| `PrintStream` | High | Writes formatted representations of Java objects to a binary stream |
| `PrintWriter` | High | Writes formatted representations of Java objects to a text-based output stream |

### Common Stream Operations

#### Closing the Stream

Since streams are considered resources, it is imperative that they be closed after they are used lest they lead to resource leaks. This can be accomplished this by calling the `close()` method in a `finally` block or
using the `try-with-resource` syntax.

#### Flushing the Stream

When data is written to an `OutputStream`, the underlying operating system does not necessarily guarantee that the data will make it to the file immediately, as the data maybe cached in memory. If the data is cached in memory and the application terminates unexpectedly, the data would be lost, because it was never written to the file system. The `flush()` method requests that all accumulated data be written immediately to disk. _Note: when closing a Stream, the  `flush()` method is automatically called._

#### Marking the Stream

The `InputStream` and `Reader` classes include `mark(int)` and `reset()` methods to move the stream back to an earlier position. Before calling either of these methods, you should call the `markSupported()` method, which returns true only if `mark()` is supported. Not all `java.io` input stream classes support this operation, and trying to call `mark(int)` or `reset()` on a class that does not support these operations will throw an exception at runtime. 

#### Skipping over Data

The `InputStream` and `Reader` classes also include a `skip(long)` method, which as you might expect skips over a certain number of bytes. It returns a long value, which indicates the number of bytes that were actually skipped. If the return value is zero or negative, such as if the end of the stream was reached, no bytes were skipped.

## Working with Streams

### `FileInputStream` and `FileOutputStream` Classes

The most basic file stream classes are the `FileInputStream` and `FileOutputStream` classes. They are used to read bytes from a file or write bytes to a file, respectively. These classes include constructors that take a File object or String, representing a path to the file.

The data in a `FileInputStream` object is commonly accessed by successive calls to the `read()` method until a value of `-1` is returned, indicating that the end of the stream has been reached. 

A `FileOutputStream` object is accessed by writing successive bytes using the `write(int)`method. The `FileOutputStream` contains overloaded versions of the `write() method that allow a byte array to be passed and can be used by Buffered classes.

```java
public static void copy(File source, File destination) throws IOException {
    try (
        InputStream in = new FileInputStream(source); 
        OutputStream out = new FileOutputStream(destination)
    ) { 
        int b;
    	while((b = in.read()) != -1) {
    		out.write(b); 
        }
	} 
}

public static void main(String[] args) throws IOException { 
    File source = new File("foo.txt");
	File destination = new File("bar.txt"); 
    copy(source, destination);
}
```

### `BufferedInputStream` and `BufferedOutputStream` Classes

Enhancing the above implementation can be achieved with only a few minor code changes, by wrapping the `FileInputStream` and `FileOutputStream` classes with the `BufferedInputStream` and `BufferedOutputStream` classes, respectively. 

Instead of reading the data one byte at a time, the underlying `read(byte[])` method of `BufferedInputStream` can be used, which returns the number of bytes read into the provided byte array.

The data is written into the `BufferedOutputStream` using the `write(byte[], int, int)` method, which takes as input a byte array, an offset, and a length value, respectively. The offset value is the number of values to skip before writing characters, and it is often set to 0. The length value is the number of characters from the byte array to write.

```java
public static void copy(File source, File destination) throws IOException {
    try (
    	InputStream in = new BufferedInputStream(new FileInputStream(source));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(destination))
    ) {
        int lengthRead;
    	byte[] buffer = new byte[1024];
    	while ((lengthRead = in.read(buffer)) > 0) {
    		out.write(buffer, 0, lengthRead);
    		out.flush(); 
        }
    } 
}

public static void main(String[] args) throws IOException { 
    File source = new File("foo.txt");
	File destination = new File("bar.txt"); 
    copy(source, destination);
}
```

### `FileReader` and `FileWriter` classes

The `FileReader` and `FileWriter` classes, are used for reading and writing text data from/into from files. 

Like the `FileInputStream` and `FileOutputStream` classes, the `FileReader` and `FileWriter` classes contain `read()` and `write()` methods, respectively. These methods read/write char values instead of byte values, but will still return `-1` when reaching the end of a file. 

The `FileReader` and `FileWriter` classes contain the other stream methods such as `close()` and `flush()`. The `FileWriter` class offers a `write(String)` method that allows a String object to be written directly to the stream.

### `BufferedReader` and `BufferedWriter` Classes

The `BufferedReader` and `BufferedWriter` classes, are used for reading and writing text data from/into from files by internally using a buffered approach - similar to the `BufferedInputStream` and `BufferedOutputStream` classes.

The `BufferedReader` class adds a `readLine()` method that will return a line from the given stream represented as a String. The `BufferedWriter` class offers the `write(String)` and `newLine()` methods that allows a String object to be written directly to the stream, and a new line character to be written directly to the stream, respectively.

As with the  `BufferedInputStream` and `BufferedOutputStream` classes, the `BufferedReader` and `BufferedWriter` classes, are buffered, and hence a better performance is achieved when compared to reading/writing each character one at a time.

```java
public static List<String> readFile(File source) throws IOException {
    List<String> data = new ArrayList<String>();
    try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
        String s;
        while((s = reader.readLine()) != null) {
            data.add(s); 
        }
    }
    
    return data; 
}

public static void writeFile(List<String> data, File destination) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
        for(String s: data) { 
            writer.write(s);
            writer.newLine(); 
        }
    } 
}

public static void main(String[] args) throws IOException { 
    File source = new File("data.csv");
    File destination = new File("data-copy.csv");
	
    List<String> data = readFile(source);
    writeFile(data, destination); 
}
```

### `ObjectInputStream` and `ObjectOutputStream` Classes

The process of converting an in-memory object to a stored data format is referred to as _serialization_, with the reciprocal process of converting stored data into an object, which is known as _deserialization_. In order to serialize objects using the `java.io` API, the class they belong to must implement the `java.io.Serializable` interface.

A process attempting to serialize an object will throw a `NotSerializableException` if the class or one of its contained classes does not properly implement the `Serializable` interface. 

Let’s say that you have a particular object within a larger object that is not serializable, such as one that stores temporary state or metadata information about the larger object. You can use the `transient` keyword on the reference to the object, which will instruct the process serializing the object to skip it and avoid throwing a `NotSerializableException`. The only limitation is that the data stored in the object will be lost during the serialization process.

Besides `transient` instance variables, `static` class members will also be ignored during the serialization and deserialization process.

The `java.io` API provides two stream classes for object serialization and deserialization called `ObjectInputStream` and `ObjectOutputStream`.

The `ObjectOutputStream` class includes a method to serialize the object to the stream
called `void writeObject(Object)`, noting that if the Object does not implement `Serializable`, then a `NotSerializableException` will be thrown at runtime.

For the reciprocal process, the `ObjectInputStream` class includes a deserialization method that returns an object called `readObject()`. 

When reading objects using the the `ObjectInputStream` class, we can’t use a `-1` integer value to determine when we have finished reading a file. Instead, the proper technique is to catch an `EOFException`, which marks the program encountering the end of the file. Notice that we don’t do anything with the exception other than finish the method.

```java
public static List<Animal> getAnimals(File dataFile) throws IOException,                                                     ClassNotFoundException {
    List<Animal> animals = new ArrayList<Animal>(); 
    try (
        ObjectInputStream in = new ObjectInputStream(
            new BufferedInputStream(
                new FileInputStream(dataFile)
            )
        )
    ) { 
        while(true) {
            Object object = in.readObject(); 
            if(object instanceof Animal)
                animals.add((Animal)object); 
        }
    } catch (EOFException e) { 
        // File end reached
    }

    return animals; 
}

public static void createAnimalsFile(List<Animal> animals, File dataFile) throws IOException {
    try (
        ObjectOutputStream out = new ObjectOutputStream(
            new BufferedOutputStream(
                new FileOutputStream(dataFile)
            )
        )
    ) {
        for (Animal animal : animals) 
            out.writeObject(animal);
    } 
}

public static void main(String[] args) throws IOException, ClassNotFoundException {
    List<Animal> animals = new ArrayList<>(); 
    animals.add(new Animal("Tommy Tiger", 5, 'T')); 
    animals.add(new Animal("Peter Penguin", 8, 'P'));

    File dataFile = new File("animal.data"); 
    createAnimalsFile(animals, dataFile); 

    System.out.println(getAnimals(dataFile)); // prints [Animal [name=Tommy Tiger, age=5, type=T], Animal [name=Peter Penguin, age=8, type=P]]
}
```

### `PrintStream` and `PrintWriter` Classes

The `PrintStream` and `PrintWriter` classes are high-level stream classes that write formatted representation of Java objects to a text-based output stream. The `PrintStream` class operates on `OutputStream` instances and writes data as bytes, whereas the `PrintWriter` class operates on `Writer` instances and writes data as characters.

#### `print()`

The most basic of the print-based methods is `print()`, which is overloaded with all Java primitives as well as `String` and `Object`. In general, these methods perform `String.valueOf()` on the argument and call the underlying stream’s `write()` method, although they also handle character encoding automatically. 

```java
PrintWriter out = new PrintWriter("zoo.log"); 
out.print(5);                  // PrintWriter method 
out.write(String.valueOf(5));  // Writer method 
```

#### `println()` 

The next methods available in the `PrintStream` and `PrintWriter` classes are the `println()` methods, which are virtually identical to the `print()` methods, except that they insert a line break after the String value is written. 

#### `format()` and `printf()`

The `format()` method in `PrintStream` and `PrintWriter` takes a `String`, an optional locale, and a set of arguments, and it writes a formatted `String` to the stream based on the input. 

The `PrintStream` and `PrintWriter` APIs also include a set of `printf()` methods, which are straight pass-through methods to the `format()` methods. 

```java
public PrintWriter format(String format, Object args...);
public PrintWriter printf(String format, Object args...); 
```

## Interacting with Users

The `java.io` API includes numerous classes for interacting with the user. For example, you might want to write an application that asks a user to log in and reads their login details.
In Java 6, the `java.io.Console` class was introduced with far more features and abilities than the original techniques. After all, `System.in` and `System.out` are just raw streams, whereas `Console` is a class with multiple convenience methods, one that is capable of containing additional methods in the future.

The `Console` class is a singleton, and can be accessed by calling the `System.console()` method. Be aware that this method will return null in environments where text interactions are not supported.

```java
Console console = System.console(); 
if(console != null) {
	String userInput = console.readLine();
	console.writer().println("You entered the following: " + userInput);
}
```



#### `reader()` and `writer()` 

The `Console` class provides access to an instance of `Reader` and `PrintWriter` using the methods `reader()` and `writer()`, respectively.

#### `format()` and `printf()` 

For outputting data to the user, you can use the `PrintWriter writer()` object or use the convenience `format(String, Object...)`, or `printf(String Object...)` methods directly. Both methods takes a String format and list of arguments.

The following sample Console application prints information to the user:

```java
Console console = System.console(); 
if(console == null) {
	throw new RuntimeException("Console not available"); 
} else {
	console.writer().println("Welcome to Our Zoo!"); 
    console.format("Our zoo has 391 animals and employs 25 people.");
    console.writer().println();
    console.printf("The zoo spans 128.91 acres.");
}
```

#### `flush()` 

The `flush()` method forces any buffered output to be written immediately. It is recommended that you call the `flush()` method prior to calling any `readLine()` or `readPassword()` methods in order to ensure that no data is pending during the read. 

#### `readLine()` 

The basic `readLine()` method retrieves a single line of text from the user, and the user presses the `Enter` key to terminate it. 

The Console class also supports an overloaded version of the `readLine()` method with the signature `readLine(String format, Object... args)`, which displays a formatted prompt to the user prior to accepting text. 

The following sample application reads information from the user and writes it back to the screen:

```java
Console console = System.console(); 
if(console == null) {
	throw new RuntimeException("Console not available"); 
} else {
    console.writer().print("How excited are you about your trip today? ");
    console.flush();
    String excitementAnswer = console.readLine();
    
    String name = console.readLine("Please enter your name: ");
    
    console.writer().print("What is your age? ");
    console.flush();
    BufferedReader reader = new BufferedReader(console.reader()); 
    Integer age = Integer.valueOf(reader.readLine());
    
    console.writer().println();
    console.format("Your name is " + name); console.writer().println();
    console.format("Your age is " + age);
	console.printf("Your excitement level is: " + excitementAnswer);
}
```

#### `readPassword()` 

The `readPassword()` method is similar to the `readLine()` method, except that echoing is disabled - the user does not see the text they are typing. 

Also like the `readLine()` method, the `Console` class offers an overloaded version of the `readPassword()` method with the signature `readPassword(String format, Object... args)` used for displaying a formatted prompt to the user prior to accepting text. Unlike the `readLine()` method, though, the `readPassword()` method returns an array of characters instead of a String.

```java
Console console = System.console(); 
if(console == null) {
    throw new RuntimeException("Console not available"); 
} else {
	char[] password = console.readPassword("Enter your password: ");
   	console.format("Enter your password again: "); 
    console.flush();
	char[] verify = console.readPassword();
	boolean match = Arrays.equals(password, verify);
    console.writer().print("Passwords match? " + match);
}
```

