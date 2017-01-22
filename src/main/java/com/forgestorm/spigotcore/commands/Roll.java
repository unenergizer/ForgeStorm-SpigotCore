package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.constants.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public class Roll implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

		Player player = (Player) commandSender;

		if (args.length == 0) {
			roll(player, 100);
		} else if (args.length == 1) {

			int roll = Integer.parseInt(args[0]);
			roll(player, roll);

		}
		return false;
	}

	private void roll(Player player, int diceRoll) {
		try {
			String name = player.getName();
			String num = Integer.toString(diceRoll);
			int roll = onDiceRoll(diceRoll);

			String message = Messages.ROLL.toString().replace("%player%", name).replace("%s", Integer.toString(roll)).replace("%f", num);
			player.sendMessage(message);

			//Get players near by that will receive this message.
			List<Entity> localPlayers = player.getNearbyEntities(20, 20, 20);
			int messagesReceived = 0;
			
			//Loop through the list of entities.
			for (Entity entity : localPlayers) {
				//The entity that is near by the player
				//If the entity is a player, then send them the message.
				if (entity instanceof Player) {

					//Finally display the local message to near by players.
					entity.sendMessage(message);
					messagesReceived++;
				}
			}
			
			//If no one is around to hear their message, let the player know.
			if (messagesReceived == 0) {
				player.sendMessage("");
				player.sendMessage(Messages.ROLL_UNHEARD.toString());
			}

		} catch (NumberFormatException e) {
			player.sendMessage(" ");
			player.sendMessage(Messages.GAME_BAR_ROLL.toString());
			player.sendMessage(" ");
			player.sendMessage(Messages.ROLL_ERROR.toString());
			player.sendMessage(" ");
			player.sendMessage(Messages.ROLL_EXAMPLE.toString());
			player.sendMessage(Messages.BAR_BOTTOM.toString());
		}
	}

	/**
	 * Displays a virtual dice roll in the players chat console.
	 * 
	 * @param diceSize How big the dice should be.
	 * @return The number the dice landed on after it was rolled.
	 */
	private int onDiceRoll(int diceSize) {
		if (diceSize < 10000) {
			return (int) (Math.random() * diceSize) + 1;
		} else {
			return (int) (Math.random() * 10000) + 1;
		}
	}
}
