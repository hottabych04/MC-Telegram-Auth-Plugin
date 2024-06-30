package auth.plugin.mc.http;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.events.AsyncLoginResponseEvent;
import auth.plugin.mc.events.AsyncRegisterResponseEvent;
import auth.plugin.mc.model.Account;
import auth.plugin.mc.settings.Settings;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class HttpClient {

    private final McTelegramAuthPlugin plugin;

    private final Javalin app;

    private final OkHttpClient client;

    public HttpClient(@NonNull McTelegramAuthPlugin plugin){
        this.plugin = plugin;
        // Temporarily switch the plugin classloader to load Javalin.
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(plugin.getClass().getClassLoader());
        // Create a Javalin instance.
        this.app = Javalin.create().start(Settings.CLIENT_PORT.asInt(8080));

        this.client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Restore default loader.
        Thread.currentThread().setContextClassLoader(classLoader);
        // The created instance can be used outside the class loader.
        this.app.post("/login", ctx -> {
            String jsonResponse = ctx.body();
            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.readValue(jsonResponse, Account.class);
            Player player = plugin.getServer().getPlayer(account.getUsername());
            new AsyncLoginResponseEvent(player).callEvt(plugin);
            ctx.status(HttpStatus.OK);
            ctx.result("Json was accepted");
        });

        this.app.post("/register", ctx -> {
            String jsonResponse = ctx.body();
            ObjectMapper objectMapper = new ObjectMapper();
            Account account = objectMapper.convertValue(jsonResponse, Account.class);
            Player player = plugin.getServer().getPlayer(account.getUsername());
            new AsyncRegisterResponseEvent(player).callEvt(plugin);
            ctx.status(HttpStatus.OK);
            ctx.result("Json was accepted");
        });
        // log
        plugin.getLogger().info("JavalinPlugin is enabled");
    }

    @SneakyThrows
    public Response sendJoinPostRequest(Player player){
        String requestUrl = getHttpAddress(Settings.SERVER_IP.asString(""),
                Settings.SERVER_PORT.asInt(8080));

        MediaType mediaType = MediaType.get("application/json");

        ObjectMapper objectMapper = new ObjectMapper();

        Account account = Account.builder()
                .username(player.getName())
                .uuid(player.getUniqueId().toString())
                .build();

        String json = objectMapper.writeValueAsString(account);

        RequestBody body = RequestBody.create(json, mediaType);

        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return response;
    }

    private static String getHttpAddress(String ip,
                                        int port){
        return getHttpAddress(ip, port, "/");
    }

    private static String getHttpAddress(String ip,
                                        int port,
                                        String path){
        return "http://" + ip + ":" + port + path;
    }

}
