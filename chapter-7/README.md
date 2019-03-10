# Concurrency

* A *task* is a single unit of work performed by a thread. 
* A *thread* is the smallest unit of execution that can be scheduled by the operating system. 
* A *process* is a group of associated threads that execute in the same, shared environment.
* A *shared environment*, allows all threads within the same process to share the same memory space and can communicate directly with one another (such as using static variables).

### Distinguishing Thread Types

A *system thread* is created by the JVM and runs in the background of the application, for example garbage-collection. For the most part, system threads are invisible to the applications developer. When these threads encounter a problem and cannot recover, such as running out of memory, then an `java.lang.Error` is thrown rather than an `java.lang.Exception`

A *user-defined thread* is one created by the application developer to accomplish a specific task. 

### Understanding Thread Concurrency

Operating systems use a *thread scheduler* to determine which threads should be currently executing. 

When a thread’s allotted time is complete but the thread has not finished processing, a context switch occurs. A *context switch* is the process of storing a thread’s current state and later restoring the state of the thread to continue execution.

A thread can interrupt or supersede another thread if it has a higher thread priority than the other thread. A *thread priority* is a numeric value associated with a thread that is taken into consideration by the thread scheduler when determining which threads should currently be executing.

In Java, thread priorities are specified as integer values, with the default thread priority taking a value of `Thread.NORM_PRIORITY`.If two threads have the same priority, the thread scheduler will arbitrarily choose the one to process first in most situations.

|    Constant Variable   | Value |
|:----------------------:|:-----:|
|  `Thread.MIN_PRIORITY` |   1   |
| `Thread.NORM_PRIORITY` |   5   |
|  `Thread.MAX_PRIORITY` |   10  |

### Introducing *Runnable*

The `java.lang.Runnable` is a functional interface that takes no arguments and returns no data. It is commonly used to define the work a thread will execute, separate from the main application thread.

```java
// Valid Runnable implementations
Runnable run1 = () -> System.out.println("Hello World");
Runnable run2 = () -> { int i = 10; i++ };
Runnable run3 = () -> { return ; };
Runnable run4 = () -> {};

// Invalid Runnable implementations
Runnable run5 = () -> "";
Runnable run6 = () -> 5;
Runnable run7 = () -> { return new Object(); };
Runnable run8 = () -> { throw new IOException(); }; // Does not compile
```

### Creating a Thread

The simplest way to execute a thread is to use the `java.lang.Thread` class. The `Thread` class requires it to be first defined, and then started — remember Java does not provide any guarantees about the order in which a thread will be processed once it is started.

Defining the task that a `Thread` instance will execute can be done in two ways in Java:

* Provide a `Runnable` object or lambda expression to the `Thread` constructor
* Create a class that extends `Thread` and overrides the `run()` method

Example using the `Runnable` interface:

```java
public class PrintData implements Runnable { 
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println("Printing record: " + i);
		}
	}

	public static void main(String[] args) {
		(new Thread(new PrintData())).start();
	} 
}
```

Example extending the `Thread` class:

```java
public class ReadInventoryThread extends Thread { 
	public void run() {
		System.out.println("Printing zoo inventory");
	}

	public static void main(String[] args) {
		(new ReadInventoryThread()).start();
	}
}
```

### Polling with Sleep

*Polling* is the process of intermittently checking data at some fix interval. 

It is possible to implement a wait function, to wait for a period of time before checking a value, however a more safe approach would be to use the `Thread.sleep()` method which will instruct the current thread to sleep for a given number of milliseconds.

Note: the  `Thread.sleep()` method throws a checked exception `InterruptedException` and must be declared or handled.

## Creating Threads with the *ExecutorService*

The `ExecutorService` is an interface that creates and manages threads and is part of the Concurrency API. After obtaining an instance of the `ExecutorService`, you then send the tasks to the service to be processed.

### Single-Thread Executor

The Concurrency API includes the `Executors` factory class that can be used to create instances of the `ExecutorService` object.

```java
import java.util.concurrent.*;

public class ZooInfo {
	public static void main(String[] args) {
		ExecutorService service = null; 
		try {
			service = Executors.newSingleThreadExecutor();
			System.out.println("begin");

			service.execute(() -> System.out.println("Printing zoo inventory")); 
			service.execute(() -> { 
                for(int i=0; i<3; i++) {
                    System.out.println("Printing record: " + i); 
                }
            });
			service.execute(() -> System.out.println("Printing zoo inventory"));
			
			System.out.println("end"); 
		
		} finally {
			if(service != null) {
				service.shutdown();
			} 
		}
	} 
}
```

In this example, we used the newSingleThreadExecutor() method, which is the simplest ExecutorService that we could create. Unlike our earlier example, in which we had three extra threads for newly created tasks, this example uses only one, which means that the threads will order their results. For example, the following is a possible output for this code snippet: 

```
begin
Printing zoo inventory 
Printing record: 0 
Printing record: 1
end
Printing record: 2 
Printing zoo inventory
```

With a single-thread executor, results are guaranteed to be executed in the order in which they are added to the executor service. The main application execution is an independent thread from the `ExecutorService`, and it can perform tasks while the other thread is running, this is why the end message is printed before all the executions have completed.

### Shutting Down a Thread Executor

A thread executor creates a non-deamon thread on the first task that is executed, so failing to shutdown the executor will cause the applcation to never terminate. To shutdown an executor, the `shutdown()` method should be called.

The shutdown process for a thread executor involves first rejecting any new tasks submitted to the thread executor while continuing to execute any previously submitted tasks. During this time, calling `isShutdown()` will return `true`, while `isTerminated()` will return `false`.  If a new task is submitted to the thread executor while it is shutting down, a RejectedExecutionException will be thrown.

The `shutdown()` method does not stop any tasks that have already been submitted to the thread executor. To achieve this, then `shutdownNow()` method should be used, which will attempt to force shutdown all active threads, returning a list `List<Runnable>` of tasks that were submitted to the thread executor but that were never started.

### Submitting Tasks

You can submit tasks to an ExecutorService instance multiple ways, to which are outlined in the table.

The `invokeAll()` and `invokeAny()` methods take a `Collection` of `Callable` objects and are executed synchronously, meaning the methods will wait until the results are available before returning the control to the enclosing program. 

| Method Name | Description |
|-------------|-------------|
| `void execute(Runnable command)` | Executes a `Runnable` task at some point in the future |
| `Future<?> submit(Runnable task)` | Executes a `Runnable` task at some point in the future and returns a `Future` representing the task. |
| `<T> Future<T> submit(Callable<T> task)` | Executes a `Callable` task at some point in the future and returns a `Future` representing the pending results of the task. |
| `<T> List<Future<T>> invokeAll( Collection<? extends Callable<T>> tasks) throws InterruptedException` | Executes the given tasks, synchronously returning the results of all tasks as a `Collection` of `Future` objects, in the same order they were in the original collection. |
| `<T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException` | Executes the given tasks, synchronously returning the result of one of finished tasks, cancelling any unfinished tasks. |

### Waiting for Results

The `submit()` method returns a `java.util.concurrent.Future<V>` object that can be used to determine the result for example:

```java
Future<?> future = service.submit(() -> System.out.println("Hello World"));
```

The available methods upon the `Future<V>` class are listed in the table below:

| Method Name             | Description |
| ----------------------- | ----------- |
| `boolean isDone()`         | Returns `true` if the task was completed, threw an <br />exception, or was cancelled. |
| `boolean isCancelled() ` | Returns `true` if the task was cancelled before it<br /> completely normally. |
| `boolean cancel() ` | Attempts to cancel execution of the task. |
| `V get()`              | Retrieves the result of a task, waiting endlessly if it <br />is not yet available. |
| `V get(long timeout, TimeUnit unit)`              | Retrieves the result of a task, waiting the specified <br />amount of time. If the result is not ready by the time<br /> the timeout is reached, a checked<br /> `TimeoutException` will be thrown. |

### *Introducing Callable*

The `Callable` interface is similar to the `Runnable` interface, except that it's `call()` method returns a value and can throw a checked exception. Just like the `Runnable` interface, the  `Callable` interface is a *Functional Interface*. The definition of the  `Callable` interface is:

```java
@FunctionalInterface 
public interface Callable<V> { 
    V call() throws Exception;
}
```

An example usage of the `Callable` interface:

```java
ExecutorService service = Executors.newSingleThreadExecutor(); 
Future<Integer> result = service.submit(() -> 30 + 11); 
System.out.println(result.get());   // prints 41
```

### Scheduling Tasks

Often we need to schedule a task to happen at some point in the future, so we may to schedule it to run multiple times. We can use the `ScheduledExecutorService`, which is a sub-interface of the `ExecutorService` that can be used for just a task.

Creating a new `ScheduledExecutorService` can be achieved using the factory method, as shown below:

```java
ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
```

The following table outline the various methods that are available to the `ScheduledExecutorService`.

| Method Name                                                                            | Description                                                                                                                                                                        |
|----------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `schedule(Callable callable, long delay, TimeUnit unit),`                              | Creates and executes a Callable task after the given delay                                                                                                                         |
| `schedule(Runnable command, long delay, TimeUnit unit),`                               | Creates and executes a Runnable task after the given delay                                                                                                                         |
| `scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)` | Creates and executes a Runnable task after the given initial delay, creating a new task every period value that passes.                                                            |
| `scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)` | Creates and executes a Runnable task after the given initial delay and subsequently with the given delay between the termination of one execution and the commencement of the next |

Example uses:

```java
// Schedule a Runnable task to start in 10 seconds
Runnable task1 = () -> System.out.println("Hello Zoo"); 
Future<?> result1 = service.schedule(task1, 10, TimeUnit.SECONDS); 

// Schedule a Callable task to start in 8 minutes
Callable<String> task2 = () -> "Monkey";
Future<?> result2 = service.schedule(task2, 8, TimeUnit.MINUTES);
```

The `scheduleAtFixedRate` and `scheduleWithFixedDelay` methods perform the same task repeatedly, after completing the initialDelay. Neither the `scheduleAtFixedRate` or `scheduleWithFixedDelay` methods take a `Callable` (only `Runnable`), as these methods are designed to run infinitely, as long as the `ScheduledExecutorService` is still alive.

The `scheduleAtFixedRate` method creates a new task and submits it to the executor every period, regardless of whether or not the previous task finished.

```java
// Execute the command every minute following a 5 minute delay
service.scheduleAtFixedRate(command, 5, 1, TimeUnit.MINUTE);
```

The  `scheduleWithFixedDelay` method creates a new task after the previous task has finished. For example, if the first task runs at 12:00 and takes five minutes to finish, with a period of 2 minutes, then the second task will start at 12:07.

```java
// Execute the first command immediately, and then 
// execute the second command 2 minutes after the previous command completed
service.scheduleWithFixedDelay(command, 0, 2, TimeUnit.MINUTE); 
```

### Increasing Concurrency with Pools

A *thread pool* is a group of pre-instantiated reusable threads that are available to perform a set of arbitrary tasks. The table below shows all the thread pools that appear on the exam.

| Method Name                            | Return Type                | Description                                                  |
| -------------------------------------- | -------------------------- | ------------------------------------------------------------ |
| `newSingleThreadExecutor()`            | `ExecutorService`          | Creates a single-threaded executor that uses a single worker thread operating off an unbounded queue. Results are processed sequentially in the order in which they are submitted. |
| `newSingleThreadScheduled Executor()`  | `ScheduledExecutorService` | Creates a single-threaded executor that can schedule commands to run after a given delay or to execute periodically. |
| `newCachedThreadPool()`                | `ExecutorService`          | Creates a thread pool that creates new threads as needed, but will reuse previously constructed threads when they are available. |
| `newFixedThreadPool(int nThreads)`     | `ExecutorService`          | Creates a thread pool that reuses a fixed number of threads operating off a shared unbounded queue. |
| `newScheduledThreadPool(int nThreads)` | `ScheduledExecutorService` | Creates a thread pool that can schedule commands to run after a given delay or to execute periodically. |

## Synchronising Data Access

*Thread safety* is the property of an object that guarantees safe execution by multiple threads at the same time. This can be achieved by ensuring that our access to data is performed in such a way that we don’t end up with invalid or unexpected results.

Given the following simple counting program, when executing this code across multiple threads, there will be undesired results. Firstly the results may not be in the correct order, but the correct numbers (in this case may not be returned).

```java
private static int count = 0;

private static void incrementAndReport() {
    System.out.print((++count) + " ");
}

public static void main(String[] args) {
    ExecutorService service = null;

    try {
        service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.submit(() -> incrementAndReport());
        }
    } finally {
        if (service != null) {
            service.shutdown();
        }
    }
}

// prints 1 4 3 5 5 2 7 10 9 8 (may differ if executed again)
```

A problem occurs when two threads both execute the right side of the increment expression — `++count` —  reading the “old” value before either thread writes the “new” value of the variable. The two assignments become redundant; they both assign the same new value, with one thread overwriting the results of the other. 

### Protecting Data with Atomic Classes

*Atomic* is the property of an operation to be carried out as a single unit of execution without any interference by another thread. A thread-safe atomic version of the increment operator would be one that performed the read and write of the variable as a single operation, not allowing any other threads to access the variable during the operation.

Since accessing primitives and references in Java is common in shared environments, the Concurrency API includes numerous useful classes that are conceptually the same as our primitive classes but that support atomic operations, which are outlined in the table below.

| Class Name             | Description                                                  |
| ---------------------- | ------------------------------------------------------------ |
| `AtomicBoolean`        | A `boolean` value that may be updated atomically             |
| `AtomicInteger`        | An `int` value that may be updated atomically                |
| `AtomicIntegerArray`   | An `int` array in which elements may be updated atomically   |
| `AtomicLong`           | A `long` value that may be updated atomically                |
| `AtomicLongArray`      | A `long` array in which elements may be updated atomically   |
| `AtomicReference`      | A generic object reference that may be updated atomically    |
| `AtomicReferenceArray` | An array of generic object references in which elements may be updated automatically |

Each class includes numerous methods that are equivalent to many of the primitive built-in operators that we use on primitives, such as the assignment operator `=` and the increment operators `++`. The common atomic methods that you should know for the exam are shown in the table below.

| Method Name         | Description                                                  |
| ------------------- | ------------------------------------------------------------ |
| `get()`             | Retrieve the current value                                   |
| `set()`             | Set the given value, equivalent to the assignment `=` operator |
| `getAndSet`         | Atomically sets the new value and returns the old value      |
| `incrementAndGet()` | For numeric classes, atomic pre-increment operation equivalent to `++value` |
| `getAndIncrement()` | For numeric classes, atomic post-increment operation equivalent to `value++` |
| `decrementAndGet()` | For numeric classes, atomic pre-decrement operation equivalent to `--value` |
| `getAndDecrement()` | For numeric classes, atomic post-decrement operation equivalent to `value--` |

Instead of using a primitive integer, an `AtomicInteger` can be used. The key here is that using the atomic classes ensures that the data is consistent between threads and that no values are lost due to concurrent modifications.

```java
private AtomicInteger count = new AtomicInteger(0); 

private void incrementAndReport() {
    System.out.print(count.incrementAndGet() + " ");
}

// prints 1 2 4 5 6 3 7 9 8 10 (may differ if executed again)
```

###  Synchronized Blocks

A *monitor* (also called a *lock*) is a structure that supports mutual exclusion or the property that at most one thread is executing a particular segment of code at a given time. 

In Java, any Object can be used as a monitor, along with the `synchronized` keyword, as shown in the following *synchronized block* example: 

```java
// Synchronized upon a given instance
Object obj = new Object(); 
synchronized(obj) {
	// Work to be completed by one thread at a time
}

// Synchronized upon all "App" instances
synchronized(App.class) {
	// Work to be completed by one thread at a time
}
```

Each thread that arrives will first check if any threads are in the block. In this manner, a thread “acquires the lock” for the monitor. If the lock is available, a single thread will enter the block, acquiring the lock and preventing all other threads from entering. While the first thread is executing the block, all threads that arrive will attempt to acquire the same lock and wait for first thread to finish. Once a thread finishes executing the block, it will release the lock, allowing one of the waiting threads to proceed.

```java
private static int count = 0;

private static void incrementAndReport() {
    synchronized(this) {
        System.out.print((++count) + " "); 
    }
}

public static void main(String[] args) {
    ExecutorService service = null;

    try {
        service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.submit(() -> incrementAndReport());
        }
    } finally {
        if (service != null) {
            service.shutdown();
        }
    }
}

// prints 1 2 3 4 5 6 7 8 9 10 
```

Although all threads are still created and executed at the same time, they each wait at the `synchronized` block for the thread to increment and report the result before entering.

### Synchronizing Methods

 Java actually provides a more convenient compiler enhancement for `synchronized` methods. For example, the following two method definitions are equivalent: 

```java
private void incrementAndReport() { 
    synchronized(this) { 
        System.out.print((++count) + " "); 
    } 
} 

private synchronized void incrementAndReport() { 
    System.out.print((++count) + " "); 
} 
```

The first uses a `synchronized` block, whereas the second uses the `synchronized` method modifier.

The `synchronized` modifier can also be added to static methods, and it uses the class object to lock. For example, the following two methods are equivalent for `static synchronization`

```java
public static void printDaysWork() { 
    synchronized(Application.class) { 
        System.out.print("Finished work");
    } 
} 

public static synchronized void printDaysWork() { 
    System.out.print("Finished work"); 
} 
```

## Identifying Threading Problems

*Liveness* is the ability of an application to be able to execute in a timely manner. Liveness problems, are those in which the application becomes unresponsive or in some kind of “stuck” state. For the exam, there are three types of liveness issues with which you should be familiar: deadlock, starvation, and livelock.

### Deadlock

*Deadlock* occurs when two or more threads are blocked forever, each waiting on the other. This can easily be achieved, when two threads have synchronised upon an object the other requires. For example if two threads each call one of the following methods, a deadlock will arise, as each method has locked an object the other requires.

```java
public void eatAndDrink(Food food, Water water) {
	synchronized(food) { 
        System.out.println("Got Food!"); 
        move(); // do some work that takes some time
        synchronized(water) {
            System.out.println("Got Water!");
		}
	}
}

public void drinkAndEat(Food food, Water water) {
	synchronized(water) { 
        System.out.println("Got Water!"); 
        move(); // do some work that takes some time
		synchronized(food) {
			System.out.println("Got Food!");
		}
    }
}
```

### Starvation

*Starvation* occurs when a single thread is perpetually denied access to a shared resource or lock. The thread is still active, but it is unable to complete its work as a result of other threads constantly taking the resource that they trying to access.

### Livelock

*Livelock* occurs when two or more threads are conceptually blocked forever, although they are each still active and trying to complete their task. Livelock is a special case of resource starvation in which two or more threads actively try to acquire a set of locks, are unable to do so, and restart part of the process. Livelock is often a result of two threads trying to resolve a deadlock.

### Race Conditions

A *race condition* is an undesirable result that occurs when two tasks, which should be completed sequentially, are completed at the same time, such as two users creating an account with the same username at the same time. This should be prevented, as two users with the same username cannot login to the system with different passwords.