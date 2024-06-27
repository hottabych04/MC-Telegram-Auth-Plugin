package auth.plugin.mc.commands.executors;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.commands.AbstractCommand;
import auth.plugin.mc.managers.ChatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginCommand extends AbstractCommand {

    public LoginCommand(McTelegramAuthPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) return false;

        if (!command.getName().equalsIgnoreCase("login")) return true;

        Player player = (Player) sender;

        if (plugin.getLoginManager().isAuthenticated(player.getName())) return true;

        plugin.getLoginManager().setAuthenticated(player.getName());
        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.1F);
        ChatManager chatManager = new ChatManager(player);
        chatManager.sendAfterLoginMessage();

        return true;
    }

}
