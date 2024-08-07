package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncRegisterEvent extends CustomEvent{

    private final Player player;
    public AsyncRegisterEvent(Player player) {
        super(true);
        this.player = player;
    }
}
