package auth.plugin.mc.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class QrCodeUtil {

    public static BufferedImage genImage(String base64QrCode){

        byte[] qrCodeData = Base64.getDecoder().decode(base64QrCode);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(qrCodeData);) {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteQrCode(Player player){
        HashMap<Integer, ? extends ItemStack> all = player.getInventory().all(Material.FILLED_MAP);

        all.values().stream()
                .filter(ItemStack::hasItemMeta)
                .filter(it -> it.getItemMeta().hasDisplayName())
                .filter(it -> it.getItemMeta().getDisplayName().equals("QR Code"))
                .findFirst()
                .ifPresent(it -> player.getInventory().remove(it));
    }

}
