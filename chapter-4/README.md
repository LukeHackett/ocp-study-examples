# Chapter 4 - Functional Programming

## Using Variables in Lamdas

Lambda expressions can access *static variables*, *instance variables*, *effectively final method parameters* and *effectively final local variables*.

If a Lambda expression tries to access a non-effectively final variable (i.e. a variable that has been reassigned), then a compiler error will be thrown. 

## Working with Built-In Functional Interfaces

All of the *functional interfaces* below were introduced in Java 8, and are found within the `java.util.function` package. The convention here is to use the generic type `T` for type parameter. If a second parameter is needed, the next letter `U`, is used. If a distinct return type is needed, `R` for *return* is used for the generic type.

| Functional Interfaces | # Parameters | Return Type | Single Abstract Method |
|-----------------------|--------------|-------------|------------------------|
| `Supplier<T>` | 0 | `T` | `get` |
| `Consumer<T>` | 1 (T) | `void` | accept |
| `BiConsumer<T,U>` | 2 (T, U) | `void` | accept |
| `Predicate<T>` | 1 (T) | `boolean` | test |
| `BiPredicate<T, U>` | 2 (T, U) | `boolean` | test |
| `Function<T, R>` | 1 (T) | `R` | apply |
| `BiFunction<T, U, R>` | 2 (T, U) | `R` | apply |
| `Unaryoperator<T>` | 1 (T) | `T` | apply |
| `BinaryOperator<T>` | 2 (T, T) | `T` | apply |

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














