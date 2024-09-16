package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncRegisterInviteEvent extends CustomEvent {

    private final Player player;

    private final String registrationUrl;

    private final String base64QrCode;

    public AsyncRegisterInviteEvent(Player player, String url, String qrCode){
        super(true);
        this.player = player;
        this.registrationUrl = url;
        this.base64QrCode = qrCode;
    }

}
