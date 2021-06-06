package org.bot.command.commands;

import net.dv8tion.jda.api.entities.TextChannel;
import org.bot.CommandManager;
import org.bot.CryptoRequest;
import org.bot.command.CommandContext;
import org.bot.command.ICommand;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PriceCommand implements ICommand {
    private final CommandManager manager;

    public PriceCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();
        System.out.println(args);
        URL url = null;
        try {
            url = new URL("https://api.coingecko.com/api/v3/simple/price?ids="+args.get(0) + "&vs_currencies=" + args.get(1));
            String coinPrice = new CryptoRequest(url).getCoinPrice();
            channel.sendMessage(args.get(0) + " price in " + args.get(1) + " is " + coinPrice).queue();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "price";
    }

    @Override
    public String getHelp() {
        return "Get the prince of any Crypto Coins!!";
    }
}
