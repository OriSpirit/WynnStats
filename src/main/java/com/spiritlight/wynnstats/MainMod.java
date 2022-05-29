package com.spiritlight.wynnstats;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod(modid = MainMod.MODID, name = MainMod.NAME, version = MainMod.VERSION)
public class MainMod
{
    public static final String MODID = "wynnstats";
    public static final String NAME = "WynnStats";
    public static final String VERSION = "1.0";
    public static List<String> guildLists = new ArrayList<>();
    public static List<String> onlinePlayers = new ArrayList<>();
    private static boolean initDone = false;
    static boolean debug = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        try {
            ConfigSpirit.getConfig();
        } catch (IOException e) {
            try {
                ConfigSpirit.writeConfig();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        if(guildLists.equals(Collections.emptyList())) {
            CompletableFuture.supplyAsync(() -> HttpSpirit.get("https://api.wynncraft.com/public_api.php?action=guildList")).thenAccept(ScannerSpirit::passGuild);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        if(!initDone) {
            init();
            initDone = true;
        }
    }

    public static void init() {
        // Managed: Schedule time
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(scanPlayers, 0, 5, TimeUnit.MINUTES);
    }

    private static final Runnable scanPlayers = (ScannerSpirit::parseOnline);
}
