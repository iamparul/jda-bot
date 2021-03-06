package org.bot;

import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class Listener extends ListenerAdapter {
    private final CommandManager manager = new CommandManager();


    /*@Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s");
        String inr = "";
        if (args[0].equalsIgnoreCase(org.bot.Main.prefix + "price")) {
            event.getChannel().sendTyping().queue();
//            event.getJDA().shutdown();
            try {
                inr = getCoinPrice(args[1],args[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage(String.valueOf(inr)).queue();
        }

        if (args[0].equalsIgnoreCase(org.bot.Main.prefix + "hi")) {
            event.getChannel().sendMessage("Hello "+ event.getMember().getEffectiveName()).queue();
        }
    }*/

//    public static void main(String[] args) throws IOException {
//        String coinPrice = getCoinPrice("dogecoin", "inr");
//        System.out.println("main" + coinPrice);
//    }



    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }

        String prefix = Main.prefix;
        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(PropertyReader.getInstance().getProperty("owner_id"))) {
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if(raw.startsWith(prefix)){
            manager.handle(event);
        }
    }
}