package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerMainListener implements Listener {

    private final McTelegramAuthPlugin plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        if (!plugin.getLoginManager().isAuthenticated(name)){
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        if (!plugin.getLoginManager().isAuthenticated(name)){
            player.teleport(event.getFrom());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        plugin.getLoginManager().cleanup(player.getName());
    }
}
