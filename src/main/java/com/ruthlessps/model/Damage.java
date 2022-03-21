package com.ruthlessps.model;

public class Damage {

	private final Hit[] hits;

	private boolean shown;

	public Damage(Hit... hits) {
		if (hits.length > 2 || hits.length <= 0)
			throw new IllegalArgumentException("Hit array length cannot be less than 1 and cannot be greater than 2!");
		this.hits = hits;
	}

	public Hit[] getHits() {
		return hits;
	}

	public boolean hasBeenShown() {
		return shown;
	}

	public void setShown(boolean b) {
		this.shown = b;
	}
}