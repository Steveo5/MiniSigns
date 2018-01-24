package com.hotmail.steven.minisigns;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import com.garbagemule.MobArena.framework.Arena;
import com.hotmail.steven.minisigns.util.StringUtil;

import net.md_5.bungee.api.ChatColor;

public class MiniSignCreateListener implements Listener {

	private MiniSigns plugin;
	
	public MiniSignCreateListener(MiniSigns plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent evt)
	{
		// Get the actual text of the sign
		String firstLine = ChatColor.stripColor(evt.getLine(0)).trim();
		String secondLine = evt.getLine(1);
		System.out.println(firstLine.charAt(firstLine.length() - 1));
		if(firstLine.charAt(0) == '[' && firstLine.charAt(firstLine.length() - 1) == ']')
		{
			// Remove the boxed characters
			firstLine = firstLine.replaceAll("\\[", "").replaceAll("\\]", "");
			try
			{
				SignType type = SignType.valueOf(firstLine.toUpperCase());
				Arena arena = MiniSigns.getMobArena().getArenaMaster().getArenaWithName(secondLine);
				if(arena == null || !MiniSigns.isSignEnabled(type))
				{
					evt.setLine(0, StringUtil.colorize("&c&l[Error]"));
				} else
				{
					evt.setLine(0, StringUtil.colorize("&a&l[" + StringUtil.capitalize(type.name()) + "]"));
					evt.setLine(2, arena.getAllPlayers().size() + " / " + arena.getMaxPlayers() + " players");
					evt.setLine(3, StringUtil.colorize("&5Click to join"));
					evt.getPlayer().sendMessage(StringUtil.colorize("&aArena sign created successfully!"));
					MiniSign newSign = new MiniSign(type, evt.getBlock().getLocation());
					MiniSigns.getSignManager().registerSign(newSign);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
