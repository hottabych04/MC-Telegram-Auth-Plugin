package auth.plugin.mc.events;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncLoginInviteEvent extends CustomEvent {

    private final Player player;

    private final String loginUrl;

    private final String base64QrCode;

    public AsyncLoginInviteEvent(Player player, String regUrl, String base64QrCode){
        super(true);
        this.player = player;
        this.loginUrl = regUrl;
        this.base64QrCode = base64QrCode;
    }

}
