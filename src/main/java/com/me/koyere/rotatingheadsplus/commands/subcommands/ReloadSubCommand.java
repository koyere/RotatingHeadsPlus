package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        RotatingHeadsPlus plugin = RotatingHeadsPlus.getInstance();

        plugin.getAnimationScheduler().clearAll();
        plugin.getHeadManager().clearAllHeads();
        plugin.getDataManager().loadHeads();

        sender.sendMessage(plugin.getLangManager().get("command.reload.success"));
        return true;
    }
}
