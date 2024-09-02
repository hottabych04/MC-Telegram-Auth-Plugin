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

@RequiredArgsConstructor
public class PlayerAuthListener implements Listener {

    private final McTelegramAuthPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        ChatManager.getInstance(player).sendWaitMessage();
        boolean callEvt = new AsyncJoinRequestEvent(player).callEvt(plugin);
        if (!callEvt) { player.kickPlayer("Failed to call join request send event :(");}
        String uuid = player.getUniqueId().toString();
        if (!plugin.getLoginManager().isAuthenticated(uuid)){
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
    }

    @EventHandler
    public void onAsyncJoinRequestEvent(AsyncJoinRequestEvent event){
        try (Response response = plugin.getClient().sendJoinPostRequest(event.getPlayer())) {
            if (!response.isSuccessful()) {
                new AsyncNotAuthEvent(event.getPlayer(), "Dont have login response from telegram server :(").callEvt(plugin);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onAsyncRegisterInviteEvent(AsyncRegisterInviteEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {

            ChatManager.getInstance(event.getPlayer()).sendBeforeRegisterMessage(event.getRegistrationUrl());

            return null;
        }
        );
    }

    @EventHandler
    public void onAsyncLoginInviteEvent(AsyncLoginInviteEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {

                    ChatManager.getInstance(event.getPlayer()).sendBeforeLoginMessage(event.getLoginUrl());

                    return null;

                }
        );
    }

    @EventHandler
    public void onAsyncLoginEvent(AsyncLoginEvent event){
        Player player = event.getPlayer();

        auth(event.getPlayer());

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            ChatManager.getInstance(player).sendAfterLoginMessage();
            return null;
        });
    }

    @EventHandler
    public void onAsyncRegisterEvent(AsyncRegisterEvent event){
        Player player = event.getPlayer();

        auth(event.getPlayer());

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            ChatManager.getInstance(player).sendAfterRegisterMessage();
            return null;
        });
    }

    private void auth(Player player){

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
