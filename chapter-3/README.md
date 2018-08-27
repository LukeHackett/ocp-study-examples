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
