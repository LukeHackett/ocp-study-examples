# Chapter 3 - Generics and Collections

## Reviewing OCA Collections

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
Note: the `remove` method is overloaded and will remove an index position  - `remove(int index)` -  or an item in the collection - `remove(Object item)`. Java did not *autobox* the `remove(1)` call, as it matched it to a valid signature before auto boxing, so the compiler assumed you wanted to call that method and not `remove(new Integer(1))`.

## Working with Generics

### Generic Classes

Generics can been introduced by declaring a *formal type parameter* in angle brackets. For example, the following class named `Crate` has a generic type variable declared after the name of the class:

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

Generic methods can be used on `static` and `non-static` methods, and are often used on static methods since they aren't part of an instance that can declare the type. Before the return type, we declare the formal type of generic parameters.

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

In this example unbounded or upper bounded generics are not a solution, as neither allow for mutability (they are both immutable). Thus a new bound, the lower bound is required.

With a lower bound, we are telling Java that the list will be a list of String objects or some list of objects that are a superclass to String. Ether way it is safe to add a String to that list.

The corresponding method declaration for `addSound` could look like the following:

```java
public static void addSound(List<? super String? list) {
  list.add("quack");
}
```

## Using Lists, Sets, Maps & Queues

The *Java Collections Framework* is a set of classes in `java.util` for storing collections. There are four main interfaces in the Java Collections Framework:

* `List:` A *list* is an ordered collection of elements that allows duplicate entries. Elements in a list can be access by an index (`ArrayList`, `LinkedList`, `Vector`, `Stack`).
* `Set:` A *set* is a collection that does not allow duplicates (`HashSet`, `TreeSet`).
* `Queue:` A *queue* is a collection that orders it elements in a specific order for processing, e.g. first-in, first-out (`ArrayDeque`, as well as `LinkedList`). 
* `Map:` A *map* is a collection that maps keys to values, with no duplicate keys allowed. (`HashMap`, `TreeMap`, `Hashtable`)


### Common Collections Methods

#### add

The `add()` method inserts a new element into the Collection and returns whether it was successful. The method signature is:

```
boolean add(E element);
```

For a *list* the method always returns `true`, whilst when adding to a `set`, the method will return `false` if the item is already in the `set`.

#### remove

The `remove()` method removes a single matching value in the Collection and returns whether it was successful. The method signature is:

```
boolean add(Object object);
```

The method will return `true` when it removed an element that was found within the Collection, whilst returning false if the element was not found within the Collection. *Note:* `remove()` *will remove the first matching element, if there are duplicates within the* `List` *only the first element will be removed.*

When calling `remove()` with an `int` it will remove the element at the index location. When the index location does not exist an `IndexOutOfBoundsException` this thrown.

#### isEmpty and size

The `isEmpty()` and `size()` methods look at how many elements are in the Collection. The method signatures are:

```
boolean isEmpty();
int size();
```

#### clear

The `clear()` method provides an easy way to discard all elements of the Collection. The method signature is:

```
void clear();
```

#### contains

The `contains()` method check if a certain value is in the Collection. This method calls `equals()` on each element of the `ArrayList` to see if there are any matches. The method signature is:

```
boolean contains(Object object);
```

### Using the *List* Interface

All implementations of the `List` interface are ordered and allow duplicates, they also all implement the same methods.

* An `ArrayList` is like a resizable array. Looking up elements takes `O(1)` - constant time, but modifying the `ArrayList` is slower than accessing an element. This makes an `ArrayList` a good choice when you are reading more often then writing.
* A `LinkedList` implements both `List` and `Queue`, and has additional methods to facilitate adding or removing from the beginning and/or end of the list. `LinkedList` allows for constant time insertions or removals using iterators, but only sequential access of elements. `LinkedLists` are a good choice when you'll be using it as a `Queue`.
* A `Stack` is a data structure where you add and remove element from the top of the stack (e.g. like a stack of paper). `Stack` is a legacy structure, to which `ArrayDeque` is the replacement.

In addition to the inherited `Collection` methods, the method signatures of the `List` interface are as follows:

| Method | Description |
|--------------------------------|---------------------------------------------------------------------------------------------|
| void add(int index, E element) | Adds `element` at `index`, and moves the rest towards the end |
| E get(int index) | Returns element at `index` |
| int indexOf(Object o) | Returns first matching `index` or `-1` if not found |
| int last indexOf(Object o) | Returns last matching `index` or `-1` if not found |
| E remove(int index) | Removes element at `index`, then moves the rest toward the front, and returns the `element` |
| E set(int index, E e) | Replaces `element` at index, returning the original |


### Using the *Set* Interface

All implementations of the `Set` interface are not ordered and do not allow duplicates.

* A `HashSet` stores its elements in a hash table, using the `hashCode()` method to determine it's bucket location. Adding and accessing elements in the set both have constant time, but the order in which elements are inserted is lost.
* A `TreeSet` stores its elements in a sorted tree structure, which means all elements are sorted. However adding and checking if an element is present are both `O(log n)` - logarithmic time. Elements are accessed in their natural sorted order, e.g. numbers smallest to largest. Elements cannot be `null`.

The `Set` interface does not add any extra methods, and uses the default `Collection` methods.


### Using the *Queue* Interface

All implementations of the `Queue` interface support adding and removing elements in a specific order. Unless stated, a queue is assumed to be a FIFO (first-in, first-out). A `Queue` will allow duplicate elements.

* A `LinkedList` implements both the `List` and `Queue` interfaces, and thus is a List and a double-ended queue. A double-ended queue supports adding elements and removing elements from both the front and back of the queue. A `LinkedList` is not as efficient as a "pure" queue.
* An `ArrayDeque` is a "pure" double-ended queue, storing elements in a resizable array. The `ArrayDeque` is more efficient than a `LinkedList`. Elements cannot be inserted as `null`.

In addition to the inherited `Collection` methods, the method signatures of the `ArrayDeque` class are as follows:

| Method | Description | For queue | For Stack |
|--------------------------|----------------------------------------------------------------------------------------|-----------|-----------|
| boolean add(E element) | Adds an `element` to the back of the queue and returns `true` or throws an `Exception` | Yes | No |
| E element() | Returns next `element` or throws an `Exception` | Yes | No |
| boolean offer(E element) | Adds an `element` to the back of the queue and returns whether successful | Yes | No |
| E remove() | Removes and returns next `element` or throws `Exception` if empty queue | Yes | No |
| void push(E e) | Adds an `element` to the front of the queue | No | Yes |
| E poll() | Removes and returns next element or returns `null` if queue is empty | Yes | No |
| E peek() | Returns next `element` or returns `null` if queue is empty | Yes | Yes |
| E pop() | Removes and returns next `element` or throws an `Exception` if queue is empty | No | Yes |

The difference between an `ArrayDeque` is being used as a stack or a queue is really important. A queue is like a line of people, you join at the back, and leave at the front, while a stack is like a stack of plates, you put plates on top of each other and take them off from the top.


### Using the *Map* Interface

All implementations of the `Map` interface support identifying values by a key, such as a contact list.

* A `HashMap` store the keys in a has table, which uses the `hashCode()` method of the keys to retrieve their values for efficiently. Adding and retrieving elements both have constant time, but the order in which the insertion occurred is lost - *note: `LinkedHashMap` maintains insertion order*.
* A `TreeMap` stores the keys in a sorted tree structure, ensuring that the key are always in sorted order, but adding and checking if a key is present are both logarithmic time. Elements are accessed in their natural sorted order, e.g. numbers smallest to largest. Keys cannot be `null`.
* A `Hashtable` is the old, thread-safe implementation and will not be expected to use it, as it was replaced with the `HashMap`. Keys or values cannot be `null`.

A `Map` does not extend `Collection`, and therefore has it's own methods as shown below:

| Method | Description |
|-----------------------------------|--------------------------------------------------------------------|
| void clear() | Removes all keys and values from the map |
| boolean isEmpty() | Returns whether the map is empty |
| int size() | Returns the number of entries in the map |
| V get(Object key) | Returns the value mapped by `key` or null if none is mapped |
| V put(K key, V value) | Adds or replaces key/value pair. Returns previous value or `null`. |
| V remove(Object key) | Removes and returns value mapped to key. Returns `null` if none. |
| boolean containsKey(Object key) | Returns whether `key` is in a map. |
| boolean containsValue(Object key) | Returns whether `value` is in a map. |
| Set<K> keySet() | Returns set of all keys |
| Collection<V> values() | Returns `Collection` of all values |


## Comparator vs Comparable

By default numbers sort before letters and uppercase letters sort before lowercase letters. Sorting objects that you create can be completed using the Comparable interface.

### Comparable

The `Comparable` interface has only one method, as shown below:

```java
public interface Comparable<T> {
  public int compareTo(T o);
}
```
There are three rules to know when implementing the `compareTo` method:

* `0` is returned when the current object is equal to the `compareTo()` method argument.
* A number less than `0` is returned when the current object is smaller than the `compareTo()` method argument.
* A number greater than `0` is returned when the current object is larger than the `compareTo()` method argument.


### Comparator

The `Comparator` interface allows for objects to be compared differently to how it may have been implemented within the `compareTo` method. The `Comparator` interface has only one method, as shown below:

```java
public interface Comparator<T> {
  public int compare(T a, T b);
}
```

The `Collections` interface provides a `sort` method that can be used to sort lists. For example:

```java
List<Duck> ducks = new ArrayList<>();
// ... add elements to the list

// Sorts the list by the Comparable interface
Collections.sort(ducks); 


// Create a custom comparator
Comparator<Duck> byWeight = new Comparator<Duck>() {
  public int compare(Duck d1, Duck d2) {
    return d1.getWeight() - d2.getWeight();
  }
};

// Sorts the list by the Comparator interface
Collections.sort(ducks, byWeight);
```

When using the `sort` method without a comparator, e.g. `Collections.sort(ducks)`, list of objects must implement the `Comparable` interface. A compilation error will be raised if the objects do not implement the `Comparable` interface.

Be wary of these interfaces, as they can easily be mixed up. `Comparable` requires a `compareTo` method to be defined that takes a single argument, whilst `Comparator` requires a `compare` method to be defined that take two arguments.


### Searching & Sorting

`Collections.sort` and `Collections.binarySearch` support passing a `Comparator` when you do not want to use the natural sort order.


## Java 8 List Additions

### Using Method References

*Method references* are a way to make the code shorter by reducing some of the code that can be inferred by simply mentioning the name of the method.

Static method example:

```java
String str = "abc";
Consumer<List<Integer>> methodRef1 = Collections::sort;
Consumer<List<Integer>> lambda1 = list -> Collections.sort(list);
```

Instance methods examples:

```java
String str = "abc";
Predicate<String> methodRef2 = str::startsWith;
Predicate<String> lambda2 = s -> str.startsWith(s);
```

```java
Predicate<String> methodRef3 = String::isEmpty;
Predicate<String lambda3 = s -> s.isEmpty();
```

Constructor reference examples:

```java
Supplier<ArrayList> methodRef4 = ArrayList::new;
Supplier<ArrayList> lambda4 = () -> new ArrayList();
```

### Removing Conditionally

Removes all elements from a `Collection` if the  given `Predicate` evaluates to `true`. The method signature looks like this:

```java
boolean removeIf(Predicate<? super E> filter>);
```
Example:


```java
List<String> names = Arrays.asList("Ben", "David");
names.removeIf(name -> name.startsWith("D");
System.out.println(names);   // [Ben]
```

### Updating All Elements

The `replaceAll` method allows a lambda expression be applied to each element within a `List`. The method signature looks like this:

```java
void replaceAll(UnaryOperator<E> o);
```
Example:

```java
List<Integer> list = Arrays.asList(1, 2, 3);
list.replaceAll(x -> x * 2);
System.out.println(list);   // [2, 4, 6]
```

### Looping through a Collection

The `forEach` method is similar to a for-loop, and can be used in a number of ways, including:

```java
List<String> names = Arrays.asList("Ben", "David");

names.forEach(name -> System.out.println(name));

names.forEach(System.out::println);
```

## Java 8 Map Additions

### Put if Absent

The `putIfAbsent` method will set a value within a map if the existing value is not present or null, for example:

```java
Map<String, String> favourites = new HashMap<>();
favourites.put("Jenny", "Bus Tour");
favourites.put("Tom", null);

favourites.putIfAbsent("Jenny", "Tram");
favourites.putIfAbsent("Sam", "Tram");
favourites.putIfAbsent("Tom", "Tram");

System.out.println(favourites);  // {Tom=Tram, Sam=Tram, Jenny=Bus Tour}
```

### merge

The `merge()` method allows adding logic to the problem of what to choose. For example, suppose that our guests are indecisive and can't pick a favourite. They agree that the ride that has the longest name must be the most fun. We can write code to express this by passing a mapping function to the `merge()` method:

```java
BiFunction<String, String, String> mapper = (v1, v2) -> v1.length() > v2.length() ? v1 : v2;

Map<String, String> favourites = new HashMap<>();
favourites.put("Jenny", "Bus Tour");
favourites.put("Tom", "Tram");
favourites.putIfAbsent("Sam", null);

favourites.merge("Jenny", "Skyride", mapper);
favourites.merge("Tom", "Skyride", mapper);

System.out.println(favourites);  // {Tom=Skyride, Jenny=Bus Tour, Sam=null}
```

Note: it is worth pointing out that the mapper function is only called when a value exists. In the above example "Sam" does not have a non-null value, and thus the mapper function skips that record.
