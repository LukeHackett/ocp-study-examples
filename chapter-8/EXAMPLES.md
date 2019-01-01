# Chapter 8 Examples

The `UserInput` example cannot be ran from within Intellij, as Intellij does not support the `Console` class. 
To run the `UserInput` example, the following maven command can be used:

```shell
mvn compile exec:java -Dexec.mainClass="com.github.lukehackett.ocp.chapter8.UserInput" --quiet
```