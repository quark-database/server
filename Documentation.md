### `add column`

Adds a new column to a table

Permission: `column.add`

Syntax:
```sql
<blue>add column</> <gray>(</><blue>column</>: definition<gray>)</>: table = <blue>str</>, generator = <blue>generator</>;
```

### `change in`

Changes records match the condition

Permission: `table.change`

Syntax:
```sql
<blue>change in</> <gray>(</><blue>str</>: table<gray>)</>: selector = <blue>selector</>, changer = <blue>changer</>;
```

### `change port to`

Changes the server port

Permission: `server.port.change`

Syntax:
```sql
<blue>change port to</> <gray>(</><blue>int</>: port<gray>)</>;
```

### `clear database`

Deletes all the tables inside the database

Permission: `database.clear`

Syntax:
```sql
<blue>clear database</> <gray>(</><blue>str</>: database<gray>)</>;
```

### `clear table`

Removes all the records from the table

Permission: `table.clear`

Syntax:
```sql
<blue>clear table</> <gray>(</><blue>str</>: table<gray>)</>;
```

### `clone database`

Copies the database with all the contents

Permission: `database.clone`

Syntax:
```sql
<blue>clone database</> <gray>(</><blue>str</>: prototype<gray>)</>: destination = <blue>str</>;
```

### `clone database scheme`

Clones the database, but clears the tables

Permission: `database.scheme`

Syntax:
```sql
<blue>clone database scheme</> <gray>(</><blue>str</>: prototype<gray>)</>: destination = <blue>str</>;
```

### `clone table`

Clones the table

Permission: `table.clone`

Syntax:
```sql
<blue>clone table</> <gray>(</><blue>str</>: prototype<gray>)</>: destination = <blue>str</>;
```

### `clone table scheme`

Clones the table, but deletes the records

Permission: `table.scheme`

Syntax:
```sql
<blue>clone table scheme</> <gray>(</><blue>str</>: prototype<gray>)</>: destination = <blue>str</>;
```

### `create database`

Creates a database

Permission: `database.create`

Syntax:
```sql
<blue>create database</> <gray>(</><blue>str</>: name<gray>)</>;
```

### `create table`

Creates a table

Permission: `table.create`

Syntax:
```sql
<blue>create table</> <gray>(</><blue>str</>: table<gray>)</>: columns = <blue>list of column</>, records = <blue>list of record</>;
```

### `create token`

Creates an access token

Permission: `token.create`

Syntax:
```sql
<blue>create token</> <gray>(</><blue>str</>: token<gray>)</>: permissions = <blue>list of str</>;
```

### `_debug`

Does stuff for Quark developers. Don't use.

Permission: `!unsafe.debug`

Syntax:
```sql
<blue>_debug</>: json = <blue>str</>;
```

### `delete column`

Deletes a column in a table

Permission: `column.delete`

Syntax:
```sql
<blue>delete column</> <gray>(</><blue>str</>: name<gray>)</>: table = <blue>str</>;
```

### `delete database`

Deletes a database

Permission: `database.delete`

Syntax:
```sql
<blue>delete database</> <gray>(</><blue>str</>: database<gray>)</>;
```

### `delete from`

Deletes records meet the condition

Permission: `json.delete`

Syntax:
```sql
<blue>delete from</> <gray>(</><blue>str</>: table<gray>)</>: selector = <blue>selector</>, skip = <blue>int</>, limit = <blue>int</>;
```

### `delete table`

Deletes a table

Permission: `table.delete`

Syntax:
```sql
<blue>delete table</> <gray>(</><blue>str</>: table<gray>)</>;
```

### `eval`

Evaluates the Quark QL entity.

Permission: `!unsafe.eval`

Syntax:
```sql
<blue>eval</> <gray>(</><blue>?</>: entity<gray>)</>;
```

### `factory reset`

Resets the server to factory settings

Permission: `!unsafe.factory-reset`

Syntax:
```sql
<blue>factory reset</>;
```

### `grant token`

Grants a token a permission

Permission: `token.grand`

Syntax:
```sql
<blue>grant token</> <gray>(</><blue>str</>: token<gray>)</>: permission = <blue>str</>;
```

### `insert into`

Inserts a new record to a table

Permission: `table.insert`

Syntax:
```sql
<blue>insert into</> <gray>(</><blue>str</>: table<gray>)</>: record = <blue>record</>;
```

### `list columns in`

Lists columns in a table

Permission: `column.list`

Syntax:
```sql
<blue>list columns in</> <gray>(</><blue>str</>: table<gray>)</>;
```

### `list databases`

Lists the databases

Permission: `databases.list`

Syntax:
```sql
<blue>list databases</>;
```

### `list tables in`

Lists tables in a database

Permission: `table.list`

Syntax:
```sql
<blue>list tables in</> <gray>(</><blue>str</>: database<gray>)</>;
```

### `redefine column in`

Changes the definition of a column

Permission: `column.redefine`

Syntax:
```sql
<blue>redefine column in</> <gray>(</><blue>str</>: table<gray>)</>: definition = <blue>column</>;
```

### `redefine permissions for token`

Defines a new set of permissions for a token

Permission: `token.redefine`

Syntax:
```sql
<blue>redefine permissions for token</> <gray>(</><blue>str</>: token<gray>)</>: permissions = <blue>list of str</>;
```

### `reload server`

Reloads the server

Permission: `server.reload`

Syntax:
```sql
<blue>reload server</>;
```

### `rename column in`

Renames a column in a table

Permission: `column.rename`

Syntax:
```sql
<blue>rename column in</> <gray>(</><blue>str</>: table<gray>)</>: old = <blue>str</>, new = <blue>str</>;
```

### `rename database`

Renames a database

Permission: `database.rename`

Syntax:
```sql
<blue>rename database</>: old = <blue>str</>, new = <blue>str</>;
```

### `rename server to`

Renames the server

Permission: `server.rename`

Syntax:
```sql
<blue>rename server to</> <gray>(</><blue>str</>: new<gray>)</>;
```

### `rename table`

Renames a table

Permission: `table.rename`

Syntax:
```sql
<blue>rename table</>: old = <blue>str</>, new = <blue>str</>;
```

### `reorder columns in`

Changes the column order

Permission: `column.reorder`

Syntax:
```sql
<blue>reorder columns in</> <gray>(</><blue>str</>: table<gray>)</>: order = <blue>list of str</>;
```

### `run command`

Runs a command

Permission: `server.command`

Syntax:
```sql
<blue>run command</> <gray>(</><blue>str</>: command<gray>)</>;
```

### `schedule command`

Schedules a command

Permission: `server.schedule.command`

Syntax:
```sql
<blue>schedule command</> <gray>(</><blue>str</>: command<gray>)</>: period = <blue>long</>;
```

### `schedule query`

Schedules a query

Permission: `server.schedule.query`

Syntax:
```sql
<blue>schedule query</> <gray>(</><blue>str</>: query<gray>)</>: period = <blue>long</>;
```

### `select from`

Selects records from a table

Permission: `json.select`

Syntax:
```sql
<blue>select from</> <gray>(</><blue>str</>: table<gray>)</>: selector = <blue>selector</>, skip = <blue>int</>, limit = <blue>int</>;
```

### `stop server`

Stop the server

Permission: `server.stop`

Syntax:
```sql
<blue>stop server</>;
```

### `swap columns in`

Swaps two columns in a table

Permission: `columns.swap`

Syntax:
```sql
<blue>swap columns in</> <gray>(</><blue>str</>: table<gray>)</>: first = <blue>str</>, second = <blue>str</>;
```

### `remove permission from token`

Removes a permission from a token

Permission: `token.remove permission`

Syntax:
```sql
<blue>remove permission from token</> <gray>(</><blue>str</>: token<gray>)</>: permission = <blue>str</>;
```

### `_describe instructions`

Describes all the instructions. Don't use.

Permission: `any`

Syntax:
```sql
<blue>_describe instructions</>;
```

### `_describe constructors`

Describes all the constructors. Don't use.

Permission: `any`

Syntax:
```sql
<blue>_describe constructors</>;
```

### `_hint next elements`

Hints the next elements for editor hints. Don't use.

Permission: `any`

Syntax:
```sql
<blue>_hint next elements</>: query = <blue>str</>, caret position = <blue>int</>;
```

### `get server name`

Returns the server name

Permission: `any`

Syntax:
```sql
<blue>get server name</>;
```

### `secret`

What does it do? Hm-m...

Permission: `any`

Syntax:
```sql
<blue>secret</>;
```

### `add modifier to`

Adds a new table modifier

Permission: `table.add modifier`

Syntax:
```sql
<blue>add modifier to</> <gray>(</><blue>str</>: table<gray>)</>: column = <blue>str</>, modifier = <blue>modifier</>;
```

### `list plugins`

Lists loaded plugins

Permission: `server.plugins`

Syntax:
```sql
<blue>list plugins</>;
```

### `find in`

Quickly finds a record by a single field

Permission: `table.find`

Syntax:
```sql
<blue>find in</> <gray>(</><blue>str</>: table<gray>)</>: finder = <blue>finder</>;
```

### `count in`

Counts records matching condition

Permission: `table.count in`

Syntax:
```sql
<blue>count in</> <gray>(</><blue>str</>: table<gray>)</>: selector = <blue>selector</>;
```

### `_describe modifiers of`

Shows all the modifiers of the table. Don't use

Permission: `table.describe modifiers`

Syntax:
```sql
<blue>_describe modifiers of</> <gray>(</><blue>str</>: table<gray>)</>;
```

### `clear scheduled commands`

Clears all the scheduled commands.

Permission: `server.schedule.command.clear`

Syntax:
```sql
<blue>clear scheduled commands</>;
```

### `clear scheduled queries`

Clears all the scheduled queries.

Permission: `server.schedule.query.clear`

Syntax:
```sql
<blue>clear scheduled queries</>;
```

### `get variable in`

Shows the variable value of the table

Permission: `table.variable.get`

Syntax:
```sql
<blue>get variable in</> <gray>(</><blue>str</>: table<gray>)</>: name = <blue>str</>;
```

### `set variable in`

Sets a new value for a variable in the table

Permission: `table.variable.set`

Syntax:
```sql
<blue>set variable in</> <gray>(</><blue>str</>: table<gray>)</>: name = <blue>str</>, value = <blue>?</>;
```

### `list variables in`

Shows all the variables inside the table

Permission: `table.variable.list`

Syntax:
```sql
<blue>list variables in</> <gray>(</><blue>str</>: table<gray>)</>;
```

### `delete variable in`

Deletes a variable in the table

Permission: `table.variable.delete`

Syntax:
```sql
<blue>delete variable in</> <gray>(</><blue>str</>: table<gray>)</>: name = <blue>str</>;
```

### `exclude from`

Excludes the records matches the finder

Permission: `table.exclude`

Syntax:
```sql
<blue>exclude from</> <gray>(</><blue>str</>: table<gray>)</>: finder = <blue>finder</>;
```

### `get version`

Returns Quark's version

Permission: `any`

Syntax:
```sql
<blue>get version</>;
```

### `var`

Defines a variable

Permission: `var`

Syntax:
```sql
<blue>var</> <gray>(</><blue>str</>: name<gray>)</>: value = <blue>?</>;
```

