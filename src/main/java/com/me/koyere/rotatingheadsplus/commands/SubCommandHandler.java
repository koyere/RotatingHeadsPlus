package com.me.koyere.rotatingheadsplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class SubCommandHandler implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    /**
     * Registra un subcomando por su nombre principal.
     */
    public void registerSubCommand(String name, SubCommand command) {
        subCommands.put(name.toLowerCase(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage("§cUsage: /" + label + " <subcommand>");
            return true;
        }

        String sub = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(sub);

        if (subCommand == null) {
            sender.sendMessage("§cUnknown subcommand. Use /" + label + " help");
            return true;
        }

        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, newArgs.length);

        return subCommand.execute(sender, newArgs);
    }
}
