# Chapter 1 - Advanced Class Design

## Using *instanceof*
- `a instanceof B` returns `true` if the reference to which `a` points to an instance of class B, a subclass of B or a class that implements the B interface.
- `a instanceof Object` will always be `true`, as all classes extend `Object` by default.
- `hippo instanceof Lion` will throw a compiler error if the compiler knows that there is no possible way for a Hippo variable reference to be a Lion (since the Hippo class does not extend the Lion class directly or indirectly).
`instanceof` checks with an interface (e.g. `hippo instanceof Interface`will not throw compiler errors, as it is possible to implement an interface at runtime.

## Virtual Method Invocation
- Java looks for an overridden method or a concrete subclass method and will call that over the one that the compiler says we have.
- This technique will only work on methods, class variables belong to the class, and cannot be overridden.

```java
abstract class Animal {
	abstract void feed();
}

class Bird extends Animal {
	public void feed() { addSeed(); }
	private void addSeed() { }
}

class Lion extends Animal {
	public void feed() { addMeat(); }
	private void addMeat() { }
}

Animal animal = new Lion();
animal.feed();
```

## Annotating Overridden Methods
- An annotation is a type of metadata that can be used by the compiler or even at runtime.
- The `@Override` annotation is used to express that you (the programmer) intent for this method to override one in a superclass or implement one from an interface.
- A compiler error will be thrown if you are not correctly overriding the superclass method (e.g. method signatures are different). You cannot apply the annotation to a field, as fields cannot be overridden.
- The exam can trick you by applying the `@Override` annotation when overridding the`equals`, `hashCode` and `toString`methods - this is legal as long as the method signatures are the same as those defined in the `Object` class.

## Coding `equals`, `hashCode` &amp; `toString`

### `toString`

```java
@Override
public String toString() {
	return "Name: " + name;
}
```

### `equals`
- The `equals` method provides a number of rules in the contract for this method:
	1. **It is reflective:** for any non-null reference, `x`, then `x.equals(x)` should return `true`.
	2. **It is symmetric:** for any non-null reference values `x` and `y` then `x.equals(y)` should equal `y.equals(x)`.
	3. **It is transitive:**  for any non-null reference values `x`, `y`, `z`if `x.equals(y)`returns `true` and `y.equals(z)` returns `true`, then `x.equals(z)` should return `true`. 
	4. **It is consistent:** for any non-null reference values `x` and `y` then multiple invocations of `x.equals(y)` should consistently equal `y.equals(x)` provided no modifications on either object is made.
	5. For any non-null reference value `x`, then `x.equals(null)` should return `false`.

An example (pure java) implementation:

```java
@Override
public boolean equals(Object obj) {
    if (!(obj instanceof Lion)) return false;
    Lion otherLion = (Lion) obj;
    return this.id == otherLion.id;
}
```

- ***Note:*** it is possible to declare the other Object as a subclass of `Object` (e.g. `Lion`), however it does not override the method, it overloads the method, which is probably not what was intended

### `hashCode`
- The `hashCode` method provides a number of rules in the contract for this method:
	1. Within the same program, the result of the `hashCode()` must not change. This means that you shouldn't include variables that change in figuring out the hash code, e.g. age of a person.
	2. if `equals()` returns `true` when called with two objects, calling `hashCode()` on each of those objects must return the same result. This means that `hashCode()` can use a subset of the variables used within `equals()` but no more.
	3. If `equals()` returns `false` with two objects, calling `hashCode()` on each of those objects does not have to return a different result. This means that the `hashCode()` results do not need to be unique when called on unequal objects.

An example (pure java) implementation:

```java
@Override
public int hashCode() {
	return this.id + 7 * this.name.hashCode();
}
```

## `Enums`

- Enums are like a set of constants, they can be printed with `toString()` and compared with `==` (as they are like a static final constant).
- `Enum.values()` returns a list of possible Enum values, while `Enum.ordinal()` returns the corresponding int value *__Note:__ You can't compare an Enum to it's ordinal value - it won't compile!*
- Use `Enum.valueOf(str)` to create an Enum from a string value - this will raise an IllegalArgumentException if the value is incorrect (it's case sensitive)
- An `enum` cannot be extended.

```java
enum Season {
	WINTER, SPRING, SUMMER, FALL
}
		
Season s1 = Season.WINTER;
Season s2 = Season.valueOf("SUMMER");
```

- When using an `enum` in switch statements, you should only give the value not the `enum` name, e.g:

```java
Season season = Season.WINTER;
switch (season) {
	case WINTER:             // COMPILES
	case 0:                  // DOES NOT COMPILE
	case Season.WINTER:      // DOES NOT COMPILE
	...
}
```

### Constructors, Fields &amp; Methods
- An example `enum` with custom fields &amp; methods:

```java
enum Season {
    // NOTE: semicolon is only required when defining values.
    WINTER("Low"), SPRING("Medium"), SUMMER("High"), FALL("Medium");
    
    // Usual field declarations are allowed
    private String visitorCount;
    
    // Constructors have to be private (public will throw an error)
    // Constructors are only called once throughout the life of the program.
    private Season(String visitorCount) {
        this.visitorCount = visitorCount;
    }
			
    public void printVisitorCount() {
        System.out.println(visitorCount);
    }
}

// Usage
Season.SPRING.printVisitorCount();
```

- An `enum` value can manage it's own methods be extended, for example:

```java
enum Season {
	WINTER {
    	public void printHours() { System.out.println("short hours"); }
    }, 
    SPRING {
    	public void printHours() { System.out.println("default hours"); }
    },
    SUMMER {
    	public void printHours() { System.out.println("long hours"); }
    }, 
    FALL {
    	public void printHours() { System.out.println("default hours"); }
    };

    // Define an abstract method here, to force implementation by all values.
    // Failure to provide an abstract (or concrete) method will cause a compliation error.
    public abstract void printHours();
}
```

- The above example can be more concisely written as:

```java
enum Season {
    WINTER {
    	public void printHours() { System.out.println("short hours"); }
    }, 
    SUMMER {
    	public void printHours() { System.out.println("long hours"); }
    }, 
    SPRING, FALL;

    // Failure to provide a concrete method will cause a compliation error.
	public void printHours() { System.out.println("default hours"); }
}

// Usage
Season.WINTER.printHours();    // prints "short hours"
Season.SPRING.printHours();    // prints "default hours"
```

## Nested Classes

### Member Inner Class
- A *member inner class*  is defined at the same level as methods, instance variables and constructors (referred to as members), and have the following properties:
  - **Can** be declared public, protected, private or default access
  - **Can** extend and class or interface
  - **Can** be abstract or final
  - **Can** access member of the outer class, including private members
  - **Cannot** declare static fields or methods (except for static final fields)

- Example member inner class implementation:

```java
public class Outer {
	private String greeting = "Hello World!";
	
	protected class Inner {
   		private int repeat = 3;
   
   		public void go() {
   			for (int i = 0; i < repeat; i++)
   				System.out.println(greeting);
   		}
   	}
   
   	public void callInner() {
   		Inner inner = new Inner();
   		System.out.println("Repeating greeting " + inner.repeat + " times.");
   		inner.go();
   	}
   
   	public static void main(String[] args) {
   		Outer outer = new Outer();
   		outer.callInner();
   
   		// Alternative way of initializing an inner class
   		Inner inner = outer.new Inner();
   		inner.go();
   	}
}
```

- Inner classes can have the same variable names as outer classes:
```java
public class A {
    private int x = 10;

    class B {
        private int x = 20;

        class C {
            private int x = 30;

            public void allTheX() {
                System.out.println(x);         // 30
                System.out.println(this.x);    // 30
                System.out.println(B.this.x);  // 20
                System.out.println(A.this.x);  // 10
            }
        }
    }

    public static void main(String[] args) {
        nested
        A a = new A();
        A.B b = a.new B();
        A.B.C c = b.new C();
        c.allTheX();

        // Alternative way of calling
        // Note Java can detect the first nested class, but not the second or beyond
        A a = new A();
        B b = a.new B();
        B.C c = b.new C();
        c.allTheX();
    }
}
```


### Local Inner Class
- A *local inner class*  is a nested class defined within a method. The inner class declaration does not exist until the method is executed, and goes out of scope once the method returns. Local inner classes have the following properties:
	- They **have** access to all fields and methods of the encoding class
	- They **do not** have an access modifier
	- They **cannot** be declared static and cannot declare static fields or methods.
	- They **do not** have access to local variables of a method are *final* or *effectively final*.

- Example local inner class implementation:
```java
public class LocalInner {
    private int length = 5;

    public void calculate() {
        final int width = 20;
        class Inner {
            public void multiply() {
                System.out.println(length * width);
            }
        }
        Inner inner = new Inner();
        inner.multiply();
    }

    public static void main(String[] args) {
        LocalInner localInner = new LocalInner();
        localInner.calculate();
    }
}
```

#### *final* vs *effectively final*

- A variable or parameter whose value is never changed after it is initialized is effectively final, for example:

```java
public void isItFinal() {
    int one = 20;    // Effectively final
    int two = one;
    two++;           // Not final (as value has changed)
    int three;       // Effectively final (as it's only assigned once as part of the below if condition)
    if (one == 3) three = 3;
    else three = 4;
    int four = 4;    // Not final (as value has changed)
    class Inner { }
    four = 5;			
}
```

### Anonymous Inner Class
- An *anonymous inner class* is a local inner class that does not have a name. They are declared and instantiated all in one statement using the `new` keyword
- Anonymous inner classes are required to extend an exciting class or implement an interface
- Anonymous inner classes follow the same rules that local inner classes follow

- Example anonymous inner class implementation:
```java
public class AnonInner {
    abstract class SaleTodayOnly {
        abstract int dollarsOff();
    }

    public int admission(int basePrice) {
        SaleTodayOnly sale = new SaleTodayOnly() {
            int dollarsOff() { return 3; }
        };  // semicolon is required as we are declaring a local variable
        return basePrice - sale.dollarsOff();
    }
}
```

### Static Nested Class

- A *static nested class* is a static class defined at the member level
- It can be instantiated without an object of the enclosing class, so it can't access the instance variables without an explicit object of the enclosing class
- It can be public, protected, private or default access and can refer to the fields and method of the static nested class

- Example anonymous inner class implementation:
```java
public class Enclosing {
    static class Nested {
        private int price = 6;
    }

    public static void main(String[] args) {
        Nested nested = new Nested();
        System.out.println(nested.price);
    }
}
```
