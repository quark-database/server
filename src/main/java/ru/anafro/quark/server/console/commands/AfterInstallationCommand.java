package ru.anafro.quark.server.console.commands;

import ru.anafro.quark.server.api.Quark;
import ru.anafro.quark.server.console.Command;
import ru.anafro.quark.server.console.CommandArguments;
import ru.anafro.quark.server.databases.data.Database;
import ru.anafro.quark.server.databases.ql.entities.StringEntity;
import ru.anafro.quark.server.security.Token;
import ru.anafro.quark.server.utils.containers.UniqueList;

public class AfterInstallationCommand extends Command {
    public AfterInstallationCommand() {
        super(
                new UniqueList<>("---not-for-manual-run---after-installation"),
                "[NOT FOR MANUAL RUN] This command is run after installation of Quark.",
                "[NOT FOR MANUAL RUN] Creates all the necessary databases and tables for Quark Server to work."
        );
    }

    @Override
    public void action(CommandArguments arguments) {
        var generatedToken = Token.generate();

        if(Database.exists("Quark")) {
            Quark.runInstruction("""
                    delete database "Quark";
            """);
        }

        Quark.runInstruction("""
                create database "Quark";
        """);

        Quark.runInstruction("""
                create table "Quark.Tokens": columns = @list(@id(), @str("token"), @str("permission"));
        """);

        Quark.runInstruction("""
                insert into "Quark.Tokens": record = @record(:token, "*");
        """.replace(":token", new StringEntity(generatedToken).toInstructionForm()));

        Quark.runInstruction("""
                create table "Quark.Scheduled Commands": columns = @list(@id(), @str("command"), @long("period"));
        """);

        Quark.runInstruction("""
                create table "Quark.Scheduled Queries": columns = @list(@id(), @str("query"), @long("period"));
        """);

        Quark.logger().info("Your access token with all permissions: %s".formatted(generatedToken));
    }
}
