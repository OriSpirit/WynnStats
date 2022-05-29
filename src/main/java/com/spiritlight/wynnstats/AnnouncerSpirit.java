package com.spiritlight.wynnstats;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.util.Arrays;

public class AnnouncerSpirit {
    public static void send(String message) {
        try {
            Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§2[§aWynn§cStat§2] §a" + message));
        } catch (NullPointerException ignored) {
            System.out.println("Caught NullPointerException whilst attempting to send a message, assuming player does not yet exist.");
        }
    }

    public static void sendDebug(String message) {
        if(MainMod.debug)
            send(message);
    }

    public static void sendDebug(TextComponentString message) {
        if(MainMod.debug)
            send(message);
    }

    public static void send(TextComponentString message) {
        try {
            Minecraft.getMinecraft().player.sendMessage(message);
        } catch (NullPointerException ignored) {
            System.out.println("Caught NullPointerException whilst attempting to send a message, assuming player does not yet exist.");
        }
    }

    public static void sendException(Exception e, String message, boolean printStackTrace) {
        Style style;
        TextComponentString t = new TextComponentString(message.replaceAll("\\$err", e.getMessage()).replaceAll("\\$errType", e.getClass().getCanonicalName()));
        style = t.getStyle();
        TextComponentString s = new TextComponentString(e.getClass().getCanonicalName() + ": " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()).replaceAll("(,)", ",\n").replaceAll("\\[", "").replaceAll("]", ""));
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, s));
        AnnouncerSpirit.sendDebug(t);
        if(printStackTrace)
            e.printStackTrace();
    }

    public static void sendException(Exception e) {
        sendException(e, "$errType: $err caught whilst performing an action (Hover for details)", true);
    }
}
