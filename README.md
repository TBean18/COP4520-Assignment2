# Assignment 2 - Taylor Bean

# Problem 1: Minotaurs Birthday Party

## Source Code

All source code is located in the `Party` directory which should contain the
`Party` and `Guest` classes. The main method is located in Party.java

### Run Instructions

1. Compile Source Code `javac ./Party/*.java`
2. Run the Program `java Party.Party`

### Configuration Variables

- `NUM_THREADS`
  - Default: `10`
- `IS_VERBOSE`
  - Default: `false`
  - Additional thread-status output will print to console
    - Note: This option will impact the runtime substantially since printing to console is a single threaded operation.
- `IS_FAIR`
  - Default: `false`
  - Denotes whether the lock representing the Minotaur's maze invitation is fair.
    - This is passed as the parameter for the reentrant lock which ensures the mutual exclusivity of the Labyrinth.

## Output

```
All NUM_THREADS Guests have Visited the Maze
Computation Completed in RUNTIMEms
```

# Problem 2: Crystal Vase

## Strategy Discussion

The 3rd option directly translates to the MCS lock discussed in lecture. I
would argue that it is the best option as the only draw back to a MCS Style lock
would be the busy wait in the unlock operation. In this scenario, I think that
it would be fair to assume that guests who wish to visit the showroom form a
queue at the showroom door. Then, as the prompt describes the current showroom
guest will leave the room and on the way out inform the first guest in the queue
that it is his/her turn.

## Source Code

All source code is located in the `vase` directory which should contain the
`Vase`, `Guest` and, `GQueue` classes. The main method is located in `Vase.java`

## Run Instructions

1. Compile the Vase Package Source Code `javac ./vase/*.java`
2. Run the Program `java vase.Vase`

## Data Structure Implementation

As mentioned before, I found the third potential solution to be extremely
similar to the MCS style lock we reviewed in lecture. Therefore, I followed much
of the implementation notes in the Textbook and slides. This is all located in
the `GQueue.java` file.

## Problem Implementation

- Finishing Condition
  - Threads will requeue until it has been detected that all threads have visited the showroom.
  - In this point, the queue is locked, all threads currently waiting in the queue will complete but no more threads can enter.
  - The program will terminate once it has detected that the queue is empty.

## Output

```
All NUM_THREADS Guests have Visited the Maze
Computation Completed in RUNTIMEms
```
