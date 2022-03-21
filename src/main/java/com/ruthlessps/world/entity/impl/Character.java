package com.ruthlessps.world.entity.impl;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Direction;
import com.ruthlessps.model.Flag;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.Hit;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.RegionInstance;
import com.ruthlessps.model.UpdateFlag;
import com.ruthlessps.model.movement.MovementQueue;
import com.ruthlessps.util.Stopwatch;
import com.ruthlessps.world.content.combat.CombatBuilder;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.magic.CombatSpell;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.entity.Entity;

/**
 * A player or NPC
 * 
 * @author Gabriel Hannason
 */

public abstract class Character extends Entity {

	/*** STRINGS ***/
	private String forcedChat;

	/*
	 * Fields
	 */

	/*** INSTANCES ***/
	private Direction direction, primaryDirection = Direction.NONE, secondaryDirection = Direction.NONE,
			lastDirection = Direction.NONE;

	/*** LONGS **/

	private CombatBuilder combatBuilder = new CombatBuilder(this);
	private MovementQueue movementQueue = new MovementQueue(this);
	private Stopwatch lastCombat = new Stopwatch();
	private UpdateFlag updateFlag = new UpdateFlag();
	private Location location;
	private Position positionToFace;
	private Animation animation;
	private Graphic graphic;
	private Entity interactingEntity;
	public Position singlePlayerPositionFacing;
	private CombatSpell currentlyCasting;
	private Hit primaryHit;
	private Hit secondaryHit;
	private RegionInstance regionInstance;
	/*** INTS ***/
	private int npcTransformationId;

	private int poisonDamage;
	private int freezeDelay;
	/*** BOOLEANS ***/
	private boolean[] prayerActive = new boolean[30], curseActive = new boolean[20];

	private boolean registered;
	private boolean teleporting;
	private boolean resetMovementQueue;
	private boolean needsPlacement;
	private boolean moving;

	public Character(Position position) {
		super(position);
		location = Location.getLocation(this);
	}

	public abstract void appendDeath();

	/**
	 * Deals one damage to this entity.
	 * 
	 * @param hit
	 *            the damage to be dealt.
	 */
	public void dealDamage(Hit hit) {
		if (getUpdateFlag().flagged(Flag.SINGLE_HIT)) {
			dealSecondaryDamage(hit);
			return;
		}
		if (getConstitution() <= 0)
			return;
		primaryHit = decrementHealth(hit);
		getUpdateFlag().flag(Flag.SINGLE_HIT);
	}
	
	public void dealFakeDamage(Hit hit) {
		if (getUpdateFlag().flagged(Flag.SINGLE_HIT)) {
			//dealSecondaryDamage(hit);
			return;
		}
		if (getConstitution() <= 0)
			return;
		primaryHit = hit;
		decrementHealth(new Hit(0));
		getUpdateFlag().flag(Flag.SINGLE_HIT);
	}

	/**
	 * Deals two damage splats to this entity.
	 * 
	 * @param hit
	 *            the first hit.
	 * @param secondHit
	 *            the second hit.
	 */
	public void dealDoubleDamage(Hit hit, Hit secondHit) {
		dealDamage(hit);
		dealSecondaryDamage(secondHit);
	}
	
	public void dealFakeDoubleDamage(Hit hit, Hit secondHit) {
		dealFakeDamage(hit);
		dealFakeSecondaryDamage(secondHit);
	}

	/**
	 * Deals four damage splats to this entity.
	 * 
	 * @param hit
	 *            the first hit.
	 * @param secondHit
	 *            the second hit.
	 * @param thirdHit
	 *            the third hit.
	 * @param fourthHit
	 *            the fourth hit.
	 */
	public void dealQuadrupleDamage(Hit hit, Hit secondHit, final Hit thirdHit, final Hit fourthHit) {
		dealDoubleDamage(hit, secondHit);

		TaskManager.submit(new Task(1, this, false) {
			@Override
			public void execute() {
				if (!registered) {
					this.stop();
					return;
				}
				dealDoubleDamage(thirdHit, fourthHit);
				this.stop();
			}
		});
	}
	
	public void dealFakeQuadrupleDamage(Hit hit, Hit secondHit, final Hit thirdHit, final Hit fourthHit) {
		dealFakeDoubleDamage(hit, secondHit);

		TaskManager.submit(new Task(1, this, false) {
			@Override
			public void execute() {
				if (!registered) {
					this.stop();
					return;
				}
				dealFakeDoubleDamage(thirdHit, fourthHit);
				this.stop();
			}
		});
	}

	/**
	 * Deal secondary damage to this entity.
	 * 
	 * @param hit
	 *            the damage to be dealt.
	 */
	private void dealSecondaryDamage(Hit hit) {
		secondaryHit = decrementHealth(hit);
		getUpdateFlag().flag(Flag.DOUBLE_HIT);
	}
	
	private void dealFakeSecondaryDamage(Hit hit) {
		secondaryHit = hit;
		decrementHealth(new Hit(0));
		getUpdateFlag().flag(Flag.DOUBLE_HIT);
	}

	/**
	 * Deals three damage splats to this entity.
	 * 
	 * @param hit
	 *            the first hit.
	 * @param secondHit
	 *            the second hit.
	 * @param thirdHit
	 *            the third hit.
	 */
	public void dealTripleDamage(Hit hit, Hit secondHit, final Hit thirdHit) {
		dealDoubleDamage(hit, secondHit);

		TaskManager.submit(new Task(1, this, false) {
			@Override
			public void execute() {
				if (!registered) {
					this.stop();
					return;
				}
				dealDamage(thirdHit);
				this.stop();
			}
		});
	}
	
	public void dealFakeTripleDamage(Hit hit, Hit secondHit, final Hit thirdHit) {
		dealFakeDoubleDamage(hit, secondHit);

		TaskManager.submit(new Task(1, this, false) {
			@Override
			public void execute() {
				if (!registered) {
					this.stop();
					return;
				}
				dealFakeDamage(thirdHit);
				this.stop();
			}
		});
	}

	public int decrementAndGetFreezeDelay() {
		return this.freezeDelay--;
	}

	public Hit decrementHealth(Hit hit) {
		if (getConstitution() <= 0)
			return hit;
		if (hit.getDamage() > getConstitution())
			hit.setDamage(getConstitution());
		if (hit.getDamage() < 0)
			hit.setDamage(0);
		int outcome = getConstitution() - hit.getDamage();
		if (outcome < 0)
			outcome = 0;
		setConstitution(outcome);
		return hit;
	}

	/*
	 * Getters and setters Also contains methods.
	 */

	public void delayedMoveTo(final Position teleportTarget, final int delay) {
		if (moving)
			return;
		moving = true;
		TaskManager.submit(new Task(delay, this, false) {
			@Override
			protected void execute() {
				moveTo(teleportTarget);
				stop();
			}

			@Override
			public void stop() {
				setEventRunning(false);
				moving = false;
			}
		});
	}

	public abstract CombatStrategy determineStrategy();

	public Character forceChat(String message) {
		setForcedChat(message);
		getUpdateFlag().flag(Flag.FORCED_CHAT);
		return this;
	}

	public int getAndDecrementPoisonDamage() {
		return poisonDamage -= 15;
	}

	public Animation getAnimation() {
		return animation;
	}

	public abstract int getAttackSpeed();

	public abstract int getBaseAttack(CombatType type);

	public abstract int getBaseDefence(CombatType type);

	/**
	 * Gets the combat session.
	 * 
	 * @return the combat session.
	 */
	public CombatBuilder getCombatBuilder() {
		return combatBuilder;
	}

	public abstract int getConstitution();

	public CombatSpell getCurrentlyCasting() {
		return currentlyCasting;
	}

	public boolean[] getCurseActive() {
		return curseActive;
	}

	public Direction getDirection() {
		return direction;
	}

	public String getForcedChat() {
		return forcedChat;
	}

	public int getFreezeDelay() {
		return freezeDelay;
	}

	public Graphic getGraphic() {
		return graphic;
	}

	public Entity getInteractingEntity() {
		return interactingEntity;
	}

	/**
	 * @return the lastCombat
	 */
	public Stopwatch getLastCombat() {
		return lastCombat;
	}

	/**
	 * Gets the last direction this character was facing.
	 *
	 * @return the last direction.
	 */
	public final Direction getLastDirection() {
		return lastDirection;
	}

	public Location getLocation() {
		return location;
	}

	public MovementQueue getMovementQueue() {
		return movementQueue;
	}

	public int getNpcTransformationId() {
		return npcTransformationId;
	}

	public int getPoisonDamage() {
		return poisonDamage;
	}

	public Position getPositionToFace() {
		return positionToFace;
	}

	public boolean[] getPrayerActive() {
		return prayerActive;
	}

	public Direction getPrimaryDirection() {
		return primaryDirection;
	}

	/**
	 * Get the primary hit for this entity.
	 * 
	 * @return the primaryHit.
	 */
	public Hit getPrimaryHit() {
		return primaryHit;
	}

	public RegionInstance getRegionInstance() {
		return regionInstance;
	}

	public Direction getSecondaryDirection() {
		return secondaryDirection;
	}

	/**
	 * Get the secondary hit for this entity.
	 * 
	 * @return the secondaryHit.
	 */
	public Hit getSecondaryHit() {
		return secondaryHit;
	}

	public UpdateFlag getUpdateFlag() {
		return updateFlag;
	}

	public abstract void heal(int damage);

	public boolean isFrozen() {
		return freezeDelay > 0;
	}

	public boolean isNeedsPlacement() {
		return needsPlacement;
	}

	public boolean isPoisoned() {
		if (poisonDamage < 0)
			poisonDamage = 0;
		return poisonDamage != 0;
	}

	/**
	 * Gets if this entity is registered.
	 * 
	 * @return the unregistered.
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * Determines if this character needs to reset their movement queue.
	 *
	 * @return {@code true} if this character needs to reset their movement queue,
	 *         {@code false} otherwise.
	 */
	public final boolean isResetMovementQueue() {
		return resetMovementQueue;
	}

	public boolean isTeleporting() {
		return this.teleporting;
	}

	public Character moveTo(Position teleportTarget) {
		getMovementQueue().reset();
		super.setPosition(teleportTarget.copy());
		setNeedsPlacement(true);
		setResetMovementQueue(true);
		setTeleporting(true);
		if (isPlayer()) {
			getMovementQueue().handleRegionChange();
		}
		return this;
	}

	@Override
	public void performAnimation(Animation animation) {
		if (animation == null)
			return;
		setAnimation(animation);
	}

	@Override
	public void performGraphic(Graphic graphic) {
		if (graphic == null)
			return;
		setGraphic(graphic);
	}

	public abstract void poisonVictim(Character victim, CombatType type);

	/**
	 * Prepares to cast the argued spell on the argued victim.
	 * 
	 * @param spell
	 *            the spell to cast.
	 * @param victim
	 *            the victim to cast the spell on.
	 */
	public void prepareSpell(CombatSpell spell, Character victim) {
		currentlyCasting = spell;
		currentlyCasting.startCast(this, victim);
	}

	public Character setAnimation(Animation animation) {
		this.animation = animation;
		getUpdateFlag().flag(Flag.ANIMATION);
		return this;
	}

	/*** ABSTRACT METHODS ***/
	public abstract Character setConstitution(int constitution);

	public void setCurrentlyCasting(CombatSpell currentlyCasting) {
		this.currentlyCasting = currentlyCasting;
	}

	public Character setCurseActive(boolean[] curseActive) {
		this.curseActive = curseActive;
		return this;
	}

	public Character setCurseActive(int id, boolean curseActive) {
		this.curseActive[id] = curseActive;
		return this;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
		int[] directionDeltas = direction.getDirectionDelta();
		setPositionToFace(getPosition().copy().add(directionDeltas[0], directionDeltas[1]));
	}

	public Character setEntityInteraction(Entity entity) {
		this.interactingEntity = entity;
		getUpdateFlag().flag(Flag.ENTITY_INTERACTION);
		return this;
	}

	public Character setForcedChat(String forcedChat) {
		this.forcedChat = forcedChat;
		return this;
	}

	public void setFreezeDelay(int freezeDelay) {
		this.freezeDelay = freezeDelay;
	}

	public Character setGraphic(Graphic graphic) {
		this.graphic = graphic;
		getUpdateFlag().flag(Flag.GRAPHIC);
		return this;
	}

	/*
	 * Movement queue
	 */

	/**
	 * Sets the value for {@link CharacterNode#lastDirection}.
	 *
	 * @param lastDirection
	 *            the new value to set.
	 */
	public final void setLastDirection(Direction lastDirection) {
		this.lastDirection = lastDirection;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Character setMovementQueue(MovementQueue movementQueue) {
		this.movementQueue = movementQueue;
		return this;
	}

	public void setNeedsPlacement(boolean needsPlacement) {
		this.needsPlacement = needsPlacement;
	}

	public Character setNpcTransformationId(int npcTransformationId) {
		this.npcTransformationId = npcTransformationId;
		return this;
	}

	public void setPoisonDamage(int poisonDamage) {
		this.poisonDamage = poisonDamage;
	}

	public Character setPositionToFace(Position positionToFace) {
		this.positionToFace = positionToFace;
		getUpdateFlag().flag(Flag.FACE_POSITION);
		return this;
	}

	public Character setPrayerActive(boolean[] prayerActive) {
		this.prayerActive = prayerActive;
		return this;
	}

	public Character setPrayerActive(int id, boolean prayerActive) {
		this.prayerActive[id] = prayerActive;
		return this;
	}

	public void setPrimaryDirection(Direction primaryDirection) {
		this.primaryDirection = primaryDirection;
	}

	public void setRegionInstance(RegionInstance regionInstance) {
		this.regionInstance = regionInstance;
	}

	/**
	 * Sets if this entity is registered,
	 * 
	 * @param unregistered
	 *            the unregistered to set.
	 */
	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	/**
	 * Sets the value for {@link CharacterNode#resetMovementQueue}.
	 *
	 * @param resetMovementQueue
	 *            the new value to set.
	 */
	public final void setResetMovementQueue(boolean resetMovementQueue) {
		this.resetMovementQueue = resetMovementQueue;
	}

	/**
	 * Sets the value for {@link CharacterNode#secondaryDirection}.
	 *
	 * @param secondaryDirection
	 *            the new value to set.
	 */
	public final void setSecondaryDirection(Direction secondaryDirection) {
		this.secondaryDirection = secondaryDirection;
	}

	public Character setTeleporting(boolean teleporting) {
		this.teleporting = teleporting;
		return this;
	}
}