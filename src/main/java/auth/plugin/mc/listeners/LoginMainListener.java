package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.util.QrCodeUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

@RequiredArgsConstructor
public class LoginMainListener implements Listener {

    private final McTelegramAuthPlugin plugin;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        if (!plugin.getLoginManager().isAuthenticated(name)){
            Location to = event.getTo();
            if (to != null && event.getFrom().getY() > to.getY()) return;
            player.teleport(event.getFrom());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        player.setWalkSpeed(0F);
        player.setFlySpeed(0F);
        plugin.getLoginManager().cleanup(player.getName());
        QrCodeUtil.deleteQrCode(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) return;
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player player = ((Player) event.getEntity());
        if (!plugin.getLoginManager().isAuthenticated(player.getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String name = event.getWhoClicked().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.isCancelled()) return;

        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        String name = e.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.SUICIDE) return;
        if (event.isCancelled()) return;

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (!plugin.getLoginManager().isAuthenticated(player.getName())) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (!plugin.getLoginManager().isAuthenticated(player.getName())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerShearEntity(PlayerShearEntityEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFish(PlayerFishEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSignChange(SignChangeEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        String name = event.getPlayer().getName();
        if (!plugin.getLoginManager().isAuthenticated(name)) event.setCancelled(true);
    }
}
