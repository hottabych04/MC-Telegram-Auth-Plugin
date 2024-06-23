package auth.plugin.mc.commands.executors;

import auth.plugin.mc.McTelegramAuthPlugin;
import auth.plugin.mc.commands.AbstractCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoginExecutor extends AbstractCommand {

    public LoginExecutor(McTelegramAuthPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("login")) {

            if(sender instanceof Player){
                var player = (Player) sender;
                plugin.getLoginManager().setAuthenticated(player.getName());
                player.setWalkSpeed(0.2F);
                player.setFlySpeed(0.1F);
                player.sendMessage("Login success...");
            }

        }
        return false;
    }

}
