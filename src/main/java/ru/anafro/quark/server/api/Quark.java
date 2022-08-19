package ru.anafro.quark.server.api;

import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.console.CommandLoop;
import ru.anafro.quark.server.console.CommandRegistry;
import ru.anafro.quark.server.console.commands.*;
import ru.anafro.quark.server.console.exceptions.NoSuchCommandException;
import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.InstructionRegistry;
import ru.anafro.quark.server.databases.ql.InstructionResult;
import ru.anafro.quark.server.databases.ql.entities.constructors.*;
import ru.anafro.quark.server.databases.ql.instructions.*;
import ru.anafro.quark.server.exceptions.QuarkException;
import ru.anafro.quark.server.exceptions.QuarkExceptionHandler;
import ru.anafro.quark.server.fun.Greeter;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.multithreading.AsyncServicePool;
import ru.anafro.quark.server.networking.Server;
import ru.anafro.quark.server.plugins.PluginManager;

public final class Quark {
    private static final Server server = new Server();
    private static final Logger logger = new Logger(Quark.class);
    private static final PluginManager pluginManager = new PluginManager();
    private static final CommandRegistry commandRegistry = new CommandRegistry();
    private static final EntityConstructorRegistry constructorRegistry = new EntityConstructorRegistry();
    private static final InstructionRegistry instructionRegistry = new InstructionRegistry();
    private static final CommandLoop commandLoop = new CommandLoop(server);
    private static final AsyncServicePool pool = new AsyncServicePool(commandLoop, server);
    private static boolean initialized = false;

    private Quark() {
        //
    }

    public static void init() {
        Greeter.greet();

        Thread.setDefaultUncaughtExceptionHandler(new QuarkExceptionHandler());

        if(initialized) {
            throw new QuarkException("You cannot call Quark.init() twice. If you are writing a plugin, please do not call Quark.init() by yourself - it is called automatically on Quark Server start up.");
        }

        // Entity constructor registering
        constructorRegistry.add(new UpperInstructionEntityConstructor());
        constructorRegistry.add(new LowerInstructionEntityConstructor());
        constructorRegistry.add(new ListInstructionEntityConstructor());
        constructorRegistry.add(new YesInstructionEntityConstructor());
        constructorRegistry.add(new NoInstructionEntityConstructor());
        constructorRegistry.add(new ConcatInstructionEntityConstructor());
        
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
        instructionRegistry.add(new DeleteColumnInstruction());
        instructionRegistry.add(new DeleteDatabaseInstruction());
        instructionRegistry.add(new DeleteFromInstruction());
        instructionRegistry.add(new DeleteTableInstruction());
        instructionRegistry.add(new DebugInstruction());
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
//        commandRegistry.add(new ReloadCommand());        TODO: Not working yet

        pluginManager.loadPlugins();
        pool.run();

        initialized = true;
    }

    public static Server server() {
        return server;
    }

    public static PluginManager plugins() {
        return pluginManager;
    }

    public static CommandLoop commandLoop() {
        return commandLoop;
    }

    public static CommandRegistry commands() {
        return commandRegistry;
    }

    public static EntityConstructorRegistry constructors() {
        return constructorRegistry;
    }

    public static InstructionRegistry instructions() {
        return instructionRegistry;
    }

    public static InstructionResult runInstruction(String instructionToRun) {
        server.getInstructionParser().parse(server.getInstructionLexer().lex(instructionToRun));

        Instruction instruction = server.getInstructionParser().getInstruction();
        InstructionArguments arguments = server.getInstructionParser().getArguments();

        return instruction.execute(arguments);
    }

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

    public static Logger logger() {
        return logger;
    }
}
