/*
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.gitlab.retropronghorn.retroslodestones;

import com.gitlab.retropronghorn.retroslodestones.commands.InfoCommand;
import com.gitlab.retropronghorn.retroslodestones.listeners.BlockInteractions;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


/**
 * Represents the RetrosLodestones plugin.
 */
public final class RetrosLodestones extends JavaPlugin {
    private static RetrosLodestones instance;

    private FileConfiguration languageConfig;
    private FileConfiguration particlesConfig;
    private FileConfiguration soundsConfig;

    /**
     * Get plugin instance
     **/
    public static RetrosLodestones getPlugin() {
        return instance;
    }

    public static RetrosLodestones getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        RetrosLodestones.instance = this;
        this.saveDefaultConfig();
        createCustomConfigs();

        // Register events
        this.getServer().getPluginManager().registerEvents(new BlockInteractions(this), this);

        // Register Commands
        Objects.requireNonNull(this.getCommand("retroslodestones")).setExecutor(new InfoCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Get the language configuration
     **/
    public FileConfiguration getLanguageConfig() {
        return this.languageConfig;
    }

    /**
     * Get the particles configuration
     **/
    public FileConfiguration getParticlesConfig() {
        return this.particlesConfig;
    }

    /**
     * Get the sound configuration
     **/
    public FileConfiguration getSoundsConfig() {
        return this.soundsConfig;
    }

    /**
     * Create custom config & language files
     **/
    private void createCustomConfigs() {
        File languageFile = new File(getDataFolder(), "language.yml");
        File particlesFile = new File(getDataFolder(), "particles.yml");
        File soundsFile = new File(getDataFolder(), "sounds.yml");

        if (!languageFile.exists()) {
            languageFile.getParentFile().mkdirs();
            saveResource("language.yml", false);
        }

        if (!particlesFile.exists()) {
            particlesFile.getParentFile().mkdirs();
            saveResource("particles.yml", false);
        }

        if (!soundsFile.exists()) {
            soundsFile.getParentFile().mkdirs();
            saveResource("sounds.yml", false);
        }

        languageConfig = new YamlConfiguration();
        particlesConfig = new YamlConfiguration();
        soundsConfig = new YamlConfiguration();

        try {
            soundsConfig.load(soundsFile);
            particlesConfig.load(particlesFile);
            languageConfig.load(languageFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
