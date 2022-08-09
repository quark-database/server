package ru.anafro.quark.server.databases.instructions.parser;

import ru.anafro.quark.server.databases.Instruction;
import ru.anafro.quark.server.databases.InstructionArguments;
import ru.anafro.quark.server.databases.instructions.*;
import ru.anafro.quark.server.databases.instructions.exceptions.InstructionSyntaxException;
import ru.anafro.quark.server.databases.instructions.lexer.InstructionToken;
import ru.anafro.quark.server.databases.instructions.parser.states.ExpectingInstructionNameInstructionParserState;
import ru.anafro.quark.server.databases.instructions.parser.states.InstructionParserState;
import ru.anafro.quark.server.utils.containers.UniqueList;
import ru.anafro.quark.server.utils.strings.StringSimilarityFinder;

import java.util.ArrayList;

import static ru.anafro.quark.server.utils.strings.Wrapper.quoted;

public class InstructionParser {
    private InstructionParserState state = new ExpectingInstructionNameInstructionParserState(this);
    private String instructionName;
    private final InstructionArguments arguments = new InstructionArguments();
    private final UniqueList<Instruction> registeredInstructions = new UniqueList<>();

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
                                        .asList()
                                        .stream()
                                        .sorted((a, b) -> StringSimilarityFinder.compare(instructionName, a.getName(), b.getName()))
                                        .map(Instruction::getName)
                                        .findFirst()
                                        .orElse("<No instruction to suggest>")
                        )
                )
        );
    }

    public void parse(ArrayList<InstructionToken> tokens) {
        for(var token : tokens) {
            state.handleToken(token);
        }
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
}
