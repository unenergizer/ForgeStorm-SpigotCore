package com.forgestorm.spigotcore.util.text;

import org.bukkit.ChatColor;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 8/9/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class ColorMessage {

    /**
     * Converts special characters in text into Minecraft client color codes.
     * <p>
     * This will give the messages color.
     *
     * @param msg The message that needs to have its color codes converted.
     * @return Returns a colored message!
     */
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
