# Usage instructions

## How to use the program

The program is used for trying to match an inputs with some regex. 
The program uses Java like _anchored_ regexes.
If you do not know how these kinds of regexes work you can read about them following links: [Java docs](https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html), [Java Tutorials](https://docs.oracle.com/javase/tutorial/essential/regex/pre_char_classes.html) and [Regeone](https://regexone.com/). Note that this program doesn't support all of the operations defined in the linked pages.
The repository [contains a jar file](../dist) which can be ran from the terminal. 
It can be given multiple arguments such as the regex and input.
On normal run the program returns true or false depending on whether the input string matched the regex.
On benchmark run the program returns benchmark data of the program with the given arguments.

#### Running the program

***The runnable jar file can be found in the dist directory or can be compiled in the repository root by running `./gradlew build`.*** 

To run the program use command `java -jar regex.jar` in the jar directory dist.
Get instructions how to use the program with command `java -jar regex.jar --help` or from the argument list and examples below.
After the base command give regex and input string arguments like so `a* aa` or `a* 'aa'`. 

Optional benchmark test type argument and 'times to run' argument can be provided for benchmarking the program.
***loremipsum1k.txt file found in the root of the repository can be used for benchmark testing.***
    
## Program arguments
    
Give arguments in form:  `regex input`  or `regex 'input'`
Empty string can be input like this `''`.
_By default the program treats regexes as if they were anchored from left and right.
Add .* expressions at beginning and end of your regex to match inputs without anchoring.
For example `.*a.*` matches any a character in the input string._

#### Program argument list

Symbols:
* parentheses: `()`
* kleenestar: `*`
* plus: `+`
* union: `|` (It is important to )

Character classes (match certain type of character):
* any character - `.`
* digit - `\\d`
* alphabet - `\\a`
* lowercase character - `\\l`
* uppercase character - `\\u`
* alphabet, digit and _ - `\\w`

Optional 3rd argument: test type can be provided after regex and input arguments
Optional 4th argument: run times can be provided to run the tests a specific number of times
Without 4th parameter the default mode with multiple runs is done

Test types:
* benchmark whole process with all prints - `a`
* benchmark whole process - `w`
* benchmark regex preprocessing - `p`
* benchmark nfa construction - `c`
* benchmark input string matching - `m`
* benchmark comparisons against Java Patter.match() - `r`

#### Examples

* `java -jar regex.jar a+ aa` - Normal run, regex `a+` with input `aa`. 
* `java -jar regex.jar a* ''` - Normal run, regex `a*` with input ` `.
* `java -jar regex.jar (a|b)* aaaaabba w` - Benchmark the whole program, regex `(a|b)*` with input `aaaaabba`.
* `java -jar regex.jar \\a*. abcd_ m` - Benchmark matching, regex `\\a*.` with input `abcd_`.
* `java -jar regex.jar 'lorem' ../loremipsum1k.txt v` - Benchmark matching words versus Java regex with loremipsum1k.txt file, regex `lorem` with input file `loremipsum1k.txt`.
