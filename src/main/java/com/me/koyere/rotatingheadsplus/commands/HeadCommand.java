package com.me.koyere.rotatingheadsplus.commands;

import com.me.koyere.rotatingheadsplus.commands.subcommands.CreateSubCommand;
import com.me.koyere.rotatingheadsplus.commands.subcommands.RemoveSubCommand;
import com.me.koyere.rotatingheadsplus.commands.subcommands.ListSubCommand;
import com.me.koyere.rotatingheadsplus.commands.subcommands.ReloadSubCommand;
import com.me.koyere.rotatingheadsplus.commands.subcommands.HelpSubCommand;
import com.me.koyere.rotatingheadsplus.commands.subcommands.StopSubCommand;

/**
 * Root command executor for /rhead
 * Registers all subcommands and dispatches them.
 */
public class HeadCommand extends SubCommandHandler {

    public HeadCommand() {
        registerSubCommand("create", new CreateSubCommand());
        registerSubCommand("remove", new RemoveSubCommand());
        registerSubCommand("list", new ListSubCommand());
        registerSubCommand("reload", new ReloadSubCommand());
        registerSubCommand("help", new HelpSubCommand());
        registerSubCommand("stop", new StopSubCommand()); // ✅ new
    }
}
