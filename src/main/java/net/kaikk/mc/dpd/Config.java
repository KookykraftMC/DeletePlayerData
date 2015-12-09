package net.kaikk.mc.dpd;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class Config {
	public String playerDataPath;
	
	Config(JavaPlugin instance) {
		instance.getConfig().options().copyDefaults(true);
		instance.saveDefaultConfig();
		
		this.playerDataPath=instance.getConfig().getString("PlayerDataPath", "autodetect");
		if (this.playerDataPath.equalsIgnoreCase("autodetect")) {
			this.playerDataPath = instance.getServer().getWorldContainer().getPath()+File.separator+"playerdata";
		}
	}
}
