# The PP2LAL2PP-language specification

Example documents can be found in the [examples-folder](../examples).

## General code layout
Every single statement must be placed on a seperate line. Blank lines will be ignored. Multiple spaces or tabs will be interpreted as 1 space.

It is recommended for PP2LAL2PP source files to either have a `.pp2` or `.pp2lal2pp` file extension.

Also, use camelCase.

## File inclusion (`include`)

The entire contents of another file can be included in your code with the `include` keyword at the exact place you put the statement. Usage: `include <fileName>`.

*fileName* must be the name of the source file. If the source file has a `.pp2` or `.pp2lal2pp` file extension, you do not need to specify the extension. E.g. you can include the file "constants.pp2" by using `include constants`. When an extensionless file, a `.pp2` and a `.pp2lal2pp` file exists with the same name, the extensionless file will be chosen first, then the `.pp2lal2pp` file and finally the `.pp2` file. Files from different directories must include their directory names. The name does not have to be between qoutes. E.g. the file "peanut saussage.cheese" can be includied using `include peanut saussage.cheese`.

## Global variables (`global`)

Global variables are stored in the Global Base and can be used from any scope in the program. You can declare global variables only in the outermost scope using `global <varName> = <value>`.

*varName* is the name used for the variable and must be unique. There cannot be local or global variables with the same name. Variables can't have the same name as reserved keywords.

*value (optional)* this is an equal sign followed by a number. This means that the global variable has an initial value of *value*. If no value is specified (i.e. `global <varName>`) the value will be initialised with 0 being an abbreviation of `global <varName> = 0` effectively.

## Local variables (`var`)

Local variables have their values stored on the stack and are only usable in their local scope. Meaning that as soon as the function of code ends where the local variable is declared, the variable cannot be used anymore. Local variables can only be declared in functions and are declared as follows: `var <varName> = <value>`, analogous to global variables. Variables cannot have the same name as reserved keywords, neither can they have the same name as any global variable.

## Functions

Functions are a block of code that can be called from another place in the program. Functions are declared as follows:

```
function <functionName>(<arg0>, ... <argN>) {
    # Code to execute
    return <returnValue>
}
```

*functionName* this is the name of the function and must be unique and can neither be "main", "init" or "exit". It is also forbidden to use the names of any of the API functions as described in [Base API.md](Base API.md). Neither can you use one of the reserved keywords (see section down below).

*argN (optional)* is a list of arguments seperated by a comma. You can have as many arguments as you like. If you don't want to have arguments you must leave the space between the parenthesis blank like `()`.

*return* exits the function-loop. If you add a *returnValue* beind return the function will return said value. However, if you don't want to return a value you can ommit it. **Every function must return.** The return value can either be a function call, number or variable.

Using the keyword `continue` forces the function to quit and start over.

### Interrupts

Interrupts are special kinds of functions that can only be called by the processor itself. Interrupts are declared just like functions, but then with an `interrupt` keyword instead of `function`. They also have no arguments. The `return` statement in the interrupt will also be replaced by the special interrupt return instruction when compiled. Ending with `continue` will re-enable the interrupt and then behaves the same as a `return` (it does not loop automatically!).

## Main function (`main()`)

The main function is a special function, as this is where the program will start.

## If-statements (`if`/`else`)

If-statements allow you to execute certain code solely when a certain condition holds. Syntax:

```
if (<expression1>) {
    # Execute code when expression 1 holds.
}
else if (<expression2>) {
    # Execute code when expression 1 doesn't hold, but expression 2 does.
}
else {
    # Execute code when neither expression 1 or expression 2 holds.
}
```

*expression* this is a boolean expression using the relational operators mentioned down below.

## Numbers

**Characters** can be used to express ASCII characters. A character is placed between single quotes and will be converted behind the scenes to the corresponding ASCII value. Example: `'7'` becomes *55*.

**Decimal numbers** can only be typed using characters in the set `{1,2,3,4,5,6,7,8,9,0}` and cannot start with a `0`. Examples: `3`, `123`, `598123`, 

**Binary numbers** are written with the characters `{0,1}` after the prefix `0b`. Meaning that the number 10 would become `0b1010` or `0b00001010` for example as leading zeros are allowed.

**Octal numbers** are written with the characters `{0,1,2,3,4,5,6,7}` after the prefix `0`. Meaning that the number `043` would become 35 in decimal.

**Hexadecimal numbers** are written with the characters `{0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,a,b,c,d,e,f}` after the prefix `0x`. Meaning that the number 334 would become `0x14E` or `0x0014e` as leading zeros are allowed.

**Other bases** can be made by using `0_BASE_DIGITS` where `BASE` is the base from 2 to 16 and `DIGITS` is the actual number. So 104 would become `0_3_10212` if you want to write it in base 3.

## Definitions (`define`)

Definitions can be used for named constants that must be declared in the outermost scope. Defined constants cannot be changed afterwards. Definitions will be compiled to equivalent `EQU` statements. Usage: `define <name> <value>`.

*name* is the name of the definition. It is convention to use ALL_CAPS_AND_SPACES for the name.

*value* this is the numerical value of *name*.

## Comments

Comments are extra pieces of information that are functionally ignored by the compiler. However, they do appear in assembly-comments, meaning that almost any comment you write will be placed accordingly in the compiled assembly file. There is only one kind of comment, and that's the line comment.

All comments at the beginning of the file starting with `#;` will be placed as header comment in the `.asm` file. All comments placed above a function will be placed above the beginning of that subroutine and other comments will be placed behind the corresponding assembly line after the `;`.

### Line comments
Line commments will make a whole line to be functionally ignored by the compiler. You start a line comment with a hash sign `#`.

### Example
```
someFunction()
# Ignored comment
andSomeOtherFunctionCallYeeHaa()
```

## Operators

### Assignment operators

Usage:
* `<variable> = <number>` assigns the given number to the variable.
* `<variable> = <variable2>` assigns the value of *variable2* to *variable*.

Variants (thank Sten...):
* `:=` has the exact same effect as `=`.
* `=:` assigns the left value to the right variable. E.g. `<variable2> =: <variable>`.

### Arithmetic operators

The following arithmatic operators are supported:
* `+` Addition
* `-` Substraction
* `*` Multiplication
* `/` Integer division
* `%` Remainder
* **`TODO`** `**` Power (`2**3` equals 8)

So if you want to store the product of 5 and 7 in a variable a, do this: `a = 5 * 7`.

To make it even better, if you want to do arithmetics on a variable and want to store it directly back in the same variable, you can add a `=`-sign to make it automatically assign. Example:

```
a = 7 * 3   # a becomes 21
a -= 15     # a becomes 6
a **= 3     # a becomes 216
```

### Bitwise operators

You can do some epic shizzles with binary numbers. These operators are supported on a bitwise level:
* `|` Bitwise OR
* `&` Bitwise AND
* `^` Bitwise XOR
* `~` Bitwise NOT (see unary operators)

Like the arithmetic operators, you can use them in conjunction with the `=`-sign (except `~` - e.g. `^=`).

### Relational operators

Relational operators are only used in boolean expressions (in If-statements). The following exist:
* `<` Strictly lesser than
* `<=` Lesser than or equal to
* `>` Strictly greater than
* `>=` Strictly greater than or equal to
* `==` Equals
* `!=` Does not equal

#### Example expressions

```
3 < 4       # true
5 >= 1      # true

a = 3
5 == a      # false
5 != a      # true
```

### Unary operators
These are placed right in front of a variable or number.
* `-` marks a positive number or variable as negative, or a negative number positive. This does work on numbers and on variables and you cannot use this in an operation other than assignment (e.g. `this = -that`, *not* `this = -that * 6`).
* `~` will replace all `1`s by `0`s and all `0`s by `1`s in binary representation. This does not work on numbers and you cannot use this in an operation other than assignment (e.g. `this = ~that`, *not* `this = ~that + 2`).
* `!` negates a boolean expression. Can **only** be used with the API function `isInputOn(num)`.

## Assembly injection (`inject`)

*Warning: dangerous!* Assembly injection lets you inject native assembly code in your program. The lines will be placed in the 'compiled' file exactly as you type them. This makes it really powerful, but also really dangerous to use. We do not recommend using it. It is solely meant for people who know what they are doing and want to have some more control. Hashtag Yolo.

Usage:
```
inject {
    LOAD  R2  0
    STOR  R2  [GB+123]
    ; More of your assembly shizzles.
}
```

## Reserved words

The following names are reserved and cannot be used as a global variable name, function name, variable name or any other name I might have missed:
* IOAREA 
* INPUT  
* OUTPUT 
* DSPDIG 
* DSPSEG 
* TIMER  
* ADCONV 
* CODESEG 
* LAST_OUTPUT
* Hex7Seg
* Hex7Seg_tbl
* Hex7Seg_bgn
* main
* init
* exit
* Words with prefix "if#" where # is a number.
* Any of the names of the Base API functions.
* Any of the PP2 instructions.
* R0, R1, R2, R3, R4, R5, R6, R7, SP, GB.
* Any bracket.
* When you have a interrupt named `x`, the names `enable_x`, `enable_hack_x` and `disable_x` cannot be used for a function or interrupt.
* And probably more assembly-related stuff.

## Weird exceptions on the rules

* All operations can only consist of two operands excluding assignment. Meaning that `a = b * c` is valid, whilst `a = b * (c + 2)` is not.
* There can't be any operations in function calls. E.g. `main(3 + 2)` is illegal.
* Function calls may only appear as the second element in operations.
* You can't start your block on the same line as the opening bracket `{`.
* A pair of a unary operator and a variable/number counts as a whole operation.
