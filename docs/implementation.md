# Implementation of the program

## Program structure overview

The program consist of three parts: preprocessing the regex, building the NFA and matching the input string.

1. Preprocessor `RegexStringPreprocessor` manipulates the input regex by adding meta characters and parsing regex syntax into a form understood by the rest of the program.
* For example the wild card character `.` is changed into unicode character with the value of 0.    
* Special regex syntax like `[0-2]` is parsed into a general form `(0|1|2)`.

2. `NFAConstructor` builds the NFA based on the preprocessed regex with the help of `RegexSubstring`.
* `NFAConstructor` builds the NFA based on the values returned by `RegexSubstring` which goes over the regex one character at a time.
* `NFAState` is an object containing all of the data of a single state in the NFA. The data consist of state's: `type, transition symbol, and possibly two children states`. 
* `NFAConstructor` has its own functions for handling special regex character like `|, *, +`.

3. `NFAMatcher` simulates whether the built NFA recognizes the given input string with the help a `Queue`

Matching works as follows:
1. To start, enqueue the first state to active states queue Q.
2. Whenever the machine reads an input character c, dequeue all the states from Q one by one. If a dequeued state X has an outgoing transition(s) labeled c add the state(s) at the end of the transition(s) to Q. If there is a blank (ε) transition leading from state X to Y, then whenever X is enqueued to Q, also enqueue Y to Q. The machined continues enqueuing all subsequent states with blank transition recursively until it can't.   
3. If there are still more characters in the input string return to 2 and read the next char c. 
4. When the input string is read. Dequeu all the states from Q if any one of them was the final state then the input string is matched. Otherwise the string is not matched.

## Time and space complexity

TODO

## Comparisons    

TODO
    
## Possible shortcomings & improvement ideas

The way the NFA is implemented might be a bit heavy. 
NFA to DFA transformation with powerset construction would be a interesting thing to add to the project in the future.
   
## Sources

TODO
