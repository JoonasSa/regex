# Project Definition

## Algorithms

The idea is to implement the Thompson's construction algorithm to compile a regular expression (regex) into an equivalent nondeterministic finite automaton (NFA). This NFA will be used to match strings againts the regular expression. The program will also preprocess the string to allow for richer regex syntax ([1-9], \d etc). Finally the NFA is used to match the string.

#### Thompson's contruction

The algorithm works recursively by splitting an expression into its constituent subexpressions, from which the NFA will be constructed using [a set of rules](https://en.wikipedia.org/wiki/Thompson%27s_construction) (*How to Turn a Regex into a Penny Machine*).

The algorithm has following properties: 

1. Has a single initial state which can't be reached from any other state.
2. Has a single final state which is _ _ _.
3. Two number of states leaving any state is at most two.
4. Since an NFA of m states and at most e transitions from each state can match a string of length n in time O(emn), a Thompson NFA can do pattern matching in linear time, assuming a fixed-size alphabet.

#### Preprocessing

Preprocessing removes all the syntactical sugar from the regex. For example [1-5] is tranformed to (1|2|3|4|5). This allows for richer regexes making the program more powerful for the user. Also all regexes are considered to have implicit anchor symbols at the start and the end therefore we need to add .* at the beginning and the end of the string. 

#### Matching strings

NFA is given the input string for matching. It handles the input by using these rules:

A. If there is a blank arrow leading from state X to Y, then whenever X is enqueued to Q, also enqueue Y to Q.
1. To start, enqueue the first state to active states queue Q.
2. Whenever the machine reads an input character c, dequeue all the states from Q one by one. If a dequeued state has an outgoing arrow(s) labeled c add the state(s) at the end of the arrow(s) to Q and apply rule A on it/them.
3. If there is still more characters in the input string return to 2. 
4. If one of the dequeued states was the final state and the input string has been read then the input string is matched. Otherwise the string is not matched.

## Data structures

	? :(

Mitä algoritmeja ja tietorakenteita toteutat työssäsi

## The problems and solutions

Contructing NFA's is useful for programs that try to match the same regex multiple times because it is faster than simulating the regex when applied on multiple different strings.

I chose Thompson's construction as the algorithm to perform the transformation because it is the original and most well known one. 

	TIETORAKENTEET   

    Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet

## Using the program

The program takes a regex or a string as an input. The regexes are used to contruct NFA's and the strings are match against a regex (the last one or anyone if I have the time to implement this).

## Target time and space complexity

* The target time complexity for preprocessing the regex is O(n) with space complexity of O(n).
* The target time complexity for constructing a NFA is O(n^2) with space complexity of O(n).
* The target time complexity for matching the string is O(n) with space complexity of O(n). 

	Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)

## Sources

[Thompson's contruction](https://en.wikipedia.org/wiki/Thompson%27s_construction)

[A Regular Expression Matcher](http://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html)

[Regex in Perl](https://perl.plover.com/Regex/article.html)

