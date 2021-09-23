package com.icepaka89.UltimateManHunt.Util;

import net.minecraft.server.v1_16_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import net.minecraft.server.v1_16_R3.IChatBaseComponent.ChatSerializer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author : Daniel Gomm
 * @since : 9/21/21, Tue
 */
public class ActionBarUtil {
    public static void sendMessage(Player p, String message) {
//        PacketPlayOutChat packet = new PacketPlayOutChat(
//            ChatSerializer.a(
//                "{\"text\":\"" + message.replace("&", "§") + "\"}",
//                (byte)2
//            )
//        );
//        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
