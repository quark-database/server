package ru.anafro.quark.server.api;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandLoop;
import ru.anafro.quark.server.console.CommandRegistry;
import ru.anafro.quark.server.console.commands.*;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.console.exceptions.CommandSyntaxException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.databases.data.ColumnModifierRegistry;
import ru.anafro.quark.server.databases.data.modifiers.*;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionRegistry;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.entities.InstructionEntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.constructors.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.columns.IdColumnConstructor;
import ru.anafro.quark.server.databases.ql.entities.constructors.columns.IntegerColumnConstructor;
import ru.anafro.quark.server.databases.ql.entities.constructors.columns.StringColumnConstructor;
import ru.anafro.quark.server.databases.ql.entities.constructors.modifiers.*;
import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.instructions.*;
import ru.anafro.quark.server.databases.ql.types.*;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.exceptions.QuarkExceptionHandler;
import ru.anafro.quark.server.fun.Greeter;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.multithreading.AsyncServicePool;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.plugins.PluginManager;

/**
 * Provides the easiest way of communicating with the Quark Server by having
 * all Quark Server components in one place. Quark class also contains some
 * static methods to do the most frequent things a simpler way, such valueAs
 * running console commands or database instructions.
 *
 * @since  Quark 1.1
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Quark {
    private static final Server server = new Server();
    private static final Logger logger = new Logger(Quark.class);
    private static final PluginManager pluginManager = new PluginManager();
    private static final CommandRegistry commandRegistry = new CommandRegistry();
    private static final EntityConstructorRegistry constructorRegistry = new EntityConstructorRegistry();
    private static final InstructionRegistry instructionRegistry = new InstructionRegistry();
    private static final ColumnModifierRegistry modifierRegistry = new ColumnModifierRegistry();
    private static final TypeRegistry typeRegistry = new TypeRegistry();
    private static final CommandLoop commandLoop = new CommandLoop(server);
    private static final AsyncServicePool pool = new AsyncServicePool(commandLoop, server);
    private static boolean initialized = false;

    /**
     * This private constructor of Quark class <strong>MUST NOT</strong> be ever
     * called, because Quark is a utility class. Use static methods declared inside.
     *
     * @see Quark
     */
    private Quark() {
        //
    }
    /**
     * Initializes the whole Quark Server with all modules inside. It registers
     * all default console commands, Quark QL instructions and constructors.
     * It also loads up the plugins from the /Plugins folder.
     * <br>
     * Due to the fact that this method is invoked by Quark Server itself,
     * <strong>you don't have to invoke it inside your plugins</strong>.
     *
     * @throws QuarkException when this method is called twice.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Quark
     */
    public static void init() {
        Greeter.greet();

        Thread.setDefaultUncaughtExceptionHandler(new QuarkExceptionHandler());

        if(initialized) {
            throw new QuarkException("You cannot call Quark.init() twice. If you are writing a plugin, please do not call Quark.init() by yourself - it is called automatically on Quark Server start up.");
        }

        // Entity type registering
        typeRegistry.add(new BooleanType());
        typeRegistry.add(new ChangerType());
        typeRegistry.add(new ColumnModifierType());
        typeRegistry.add(new ColumnType());
        typeRegistry.add(new FloatType());
        typeRegistry.add(new IntegerType());
        typeRegistry.add(new ListType());
        typeRegistry.add(new SelectorType());
        typeRegistry.add(new StringType());

        // Modifier registering
        modifierRegistry.add(new UniqueColumnModifier());
        modifierRegistry.add(new IncrementingColumnModifier());
        modifierRegistry.add(new PositiveColumnModifier());
        modifierRegistry.add(new NotPositiveColumnModifier());
        modifierRegistry.add(new NegativeColumnModifier());
        modifierRegistry.add(new NotNegativeColumnModifier());
        modifierRegistry.add(new BetweenColumnModifier());
        modifierRegistry.add(new ConstantColumnModifier());

        // Entity constructor registering
        constructorRegistry.add(new UpperConstructor());
        constructorRegistry.add(new LowerConstructor());
        constructorRegistry.add(new ListConstructor());
        constructorRegistry.add(new YesConstructor());
        constructorRegistry.add(new NoConstructor());
        constructorRegistry.add(new ConcatConstructor());
        constructorRegistry.add(new SelectorConstructor());
        constructorRegistry.add(new AbsConstructor());
        constructorRegistry.add(new AcosConstructor());
        constructorRegistry.add(new AndConstructor());
        constructorRegistry.add(new AsinConstructor());
        constructorRegistry.add(new Atan2Constructor());
        constructorRegistry.add(new CapitalizeConstructor());
        constructorRegistry.add(new InvertCaseConstructor());
        constructorRegistry.add(new CbrtConstructor());
        constructorRegistry.add(new CeilConstructor());
        constructorRegistry.add(new CopySignConstructor());
        constructorRegistry.add(new CosConstructor());
        constructorRegistry.add(new CoshConstructor());
        constructorRegistry.add(new CountConstructor());
        constructorRegistry.add(new DigitCountConstructor());
        constructorRegistry.add(new EConstructor());
        constructorRegistry.add(new EmptyListOfConstructor());
        constructorRegistry.add(new EndsWithConstructor());
        constructorRegistry.add(new EqualsConstructor());
        constructorRegistry.add(new ExpConstructor());
        constructorRegistry.add(new ExpMinusOneConstructor());
        constructorRegistry.add(new FloorConstructor());
        constructorRegistry.add(new FloorDivConstructor());
        constructorRegistry.add(new FloorModConstructor());
        constructorRegistry.add(new FromBinaryStringConstructor());
        constructorRegistry.add(new FromHexStringConstructor());
        constructorRegistry.add(new FromOctalStringConstructor());
        constructorRegistry.add(new GetExponentConstructor());
        constructorRegistry.add(new GreaterConstructor());
        constructorRegistry.add(new GreaterOrEqualsConstructor());
        constructorRegistry.add(new FastHypotConstructor());
        constructorRegistry.add(new IEEERemainderConstructor());
        constructorRegistry.add(new IsStringEmptyConstructor());
        constructorRegistry.add(new JoinConstructor());
        constructorRegistry.add(new LeftTrimConstructor());
        constructorRegistry.add(new LengthConstructor());
        constructorRegistry.add(new LessConstructor());
        constructorRegistry.add(new LessOrEqualsConstructor());
        constructorRegistry.add(new Log1PConstructor());
        constructorRegistry.add(new Log10Constructor());
        constructorRegistry.add(new LogConstructor());
        constructorRegistry.add(new MatchesConstructor());
        constructorRegistry.add(new MaxConstructor());
        constructorRegistry.add(new MinConstructor());
        constructorRegistry.add(new ReverseStringConstructor());
        constructorRegistry.add(new NextAfterConstructor());
        constructorRegistry.add(new NextDownConstructor());
        constructorRegistry.add(new NextUpConstructor());
        constructorRegistry.add(new OrConstructor());
        constructorRegistry.add(new PiConstructor());
        constructorRegistry.add(new RandomBetweenConstructor());
        constructorRegistry.add(new RepeatConstructor());
        constructorRegistry.add(new ReplaceConstructor());
        constructorRegistry.add(new RightTrimConstructor());
        constructorRegistry.add(new RintConstructor());
        constructorRegistry.add(new RoundConstructor());
        constructorRegistry.add(new ScalbConstructor());
        constructorRegistry.add(new SignumConstructor());
        constructorRegistry.add(new SinConstructor());
        constructorRegistry.add(new SortConstructor());
        constructorRegistry.add(new SplitConstructor());
        constructorRegistry.add(new StartsWithConstructor());
        constructorRegistry.add(new StringContainsConstructor());
        constructorRegistry.add(new TanConstructor());
        constructorRegistry.add(new TanhConstructor());
        constructorRegistry.add(new ToBinaryStringConstructor());
        constructorRegistry.add(new ToBooleanConstructor());
        constructorRegistry.add(new ToDegreesConstructor());
        constructorRegistry.add(new ToFloatConstructor());
        constructorRegistry.add(new ToHexStringConstructor());
        constructorRegistry.add(new ToIntConstructor());
        constructorRegistry.add(new ToOctalStringConstructor());
        constructorRegistry.add(new ToRadiansConstructor());
        constructorRegistry.add(new TrimConstructor());
        constructorRegistry.add(new UlpConstructor());

        // ...including column constructors...
        constructorRegistry.add(new IdColumnConstructor());
        constructorRegistry.add(new StringColumnConstructor());
        constructorRegistry.add(new IntegerColumnConstructor());
        // ...including column modifiers...
        constructorRegistry.add(new BetweenModifierConstructor());
        constructorRegistry.add(new ConstantModifierConstructor());
        constructorRegistry.add(new IncrementingModifierConstructor());
        constructorRegistry.add(new NegativeModifierConstructor());
        constructorRegistry.add(new NotNegativeModifierConstructor());
        constructorRegistry.add(new PositiveModifierConstructor());
        constructorRegistry.add(new NotPositiveModifierConstructor());

        // Instruction registering
        instructionRegistry.add(new AddColumnInstruction());
        instructionRegistry.add(new ChangeInInstruction());
        instructionRegistry.add(new ChangePortToInstruction());
        instructionRegistry.add(new ClearDatabaseInstruction());
        instructionRegistry.add(new ClearTableInstruction());
        instructionRegistry.add(new CloneDatabaseInstruction());
        instructionRegistry.add(new CloneTableInstruction());
        instructionRegistry.add(new CloneTableSchemeInstruction());
        instructionRegistry.add(new CreateDatabaseInstruction());
        instructionRegistry.add(new CreateTableInstruction());
        instructionRegistry.add(new CreateTokenInstruction());
        instructionRegistry.add(new DebugInstruction());
        instructionRegistry.add(new DeleteColumnInstruction());
        instructionRegistry.add(new DeleteDatabaseInstruction());
        instructionRegistry.add(new DeleteFromInstruction());
        instructionRegistry.add(new DeleteTableInstruction());
        instructionRegistry.add(new EvalInstruction());
        instructionRegistry.add(new FactoryResetInstruction());
        instructionRegistry.add(new GrandTokenInstruction());
        instructionRegistry.add(new InsertIntoInstruction());
        instructionRegistry.add(new ListColumnsInstruction());
        instructionRegistry.add(new ListDatabasesInstruction());
        instructionRegistry.add(new ListTablesInstruction());
        instructionRegistry.add(new RedefineColumnInstruction());
        instructionRegistry.add(new RegrandTokenInstruction());
        instructionRegistry.add(new ReloadServerInstruction());
        instructionRegistry.add(new RenameColumnInstruction());
        instructionRegistry.add(new RenameDatabaseInstruction());
        instructionRegistry.add(new RenameServerInstruction());
        instructionRegistry.add(new RenameTableInstruction());
        instructionRegistry.add(new ReorderColumnsInstruction());
        instructionRegistry.add(new RunCommandInstruction());
        instructionRegistry.add(new ScheduleCommandInstruction());
        instructionRegistry.add(new ScheduleQueryInstruction());
        instructionRegistry.add(new SelectFromInstruction());
        instructionRegistry.add(new StopServerInstruction());
        instructionRegistry.add(new SwapColumnsInstruction());
        instructionRegistry.add(new UngrandTokenInstruction());

        // Command registering
        commandRegistry.add(new ExitCommand());
        commandRegistry.add(new HelpCommand());
        commandRegistry.add(new ChangeLogLevelCommand());
        commandRegistry.add(new OpenDebugCommand());
        commandRegistry.add(new EnableDebugCommand());
        commandRegistry.add(new DisableDebugCommand());
        commandRegistry.add(new ConstructorsCommand());
        commandRegistry.add(new InstructionsCommand());
        commandRegistry.add(new RunCommand());
        commandRegistry.add(new TestCommand());
        commandRegistry.add(new EvalCommand());
        commandRegistry.add(new ModifiersCommand());
//        commandRegistry.add(new ReloadCommand());        TODO: Not working yet

        pluginManager.loadPlugins();
        pool.run();

        initialized = true;
    }

    /**
     * Returns the server object currently running inside Quark Server.
     *
     * @return a server that is currently receiving requests from the Internet.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static Server server() {
        return server;
    }

    /**
     * Returns the plugin manager of the Quark Server that handles all the plugins
     * loaded to Quark Server.
     *
     * @return the current plugin manager of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see PluginManager
     */
    public static PluginManager plugins() {
        return pluginManager;
    }

    /**
     * Returns the command loop that currently running and reading command
     * from the console line.
     *
     * @return the current command loop of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandLoop
     */
    public static CommandLoop commandLoop() {
        return commandLoop;
    }

    /**
     * Returns the command registry that contains all default Quark Server
     * console commands, and also commands that was registered by loaded
     * plugins.
     *
     * @return the current command registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandRegistry
     */
    public static CommandRegistry commands() {
        return commandRegistry;
    }

    /**
     * Returns the constructor registry that contains all default Quark Server
     * constructors, and also constructors that was registered by loaded
     * plugins.
     *
     * @return the current constructor registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see EntityConstructorRegistry
     * @see InstructionEntityConstructor
     */
    public static EntityConstructorRegistry constructors() {
        return constructorRegistry;
    }

    /**
     * Returns the instruction registry that contains all default Quark Server
     * constructors, and also constructors that was registered by loaded
     * plugins.
     *
     * @return the current constructor registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionRegistry
     * @see Instruction
     */
    public static InstructionRegistry instructions() {
        return instructionRegistry;
    }

    /**
     * Runs a single instruction written in Quark QL, waits until its result
     * and returns the result of instruction execution.
     * <br><br>
     *
     * <strong>Important:</strong> a colon (;) is required at the end of instructions.
     * <br><br>
     *
     * For example, inside the <code>view</code> variable below all the users
     * which age is greater than 18
     *
     * <pre>var view = Quark.runInstruction("select from 'users': if = @condition('age > 18');").tableView();</pre>
     *
     * You can read about all the instructions by running the 'list-instructions' command inside your terminal,
     * or by opening the documentation on official GitHub project: <a href="https://github.com/anafro/quark">here</a>.
     *
     * @param  instructionToRun a single instruction that should be executed.
     * @return the result of instruction execution.
     * @throws InstructionSyntaxException when instruction passed is malformed.
     * @throws DatabaseException when the database has crashed on execution,
     *         or the instruction execution is impossible.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionRegistry
     * @see Instruction
     * @see TableView
     */
    public static InstructionResult runInstruction(String instructionToRun) {
        server.getInstructionParser().parse(server.getInstructionLexer().lex(instructionToRun));

        Instruction instruction = server.getInstructionParser().getInstruction();
        InstructionArguments arguments = server.getInstructionParser().getArguments();

        return instruction.execute(arguments);
    }

    /**
     * Runs a console command programmatically, and waits until its completion.
     * The result will be the same as if you run the same command inside your terminal.
     * <br><br>
     *
     * To run the command 'help' from the code, use the following code:
     *
     * <pre>Quark.runCommand("help");</pre>
     *
     * You can read about all the commands by running the 'help' command inside your terminal,
     * or by opening the documentation on official GitHub project: <a href="https://github.com/anafro/quark">here</a>.
     * If you need the help with some command, use the command: help command "&lt;your command name&gt;".
     *
     * @param  commandToRun a single instruction that should be executed
     * @throws CommandSyntaxException when command syntax passed is malformed.
     * @throws CommandRuntimeException when command execution is impossible, because
     *         of the arguments you passed or other conditions. You can get more information
     *         about every situation when CommandRuntimeException can be thrown inside
     *         all Command class documentations.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandRegistry
     * @see CommandLoop
     * @see Command
     */
    public static void runCommand(String commandToRun) {
        if(commandToRun.isBlank()) {
            return;
        }

        commandLoop.getParser().parse(commandToRun);

        String commandName = commandLoop.getParser().getCommandName();
        CommandArguments arguments = commandLoop.getParser().getArguments();

        if(commandRegistry.has(commandName)) {
            commandRegistry.get(commandName).run(arguments);
            System.out.println();
        } else {
            throw new NoSuchCommandException(commandLoop, commandName);
        }
    }

    /**
     * Returns the general logger of Quark Server, which can be used to display
     * really important messages about the server itself.
     * <br><br>
     *
     * <strong>Please, do not use it to log from modules of your plugin, when knowing the logging source is important!</strong>
     * Instead, create a Logger class instance inside your class and log from it.
     *
     * @return a general logger of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Logger
     */
    public static Logger logger() {
        return logger;
    }

    public static TypeRegistry types() {
        return typeRegistry;
    }

    public static ColumnModifierRegistry modifiers() {
        return modifierRegistry;
    }
}
