package ru.anafro.quark.server.api;

import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandLoop;
import ru.anafro.quark.server.console.CommandRegistry;
import ru.anafro.quark.server.console.commands.*;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.console.exceptions.CommandSyntaxException;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.databases.data.ColumnModifier;
import ru.anafro.quark.server.databases.data.ColumnModifierRegistry;
import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.data.modifiers.*;
import ru.anafro.quark.server.databases.exceptions.DatabaseException;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionRegistry;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.entities.EntityConstructor;
import ru.anafro.quark.server.databases.ql.entities.constructors.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.columns.*;
import ru.anafro.quark.server.databases.ql.entities.constructors.modifiers.*;
import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.instructions.*;
import ru.anafro.quark.server.databases.ql.types.*;
import ru.anafro.quark.server.databases.views.TableView;
import ru.anafro.quark.server.debug.*;
import ru.anafro.quark.server.debug.components.DebugFrame;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.exceptions.QuarkExceptionHandler;
import ru.anafro.quark.server.fun.Greeter;
import ru.anafro.quark.server.logging.LogLevel;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.multithreading.AsyncServicePool;
import ru.anafro.quark.server.multithreading.Threads;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.plugins.PluginManager;
import ru.anafro.quark.server.utils.exceptions.CallingUtilityConstructorException;
import ru.anafro.quark.server.utils.hashing.HashingFunctionRegistryGroup;
import ru.anafro.quark.server.utils.hashing.integers.DefaultIntegerHashingFunction;
import ru.anafro.quark.server.utils.hashing.strings.DefaultStringHashingFunction;
import ru.anafro.quark.server.utils.hashing.strings.PearsonHashingFunction;

/**
 * Provides the easiest way of communicating with the Quark Server by having
 * all Quark Server components in one place. Quark class also contains some
 * static methods to do the most frequent things a simpler way, such as
 * running console commands or database instructions.
 *
 * @since   Quark 1.1
 * @version Quark 1.1
 * @author  Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 */
public final class Quark {

    /**
     * The server of Quark.
     * @since Quark 1.1
     */
    private static final Server server = new Server();

    /**
     * The default Quark logger.
     * Should be used only for debugging. Do not use it for your modules.
     * Create a new logger object instead.
     * @since Quark 1.1
     */
    private static final Logger logger = new Logger(Quark.class, LogLevel.DEBUG);

    /**
     * The Quark plugin manager.
     * @since Quark 1.1
     */
    private static final PluginManager pluginManager = new PluginManager();

    /**
     * The registry of all commands of Quark.
     * @since Quark 1.1
     */
    private static final CommandRegistry commandRegistry = new CommandRegistry();

    /**
     * The registry of all constructors existing in Quark.
     * @since Quark 1.1
     */
    private static final EntityConstructorRegistry constructorRegistry = new EntityConstructorRegistry();

    /**
     * The registry of all instructions of Quark.
     * @since Quark 1.1
     */
    private static final InstructionRegistry instructionRegistry = new InstructionRegistry();

    /**
     * The registry of all column modifiers of Quark.
     * @since Quark 1.1
     */
    private static final ColumnModifierRegistry modifierRegistry = new ColumnModifierRegistry();

    /**
     * The registry of all types existing in Quark.
     * @since Quark 1.1
     */
    private static final TypeRegistry typeRegistry = new TypeRegistry();

    /**
     * The registry of all debug frames in Quark.
     * @since Quark 1.1
     */
    private static final DebugFrameRegistry debugFrameRegistry = new DebugFrameRegistry();

    /**
     * The registry group of all hashing functions registries used in Quark.
     * @since Quark 1.1
     */
    private static final HashingFunctionRegistryGroup hashingFunctionRegistryGroup = new HashingFunctionRegistryGroup();

    /**
     * The command loop reading Quark commands.
     * @since Quark 1.1
     */
    private static final CommandLoop commandLoop = new CommandLoop(server);

    /**
     * The pool of all asynchronous services running in Quark.
     * @since Quark 1.1
     */
    private static final AsyncServicePool pool = new AsyncServicePool(commandLoop, server);

    /**
     * Indicates whether {@link Quark#init(String[])} was run or not.
     * @since Quark 1.1
     */
    private static boolean initialized = false;

    /**
     * This private constructor of Quark class <strong>MUST NOT</strong> be ever
     * called, because Quark is a utility class. Use static methods declared inside.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private Quark() {
        throw new CallingUtilityConstructorException(getClass());
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
     * @param args the command line arguments. All the arguments will be run as Quark commands.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static void init(String[] args) {
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
        typeRegistry.add(new RecordType());
        typeRegistry.add(new AnyType());
        typeRegistry.add(new NullType());
        typeRegistry.add(new GeneratorType());
        typeRegistry.add(new LongType());
        typeRegistry.add(new DateType());

        // Modifier registering
        modifierRegistry.add(new RequireUniqueColumnModifier());
        modifierRegistry.add(new IncrementingColumnModifier());
        modifierRegistry.add(new PositiveColumnModifier());
        modifierRegistry.add(new NotPositiveColumnModifier());
        modifierRegistry.add(new NegativeColumnModifier());
        modifierRegistry.add(new NotNegativeColumnModifier());
        modifierRegistry.add(new BetweenColumnModifier());
        modifierRegistry.add(new ConstantColumnModifier());
        modifierRegistry.add(new AlphaDashDotUnderscoreModifier());
        modifierRegistry.add(new AlphaDashModifier());
        modifierRegistry.add(new AlphaDashNumericModifier());
        modifierRegistry.add(new AlphaDashUnderscoreModifier());
        modifierRegistry.add(new AlphaDotModifier());
        modifierRegistry.add(new AlphaDotUnderscoreModifier());
        modifierRegistry.add(new EmailModifier());
        modifierRegistry.add(new NotBlankModifier());
        modifierRegistry.add(new RequiredModifier());
        modifierRegistry.add(new HexColorModifier());
        modifierRegistry.add(new UrlColumnModifier());

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
        constructorRegistry.add(new DivideConstructor());
        constructorRegistry.add(new MultiplyConstructor());
        constructorRegistry.add(new NotConstructor());
        constructorRegistry.add(new SubtractConstructor());
        constructorRegistry.add(new SumConstructor());
        constructorRegistry.add(new RecordConstructor());
        constructorRegistry.add(new ChangerConstructor());
        constructorRegistry.add(new NullConstructor());
        constructorRegistry.add(new GeneratorConstructor());
        // ...including column constructors...
        constructorRegistry.add(new IdColumnConstructor());
        constructorRegistry.add(new StringColumnConstructor());
        constructorRegistry.add(new IntegerColumnConstructor());
        constructorRegistry.add(new BooleanColumnConstructor());
        constructorRegistry.add(new FloatColumnConstructor());
        constructorRegistry.add(new DateColumnConstructor());
        // ...including column modifiers...
        constructorRegistry.add(new BetweenModifierConstructor());
        constructorRegistry.add(new ConstantModifierConstructor());
        constructorRegistry.add(new IncrementingModifierConstructor());
        constructorRegistry.add(new NegativeModifierConstructor());
        constructorRegistry.add(new NotNegativeModifierConstructor());
        constructorRegistry.add(new PositiveModifierConstructor());
        constructorRegistry.add(new NotPositiveModifierConstructor());
        constructorRegistry.add(new AlphaDashDotUnderscoreModifierConstructor());
        constructorRegistry.add(new AlphaDashModifierConstructor());
        constructorRegistry.add(new AlphaDashNumericModifierConstructor());
        constructorRegistry.add(new AlphaDashUnderscoreModifierConstructor());
        constructorRegistry.add(new AlphaDotModifierConstructor());
        constructorRegistry.add(new AlphaDotUnderscoreModifierConstructor());
        constructorRegistry.add(new AlphaUnderscoreModifierConstructor());
        constructorRegistry.add(new EmailModifierConstructor());
        constructorRegistry.add(new NotBlankModifierConstructor());
        constructorRegistry.add(new RequiredModifierConstructor());
        constructorRegistry.add(new RegexModifierConstructor());
        constructorRegistry.add(new HexColorModifierConstructor());
        constructorRegistry.add(new UrlColumnModifierConstructor());
        constructorRegistry.add(new RequireUniqueModifierConstructor());
        constructorRegistry.add(new DateFromStampConstructor());
        constructorRegistry.add(new MillisecondsConstructor());
        constructorRegistry.add(new SecondsConstructor());
        constructorRegistry.add(new MinutesConstructor());
        constructorRegistry.add(new HoursConstructor());
        constructorRegistry.add(new DaysConstructor());
        constructorRegistry.add(new WeeksConstructor());
        constructorRegistry.add(new MonthsConstructor());
        constructorRegistry.add(new YearsConstructor());
        constructorRegistry.add(new DateFromFormatConstructor());

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
        instructionRegistry.add(new GrantTokenInstruction());
        instructionRegistry.add(new InsertIntoInstruction());
        instructionRegistry.add(new ListColumnsInstruction());
        instructionRegistry.add(new ListDatabasesInstruction());
        instructionRegistry.add(new ListTablesInstruction());
        instructionRegistry.add(new RedefineColumnInstruction());
        instructionRegistry.add(new RedefinePermissionsForTokenInstruction());
//        instructionRegistry.add(new ReloadServerInstruction());  TODO: Not working
        instructionRegistry.add(new RenameColumnInInstruction());
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
        instructionRegistry.add(new RemovePermissionFromTokenInstruction());
        instructionRegistry.add(new DescribeInstructionsInstruction());
        instructionRegistry.add(new DescribeConstructorsInstruction());
        instructionRegistry.add(new HintNextElementsInstruction());
        instructionRegistry.add(new GetServerNameInstruction());

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
        commandRegistry.add(new AfterInstallationCommand());
        commandRegistry.add(new FactoryResetCommand());
//        commandRegistry.add(new ReloadCommand());        TODO: Not working yet

        debugFrameRegistry.add(new InstructionLexerDebugFrame());
        debugFrameRegistry.add(new InstructionParserDebugFrame());
        debugFrameRegistry.add(new EntityConstructorDebugFrame());
        debugFrameRegistry.add(new PermissionDebugFrame());

        // Hashing functions
        hashingFunctionRegistryGroup.forIntegers().add(new DefaultIntegerHashingFunction());
        hashingFunctionRegistryGroup.forStrings().add(new DefaultStringHashingFunction());
        hashingFunctionRegistryGroup.forStrings().add(new PearsonHashingFunction());

        runCommandsFromCommandLineArguments(args);

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
     * @see EntityConstructor
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
     * For example, inside the {@code view} variable below all the users
     * which age is greater than 18
     *
     * <pre>var view = Quark.runInstruction("select from 'users': selector = @selector(\\"@greater(:age, 18)\\");").tableView();</pre>
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

    /**
     * Returns the instruction registry that contains all default Quark Server
     * constructors, and also constructors that was registered by loaded
     * plugins.
     *
     * @return the constructor registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionRegistry
     * @see Instruction
     */
    public static TypeRegistry types() {
        return typeRegistry;
    }

    /**
     * Returns the modifier registry that contains all default Quark Server
     * column modifiers, and also modifiers that was registered by loaded
     * plugins.
     *
     * @return the modifier registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see ColumnModifierRegistry
     * @see ColumnModifier
     * @see ColumnModifierType
     * @see ColumnModifierConstructor
     */
    public static ColumnModifierRegistry modifiers() {
        return modifierRegistry;
    }

    /**
     * Returns the debug frame registry that contains all Quark Server
     * debug frames, and also debug frames that was registered by loaded
     * plugins.
     *
     * @return the debug frame registry of Quark Server.
     *
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see DebugFrame
     */
    public static DebugFrameRegistry debugFrames() {
        return debugFrameRegistry;
    }

    public static HashingFunctionRegistryGroup hashingFunctions() {
        return hashingFunctionRegistryGroup;
    }

    /**
     * Stops the server due the error that prevents the normal
     * runtime process of Quark Server.
     *
     * @param message the error that will be printed.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    public static void crash(String message) {
        logger.error("Server has crashed.");
        logger.error(message);

        System.exit(-1);
    }

    /**
     * Runs commands from command arguments. Should be called only once inside
     * {@link Quark#init(String[])}.
     *
     * @param arguments the command arguments.
     * @since  Quark 1.1
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     */
    private static void runCommandsFromCommandLineArguments(String... arguments) {
        for(String argument : arguments) {
            runCommand(argument);
        }
    }

    public static void factoryReset() {
        Quark.logger().info("Quark is about to factory reset.");
        Quark.logger().info("It may take a while.");
        Quark.logger().warning("!! TO CANCEL, CTRL+C IN THE TERMINAL !!");
        Quark.logger().info("Resetting to the factory in 7 seconds");
        Quark.logger().info("After the resetting started, it CAN NOT be undone.");

        Threads.freezeFor(7.0);

        Quark.logger().info("Quark Server is resetting. ");
        Quark.logger().warning("---------------------------------------------");
        Quark.logger().warning("     <!>    DO NOT PRESS CTRL+C.    <!>      ");
        Quark.logger().warning("                                             ");
        Quark.logger().warning("  IT MAY BREAK QUARK SERVER WITH NO ABILITY  ");
        Quark.logger().warning("              TO BE REPAIRED                 ");
        Quark.logger().warning("---------------------------------------------");
        Quark.logger().info("");
        Quark.logger().info("Please, do not type any command.");

        Quark.logger().info("Stopping the server...");
        server.stop();

        Database.all().forEach(database -> {
            Quark.logger().info("Deleting database %s.".formatted(database.getName()));
            Database.delete(database.getName());

            Quark.logger().info("Successfully deleted.");
        });

        Quark.logger().info("All the databases are deleted.");
        Quark.logger().info("Running the command after installation...");

        Quark.runCommand("---not-for-manual-run---after-installation");

        Quark.logger().info("Factory resetting is completed. Stopping the Quark Server.");
        Quark.logger().info("Relaunch it, please.");

        System.exit(0);
    }

    public static void debug(String message) {
        logger.debug(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void warning(String message) {
        logger.warning(message);
    }

    public static void error(String message) {
        logger.error(message);
    }
}
