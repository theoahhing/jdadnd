/**
    Roll command provides dice rolling functionality for the bot, allowing users
    to roll a die with a specified number of sides through a slash command interaction.
*/

package com.bktvsolutions.command;

import com.bktvsolutions.service.DiceService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class RollCommand implements Command {
    private final DiceService diceService;

    /**
        Creates a roll command with a default dice service.
    */

    public RollCommand() {
        this.diceService = new DiceService();
    }

    /**
        Creates a roll command with the provided dice service.

        @param diceService dice service to use
        @throws IllegalArgumentException if diceService is null
    */

    public RollCommand(DiceService diceService) {
        if (diceService == null) {
            throw new IllegalArgumentException("diceService cannot be null");
        }

        this.diceService = diceService;
    }

    /**
        Gets the name of the command as used in Discord.

        @return command name
    */

    @Override
    public String getName() {
        return "roll";
    }

    /**
        Executes the roll command.

        @param event slash command interaction event
    */

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("event cannot be null");
        }

        int sides = 20;

        if (event.getOption("sides") != null) {
            sides = (int) event.getOption("sides").getAsLong();
        }

        if (sides <= 0) {
            event.reply("The number of sides must be greater than 0.")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        int result = diceService.rollDie(sides);

        event.reply("You rolled a d" + sides + " and got **" + result + "**.")
                .queue();
    }
}