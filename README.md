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
    Even with this initial struggle, we worked well together by working on the project at different times, so as to avoid merge conflicts, and 
    both being willing to put in the work on the project in order to get it done.

* What concepts still aren't quite clear?

    We think turing machines and their related concepts are pretty clear, so we don't have any lingering questions.

* What techniques did you use to make your code easy to debug and modify?

    We used Object-Oriented Programming (OOP) techniques in order to separate concerns and functionality of our project. This made it easy 
    to test one section of our code at a time by making sure a class works before implementing it into the whole. This also made our code 
    more readable -- for instance, our actual turing machine simulation and output boils down to just two function calls, making it easy to see 
    what happens when.

* What would you change about your design process?

    As stated in the first section, we would think more carefully about the project specifications in order to decide our data types more 
    effectively. On top of that, we might consider splitting our project up into more files/classes in order to avoid large chunks of 
    code.

* If you could go back in time, what would you tell yourself about doing this project?

    We would tell ourselves that it's okay to change things as you go, even if that means you'll have to do some extra work on the front-end. This 
    is because these changes will make our code better in the long run. For us, this meant redesigning our project a few times in order to create 
    something that was split up in a way that made sense and that used the right data structures and data types. We were hesitant to make these changes 
    at first, but they paid off in the end!

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
* https://docs.oracle.com/javase/8/docs/api/java/util/Set.html
