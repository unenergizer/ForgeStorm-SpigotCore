package com.forgestorm.spigotcore.realm.remotefilemanager;

import com.forgestorm.spigotcore.SpigotCore;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by unene on 2/6/2017.
 */
public class AsyncRealmUploadFTP extends BukkitRunnable {

    private final SpigotCore plugin;
    private final String tempPath = Bukkit.getWorldContainer() + "/temp/";
    private final String remotePath = "/realms/";
    private final String zip = ".zip";
    private final int capacity = 100;
    private final String server = "ftp.forgestorm.com";
    private final int port = 21;
    private final String user = "realmdata@forgestorm.com";
    private final String pass = "FKGo$Gbv;H~J";
    private BlockingQueue<String> worldUploadQueue = new ArrayBlockingQueue<>(capacity);//<String> worldName/playerUUID
    private volatile boolean isShuttingDown = false;

    public AsyncRealmUploadFTP(SpigotCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (isShuttingDown) {
            cancel();
        } else {
            processQueue();
        }
    }

    /**
     * This will save worlds that are currently in the queue.
     */
    public void processQueue() {
        if (!worldUploadQueue.isEmpty()) {
            String fileName = worldUploadQueue.remove();
            System.out.println("[REALM] Exit upload queue: " + fileName);
            saveRealm(fileName);
        }
    }

    /**
     * Used to add worlds to queue for loading from a FTP to Server!
     *
     * @param uuidFileName The string UUID of the player name. Also the world name / directory name.
     * @return True if added to the queue. False otherwise.
     */
    public void uploadWorld(String uuidFileName, boolean asyncUnload) {
        if (asyncUnload) {
            // This code is ran if the server is still running and a player has closed their realm.
            if (worldUploadQueue.size() < capacity) {
                System.out.println("[REALM] Adding to FTP upload queue: " + uuidFileName);
                worldUploadQueue.add(uuidFileName);
            }
        } else {
            // This is a sync call to save loadedRealms on server onDisable.
            isShuttingDown = true;

            // If the queue is not empty, go ahead and process it now.
            // This is an attempt at a quick save.  This may get called more
            // than once if there are several loadedRealms to unload on server onDisable.
            if (!worldUploadQueue.isEmpty()) {
                List<String> uuidCollection = new ArrayList<>();
                worldUploadQueue.removeAll(uuidCollection);
                uuidCollection.forEach((uuid) -> saveRealm(uuid));
            }

            // Finally save the world originally called by this method.
            saveRealm(uuidFileName);
        }
    }

    /**
     * This saves the players realm to FTP.
     *
     * @param uuidFileName The folder name that we want to upload to FTP.
     *                     The folders name is the realm owners UUID.
     */
    public void saveRealm(String uuidFileName) {
        //Zip world directory.
        System.out.println("[REALM] Zipping: " + uuidFileName);
        ZipUtil.pack(new File(uuidFileName), new File(tempPath + uuidFileName + zip));

        //Connect to FTP
        System.out.println("[REALM] Starting realm upload via FTP.");
        Player player = Bukkit.getPlayer(UUID.fromString(uuidFileName));
        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;

        if (player != null) player.sendMessage(ChatColor.YELLOW + "Uploading your realm to our data center.");

        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File localFile = new File(tempPath + uuidFileName + zip);
            String remoteFile = remotePath + uuidFileName + zip;
            inputStream = new FileInputStream(localFile);
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("[REALM] The realm was uploaded successfully.");
                if (player != null)
                    player.sendMessage(ChatColor.GREEN + "Uploading complete!! Your realm has been saved.");
                deleteFiles(uuidFileName);
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            System.out.println("[REALM] Closing FTP connection.");

            try {
                if (inputStream != null) inputStream.close();

                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void deleteFiles(String uuidFileName) {
        // Remove Zip File
        new File(tempPath + uuidFileName + zip).delete();

        // Remove Player Realm "world" Folder.
        try {
            Files.walk(Paths.get(uuidFileName))
                    .map(Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2))
                    .forEach(File::delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[REALM] The realm directory and zip file were removed.");
    }
}
