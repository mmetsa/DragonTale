package com.ruthlessps.world.content.teleportation;

import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.util.Misc;

public enum TeleportType {

	// NORMAL
	NORMAL(3, new Animation(8939, 20), new Animation(8941), null, null),
	// ANCIENTS
	ANCIENT(5, new Animation(9599), new Animation(8941), new Graphic(1681, 0), null),
	// LUNAR
	LUNAR(4, new Animation(9606), new Animation(9013), new Graphic(1685), null),
	// TELE TAB
	TELE_TAB(2, new Animation(4731), Animations.DEFAULT_RESET_ANIMATION, new Graphic(678), null),
	// RING TELE
	RING_TELE(2, new Animation(9603), Animations.DEFAULT_RESET_ANIMATION, new Graphic(1684), null),
	// LEVER
	LEVER(-1, null, null, null, null),
	// PURO PURO
	PURO_PURO(9, new Animation(6601), Animations.DEFAULT_RESET_ANIMATION, new Graphic(1118), null);

	static class Animations {
		static Animation DEFAULT_RESET_ANIMATION = new Animation(65535);
	}

	private Animation startAnim, endAnim;
	private Graphic startGraphic, endGraphic;
	private int startTick;

	TeleportType(int startTick, Animation startAnim, Animation endAnim, Graphic startGraphic, Graphic endGraphic) {
		this.startTick = startTick;
		this.startAnim = startAnim;
		this.endAnim = endAnim;
		this.startGraphic = startGraphic;
		this.endGraphic = endGraphic;
	}

	public Animation getEndAnimation() {
		return endAnim;
	}

	public Graphic getEndGraphic() {
		return endGraphic;
	}

	public Animation getStartAnimation() {
		return startAnim;
	}

	public Graphic getStartGraphic() {
		return this == NORMAL ? new Graphic(1512 + Misc.getRandom(2)) : startGraphic;
	}

	public int getStartTick() {
		return startTick;
	}
}
