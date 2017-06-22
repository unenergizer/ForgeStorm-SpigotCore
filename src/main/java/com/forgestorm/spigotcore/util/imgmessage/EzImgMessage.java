package com.forgestorm.spigotcore.util.imgmessage;

import com.forgestorm.spigotcore.constants.ChatIcons;
import com.forgestorm.spigotcore.constants.FilePaths;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EzImgMessage {


    public void sendEzImgMessage(Player player, ChatIcons iconType, String... text) {
        final BufferedImage imageToSend;            //The BufferedImage to send
        final int height = 8;                        //The image height
        final char chor = ImageChar.BLOCK.getChar();//The character that the image is made of.
        String icon = iconType.toString().toLowerCase() + ".png"; //Image name from enum.
        File file = new File(FilePaths.CHAT_ICONS.toString() + icon); //Image path

        if (!file.exists()) return;
        try {
            imageToSend = ImageIO.read(file);
            new ImageMessage(imageToSend, height, chor).appendText(text).sendToPlayer(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
