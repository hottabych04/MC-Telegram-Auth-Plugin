package auth.plugin.mc.managers;

import auth.plugin.mc.chat.Messages;
import auth.plugin.mc.settings.Settings;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class ChatManager {

    private final Player player;

    public void sendRegisterMessage(){
        BaseComponent component = new ComponentBuilder()
                .append((String) Settings.SETTINGS.get(Messages.BEFORE_REGISTER.getKey()))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/login"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Link to the Telegram bot for registration")))
                .color(ChatColor.AQUA)
                .bold(true)
                .underlined(true)
                .build();
        sendComponent(component);
    }

    public void sendSuccessRegisterMessage(){
        player.sendMessage("Register success...");
    }

    public void sendLoginMessage(){
        BaseComponent component = new ComponentBuilder()
                .append((String) Settings.SETTINGS.get(Messages.BEFORE_LOGIN.getKey()))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/login"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Link to the Telegram bot for login")))
                .color(ChatColor.AQUA)
                .bold(true)
                .underlined(true)
                .build();
        sendComponent(component);
    }

    public void sendSuccessLoginMessage(){
        player.sendMessage((String) Settings.SETTINGS.get(Messages.AFTER_LOGIN.getKey()));
//        player.sendTitle("Welcome!", "Your login success", 10, 70, 20);
    }

    private void sendComponent(BaseComponent component){
        player.spigot().sendMessage(component);
    }

}
