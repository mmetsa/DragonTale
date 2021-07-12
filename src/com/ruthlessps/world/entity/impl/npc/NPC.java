package com.ruthlessps.world.entity.impl.npc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.NPCDeathTask;
import com.ruthlessps.model.Direction;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.model.Position;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.util.JsonLoader;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.combat.CombatFactory;
import com.ruthlessps.world.content.combat.CombatType;
import com.ruthlessps.world.content.combat.effect.CombatPoisonEffect.PoisonType;
import com.ruthlessps.world.content.combat.strategy.CombatStrategies;
import com.ruthlessps.world.content.combat.strategy.CombatStrategy;
import com.ruthlessps.world.content.skill.impl.hunter.Hunter;
import com.ruthlessps.world.entity.impl.Character;
import com.ruthlessps.world.entity.impl.npc.NPCMovementCoordinator.Coordinator;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Represents a non-playable character, which players can interact with.
 * 
 * @author Gabriel Hannason
 */

public class NPC extends Character {

	/**
	 * Prepares the dynamic json loader for loading world npcs.
	 * 
	 * @return the dynamic json loader.
	 * @throws Exception
	 *             if any errors occur while preparing for load.
	 */
	public static void init() {
		new JsonLoader() {
			@Override
			public String filePath() {
				return "./data/def/json/world_npcs.json";
			}

			@Override
			public void load(JsonObject reader, Gson builder) {
				if (!reader.has("npc-id"))
					return;
				int id = reader.get("npc-id").getAsInt();
				Position position = builder.fromJson(reader.get("position").getAsJsonObject(), Position.class);
				Coordinator coordinator = builder.fromJson(reader.get("walking-policy").getAsJsonObject(),
						Coordinator.class);
				Direction direction = Direction.valueOf(reader.get("face").getAsString());
				NPC npc = new NPC(id, position);
				npc.movementCoordinator.setCoordinator(coordinator);
				npc.setDirection(direction);
				World.register(npc);
				if (id > 5070 && id < 5081) {
					Hunter.HUNTER_NPC_LIST.add(npc);
				}
				position = null;
				coordinator = null;
				direction = null;
			}
		}.load();
	}

	/*
	 * Fields
	 */
	/** INSTANCES **/
	private final Position defaultPosition;

	private NPCMovementCoordinator movementCoordinator = new NPCMovementCoordinator(this);

	private Player spawnedFor;

	private NpcDefinition definition;

	/** INTS **/
	private final int id;

	private int constitution = 100;

	private int defaultConstitution;

	private int transformationId = -1;

	/** BOOLEANS **/
	private boolean[] attackWeakened = new boolean[3], strengthWeakened = new boolean[3];

	private boolean summoningNpc, summoningCombat;

	private boolean isDying;

	private boolean visible = true;

	private boolean healed, chargingAttack;

	private boolean findNewTarget;

	private long change_target;

	public NPC(int id, Position position) {
		super(position);
		NpcDefinition definition = NpcDefinition.forId(id);
		if (definition == null)
			throw new NullPointerException("NPC " + id + " is not defined!");
		this.defaultPosition = position;
		this.id = id;
		this.definition = definition;
		this.defaultConstitution = definition.getHitpoints() < 100 ? 100 : definition.getHitpoints();
		this.constitution = defaultConstitution;
		setLocation(Location.getLocation(this));
	}

	@Override
	public void appendDeath() {
		if (!isDying && !summoningNpc) {
			TaskManager.submit(new NPCDeathTask(this));
			isDying = true;
		}
	}

	@Override
	public CombatStrategy determineStrategy() {
		return CombatStrategies.getStrategy(id);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof NPC && ((NPC) other).getIndex() == getIndex();
	}

	public boolean findNewTarget() {
		return findNewTarget;
	}

	public boolean canChangeTarget() {
		return (System.currentTimeMillis() - change_target) > 3000;
	}

	public int getAggressionDistance() {
		int distance = 7;

		if (id == 2896) {
			distance = 50;
		}
		return distance;
	}

	@Override
	public int getAttackSpeed() {
		return this.getDefinition().getAttackSpeed();
	}

	@Override
	public int getBaseAttack(CombatType type) {
		return getDefinition().getAttackBonus();
	}

	@Override
	public int getBaseDefence(CombatType type) {

		if (type == CombatType.MAGIC)
			return getDefinition().getDefenceMage();
		else if (type == CombatType.RANGED)
			return getDefinition().getDefenceRange();

		return getDefinition().getDefenceMelee();
	}

	@Override
	public int getConstitution() {
		return constitution;
	}

	public int getDefaultConstitution() {
		return defaultConstitution;
	}

	public Position getDefaultPosition() {
		return defaultPosition;
	}

	/**
	 * @return the statsWeakened
	 */
	public boolean[] getDefenceWeakened() {
		return attackWeakened;
	}

	public NpcDefinition getDefinition() {
		return definition;
	}

	public int getId() {
		return id;
	}

	public NPCMovementCoordinator getMovementCoordinator() {
		return movementCoordinator;
	}

	/*
	 * Getters and setters
	 */

	@Override
	public int getSize() {
		return getDefinition().getSize();
	}

	public Player getSpawnedFor() {
		return spawnedFor;
	}

	/**
	 * @return the statsBadlyWeakened
	 */
	public boolean[] getStrengthWeakened() {
		return strengthWeakened;
	}

	public int getTransformationId() {
		return transformationId;
	}

	public boolean hasHealed() {
		return healed;
	}

	@Override
	public void heal(int heal) {
		if ((this.constitution + heal) > getDefaultConstitution()) {
			setConstitution(getDefaultConstitution());
			return;
		}
		setConstitution(this.constitution + heal);
	}

	public boolean isChargingAttack() {
		return chargingAttack;
	}

	public boolean isDying() {
		return isDying;
	}

	@Override
	public boolean isNpc() {
		return true;
	}

	public boolean isSummoningNpc() {
		return summoningNpc;
	}

	public boolean isVisible() {
		return visible;
	}

	@Override
	public void poisonVictim(Character victim, CombatType type) {
		if (getDefinition().isPoisonous()) {
			CombatFactory.poisonEntity(victim,
					type == CombatType.RANGED || type == CombatType.MAGIC ? PoisonType.MILD : PoisonType.EXTRA);
		}

	}

	public void sequence() {
		/**
		 * HP restoration
		 */
		if (constitution < defaultConstitution) {
			if (!isDying) {
				if (getLastCombat().elapsed((id == 13447 || id == 3200 ? 50000 : 5000))
						&& !getCombatBuilder().isAttacking() && getLocation() != Location.DUNGEONEERING) {
					setConstitution(constitution + (int) (defaultConstitution * 0.1));
					if (constitution > defaultConstitution)
						setConstitution(defaultConstitution);
				}
			}
		}
	}

	public NPC setChargingAttack(boolean chargingAttack) {
		this.chargingAttack = chargingAttack;
		return this;
	}

	@Override
	public NPC setConstitution(int constitution) {
		this.constitution = constitution;
		if (this.constitution <= 0)
			appendDeath();
		return this;
	}

	public void setDefaultConstitution(int defaultConstitution) {
		this.defaultConstitution = defaultConstitution;
	}

	public void setDying(boolean isDying) {
		this.isDying = isDying;
	}

	public void setFindNewTarget(boolean findNewTarget) {
		this.findNewTarget = findNewTarget;
	}

	public void setChangeTargetTime(long change_target) {
		this.change_target = change_target;
	}

	public void setHealed(boolean healed) {
		this.healed = healed;
	}

	public NPC setSpawnedFor(Player spawnedFor) {
		this.spawnedFor = spawnedFor;
		return this;
	}

	public void setSummoningCombat(boolean summoningCombat) {
		this.summoningCombat = summoningCombat;
	}

	public void setSummoningNpc(boolean summoningNpc) {
		this.summoningNpc = summoningNpc;
	}

	public void setTransformationId(int transformationId) {
		this.transformationId = transformationId;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean summoningCombat() {
		return summoningCombat;
	}

	public boolean switchesVictim() {
		if (getLocation() == Location.DUNGEONEERING) {
			return true;
		}
		return id == 6263 || id == 6265 || id == 6203 || id == 6208 || id == 6206 || id == 6247 || id == 6250
				|| id == 3200 || id == 4540 || id == 1158 || id == 1160 || id == 8133 || id == 13447 || id == 13451
				|| id == 13452 || id == 13453 || id == 13454 || id == 2896 || id == 2882 || id == 2881 || id == 6260;
	}

	public boolean isBoss() {
		return id == 515 || id == 517 || id == 518;
	}
}
