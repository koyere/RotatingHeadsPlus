package com.me.koyere.rotatingheadsplus.commands.subcommands;

import com.me.koyere.rotatingheadsplus.RotatingHeadsPlus;
import com.me.koyere.rotatingheadsplus.commands.SubCommand;
import org.bukkit.command.CommandSender;

/**
 * /rhead help
 * Shows a list of all available subcommands.
 */
public class HelpSubCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        var lang = RotatingHeadsPlus.getInstance().getLangManager();

        sender.sendMessage(lang.get("command.help.header"));
        sender.sendMessage(lang.get("command.help.create"));
        sender.sendMessage(lang.get("command.help.remove"));
        sender.sendMessage(lang.get("command.help.list"));
        sender.sendMessage(lang.get("command.help.reload"));
        sender.sendMessage(lang.get("command.help.stop"));   // ✅ new
        sender.sendMessage(lang.get("command.help.help"));

        return true;
    }
}
