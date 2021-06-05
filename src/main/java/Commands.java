import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Commands extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s");
        String inr = "";
        if (args[0].equalsIgnoreCase(Main.prefix + "price")) {
            event.getChannel().sendTyping().queue();
//            event.getJDA().shutdown();
            try {
                inr = getCoinPrice(args[1],args[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getChannel().sendMessage(String.valueOf(inr)).queue();
        }

        if (args[0].equalsIgnoreCase(Main.prefix + "hi")) {
            event.getChannel().sendMessage("Hello "+ event.getMember().getEffectiveName()).queue();
        }
    }

//    public static void main(String[] args) throws IOException {
//        String coinPrice = getCoinPrice("dogecoin", "inr");
//        System.out.println("main" + coinPrice);
//    }

    public static String getCoinPrice(String ids, String currency) throws IOException {
        URL urlForGetRequest = new URL("https://api.coingecko.com/api/v3/simple/price?ids="+ids+"&vs_currencies="+currency);
//        URL urlForGetRequest = new URL("https://jsonplaceholder.typicode.com/posts/1");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
//        conection.setRequestProperty("ids", ids);
//        conection.setRequestProperty("vs_currencies", currency);
        int responseCode = conection.getResponseCode();
        String inr="";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println("JSON String Result " + response.toString());
            inr = jsonObject.getJSONObject(ids).get(currency).toString();
//            System.out.println(jsonObject.);

        } else {
            System.out.println("GET NOT WORKED");
        }
        return inr;
    }
}