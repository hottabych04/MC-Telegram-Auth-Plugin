package auth.plugin.mc.map;

import org.bukkit.entity.Player;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class CustomMapRenderer extends MapRenderer {

    private boolean rendered = false;

    private final BufferedImage image;

    public CustomMapRenderer(BufferedImage image){
        this.image = image;
    }

    @Override
    public void render(MapView map, org.bukkit.map.MapCanvas canvas, Player player) {
        if (rendered) return;

        canvas.drawImage(0, 0, this.image);

        rendered = true;
    }
}
