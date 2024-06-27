package auth.plugin.mc.chat;

import net.md_5.bungee.api.ChatColor;

public class Title {

    public static final Title EMPTY = new Title("", "", 0, 0, 0);

    public final String title;
    public final String subtitle;
    public final int fadeIn;
    public final int stay;
    public final int fadeOut;

    public Title(String title, String subtitle, int start, int duration, int end) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        this.subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        this.fadeIn = start;
        this.stay = duration;
        this.fadeOut = end;
    }

}
