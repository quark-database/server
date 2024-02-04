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

### `add column`

Adds a new column to a table

Permission: `column.add`

Syntax:
```sql
add column (column: definition): table = str, generator = generator;
```

### `change in`

Changes records match the condition

Permission: `table.change`

Syntax:
```sql
change in (str: table): selector = selector, changer = changer;
```

### `change port to`

Changes the server port

Permission: `server.port.change`

Syntax:
```sql
change port to (int: port);
```

### `clear database`

Deletes all the tables inside the database

Permission: `database.clear`

Syntax:
```sql
clear database (str: database);
```

### `clear table`

Removes all the records from the table

Permission: `table.clear`

Syntax:
```sql
clear table (str: table);
```

### `clone database`

Copies the database with all the contents

Permission: `database.clone`

Syntax:
```sql
clone database (str: prototype): destination = str;
```

### `clone database scheme`

Clones the database, but clears the tables

Permission: `database.scheme`

Syntax:
```sql
clone database scheme (str: prototype): destination = str;
```

### `clone table`

Clones the table

Permission: `table.clone`

Syntax:
```sql
clone table (str: prototype): destination = str;
```

### `clone table scheme`

Clones the table, but deletes the records

Permission: `table.scheme`

Syntax:
```sql
clone table scheme (str: prototype): destination = str;
```

### `create database`

Creates a database

Permission: `database.create`

Syntax:
```sql
create database (str: name);
```

### `create table`

Creates a table

Permission: `table.create`

Syntax:
```sql
create table (str: table): columns = list of column, records = list of record;
```

### `create token`

Creates an access token

Permission: `token.create`

Syntax:
```sql
create token (str: token): permissions = list of str;
```

### `_debug`

Does stuff for Quark developers. Don't use.

Permission: `!unsafe.debug`

Syntax:
```sql
_debug: json = str;
```

### `delete column`

Deletes a column in a table

Permission: `column.delete`

Syntax:
```sql
delete column (str: name): table = str;
```

### `delete database`

Deletes a database

Permission: `database.delete`

Syntax:
```sql
delete database (str: database);
```

### `delete from`

Deletes records meet the condition

Permission: `json.delete`

Syntax:
```sql
delete from (str: table): selector = selector, skip = int, limit = int;
```

### `delete table`

Deletes a table

Permission: `table.delete`

Syntax:
```sql
delete table (str: table);
```

### `eval`

Evaluates the Quark QL entity.

Permission: `!unsafe.eval`

Syntax:
```sql
eval (?: entity);
```

### `factory reset`

Resets the server to factory settings

Permission: `!unsafe.factory-reset`

Syntax:
```sql
factory reset;
```

### `grant token`

Grants a token a permission

Permission: `token.grand`

Syntax:
```sql
grant token (str: token): permission = str;
```

### `insert into`

Inserts a new record to a table

Permission: `table.insert`

Syntax:
```sql
insert into (str: table): record = record;
```

### `list columns in`

Lists columns in a table

Permission: `column.list`

Syntax:
```sql
list columns in (str: table);
```

### `list databases`

Lists the databases

Permission: `databases.list`

Syntax:
```sql
list databases;
```

### `list tables in`

Lists tables in a database

Permission: `table.list`

Syntax:
```sql
list tables in (str: database);
```

### `redefine column in`

Changes the definition of a column

Permission: `column.redefine`

Syntax:
```sql
redefine column in (str: table): definition = column;
```

### `redefine permissions for token`

Defines a new set of permissions for a token

Permission: `token.redefine`

Syntax:
```sql
redefine permissions for token (str: token): permissions = list of str;
```

### `reload server`

Reloads the server

Permission: `server.reload`

Syntax:
```sql
reload server;
```

### `rename column in`

Renames a column in a table

Permission: `column.rename`

Syntax:
```sql
rename column in (str: table): old = str, new = str;
```

### `rename database`

Renames a database

Permission: `database.rename`

Syntax:
```sql
rename database: old = str, new = str;
```

### `rename server to`

Renames the server

Permission: `server.rename`

Syntax:
```sql
rename server to (str: new);
```

### `rename table`

Renames a table

Permission: `table.rename`

Syntax:
```sql
rename table: old = str, new = str;
```

### `reorder columns in`

Changes the column order

Permission: `column.reorder`

Syntax:
```sql
reorder columns in (str: table): order = list of str;
```

### `run command`

Runs a command

Permission: `server.command`

Syntax:
```sql
run command (str: command);
```

### `schedule command`

Schedules a command

Permission: `server.schedule.command`

Syntax:
```sql
schedule command (str: command): period = long;
```

### `schedule query`

Schedules a query

Permission: `server.schedule.query`

Syntax:
```sql
schedule query (str: query): period = long;
```

### `select from`

Selects records from a table

Permission: `json.select`

Syntax:
```sql
select from (str: table): selector = selector, skip = int, limit = int;
```

### `stop server`

Stop the server

Permission: `server.stop`

Syntax:
```sql
stop server;
```

### `swap columns in`

Swaps two columns in a table

Permission: `columns.swap`

Syntax:
```sql
swap columns in (str: table): first = str, second = str;
```

### `remove permission from token`

Removes a permission from a token

Permission: `token.remove permission`

Syntax:
```sql
remove permission from token (str: token): permission = str;
```

### `_describe instructions`

Describes all the instructions. Don't use.

Permission: `any`

Syntax:
```sql
_describe instructions;
```

### `_describe constructors`

Describes all the constructors. Don't use.

Permission: `any`

Syntax:
```sql
_describe constructors;
```

### `_hint next elements`

Hints the next elements for editor hints. Don't use.

Permission: `any`

Syntax:
```sql
_hint next elements: query = str, caret position = int;
```

### `get server name`

Returns the server name

Permission: `any`

Syntax:
```sql
get server name;
```

### `secret`

What does it do? Hm-m...

Permission: `any`

Syntax:
```sql
secret;
```

### `add modifier to`

Adds a new table modifier

Permission: `table.add modifier`

Syntax:
```sql
add modifier to (str: table): column = str, modifier = modifier;
```

### `list plugins`

Lists loaded plugins

Permission: `server.plugins`

Syntax:
```sql
list plugins;
```

### `find in`

Quickly finds a record by a single field

Permission: `table.find`

Syntax:
```sql
find in (str: table): finder = finder;
```

### `count in`

Counts records matching condition

Permission: `table.count in`

Syntax:
```sql
count in (str: table): selector = selector;
```

### `_describe modifiers of`

Shows all the modifiers of the table. Don't use

Permission: `table.describe modifiers`

Syntax:
```sql
_describe modifiers of (str: table);
```

### `clear scheduled commands`

Clears all the scheduled commands.

Permission: `server.schedule.command.clear`

Syntax:
```sql
clear scheduled commands;
```

### `clear scheduled queries`

Clears all the scheduled queries.

Permission: `server.schedule.query.clear`

Syntax:
```sql
clear scheduled queries;
```

### `get variable in`

Shows the variable value of the table

Permission: `table.variable.get`

Syntax:
```sql
get variable in (str: table): name = str;
```

### `set variable in`

Sets a new value for a variable in the table

Permission: `table.variable.set`

Syntax:
```sql
set variable in (str: table): name = str, value = ?;
```

### `list variables in`

Shows all the variables inside the table

Permission: `table.variable.list`

Syntax:
```sql
list variables in (str: table);
```

### `delete variable in`

Deletes a variable in the table

Permission: `table.variable.delete`

Syntax:
```sql
delete variable in (str: table): name = str;
```

### `exclude from`

Excludes the records matches the finder

Permission: `table.exclude`

Syntax:
```sql
exclude from (str: table): finder = finder;
```

### `get version`

Returns Quark's version

Permission: `any`

Syntax:
```sql
get version;
```

### `var`

Defines a variable

Permission: `var`

Syntax:
```sql
var (str: name): value = ?;
```



