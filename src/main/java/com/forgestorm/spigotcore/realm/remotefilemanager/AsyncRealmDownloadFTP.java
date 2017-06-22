package com.forgestorm.spigotcore.realm.remotefilemanager;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zeroturnaround.zip.ZipUtil;
import org.zeroturnaround.zip.commons.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by unene on 2/6/2017.
 */
public class AsyncRealmDownloadFTP extends BukkitRunnable {

    private final SpigotCore plugin;
    private final String tempPath = Bukkit.getWorldContainer() + "/temp/";
    private final String remotePath = "/realms/";
    private final String zipExtension = ".zip";
    private final int capacity = 100;
    private final String server = "ftp.forgestorm.com";
    private final int port = 21;
    private final String user = "realmdata@forgestorm.com";
    private final String pass = "FKGo$Gbv;H~J";
    private BlockingQueue<String> worldDownloadQueue = new ArrayBlockingQueue<>(capacity);//<String> worldName/playerUUID

    public AsyncRealmDownloadFTP(SpigotCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (!worldDownloadQueue.isEmpty()) {
            String fileName = worldDownloadQueue.remove();
            System.out.println("[REALM] Exiting FTP download queue: " + fileName);
            getRealm(fileName);
        }
    }

    /**
     * Used to add worlds to queue for loading from a FTP to Server!
     *
     * @param uuidFileName The string UUID of the player name. Also the world name / directory name.
     * @return True if added to the queue. False otherwise.
     */
    public boolean downloadWorld(String uuidFileName) {
        if (worldDownloadQueue.size() < capacity) {
            System.out.println("[REALM] Adding to FTP download queue: " + uuidFileName);
            worldDownloadQueue.add(uuidFileName);
            return true;
        }
        return false;
    }

    private void getRealm(String uuidFileName) {
        //Connect to FTP
        System.out.println("[REALM] Starting realm download via FTP.");
        Player player = Bukkit.getPlayer(UUID.fromString(uuidFileName));
        FTPClient ftpClient = new FTPClient();
        OutputStream outputStream = null;

        player.sendMessage(ChatColor.YELLOW + "Download your realm from our data center...");

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: using retrieveFile(String, OutputStream)
            String remoteFile = remotePath + uuidFileName + zipExtension;
            File downloadFile = new File(tempPath + uuidFileName + zipExtension);
            outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream);

            if (success) {
                System.out.println("[REALM] The realm has been downloaded successfully.");
                player.sendMessage(ChatColor.GREEN + "Download complete! Opening your realm!");
                player.sendMessage(SpigotCoreMessages.REALM_PORTAL_TITLE.toString());
            }

        } catch (IOException ex) {
            System.out.println("[REALM] Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.out.println("[REALM] Realm download, closing FTP connection.");
            try {

                if (outputStream != null) outputStream.close();

                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //Unzip the file
            unzip(uuidFileName);

            //Notify Sync World Loader
            plugin.getSyncWorldLoader().addWorldName(uuidFileName);
        }
    }

    private void unzip(String uuidFileName) {
        System.out.println("[REALM] Unzipping Realm: " + uuidFileName);

        String zipFile = tempPath + uuidFileName + zipExtension;
        String destination = uuidFileName;
        ZipUtil.unpack(new File(zipFile), new File(destination));

        //Delete temp files...
        try {
            FileUtils.deleteDirectory(new File(uuidFileName + zipExtension));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
