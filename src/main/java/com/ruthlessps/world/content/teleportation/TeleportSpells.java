package com.ruthlessps.world.content.teleportation;

import java.util.Arrays;

import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;

public enum TeleportSpells {
	// Normal spells.
	VARROCK(1164, 25, 35, new Position(3210, 3424), new Item(554, 1), new Item(556, 3), new Item(563, 1)), LUMBRIDGE(
			1167, 31, 41, new Position(3222, 3218), new Item(556, 3), new Item(557, 1), new Item(563, 1)), FALADOR(1170,
					37, 48, new Position(2964, 3378), new Item(563, 1), new Item(555, 1), new Item(556, 3)), CAMELOT(
							1174, 45, 55, new Position(2757, 3477), new Item(563, 1), new Item(556, 5)), ARDOUGNE(1540,
									51, 61, new Position(2662, 3305), new Item(563, 2), new Item(555, 2)), WATCHTOWER(
											1541, 58, 68, new Position(2728, 3349), new Item(563, 2),
											new Item(557, 2)), TROLLHEIM(7455, 61, 68, new Position(2888, 3675),
													new Item(563, 2), new Item(554, 2)), APE_ATOLL(18470, 64, 74,
															new Position(2749, 2785), new Item(563, 2),
															new Item(554, 2), new Item(555, 2), new Item(18199)),

	// Ancient spells.
	PADDEWWA(13035, 54, 64, new Position(3096, 9883), new Item(563, 2), new Item(554, 1), new Item(556, 1)), SENNTISTEN(
			13045, 60, 70, new Position(3323, 3336), new Item(566, 1), new Item(563, 2)), KHARYRLL(13053, 66, 76,
					new Position(3500, 3484), new Item(563, 2), new Item(565, 1)), LASSAR(13061, 72, 82,
							new Position(3006, 3472), new Item(563, 2), new Item(555, 4)), DAREEYAK(13069, 78, 88,
									new Position(2967, 3696), new Item(563, 2), new Item(554, 3),
									new Item(556, 2)), CARRALLANGAR(13079, 84, 94, new Position(3222, 3668),
											new Item(563, 2), new Item(566, 2)), ANNAKARL(13087, 90, 100,
													new Position(2604, 4768), new Item(563, 2),
													new Item(565, 2)), GHORROCK(13095, 96, 106,
															new Position(2976, 3874), new Item(563, 2),
															new Item(555, 8)),

	// Lunar spells.
	OURANIA(30083, 71, 60, new Position(0, 0, 0), new Item(563, 1), new Item(557, 6), new Item(9075, 2)), // TODO
	WATERBIRTH(30106, 72, 71, new Position(2527, 3739, 0), new Item(563, 1), new Item(9075, 2),
			new Item(555, 1)), BARBARIAN(30138, 75, 53, new Position(2535, 3567, 0), new Item(563, 2),
					new Item(9075, 2), new Item(554, 3)), KHAZARD(30162, 78, 80, new Position(2658, 3158, 0),
							new Item(555, 4), new Item(562, 2), new Item(9075, 2)), FISHING_GUILD(30226, 85, 65,
									new Position(2569, 3410, 0), new Item(555, 10), new Item(563, 3),
									new Item(9075, 3)), CATHERBY(30250, 87, 78, new Position(2808, 3440, 0),
											new Item(555, 10), new Item(563, 3), new Item(9075, 3)), ICE_PLATEAU(30266,
													89, 96, new Position(0, 0, 0), new Item(555, 8), new Item(563, 3)),// TODO
	;

	public final int button;
	public final int level;
	public final Item[] runes;
	public final Position loc;
	public final int experience;

	TeleportSpells(int button, int level, int exp, Position loc, Item... runes) {
		this.button = button;
		this.level = level;
		this.runes = runes;
		this.loc = loc;
		this.experience = exp * 100;
	}

	public static TeleportSpells get(int button) {
		return Arrays.stream(values()).filter(t -> t.button == button).findAny().orElse(null);
	}
}
