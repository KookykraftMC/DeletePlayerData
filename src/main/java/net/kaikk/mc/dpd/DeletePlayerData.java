package net.kaikk.mc.dpd;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

public class DeletePlayerData extends JavaPlugin {
	static DeletePlayerData instance;
	static String playerDataDir;
	
	@Override
	public void onEnable() {
		instance=this;
		playerDataDir=this.getServer().getWorldContainer().getPath()+File.separator+"playerdata";
		this.getCommand("deleteplayerdata").setExecutor(new CommandExec(this));
	}
}
