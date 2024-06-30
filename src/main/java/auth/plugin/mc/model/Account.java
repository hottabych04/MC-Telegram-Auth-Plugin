package auth.plugin.mc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @JsonProperty("username")
    private String  username;
    @JsonProperty("uuid")
    private String  uuid;

    public static Account buildByPlayer(Player player){
        return Account.builder()
                .username(player.getName())
                .uuid(player.getUniqueId().toString())
                .build();
    }

}






