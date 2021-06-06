package org.bot.command.commands;

import org.bot.CommandManager;
import org.bot.PropertyReader;
import org.bot.command.CommandContext;
import org.bot.command.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        System.out.println(args);
        if (args.isEmpty()){
            StringBuilder builder = new StringBuilder();
            builder.append("List of Commands\n");
            manager.getCommands().stream().map(ICommand::getHelp).forEach(
                    (it)->builder.append('`').append(PropertyReader.getInstance()
                            .getProperty("prefix")).append(it).append("`\n")
            );
            channel.sendMessage(builder.toString()).queue();
            return;
        }
        String search = args.get(0);
        ICommand command = manager.getCommand(search);
        if (command==null){
            channel.sendMessage("Nothing found for "+search).queue();
            return;
        }
        channel.sendMessage(command.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Help Description!!";
    }
}
