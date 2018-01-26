package com.hotmail.steven.minisigns;

import org.bukkit.scheduler.BukkitRunnable;

import com.garbagemule.MobArena.framework.Arena;

import me.poutineqc.cuberunner.game.GameState;

public class SignUpdater extends BukkitRunnable {

	@Override
	public void run() {
		
		for(MiniSign sign : MiniSigns.getSignManager().getRegisteredSigns())
		{
			updateSign(sign);
		}
		
	}
	
	/**
	 * Updates the MiniSign with values live values
	 * @param miniSign
	 */
	public void updateSign(MiniSign miniSign)
	{
		switch(miniSign.getType())
		{
		case MOBARENA:
			updateMobarenaSign(miniSign);
			break;
		case CUBERUN:
			updateCuberunnerSign(miniSign);
			break;
		}
	}
	
	/**
	 * Updates a Mobarena sign
	 * @param sign
	 */
	public void updateMobarenaSign(MiniSign sign)
	{
		Arena arena = MiniSigns.getMobArena().getArenaMaster().getArenaWithName(sign.getArenaName());
		if(arena != null)
		{
			sign.setMaxPlayers(arena.getMaxPlayers());
			sign.setPlayerCount(arena.getAllPlayers().size());
			if(arena.isRunning()) 
			{
				sign.start();
			} else
			{
				sign.stop();
			}
		}
	}
	
	/**
	 * Updates a Cuberunner sign
	 * @param sign
	 */
	public void updateCuberunnerSign(MiniSign sign)
	{
		me.poutineqc.cuberunner.game.Arena arena = me.poutineqc.cuberunner.game.Arena.getArena(sign.getArenaName());
		if(arena != null)
		{
			sign.setMaxPlayers(arena.getMaxPlayer());
			sign.setPlayerCount(arena.getAmountOfPlayerInGame());
			if(arena.getGameState() == GameState.ACTIVE)
			{
				sign.start();
			} else
			{
				sign.stop();
			}
		}
	}

}
