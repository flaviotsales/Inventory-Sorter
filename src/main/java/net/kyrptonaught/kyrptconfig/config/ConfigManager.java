package net.kyrptonaught.kyrptconfig.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import blue.endless.jankson.Jankson;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigManager {
    private final Jankson JANKSON = Jankson.builder().build();
    private HashMap<String, ConfigStorage> configs = new HashMap<>();
    protected File dir;
    protected String MOD_ID;

    private ConfigManager(String mod_id) {
        this.MOD_ID = mod_id;
        dir = FabricLoader.getInstance().getConfigDirectory();
    }

    public AbstractConfigFile getConfig(String name) {
        return configs.get(name).config;
    }

    public void registerFile(String name, AbstractConfigFile defaultConfig) {
        configs.put(name, new ConfigStorage(new File(dir, name), defaultConfig));
    }

    public void saveAll() {
        configs.values().forEach(configStorage -> configStorage.save(MOD_ID, JANKSON));
    }

    public void loadAll() {
        configs.values().forEach(configStorage -> configStorage.load(MOD_ID, JANKSON));
        saveAll();
    }

    public static class SingleConfigManager extends ConfigManager {
        public SingleConfigManager(String mod_id) {
            super(mod_id);
            dir = FabricLoader.getInstance().getConfigDirectory();
        }
    }

    public static class MultiConfigManager extends ConfigManager {
        public MultiConfigManager(String mod_id) {
            super(mod_id);
            dir = new File(FabricLoader.getInstance().getConfigDirectory() + "/" + MOD_ID);
            if (!Files.exists(dir.toPath())) {
                try {
                    Files.createDirectories(dir.toPath());
                } catch (IOException e) {
                }
            }
        }
    }
}
