package ru.anafro.quark.server.facade;

import ru.anafro.quark.server.console.*;
import ru.anafro.quark.server.console.commands.*;
import ru.anafro.quark.server.console.exceptions.CommandRuntimeException;
import ru.anafro.quark.server.console.exceptions.CommandSyntaxException;
import ru.anafro.quark.server.database.data.ColumnModifier;
import ru.anafro.quark.server.database.data.ColumnModifierList;
import ru.anafro.quark.server.database.data.Database;
import ru.anafro.quark.server.database.data.Table;
import ru.anafro.quark.server.database.data.modifiers.*;
import ru.anafro.quark.server.database.data.schemes.ScheduledCommandsTableScheme;
import ru.anafro.quark.server.database.data.schemes.ScheduledQueriesTableScheme;
import ru.anafro.quark.server.database.data.schemes.TableSchemeList;
import ru.anafro.quark.server.database.data.schemes.TokensTableScheme;
import ru.anafro.quark.server.database.exceptions.DatabaseException;
import ru.anafro.quark.server.database.exceptions.QuerySyntaxException;
import ru.anafro.quark.server.database.views.TableView;
import ru.anafro.quark.server.debug.*;
import ru.anafro.quark.server.debug.components.Debugger;
import ru.anafro.quark.server.development.HotReloadService;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.exceptions.QuarkExceptionHandler;
import ru.anafro.quark.server.language.Instruction;
import ru.anafro.quark.server.language.InstructionList;
import ru.anafro.quark.server.language.InstructionResult;
import ru.anafro.quark.server.language.constructors.*;
import ru.anafro.quark.server.language.constructors.columns.*;
import ru.anafro.quark.server.language.constructors.mapping.types.Conditions;
import ru.anafro.quark.server.language.constructors.mapping.types.Dates;
import ru.anafro.quark.server.language.constructors.modifiers.*;
import ru.anafro.quark.server.language.entities.Entity;
import ru.anafro.quark.server.language.entities.EntityConstructor;
import ru.anafro.quark.server.language.entities.NullEntity;
import ru.anafro.quark.server.language.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.language.instructions.*;
import ru.anafro.quark.server.language.types.*;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.logging.LoggingLevel;
import ru.anafro.quark.server.multithreading.ParallelServiceRunner;
import ru.anafro.quark.server.networking.Configuration;
import ru.anafro.quark.server.networking.Ports;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.plugins.Plugin;
import ru.anafro.quark.server.plugins.PluginManager;
import ru.anafro.quark.server.plugins.events.Event;
import ru.anafro.quark.server.scheduling.Scheduler;
import ru.anafro.quark.server.security.Token;
import ru.anafro.quark.server.security.TokenPermission;
import ru.anafro.quark.server.utils.exceptions.UtilityClassInstantiationException;
import ru.anafro.quark.server.utils.files.FileSystem;
import ru.anafro.quark.server.utils.hashing.HashingFunction;
import ru.anafro.quark.server.utils.hashing.HashingFunctionRegistryGroup;
import ru.anafro.quark.server.utils.hashing.integers.BitMixerHashingFunction;
import ru.anafro.quark.server.utils.hashing.integers.DefaultIntegerHashingFunction;
import ru.anafro.quark.server.utils.hashing.integers.SevenShiftsHashingFunction;
import ru.anafro.quark.server.utils.hashing.integers.ThomasWangHashingFunction;
import ru.anafro.quark.server.utils.hashing.strings.*;
import ru.anafro.quark.server.utils.integers.Integers;
import ru.anafro.quark.server.utils.product.Version;
import ru.anafro.quark.server.utils.runtime.Application;
import ru.anafro.quark.server.utils.runtime.ExitCodes;

import java.util.HashMap;
import java.util.List;

import static ru.anafro.quark.server.database.data.Table.systemTable;

/**
 * Provides the easiest way of communicating with the Quark Server by having
 * all Quark Server components in one place. Quark class also contains some
 * static methods to do the most frequent things a simpler way, such as
 * running console commands or database instructions.
 *
 * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
 * @version Quark 1.1
 * @since Quark 1.1
 */
public final class Quark {
    public static final String NAME = Quark.class.getSimpleName();

    public static final String EMAIL = "contact@anafro.ru";
    public static final boolean DEBUG = true;

    /**
     * The version of Quark.
     *
     * @since Quark 1.2
     */
    private static final Version version = new Version(3, 0, 0, "in development");
    /**
     * The default Quark logger.
     * Should be used only for debugging. Do not use it for your modules.
     * Create a new logger object instead.
     *
     * @since Quark 1.1
     */
    private static final Logger logger = new Logger(Quark.class, LoggingLevel.DEBUG);
    /**
     * The Quark plugin manager.
     *
     * @since Quark 1.1
     */
    private static final PluginManager pluginManager = new PluginManager();
    /**
     * The registry of all commands of Quark.
     *
     * @since Quark 1.1
     */
    private static final CommandList commands = new CommandList();
    /**
     * The registry of all constructors existing in Quark.
     *
     * @since Quark 1.1
     */
    private static final EntityConstructorList constructors = new EntityConstructorList();
    /**
     * The registry of all instructions of Quark.
     *
     * @since Quark 1.1
     */
    private static final InstructionList instructions = new InstructionList();
    /**
     * The registry of all column modifiers of Quark.
     *
     * @since Quark 1.1
     */
    private static final ColumnModifierList modifiers = new ColumnModifierList();
    /**
     * The registry of all types existing in Quark.
     *
     * @since Quark 1.1
     */
    private static final TypeList types = new TypeList();
    /**
     * The registry of all debug frames in Quark.
     *
     * @since Quark 1.1
     */
    private static final DebuggerList debuggers = new DebuggerList();
    /**
     * The registry group of all hashing functions registries used in Quark.
     *
     * @since Quark 1.1
     */
    private static final HashingFunctionRegistryGroup hashingFunctions = new HashingFunctionRegistryGroup();
    /**
     * The command loop reading Quark commands.
     *
     * @since Quark 1.1
     */
    private static final CommandLoop commandLoop = new CommandLoop();
    /**
     * The pool of all the scheduled tasks running
     * inside the Quark Server.
     *
     * @since Quark 1.1
     */
    private static final Scheduler scheduler = new Scheduler();
    /**
     * Contains all the default table schemes of Quark.
     *
     * @since Quark 1.1
     */
    private static final TableSchemeList schemes = new TableSchemeList();
    private static final HashMap<String, Entity> variables = new HashMap<>();
    private static boolean isInitialized = false;
    private static Configuration configuration;
    /**
     * The server of Quark.
     *
     * @since Quark 1.1
     */
    private static Server server;
    /**
     * The pool of all asynchronous services running in Quark.
     *
     * @since Quark 1.1
     */
    private static ParallelServiceRunner serviceRunner;
    /**
     * Indicates whether {@link Quark#run(String[])} was run or not.
     *
     * @since Quark 1.1
     */
    private static boolean isRun = false;
    private static final boolean isReady = false;

    static {
        initialize();
    }

    /**
     * This private constructor of Quark class <strong>MUST NOT</strong> be ever
     * called, because Quark is a utility class. Use static methods declared inside.
     *
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private Quark() {
        throw new UtilityClassInstantiationException(getClass());
    }

    public static void initialize() {
        if (isInitialized) {
            return;
        }

        isInitialized = true;

        initializeDefaultExceptionHandler();
        initializeConfiguration();
        initializeTypes();
        initializeModifiers();
        initializeConstructors();
        initializeInstructions();
        initializeCommands();
        initializeDebuggers();
        initializeHashingFunctions();
        initializeSchemes();
        repairDirectories();
    }

    /**
     * Initializes the whole Quark Server with all modules inside. It registers
     * all default console commands, Quark QL instructions and constructors.
     * It also loads up the plugins from the /Plugins folder.
     * <br>
     * Due to the fact that this method is invoked by Quark Server itself,
     * <strong>you don't have to invoke it inside your plugins</strong>.
     *
     * @param args the command line arguments. All the arguments will be run as Quark commands.
     * @throws QuarkException when this method is called twice.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static void run(String... args) {
        if (!isInitialized) {
            initialize();
        }

        if (isRun) {
            return;
        }

        isRun = true;

        initializeServer();
        initializeParallelServiceRunner();
        loadPlugins();
        enablePlugins();
        clearMavenOutput();
        runArgsCommands(args);
        runServiceRunner();
    }

    public static void clearMavenOutput() {
        FileSystem.deleteIfExists(
                "maven-status",
                "classes",
                "generated-sources",
                "generated-test-sources",
                "test-classes",
                "maven-archiver",
                "maven-status",
                "original-Quark Server.jar",
                STR."QuarkServer-\{version.getShortVersion()}-SNAPSHOT-shaded.jar"
        );
    }

    private static void initializeDefaultExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new QuarkExceptionHandler());
    }

    private static void initializeConfiguration() {
        configuration = Configuration.load("Configuration.json");
    }

    private static void initializeParallelServiceRunner() {
        serviceRunner = new ParallelServiceRunner(server, commandLoop, Application.getJarFile().makeModificationWatcherService(new HotReloadService()));
        scheduler.load();
        serviceRunner.addAll(scheduler);
    }

    private static void initializeServer() {
        server = new Server();
    }

    private static void initializeTypes() {
        types.add(
                new BooleanType(),
                new ChangerType(),
                new ColumnModifierType(),
                new ColumnType(),
                new FloatType(),
                new IntegerType(),
                new ListType(),
                new SelectorType(),
                new StringType(),
                new RecordType(),
                new GeneratorType(),
                new LongType(),
                new DateType(),
                new FinderType(),
                new DoubleType(),

                //  ?  When Java types are being cast to Quark types,
                //     the "EntityType.fromClass(Class<?>)" method
                //     searches through the list of types and,
                //     if none of types satisfies the class,
                //     the AnyType is being returned.
                //
                //  !  Because of this, keep AnyType the last
                //     in the types list.
                new NullType(),
                new AnyType()
        );
    }

    private static void initializeModifiers() {
        modifiers.add(
                new UniqueColumnModifier(),
                new IncrementingColumnModifier(),
                new PositiveColumnModifier(),
                new NotPositiveColumnModifier(),
                new NegativeColumnModifier(),
                new NotNegativeColumnModifier(),
                new BetweenColumnModifier(),
                new ConstantColumnModifier(),
                new AlphaDashDotUnderscoreColumnModifier(),
                new AlphaDashColumnModifier(),
                new AlphaDashNumericColumnModifier(),
                new AlphaDashUnderscoreColumnModifier(),
                new AlphaDotColumnModifier(),
                new AlphaDotUnderscoreColumnModifier(),
                new EmailColumnModifier(),
                new NotBlankColumnModifier(),
                new RequiredColumnModifier(),
                new HexColorColumnModifier(),
                new UrlColumnModifier(),
                new DefaultColumnModifier()
        );
    }

    private static void initializeEntityConstructors() {
        constructors.supplement(
                Math.class,
                Conditions.class,
                Integers.class,
                Dates.class
        );

        constructors.add(
                new UpperConstructor(),
                new LowerConstructor(),
                new ListConstructor(),
                new YesConstructor(),
                new NoConstructor(),
                new ConcatConstructor(),
                new SelectorConstructor(),
                new AndConstructor(),
                new CapitalizeConstructor(),
                new InvertCaseConstructor(),
                new CountConstructor(),
                new DigitCountConstructor(),
                new EConstructor(),
                new EmptyListOfConstructor(),
                new EndsWithConstructor(),
                new EqualsConstructor(),
                new FromBinaryStringConstructor(),
                new FromHexStringConstructor(),
                new FromOctalStringConstructor(),
                new GreaterConstructor(),
                new GreaterOrEqualsConstructor(),
                new IsStringEmptyConstructor(),
                new JoinConstructor(),
                new LeftTrimConstructor(),
                new LengthConstructor(),
                new LessConstructor(),
                new LessOrEqualsConstructor(),
                new MatchesConstructor(),
                new ReverseStringConstructor(),
                new OrConstructor(),
                new PiConstructor(),
                new RandomBetweenConstructor(),
                new RepeatConstructor(),
                new ReplaceConstructor(),
                new RightTrimConstructor(),
                new SortConstructor(),
                new SplitConstructor(),
                new StartsWithConstructor(),
                new StringContainsConstructor(),
                new ToBinaryStringConstructor(),
                new ToBooleanConstructor(),
                new ToFloatConstructor(),
                new ToHexStringConstructor(),
                new ToIntConstructor(),
                new ToOctalStringConstructor(),
                new TrimConstructor(),
                new DivideConstructor(),
                new MultiplyConstructor(),
                new NotConstructor(),
                new SubtractConstructor(),
                new SumConstructor(),
                new RecordConstructor(),
                new ChangerConstructor(),
                new NullConstructor(),
                new GeneratorConstructor(),
                new FinderConstructor(),
                new DateFromStampConstructor(),
                new MillisecondsConstructor(),
                new SecondsConstructor(),
                new MinutesConstructor(),
                new HoursConstructor(),
                new DaysConstructor(),
                new WeeksConstructor(),
                new MonthsConstructor(),
                new YearsConstructor(),
                new DateFromFormatConstructor(),
                new MillisecondConstructor(),
                new SecondConstructor(),
                new MinuteConstructor(),
                new HourConstructor(),
                new DayConstructor(),
                new WeekConstructor(),
                new MonthConstructor(),
                new YearConstructor(),
                new CastConstructor(),
                new VarConstructor()
        );
    }

    private static void initializeModifiersConstructors() {
        constructors.add(
                new BetweenModifierConstructor(),
                new ConstantModifierConstructor(),
                new IncrementingModifierConstructor(),
                new NegativeModifierConstructor(),
                new NotNegativeModifierConstructor(),
                new PositiveModifierConstructor(),
                new NotPositiveModifierConstructor(),
                new AlphaDashDotUnderscoreModifierConstructor(),
                new AlphaDashModifierConstructor(),
                new AlphaDashNumericModifierConstructor(),
                new AlphaDashUnderscoreModifierConstructor(),
                new AlphaDotModifierConstructor(),
                new AlphaDotUnderscoreModifierConstructor(),
                new AlphaUnderscoreModifierConstructor(),
                new EmailModifierConstructor(),
                new NotBlankModifierConstructor(),
                new RequiredModifierConstructor(),
                new RegexModifierConstructor(),
                new HexColorModifierConstructor(),
                new UrlColumnModifierConstructor(),
                new RequireUniqueModifierConstructor(),
                new DefaultModifierConstructor()
        );
    }

    private static void initializeColumnConstructors() {
        constructors.add(
                new IdColumnConstructor(),
                new StringColumnConstructor(),
                new IntegerColumnConstructor(),
                new BooleanColumnConstructor(),
                new FloatColumnConstructor(),
                new DateColumnConstructor(),
                new LongColumnConstructor()
        );
    }

    private static void initializeConstructors() {
        initializeEntityConstructors();
        initializeModifiersConstructors();
        initializeColumnConstructors();
    }

    private static void initializeInstructions() {
        instructions.add(
                new AddColumnInstruction(),
                new ChangeInInstruction(),
                new ChangePortToInstruction(),
                new ClearDatabaseInstruction(),
                new ClearTableInstruction(),
                new CloneDatabaseInstruction(),
                new CloneDatabaseSchemeInstruction(),
                new CloneTableInstruction(),
                new CloneTableSchemeInstruction(),
                new CreateDatabaseInstruction(),
                new CreateTableInstruction(),
                new CreateTokenInstruction(),
                new DebugInstruction(),
                new DeleteColumnInstruction(),
                new DeleteDatabaseInstruction(),
                new DeleteFromInstruction(),
                new DeleteTableInstruction(),
                new EvalInstruction(),
                new FactoryResetInstruction(),
                new GrantTokenInstruction(),
                new InsertIntoInstruction(),
                new ListColumnsInstruction(),
                new ListDatabasesInstruction(),
                new ListTablesInstruction(),
                new RedefineColumnInstruction(),
                new RedefinePermissionsForTokenInstruction(),
                new ReloadServerInstruction(),
                new RenameColumnInInstruction(),
                new RenameDatabaseInstruction(),
                new RenameServerInstruction(),
                new RenameTableInstruction(),
                new ReorderColumnsInstruction(),
                new RunCommandInstruction(),
                new ScheduleCommandInstruction(),
                new ScheduleQueryInstruction(),
                new SelectFromInstruction(),
                new StopServerInstruction(),
                new SwapColumnsInstruction(),
                new RemovePermissionFromTokenInstruction(),
                new DescribeInstructionsInstruction(),
                new DescribeConstructorsInstruction(),
                new HintNextElementsInstruction(),
                new GetServerNameInstruction(),
                new SecretInstruction(),
                new AddModifierInstruction(),
                new ListPluginsInstruction(),
                new FindInInstruction(),
                new CountInInstruction(),
                new DescribeModifiersOfInstruction(),
                new ClearScheduledCommandsInstruction(),
                new ClearScheduledQueriesInstruction(),
                new GetVariableInInstruction(),
                new SetVariableInInstruction(),
                new ListVariablesInInstruction(),
                new DeleteVariableInInstruction(),
                new ExcludeFromInstruction(),
                new GetVersionInstruction(),
                new VarInstruction()
        );
    }

    private static void initializeCommands() {
        commands.add(
                new ExitCommand(),
                new HelpCommand(),
                new ChangeLogLevelCommand(),
                new OpenDebugCommand(),
                new EnableDebugCommand(),
                new DisableDebugCommand(),
                new ConstructorsCommand(),
                new InstructionsCommand(),
                new RunCommand(),
                new TestCommand(),
                new EvalCommand(),
                new ModifiersCommand(),
                new InstallCommand(),
                new FactoryResetCommand(),
                new CheckIntegrityCommand(),
                new ClearMavenOutputCommand(),
                new ListScheduledTasksCommand(),
                new ReloadCommand(),
                new ClearCommand(),
                new ListCommand(),
                new FormatCommand(),
                new YepCommand(),
                new CleanModeCommand()
        );
    }

    private static void initializeDebuggers() {
        debuggers.add(
                new InstructionLexerDebugger(),
                new InstructionParserDebugger(),
                new EntityConstructorDebugger(),
                new PermissionDebugger(),
                new HashtableDebugger(),
                new TreeDebugger()
        );
    }

    private static void initializeHashingFunctions() {
        hashingFunctions.forIntegers().add(
                new DefaultIntegerHashingFunction(),
                new SevenShiftsHashingFunction(),
                new ThomasWangHashingFunction(),
                new BitMixerHashingFunction()
        );

        hashingFunctions.forStrings().add(
                new DefaultStringHashingFunction(),
                new PearsonHashingFunction(),
                new LoseLoseHashingFunction(),
                new DJB2HashingFunction(),
                new FoldHashingFunction(),
                new SDBMHashingFunction()
        );
    }

    private static void initializeSchemes() {
        schemes.add(
                new TokensTableScheme(),
                new ScheduledCommandsTableScheme(),
                new ScheduledQueriesTableScheme()
        );

        deploySchemes();
    }

    private static void deploySchemes() {
        schemes.up();
    }

    private static void loadPlugins() {
        pluginManager.load();
    }

    private static void enablePlugins() {
        pluginManager.enableAll();
    }

    private static void runServiceRunner() {
        serviceRunner.run();
    }

    /**
     * Returns the server object currently running inside Quark Server.
     *
     * @return a server that is currently receiving requests from the Internet.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    public static Server server() {
        return server;
    }

    /**
     * Returns the plugin manager of the Quark Server that handles all the plugins
     * loaded to Quark Server.
     *
     * @return the current plugin manager of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see PluginManager
     * @since Quark 1.1
     */
    public static PluginManager plugins() {
        return pluginManager;
    }

    /**
     * Returns the plugin by its name. If plugin with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the name of the plugin being searched.
     * @return The plugin object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static Plugin plugin(String name) {
        return pluginManager.get(name);
    }

    /**
     * Returns the command loop that currently running and reading command
     * from the console line.
     *
     * @return the current command loop of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandLoop
     * @since Quark 1.1
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
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandList
     * @since Quark 1.1
     */
    public static CommandList commands() {
        return commands;
    }

    /**
     * Returns the command by its name. If command with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the name of the command being searched.
     * @return The command object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static Command command(String name) {
        return commands.get(name);
    }

    /**
     * Returns the constructor registry that contains all default Quark Server
     * constructors, and also constructors that was registered by loaded
     * plugins.
     *
     * @return the current constructor registry of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see EntityConstructorList
     * @see EntityConstructor
     * @since Quark 1.1
     */
    public static EntityConstructorList constructors() {
        return constructors;
    }

    /**
     * Returns the constructor by its name. If constructor with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the name of the constructor being searched.
     * @return The constructor object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static EntityConstructor constructor(String name) {
        return constructors.get(name);
    }

    /**
     * Returns the instruction registry that contains all default Quark Server
     * constructors, and also constructors that was registered by loaded
     * plugins.
     *
     * @return the current constructor registry of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionList
     * @see Instruction
     * @since Quark 1.1
     */
    public static InstructionList instructions() {
        return instructions;
    }

    /**
     * Returns the instruction by its name. If instruction with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the name of the instruction being searched.
     * @return The instruction object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static Instruction instruction(String name) {
        return instructions.get(name);
    }

    /**
     * Runs a single instruction written in Quark QL, waits until its result
     * and returns the result of instruction execution.
     * <br><br>
     *
     * <strong>Important:</strong> a colon ";" is required at the end of instructions.
     * <br><br>
     * <p>
     * For example, inside the {@code view} variable below all the users
     * which age is greater than 18
     *
     * <pre>var view = Quark.runInstruction("select from 'users': selector = @selector(\\"@greater(:age, 18)\\");").tableView();</pre>
     * <p>
     * You can read about all the instructions by running the 'list-instructions' command inside your terminal,
     * or by opening the documentation on official GitHub project: <a href="https://github.com/anafro/quark">here</a>.
     *
     * @param query a single instruction that should be executed.
     * @return the result of instruction execution.
     * @throws InstructionSyntaxException when instruction passed is malformed.
     * @throws DatabaseException          when the database has crashed on execution,
     *                                    or the instruction execution is impossible.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionList
     * @see Instruction
     * @see TableView
     * @since Quark 1.1
     */
    public static InstructionResult query(String query) {
        var lexer = server.getLexer();
        var parser = server.getParser();
        var tokens = lexer.lex(query);
        var instruction = parser.parse(tokens);
        var arguments = parser.getArguments();

        return instruction.execute(arguments);
    }

    /**
     * Runs a console command programmatically, and waits until its completion.
     * The result will be the same as if you run the same command inside your terminal.
     * <br><br>
     * <p>
     * To run the command 'help' from the code, use the following code:
     *
     * <pre>Quark.runCommand("help");</pre>
     * <p>
     * You can read about all the commands by running the 'help' command inside your terminal,
     * or by opening the documentation on official GitHub project: <a href="https://github.com/anafro/quark">here</a>.
     * If you need the help with some command, use the command: help command "&lt;your command name&gt;".
     *
     * @param prompt a single instruction that should be executed
     * @throws CommandSyntaxException  when command syntax passed is malformed.
     * @throws CommandRuntimeException when command execution is impossible, because
     *                                 of the arguments you passed or other conditions. You can get more information
     *                                 about every situation when CommandRuntimeException can be thrown inside
     *                                 all Command class documentations.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see CommandList
     * @see CommandLoop
     * @see Command
     * @since Quark 1.1
     */
    public static void runCommand(String prompt) {
        if (prompt.isBlank()) {
            return;
        }

        var parser = commandLoop.getParser();
        var command = parser.parse(prompt);
        var arguments = parser.getArguments();

        command.run(arguments);
        Console.breakLine();
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
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Logger
     * @since Quark 1.1
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
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see InstructionList
     * @see Instruction
     * @since Quark 1.1
     */
    public static TypeList types() {
        return types;
    }

    /**
     * Returns the type by its name. If type with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the type of the instruction being searched.
     * @return The type object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static EntityType<?> type(String name) {
        return types.get(name);
    }

    /**
     * Returns the modifier registry that contains all default Quark Server
     * column modifiers, and also modifiers that was registered by loaded
     * plugins.
     *
     * @return the modifier registry of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see ColumnModifierList
     * @see ColumnModifier
     * @see ColumnModifierType
     * @see ColumnModifierConstructor
     * @since Quark 1.1
     */
    public static ColumnModifierList modifiers() {
        return modifiers;
    }

    /**
     * Returns the modifier by its name. If modifier with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the type of the modifier being searched.
     * @return The modifier object if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static ColumnModifier modifier(String name) {
        return modifiers.get(name);
    }

    /**
     * Returns the debug frame registry that contains all Quark Server
     * debug frames, and also debug frames that was registered by loaded
     * plugins.
     *
     * @return the debug frame registry of Quark Server.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @see Debugger
     * @since Quark 1.1
     */
    public static DebuggerList debuggers() {
        return debuggers;
    }

    /**
     * Returns the debug frame by its name. If debug frame with such name
     * does not exist, {@code null} will be returned.
     *
     * @param name the type of the debug frame being searched.
     * @return The debug frame if any with such name, {@code null} otherwise.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static Debugger debugger(String name) {
        return debuggers.get(name);
    }

    /**
     * Returns all the hashing functions in Quark
     * collected into the registry group.
     *
     * @return the hashing functions wrapped into the registry group.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public static HashingFunctionRegistryGroup hashingFunctions() {
        return hashingFunctions;
    }

    /**
     * Runs commands from command arguments. Should be called only once inside
     * {@link Quark#run(String[])}.
     *
     * @param arguments the command arguments.
     * @author Anatoly Frolov | Анатолий Фролов | <a href="https://anafro.ru">My website</a>
     * @since Quark 1.1
     */
    private static void runArgsCommands(String... arguments) {
        for (String argument : arguments) {
            runCommand(argument);
        }
    }

    /**
     * Resets the Quark Server to the factory settings.
     * It removes all the created tables, deletes all the access tokens
     * and creates a new one with all the permissions. Moreover, it
     * also tries to repair all the damaged files in directories and files.
     *
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static void factoryReset() {
        server.stop();

        Database.all().forEach(Database::delete);
        Quark.runCommand("install");
        System.exit(ExitCodes.OK);
    }

    /**
     * Displays the debug message in the console.
     *
     * @param message the message to display
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public static void debug(String message) {
        logger.debug(message);
    }

    /**
     * Displays the info message in the console.
     *
     * @param message the message to display
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static void info(String message) {
        logger.info(message);
    }

    /**
     * Displays the warning message in the console.
     *
     * @param message the message to display
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static void warning(String message) {
        logger.warning(message);
    }

    /**
     * Displays the error message in the console.
     *
     * @param message the message to display
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static void error(String message) {
        logger.error(message);
    }

    /**
     * Fires the event across the plugins to catch.
     *
     * @param event the event to be fired
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static void fireEvent(Event event) {
        plugins().fireEvent(event);
    }

    /**
     * Returns the string hashing function currently using inside
     * the Quark instructions. Unless the hashing function name provided
     * in the server configuration is correct, the default function
     * will be returned.
     *
     * @return the string hashing function currently using inside
     * the Quark instructions.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static HashingFunction<String> stringHashingFunction() {
        var hashingFunctionName = configuration.getStringHashingFunction();
        return hashingFunctions.forStrings().get(hashingFunctionName);
    }

    /**
     * Returns the integer hashing function currently using inside
     * the Quark instructions. Unless the hashing function name provided
     * in the server configuration is correct, the default function
     * will be returned.
     *
     * @return the integer hashing function currently using inside
     * the Quark instructions.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static HashingFunction<Integer> integerHashingFunction() {
        var hashingFunctionName = configuration.getIntegerHashingFunction();
        return hashingFunctions.forIntegers().get(hashingFunctionName);
    }

    /**
     * Returns all the default table schemes of Quark.
     *
     * @return all the default table schemes of Quark.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 1.1
     */
    public static TableSchemeList tableSchemes() {
        return schemes;
    }

    /**
     * Returns all the scheduled tasks running inside Quark.
     *
     * @return Returns all the scheduled tasks running inside Quark.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public static Scheduler scheduler() {
        return scheduler;
    }

    public static HashMap<String, Entity> variables() {
        return variables;
    }

    public static Entity variable(String name) {
        return variables.getOrDefault(name, new NullEntity());
    }

    /**
     * Runs the command script by its name. You can find
     * or create the command script in the {@code Commands/} folder.
     * To create a new command script, create a new file inside
     * the {@code Commands} folder with the following name:
     * <br><br>
     * <strong>{@code Commands/My Script.qcommandscript}</strong>
     * <br><br>
     * Paste all the commands you want to be run into this file
     * separated by new lines (it means that all the commands should be
     * at one line).
     * <br><br>
     * To run this file, use:
     * <pre>
     *     {@code
     *     Quark.runCommandScript("My Script");
     *     }
     * </pre>
     *
     * @param name the command script name.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public static void runCommandScript(String name) {
        CommandScriptFile.run(name);
    }

    /**
     * Returns the version of Quark Server.
     *
     * @return the version of Quark Server.
     * @author Anatoly Frolov <contact@anafro.ru>
     * @since Quark 2.0
     */
    public static Version version() {
        return version;
    }

    public static void install() {
        for (var scheme : schemes) {
            logger.info(STR."Deploying the '\{scheme.getTableName().getTableName()}' table...");
            scheme.up();
        }

        var token = Token.generate();

        Table.byName("Quark.Tokens").insert(token, TokenPermission.ALL_PERMISSIONS);
        logger.info(STR."The regenerated administrator access token: \{token}");
    }

    public static void repairDirectories() {
        FileSystem.createDirectoriesIfMissing(
                "Plugins",
                "Databases",
                "Libraries",
                "Commands",
                "Trash",
                "Scripts",
                "Assets"
        );
    }

    public static void setVariable(String variableName, Entity variableValue) {
        variables.put(variableName, variableValue);
    }

    public static Configuration configuration() {
        return configuration;
    }

    public static void changePort(int newPort) {
        if (Ports.isNotUsable(newPort)) {
            throw new QuerySyntaxException(STR."Port should be between \{Ports.FIRST} and \{Ports.LAST}, not \{newPort}.");
        }

        configuration.setPort(newPort);
        configuration.save();

        Quark.reload();
    }

    public static void reload() {
        Console.clear();
        System.exit(ExitCodes.RELOAD);
    }

    public static void createToken(String token, List<String> permissions) {
        repairDirectories();
        var tokens = systemTable("Tokens");

        for (var permission : permissions) {
            tokens.insert(token, permission);
        }
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static boolean isProduction() {
        return !isDebug();
    }

    public static void deleteToken(String token) {
        systemTable("Tokens").delete(record -> record.getString("token").equals(token));  // TODO: .where
    }

    public static void redefineToken(String token, List<String> permissions) {
        deleteToken(token);
        createToken(token, permissions);
    }

    public static void removePermission(String targetToken, String permission) {
        systemTable("Tokens").delete(record -> {
            if (!record.getString(targetToken).equals(targetToken)) {
                return false;
            }

            return new Token(targetToken).can(permission);
        });
    }

    public static void rename(String newName) {
        configuration.setName(newName);
        configuration.save();
    }

    public static void scheduleCommand(String command, long period) {
        systemTable("Scheduled Commands").insert(command, period);
    }

    public static void scheduleQuery(String query, long period) {
        systemTable("Scheduled Queries").insert(query, period);
    }

    public static void exit() {
        System.exit(ExitCodes.OK);
    }

    public static boolean isNotRun() {
        return !isRun;
    }
}
