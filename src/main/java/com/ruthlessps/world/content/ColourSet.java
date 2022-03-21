package com.ruthlessps.world.content;

import com.ruthlessps.world.entity.impl.player.Player;

public class ColourSet {
	public int textColour;
	public int textShadowColour;
	public int titleColour;
	public int titleShadowColour;

	public int titleAlpha;
	public int titleShadowAlpha;
	public int textAlpha;
	public int textShadowAlpha;

	public boolean full;

	public ColourSet() {
		textColour = 0xffffff;
		textShadowColour = 0x000000;
		titleColour = 0xffffff;
		titleShadowColour = 0x000000;
		titleAlpha = 256;
		titleShadowAlpha = 256;
		textAlpha = 256;
		textShadowAlpha = 256;
		full = false;
	}

	public void setColours(ColourSet set) {
		textColour = set.textColour;
		textShadowColour = set.textShadowColour;
		titleColour = set.titleColour;
		titleShadowColour = set.titleShadowColour;
		titleAlpha = set.titleAlpha;
		titleShadowAlpha = set.titleShadowAlpha;
		textAlpha = set.textAlpha;
		textShadowAlpha = set.textShadowAlpha;
	}

	public static void loadColours(Player player) {
		sendColours(player, 1, player.yellColours.presets[0]);
		sendColours(player, 2, player.yellColours.presets[1]);
		sendColours(player, 3, player.yellColours.presets[2]);
		sendColours(player, 4, player.capeColours.presets[0]);
		sendColours(player, 5, player.capeColours.presets[1]);
		sendColours(player, 6, player.capeColours.presets[2]);
	}

	public static void sendColours(Player player, int type, ColourSet set) {
		String input = set.titleColour + "_" + set.titleShadowColour + "_" + set.textColour + "_" + set.textShadowColour
				+ "_" + set.titleAlpha + "_" + set.textAlpha + "_" + set.titleShadowAlpha + "_" + set.textShadowAlpha;
		player.getPacketSender().sendMessage(":preset:_" + type + "_" + input);
	}

	public static void setColours(Player player, ColourSet set, String type[]) {
		set.titleColour = Integer.parseInt(type[1]);
		set.textColour = Integer.parseInt(type[2]);
		set.titleShadowColour = Integer.parseInt(type[3]);
		set.textShadowColour = Integer.parseInt(type[4]);
		set.titleAlpha = Integer.parseInt(type[5]);
		set.textAlpha = Integer.parseInt(type[6]);
		set.titleShadowAlpha = Integer.parseInt(type[7]);
		set.textShadowAlpha = Integer.parseInt(type[8]);
	}

	public static void setColours(Player player, ColourSet set, int type[]) {
		set.titleColour = type[0];
		set.textColour = type[1];
		set.titleShadowColour = type[2];
		set.textShadowColour = type[3];
		set.titleAlpha = type[4];
		set.textAlpha = type[5];
		set.titleShadowAlpha = type[6];
		set.textShadowAlpha = type[7];
	}

	public static int[] toArray(ColourSet set) {
		return new int[] { set.titleColour, set.textColour, set.titleShadowColour, set.textShadowColour, set.titleAlpha,
				set.textAlpha, set.titleShadowAlpha, set.textShadowAlpha, };
	}
}