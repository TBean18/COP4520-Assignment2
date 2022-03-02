# Assignment 2 - Taylor Bean

# Problem 1: Minotaurs Birthday Party

## Source Code

All source code is located in the `Party` directory which should contain the
Party and Guest classes. The main method is located in Party.java

### Run Instructions

1. Compile Source Code `javac ./Party/*.java`
2. Run the Program `java Party.Party`

### Configuration Variables

- `NUM_THREADS`
  - Default: `10`
- `IS_VERBOSE`
  - Default: `false`
  - Additional status output will print to console
- `IS_FAIR`
  - Default: `false`
  - Denotes whether the lock representing the Minotaur's maze invitation is fair.

# Problem 2: Crystal Vase

## Strategy Discussion

The 3rd option is easily the best option as the only draw back to a MCS Style
lock would be the busy wait in the unlock operation.
In this case, I think that it would be fair to assume that
