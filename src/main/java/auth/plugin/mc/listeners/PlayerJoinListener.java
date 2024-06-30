package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.events.AsyncJoinRequestEvent;
import auth.plugin.mc.events.AsyncLoginResponseEvent;
import auth.plugin.mc.events.AsyncRegisterResponseEvent;
import auth.plugin.mc.managers.ChatManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.swing.plaf.basic.BasicButtonUI;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final McTelegramAuthPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        boolean callEvt = new AsyncJoinRequestEvent(player).callEvt(plugin);
        if (!callEvt) { player.kickPlayer("Failed to call join request send event :(");}
        String name = player.getName();
        ChatManager chatManager = ChatManager.getInstance(player);
        if (!plugin.getLoginManager().isAuthenticated(name)){
            chatManager.sendBeforeLoginMessage();
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
    }

    @EventHandler
    public void onAsyncJoinRequestEvent(AsyncJoinRequestEvent event){
        if (!plugin.getClient().sendJoinPostRequest(event.getPlayer()).isSuccessful()){
            event.getPlayer().kickPlayer("Dont have login response from telegram server :(");
        }
    }

    @EventHandler
    public void onAsyncLoginResponseEvent(AsyncLoginResponseEvent event){
        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
            event.getPlayer().chat("/login");
            return null;
        });
    }

    @EventHandler
    public void onAsyncRegisterResponseEvent(AsyncRegisterResponseEvent event){

    }

}
