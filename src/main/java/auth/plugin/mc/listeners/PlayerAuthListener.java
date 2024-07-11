package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.events.AsyncJoinRequestEvent;
import auth.plugin.mc.events.AsyncLoginEvent;
import auth.plugin.mc.events.AsyncRegisterEvent;
import auth.plugin.mc.events.AsyncRegisterResponseEvent;
import auth.plugin.mc.managers.ChatManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Optional;

@RequiredArgsConstructor
public class PlayerAuthListener implements Listener {

    private final McTelegramAuthPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        boolean callEvt = new AsyncJoinRequestEvent(player).callEvt(plugin);
        if (!callEvt) { player.kickPlayer("Failed to call join request send event :(");}
        String name = player.getName();
        ChatManager chatManager = ChatManager.getInstance(player);
        if (!plugin.getLoginManager().isAuthenticated(name)){
//            chatManager.sendBeforeLoginMessage();
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
    }

    @SneakyThrows
    @EventHandler
    public void onAsyncJoinRequestEvent(AsyncJoinRequestEvent event){
        Optional<String> regUrl;
        try (Response response = plugin.getClient().sendJoinPostRequest(event.getPlayer())) {
            if (!response.isSuccessful()) {
                event.getPlayer().kickPlayer("Dont have login response from telegram server :(");
            }

            regUrl = Optional.of(response.body().string());
        }

        regUrl.ifPresent(s -> new AsyncRegisterResponseEvent(event.getPlayer(), s).callEvt(plugin));
    }

    @EventHandler
    public void onAsyncRegisterResponseEvent(AsyncRegisterResponseEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            new ChatManager(event.getPlayer()).sendBeforeRegisterMessage(event.getRegistrationUrl());
            return null;
        });
    }

    @EventHandler
    public void onAsyncLoginEvent(AsyncLoginEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            event.getPlayer().chat("/login");
            return null;
        });
    }

    @EventHandler
    public void onAsyncRegisterEvent(AsyncRegisterEvent event){

    }

}
