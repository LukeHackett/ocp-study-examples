# Chapter 3 - Generics and Collections

## Reviewing  OCA Collections

### Array and ArrayList

`arrays` and `Lists` differ in Java, as shown below.
```java
List<String> list = new ArrayList<>();
list.add("fluffy");
list.add("webby");

String array = new String[list.size()];
array[0] = "fluffy";
array[1] = "webby";

System.out.println(list.size());     // prints 2
System.out.println(array.length);    // Also prints 2
```

Now, lets review the link created when converting between an `array` and `ArrayList`.
```java
String[] array = new String { "gerbil", "mouse" };     // [gerbil, mouse]
List<String> list = Arrays.asList(array);              // returns a fixed size list
list.set(1, "test");                                   // [gerbil, test]
array[0] = "new";                                      // [new, test]
String array2 = (String[]) list.toArray();             // [new, test]
list.remove(1);                                        // throws UnsupportedOperationException
```
When using `Arrays.asList` it is worth noting that the List uses the given array, and hence if the array changes, the list will change as well (and vice versa). It is also for this reason that elements can not be added or removed as an array is a fixed size (although replacement is fine).

### Searching and Sorting

* Sorting an Array or ArrayList will sort the collection inline (i.e. it does not return a new list)
* The `binarySearch` function assumes that the collection is sorted, and will return the index of item to be found.
* If the item cannot be found, then the number one less than the negated index of where the requested value would need to be inserted is returned.

An example using `Arrays`:
```java
int[] numbers = {6, 9, 1, 8};
Arrays.sort(numbers);                                   // [1, 6, 8, 9]
System.out.println(Arrays.binarySearch(numbers, 6));    // prints 1
System.out.println(Arrays.binarySearch(numbers, 3));    // prints -2
```
The last line prints `-2`, because `3` should be inserted at position `1`, negated is `-1` and subtract `1` would equal `-2`.

Another example, using `List`:
```java
List<Integer> list = Arrays.asList(9, 7, 5, 3);
Collections.sort(list);                                   // [3, 5, 7, 9]
System.out.println(Collections.binarySearch(list, 3));    // prints 0
System.out.println(Collections.binarySearch(list, 2));    // prints -1
```

### Wrapper Clasess and Autoboxing

* Each primitive has a corresponding wrapper class, such as `int` => `Integer`.
* *Autoboxing* automatically converts a primitive to the corresponding wrapper classes, while *unboxing* automatically converts a wrapper class back to a primitive.

Let's try a tricky example:
```java
List<Integer> numbers = new ArrayList<Integer>();
numbers.add(1);                    // [1]
numbers.add(new Integer(3));       // [1, 3]
numbers.add(new Integer(5));       // [1, 3, 5]
numbers.remove(1);                 // [1, 5]
numbers.remove(new Integer(5));    // [1]
System.out.println(numbers);       // [1]
```
Note: the `remove` method is oberloaded and will remove an index postition  - `remove(int index)` -  or an item in the collection - `remove(Object item)`. Java did not *autobox* the `remove(1)` call, as it matched it to a valid signature before autoboxing, so the compiler assumed you wanted to call that method and not `remove(new Integer(1))`.

## Working with Generics

### Generic Classes

Generics can ben introduced by declaring a *formal type parameter* in angle brackets. For example, the following class named `Crate` has a generic ype variable declared after the name of the class:

```java
public class Crate<T> {
  private T contents;
  
  public T emptyCrate() {
    return contents;
  }
}
```

### Generic Interfaces

Just like a class, an interface can declare a *formal type parameter* in angle brackets. For example:

```java
public interface Shippable<T> {
  void ship(T t);
}
```
There are three ways in whihc a class an implement such an interface. It could specifiy the generic type in the class:

```java
public ShippableRobotCrate implements Shippable<Robot> {
  public void ship(Robot t) {  }
}
```
The next way is to create a generic class:

```java
public class ShippableAbstractCrate<U> implments Shippable<U> {
  public void ship(U t) {  }
}
```
The final way is to not use generics at all (also know as the "old way" of writing code). This approach will generate compiler warnings about `Shippable` being a *raw* type, but it does compile:

```java
public class ShippableCrate interface Shippable {
  public void ship(Object t) {  }
}
```

### Generic Methods

Generic methods can be used on `static` and `non-static` methods, and are often used on static methods since they aren't part of an instance that can declare the type.

```java
public static <T> void sink() {  }
public static <T> T identity(T t) { return t; }
public static <T> Crate<T> ship(T t) { return new Crate<T>(); }
public static T nogood(T t) { return t; }    // DOES NOT COMPILE
```

### Bounds

A *bounded parameter type* is a generic type that specifies a bound for the generic - i.e. it restricts the type of bounds a class can have. 

A *wildcard generic type* is an unknown generic type represented with a question mark `?`.

Generic wildcards can be used in three ways, as shown in the table below.

| Type of Bound                | Syntax           | Example                                                            |
|------------------------------|------------------|--------------------------------------------------------------------|
| Unbounded wildcard           | `?`              | `List<?> l = new ArrayList<String>();`                             |
| Wildcard with an upper bound | `? extends type` | `List<? extends Exception> l = new ArrayList<RuntimeException>();` |
| Wildcard with a lower bound  | `? super type`   | `List<? super Exception> l = new ArrayList<Object>();`             |

#### Unbounded Wildcard

An unbounded wildcard represents any data type. You use `?` when you want to specify that any type is OK with you.

For example, suppose that we wanted to write a method that prints each item within a list:

```java
public static void printList(List<Object> list) {
  for (Object o : list) System.out.println(o);
}

public static void main(String[] args) {
  List<String> keywords = Arrays.asList("java", "html");
  printList(keywords);    // DOES NOT COMPILE
}
```

This will not compile due to type erasure, as a `List<String>` cannot be applied to a `List<Object>`, even though `String` extends `Object`. To solve this problem, we can modify the print method, to accept a List of Unbounded Wildcards, for example:

```java
public static void printList(List<?> list) {
  for (Object o : list) System.out.println(o);
}

public static void main(String[] args) {
  List<String> keywords = Arrays.asList("java", "html");
  printList(keywords);    // DOES NOT COMPILE
}
```

`printList()` now takes any type of list as a parameter, and thus can be used with `List` of any objects.

#### Upper-Bounded Wildcards

Let's try to write a method that adds up the total of a list of numbers. We've established that a generic tyope can't just use a subclass:

```java
List<Number> list = new ArrayList<Integer>();    // DOES NOT COMPILE
```

Instead, we need to use a wildcard:

```java
List<? extends Number> list = new ArrayList<Integer>();
```

The upper-bounded wildcard says that any class that extends `Number` or `Number` itself can be used as the formal parameter type, e.g.

```java
public static long total(List<? extends Number> list) {
  long count = 0;
  for (Number number : list) {
    count += number.longValue();
  }
  return count;
}
```

Lists that use upper bounds or unbounded wildcards are immutable, meaning that the object cannot be modified. For example:

```java
static class Sparrow extends Bird { }
static class Bird { }

public static void main(String[] args) {
  List<? extends Bird> birds = new ArrayList<Bird>();
  birds.add(new Sparrow());    // DOES NOT COMPILE
  birds.add(new Bird());       // DOES NOT COMPILE
}
```
The reason why new elements cannot be added is because the compiler does not know what type ` List<? extends Bird>` really is. It could be `List<Bird>` or `List<Sparrow>` or some other generic type that hasn't been written yet.

Interfaces work slightly differently, given the following interface definitions:

```java
interface Flyer { void fly(); }
class HangGlider implements Flyer { public void fly() { } }
class Goose implements Flyer { public void fly() { } }
```
We also have two methods that use it. One uses the interface, and the other uses an upper bound:

```java
private void anyFly(List<Flyer> flyer) { }
private void groupOfFlyers(List<? extends Flyer> flyer) { }
```

Note: although interfaces are being used, the `extends` keyword is still used. This is because Upper bounds are like anonymous classes in that they use extends regardless of whether we are working with a class or an interface.

#### Lower-Bounded Wildcards

Trying to add a String to a list (that may have two different types) can be difficult in java, for example:

```java
List<String strings = new ArrayList<>();
strings.add"tweet");

List<Object> objects - new ArrayList<>(strings);

addSound(strings);
addSound(objects);
```

In this example unbounded or upper bounded generics are not a solution, as neither allow for mutability (they are both immutable). Thus a new bound, the lower bound is requried.

With a lower bound, we are telling Java that the list will be a list of String objects or some list of objects that are a superclass to String. Ether way it is safe to add a String to that list.

The corresponding method declaration for `addSound` could look like the following:

```java
public static void addSound(List<? super String? list) {
  list.add("quack");
}
```
