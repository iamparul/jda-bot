package org.bot.command.commands;

import org.bot.command.CommandContext;
import org.bot.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue(
                (ping)->ctx.getChannel()
                .sendMessageFormat("Reset Ping: %sms\nWs Ping: %sms",ping,jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Ping Help Description!!";
    }
}
