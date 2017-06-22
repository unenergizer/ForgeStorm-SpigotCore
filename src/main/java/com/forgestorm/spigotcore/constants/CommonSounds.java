package com.forgestorm.spigotcore.constants;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 4/28/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 RetroMMO.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public enum CommonSounds {
    //Sound , Volume, Pitch
    ACTION_SUCCESS(Sound.ENTITY_PLAYER_LEVELUP, 1, .8f),
    ACTION_FAILED(Sound.BLOCK_NOTE_BASS, 1F, .5F);

    private final Sound sound;
    private final float volume, pitch;

    CommonSounds(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void playSound(Player player) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
