package auth.plugin.mc.events;

import auth.plugin.mc.model.Account;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncJoinRequestEvent extends CustomEvent {

    private final Player player;

    private final Account account;

    public AsyncJoinRequestEvent(Player player){
        super(true);
        this.player = player;
        this.account = Account.buildByPlayer(player);
    }

}
