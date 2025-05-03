package com.me.koyere.rotatingheadsplus.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    boolean execute(CommandSender sender, String[] args);
}
