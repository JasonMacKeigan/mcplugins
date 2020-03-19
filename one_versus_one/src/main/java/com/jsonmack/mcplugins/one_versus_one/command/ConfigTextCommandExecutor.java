package com.jsonmack.mcplugins.one_versus_one.command;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.jsonmack.mcplugins.one_versus_one.service.OneVersusOneConfigService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;

/**
 * Created by Jason MK on 2020-03-19 at 12:30 a.m.
 */
public class ConfigTextCommandExecutor implements CommandExecutor {

    private OneVersusOneConfigService configService;

    public ConfigTextCommandExecutor(Injector injector) {
        this.configService = injector.getInstance(OneVersusOneConfigService.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        configService.setConfig(configService.getConfig().withMessageGlobal(!configService.getConfig().isMessageGlobal()));
        try {
            configService.write();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
