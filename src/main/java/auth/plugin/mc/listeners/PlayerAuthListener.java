package auth.plugin.mc.listeners;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.events.*;
import auth.plugin.mc.managers.ChatManager;
import auth.plugin.mc.map.CustomMapRenderer;
import auth.plugin.mc.util.QrCodeUtil;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;
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
        if (!plugin.getLoginManager().isAuthenticated(player.getName())){
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

        BufferedImage qrCode = QrCodeUtil.genImage(event.getBase64QrCode());

        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {

                ChatManager.getInstance(event.getPlayer()).sendBeforeRegisterMessage(event.getRegistrationUrl());

                ItemStack map = mapWithQr(qrCode);

                if (event.getPlayer().getInventory().firstEmpty() == -1) {
                    event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), map);
                } else {
                    event.getPlayer().getInventory().addItem(map);
                }

                return null;

            }
        );
    }

    @EventHandler
    public void onAsyncLoginInviteEvent(AsyncLoginInviteEvent event){

        BufferedImage qrCode = QrCodeUtil.genImage(event.getBase64QrCode());



        plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {

                    ChatManager.getInstance(event.getPlayer()).sendBeforeLoginMessage(event.getLoginUrl());

                    ItemStack map = mapWithQr(qrCode);

                     if (event.getPlayer().getInventory().firstEmpty() == -1) {
                         event.getPlayer().getWorld().dropItem(event.getPlayer().getLocation(), map);
                     } else {
                         event.getPlayer().getInventory().addItem(map);
                     }

                    return null;

                }
        );
    }

    private ItemStack mapWithQr(BufferedImage qrCode) {

        ItemStack map = new ItemStack(Material.FILLED_MAP);

        MapView mapView = Bukkit.createMap(Bukkit.getWorlds().getFirst());

        mapView.getRenderers().clear();

        mapView.addRenderer(new CustomMapRenderer(qrCode));

        mapView.setScale(MapView.Scale.FARTHEST);

        mapView.setTrackingPosition(false);

        MapMeta meta = (MapMeta) map.getItemMeta();

        assert meta != null;

        meta.setDisplayName("QR Code");

        meta.setMapView(mapView);

        map.setItemMeta(meta);

        return map;
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

        QrCodeUtil.deleteQrCode(player);

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
