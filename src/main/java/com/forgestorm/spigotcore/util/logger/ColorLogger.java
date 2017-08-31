package com.forgestorm.spigotcore.util.logger;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-minigame-framework
 * DATE: 7/31/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

/**
 * Logger Info from Wikipedia.
 * Link: https://en.wikipedia.org/wiki/Java_logging_framework
 */
public enum ColorLogger {
    AQUA(ChatColor.AQUA),
    BLACK(ChatColor.BLACK),
    BLUE(ChatColor.BLUE),
    DARK_AQUA(ChatColor.DARK_AQUA),
    DARK_BLUE(ChatColor.DARK_BLUE),
    DARK_GRAY(ChatColor.DARK_GRAY),
    DARK_GREEN(ChatColor.DARK_GREEN),
    DARK_PURPLE(ChatColor.DARK_PURPLE),
    DARK_RED(ChatColor.DARK_RED),
    GOLD(ChatColor.GOLD),
    GRAY(ChatColor.GRAY),
    GREEN(ChatColor.GREEN),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE),
    RED(ChatColor.RED),
    WHITE(ChatColor.WHITE),
    YELLOW(ChatColor.YELLOW);

    private static final String CL = "[CL]";
    private ChatColor chatColor;

    ColorLogger(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public void printLog(String logMessage) {
        Bukkit.getServer().getConsoleSender().sendMessage(CL + chatColor + logMessage);
    }

    public void printLog(boolean printLog, String logMessage) {
        if (printLog) Bukkit.getServer().getConsoleSender().sendMessage(CL + chatColor + logMessage);
    }
}
