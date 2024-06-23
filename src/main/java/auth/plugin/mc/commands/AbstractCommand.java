package auth.plugin.mc.commands;

import auth.plugin.mc.McTelegramAuthPlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandExecutor;

@RequiredArgsConstructor
public abstract class AbstractCommand implements CommandExecutor {

    protected final McTelegramAuthPlugin plugin;

}
