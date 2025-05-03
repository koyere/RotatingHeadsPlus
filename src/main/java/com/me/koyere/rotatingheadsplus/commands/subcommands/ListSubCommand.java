package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ListSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int count = RotatingHeadsPlus.getInstance()
                .getAnimationScheduler()
                .getAllAnimatedIds()
                .size();

        String message = RotatingHeadsPlus.getInstance()
                .getLangManager()
                .get("command.list.count")
                .replace("{count}", String.valueOf(count));

        sender.sendMessage(message);
        return true;
    }
}

