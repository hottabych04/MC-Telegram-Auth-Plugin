package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.events.*;
import auth.plugin.mc.managers.ChatManager;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
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
        if (!plugin.getLoginManager().isAuthenticated(name)){
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
    }

    @EventHandler
    public void onAsyncJoinRequestEvent(AsyncJoinRequestEvent event){
        Optional<String> regUrl;
        try (Response response = plugin.getClient().sendJoinPostRequest(event.getPlayer())) {
            if (!response.isSuccessful()) {
                new AsyncNotAuthEvent(event.getPlayer(), "Dont have login response from telegram server :(").callEvt(plugin);
            }
            regUrl = Optional.of(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        regUrl.ifPresent(s -> {
            if (!regUrl.get().isEmpty()){
                new ChatManager(event.getPlayer()).sendBeforeRegisterMessage(s);
            } else {
                new ChatManager(event.getPlayer()).sendBeforeLoginMessage();
            }
        });
    }

    @EventHandler
    public void onAsyncRegisterResponseEvent(AsyncRegisterResponseEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> null);
    }

    @EventHandler
    public void onAsyncLoginEvent(AsyncLoginEvent event){
        Player player = event.getPlayer();

        new AsyncAuthEvent(player).callEvt(plugin);

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            new ChatManager(player).sendAfterLoginMessage();
            return null;
        });
    }

    @EventHandler
    public void onAsyncRegisterEvent(AsyncRegisterEvent event){
        Player player = event.getPlayer();

        new AsyncAuthEvent(player).callEvt(plugin);

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            new ChatManager(player).sendAfterRegisterMessage();
            return null;
        });
    }

    @EventHandler
    public void onAsyncAuthEvent(AsyncAuthEvent event){
        Player player = event.getPlayer();

        if (player == null || !player.isOnline()) return;

        plugin.getLoginManager().setAuthenticated(player.getName());
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.1F);
    }

    @EventHandler
    public void onAsyncNotAuthEvent(AsyncNotAuthEvent event){
        Player player = event.getPlayer();

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            player.kickPlayer(event.getErrMsg());
            return null;
        });
    }

}
