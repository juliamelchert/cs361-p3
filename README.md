# Project 3: Turing Machine Simulator

* Authors: Julia Melchert and Axel Murillo
* Class: CS361 Section 002
* Semester: Spring 2024

## Overview

This project uses Java to mimic a turing machine.

## Reflection

* What worked well and what was a struggle?
At first, we tried using Character and String as the data type for the transition symbol in the Turing Machine simulation. 
This led to several challenges as the transition definitions in the input files are integers, and converting/parsing the integers
to characters made it more complex than just reading the integer itself. The need to parse characters back to integers and characters increased
the amount of error handling lines and made it difficult to validate the input correctly. We decided to change our approach when rereading 
the project requirements as the input string alphabet was represented as integer numbers instead of a wider range of characters. 

* What concepts still aren't quite clear?

* What techniques did you use to make your code easy to debug and modify?

* What would you change about your design process?

* If you could go back in time, what would you tell yourself about doing this project?


## Compiling and Using

In order to compile and run the test suite, use the following commands:
```
javac tm/TMSimulator.java tm/TMState.java
java tm/TMSimulator.java fileX.txt
```
where fileX.txt is the filename of the properly-formatted turing machine specifications (see file0.txt for an example).

## Sources Used

* https://www.w3schools.com/java/java_files_read.asp
* https://piazza.com/class/lqzw3z96xyika/post/72
* 
