# Chapter 4 - Functional Programming

## Using Variables in Lamdas

Lambda expressions can access *static variables*, *instance variables*, *effectively final method parameters* and *effectively final local variables*.

If a Lambda expression tries to access a non-effectively final variable (i.e. a variable that has been reassigned), then a compiler error will be thrown. 

## <a name="funcInterfaces"></a> Working with Built-In Functional Interfaces

All of the *functional interfaces* below were introduced in Java 8, and are found within the `java.util.function` package. The convention here is to use the generic type `T` for type parameter. If a second parameter is needed, the next letter `U`, is used. If a distinct return type is needed, `R` for *return* is used for the generic type.

| Functional Interfaces | # Parameters | Return Type | Single Abstract Method |
|-----------------------|--------------|-------------|------------------------|
| `Supplier<T>` | 0 | `T` | `get` |
| `Consumer<T>` | 1 (T) | `void` | `accept` |
| `BiConsumer<T,U>` | 2 (T, U) | `void` | `accept` |
| `Predicate<T>` | 1 (T) | `boolean` | `test` |
| `BiPredicate<T, U>` | 2 (T, U) | `boolean` | `test` |
| `Function<T, R>` | 1 (T) | `R` | `apply` |
| `BiFunction<T, U, R>` | 2 (T, U) | `R` | `apply` |
| `UnaryOperator<T>` | 1 (T) | `T` | `apply` |
| `BinaryOperator<T>` | 2 (T, T) | `T` | `apply` |

### Implementing *Supplier*

A `Supplier` is used when you want to generate or supply values without taking any input. The `Supplier` interface is defined as:

```java
@FunctionalInterface
public interface Supplier<T> {
  public T get();
}
```

An example implementation could be:

```java
Supplier<LocalDate> s1 = LocalDate::now;
Supplier<LocalDate> s2 = () -> LocalDate.now();
 
LocalDate d1 = s1.get();
LocalDate d2 = s2.get();
```

### Implementing *Consumer* and *BiConsumer*

A `Consumer` is used when you want to do something with a parameter but not return anything. `BiConsumer` does the same thing, but it accepts two parameters. The interfaces are defined as:

```java
@FunctionalInterface
public interface Consumer<T> {
  public void accept(T t);
}

@FunctionalInterface
public interface BiConsumer<T, U> {
  public void accept(T t, U u);
}
```

An example `Consumer` implementation could be:

```java
Consumer<String> s1 = System.out::println;
Consumer<String> s2 = x -> System.out.println(x);
 
s1.accept("Annie");  // prints Annie
s2.accept("Bill");   // prints Bill
```

An example `BiConsumer` implementation could be:

```java
Map<String, String> map = new HashMap<>();
Consumer<String, String> b1 = map::put;
Consumer<String, String> b2 = (k, v) -> map.put(k, v);
 
b1.accept("chicken", "cluck");
b2.accept("chick", "tweep");

System.out.println(map); // prints {chicken=cluck, chick=tweep}
```

### Implementing *Predicate* and *BiPredicate*

A `Predicate` is often used when filtering or matching, whilst a `BiPredicate`is just like a `Predicate` except that it takes two parameters instead of one. The interfaces are defined as:

```java
@FunctionalInterface
public interface Predicate<T> {
  public boolean test(T t);
}

@FunctionalInterface
public interface BiPredicate<T, U> {
  public boolean test(T t, U u);
}
```

An example `Predicate` implementation could be:

```java
Predicate<String> p1 = String::isEmpty;
Predicate<String> p2 = x -> x.isEmpty();
 
System.out.println(p1.test(""));  // prints true
System.out.println(p2.test(""));  // prints true
```

An example `BiPredicate` implementation could be:

```java
BiPredicate<String, String> b1 = String::startsWith;
BiPredicate<String, String> b2 = (string, prefix) -> string.startsWith(prefix);
 
System.out.println(b1.test("chicken", "chick"));  // prints true
System.out.println(b2.test("chicken", "chick"));  // prints true
```

### Implementing *Function* and *BiFunction*

A `Function` is responsible for turning one parameter into a value of a potentially different type and returning it. Similarly, a `BiFunction` is responsible for turning two parameters into a value and returning it. The interfaces are defined as:

```java
@FunctionalInterface
public interface Function<T, R> {
  public R apply(T t);
}

@FunctionalInterface
public interface BiFunction<T, U, R> {
  public R apply(T t, U u);
}
```

An example `Function` implementation could be:

```java
Function<String, Integer> f1 = String::length;
Function<String, Integer> f2 = x -> x.length();
 
System.out.println(f1.apply("cluck"));  // prints 5
System.out.println(f2.apply("cluck"));  // prints 5
```


An example `BiFunction` implementation could be:

```java
Function<String, String, String> b1 = String::concat;
Function<String, String, String> b2 = (str1, str2) -> str1.concat(str2);

System.out.println(b1.apply("baby ", "chick"));  // prints baby chick
System.out.println(b2.apply("baby ", "chick"));  // prints baby chick
```

### Implementing *UnaryOperator* and *BinaryOperator*

`UnaryOperator` and `BinaryOperator` are special case of a function. They require all type parameters to be the same type. A `UnaryOperator` transforms into one of the same type, whilst a `BinaryOperator` merges two values into one of the same type. The interfaces are defined as:

```java
@FunctionalInterface
public interface UnaryOperator<T> extends Function<T, T> {
  public T apply(T t);
}

@FunctionalInterface
public interface BinaryOperator<T, T> extendsBi Function<T, T, T> {
  public T apply(T t1, T t2);
}
```

An example `UnaryOperator` implementation could be:

```java
UnaryOperator<String> u1 = String::toUpperCase;
UnaryOperator<String> u2 = x -> x.toUpperCase();
 
System.out.println(u1.apply("cluck"));  // prints CLUCK
System.out.println(u2.apply("cluck"));  // prints CLUCK
```

An example `BinaryOperator` implementation could be:

```java
BinaryOperator<String, String> b1 = String::concat;
BinaryOperator<String, String> b2 = (str1, str2) -> str1.concat(str2);

System.out.println(b1.apply("baby ", "chick"));  // prints baby chick
System.out.println(b2.apply("baby ", "chick"));  // prints baby chick
```
## Returning an *Optional*

An `Optional` is created using a factory, and denotes when a value isn't known, for example:

```java
Optional o1 = Optional.of(95);
Optional o2 = Optional.empty();
```

To obtain the value the `Optional` is wrapping, the `get()` method should be called, for example:

```java
System.out.println(o1.get()));  // prints 95
System.out.println(o2.get()));  // throws java.util.NoSuchElementException: No value present
```

When creating an `Optional` it is common to want to use `empty()` for when a value is `null`. Java provides a convenient factory method to do this:

```java
Optional o1 = Optional.ofNullable(95);     // will hold the value 95
Optional o2 = Optional.ofNullable(null);   // will hold an empty optional
```

The table below summarises most of the instance methods on the `Optional` class:

| Method | When Optional is Empty | When Optional Contains a Value |
|---------------------------|------------------------------------------------|--------------------------------|
| `get()` | Throws an exception | Returns value |
| `ifPresent(Consumer c)` | Does nothing | Calls `Consumer` with value |
| `isPresent()` | Returns `false` | Returns `true` |
| `orElse(T other)` | Returns `other` parameter | Returns value |
| `orElseGet(Supplier s)` | Returns result of calling `Supplier` | Returns value |
| `orElseThrow(Supplier s)` | Throws exception created by calling `Supplier` | Returns value |

## Using Streams

A `stream` in Java is a sequence of data. A `stream pipeline` is the operations that run on a stream to produce a result - contextually this could be thought of as an assembly line in a factory. There are three parts to a `stream pipeline`:

1. *Source:* where the stream comes from
2. *Intermediate operations:* Transforms the stream into another one. there can be as few or as many intermediate operations as you'd like. Since streams use lazy evaluation, the intermediate operations do not run until the terminal operation runs.
3. *Terminal operation:* Actually produces a result. Since streams can be used only once, the stream is no longer valid after a terminal operation completes.

The differences between intermediate and terminal operations are shown in the table below:

| Scenario | For Intermediate Operations? | For Terminal Operations? |
|-----------------------------------------|------------------------------|--------------------------|
| Required part of a useful pipeline? | No | Yes |
| Can exist multiple times in a pipeline? | Yes | No |
| Return type is a `stream` type? | Yes | No |
| Executed upon method call? | No | Yes |
| Stream valid after call? | Yes | No |

### Creating Stream Sources

The `Stream` interface is in the `java.util.stream` package. There are a few ways to create a finite stream:

```java
Stream<String> empty = Stream.empty();            // count = 0
Stream<Integer> singleElement = Stream.of(1);     // count = 1
Stream<Integer> fromArray = Stream.of(1, 2, 3);   // count = 3
```

Java also provides convenient ways to convert from a list of a stream:

```java
List<String> list = Arrays.asList("a", "b", "c");
Stream<String> fromList = list.stream();
Stream<String> fromListParallel = list.parallelStream();
```
*Note: Parallel Streams allow for concurrent processing of intermediate operations (See Chapter 7 for more details).*

Streams, unlike lists, support infinite lengths, for example:

```java
Stream<Double> randoms = Stream.generate(Math::random);
Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
```

### Using Common Terminal Operators

You can perform a terminal operation without any intermediate operations but not the other way around. `Reductions` are a special type of terminal operation where all of the contents of the stream are combined into a single primitive or `Object`. The table below outlines the `Reductions` that may appear on the exam:

| Method | What Happens for Infinite Streams | Return Value | Reduction |
|----------------------------------------------------|-----------------------------------|---------------|-----------|
| `anyMatch()` | Terminates | `boolean` | No |
| `allMatch()` <br/>  `noneMatch()` | Does not terminate | `boolean` | No |
| `collect()` | Does not terminate | Varies | Yes |
| `count()` | Does not terminate | `long` | Yes |
| `findAny()` <br/> `findFirst()` | Terminates | `Optional<T>` | No |
| `forEach()` | Does not terminate | `void` |  No |
| `min()` <br/> `max()` | Does not terminate | `Optional<T>` | Yes |
| `reduce()` | Does not terminate | Varies | Yes |


#### *count()*

The count method determines the number of elements in a finite stream. The method will hang if it is used with a infinite stream as it is trying to count up to infinite. The method is a reduction method, as it looks at element in the stream and returns a single value.

The method signature is:

```java
long count()
```

An example usage is:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
System.out.println(s.count());    // 3
```

#### *min()* and *max()*

The *min()* and *max()* methods allow a customer comparator to be given that will fins the smallest or largest value in a finite stream according to the sort order. Both methods will hang if used on a infinite stream because they cannot be sure that a smaller or larger value isn't coming later in the stream. Both methods are reduction methods, as they return a single value after looking at the entire stream.

The method signatures are:

```java
Optional<T> min(<? super T> comparator);
Optional<T> max(<? super T> comparator);
```

An example where it finds the animal with the fewest letters in its name:

```java
Stream<String> s = Stream.of("monkey", "ape", "bonobo");
Optional<String> min = s.min((s1, s2) -> s1.length() - s2.length());
min.ifPresent(System.out.println);    // ape
```

#### *findAny()* and *findFirst()*

 The *findAny()* and *findFirst()* methods returns an element in the stream unless it is empty. If the stream is empty, then an empty `Optional` is returned. Both methods support infinite streams, since  Java generates only the amount of stream you need, the infinite stream needs to generate only one element. These methods are terminal operations, as they sometimes return without processing the all elements within the stream. 

The method signatures are:

```java
Optional<T> findAny();
Optional<T> findFirst();
```

This example finds an animal:

```java
Stream<String> s = Stream.of("monkey", "ape", "bonobo");
Stream<String> infinite = Stream.generate(() -> "chimp");

s.findAny().ifPresent(system.out::println);          // monkey
infinite.findAny().ifPresent(system.out::println);   // chimp
```

#### *allMatch()*, *anyMatch()* and *noneMatch()*

The *allMatch()*, *anyMatch()* and *noneMatch()* methods search a stream and return information about how the stream pertains to the predicate. These methods may or may not terminate for infinite streams as it depends upon the data. These methods are also not reductions, as they do not have to look at all the data.

The method signatures are:

```java
boolean allMatch(Predicate <? super T> predicate);
boolean anyMatch(Predicate <? super T> predicate);
boolean noneMatch(Predicate <? super T> predicate);
```

This example checks whether an animal names begin with letters:

```java
Stream<String> s = Stream.of("monkey", "123", "bonobo");
Stream<String> infinite = Stream.generate(() -> "chimp");
Predicate<String> pred = x -> Character.isLetter(x.chartAt(0));

System.out.println(s.anyMatch(pred);             // true
System.out.println(s.allMatch(pred);             // false
System.out.println(s.nonMatch(pred);             // false

System.out.println(infinite.anyMatch(pred));     // true
System.out.println(infinite.allMatch(pred));     // hangs
System.out.println(infinite.nonMatch(pred));     // hangs
```

#### *forEach()*

The *forEach()* method does not terminate an infinite stream, and since there is no reduction there is no return type. 

The method signature is:

```java
void forEach(Consumer<? super T> action);
```

This example prints all elements within a stream:

```java
Stream<String> s = Stream.of("Monkey", "Gorilla", "Bonobo");
s.forEach(System.out::print);  // MonkeyGorillaBonobo
```

#### *reduce()*

The *reduce()* method combines a stream into a single object. As you can tell from the name, it is a reduction. The method signatures are: 

```java
T reduce(T identity, BinaryOperator<T> accumulator);
Optional<T> reduce(BinaryOperator<T> accumulator);
<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
```

The most common way of performing a reduction is to take an initial value, and keep merging it with the next value, for example:

```java
Stream<String> s = Stream.of("w", "o", "l", "f");
BinaryOperator<String> op = (str, c) -> str + c;
String word = s.reduce("", op);
System.out.println(word);  // wolf
```

In most cases, the initial value is not required, when omitting it the reduce method returns an optional, for example

```java
Stream<String> s = Stream.of("w", "o", "l", "f");
BinaryOperator<String> op = (str, c) -> str + c;
Optional<String> word = s.reduce(op);
word.ifPresent(System.out::println);  // wolf
```

The third method is used when we are processing parallel streams (although can work on non-parallel streams as well).

```java
Stream<Integer> s = Stream.of(3, 5, 6);
BinaryOperator<Integer> op = (a, b) -> a * b;
Integer result = s.reduce(1, op, op);
System.out.println(result);  // 90
```

#### *collect()*

The *collect()* method is a special type of reduction called *mutable reduction*, and is designed to get data out of streams and into another form, such as `ArrayList` or `String`. The method signatures are as follows:

```java
<R> R collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
<R, A> R collect(Collector<? super T, A, R> collector)
```

Let's start with the first signature, which is used when we want to code specifically how collecting should work.

```java
Stream<String> s = Stream.of("w", "o", "l", "f");
TreeSet<String> set = s.collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
System.out.println(set);  // [f, l, o, w]
```

In this example, the supplier creates an empty `TreeSet`, and the accumulator adds each element to the `TreeSet`. The combiner adds all of the elements of one `TreeSet` to another, in case the options where done in parallel and need to be merged.

### Using Common Intermediate Operations

Unlike a terminal operation, intermediate operations deal with infinite streams simply by returning an infinite stream. Since elements are produced only as needed, this works fine.

#### *filter()*

The *filter()* method returns a `Stream` with elements that match a given expression. The method signature is as follows:

```java
Stream<T> filter(Predicate<? super T> predicate)
```

This example filters all elements that begin with the letter **m**:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.filter(x -> x.startsWith("m")).forEach(System.out::println);   // monkey
```

#### *distinct()*

The *distinct()* method returns a `Stream` with duplicate elements removed. Java calls the `equals()` method of each element to determine whether the objects are the same. The method signature is as follows:

```java
Stream<T> distinct()
```

This example removes all duplicate elements:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "monkey", "monkey", "bonobo");
s.distinct().forEach(System.out::println);   // monkey, gorilla, bonobo
```

#### *limit()* and *skip()*

The *limit()* and *skip()* methods make a stream smaller. The method signatures are as follows:

```java
Stream<T> limit(int maxSize)
Stream<T> skip(int n)
```

This example shows that elements 1-5 are skipped, and then limits the elements after the skip:

```java
Stream<Integer> s = Stream.iterate(1, n -> n + 1);
s.skip(5).limit(2).forEach(System.out::println);   // 6, 7
```

#### *map()*

The *map()* method creates a one-to-one mapping from the elements in the stream to the elements of the next step in the stream. In other words, it is transforming data. The method signature is as follows:

```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper)
```

This example converts a list of String objects into a list of Integers representing their lengths:

```java
Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
s.map(String::length).forEach(System.out::println);   // 6, 7, 6
```

#### *flatMap()*

The *flatMap()* method takes each element in the stream and makes any elements it contains top-level elements in a single stream. This method is useful for when you need to flatten multiple lists into a single list. The method signature is as follows:

```java
<R> Stream<R> flatMap(Function<? super T,? extends Stream<? extends R>> mapper)
```
This example converts a Stream of Lists of String objects into a Stream of String objects:

```java
List<String> zero = Arrays.asList();
List<String> one = Arrays.asList("Bonobo");
List<String> two = Arrays.asList("Mama Gorilla", "Baby Gorilla");
Stream<List<String>> animals = Stream.of(zero, one, two);

animals.flatMap(l -> l.stream()).forEach(System.out::println);   // Bonobo, Mama Gorilla, Baby Gorilla
```

*Note: this method will remove any empy lists*.

#### *sorted()*

The *sorted()* method returns a stream with the elements sorted. Java will use natural sorting, unless a comparator is supplied. The method signature is as follows:

```java
Stream<T> sorted()
Stream<T> sorted(Comparator<? super T> comparator)
```

These examples show sorting with the natural comparator or a custom implementation:

```java
Stream<String> s1 = Stream.of("brown", "red", "blue");
s1.sorted().forEach(System.out::println);   // blue, brown, red

Stream<String> s2 = Stream.of("brown", "red", "blue");
s2.sorted(Comparator.reverseOrder()).forEach(System.out::println);   // red, brown, blue
```

#### *peek()*

The *peek()* method allow us to perform a stream operation without actually changing the stream. The method signature is as follows:

```java
Stream<T> peek(Consumer<? super T> action)
```

The most common use for `peek()` is to output the contents of the stream as it goes by, for example:

```java
Stream<String> s = Stream.of("brown", "red", "blue");
long count = s.filter(c -> c.startsWith("r")).peek(System.out::println).count();  // red
System.out.println(count);  // 1
```

### Working with Primitives

Here are the three types of primitives streams:

* `IntStream`: used for primitive types, `int`, `short`, `byte`, and `char`.
* `LongStream`: used for primitive types `long`.
* `DoubleStream`: used for primitive types, `double`, and `float`.

The methods for creating an empty stream or from the factory method `of()` exist upon the primitive streams, but two additional methods have been added for creating infinite streams:

```java
DoubleStream random = DoubleStream.generate(Math::random);
random.limit(3).forEach(System.out::println); // prints 3 random doubles

DoubleStream fractions = DoubleStream.iterate(.5, d -> d / 2);
fractions.limit(3).forEach(System.out::println); // 0.5, 0.25, 0.125
```

It is also common to generate ranges of numbers, to which Java has provided the `range` and `rangeClosed` methods, as shown below:

```java
IntStream range = IntStream.range(1, 6);
range.forEach(System.out::println);   // 1, 2, 3, 4, 5

IntStream rangeClosed = IntStream.rangeClosed(1, 5);
rangeClosed.forEach(System.out::println); // 1, 2, 3, 4, 5
```

#### Using *Optional* with Primitive Streams

Primitives cannot be used with the `Optional` class,as it only supports Objects. In order to get around this problem, Java invented the Optional Primitive class, as shown below:

```java
IntStream stream = IntStream.rangeClosed(1, 10);
OptionalDouble optional = stream.average();

System.out.println(optional.getAsDouble());
System.out.println(optional.orElseGet(() -> Double.NaN));
```

The table below outlines the optional types for primitives, and their methods:

|  | OptionalDouble | OptionalInt | OptionalLong |
|------------------------------|------------------|------------------|------------------|
| Getting as a primitive | `getAsDouble()` | `getAsInt()` | `getAsLong()` |
| `orElseGet()` parameter type | `DoubleSupplier` | `IntSupplier` | `LongSupplier` |
| return type of `max()` | `OptionalDouble` | `OptionalInt` | `OptionalLong` |
| return type of `sum()` | `double` | `int` | `Long` |
| return type of `average()` | `OptionalDouble` | `OptionalDouble` | `OptionalDouble` |

#### Summarizing Statistics

As methods such as `max()` and `min()` are terminating methods, and a stream cannot be reused once terminated, Java introducted the `SummaryStatistics` for each primitive stream, e.g. `IntSummaryStatistics`, `DoubleSummaryStatistics`, `FloatSummaryStatistics`. Calling the `summaryStatistics()` method will close the stream. 

```java
IntStream stream = IntStream.rangeClosed(1, 50);
IntSummaryStatistics statistics = stream.summaryStatistics();

System.out.println(statistics.getMax());    // prints 50
System.out.println(statistics.getMin());    // prints 1
System.out.println(statistics.getSum());    // print 1275
System.out.println(statistics.getCount());  // prints 50
```

### Functional Interfaces for Primitives

### *boolean*

The `BooleanSupplier` is a seperate type, as it only requires one method to implement, as shown below:

```java
boolean getAsBoolean()
```

It can be used in the following ways:

```java
BooleanSupplier b1 = () -> true;

System.out.printlnr(b1.getAsBoolean);  // prints true
```

### *double*, *int* and *long*

Most of the functional interfaces are for double, int and long to match the streams and optionals that have been useing for primitives. The table below outlines the methods for each of the functional interfaces for each type. This table is similar to the [Built-In Functional Interfaces](#funcInterfaces), but returns primitives rather than objects.

| Functional Interfaces                                        | # Parameters                                             | Return Type                     | Single Abstract Method                               |
| ------------------------------------------------------------ | -------------------------------------------------------- | ------------------------------- | ---------------------------------------------------- |
| `DoubleSupplier`<br />`IntSuppler`<br />`LongSupplier`       | 0                                                        | `double`<br />`int`<br />`long` | `getAsdouble`<br />`getAsInt`<br />`getAsLong`       |
| `DoubleConsumer`<br />`IntConsumer`<br />`LongConsumer`      | 1 (`double`)<br />1 (`int`)<br />1 (`long`)                    | `void`                          | `accept`                                             |
| `DoublePredicate`<br />`IntPredicate`<br />`LongPredicate`   | 1 (`double`)<br />1 (`int`)<br />1 (`long`)                    | `boolean`                       | `test`                                               |
| `DoubleFunction<R>`<br />`IntFunction<R>`<br />`LongFunction<R>` | 1 (`double`)<br />1 (`int`)<br />1 (`long`)                    | `R`                             | `apply`                                              |
| `DoubleUnaryOperator`<br />`IntUnaryOperator`<br />`LongUnaryOperator` | 1 (`double`)<br />1 (`int`)<br />1 (`long`)                    | `double`<br />`int`<br />`long` | `applyAsdouble`<br />`applyAsInt`<br />`applyAsLong` |
| `DoubleBinaryOperator`<br />`IntBinaryOperator`<br />`LongBinaryOperator` | 2 (`double`, `double`)<br />2 (`int`, `int`)<br />2 (`long`, `long`) | `double`<br />`int`<br />`long` | `applyAsdouble`<br />`applyAsInt`<br />`applyAsLong` |

Note: `BiConsumer`, `BiPredicate`, `BiFunction` are not included in the above table, as it is uncommon to perform an operation using two differnent predicates.

| Functional Interfaces                                        | # Parameters                                                 | Return Type                                                  | Single Abstract Method                                       |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `ToDoubleFunction<T>`<br />`ToIntFunction<T>`<br />`ToLongFunction`<T> | 1 (`T`)                                                      | `double`<br />`int`<br />`long`                              | `applyAsDouble`<br />`applyAsInt`<br />`applyAsLong`         |
| `ToDoubleBiFunction<T, U>`<br />`ToIntBiFunction<T, U>`<br />`ToLongBiFunction<T, U>` | 1 (`T`, `U`)                                                 | `double`<br />`int`<br />`long`                              | `applyAsDouble`<br />`applyAsInt`<br />`applyAsLong`         |
| `DoubleToIntFunction`<br />`DoubleToLongFunction`<br />`IntToDoubleFunction`<br />`IntToLongFunction`<br />`LongToDoubleFunction`<br />`LongToIntFunction` | 1 (`double`)<br />1 (`double`)<br />1 (`int`)<br />1 (`int`)<br />1 (`long`)<br />1 (`long`) | `int`<br />`long`<br />`double`<br />`long`<br />`double`<br />`int` | `applyAsInt`<br />`applyAsLong`<br />`applyAsDouble`<br />`applyAsLong`<br />`applyAsDouble`<br />`applyAsInt` |
| `ObjDoubleConsumer<T>`<br />`ObjIntConsumer<T>`<br />`ObjLongConsumer<T>` | 2 (`T`, `double`)<br/ >2 (`T`, `int`)<br/ >2 (`T`, `long`)<br/ > | `void`                                                       | `accept`                                                     |

### Working with Advanced Stream Pipeline Concepts

#### Linking Streams to the Underlying Data

Remember, streams are lazily evaludated, meaning they are not used util a terminal operator is performed. The example below illustrates this:

```java
List<String> cats = new ArrayList<>();
cats.add("Annie");
cats.add("Ripley");

Stream<String> stream = cats.stream();
cats.add("KC");

System.out.println(stream.count());  // prints 3
```

#### Chaining Optionals

Optionals can be chained just like streams, with the advantage being that these are much shorter and more expressive than with traditional `if` statements. The example below uses the `flatMap` method to flatten an `Optional<Optional<Integer>>` into an `Optional<Integer>`.

```java
// Static method can be found within the App class
public static Optional<Integer> calculator(String str) {
    return Optional.of(str)
        .filter(s -> s.length() == 3)
        .map(String::length);
}

Optional<Integer> r1 = Optional.of(123)
    .map(String::valueOf)
    .flatMap(App::calculator);

Optional<Integer> r2 = Optional.of(1)
    .map(String::valueOf)
    .flatMap(App::calculator);

r1.ifPresent(System.out::println);  // prints 3
r2.ifPresent(System.out::println);  // prints nothing (no result)
```

#### Collecting Results

| Collector                                                    | Description                                                  | Return Value when Passed to `collect`                        |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `averagingDouble(ToDoubleFunction f)`<br />`averagingInt(ToIntFunction f)`<br />`averagingLong(ToLongFunction f)` | Calculates the average for our three core primitive types    | `Double`                                                     |
| `counting()`                                                 | Counts the number of elements                                | `Long`                                                       |
| `groupingBy(Function f)`<br />`groupingBy(Function f, Collector dc)`<br />`groupingBy(Function f, Supplier s, Collector dc)` | Creates a map grouping by the specified function with the optional type and optional downstream collector | `Map<K, List<T>>`                                            |
| `joining()`<br />`joining(CharSequence cs)`                  | Creates a single `String` using `cs` as a dlimiter between elements if one is specified | `String`                                                     |
| `maxBy(Comparator c)`<br />`minBy(Comparator c)`             | Finds the largest/smallest elements                          | `Optional<T>`                                                |
| `mapping(Function f, Collector dc)`                          | Adds another level of collectors                             | `Collector`                                                  |
| `partitioningBy(Predicate p)`<br />`partitioningBy(Predicate p, Collector dc)` | Creates a map grouping by the specified predicate with the optional further downstream collector | `Map<Boolean, List<T>`                                       |
| `summarizingDouble(ToDoubleFunction f)`<br />`summarizingInt(ToIntFunction f)`<br />`summarizingLong(ToLongFunction f)` | Calculate average, min, max and so on (i.e. it creates the a `SummaryStatistics`) | `DoubleSummaryStatistics`<br />`IntSummaryStatistics`<br />`LongSummaryStatistics` |
| `summingDouble(ToDoubleFunction f)`<br />`summingInt(ToIntFunction f)`<br />`summingLong(ToLongFunction f)` | Calculates the sum for our three core primitive types        | `Double`<br />`Integer`<br />`Long`                          |
| `toList()`<br />`toSet()`                                    | Creates an arbitrary type of list or set                     | `List`<br />`Set`                                            |
| `toCollection(Supplier s)`                                   | Creates a `Collection` of the specified type                 | `Collection`                                                 |
| `toMap(Function k, Function v)`<br />`toMap(Function k, Function v, BinaryOperator m)`<br />`toMap(Function k, Function v, BinaryOperator m, Supplier s)` | Creates a map user functions to map the keys, values an optional merge function, and an optional type | `Map`                                                        |

##### Collecting using Basic Collectors

Many of the above collectors work the same, below are a few examples.

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
String result = animals.collect(Collectors.joining(", "));
System.out.println(result);  // prints lions, tigers, bears
```

The following example prints the average length of the animal names:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Double result = animals.collect(Collectors.averagingInt(String::length));
System.out.println(result);  // prints 5.333333333333333
```

##### Collecting into Maps

Collector code involving maps can be complex, and can get long quickly.

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<String, Integer> result = animals.collect(Collectors.toMap(k -> k, String::length));
System.out.println(result);  // prints {lions=5, bears=5, tigers=6}
```

Now lets map the length of the animal name to the animal. This will requre a mere function, as two animal names may have the same length.

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Integer, String> result = animals.collect(
    Collectors.toMap(String::length, k -> k, (s1, s2) -> s1 + ", " + s2)
);
System.out.println(result);             // prints {5=lions, bears, 6=tigers}
System.out.println(result.getClass());  // prints java.util.HashMap
```

By default `HashMap` are returned from the `Collectors.toMap()` function. To use an alternative Map, such as a `TreeMap`, we would need to add a constructor reference map:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Integer, String> result = animals.collect(
    Collectors.toMap(String::length, k -> k, (s1, s2) -> s1 + ", " + s2, TreeMap::new)
);
System.out.println(result);             // prints {5=lions, bears, 6=tigers}
System.out.println(result.getClass());  // prints java.util.TreeMap
```

##### Collecting Using Grouping, Partitioning, and Mapping

Suppose we want to get a group of names by their length, we can achieve this using the `groupingBy` method, as shown below:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Integer, List<String>> result = animals.collect(
    Collectors.groupingBy(String::length)
);
System.out.println(result);  // prints {5=[lions, bears], 6=[tigers]}
```

The `groupingBy` collector tell collect that it should group all of the elemnts of the stream into lists, organising them by the function provided. Suppose we require a `Set` rather than a `List`, this isn't a problem as we would need to pass a *downtream collector*, as shown below:

```java
Stream<String> animals = Stream.of("lions", "lions", "tigers", "bears");
Map<Integer, Set<String>> result = animals.collect(
    Collectors.groupingBy(String::length, Collectors.toSet())
);
System.out.println(result);  // prints {5=[lions, bears], 6=[tigers]}
```

We can also output to a `TreeMap`, for example:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
TreeMap<Integer, List<String>> result = animals.collect(
    Collectors.groupingBy(String::length, TreeMap::new, Collectors.toList())
);
System.out.println(result);  // prints {5=[lions, bears], 6=[tigers]}
```

*Partioning* is a special type of grouping, to which there are only two possible groups - true and false - and is like splitting a list into two parts. Suppose we are making a sign to put outside each animal's exhibit. We have two sizes of signs, one can accommodate names with five or fewer characters, and the other for longer names. We can partition the list according to which sign is needed:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Boolean, List<String>> result = animals.collect(
    Collectors.partitioningBy((name) -> name.length() <= 5)
);
System.out.println(result);  // prints {true=[lions, bears], false=[tigers]}
```

The result will always contain a `true` and `false` key regards of whether or not the value has been populated, (e.g. incase of an empty resultset). As with `groupingBy()`, the value type can be changed, for example:

```java
Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Boolean, Set<String>> result = animals.collect(
    Collectors.partitioningBy((name) -> name.length() <= 7, Collectors.toSet())
);
System.out.println(result);  // prints {true=[lions, bears, tigers], false=[]}
```

In this example the code is grouping all elements by their length (for the key), and selects the first character of each of the values and orders them naturally, selecting the minimum character (or in this case the one closest to the letter a).

```java
// Can't put this inline due to a Java bug
// See: https://coderanch.com/t/661352/certification/min-doesn-compile-Sybex
Comparator<Character> comparator = Comparator.naturalOrder();

Stream<String> animals = Stream.of("lions", "tigers", "bears");
Map<Integer, Optional<Character>> result = animals.collect(
    Collectors.groupingBy(
        String::length,
        Collectors.mapping(s -> s.charAt(0), Collectors.minBy(comparator))
    )
);
System.out.println(result);  // prints {5=Optional[b], 6=Optional[t]}
```

