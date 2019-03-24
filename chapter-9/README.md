# Chapter 9 - NIO.2

The OCP exam topics covered in this chapter include the following: 
- Use Path interface to operate on file and directory paths 
- Use Files class to check, read, delete, copy, move, manage metadata of a file or directory 
- Use Stream API with NIO.2 

## Introducing NIO.2

### Introducting _Path_

The `java.nio.file.Path` interface, or Path interface for short, represents a hierarchical path on the storage system to a file or directory. Path is a direct replacement for the legacy `java.io.File` class, and conceptually it contains many of the same properties.

Just like the  `File` class, a `Path` may refer to an absolute path or relative path within the file system, although unlike the `File` class, the `Path` interface contains support for symbolic links.

NIO.2 also includes helper classes such as `java.nio.file.Files`, whose primary purpose is to operate on instances of Path objects.

### Creating Paths

Since Path is an interface, you need a factory class to create instances of one.

#### Using the _Paths_ Class

The simplest way to obtain a Path object is using the `java.nio.files.Paths` factory class, or `Paths` for short. To obtain a reference to a file or directory, you would call the static `getPath(String)` method:

```java
Path path1 = Paths.get("pandas/cuddly.png");                      // relative file
Path path2 = Paths.get("c:\\zooinfo\\November\\employees.txt");   // absolute path
Path path3 = Paths.get("/home/zoodirector");                      // absolute path
```

The `Paths` interface also supports varargs of type String, to help build the path, examples are:

```java
Path path1 = Paths.get("pandas","cuddly.png");
Path path2 = Paths.get("c:","zooinfo","November","employees.txt"); 
Path path3 = Paths.get("/","home","zoodirector");
```

The `Paths` interface also supports `URI` which can be used to obtain a reference to a URI-based resource:

 ```java
Path path1 = Paths.get(new URI("file:///c:/zoo-info/November/employees.txt")); 
Path path2 = Paths.get(new URI("file:///home/zoodirectory")); 

// URIs must be absolute file paths, otherwise a Runtime exception is thrown.
// 3x slashes denote an absolute file path when using URIs
Path path3 = Paths.get(new URI("file://pandas/cuddly.png")); 
 ```

#### Accessing the Underlying _FileSystem_ Object

The `FileSystem` class has a protected constructor, so we use the plural `FileSystems` factory class to obtain an instance of `FileSystem`, as shown in the following example code:

```java
Path path1 = FileSystems.getDefault().getPath("pandas/cuddly.png");
Path path2 = FileSystems.getDefault().getPath("c:","zooinfo","November", "employees.txt");
Path path3 = FileSystems.getDefault().getPath("/home/zoodirector");
```

Most of the time our application will access a `Path` object that is within the local file system, the `FileSystems` factory class does give the ability to connect to a remote file system, as shown in the following sample code: 

```java
FileSystem fileSystem = FileSystems.getFileSystem(new URI("http://www.example.com")); 
Path path = fileSystem.getPath("duck.txt"); 
```

#### Working with Legacy File Instances

The legacy `java.io.File` class supports converting a `File` instance to a `Path` instance using the `toPath()` method:

```java
File file = new File("pandas/cuddly.png");
Path path = file.toPath(); 
```

In addition to this, the `Path` interface supports converting a `Path` instance back to a `File` instance: 

```java
Path path = Paths.get("cuddly.png");
File file = path.toFile(); 
```

## Interacting with Paths and Files

Many of the methods in the NIO.2 API that interact with real files and directories take additional options flags in the form of a vararg. The table below lists the values that you should know for the exam, each value can be applied to both files and directories.

| Enum Value         | Usage                                                        | Description                                                  |
| ------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `NOFOLLOW_LINKS`   | Test file exists<br/>Read file data<br />Copy file<br />Move file | If provided, symbolic links when encountered will not be traversed. Useful for performing operations on symbolic links themselves rather than their target. |
| `FOLLOW_LINKS`     | Traverse a directory tree                                    | If provided, symbolic links when encountered will be traversed. |
| `COPY_ATTRIBUTES`  | Copy file                                                    | If provided, all metadata about a file will be copied with it. |
| `REPLACE_EXISTING` | Copy file<br />Move file                                     | If provided and the target file exists, it will be replaced; otherwise, if it is not provided, an exception will be thrown if the file already exists. |
| `ATOMIC_MOVE`      | Move file                                                    | The operation is performed in an atomic manner within the file system, ensuring that any process using the file sees only a complete record. Method using it may throw an exception if the feature is unsupported by the file system. |

_**Note:** You do not need to memorise which of the dozens of NIO.2 methods take which optional arguments for the exam, but you should be able to recognise what they do when you see them on the exam._

### Using Path Objects

Many of the methods in the `Path` interface transform the path value in some way and return a new `Path` object, allowing the methods to be chained.

#### `toString()`, `getNameCount()`, and `getName()`

The `toString()` method returns a String representation of the entire path. The `getNameCount()` and `getName(int)` methods are often used in conjunction to retrieve the number of elements in the path and a reference to each element, respectively. 

```java
Path path = Paths.get("/land/hippo/harry.happy");
for(int i = 0; i < path.getNameCount(); i++) {
	System.out.println(" Element " + i + " is: " + path.getName(i));
}
// prints:
// Element 0 is: land
// Element 1 is: hippo
// Element 2 is: harry.happy
```

The root element `/` is not included in the list of names, and the `getName(int)` method is zero-indexed, with the file system excluded from the path components.

#### `getFileName()`, `getParent()`, and `getRoot()`
The `getFileName()` returns a `Path` instance representing the filename (including any extension). 

The `getParent()` method returns a `Path` instance representing the parent path or null if there is no such parent. If the instance of the `Path` object is relative, this method will stop at the top-level element defined in the Path object — i.e. it will not traverse outside the working directory to the file system root. 

The `getRoot()` returns the root element for the `Path` object or null if the `Path` object is relative. 

#### `isAbsolute()` and `toAbsolutePath()`

The `isAbsolute()` method returns `true` if the path the object references is absolute and `false` if the path the object references is relative.

The `toAbsolutePath()` method converts a relative `Path` object to an absolute `Path` object by joining it to the current working directory. If the `Path`  object is already absolute, then the method just returns a new `Path` object with the same value.

```java
Path path1 = Paths.get("C:\\birds\\egret.txt"); 
System.out.println(path1.isAbsolute());      // true 
System.out.println(path1.toAbsolutePath());  // C:\birds\egret.txt

Path path2 = Paths.get("birds/condor.txt"); 
System.out.println(path2.isAbsolute());      // false
System.out.println(path2.toAbsolutePath());  // (working directory = /home) /home/birds/condor.txt
```

#### `subpath()`

The `subpath(int,int)` method returns a relative subpath of the `Path` object, referenced by an inclusive start index and an exclusive end index. The method does not include the root of the file, nor will it accept invalid start index and end index values.

```java
Path path = Paths.get("/mammal/carnivore/raccoon.image");
System.out.println(path.subpath(0,3));  // prints mammal/carnivore/raccoon.image
System.out.println(path.subpath(1,3));  // prints carnivore/raccoon.image
System.out.println(path.subpath(1,2));  // prints raccoon.image
System.out.println(path.subpath(0,4));  // Throws Runtime Exception
System.out.println(path.subpath(1,1));  // Throws Runtime Exception
```

#### `relativize()`

The `relativize(Path)` method is used for constructing the relative path from one Path object to another - i.e. navigating to a path from a given path.

```java
Path path1 = Paths.get("E:\\habitat");
Path path2 = Paths.get("E:\\sanctuary\\raven"); System.out.println(path1.relativize(path2));  // prints ..\sanctuary\raven
System.out.println(path2.relativize(path1));  // prints ..\..\habitat
```

The `relativize(Path)` method also supports files as well as relative paths, for example:

```java
Path path1 = Paths.get("fish.txt");
Path path2 = Paths.get("birds.txt"); 
System.out.println(path1.relativize(path2));  // prints ..\birds.txt
System.out.println(path2.relativize(path1));  // prints ..\fish.txt
```



The `relativize()` method requires that *both paths be absolute or both relative*, and it will throw an `IllegalArgumentException` if a relative path value is mixed with an absolute path value. For example, the following would throw an exception at runtime:

```java
Path path1 = Paths.get("/primate/chimpanzee");
Path path2 = Paths.get("bananas.txt"); 
path1.relativize(path3);  // throws exception at runtime
```

On Windows-based systems, it also requires that if absolute paths are used, then both paths must have the same root directory or drive letter. For example, the following would also throw an `IllegalArgumentException` at runtime in a Windows-based system since they use different roots:

```java
Path path3 = Paths.get("c:\\primate\\chimpanzee");
Path path4 = Paths.get("d:\\storage\\bananas.txt"); 
path3.relativize(path4);  // throws exception at runtime
```

#### `resolve()`

The `resolve(Path)` method is used for creating a new `Path` by joining an existing path to the current path. This method simply joins two paths together, and does not clean up the path symbols.

```java
Path path1 = Paths.get("/cats/../panther"); 
Path path2 = Paths.get("food"); 
System.out.println(path1.resolve(path2));  // prints /cats/../panther/food
```

If the `resolve()` method receives an absolute path as input, then it will always return the absolute path, for example:

```java
Path path1 = Paths.get("/turkey/food");
Path path2 = Paths.get("/tiger/cage");
Path path3 = Paths.get("./lion/cub");

System.out.println(path1.resolve(path2));  // prints /tiger/cage
System.out.println(path3.resolve(path1));  // prints /turkey/food
```

#### `toRealPath()`

The `toRealPath()` method returns a reference to a real path within the file system, even though the `Path` object may or may not point to an exiting file within the file system. The  `toRealPath()` method will convert a relative path to an absolute path, and also verifies that the file referenced by the path actually exists, and thus it throws a checked IOException at runtime if the file cannot be located.  It is also the only Path method to support the `NOFOLLOW_LINKS` option.

Assume there is a symbolic link from `food.source` to `food.txt`, as described in the following relationship: 

```
/zebra/food.source → /horse/food.txt
```

and the following code:

```java
try { 
    Path path1 = Paths.get("/zebra/food.source").toRealPath();
    Path path2 = Paths.get(".././food.txt").toRealPath()); 
    System.out.println(path1);  // prints /horse/food.txt
    System.out.println(path2);  // prints /horse/food.txt
} catch (IOException e) {
    // IOException thrown when file does not exist
}
```

We can also use the `toRealPath()` method to gain access to the current working directory, such as shown here: 

```java
System.out.println(Paths.get(".").toRealPath()); 
```

### Interacting with Files

Java provides a helper class called `java.nio.file.Files`, or `Files` for short, that provides a number of File operations. Unlike the methods in the `Path` and `Paths` class, most of the options within the `Files` class will throw an exception if the file to which the `Path` refers does not exist.

#### `exists()`

The `Files.exists(Path)` method takes a `Path` object and returns `true` if, and only if, it references a file that exists in the file system. This method does not throw an `Exception` if the underlying file or directory does not exist.

```java
Files.exists(Paths.get("/ostrich/feathers.png"));  // file check
Files.exists(Paths.get("/ostrich"));               // directory check
```

#### `isSameFile()`

The `Files.isSameFile(Path,Path)` method is useful for determining if two `Path` objects relate to the same file or directory within the file system. It takes two `Path` objects as input and follows symbolic links.

The `Files.isSameFile(Path,Path)` method  first checks if the `Path` objects are equal in terms of `equal()`, and if so, it returns `true` without checking to see if either file exists. 

If the `Path` object equals() comparison returns `false`, then it locates each file and determines if they are the same, throwing a checked `IOException` if either file does not exist. If the files have the same name and the same contents, but are at different locations, they are considered different files within the file system.

```java
Files.isSameFile(Paths.get("/user/home/cobra"), Paths.get("/user/home/snake"));  // false
Files.isSameFile(Paths.get("/user/tree/../monkey"), Paths.get("/user/monkey"));  // true
```

#### `createDirectory()` and `createDirectories()`

The `Files.createDirectory(Path)` method creates a directory on the file system, with the plural form of the method `Files.createDirectories(Path)`, creating the target directory along with any nonexistent parent directories leading up to the target directory in the path.

The directory-creation methods can throw the checked `IOException`, such as when the directory cannot be created or already exists.

```java
try {
    // Throws IOException if the /bison directory does not already exist
    Files.createDirectory(Paths.get("/bison/field"));

    // Will create all parent directories that do not exist
    Files.createDirectories(Paths.get("/bison/field/pasture/green"));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### ` copy()`

The `Files.copy(Path, Path)` performs copy of file or a shallow copy of a directory from one location to another.  The `Files.copy(Path, Path)` method throws the checked `IOException`, such as when the file or directory does not exist or cannot be read.

By default, copying files and directories will traverse symbolic links, although it will not overwrite a file or directory if it already exists, nor will it copy file attributes.

```java
try {
    // Performs a shallow copy of the directory
    // Child files and directories of this directory are not copied
	Files.copy(Paths.get("/panda"), Paths.get("/panda-save"));
    
    // Performs a copy of the file
	Files.copy(Paths.get("/panda/bamboo.txt"), Paths.get("/panda-save/bamboo.txt"));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### Copying Files with _java.io_ and NIO.2

Copying files can also be achieved using Streams, to which there is support for copying an `InputStream` data into a file with `Files.copy(InputStream, Path)` as well as reading the contents of a file into an `OutputStream` using  `Files.copy(Path, OutputStream)`.

```java
try (
    InputStream is = new FileInputStream("source-data.txt");
	OutputStream out = new FileOutputStream("output-data.txt")
) { 
    CopyExamples
	Files.copy(is, Paths.get("c:\\mammals\\wolf.txt"));

    CopyExamples
	Files.copy(Paths.get("c:\\fish\\clown.xls"), out);
} catch (IOException e) {
	// Handle file I/O exception... 
}
```

Like the first `copy()` method, the `Files.copy(InputStream, Path)` method also supports optional vararg options, since the data is being written to a file represented by a `Path` object. The second method, `Files.copy(Path, OutputStream)`, does not support optional vararg values.

#### `move()`

The `Files.move(Path,Path)` method moves (or renames) a file or directory within the file system, throwing a checked `IOException` in the event that the file or directory could not be found or moved.

By default, the `move()` method will follow links, throw an exception if the file already exists, and not perform an atomic move.

```java
try {
	Files.move(Paths.get("c:\\zoo"), Paths.get("c:\\zoo-new"));
	Files.move(Paths.get("c:\\user\\addresses.txt"), Paths.get("c:\\zoo-new\\addresses.txt"));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### `delete()` and `deleteIfExists()`

The `Files.delete(Path)` method deletes a file or **empty** directory within the file system. The method will throw a checked `IOException` under various circumstances. If the path represents a non-empty directory a  `DirectoryNotEmptyException` runtime exception is thrown, whilst if the target of the path is a symbol link, then the symbolic link will be deleted, not the target of the link. 

The `Files.deleteIfExists(Path)` method is identical to the `Files.delete(Path)` method, except that it will not throw an exception if the file or directory does not exist, but instead it will return a boolean value of false. It will still throw an exception if the file or directory does exist but fails, such as in the case of the directory not being empty. 

```java
try {
	Files.delete(Paths.get("/vulture/feathers.txt"));
	Files.deleteIfExists(Paths.get("/pigeon"));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### `newBufferedReader()` and `newBufferedWriter()`

The `Files.newBufferedReader(Path, Charset)` method reads the file specified at the `Path` location using a `java.io.BufferedReader` object using the specified `Charset` value to determine what character encoding to use to read the file. 

```java
Path path = Paths.get("/animals/gopher.txt");
Charset charset = Charset.forName("US-ASCII");

try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
	// Read from the stream
	String currentLine = null;
	while((currentLine = reader.readLine()) != null)
		System.out.println(currentLine); 
} catch (IOException e) {
	// Handle file I/O exception... 
}
```

The `Files.newBufferedWriter(Path,Charset)` writes to a file specified at the `Path` location using a `BufferedWriter`, also taking a specified `Charset` value to determine what character encoding to use to read the file. 

```java
Path path = Paths.get("/animals/gorilla.txt");
Charset charset = Charset.defaultCharset();

try (BufferedWriter writer = Files.newBufferedWriter(path, charset)) {
    // Create a file with the specified contents, overwriting the file if it already exists.
	writer.write("Hello World"); 
} catch (IOException e) {
	// Handle file I/O exception... 
}
```

#### `readAllLines()`

The `Files.readAllLines()` method reads all of the lines of a text file and returns the results as an ordered `List` of `String` values. The NIO.2 API includes an overloaded version that takes an optional `Charset` value. 

```java
Path path = Paths.get("/fish/sharks.log"); 

try {
	final List<String> lines = Files.readAllLines(path);
    for(String line: lines) { 
        System.out.println(line);
	}
} catch (IOException e) {
	// Handle file I/O exception... 
}
```

The method may throw an IOException if the file cannot be read, as well as an `OutOfMemoryError` may be thrown if loading a large file into memory. 

## Understanding File Attributes

The `Files` class also provides numerous methods accessing file and directory metadata, referred to as file attributes, such as denoting whether or not a file or directory may be hidden within a file system or marked with a permission that prevents the current user from reading it.

### Discovering Basic File Attributes 

#### `isDirectory()`, `isRegularFile()`, and `isSymbolicLink()`

The `Files` class includes three methods for determining if a path refers to a directory, a regular file, or a symbolic link. The methods to accomplish this are named `Files.isDirectory(Path)`, `Files.isRegularFile(Path)`, and `Files.isSymbolicLink(Path)`, respectively.

Java defines a _regular file_ as one that contains content, as opposed to a symbolic link, directory, resource, or other non-regular file that may be present in some operating systems. A symbolic link may point to a directory in which case `Files.isDirectory(Path)` would return `true`, and if the symbolic link points to a regular file, then `Files.isRegularFile(Path)` would return `true`.

```java
Files.isDirectory(Paths.get("/canine/coyote/fur.jpg")); Files.isRegularFile(Paths.get("/canine/types.txt")); Files.isSymbolicLink(Paths.get("/canine/coyote"));
```

The table below illustrates the various return values when the directory `/canine/coyote` and file `/canine/types.txt` exists. Furthermore, assume that `/coyotes` is a symbolic link within the file system that points to another path within the file system.

|                     | isDirectory()                       | isRegularFile()                        | isSymbolicLink() |
| ------------------- | ----------------------------------- | -------------------------------------- | ---------------- |
| `/canine/coyote`    | `true`                              | `false`                                | `false`          |
| `/canine/types.txt` | `false`                             | `true`                                 | `false`          |
| `/coyotes`          | `true` if the target is a directory | `true` if the target is a regular file | `true`           |

#### `isHidden()`

The `Files.isHidden(Path)` method is used to determine whether a file or directory is hidden within the file system. The `isHidden()` method throws the checked `IOException`, as there may be an I/O error reading the underlying file information. 

```java
try {
    // prints true if the walrus.txt file is available and hidden
    System.out.println(Files.isHidden(Paths.get("/walrus.txt")));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### `isReadable()` and `isExecutable()`

The `Files` class includes two methods for reading file accessibility: `Files.isReadable(Path)` and `Files.isExecutable(Path)`.  The `isReadable()` and `isExecutable()` methods do not throw exceptions if the file does not exist but instead return false.

```java
// returns true if the baby.png file exists and its contents are readable
// based on the permission rules of the underlying file system
System.out.println(Files.isReadable(Paths.get("/seal/baby.png"))); 

// returns true if the baby.png file is marked executable within the file system
System.out.println(Files.isExecutable(Paths.get("/seal/baby.png")));
```

#### size()

The `Files.size(Path)` method is used to determine the size of the file in bytes, and will throw the checked `IOException` if the file does not exist or if the process is unable to read the file information.

```java
try { 
    System.out.println(Files.size(Paths.get("/zoo/c/animals.txt")));
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### `getLastModifiedTime()` and `setLastModifiedTime()`

The `Files.getLastModifiedTime(Path)` method returns a `FileTime` for the given `Path` to which the date/time information about when a file was accessed, modified, or created can be obtained. For convenience, the `FileTime` class has a `toMillis()` method that returns the epoch time.

The `Files.setLastModifiedTime(Path, FileTime)` method provides a mechanism for updating the last-modified date/time of a file. The `FileTime` class also has a static `fromMillis()` method that converts from the epoch time to a `FileTime` object.

```java
try {
	Path path = Paths.get("/rabbit/food.jpg"); 
    System.out.println(Files.getLastModifiedTime(path).toMillis());

    Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
	System.out.println(Files.getLastModifiedTime(path).toMillis());
} catch (IOException e) {
	// Handle file I/O exception...
}
```

#### `getOwner()` and `setOwner()`

The `Files.getOwner(Path)` method returns an instance of `UserPrincipal` that represents the owner of the file within the file system. There is also a method to set the owner, called `Files.setOwner(Path,UserPrincipal)`. Both the `getOwner()` and `setOwner()` methods can throw the checked exception `IOException` incase of any issues accessing or modifying the file.

In order to set a file owner to an arbitrary user, the NIO.2 API provides a`UserPrincipalLookupService` helper class for finding a `UserPrincipal` record for a particular user within a file system. If the user cannot be found, a checked `UserPrincipalNotFoundException` exception will be thrown.

```java
try {
    // Read owner of file
    Path path = Paths.get("/chicken/feathers.txt");
    System.out.println(Files.getOwner(path).getName());
    
    // Change owner of file
    // Can also use: FileSystems.getDefault().getUserPrincipalLookupService()..lookupPrincipalByName("jane");
    UserPrincipal owner = path.getFileSystem()
        .getUserPrincipalLookupService()
        .lookupPrincipalByName("jane");
    Files.setOwner(path, owner);
    
    // Output the updated owner information
    System.out.println(Files.getOwner(path).getName());
} catch (IOException e) {
    // Handle file I/O exception...
}
```

### Improving Access with Views

A view is a group of related attributes for a particular file system type. A file may support multiple views, allowing you to retrieve and update various sets of information about the file.

#### Understanding Views

The `Files` API includes two sets of methods of analogous classes for accessing view information. The `Files.readAttributes()` method returns a read-only view of the file attribute, while the `Files.getFileAttributeView()` method returns the underlying attribute view, and it provides a direct resource for modifying file information. 

Both of these methods can throw a checked `IOException`, such as when trying to read Windows-based attributes within a Linux file system may throw an `UnsupportedOperationException`. 

The table below lists the commonly used attributes and view classes, however, only the first row is required knowledge for the exam.

| Attributes Class      | View Class               | Description                                                  |
| --------------------- | ------------------------ | ------------------------------------------------------------ |
| `BasicFileAttributes` | `BasicFileAttributeView` | Basic set of attributes supported by all file systems        |
| `DosFileAttributes`   | `DosFileAttributeView`   | Attributes supported by DOS/Windows-based systems            |
| `PosixFileAttributes` | `PosixFileAttributeView` | Attributes supported by POSIX systems, such as UNIX, Linux, Mac, and so on |

#### Reading Attributes

The `Files.readAttributes(Path, Class<A>)` method returns read-only versions of a file view. The second parameter uses generics such that the return type of the method will be an instance of the provided class.

All attributes classes extend from `BasicFileAttributes`, therefore it contains attributes common to all supported file systems. It includes many of the file attributes such as `Files.isDirectory()` and `Files.getLastModifiedTime()`.

```java
Path path = Paths.get("/turtles/sea.txt");

try {
    BasicFileAttributes data = Files.readAttributes(path, BasicFileAttributes.class);
    
    // Works as demonstrated in previous sections
    System.out.println("Is path a directory? " + data.isDirectory());
    System.out.println("Is path a regular file? " + data.isRegularFile());
    System.out.println("Is path a symbolic link? " + data.isSymbolicLink());
    System.out.println("Size (in bytes): " + data.size());
    System.out.println("Last modified date/time: " + data.lastModifiedTime());
    
    // returns true of path is not a file, directory or symbolic link
    System.out.println("Path not a file, directory, nor symbolic link? " + data.isOther());
    
    // returns the FileTime of the created / last accessed values of the file
    System.out.println("Creation date/time: " + data.creationTime());
    System.out.println("Last accessed date/time: " + data.lastAccessTime());
    
    // returns a file system value that represents a unique identifier for the file or null if it is not supported by the file system.
    System.out.println("Unique file identifier (if available): " + data.fileKey());
} catch (IOException e) {
    // Handle file I/O exception...
}
```


#### Modifying Attributes

The `Files.getFileAttributeView(Path, Class<V>)` method returns a view object that we can use to update the file system–dependent attributes. We can also use the view object to read the associated file system attributes by calling `readAttributes()` on the view object.

The `BasicFileAttributeView` class only supports retrieving a `BasicFileAttributes` object by calling `readAttributes()`, as well as updating the three supported timestamps - last modified time, last access time and create time - by using the `setTimes(FileTime lastModifiedTime, FileTime lastAccessTime, FileTime createTime)` method. Passing `null` for one of the `setTimes` parameters results in that value not being modified. 

```java
Path path = Paths.get("/turtles/sea.txt");

try {
    BasicFileAttributeView view = Files.getFileAttributeView(path,BasicFileAttributeView.class);
    BasicFileAttributes data = view.readAttributes();

    FileTime lastModifiedTime = FileTime.fromMillis(data.lastModifiedTime().toMillis() + 10_000);
    view.setTimes(lastModifiedTime, null, null);

} catch (IOException e) {
    // Handle file I/O exception...
}
```

## Presenting the New Stream Methods

A common task in a file system is to iterate over the descendants of a particular file path, either recording information about them or, more commonly, filtering them for a specific set of files.

*Walking* or *traversing a directory* is the process by which you start with a parent directory and iterate over all of its descendants until some condition is met or there are no more elements over which to iterate.

The Java Streams API uses depth-first searching, with a default maxium depth of `Integer.MAX_VALUE`, although the depth can be configured to another value.

### Walking a Directory

The `Files.walk(path)` method returns a `Stream<Path>` object that traverses the directory in a depth-first, lazy manner (the set of elements is built and read while the directory is being traversed). The `walk(path)` method may throw a checked `IOException`, as there could be a problem reading the underlying file system.

```java
Path path = Paths.get("/bigcats");

try {
	Files.walk(path)
		.filter(p -> p.toString().endsWith(".java"))
        .forEach(System.out::println);
} catch (IOException e) {
	// Handle file I/O exception...
}

/**
  prints:
   /bigcats/version1/backup/Lion.java 
   /bigcats/version1/Lion.java 
   /bigcats/version1/Tiger.java 
   /bigcats/Lion.java
 **/
```

There is an overloaded version of `walk(Path, int)` that takes a maximum directory depth integer value as the second parameter. A value of 0 indicates the current path record itself, whilst `Integer.MAX_VALUE` is the default value.

#### Avoiding Circular Paths

The `walk()` method will not traverse symbolic links by default, because unrelated directories may appear in the search, or a *cycle* could occur (an infinite circular dependency). To enable traversing with symbolic links, the `walk()` method supports to the `FOLLOW_LINKS` option as a vararg. When using this option, the `walk()` method tracks the paths it has visited, and will throw a `FileSystemLoopException` if a cycle is detected.

### Searching a Directory

The `Files.find(Path, int, BiPredicate<Path, BasicFileAttributes>)` method behaves in a similar manner as the `Files.walk()` method, except that it requires the depth value to be explicitly set along with a `BiPredicate<Path, BasicFileAttributes>` to filter the data. The `find() method automatically loads the `BasicFileAttributes` object for you, allowing you to write complex lambda expressions that have direct access to this object.

```java
Path path = Paths.get("/bigcats");
long dateFilter = 1420070400000l;

try {
    BiPredicate<Path, BasicFileAttributes> predicate = (p, a) -> p.toString().endsWith(".java") && a.lastModifiedTime().toMillis() > dateFilter;
    
	Files.find(path, 10, predicate).forEach(System.out::println);
} catch (Exception e) {
	// Handle file I/O exception...
}
```

### Listing Directory Contents

The `Files.list(Path)` method returns a `Stream<Path>` object that contains a list all files and directories of the given path. `Files.list()` searches one level deep and is analogous to `java.io.File.listFiles()`, except that it relies on streams.

```java
try {
    // relative path, java will automatically add the working directory
	Path path = Paths.get("ducks"); 
    
    Files.list(path)
        .filter(p -> !Files.isDirectory(p))  // filter out all directories
        .map(p -> p.toAbsolutePath())        // obtain the absolute path
        .forEach(System.out::println);       // print out
} catch (IOException e) {
	// Handle file I/O exception...
}

/**
  prints
    /zoo/ducks/food.txt 
    /zoo/ducks/food-backup.txt 
    /zoo/ducks/weight.txt
 **/
```

### Printing File Contents

The `Files.lines(Path)` method returns a `Stream<String>` object and will read the contents of a file in a lazy fashion, which means that only a small portion of the file is stored in memory at any given time. Functionally, it is the equivalent of `Files.readAllLines()`, apart from the fact that lines are lazily read in memory, thus resulting a lower memory footprint.

```java
Path path = Paths.get("/fish/sharks.log"); 

try {
    Files.lines(path)
        .filter(s -> s.startsWith("WARN"))
        .forEach(System.out::println);
} catch (IOException e) {
	// Handle file I/O exception...
}
```

## Comparing Legacy File and NIO.2 Methods
| Legacy Method | NIO.2 Method |
|---------------|--------------|
| `file.exists()` | `Files.exists(path)` |
| `file.getName()` | `path.getFileName()` |
| `file.getAbsolutePath()` | `path.toAbsolutePath()` |
| `file.isDirectory()` | `Files.isDirectory(path)` |
| `file.isFile()` | `Files.isRegularFile(path)` |
| `file.isHidden()` | `Files.isHidden(path)` |
| `file.length()` | `Files.size(path)` |
| `file.lastModified()` | `Files.getLastModifiedTime(path)` |
| `file.setLastModified(time)` | `Files.setLastModifiedTime(path,fileTime)` |
| `file.delete()` | `Files.delete(path)` |
| `file.renameTo(otherFile)` | `Files.move(path,otherPath)` |
| `file.mkdir()` | `Files.createDirectory(path)` |
| `file.mkdirs()` | `Files.createDirectories(path)` |
| `file.listFiles()` | `Files.list(path)` |
