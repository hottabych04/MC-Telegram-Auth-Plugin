package auth.plugin.mc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthAccount {

    @JsonProperty("username")
    private String  username;

    @JsonProperty("uuid")
    private String  uuid;

    @JsonProperty("url")
    private String url;

}
