# Project Definition

### Algorithms

The idea is to implement the Thompson's construction algorithm to compile a regular expression (regex) into an equivalent nondeterministic finite automaton (NFA). This NFA will be used to match strings againts the regular expression. The program will also preprocess the string to allow for richer regex syntax ([1-9], \d etc). Finally the NFA is used to match the string.

####Thompson's contruction

The algorithm works recursively by splitting an expression into its constituent subexpressions, from which the NFA will be constructed using [a set of rules](https://en.wikipedia.org/wiki/Thompson%27s_construction) (*How to Turn a Regex into a Penny Machine*).

The algorithm has following properties: 

1. Has a single initial state which can't be reached from any other state.
2. Has a single final state which is ___.
3. Two number of states leaving any state is at most two.
4. Since an NFA of m states and at most e transitions from each state can match a string of length n in time O(emn), a Thompson NFA can do pattern matching in linear time, assuming a fixed-size alphabet.

####Preprocessing



####Matching strings

Give the input string to the NFA for matching.  

### Data structures

	? :(

Mitä algoritmeja ja tietorakenteita toteutat työssäsi

### The problems and solutions

Contructing NFA's is useful for programs that try to match the same regex multiple times because it is faster than simulating the regex when applied on multiple different strings.

I chose Thompson's construction as the algorithm to perform the transformation because it is the original and most well known one. 

	TIETORAKENTEET   

    Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet

### Using the program

The program takes a regex or a string as an input. The regexes are used to contruct NFA's and the strings are match against a regex (the last one or anyone if I have the time to implement this).

### Target time and space complexity

* The target time complexity for preprocessing the regex is O(n) with space complexity of O(n).
* The target time complexity for constructing a NFA is O(n^2) with space complexity of O(n).
* The target time complexity for matching the string is O(n) with space complexity of O(n). 

	Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)

### Sources

[Thompson's contruction](https://en.wikipedia.org/wiki/Thompson%27s_construction)

[A Regular Expression Matcher](http://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html)

[Regex in Perl](https://perl.plover.com/Regex/article.html)

