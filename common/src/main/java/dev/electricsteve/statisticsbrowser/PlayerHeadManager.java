package dev.electricsteve.statisticsbrowser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import dev.electricsteve.statisticsbrowser.platform.Services;

// Most of the code in this file was inspired by or taken from bluemap,
// specifically classes in common/src/main/java/de/bluecolored/bluemap/common/plugin/skins
public class PlayerHeadManager {
    private static final byte[] NO_IMAGE_RETURN = new byte[0];

    private static PlayerHeadManager INSTANCE;

    private final Path headCachePath;
    private final Map<UUID, Long> skinUpdates;

    private PlayerHeadManager() {
        this.headCachePath = Services.PLATFORM.getConfigDir().resolve("cache/playerheads");
        this.skinUpdates = new ConcurrentHashMap<>();
    }

    public static PlayerHeadManager getInstance() {
        if (INSTANCE == null) INSTANCE = new PlayerHeadManager();
        return INSTANCE;
    }

    public byte[] getHeadImage(UUID uuid) {
        if (!shouldUpdate(uuid)) {
            File file = headCachePath.resolve(uuid + ".png").toFile();
            try (FileInputStream inputStream = new FileInputStream(file)) {
                return inputStream.readAllBytes();
            } catch (FileNotFoundException e) {
                Constants.LOG.warn("Player head cache shouldn't update, but no cache file was found for {}: ", uuid, e);
                return getAndSave(uuid);
            } catch (IOException e) {
                Constants.LOG.error("Error reading player head cache file for {}: ", uuid, e);
                return NO_IMAGE_RETURN;
            }
        } else {
            return getAndSave(uuid);
        }
    }

    private boolean shouldUpdate(UUID uuid) {
        long lastUpdate = skinUpdates.getOrDefault(uuid, 0L);
        long now = System.currentTimeMillis();
        if (now - lastUpdate < TimeUnit.HOURS.toMillis(1)) return false;
        skinUpdates.put(uuid, now);
        return true;
    }

    private byte[] getAndSave(UUID uuid) {
        Optional<BufferedImage> playerSkin = getPlayerSkin(uuid);
        if (playerSkin.isEmpty()) {
            return NO_IMAGE_RETURN;
        }
        BufferedImage head = sliceHead(playerSkin.get());
        // Write to output file
        File file = headCachePath.resolve(uuid + ".png").toFile();
        try {
            Files.createDirectories(headCachePath);
            boolean _ = file.createNewFile();
        } catch (IOException e) {
            Constants.LOG.error("Failed to create player head image file for {}: ", uuid, e);
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            ImageIO.write(head, "png", outputStream);
        } catch (IOException e) {
            Constants.LOG.error("Failed to write player head image for {} to file: {}", uuid, e);
        }
        // Write to output array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(head, "png", outputStream);
        } catch (IOException e) {
            Constants.LOG.error("Failed to write player head image for {} to output stream: {}", uuid, e);
            return NO_IMAGE_RETURN;
        }
        return outputStream.toByteArray();
    }

    private Optional<BufferedImage> getPlayerSkin(UUID uuid) {
        try (Reader reader = requestProfileJson(uuid)) {
            String textureInfoJson = readTextureInfoJson(JsonParser.parseReader(reader));
            String textureUrl = readTextureUrl(JsonParser.parseString(textureInfoJson));
            return Optional.of(ImageIO.read(URI.create(textureUrl).toURL()));
        } catch (IOException ex) {
            Constants.LOG.warn("Failed to load skin from mojang for player: '{}' - {}", uuid, ex);
            return Optional.empty();
        }
    }

    private BufferedImage sliceHead(BufferedImage in) {
        // Was gonna copy some code from bluemap but copilot did it for me LOL
        BufferedImage head;

        BufferedImage layer1 = in.getSubimage(8, 8, 8, 8);
        BufferedImage layer2 = in.getSubimage(40, 8, 8, 8);

        try {
            head = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
            head.getGraphics().drawImage(layer1, 4, 4, 40, 40, null);
            head.getGraphics().drawImage(layer2, 0, 0, 48, 48, null);
        } catch (Throwable t) { // There might be problems with headless servers when loading the graphics class
            Constants.LOG.warn("Could not access Graphics2D to render player-skin texture. Try adding '-Djava.awt.headless=true' to your startup flags or ignore this warning.");
            head = new BufferedImage(8, 8, in.getType());
            layer1.copyData(head.getRaster());
        }

        return head;
    }

    private Reader requestProfileJson(UUID playerUUID) throws IOException {
        String undashedUUID = playerUUID.toString().replace("-", "");
        URL url = URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + undashedUUID).toURL();
        return new InputStreamReader(url.openStream());
    }

    private String readTextureInfoJson(JsonElement json) throws IOException {
        try {
            JsonArray properties = json.getAsJsonObject().getAsJsonArray("properties");

            for (JsonElement element : properties) {
                if (element.getAsJsonObject().get("name").getAsString().equals("textures")) {
                    return new String(Base64.getDecoder().decode(element.getAsJsonObject().get("value").getAsString().getBytes()));
                }
            }

            throw new IOException("No texture info found!");
        } catch (NullPointerException | IllegalStateException | ClassCastException e) {
            throw new IOException(e);
        }

    }

    private String readTextureUrl(JsonElement json) throws IOException {
        try {
            return json.getAsJsonObject()
                    .getAsJsonObject("textures")
                    .getAsJsonObject("SKIN")
                    .get("url").getAsString();
        } catch (NullPointerException | IllegalStateException | ClassCastException e) {
            throw new IOException(e);
        }
    }
}
