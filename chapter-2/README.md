# Chapter 2 - Design Patterns & Principles

## Designing an interface
- All methods must be public, and if the visibility is not declared then the compiler will automatically declare it as public (including default methods).
- All non-static methods are abstract, if not declared the compiler will automatically declare it as abstract.
- An implementing class must implement each interface method fully (i.e. it must declare them as public for example).
- Interfaces can extend other interfaces, while classes implement interfaces (do not extend them)
- Interfaces cannot be declared a `final` nor can be instantiated directly.

## Functional Programming
- A `functional interface` is an interface that contains a **single abstract method**.
- A `functional interface` is annotated with `@FunctionalInterface`, but is not required to be annotated for it to be a `functional interface`.
- If **more than one abstract method** is declared within an interface that is annotated with `@FunctionalInterface`, then a compiler error will be thrown. 
- By default all interfaces with **only a single abstract** method are treated as a `functional interface` (any number of `default` methods maybe included, so long as they have a method body. 
- The `@FunctionalInterface` annotation will force a compiler error when a violation of this rule occurs.

```java
@FunctionalInterface 
public interface Sprint {
	public void sprint(Animal animal);
}
		
public class Tiger implements Sprint {
	public void sprint(Animal animal) { 
		System.out.println("Animal is sprinting fast! " + animal.toString());
	}
}
```

### Implementing Function Interfaces with Lambdas

```java
public interface CheckEvenNumber {
    public boolean test(int number);
}
	
public class EvenNumberChecker {
    public static void print(int number, CheckEvenNumber check) {
        if (check.test(number))
            System.out.println("Found an even number :: " + number);
    }
    
    public static void main(String[] args) {
        print(2, a -> a % 2 == 0);
        print(13, a -> a % 2 == 0);
    }
}
```

#### Lambda Syntax Rules
- A lambda must have zero or more parameters, an arrow, and a body.

- The parameter must be enclosed with braces if the lambda is a no-args method, has more than 1 parameter or if there are parameter types present.

- The method body may not have braces, unless it is has more than one line of code.

- When the method body is encapsulated with braces, then a terminating `;` must be present, as well as a `return` statement if required (`return` is not required when a single line, or is a void method). ​	

   ```java
   a -> a.canHop()
   (Animal a) -> a.canHop()
   
   a -> { return a.canHop();}
   (Animal a) -> { return a.canHop(); }
   
   () -> new Duck()
   () -> { return new Duck(); }
   
   (Animal a, Duck d) -> d.quack()
   (Animal a, Duck d) -> { d.quack(); }
   ```

- In addition to the above rules, re-declaring a local variable within a lambda is not permitted, but creating a new variable is allowed, for example:

    ```java
    (a, b) -> { int a = 0; return 5; }    // Does not compile
    (a, b) -> { int c = 0; return 5; }    // Does compile
    ```

#### Predicate Interface
- Java provides a common functional interface `Predicate<T>` that can be used to perform simple tests:

   ```java
   package java.util.function;
   
   public interface Predicate<T> {
   	public boolean test(T t);
   }
   ```

- Hence the earlier example, could be re-written using the Predicate interface:

	```java
  public class EvenNumberChecker {
      public static void print(int number, Predicate<Boolean> check) {
          if (check.test(number))
              System.out.println("Found an even number :: " + number);
      }
   	
      public static void main(String[] args) {
          print(2, a -> a % 2 == 0);
          print(13, a -> a % 2 == 0);
      }
  }
	```

## Polymorphism
- *Polymorphism* is the ability of a single interface to support multiple underlying forms.

	```java
	public interface LivesInOcean { public void speak(); }

	public class Dolphin implements LivesInOcean { 
	    public int age = 10;
	    public void speak() { System.out.println("whistle"); }
	}
		
	public class Whale implements LivesInOcean { 
	    public void speak() { System.out.println("sing"); }
	}
		
	public class Oceanographer {
	    public void checkSound(LivesInOcean animal) {
	        animal.speak();
	    }
			
	    public static void main(String args[]) {
	        Oceanographer o = new Oceanographer();
	        o.checkSound(new Dolphin());    // Prints whistle
	        o.checkSound(new Whale());      // Prints sing
	    }
	}
	```

- Another Example:

	```java
	Dolphin d = new Dolphin();
	System.out.println(d.age);            // prints 10

	LivesInOcean animal = d;
	System.out.println(animal.speak());   // prints whistle
		
	System.out.println(animal.age);       // DOES NOT COMPILE (requires a cast to Dolphin)
	```

- In this example only one object is created (count the number of `new` calls)
- The ability of the `Dolphin` object to be passed as an instance of an interface it implements, `LivesInOcean` (as well as a class it might extend) is the nature of polymorphism

### Distingushing between an Object and a Reference

- All java objects extend `java.lang.Object`, and therefore can be assigned to `java.lang.Object`, as shown below:

  ```java
  Dolphin dolphin = new Dolphin();
  Object obj = dolphin;
  ```

- Even though the object has been assigned a reference with a different type, the underlying object itself has not changed, and still exists as a `Dolphin` object.
- To access `Dolphin` properties or methods using the `obj` variable, would require a cast to `Dolphin`.

- This can be summarised with the following rules:
	1. The type of the object determines which properties exist within the object in memory
	2. The type of the reference to the object determines which methods and variables are accessible to the Java program.


### Casting Object References

- There are four basic rules to keep in mind when casting variables:
  1. Casting an object from a subclass to a superclass doesn't require an explicit class
      ```java
      Dolphin dolphin = new Dolphin();
      LivesInOcean dolphin1 = (LivesInOcean) dolphin;  // Explicit cast not required
      ```

  2. Casting an object from a superclass to a subclass requires an explicit class
      ```java
      LivesInOcean dolphin = new Dolphin();
      Dolphin dolphin1 = (Dolphin) dolphin;  // Explicit cast not required
      ```

  3. The compiler will not allow casts to unrelated types
      ```java
      LivesInOcean dolphin = new Dolphin();
      LivesOnLand obj = dolphin;   // Does Not Compile
      ```

  4. Even when the code compiles without issue, an exception may be thrown at runtime if the object being cast is not actually an instance of that class.
        ```java
        LivesInOcean whale = new Whale();

        // Compiles, but throws a ClassCastException at runtime
        // since the object is a Whale and not a Dolphin.
        Dolphin dolphin = whale; 
        ```
- The use of the `instanceof` operator can prevent `ClassCastExceptions`, for example:
  ```java
  if (whale instance of Dolphin) {
      Dolphin dolphin = whale;
  }			
  ```

## Understanding Design Principles

### Encapsulation

- Encapsulation is the idea of combining fields and methods in a class such that the methods operate on the data, as opposed to the users of class access the fields directly.
- With encapsulation, a class is able to maintain certain in-variants about its internal data.
- An invariant is a property or truth that is maintained even after the data is modified, e.g. a person's age must be greater than or equal to 0.

### Creating JavaBeans

- A JavaBean is a design principle for encapsulating data in an object in Java, and has a number of naming conventions as shown below:

	- Properties are `private`
	- Getters for non-`boolean` properties begin with `get`
	- Getters for `boolean` properties may begin with `is` or `get`.
	- Setter methods begin with `set`
	- The method must have a prefix of `get`/`set`/`is` followed by the first letter of the property in uppercase and followed by the rest of the property name.

***Note:** The wrapper class `Boolean` is an object, and therefore uses `get` rather than `is`*

### Applying the Is-a Relationship

- If `A` is-a `B`, then any instance of `A` can be treated like an instance of `B`.
- This holds true for a child that is a subclass of any parent (to any depth).

### Applying the Has-a Relationship

- We refer to the *has-a relationship* as the property of an object having a named data object or primitive as a member.
- This test is also known as the object composition test.
	```java
	class Bird {
	    public Beak beak;
	    public Foot rightFoot;
	    public Foot leftFoot;	
	}
		
	class Beak {
	    public String colour;
	    public double Length;
	}
	```
- In this example, `Bird` and `Beak` fail the is-a test, as they are not related, but they do pass the has-a test, as `Bird` has-a `Beak`.
- Any child of Bird must also have a `Beak`.
- More generally if a parent has-a object as `protected` or `public` member, then any child of the parent must also have that object as a member.
- This does not hold true for `private` members defined in the parent classes, because `private` members are not inherited in Java.

### Composing Objects

- We refer to *object composition* as the property of constructing a class using references to other classes in order to reuse the functionality of the other classes, for example:

```java
class Bird {
    public Beak beak = new Beak();
    
    public void peck() {
        this.beak.peck();
    }
}
		
class Beak {
    public String colour;
    public double Length;
			
    public void peck() {
        System.out.println("Pecking!");
    }
}
```

- In this example the `beak` class can be reused in classes completely unrelated to a `Bird` class.


## Working with Design Patterns

- A *design pattern* is an established general solution to a commonly occurring software development problem.

### Singleton Design Patterns

- The *singleton pattern* is a creational pattern focused on creating only one instance of an object in memory within an application, shareable by all classes and threads within the application.
- Common characteristics of a singleton are:
	- The instance can be accessed through a single `public static` method, often named `getInstance()`, with will return a reference to the singleton object.
	- All constructors are marked as `private` which ensures no other class can instantiate another version of the singleton.
	- By making the constructors `private`, we have implicitly marked the class as being `final` - it cannot be extended.
	- Methods are often `syncronized` to prevent two processes from running the same method at the exact same time.
	
#### Applying Lazy Instantiation to Singletons

- Instantiation of the singleton can be done directly in the definition of the instance reference, or it can be done through a `static` initialisation block when the class is loaded.
- Alternatively, we can delay creation of the instance until the first time the `getInstance()` method is called:

	```java
	public class VistorTicketTracker {
	    private static VisitorTicketTracker;
	    
	    private VistorTicketTracker() { }
			
	    public static VistorTicketTracker getInstance() {
	        if (instance == null) {
	            instance = new VisitorTicketTracker();  // Not Thread Safe!
	        }
				
	        return instance;
	    }
			
	    // Data Access Methods
	}
	```

- This creates a reusable object the first time it is requested, and is known as *lazy instantiation*.
- Lazy instantiation reduces memory usage and improves performance when an application starts up

#### Creating Unique Singletons

- When creating a singleton using *lazy instantiation*, an additional step must be performed to ensure that only *one* instance is ever created.
- The *lazy instantiation* implementation is not considered thread-safe as two threads could call `getInstance()` at the same time, resulting in two objects being created.
- *Thread safety* is the property of an object that guarantees safe execution by multiple threads at the same time, a simple solution to the thread safety issue would be:

    ```java
    public class VistorTicketTracker {
        private static VisitorTicketTracker;

        private VistorTicketTracker() { }

        public static synchronized VistorTicketTracker getInstance() {
            if (instance == null) {
                instance = new VisitorTicketTracker();  // Not Thread Safe!
            }

            return instance;
        }

        // Data Access Methods
    }
    ```

### Creating Immutable Objects

- An immutable object, is a read-only object, that is an object that cannot be modified after it has been created (e.g. String).
- An immutable object will have the following properties:
	- Use a constructor to set all properties of the object.
	- Mark all the instance variable `private` and `final`.
	- Does not define any setter methods.
	- Does not allow referenced mutable objects to be modified or accessed directly
	- Prevents methods from being overridden, i.e. the class will be marked `final`

An example Immutable object implementation:

```java
public final class Animal {
    private List<string favouriteFoods;
		
    public Animal(List<String> favouriteFoods) {
        if (favouriteFoods == null) {
            throw new RuntimeException("Favourite Foods is required");
        }
			
        // Take a new copy of the list, as the caller will have access to the 
        // original list, thus preventing altering list from outside this class.
        this.favouriteFoods = new ArrayList<String>(favouriteFoods);
    }
		
    // This method makes the class mutable, as modifiying the list - such as
    // .remove(), .clear() - will alter the underlying list.
    public List<String> getFavouriteFoods_Mutable() {
        return this.favouriteFoods;
    }
		
    // This method always returns a clone of the list, thus ensuring that the
    // underlying list held within this class is never altered.
    public List<String> getFavouriteFoods_Immutable() {
        return new ArrayList<String>(this.favouriteFoods);
    }
}
```

- Modifying an immutable object is not possible, but it is possible to create a new object, with the updated values.

### Using the Builder Pattern

***Note**: The Builder Pattern is not on the exam, but is used through the exam.*

- The *builder pattern* is a creational pattern in which parameters are passed to a builder object, and an object is generated with a final `build` call.
- It is often used with immutable objects, since immutable objects do not gave setter methods and must be created with all of their parameter set.
- A builder may thrown an exception if required fields are not set, but may also choose to set default values.


### Creating Objects with the Factory Pattern

***Note:** The Factory Pattern is not on the exam, but is used through the exam.*

- The *factory pattern* is based on the idea of using a factory class to produce instance of object based on a set of input parameters.
- It is similar to the builder pattern, although it is focused on supporting class polymorphism

