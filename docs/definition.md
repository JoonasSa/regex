# Project Definition

## Algorithms and Data structures

The idea is to implement the Thompson's construction algorithm to compile a regular expression (regex) into an equivalent nondeterministic finite automaton (NFA). This NFA will be used to match strings againts the regular expression. The program will also preprocess the string  

	TIETORAKENTEET

Mitä algoritmeja ja tietorakenteita toteutat työssäsi

## The problems and solutions

Contructing NFA's is useful for programs that try to match the same regex multiple times because it is faster than simulating the regex when applied on multiple different strings.

I chose Thompson's construction as the algorithm to perform the transformation because it is the original and most well known one. 

	TIETORAKENTEET   
    Mitä ongelmaa ratkaiset ja miksi valitsit kyseiset algoritmit/tietorakenteet

## Program inputs

The program takes as an input a regex or a string to match against a regex. The regexes will be used to contruct NFA's and the inputs will be used to run against the last regex (or you might be able to choose which regex to test it with if I have the time to implement this).     

	Mitä syötteitä ohjelma saa ja miten näitä käytetään

## Target time and space complexity

* The target time complexity for preprocessing the regex is O(n) with space complexity of O(n).
* The target time complexity for constructing a NFA is O(n^2) with space complexity of O(n).
* The target time complexity for matching the string is O(n) with space complexity of O(n). 

	Tavoitteena olevat aika- ja tilavaativuudet (m.m. O-analyysit)

## Sources

[Thompson's contruction](https://en.wikipedia.org/wiki/Thompson%27s_construction)
[A Regular Expression Matcher](http://www.cs.princeton.edu/courses/archive/spr09/cos333/beautiful.html)
[Regex in Perl](https://perl.plover.com/Regex/article.html)

