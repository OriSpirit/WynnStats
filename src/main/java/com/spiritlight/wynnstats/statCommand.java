package com.spiritlight.wynnstats;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class statCommand extends CommandBase {
    public static final List<String> a_0 = Arrays.asList("player", "guild");
    public static final String ERR_INVALID_ARGS = "Invalid arguments, try /stat for more info.";
    public static final String ERR_REQUEST_FAIL = "Request failed, please check your spellings, or try again after some while.";
    @Override
    public String getName() {
        return "stat";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/stat player|guild <name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0) {
            AnnouncerSpirit.send("Usage: /stat [player/guild] <params>");
            AnnouncerSpirit.send("Refresh guild list via: /stat refreshGuilds");
            return;
        }
        switch(args[0].toLowerCase(Locale.ROOT)) {
            case "player":
                if(args.length < 2) {
                    AnnouncerSpirit.send(ERR_INVALID_ARGS);
                    return;
                }
                AnnouncerSpirit.send("Checking player " + args[1] + "...");
                List<TextComponentString> message;
                boolean exists = false;
                if(MainMod.playerMap.containsKey(args[1]))  {
                    message = MainMod.playerMap.get(args[1]);
                    exists = true;
                } else {
                    message = LookupSpirit.searchPlayer(args[1]);
                }
                if(message == null) {
                    AnnouncerSpirit.send(ERR_REQUEST_FAIL);
                    return;
                } else if (!exists) {
                    MainMod.playerMap.put(args[1], new ArrayList<>(message));
                }
                for(TextComponentString iTextComponents : message)
                    AnnouncerSpirit.send(iTextComponents);
                break;
            case "guild":
                AnnouncerSpirit.send("Under work.");
                break;
            case "refreshguilds":
                AnnouncerSpirit.send("Refreshing guild stats...");
                CompletableFuture.supplyAsync(() -> HttpSpirit.get("https://api.wynncraft.com/public_api.php?action=guildList")).thenAccept(ScannerSpirit::passGuild);
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        if(args.length == 0) return a_0;
        switch(args[0].toLowerCase(Locale.ROOT)) {
            case "player":
                return MainMod.onlinePlayers;
            case "guild":
                return MainMod.guildLists;
            default:
                return Collections.emptyList();
        }
    }
}
