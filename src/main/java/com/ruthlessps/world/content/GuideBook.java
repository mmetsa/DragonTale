package com.ruthlessps.world.content;

public class GuideBook extends Book {

	private static final String[][] pages = {

			// NOTE: 10 lines per page, 43 letters per line.

			/*
			 * Page 0.
			 */
			/*
			 * Page 3.
			 */
			{ "Player+ Commands", "::benefits - benefits of donating", "::commands - list of all commands",
					"::home - teles to home", "::train - teles to train", "::pointzone - teles to point zone",
					"::american - teles to american torvas", "::oreo - teles to oreo torvas",
					"::sky - teles to sky torvas", "::darth - teles to darth torvas", },
			/*
			 * Page 4.
			 */
			{ "::cash - teles to cash torvas", "::silver - teles to silver torvas", "::camo - teles to camo boss",
					"::winter - teles to winter boss", "::bloodshot - teleports to bloodshot boss",
					"::gamble - teleports to gambling zone", "::duel - teles to duel arena",
					"::zombies - teles to zombies starting point", "::rfd - teles to recipe for disaster ",
					"starting point", },
			
			{ "::freezer - teleports you to a boss", "::ele - teleports you to a boss", "",
						"::scorpion - teleports you to a boss", "::mageisland - teleports you to mage island",
						"::legion - teleports you to legion", "::zarthyx - teleports you to zarthyx", "::rucord - teleports you to rucord", },
			/*
			 * Page 5.
			 */
			{ "::nomad - teles to nomad starting point", "::1v1 - teles to edgeville ditch",
					"::multi - teleports to varrock ditch", "::vote - opens the webpage",
					"::donate - opens the webpage", "::forums - opens the webpage", "::hiscores - opens the webpage",
					"::store - opens the webpage", "::droplog - opens the interface.", "::help - request help",
					"::discord - opens the webpage", },
			/*
			 * Page 6.
			 */
			{ "::report - opens the webpage", "::rules - opens the webpage", "::support - opens the webpage",
					"::players - shows players online interface", "::empty - emptys your inventory",
					"::emptybank - clears the players bank", "::save - saves your account",
					"::guides - opens webpage for in-game guides", "::giveaway - opens giveaway", },
			/*
			 * Page 7.
			 */
			{ "Donator Commands", "::yell (message here) - sends yell message", "::dzone - teleports to donor zone", "",
					"Deluxe Donor+ Commands", "::ddzone - teleport to deluxe donor zone", "::bank - opens bank", "+ All Donor Commands", },
			/*
			 * Page 8.
			 */
			{ "Sponsor Commands", "::szone - teleports to sponsor zone", "+ All Donor/Deluxe Commands" }, { "" } };

	public GuideBook() {
		super(pages);
	}

	@Override
	public String getName() {
		return "Guide Book";
	}

}
