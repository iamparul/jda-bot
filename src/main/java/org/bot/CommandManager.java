package org.bot;

import org.bot.command.CommandContext;
import org.bot.command.ICommand;
import org.bot.command.commands.PingCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager() {
        addCommand(new PingCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound;
        nameFound = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound) {
            throw new IllegalArgumentException("A Command with this name is all present");
        }
        commands.add(cmd);

    }

    public List<ICommand> getCommands(){
        return commands;
    }

    @Nullable
    public ICommand getCommand(String search) {
        String searchLower = search.toLowerCase();
        for (ICommand command : this.commands) {
            if (command.getName().equals(searchLower) || command.getAliases().contains(searchLower)) {
                return command;
            }
        }
        return null;
    }

    void handle(GuildMessageReceivedEvent event) {
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(PropertyReader.getInstance().getProperty("prefix")), "")
                .split("\\s");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext context = new CommandContext(event, args);
            cmd.handle(context);
        }
    }

}
