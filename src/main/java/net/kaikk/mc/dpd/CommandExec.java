package net.kaikk.mc.dpd;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CommandExec implements CommandExecutor {
	private DeletePlayerData instance;
	
	CommandExec(DeletePlayerData instance) {
		this.instance = instance;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player=null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}
		
		if (cmd.getName().equals("deleteplayerdata")) {
			OfflinePlayer playerToDelete;
			if (args.length==0) {
				sender.sendMessage("Usage: /"+label+" [PlayerName]");
				return false;
			}
			
			if (sender.getName().equals(args[0])) {
				if (!sender.hasPermission("deleteplayerdata.self")) {
					sender.sendMessage("Insufficient permissions");
					return false;
				}
				playerToDelete=player;
			} else {
				if (!sender.hasPermission("deleteplayerdata.others")) {
					sender.sendMessage("Insufficient permissions");
					return false;
				}
				playerToDelete = instance.getServer().getPlayer(args[0]);
			}
			
			if (playerToDelete==null || !playerToDelete.hasPlayedBefore()) {
				sender.sendMessage("No player data found on this server.");
				return false;
			}
			
			sender.sendMessage(playerToDelete.getName()+"'s player data is going to be deleted.");
			
			if (playerToDelete.isOnline()) {
				((Player)playerToDelete).kickPlayer("Your player data is going to be deleted.");
			}
			
			scheduleDelete(playerToDelete.getUniqueId());
		}
		return false;
	}
	
	void scheduleDelete(final UUID uuid) {
		final String uuidString = uuid.toString();
		new BukkitRunnable() {
			@Override
			public void run() {
				instance.getLogger().info("Deleting '"+instance.config.playerDataPath+"/"+uuidString+".dat'...");
				
				File file = new File(instance.config.playerDataPath+File.separator+uuidString+".dat");
				if (file.delete()) {
					instance.getLogger().info("Deleted '"+instance.config.playerDataPath+"/"+uuidString+".dat'!");
					this.cancel();
				}
			}
		}.runTaskTimerAsynchronously(DeletePlayerData.instance, 4L, 4L);
	}
}
