package auth.plugin.mc.managers;

import auth.plugin.mc.chat.Messages;
import auth.plugin.mc.chat.Title;
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

    public void sendBeforeRegisterMessage(){
        BaseComponent component = new ComponentBuilder()
                .append(Messages.BEFORE_REGISTER.asString())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/login"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Link to the Telegram bot for registration")))
                .color(ChatColor.AQUA)
                .bold(true)
                .underlined(true)
                .build();
        sendComponent(component);
    }

    public void sendAfterRegisterMessage(){
        player.sendMessage(Messages.AFTER_REGISTER.asString());
    }

    public void sendBeforeLoginMessage(){
        BaseComponent component = new ComponentBuilder()
                .append(Messages.BEFORE_LOGIN.asString())
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/login"))
                .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Link to the Telegram bot for login")))
                .color(ChatColor.AQUA)
                .bold(true)
                .underlined(true)
                .build();
        sendComponent(component);
        sendTitle(Messages.TITLE_BEFORE_LOGIN.asTitle());
    }

    public void sendAfterLoginMessage(){
        player.sendMessage(Messages.AFTER_LOGIN.asString());
        sendTitle(Messages.TITLE_AFTER_LOGIN.asTitle());
    }

    private void sendComponent(BaseComponent component){
        player.spigot().sendMessage(component);
    }

    private void sendTitle(Title title){
        player.sendTitle(
                title.title,
                title.subtitle,
                title.fadeIn,
                title.stay,
                title.fadeOut
        );
    }

}
