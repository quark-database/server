<p align="center">
  <a href="https://anafro.ru/quark">
    <img src="https://raw.githubusercontent.com/quark-database/.github/main/Assets/Banner.png" alt="Quark Banner">
  </a>
</p>


[![Java CI with Maven](https://github.com/quark-database/server/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/quark-database/server/actions?query=workflow:"Java+CI+with+Maven")
[![GitHub tag](https://img.shields.io/github/tag/quark-database/.github?include_prereleases=&sort=semver&color=blue)](https://github.com/quark-database/server/releases/)
[![License](https://img.shields.io/badge/License-MIT-blue)](#license)
[![issues - server](https://img.shields.io/github/issues/quark-database/.github)](https://github.com/quark-database/server/issues)
![Coverage](.github/badges/jacoco.svg)

## About Quark Server

*...*

## Documentation

This documentation is for Quark 3.0.0 in development.
For earlier versions of Quark, please, search for README.md
in the release commit with tag you want. We are currently working
on a documentation system with multiple versions.
### Instructions
Instructions are used to create tables, query data, and much more.

#### `add column`

Adds a new column to a table

Permission: `column.add`

Parameters:
`definition: column`}

, `table: str`}

, `generator: generator`}



Syntax:
```sql
add column (column: definition): table = str, generator = generator;
```
#### `change in`

Changes records match the condition

Permission: `table.change`

Parameters:
`table: str`}

, `selector: selector`}

, `changer: changer`}



Syntax:
```sql
change in (str: table): selector = selector, changer = changer;
```
#### `change port to`

Changes the server port

Permission: `server.port.change`

Parameters:
`port: int`}



Syntax:
```sql
change port to (int: port);
```
#### `clear database`

Deletes all the tables inside the database

Permission: `database.clear`

Parameters:
`database: str`}



Syntax:
```sql
clear database (str: database);
```
#### `clear table`

Removes all the records from the table

Permission: `table.clear`

Parameters:
`table: str`}



Syntax:
```sql
clear table (str: table);
```
#### `clone database`

Copies the database with all the contents

Permission: `database.clone`

Parameters:
`prototype: str`}

, `destination: str`}



Syntax:
```sql
clone database (str: prototype): destination = str;
```
#### `clone database scheme`

Clones the database, but clears the tables

Permission: `database.scheme`

Parameters:
`prototype: str`}

, `destination: str`}



Syntax:
```sql
clone database scheme (str: prototype): destination = str;
```
#### `clone table`

Clones the table

Permission: `table.clone`

Parameters:
`prototype: str`}

, `destination: str`}



Syntax:
```sql
clone table (str: prototype): destination = str;
```
#### `clone table scheme`

Clones the table, but deletes the records

Permission: `table.scheme`

Parameters:
`prototype: str`}

, `destination: str`}



Syntax:
```sql
clone table scheme (str: prototype): destination = str;
```
#### `create database`

Creates a database

Permission: `database.create`

Parameters:
`name: str`}



Syntax:
```sql
create database (str: name);
```
#### `create table`

Creates a table

Permission: `table.create`

Parameters:
`table: str`}

, `columns: list of column`}

, `records: list of record`}



Syntax:
```sql
create table (str: table): columns = list of column, records = list of record;
```
#### `create token`

Creates an access token

Permission: `token.create`

Parameters:
`token: str`}

, `permissions: list of str`}



Syntax:
```sql
create token (str: token): permissions = list of str;
```
#### `_debug`

Does stuff for Quark developers. Don't use.

Permission: `!unsafe.debug`

Parameters:
`json: str`}



Syntax:
```sql
_debug: json = str;
```
#### `delete column`

Deletes a column in a table

Permission: `column.delete`

Parameters:
`name: str`}

, `table: str`}



Syntax:
```sql
delete column (str: name): table = str;
```
#### `delete database`

Deletes a database

Permission: `database.delete`

Parameters:
`database: str`}



Syntax:
```sql
delete database (str: database);
```
#### `delete from`

Deletes records meet the condition

Permission: `json.delete`

Parameters:
`table: str`}

, `selector: selector`}

, `skip: int`}

, `limit: int`}



Syntax:
```sql
delete from (str: table): selector = selector, skip = int, limit = int;
```
#### `delete table`

Deletes a table

Permission: `table.delete`

Parameters:
`table: str`}



Syntax:
```sql
delete table (str: table);
```
#### `eval`

Evaluates the Quark QL entity.

Permission: `!unsafe.eval`

Parameters:
`entity: ?`}



Syntax:
```sql
eval (?: entity);
```
#### `factory reset`

Resets the server to factory settings

Permission: `!unsafe.factory-reset`

Parameters:


Syntax:
```sql
factory reset;
```
#### `grant token`

Grants a token a permission

Permission: `token.grand`

Parameters:
`token: str`}

, `permission: str`}



Syntax:
```sql
grant token (str: token): permission = str;
```
#### `insert into`

Inserts a new record to a table

Permission: `table.insert`

Parameters:
`table: str`}

, `record: record`}



Syntax:
```sql
insert into (str: table): record = record;
```
#### `list columns in`

Lists columns in a table

Permission: `column.list`

Parameters:
`table: str`}



Syntax:
```sql
list columns in (str: table);
```
#### `list databases`

Lists the databases

Permission: `databases.list`

Parameters:


Syntax:
```sql
list databases;
```
#### `list tables in`

Lists tables in a database

Permission: `table.list`

Parameters:
`database: str`}



Syntax:
```sql
list tables in (str: database);
```
#### `redefine column in`

Changes the definition of a column

Permission: `column.redefine`

Parameters:
`table: str`}

, `definition: column`}



Syntax:
```sql
redefine column in (str: table): definition = column;
```
#### `redefine permissions for token`

Defines a new set of permissions for a token

Permission: `token.redefine`

Parameters:
`token: str`}

, `permissions: list of str`}



Syntax:
```sql
redefine permissions for token (str: token): permissions = list of str;
```
#### `reload server`

Reloads the server

Permission: `server.reload`

Parameters:


Syntax:
```sql
reload server;
```
#### `rename column in`

Renames a column in a table

Permission: `column.rename`

Parameters:
`table: str`}

, `old: str`}

, `new: str`}



Syntax:
```sql
rename column in (str: table): old = str, new = str;
```
#### `rename database`

Renames a database

Permission: `database.rename`

Parameters:
`old: str`}

, `new: str`}



Syntax:
```sql
rename database: old = str, new = str;
```
#### `rename server to`

Renames the server

Permission: `server.rename`

Parameters:
`new: str`}



Syntax:
```sql
rename server to (str: new);
```
#### `rename table`

Renames a table

Permission: `table.rename`

Parameters:
`old: str`}

, `new: str`}



Syntax:
```sql
rename table: old = str, new = str;
```
#### `reorder columns in`

Changes the column order

Permission: `column.reorder`

Parameters:
`table: str`}

, `order: list of str`}



Syntax:
```sql
reorder columns in (str: table): order = list of str;
```
#### `run command`

Runs a command

Permission: `server.command`

Parameters:
`command: str`}



Syntax:
```sql
run command (str: command);
```
#### `schedule command`

Schedules a command

Permission: `server.schedule.command`

Parameters:
`command: str`}

, `period: long`}



Syntax:
```sql
schedule command (str: command): period = long;
```
#### `schedule query`

Schedules a query

Permission: `server.schedule.query`

Parameters:
`query: str`}

, `period: long`}



Syntax:
```sql
schedule query (str: query): period = long;
```
#### `select from`

Selects records from a table

Permission: `json.select`

Parameters:
`table: str`}

, `selector: selector`}

, `skip: int`}

, `limit: int`}



Syntax:
```sql
select from (str: table): selector = selector, skip = int, limit = int;
```
#### `stop server`

Stop the server

Permission: `server.stop`

Parameters:


Syntax:
```sql
stop server;
```
#### `swap columns in`

Swaps two columns in a table

Permission: `columns.swap`

Parameters:
`table: str`}

, `first: str`}

, `second: str`}



Syntax:
```sql
swap columns in (str: table): first = str, second = str;
```
#### `remove permission from token`

Removes a permission from a token

Permission: `token.remove permission`

Parameters:
`token: str`}

, `permission: str`}



Syntax:
```sql
remove permission from token (str: token): permission = str;
```
#### `_describe instructions`

Describes all the instructions. Don't use.

Permission: `any`

Parameters:


Syntax:
```sql
_describe instructions;
```
#### `_describe constructors`

Describes all the constructors. Don't use.

Permission: `any`

Parameters:


Syntax:
```sql
_describe constructors;
```
#### `_hint next elements`

Hints the next elements for editor hints. Don't use.

Permission: `any`

Parameters:
`query: str`}

, `caret position: int`}



Syntax:
```sql
_hint next elements: query = str, caret position = int;
```
#### `get server name`

Returns the server name

Permission: `any`

Parameters:


Syntax:
```sql
get server name;
```
#### `secret`

What does it do? Hm-m...

Permission: `any`

Parameters:


Syntax:
```sql
secret;
```
#### `add modifier to`

Adds a new table modifier

Permission: `table.add modifier`

Parameters:
`table: str`}

, `column: str`}

, `modifier: modifier`}



Syntax:
```sql
add modifier to (str: table): column = str, modifier = modifier;
```
#### `list plugins`

Lists loaded plugins

Permission: `server.plugins`

Parameters:


Syntax:
```sql
list plugins;
```
#### `find in`

Quickly finds a record by a single field

Permission: `table.find`

Parameters:
`table: str`}

, `finder: finder`}



Syntax:
```sql
find in (str: table): finder = finder;
```
#### `count in`

Counts records matching condition

Permission: `table.count in`

Parameters:
`table: str`}

, `selector: selector`}



Syntax:
```sql
count in (str: table): selector = selector;
```
#### `_describe modifiers of`

Shows all the modifiers of the table. Don't use

Permission: `table.describe modifiers`

Parameters:
`table: str`}



Syntax:
```sql
_describe modifiers of (str: table);
```
#### `clear scheduled commands`

Clears all the scheduled commands.

Permission: `server.schedule.command.clear`

Parameters:


Syntax:
```sql
clear scheduled commands;
```
#### `clear scheduled queries`

Clears all the scheduled queries.

Permission: `server.schedule.query.clear`

Parameters:


Syntax:
```sql
clear scheduled queries;
```
#### `get variable in`

Shows the variable value of the table

Permission: `table.variable.get`

Parameters:
`table: str`}

, `name: str`}



Syntax:
```sql
get variable in (str: table): name = str;
```
#### `set variable in`

Sets a new value for a variable in the table

Permission: `table.variable.set`

Parameters:
`table: str`}

, `name: str`}

, `value: ?`}



Syntax:
```sql
set variable in (str: table): name = str, value = ?;
```
#### `list variables in`

Shows all the variables inside the table

Permission: `table.variable.list`

Parameters:
`table: str`}



Syntax:
```sql
list variables in (str: table);
```
#### `delete variable in`

Deletes a variable in the table

Permission: `table.variable.delete`

Parameters:
`table: str`}

, `name: str`}



Syntax:
```sql
delete variable in (str: table): name = str;
```
#### `exclude from`

Excludes the records matches the finder

Permission: `table.exclude`

Parameters:
`table: str`}

, `finder: finder`}



Syntax:
```sql
exclude from (str: table): finder = finder;
```
#### `get version`

Returns Quark's version

Permission: `any`

Parameters:


Syntax:
```sql
get version;
```
#### `var`

Defines a variable

Permission: `var`

Parameters:
`name: str`}

, `value: ?`}



Syntax:
```sql
var (str: name): value = ?;
```
### Constructors
Constructors can transform entities in both your instructions and tables.
Use them for integers, strings, lists, and any other type of entities.

#### `abs: double`

Abs

Parameters:

`arg0: double`



Syntax:
```sql
double @abs(double: arg0)
```
#### `sin: double`

Sin

Parameters:

`arg0: double`



Syntax:
```sql
double @sin(double: arg0)
```
#### `cos: double`

Cos

Parameters:

`arg0: double`



Syntax:
```sql
double @cos(double: arg0)
```
#### `tan: double`

Tan

Parameters:

`arg0: double`



Syntax:
```sql
double @tan(double: arg0)
```
#### `atan2: double`

Atan2

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @atan2(double: arg0, double: arg1)
```
#### `sqrt: double`

Sqrt

Parameters:

`arg0: double`



Syntax:
```sql
double @sqrt(double: arg0)
```
#### `log: double`

Log

Parameters:

`arg0: double`



Syntax:
```sql
double @log(double: arg0)
```
#### `log10: double`

Log10

Parameters:

`arg0: double`



Syntax:
```sql
double @log10(double: arg0)
```
#### `pow: double`

Pow

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @pow(double: arg0, double: arg1)
```
#### `exp: double`

Exp

Parameters:

`arg0: double`



Syntax:
```sql
double @exp(double: arg0)
```
#### `min: double`

Min

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @min(double: arg0, double: arg1)
```
#### `max: double`

Max

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @max(double: arg0, double: arg1)
```
#### `floor: double`

Floor

Parameters:

`arg0: double`



Syntax:
```sql
double @floor(double: arg0)
```
#### `ceil: double`

Ceil

Parameters:

`arg0: double`



Syntax:
```sql
double @ceil(double: arg0)
```
#### `rint: double`

Rint

Parameters:

`arg0: double`



Syntax:
```sql
double @rint(double: arg0)
```
#### `round: int`

Round

Parameters:

`arg0: float`



Syntax:
```sql
int @round(float: arg0)
```
#### `add exact: long`

Add exact

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @add exact(long: arg0, long: arg1)
```
#### `decrement exact: int`

Decrement exact

Parameters:

`arg0: int`



Syntax:
```sql
int @decrement exact(int: arg0)
```
#### `increment exact: long`

Increment exact

Parameters:

`arg0: long`



Syntax:
```sql
long @increment exact(long: arg0)
```
#### `multiply exact: long`

Multiply exact

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @multiply exact(long: arg0, long: arg1)
```
#### `multiply high: long`

Multiply high

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @multiply high(long: arg0, long: arg1)
```
#### `unsigned multiply high: long`

Unsigned multiply high

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @unsigned multiply high(long: arg0, long: arg1)
```
#### `negate exact: int`

Negate exact

Parameters:

`arg0: int`



Syntax:
```sql
int @negate exact(int: arg0)
```
#### `subtract exact: long`

Subtract exact

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @subtract exact(long: arg0, long: arg1)
```
#### `fma: double`

Fma

Parameters:

`arg0: double`

, `arg1: double`

, `arg2: double`



Syntax:
```sql
double @fma(double: arg0, double: arg1, double: arg2)
```
#### `copy sign: double`

Copy sign

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @copy sign(double: arg0, double: arg1)
```
#### `signum: double`

Signum

Parameters:

`arg0: double`



Syntax:
```sql
double @signum(double: arg0)
```
#### `clamp: float`

Clamp

Parameters:

`arg0: float`

, `arg1: float`

, `arg2: float`



Syntax:
```sql
float @clamp(float: arg0, float: arg1, float: arg2)
```
#### `scalb: float`

Scalb

Parameters:

`arg0: float`

, `arg1: int`



Syntax:
```sql
float @scalb(float: arg0, int: arg1)
```
#### `get exponent: int`

Get exponent

Parameters:

`arg0: double`



Syntax:
```sql
int @get exponent(double: arg0)
```
#### `floor mod: int`

Floor mod

Parameters:

`arg0: int`

, `arg1: int`



Syntax:
```sql
int @floor mod(int: arg0, int: arg1)
```
#### `asin: double`

Asin

Parameters:

`arg0: double`



Syntax:
```sql
double @asin(double: arg0)
```
#### `acos: double`

Acos

Parameters:

`arg0: double`



Syntax:
```sql
double @acos(double: arg0)
```
#### `atan: double`

Atan

Parameters:

`arg0: double`



Syntax:
```sql
double @atan(double: arg0)
```
#### `cbrt: double`

Cbrt

Parameters:

`arg0: double`



Syntax:
```sql
double @cbrt(double: arg0)
```
#### ` i e e eremainder: double`

 i e e eremainder

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @ i e e eremainder(double: arg0, double: arg1)
```
#### `floor div: long`

Floor div

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @floor div(long: arg0, long: arg1)
```
#### `ceil div: long`

Ceil div

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @ceil div(long: arg0, long: arg1)
```
#### `ceil mod: long`

Ceil mod

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @ceil mod(long: arg0, long: arg1)
```
#### `power of two d: double`

Power of two d

Parameters:

`arg0: int`



Syntax:
```sql
double @power of two d(int: arg0)
```
#### `power of two f: float`

Power of two f

Parameters:

`arg0: int`



Syntax:
```sql
float @power of two f(int: arg0)
```
#### `sinh: double`

Sinh

Parameters:

`arg0: double`



Syntax:
```sql
double @sinh(double: arg0)
```
#### `cosh: double`

Cosh

Parameters:

`arg0: double`



Syntax:
```sql
double @cosh(double: arg0)
```
#### `tanh: double`

Tanh

Parameters:

`arg0: double`



Syntax:
```sql
double @tanh(double: arg0)
```
#### `hypot: double`

Hypot

Parameters:

`arg0: double`

, `arg1: double`



Syntax:
```sql
double @hypot(double: arg0, double: arg1)
```
#### `expm1: double`

Expm1

Parameters:

`arg0: double`



Syntax:
```sql
double @expm1(double: arg0)
```
#### `log1p: double`

Log1p

Parameters:

`arg0: double`



Syntax:
```sql
double @log1p(double: arg0)
```
#### `to radians: double`

To radians

Parameters:

`arg0: double`



Syntax:
```sql
double @to radians(double: arg0)
```
#### `to degrees: double`

To degrees

Parameters:

`arg0: double`



Syntax:
```sql
double @to degrees(double: arg0)
```
#### `random: double`

Random

Parameters:



Syntax:
```sql
double @random
```
#### `divide exact: int`

Divide exact

Parameters:

`arg0: int`

, `arg1: int`



Syntax:
```sql
int @divide exact(int: arg0, int: arg1)
```
#### `floor div exact: long`

Floor div exact

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @floor div exact(long: arg0, long: arg1)
```
#### `ceil div exact: long`

Ceil div exact

Parameters:

`arg0: long`

, `arg1: long`



Syntax:
```sql
long @ceil div exact(long: arg0, long: arg1)
```
#### `to int exact: int`

To int exact

Parameters:

`arg0: long`



Syntax:
```sql
int @to int exact(long: arg0)
```
#### `multiply full: long`

Multiply full

Parameters:

`arg0: int`

, `arg1: int`



Syntax:
```sql
long @multiply full(int: arg0, int: arg1)
```
#### `abs exact: long`

Abs exact

Parameters:

`arg0: long`



Syntax:
```sql
long @abs exact(long: arg0)
```
#### `ulp: double`

Ulp

Parameters:

`arg0: double`



Syntax:
```sql
double @ulp(double: arg0)
```
#### `next after: float`

Next after

Parameters:

`arg0: float`

, `arg1: double`



Syntax:
```sql
float @next after(float: arg0, double: arg1)
```
#### `next up: double`

Next up

Parameters:

`arg0: double`



Syntax:
```sql
double @next up(double: arg0)
```
#### `next down: double`

Next down

Parameters:

`arg0: double`



Syntax:
```sql
double @next down(double: arg0)
```
#### `limit: int`

Limit

Parameters:

`integer: int`

, `min: int`

, `max: int`



Syntax:
```sql
int @limit(int: integer, int: min, int: max)
```
#### `between: boolean`

Between

Parameters:

`min: int`

, `integer: int`

, `max: int`



Syntax:
```sql
boolean @between(int: min, int: integer, int: max)
```
#### `count digits: int`

Count digits

Parameters:

`integer: int`



Syntax:
```sql
int @count digits(int: integer)
```
#### `index limit: int`

Index limit

Parameters:

`integer: int`

, `length: int`



Syntax:
```sql
int @index limit(int: integer, int: length)
```
#### `positive modulus: int`

Positive modulus

Parameters:

`integer: int`

, `divisor: int`



Syntax:
```sql
int @positive modulus(int: integer, int: divisor)
```
#### `now: date`

Now

Parameters:



Syntax:
```sql
date @now
```
#### `upper: str`

The string in upper case

Parameters:

`string to uppercase: str`



Syntax:
```sql
str @upper(str: string to uppercase)
```
#### `lower: str`

The string in lower case

Parameters:

`string to lowercase: str`



Syntax:
```sql
str @lower(str: string to lowercase)
```
#### `list: list`

A new list of values

Parameters:

`values: ?`



Syntax:
```sql
list @list(? varargs: values)
```
#### `yes: boolean`

The true value

Parameters:



Syntax:
```sql
boolean @yes
```
#### `no: boolean`

The false value

Parameters:



Syntax:
```sql
boolean @no
```
#### `concat: str`

Concatenated string

Parameters:

`strings: str`



Syntax:
```sql
str @concat(str varargs: strings)
```
#### `selector: selector`

The selector

Parameters:

`selector lambda: str`



Syntax:
```sql
selector @selector(str: selector lambda)
```
#### `and: boolean`

Result of and

Parameters:

`booleans: boolean`



Syntax:
```sql
boolean @and(boolean varargs: booleans)
```
#### `capitalize: str`

Capitalized string

Parameters:

`string to capitalize: str`



Syntax:
```sql
str @capitalize(str: string to capitalize)
```
#### `invert case: str`

The string with case inverted

Parameters:

`string where to switch case: str`



Syntax:
```sql
str @invert case(str: string where to switch case)
```
#### `count: int`

Number of elements inside the list

Parameters:

`list to count elements: list of ?`



Syntax:
```sql
int @count(list of ?: list to count elements)
```
#### `digit count: int`

Count of digits of the integer

Parameters:

`integer: int`



Syntax:
```sql
int @digit count(int: integer)
```
#### `e: float`

The value of 'e' math constant

Parameters:



Syntax:
```sql
float @e
```
#### `empty list of: list`

An empty list with specified type

Parameters:

`type name of a new list: str`



Syntax:
```sql
list @empty list of(str: type name of a new list)
```
#### `ends with: boolean`

Is string ends with the suffix

Parameters:

`string: str`

, `suffix: str`



Syntax:
```sql
boolean @ends with(str: string, str: suffix)
```
#### `equals: boolean`

Are these objects equal

Parameters:

`first object: ?`

, `second object: ?`



Syntax:
```sql
boolean @equals(?: first object, ?: second object)
```
#### `from binary string: int`

The value of the binary string

Parameters:

`binary string to convert to integer: str`



Syntax:
```sql
int @from binary string(str: binary string to convert to integer)
```
#### `from hex string: int`

The value of the hex string

Parameters:

`hex string to convert to integer: str`



Syntax:
```sql
int @from hex string(str: hex string to convert to integer)
```
#### `from octal string: int`

The value of the octal string

Parameters:

`octal string to convert to integer: str`



Syntax:
```sql
int @from octal string(str: octal string to convert to integer)
```
#### `greater: boolean`

Is the first number greater than the second

Parameters:

`first number: float`

, `second number: float`



Syntax:
```sql
boolean @greater(float: first number, float: second number)
```
#### `greater or equals: boolean`

Is the first number greater than the second or equals to

Parameters:

`first number: float`

, `second number: float`



Syntax:
```sql
boolean @greater or equals(float: first number, float: second number)
```
#### `is string empty: boolean`

Is the string empty

Parameters:

`string: str`



Syntax:
```sql
boolean @is string empty(str: string)
```
#### `join: list`

A joined list

Parameters:

`first list: list of ?`

, `more lists: list of ?`



Syntax:
```sql
list @join(list of ?: first list, list of ? varargs: more lists)
```
#### `left trim: str`

The left trimmed string

Parameters:

`string to trim: str`



Syntax:
```sql
str @left trim(str: string to trim)
```
#### `length: int`

The length of the string

Parameters:

`string to count characters in: str`



Syntax:
```sql
int @length(str: string to count characters in)
```
#### `less: boolean`

Is the first number is less than the second

Parameters:

`first number: float`

, `second number: float`



Syntax:
```sql
boolean @less(float: first number, float: second number)
```
#### `less or equals: boolean`

Is the first number is less than the second or equals to

Parameters:

`first number: float`

, `second number: float`



Syntax:
```sql
boolean @less or equals(float: first number, float: second number)
```
#### `matches: boolean`

Is the string matches regex

Parameters:

`string to check: str`

, `regex lambda: str`



Syntax:
```sql
boolean @matches(str: string to check, str: regex lambda)
```
#### `reverse string: str`

The reversed string

Parameters:

`string to reverse: str`



Syntax:
```sql
str @reverse string(str: string to reverse)
```
#### `or: boolean`

The result of or

Parameters:

`booleans: boolean`



Syntax:
```sql
boolean @or(boolean varargs: booleans)
```
#### `pi: float`

The value of pi constant

Parameters:



Syntax:
```sql
float @pi
```
#### `random between: int`

The random value

Parameters:

`inclusive min: int`

, `exclusive max: int`



Syntax:
```sql
int @random between(int: inclusive min, int: exclusive max)
```
#### `repeat: str`

The repeated string

Parameters:

`string to repeat: str`

, `times to repeat: int`



Syntax:
```sql
str @repeat(str: string to repeat, int: times to repeat)
```
#### `replace: str`

The string with replaced entries

Parameters:

`string where to replace: str`

, `string to be replaced: str`

, `string to replace: str`



Syntax:
```sql
str @replace(str: string where to replace, str: string to be replaced, str: string to replace)
```
#### `right trim: str`

The right trimmed string

Parameters:

`string to trim: str`



Syntax:
```sql
str @right trim(str: string to trim)
```
#### `sort: list`

The sorted list

Parameters:

`list to sort: list`



Syntax:
```sql
list @sort(list: list to sort)
```
#### `split: list`

The string parts

Parameters:

`string to split: str`

, `delimiter: str`



Syntax:
```sql
list @split(str: string to split, str: delimiter)
```
#### `starts with: boolean`

Does the string start with the prefix

Parameters:

`string: str`

, `prefix: str`



Syntax:
```sql
boolean @starts with(str: string, str: prefix)
```
#### `string contains: boolean`

Does the string contain the entry

Parameters:

`string where to search: str`

, `searching string: str`



Syntax:
```sql
boolean @string contains(str: string where to search, str: searching string)
```
#### `to binary string: str`

The binary string

Parameters:

`integer to convert to binary string: int`



Syntax:
```sql
str @to binary string(int: integer to convert to binary string)
```
#### `to boolean: boolean`

The boolean

Parameters:

`string to convert to boolean: str`

, `default boolean value if conversation fails: boolean`



Syntax:
```sql
boolean @to boolean(str: string to convert to boolean, optional boolean: default boolean value if conversation fails)
```
#### `to float: float`

The float

Parameters:

`string to convert to float: str`

, `default float value if conversation fails: float`



Syntax:
```sql
float @to float(str: string to convert to float, optional float: default float value if conversation fails)
```
#### `to hex string: str`

The hex string

Parameters:

`integer to convert to hex string: int`



Syntax:
```sql
str @to hex string(int: integer to convert to hex string)
```
#### `to int: int`

The integer

Parameters:

`string to convert to integer: str`

, `default integer value if conversation fails: int`



Syntax:
```sql
int @to int(str: string to convert to integer, optional int: default integer value if conversation fails)
```
#### `to octal string: str`

The octal string

Parameters:

`integer to convert to octal string: int`



Syntax:
```sql
str @to octal string(int: integer to convert to octal string)
```
#### `trim: str`

The trimmed string

Parameters:

`string to trim: str`



Syntax:
```sql
str @trim(str: string to trim)
```
#### `divide: float`

Fraction

Parameters:

`number to divide: float`

, `divisor: float`



Syntax:
```sql
float @divide(float: number to divide, float: divisor)
```
#### `multiply: float`

The product

Parameters:

`numbers to multiply: float`



Syntax:
```sql
float @multiply(float varargs: numbers to multiply)
```
#### `not: boolean`

Negated value

Parameters:

`boolean to invert: boolean`



Syntax:
```sql
boolean @not(boolean: boolean to invert)
```
#### `subtract: float`

The difference

Parameters:

`number to subtract from: float`

, `numbers to subtract: float`



Syntax:
```sql
float @subtract(float: number to subtract from, float varargs: numbers to subtract)
```
#### `sum: float`

The sum

Parameters:

`numbers to sum: float`



Syntax:
```sql
float @sum(float varargs: numbers to sum)
```
#### `record: record`

The record

Parameters:

`values: ?`



Syntax:
```sql
record @record(? varargs: values)
```
#### `changer: changer`

The record changer

Parameters:

`column name to change: str`

, `changer lambda: str`



Syntax:
```sql
changer @changer(str: column name to change, str: changer lambda)
```
#### `null: any`

The null constant

Parameters:

`type of null: str`



Syntax:
```sql
nullable any @null(optional str: type of null)
```
#### `generator: generator`

The field generator

Parameters:

`lambda: str`



Syntax:
```sql
generator @generator(str: lambda)
```
#### `finder: finder`

The finder

Parameters:

`column name: str`

, `finding object: ?`



Syntax:
```sql
finder @finder(str: column name, ?: finding object)
```
#### `date from stamp: date`

The date from unix timestamp

Parameters:

`unix time stamp: long`



Syntax:
```sql
date @date from stamp(long: unix time stamp)
```
#### `milliseconds: long`

The amount of milliseconds

Parameters:

`milliseconds: long`



Syntax:
```sql
long @milliseconds(long: milliseconds)
```
#### `seconds: long`

The amount of milliseconds

Parameters:

`seconds: long`



Syntax:
```sql
long @seconds(long: seconds)
```
#### `minutes: long`

The amount of milliseconds

Parameters:

`minutes: long`



Syntax:
```sql
long @minutes(long: minutes)
```
#### `hours: long`

The amount of milliseconds

Parameters:

`hours: long`



Syntax:
```sql
long @hours(long: hours)
```
#### `days: long`

The amount of milliseconds

Parameters:

`days: long`



Syntax:
```sql
long @days(long: days)
```
#### `weeks: long`

The amount of milliseconds

Parameters:

`weeks: long`



Syntax:
```sql
long @weeks(long: weeks)
```
#### `months: long`

The amount of milliseconds

Parameters:

`months: long`



Syntax:
```sql
long @months(long: months)
```
#### `years: long`

The amount of milliseconds

Parameters:

`years: long`



Syntax:
```sql
long @years(long: years)
```
#### `date from format: date`

The date from the format passed in

Parameters:

`date format: str`

, `formatted date: str`



Syntax:
```sql
date @date from format(str: date format, str: formatted date)
```
#### `millisecond: long`

1 millisecond

Parameters:



Syntax:
```sql
long @millisecond
```
#### `second: long`

Milliseconds in 1 second

Parameters:



Syntax:
```sql
long @second
```
#### `minute: long`

Milliseconds in 1 minute

Parameters:



Syntax:
```sql
long @minute
```
#### `hour: long`

Milliseconds in 1 hour

Parameters:



Syntax:
```sql
long @hour
```
#### `day: long`

Milliseconds in 1 day

Parameters:



Syntax:
```sql
long @day
```
#### `week: long`

Milliseconds in 1 week

Parameters:



Syntax:
```sql
long @week
```
#### `month: long`

Milliseconds in 1 month

Parameters:



Syntax:
```sql
long @month
```
#### `year: long`

Milliseconds in 1 year

Parameters:



Syntax:
```sql
long @year
```
#### `cast: any`

Casted entity

Parameters:

`casting entity: ?`

, `cast type: str`



Syntax:
```sql
any @cast(?: casting entity, str: cast type)
```
#### `var: any`

The value of the variable

Parameters:

`variable name: str`



Syntax:
```sql
any @var(str: variable name)
```
#### `require between: modifier`

The column modifier

Parameters:

`min: float`

, `max: float`



Syntax:
```sql
modifier @require between(float: min, float: max)
```
#### `require constant: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @require constant
```
#### `incrementing: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @incrementing
```
#### `require negative: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @require negative
```
#### `require not negative: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @require not negative
```
#### `positive: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @positive
```
#### `require not positive: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @require not positive
```
#### `alpha dash dot underscore: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dash dot underscore
```
#### `alpha dash: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dash
```
#### `alpha dash numeric: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dash numeric
```
#### `alpha dash underscore: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dash underscore
```
#### `alpha dot: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dot
```
#### `alpha dot underscore: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha dot underscore
```
#### `alpha underscore: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @alpha underscore
```
#### `email: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @email
```
#### `not blank: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @not blank
```
#### `required: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @required
```
#### `regex: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @regex
```
#### `hex color: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @hex color
```
#### `url: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @url
```
#### `unique: modifier`

The column modifier

Parameters:



Syntax:
```sql
modifier @unique
```
#### `default: modifier`

The column modifier

Parameters:

`default value: ?`



Syntax:
```sql
modifier @default(?: default value)
```
#### `id: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @id(str: column name, modifier varargs: modifiers)
```
#### `str: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @str(str: column name, modifier varargs: modifiers)
```
#### `int: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @int(str: column name, modifier varargs: modifiers)
```
#### `boolean: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @boolean(str: column name, modifier varargs: modifiers)
```
#### `float: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @float(str: column name, modifier varargs: modifiers)
```
#### `date: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @date(str: column name, modifier varargs: modifiers)
```
#### `long: column`

The column description

Parameters:

`column name: str`

, `modifiers: modifier`



Syntax:
```sql
column @long(str: column name, modifier varargs: modifiers)
```
### Commands
Commands helps with server interaction.

#### `/exit`

Stops the server so you will need to start it manually by launching 'Start Server.bat'. Note that ongoing processes will be halted.

Parameters:



Syntax:
```sql
exit
```
#### `/help`

Opens up the help menu contains all the commands with descriptions and syntax

Parameters:

`command: STRING` - If you need a help with some command (for example, you don't know the argument names or the command purpose), just add a command name to display the help

, `constructor: STRING` - If you need a help with some constructor. just add the constructor name



Syntax:
```sql
help command <A command you need help with> constructor <A constructor you need help with>
```
#### `/change-log-level`

Takes a module with the name passed with 'for' argument and changes it's minimal log level to 'to' argument

Parameters:

`for: STRING` - A module that the minimal logging level should be changed for

, `to: STRING` - A minimal logging level required to log



Syntax:
```sql
change-log-level for [module name] to [log level]
```
#### `/open-debug`

Opens up a debug dialog 'named'

Parameters:

`for: STRING` - The name of debug dialog you want to open



Syntax:
```sql
open-debug for [The name of debug dialog]
```
#### `/enable-debug`

Enables the debug logging for a module with name passed in the 'for' argument

Parameters:

`for: STRING` - A name of the module which log level should be switched to 'debug'



Syntax:
```sql
enable-debug for [The module name]
```
#### `/disable-debug`

Disables the debug for a module with name passed as the 'for' argument

Parameters:

`for: STRING` - The module name which log level should be switched back to 'info'



Syntax:
```sql
disable-debug for [The module name]
```
#### `/constructors`

Shows all the constructors available in this version of Quark QL

Parameters:



Syntax:
```sql
constructors
```
#### `/instructions`

Shows all the instructions available in this version of Quark QL

Parameters:



Syntax:
```sql
instructions
```
#### `/run`

Runs an instruction or a command

Parameters:

`instruction: STRING` - An instruction that should be run

, `command: STRING` - A command that should be run



Syntax:
```sql
run instruction <An instruction to run> command <A command to run>
```
#### `/test`

Runs the small developers tests for Quark developers.

Parameters:

`for: STRING` - A name of the test to be run

, `json: STRING` - A json for testing code. The usage of json passed differs from test to test.



Syntax:
```sql
test for [The test name] json <The test json>
```
#### `/eval`

Evaluates a Quark QL entity

Parameters:

`expression: STRING` - An expression to evaluate



Syntax:
```sql
eval expression [An expression]
```
#### `/modifiers`

Shows all the column modifiers provided by Quark

Parameters:



Syntax:
```sql
modifiers
```
#### `/install`

Creates all the necessary databases and tables for Quark Server to work.
This command must never be called manually.


Parameters:



Syntax:
```sql
install
```
#### `/factory-reset`

Resets all the settings and databases to the before-use state.

Parameters:



Syntax:
```sql
factory-reset
```
#### `/repair`

Scans the Quark Server system files and creates them if needed.

Parameters:



Syntax:
```sql
repair
```
#### `/clear-maven-output`

Removes all the unnecessary files generated by Maven

Parameters:



Syntax:
```sql
clear-maven-output
```
#### `/list-scheduled-tasks`

Shows the list of all the scheduled tasks: queries and commands.

Parameters:



Syntax:
```sql
list-scheduled-tasks
```
#### `/reload`

Stops the Quark Server and starts it again. All the plugins will also be reloaded.

Parameters:



Syntax:
```sql
reload
```
#### `/clear`

Clears the server's console

Parameters:



Syntax:
```sql
clear
```
#### `/list`

Lists objects of type you specify

Parameters:

`of: STRING` - Type of objects you want to specify



Syntax:
```sql
list of [Object type]
```
#### `/format`

Formats the instruction that passed in

Parameters:

`instruction: STRING` - The instruction you want to be formatted



Syntax:
```sql
format instruction [The formatting instruction]
```
#### `//`

Reruns the last command with suggested name

Parameters:



Syntax:
```sql
/
```
#### `/clean-mode`

When Clean mode is enabled, the console is cleared after each command

Parameters:



Syntax:
```sql
clean-mode
```


