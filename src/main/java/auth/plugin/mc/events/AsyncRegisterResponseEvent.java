package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncRegisterResponseEvent extends CustomEvent {

    private final Player player;

    private final Account account;

    public AsyncRegisterResponseEvent(Player player){
        super(true);
        this.player = player;
        this.account = Account.buildByPlayer(player);
    }

}
