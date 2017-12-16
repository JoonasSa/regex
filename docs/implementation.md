# Implementation of the program

## Program structure overview

The program consist of three parts: preprocessing the regex, building the NFA and matching the input string.

1. Preprocessor `RegexStringPreprocessor` manipulates the input regex by adding meta characters and parsing regex syntax into a form understood by the rest of the program.
* For example the wild card character `.` is changed into unicode character with the value of 0.    
* Special regex syntax like `[0-2]` is parsed into a general form `(0|1|2)`.
* Expressions with regex symbol `+` is transformed into expressions with regex symbol `*`. For example expression `(ab)+` is transformed into expression `ab(ab)*` as they are equivalent.

2. `NFAConstructor` builds the NFA based on the preprocessed regex with the help of `RegexSubstring`.
* `NFAConstructor` builds the NFA based on the values returned by `RegexSubstring` which goes over the regex one character at a time.
* `NFAState` is an object containing all of the data of a single state in the NFA. The data consist of state's: `type, transition symbol, and possibly two children states`. 
* `NFAConstructor` has its own functions for handling special regex character `|` and `*`.

3. `NFAMatcher` simulates the built NFA trying to recognize given input string with the help a `Queue`.

Matching works as follows:
1. To start, enqueue the first state to active states queue Q.
2. Whenever the machine reads an input character c, dequeue all the states from Q one by one. If a dequeued state X has an outgoing transition(s) labeled c add the state(s) at the end of the transition(s) to Q. If there is a blank (`ε`) transition leading from state X to Y, then whenever X is enqueued to Q, also enqueue Y to Q. The machined continues enqueuing all subsequent states with blank transition recursively until it can't.   
3. If there are still more characters in the input string return to 2 and read the next char c. 
4. When the input string is read. Dequeu all the states from Q if any one of them was the final state then the input string is matched. Otherwise the string is not matched.

## Time and space complexity

Definitions:
* `r = regex length`
* `i = input length`

#### Regex preprocessing

Time complexity: `O(r²)`. Preprocessor goes over the whole input string 4 times. Changing `+` to `*`, reversing, parsing, and removing empty indexes. When `+` are changed to `*` the length of expression increases. For example `(abc)+` becomes `abc(abc)*` and `(a+)+` becomes `aa*(aa*)*`. This expansion seems to be about 0,5 times the length of the expression. In general case the time complexity is `4r = r`. In cases where there are multiple nested `+` symbols the time complexity might start to get closer to `O(r²)`.

Space complexity: `O(r²)`. Preprocessor needs space for input string, and couple structures to manipulate the string in. All of them about `O(r)` space. In cases where there are multiple nested `+` symbols the space complexity might start to get closer to `O(r²)`.

#### Constructing NFA

Time complexity: `O(r)`. Eventhough the construction process can go through quite a lot of recursion the constructor goes over each character in the preprocessed regex once. All the operations performed by the constructor are performed in constant time O(1). Therefore the construction process takes `r * O(1) = O(r)` time, where `r` is the length of the preprocessed regex.

Space complexity: `O(r²)`. Based on empirical test results the worst case can have close to `r²` states, where `r` is the length of the preprocessed regex. 

#### Matching input

Time complexity: `O(ir)`. First all states that are accessible from the start state are recursively added to a queue. The matcher goes over each character in the input string. Upon reading a character `c` all states in the queue are dequeued from the queue one by one and matched against the character `c`. The number of states in the queue at any time is anywhere between `1` to about `3r` based on empirical test results.

Space complexity: `O(i + r)`. Matching needs input string `i` and queue with `1-3r` states. Therefore the space complexity is `O(i + r)`.

## Comparisons

TODO (O-notaatio javalle?)

## Possible shortcomings & improvement ideas

The way the NFA is implemented might be a bit heavy.
Trying to match an input string with a NFA is by far the slowest part of the process. NFA to DFA transformation with powerset construction might make this part a lot faster.
   
## Sources

Empirical test results and reasoning about the algorithms.