package com.spiritlight.wynnstats;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.annotation.Nonnull;
import java.util.List;

public class PagingSpirit {
    public static final int MAX_INDEX = 10;

    public void fetchPage(@Nonnull List<TextComponentString> messages, int beginIndex, String guild) {
        final int size = messages.size();
        final TextComponentString PAGE_BACK = new TextComponentString("§b[<]§r");
        final TextComponentString PAGE_NEXT = new TextComponentString("§b[>]§r");
        final TextComponentString PAGE_START = new TextComponentString("§7[<]§r");
        final TextComponentString PAGE_END = new TextComponentString("§7[>]§r");
        final TextComponentString SPLITTER = new TextComponentString(" --- " + beginIndex + "/" + size + " --- ");
        ITextComponent end;
        PAGE_BACK.setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stat _showGuildPage " + guild + " " + (beginIndex - 10)))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("<< Last Page"))));
        PAGE_NEXT.setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/stat _showGuildPage " + guild + " " + (beginIndex + 10)))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Next Page >>"))));
        end = (beginIndex - MAX_INDEX > 1 ? PAGE_BACK : PAGE_START).appendSibling(SPLITTER).appendSibling((beginIndex + MAX_INDEX < size ? PAGE_NEXT : PAGE_END));
        AnnouncerSpirit.send(messages.get(0));
        for (int i = beginIndex; i < (Math.min(beginIndex + 10, size)); i++) {
            AnnouncerSpirit.send(messages.get(i));
        }
        AnnouncerSpirit.send(end);
    }
}
