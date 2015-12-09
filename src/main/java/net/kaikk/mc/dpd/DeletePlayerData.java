package net.kaikk.mc.dpd;

import org.bukkit.plugin.java.JavaPlugin;

public class DeletePlayerData extends JavaPlugin {
	static DeletePlayerData instance;
	Config config;
	
	@Override
	public void onEnable() {
		instance=this;
		this.getCommand("deleteplayerdata").setExecutor(new CommandExec(this));
	}
}
