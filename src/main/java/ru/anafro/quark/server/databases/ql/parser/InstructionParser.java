package ru.anafro.quark.server.databases.ql.parser;

import ru.anafro.quark.server.databases.ql.Instruction;
import ru.anafro.quark.server.databases.ql.InstructionArguments;
import ru.anafro.quark.server.databases.ql.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.ql.instructions.*;
import ru.anafro.quark.server.databases.ql.lexer.InstructionToken;
import ru.anafro.quark.server.databases.ql.parser.states.ExpectingInstructionNameInstructionParserState;
import ru.anafro.quark.server.databases.ql.parser.states.InstructionParserState;
import ru.anafro.quark.server.logging.Logger;
import ru.anafro.quark.server.utils.containers.UniqueList;
import ru.anafro.quark.server.utils.objects.Nulls;
import ru.anafro.quark.server.utils.strings.TextBuffer;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

import java.util.ArrayList;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionParser {
    private InstructionParserState state = new ExpectingInstructionNameInstructionParserState(this);
    private String instructionName = null;
    private InstructionArguments arguments = new InstructionArguments();
    private final UniqueList<Instruction> registeredInstructions = new UniqueList<>();
    private ArrayList<InstructionToken> tokens = new ArrayList<>();
    private final Logger logger = new Logger(this.getClass());
    private int tokenIndex = 0;

    public InstructionParser() {
        registerInstruction(new AddColumnInstruction());
        registerInstruction(new ChangeInInstruction());
        registerInstruction(new ChangePortToInstruction());
        registerInstruction(new ClearDatabaseInstruction());
        registerInstruction(new ClearTableInstruction());
        registerInstruction(new CloneDatabaseInstruction());
        registerInstruction(new CloneTableInstruction());
        registerInstruction(new CloneTableSchemeInstruction());
        registerInstruction(new CreateDatabaseInstruction());
        registerInstruction(new CreateTableInstruction());
        registerInstruction(new CreateTokenInstruction());
        registerInstruction(new DeleteColumnInstruction());
        registerInstruction(new DeleteDatabaseInstruction());
        registerInstruction(new DeleteFromInstruction());
        registerInstruction(new DeleteTableInstruction());
        registerInstruction(new DoNothingInstruction());
        registerInstruction(new FactoryResetInstruction());
        registerInstruction(new GrandTokenInstruction());
        registerInstruction(new InsertIntoInstruction());
        registerInstruction(new ListColumnsInstruction());
        registerInstruction(new ListDatabasesInstruction());
        registerInstruction(new ListTablesInstruction());
        registerInstruction(new RedefineColumnInstruction());
        registerInstruction(new RegrandTokenInstruction());
        registerInstruction(new ReloadServerInstruction());
        registerInstruction(new RenameColumnInstruction());
        registerInstruction(new RenameDatabaseInstruction());
        registerInstruction(new RenameServerInstruction());
        registerInstruction(new RenameTableInstruction());
        registerInstruction(new ReorderColumnsInstruction());
        registerInstruction(new RunCommandInstruction());
        registerInstruction(new ScheduleCommandInstruction());
        registerInstruction(new ScheduleQueryInstruction());
        registerInstruction(new SelectFromInstruction());
        registerInstruction(new StopServerInstruction());
        registerInstruction(new SwapColumnsInstruction());
        registerInstruction(new UngrandTokenInstruction());
    }

    public Instruction getInstruction() {
        for(var instruction : registeredInstructions) {
            if(instruction.getName().equals(instructionName)) {
                return instruction;
            }
        }

        throw new InstructionSyntaxException(
                this,
                "No such instruction with name " + quoted(instructionName),
                "Did you mean %s?".formatted(
                        quoted(
                                registeredInstructions
                                        .asList()                                                                                       //  TODO
                                        .stream()                                                                                       //  TODO
                                        .sorted((a, b) -> StringSimilarityFinder.compare(instructionName, a.getName(), b.getName()))    //  TODO: Extract this to the utility
                                        .map(Instruction::getName)                                                                      //  TODO
                                        .findFirst()                                                                                    //  TODO
                                        .orElse("<No instruction to suggest>")
                        )
                )
        ); // TODO: ew, replace...
    }

    public void parse(ArrayList<InstructionToken> tokens) {
        this.state = new ExpectingInstructionNameInstructionParserState(this);
        this.instructionName = null;
        this.arguments = new InstructionArguments();
        this.tokens = tokens;
        this.tokenIndex = 0;

        logger.debug("Got %d tokens to parse".formatted(tokens.size()));

        while(hasNextToken()) { // create table @upper("users"): columns = @array(@id(), @str("name"));
            for(int index = 0; index < tokens.size(); index++) {
                var token = tokens.get(index);
                logger.debug((index == tokenIndex ? "  -> " : "    ") + token.getName() + " = " + token.getValue());
            }

            TextBuffer stateStackBuffer = new TextBuffer("State stack: ");

            var stateCaret = this.state;
            while(stateCaret != null) {
                stateStackBuffer.append(stateCaret.getClass().getSimpleName().substring(0, stateCaret.getClass().getSimpleName().length() - "InstructionParserState".length()) + " -> ");
                stateCaret = stateCaret.getPreviousState();
            }

            logger.debug(stateStackBuffer.extractContent());

            logger.debug("Instruction name: " + Nulls.nullOrDefault(instructionName, "<unset>"));
            logger.debug("Arguments: ");

            for(var argument : arguments) {
                logger.debug("\t" + argument.name() + " = (" + Nulls.evalOrDefault(argument.value(), () -> argument.value().getType(), "<null type>") + ") " + Nulls.evalOrDefault(argument.value(), () -> argument.value().getValueAsString(), "<null object>"));
            }

            logger.debug("_".repeat(50)); // TODO: change to a separate method or extract "_".repeat(..) to a constant somewhere

            state.handleToken(getCurrentToken());
            nextToken();
        }
    }

    private InstructionToken getCurrentToken() {
        return tokens.get(tokenIndex);
    }

    private boolean hasNextToken() {
        return tokenIndex < tokens.size();
    }

    private void nextToken() {
        tokenIndex++;
    }

    public void switchState(InstructionParserState state) {
        this.state = state;
    }

    public void restoreState() {
        this.state = state.getPreviousState();
    }

    public InstructionParserState getState() {
        return state;
    }

    public String getInstructionName() {
        return instructionName;
    }

    public void registerInstruction(Instruction instruction) {
        registeredInstructions.add(instruction);
    }

    public InstructionArguments getArguments() {
        return arguments;
    }

    public void setInstructionName(String instructionName) {
        this.instructionName = instructionName;
    }

    public void letTheNextStateStartFromCurrentToken() {
        tokenIndex--;
    }

    public Logger getLogger() {
        return logger;
    }
}
