import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {

    public static JDABuilder builder;

    public static void main(String[] args) throws LoginException, InterruptedException {
        builder = new JDABuilder(AccountType.BOT);
        String Token = "";
        builder.setToken(Token);
        builder.addEventListener(new Main());
//        builder.buildAsync();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        System.out.println("Message Received from" +
                event.getAuthor().getName() + ": " + event.getMessage().getContentDisplay());
//        System.out.println("abc"+event.getAuthor());
        if (event.getAuthor().isBot())
            return;
        if (event.getMessage().getContentRaw().equalsIgnoreCase("hi")) {
            event.getChannel().sendMessage("Hello").queue();
        } else {
            event.getChannel().sendMessage("else case").queue();
        }

//        GuildMessageReceivedEvent
    }
}
