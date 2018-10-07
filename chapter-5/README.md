# Chapter 5 - Working with Dates and Times

All Date and Time classes within Java 8 reside within the `java.time.*` package, and will need to be imported inorder to access these classes.

## Creating Dates and Times

Java 8 introduced four new classes when working with dates and times:

- **LocalDate** contains just a date, neither the time or timezone information is stored. A good example of `LocalDate` would be someone's birthday.
- **LocalTime** contains just a time, neither the date or timezone information is stored. A good example of `LocalTime` would be midnight, as it always occurs at the same time every day.
- **LocalDateTime** contains both a date and a time, but no timezone information is stored. A good example of `LocalDateTime` would be the stroke of midnight on New Year's Eve, as its special point in time that occurs no matter the time zone.
- **ZonedDateTime** contains the date, time, and timezone. A good example of `ZonedDateTime` would be a conference call at 9:00 a.m. EST - someone wishing to join the call in the PST timezone would need to join at 6:00 a.m, as PST is three hours behind EST.

Obtaining the current date and time instances can be achieved using a static method:

```java
System.out.println(LocalDate.now());      // 2018-10-06
System.out.println(LocalTime.now());      // 12:11:11.451
System.out.println(LocalDateTime.now());  // 2018-10-06T12:11:11.451
System.out.println(ZonedDateTime.now());  // 2018-10-06T12:11:11.452+01:00[Europe/London]
```

The exam expects you to be able to convert timezones to the GMT equivalent time, as shown below:

```java
2015-06-20T07:50+02:00[Europe/Paris]      // GMT 2015-06-20 05:50
2015-06-20T06:50+05:30[Asia/Kolkata]      // GMT 2015-06-20 01:20

2015-06-20T07:50 GMT-04:00                // GMT 2015-06-20 11:50
2015-06-20T04:50 GMT-07:00                // GMT 2015-06-20 11:50
```

### LocalDate

When creating a `LocalDate` can be achieved by providing the year, month and day. Remember months start at `1` and not `0`, as previously was the case. It is also possible to use the Month enum.

```java
LocalDate date1 = LocalDate.of(2015, Month.JANUARY, 20);
LocalDate date2 = LocalDate.of(2015, 1, 20);
```

### LocalTime

When creating a `LocalTime` you can specify how detailed you want to be, with various overloaded methods. 

```java
LocalTime time1 = LocalTime.of(6, 15);           // hour, minute
LocalTime time2 = LocalTime.of(6, 15, 30);       // hour, minute, seconds
LocalTime time3 = LocalTime.of(6, 15, 30, 200);  // hour, minute, seconds, nanoseconds
```

### LocalDateTime

When creating a `LocalDateTime` you can specify how detailed you want to be, with various overloaded methods, as well as being able to create a `LocalDateTime` from an existing `LocalDate` object and `LocalTime` object.

```java
// year, month, day, hour, minute
LocalDateTime dateTime1 = LocalDateTime.of(2015, Month.JANUARY, 20, 6, 15);
LocalDateTime dateTime2 = LocalDateTime.of(2015, 1, 20, 6, 15);

// year, month, day, hour, minute, seconds
LocalDateTime dateTime3 = LocalDateTime.of(2015, Month.JANUARY, 20, 6, 15, 30);
LocalDateTime dateTime4 = LocalDateTime.of(2015, 1, 20, 6, 15, 30);

// year, month, day, hour, minute, seconds, nanoseconds
LocalDateTime dateTime5 = LocalDateTime.of(2015, Month.JANUARY, 20, 6, 15, 30, 200);
LocalDateTime dateTime6 = LocalDateTime.of(2015, 1, 20, 6, 15, 30, 200);

// LocalDate, LocalTime
LocalDateTime dateTime7 = LocalDateTime.of(date1, time1);
```

### ZonedDateTime

When creating a `ZonedDateTime` object you can specify how detailed you want to be, as well as proving the Zone that the DateTime object resides in. Only the following three method overloads are used on the exam.

```java
// Zone Id
ZoneId zone = ZoneId.of("US/Eastern");

// year, month, day, hour, minute, seconds, nanoseconds, zone
ZonedDateTime zonedDateTime1 = ZonedDateTime.of(2015, 1, 20, 6, 15, 30, 200, zone);

// LocalDate, LocalTime
ZonedDateTime zonedDateTime2 = ZonedDateTime.of(date1, time1, zone);
ZonedDateTime zonedDateTime3 = ZonedDateTime.of(dateTime1, zone);
```

It should also be noted that there is not a option to pass the `Month` enum. 

### Exam Tricks

The constructors of `LocalDate`, `LocalTime`, `LocalDateTime` and `ZonedDateTime` are all private, which means direct instantiation is not possible. 

```java
LocalDate d = new LocalDate();            // does not compile
LocalTime d = new LocalTime();            // does not compile 
LocalDateTime d = new LocalDateTime();    // does not compile
ZonedDateTime d = new ZonedDateTime();    // does not compile
```

In addition to this, when passing invalid numbers to the factory method, a runtime exception is thrown

```java
LocalDate.of(2018, 1, 32);                 // throws DateTimeException
LocalDate.of(2018, -1, 20);                // throws DateTimeException
LocalDate.of(2018, Month.FEBRUARY, 29);    // throws DateTimeException
```

## Manipulating Dates and Times

Manipulating Dates and Times are easy, thanks to various methods that reside on each of the `LocalDate`, `LocalTime`, `LocalDateTime` and `ZonedDateTime` classes. Each the `LocalDate`, `LocalTime`, `LocalDateTime` and `ZonedDateTime` objects are immutable, meaning the results of manipulating dates and times must be assigned to a reference variable.

```java
LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
date.plusDays(10);
System.out.println(date);  // prints 2020-01-20

date.plusDays(10);
System.out.println(date);  // prints 2020-01-30
```

The following table illustrates all the methods that are available for each of the `LocalDate`, `LocalTime`, `LocalDateTime` and `ZonedDateTime` classes.


|  | Can Call on `LocalDate`? | Can Call on `LocalTime`? | Can Call on `LocalDateTime`<br /> or `ZonedDateTime`? |
|-------------------------------------|:------------------------:|:------------------------:|:-----------------------------------------------:|
| `plusYears` / `minusYears` | Yes | No | Yes |
| `plusMonths` / `minusMonths` | Yes | No | Yes |
| `plusWeeks` / `minusWeeks` | Yes | No | Yes |
| `plusDays` / `minusDays` | Yes | No | Yes |
| `plusHours` / `minusHours` | No | Yes | Yes |
| `plusMinutes` / `minusMinutes` | No | Yes | Yes |
| `plusSeconds` / `minusSeconds` | No | Yes | Yes |
| `plusNanos` / `minusNanos` | No | Yes | Yes |

### Working with Periods

A `Period` uses date-based values (years, months, days), that can be reused throughout a java application. For example a 1 day period will always be the same, no matter which timezone you are working with.

```java
LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
date = date.plus(Peroid.ofDays(5));
System.out.println(date);  // 2020-01-25

date = date.minus(Peroid.ofDays(5));
System.out.println(date);  // 2020-01-20
```

The full listing of available `Period` methods are available below.

```java
Period annually = Period.ofYears(1);
Period quaterly = Period.ofMonths(3);
Period biWeekly = Period.ofWeeks(2);
Period everyOtherDay = Period.ofDays(2);
Period everyYearAndWeek = Period.of(1, 0, 7);
```

A `Period` can be used with `LocalDate`, `LocalDateTime` or `ZonedDateTime` objects, but cannot be used with `LocalTime` objects, since they do not store dates.

```java
LocalTime.now().plus(Period.ofDays(1));
// java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Days

LocalTime.now().minus(Period.ofDays(1));
// java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Days
```

You cannot chain methods when working with the `Period` class, this is because the `of___` methods are `static` methods. The following two declarations are equal.

```java
Period wrong1 = Period.ofYears(1).ofWeeks(1);  // every week

Period wrong2 = Period.ofYears(1);             // every year
wrong2 = Period.ofWeeks(1);                    // every week
```

When printing a `Period` java uses a special format to denote each of the years, months and days values. It should be noted that weeks are automatically converted to days

```
P       1Y      2M        3D
Period  1 Year  2 Months  3 days

System.out.println(Period.ofMonths(3));     // P3M
System.out.println(Period.of(0, 40, 21));   // P40M21D
System.out.println(Period.ofWeeks(3));      // P21D
```

### Working with Durations

A `Duration` measures an amount of time using time-based values (seconds, nanoseconds), that can be reused throughout a java application. For example a 1 hour duration will always be the same, no matter which timezone you are working with.

A `Duration` can be used much in the same way as a `Period` , except that it is used with objects that have time. The full listing of available `Duration` methods are available below.

```java
Duration everyDay = Duration.ofDays(1);           // PT24H
Duration everyHour = Duration.ofHours(1);         // PT1H
Duration everyMinute = Duration.ofMinutes(1);     // PT1M
Duration everySecond = Duration.ofSeconds(10);    // PT10S
Duration everyMilli = Duration.ofMillis(1);       // PT0.001S
Duration everyNano = Duration.ofNanos(1);         // PT0.000000001S
```

A `Duration` is output with `PT`, which stands for "Period Time". A `Duration` is stored in hours, minutes and seconds, with the number of seconds including a fractional seconds. `Duration` does not have a static `of()` factory method, like `Period` does, so if you wanted something to happen every hour and a half, you would use 90 minutes.

A `Duration` can be used with `LocalTime`, `LocalDateTime` or `ZonedDateTime` objects, but cannot be used with `LocalDate` objects, since they do not store time.

### Working with Instants

The `Instant` class represents a specific moment in time in the GMT timezone, for example, the following example shows the number of milliseconds it took to perform some processing:

```java
Instant now = Instant.now();
// Do some processing
Instant later = Instant.now();

Duration duration = Duration.between(now, later);
System.out.println(duration.toMillis());    // prints 1025
```

If you have a `ZonedDateTime` you can convert it into an `Instant`, but you cannot convert a `LocalDateTime` to an `Instant`, since `LocalDateTime` does not store the TimeZone information.

```java
LocalDate date = LocalDate.of(2015, 5, 25);
LocalTime time = LocalTime.of(11, 55, 00);
ZoneId zone = ZoneId.of("US/Eastern");

ZonedDateTime zonedDateTime = ZonedDateTime.of(date, time, zone);
Instant instant = zonedDateTime.toInstant();

System.out.println(zonedDateTime);    // 2015-05-25T11:55-04:00[US/Eastern]
System.out.println(instant);          // 2015-05-25T15:55:00Z
```

If you have the number of seconds since 1970, you can create an instance from Epoch

```java
Instant instant = Instant.ofEpochSecond(epochSeconds);
System.out.println(instant);          // 2018-10-06T13:40:19.190Z

System.out.println(instant.plus(1, ChronoUnit.DAYS));     // 2018-10-07T13:40:19.190Z
System.out.println(instant.plus(1, ChronoUnit.HOURS));    // 2018-10-06T14:40:19.190Z
System.out.println(instant.plus(1, ChronoUnit.MONTHS));   // exception thrown
```

`Instant` only allows you to add or minus any unit day or smaller, and does not support Years, Months or Weeks.

### Daylight Savings Time

Java automatically detects daylight savings, and will apply them appropriately. For example, in the USA clocks went forward on the 13th March 2016, as shown below:

```java
LocalDate date = LocalDate.of(2016, Month.MARCH, 13); 
LocalTime time = LocalTime.of(1, 30);
ZoneId zone = ZoneId.of("US/Eastern");
ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
System.out.println(dateTime);         // 2016–03–13T01:30–05:00[US/Eastern] 
dateTime = dateTime.plusHours(1);
System.out.println(dateTime);         // 2016–03–13T03:30–04:00[US/Eastern]
```

Similarly in November, an hour after the initial 1:30 is also 1:30 because at 2:00 a.m. the hour is repeated.

```java
LocalDate date = LocalDate.of(2016, Month.NOVEMBER, 6);
LocalTime time = LocalTime.of(1, 30);
ZoneId zone = ZoneId.of("US/Eastern");
ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone);
System.out.println(dateTime);           // 2016–11–06T01:30–04:00[US/Eastern] 
dateTime = dateTime.plusHours(1);
System.out.println(dateTime);           // 2016–11–06T01:30–05:00[US/Eastern]
```

In addition to the rolling forwards and backwards of time, when trying to create a time that doesn’t exist, java just rolls forward: 

```java
LocalDate date = LocalDate.of(2016, Month.MARCH, 13);
LocalTime time = LocalTime.of(2, 30);
ZoneId zone = ZoneId.of("US/Eastern");
ZonedDateTime dateTime = ZonedDateTime.of(date, time, zone); 
System.out.println(dateTime);   // 2016–03–13T03:30–04:00[US/Eastern] 
```

Java is smart enough to know that there is no 2:30 a.m. that night and switches over to the appropriate GMT offset. 

The exam will let you know if a date/time mentioned falls on a weekend when the clocks are scheduled to be changed.

## Adding Internationalisation and Localisation

*Internationalisation (i18n)* is the process if designing a program so that it can be adapted for multiple language and formatting options. *Localisation (i18n)* means to support multiple locales, which can be thought of as a language and country pairing.

### Picking a Locale

The `Locale` class can be found within the `java.util` package, and obtaining the current user's locale can be completed with the `getDefault` method:

```java
Locale locale = Locale.getDefault();
System.out.println(locale);    // en_US
```

A Locale can comprise of just a Language code or a Language code and a Country code. The Language code is always lowercase, while the Country code is always uppercase.

> fr              (Language only)
>
> en_US     (Language & Country) 

A Locale can also be created in a number of ways

```java
System.out.println(Locale.GERMAN);             // de        (German)
System.out.println(Locale.GERMANY);            // de_DE     (German in Germany)

System.out.println(new Locale("fr"));          // fr        (French)
System.out.println(new Locale("hi", "IN"));    // hi_IN     (Hindi in India)
```

The `Locale` class also has a builder method, that allows you to create a Locale

```java
Locale locale = new Locale.Builder()
    .setRegion("US")
    .setLanguage("en")
    .build();
```

*Note: Java will let you create a `Locale` with an invalid language or country, however it will not match the `Locale` you may have wanted, and thus the program may not behave as expected.*

You may also set the `Locale` for the duration of a program with the `setLocale` method, this will only last for the duration of the program.

```java
System.out.println(Locale.getDefault());    // en_US
Locale.setDefault(new Locale("fr"));
System.out.println(Locale.getDefault());    // fr
```

### Using a Resource Bundle

A *resource bundle* contains the local specific objects to be used by a program, and can be thought of like a map with keys and values. The resource bundle can be in a property file or in a Java class.

```java
// Property files
Zoo_en.properties
	hello=Hello
	open=The zoo is open
	
Zoo_fr.properties
	hello=Bonjour
	open=Le zoo est ouvert

// Java Class
Locale us = new Locale("en", "US");
Locale france = new Locale("fr", "FR");

ResourceBundle usResource = ResourceBundle.get("Zoo", france);
ResourceBundle frResource = ResourceBundle.get("Zoo", france);

System.out.println(usResource.getString("hello"));  // Hello
System.out.println(usResource.getString("open"));   // The zoo is open
System.out.println(frResource.getString("hello"));  // Bonjour
System.out.println(frResource.getString("open"));   // Le zoo est ouvert
```

Since a resource bundle contains key/value pair, you can loop through them to list all of the pairs, for example:

```java
Locale us = new Locale("en", "US");
ResourceBundle resourceBundle = ResourceBundle.get("Zoo", france);

Set<String> keys = resourceBundle.keySet();
keys.stream()
    .map(k -> k + " " + resourceBundle.getString(k))
    .forEach(System.out::println);
```

In addition to using a property file, java supports the use of a resource bundle class. In order to obtain the bundle class, the `getBundle` should be used, ensuring that the first parameter is the full java path to the class, not including any locale identifier.  

```java
package resource;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Tax_en_US extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                { "vat", 17.5D },
                { "corporation", 28.0D }
        };
    }

    public static void main(String[] args) {
        ResourceBundle rb = ResourceBundle.getBundle("resource.Tax", Locale.US);
        System.out.println(rb.getObject("vat"));            // 17.5
        System.out.println(rb.getObject("corporation"));    // 28.0
    }
}
```

Obtaining a resource bundler can be achieved using one of the `getBundle` methods

```java
ResourceBundle.getBundle("name");  // uses the default locale
ResourceBundle.getBundle("name", locale);  
```

Java handles the logic of picking the bet available resource bundle for a given key, buy prioritising java class files over properties, and specific Locale & Country combinations over Locale only properties. This is outlined as:

1. Always look for the property file after the matching Java class
2. Drop one thing at a time if there are no matches. First drop the country, and then the language.
3. Look at the default locale and the default resource bundle last.

For example, when requesting a resource for French in France, with a default Locale of US English, Java would perform the following steps, and would halt once it had found a match:

1. `Zoo_fr_FR.java`
2. `Zoo_fr_FR.properties`
3. `Zoo_fr.java`
4. `Zoo_fr.properties`
5. `Zoo_en_US.java`
6. `Zoo_en_US.properties`
7. `Zoo_en.java`
8. `Zoo_en.properties`
9. `Zoo.java`
10. `Zoo.properties`
11. throw `MissingResourceException` if not found

### Formatting Numbers

Formatting Numbers such as currency can be achieved by using the `NumberFormat` class, which provides factory method used to get the desired formatter.

| Description | Using Default `Locale` and a Specified `Locale` |
|-----------------------------------------------------------|---------------------------------------------------------------------------------------|
| A general purpose formatter | `NumberFormat.getInstance()` <br />`NumberFormat.getInstance(locale)` |
| Same as `getInstance` | `NumberFormat.getNumberInstance()` <br />`NumberFormat.getNumberInstance(locale)` |
| For formatting monetary amounts | `NumberFormat.getCurrencyInstance()` <br />`NumberFormat.getCurrencyInstance(locale)` |
| For formatting percentages | `NumberFormat.getPercentInstance()` <br />`NumberFormat.getPercentInstance(locale)` |
| Rounds decimal values before displaying (not on the exam) | `NumberFormat.getIntegerInstance()` <br />`NumberFormat.getIntegerInstance(locale)` |

Once a  `NumberFormat` object has been obtained you can call `format` to turn a number into a String. The following example, shows how the formatting of a number can be different for each locale.

```java
int attendeesPerYear = 3_200_000;
int attendeesPerMonth = attendeesPerYear / 12;

NumberFormat us = NumberFormat.getInstance(Locale.US);
NumberFormat g = NumberFormat.getInstance(Locale.GERMANY);
NumberFormat ca = NumberFormat.getInstance(Locale.CANADA_FRENCH); 

System.out.println(us.format(attendeesPerMonth));    // 266,666
System.out.println(g.format(attendeesPerMonth));     // 266.666
System.out.println(ca.format(attendeesPerMonth));    // 266 666
```

Formatting currency works in the same way, notice how Java automatically formats with two decimal places

```java
int ticketPrice = 36;

NumberFormat us = NumberFormat.getCurrencyInstance(Locale.US);
NumberFormat g = NumberFormat.getCurrencyInstance(Locale.GERMANY);

System.out.println(us.format(ticketPrice));    // $36.00
System.out.println(g.format(ticketPrice));     // 36,00 €
```

To parse a String, the `parse` method can be used, but unlike the `format` method, this will throw the checked exception `ParseException`, and hence will need to be handled correctly. The following code parses a discounted ticket price with different locales:

```java
NumberFormat en = NumberFormat.getInstance(Locale.US); 
NumberFormat fr = NumberFormat.getInstance(Locale.FRANCE);

String s = "40.45"; 
System.out.println(en.parse(s));  // 40.45 
System.out.println(fr.parse(s));  // 40
```

In the United States a dot is part of a number, while in France, the decimal point is not used to separate numbers, and Java parses it as a formatting character, and hence stops processing the rest of the number. This is illustrated further below:

```java	
NumberFormat nf = NumberFormat.getInstance();
String one = "456abc";
String two = "-2.5165x10";
String three = "x85.3";
System.out.println(nf.parse(one));     // 456 
System.out.println(nf.parse(two));     // -2.5165 
System.out.println(nf.parse(three));   // throws ParseException
```

The parse method is also used for parsing current, for example we can read the total amount of ticket sales with the following code

```java
String amount = "$92,234.69";
NumberFormat cf = NumberFormat.getCurrencyInstance(); 
double value = (Double) cf.parse(amount); 
System.out.println(value); // 92234.69
```

### Formatting Dates and Times

Java provides a `DateTimeFormatter` class that can be used to format any type of date and/or time object. The `DateTimeFormatter` class can be found within the `java.time.format` package. The example below, shows an example of formatting using the predefined short format. The short format will only format a given date, and hence an `UnsupportedTemporalTypeException` is thrown when a time value is given.



```java
LocalDate date = LocalDate.of(2020, Month.JANUARY, 20);
LocalTime time = LocalTime.of(11, 12, 34);
LocalDateTime dateTime = LocalDateTime.of(date, time);

DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);

System.out.println(shortDateTime.format(dateTime));    // 1/20/20
System.out.println(shortDateTime.format(date));        // 1/20/20
System.out.println(shortDateTime.format(time));        // UnsupportedTemporalTypeException
```

It should also be pointed out that the `format` methods are available upon the date/time objects as well as the `DateTimeFormatter` class.

```
DateTimeFormatter shortDateTime = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
System.out.println(dateTime.format(shortDateTime)); System.out.println(date.format(shortDateTime)); System.out.println(time.format(shortDateTime));
```

Java also provides the ability to create a custom format, using the `ofPattern` factory method. 

```java
DateTimeFormatter f = DateTimeFormatter.ofPattern("MMMM dd, yyyy, hh:mm"); System.out.println(dateTime.format(f));  // January 20, 2020, 11:12
```

*Note: The exam only requires you to be able to recognise the month, day, year, hour and seconds, timezones are excluded due to their complexity.*

The `parse` method allows for a date/time string to be parsed based upon a default parser, or a given formatter.

```java
DateTimeFormatter f = DateTimeFormatter.ofPattern("MM dd yyyy"); 
LocalDate date = LocalDate.parse("01 02 2015", f);    // custom formatter
LocalTime time = LocalTime.parse("11:22");            // default formatter
System.out.println(date);                             // 2015–01–02 
System.out.println(time);                             // 11:22
```

If the String given to the `parse` method cannot be parsed to the formatter, then a runtime exception is thrown.

```java
LocalTime time = LocalTime.parse("11:22aa");  // throws DateTimeParseException
```