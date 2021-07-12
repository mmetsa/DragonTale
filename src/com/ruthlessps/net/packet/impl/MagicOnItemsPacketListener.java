package com.ruthlessps.net.packet.impl;

import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Animation;
import com.ruthlessps.model.Graphic;
import com.ruthlessps.model.GraphicHeight;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.content.combat.magic.MagicSpells;
import com.ruthlessps.world.content.combat.magic.Spell;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Handles magic on items.
 * 
 * @author Gabriel Hannason
 */
public class MagicOnItemsPacketListener implements PacketListener {

	final static int[][] ORE_DATA = { { 436, 1, 438, 1, 2349, 53 }, // TIN
			{ 438, 1, 436, 1, 2349, 53 }, // COPPER
			{ 440, 1, -1, -1, 2351, 53 }, // IRON ORE
			{ 442, 1, -1, -1, 2355, 53 }, // SILVER ORE
			{ 444, 1, -1, -1, 2357, 23 }, // GOLD BAR
			{ 447, 1, 453, 4, 2359, 30 }, // MITHRIL ORE
			{ 449, 1, 453, 6, 2361, 38 }, // ADDY ORE
			{ 451, 1, 453, 8, 2363, 50 }, // RUNE ORE
	};

	public static final int MAGIC_ON_GROUNDITEMS = 181;

	public static final int MAGIC_ON_ITEMS = 237;

	@SuppressWarnings("unused")
	@Override
	public void handleMessage(Player player, Packet packet) {
		if (packet.getOpcode() == MAGIC_ON_GROUNDITEMS) {
			final int itemY = packet.readLEShort();
			final int itemId = packet.readShort();
			final int itemX = packet.readLEShort();
			final int spellId = packet.readUnsignedShortA();
			final MagicSpells spell = MagicSpells.forSpellId(spellId);
			if (spell == null)
				return;
			player.getMovementQueue().reset();
			// switch(spell) {}
		} else if (packet.getOpcode() == MAGIC_ON_ITEMS) {
			int slot = packet.readShort();
			int itemId = packet.readShortA();
			int childId = packet.readShort();
			int spellId = packet.readShortA();
			System.out.println("spellId: " + spellId);
			if (!player.getClickDelay().elapsed(1300))
				return;
			if (slot < 0 || slot > player.getInventory().capacity())
				return;
			if (player.getInventory().getItems()[slot].getId() != itemId)
				return;

			if (spellId == 30154) {

				if (itemId == 6958 || itemId == 10978 || itemId == 401) {

					Item[] runes = new Item[] {

							new Item(9075, 2), new Item(554, 6), new Item(556, 10)

					};

					if (player.getSkillManager().getMaxLevel(6) < 77) {
						player.getPacketSender()
								.sendMessage("You need a magic level of atleast 77 to cast this spell.");
						return;
					}
					if (!player.getInventory().containsAll(runes)) {
						player.getPacketSender().sendMessage("You don't have the required runes to cast this spell");
						return;
					}

					for (int index = 0; index < runes.length; index++) {
						if (runes[index] != null)
							player.getInventory().delete(runes[index]);
					}

					player.performAnimation(new Animation(4413));
					player.performGraphic(new Graphic(729));

					player.getInventory().delete(new Item(itemId, 1));
					player.getInventory().add(new Item(1775, 1));
					player.getSkillManager().addExperience(Skill.MAGIC, 78);
					return;

				}

			}

			Item item = new Item(itemId);
			MagicSpells magicSpell = MagicSpells.forSpellId(spellId);
			if (magicSpell == null)
				return;
			Spell spell = magicSpell.getSpell();
			switch (magicSpell) {

			case LOW_ALCHEMY:
			case HIGH_ALCHEMY:
				if (!item.tradeable() || !item.sellable() || item.getId() == 995) {
					player.getPacketSender().sendMessage("This spell can not be cast on this item.");
					return;
				}
				if (spell == null || !spell.canCast(player, true))
					return;
				player.getInventory().delete(itemId, 1).add(995, 200
						+ (int) (item.getDefinition().getValue() * (magicSpell == MagicSpells.HIGH_ALCHEMY ? 1 : 0.8)));
				player.performAnimation(new Animation(712));
				player.performGraphic(
						new Graphic(magicSpell == MagicSpells.HIGH_ALCHEMY ? 113 : 112, GraphicHeight.LOW));
				player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
				player.getPacketSender().sendTab(GameSettings.MAGIC_TAB);
				break;
			case SUPERHEAT_ITEM:
				for (int i = 0; i < ORE_DATA.length; i++) {
					if (item.getId() == ORE_DATA[i][0]) {
						if (player.getInventory().getAmount(ORE_DATA[i][2]) < ORE_DATA[i][3]) {
							player.getPacketSender().sendMessage("You do not have enough "
									+ new Item(ORE_DATA[i][2]).getDefinition().getName() + "s for this spell.");
							return;
						}
						if (spell == null || !spell.canCast(player, true))
							return;
						player.getInventory().delete(item.getId(), 1);
						for (int k = 0; k < ORE_DATA[i][3]; k++)
							player.getInventory().delete(ORE_DATA[i][2], 1);
						player.performAnimation(new Animation(725));
						player.performGraphic(new Graphic(148, GraphicHeight.HIGH));
						player.getInventory().add(ORE_DATA[i][4], 1);
						player.getPacketSender().sendTab(GameSettings.MAGIC_TAB);
						player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
						return;
					}
				}
				player.getPacketSender().sendMessage("This spell can only be cast on Mining ores.");
				break;
			case BAKE_PIE:
				if (itemId == 2317 || itemId == 2319 || itemId == 2321) {
					player.getSkillManager().addExperience(Skill.MAGIC, spell.baseExperience());
					player.performAnimation(new Animation(4413));
					player.performGraphic(new Graphic(746, GraphicHeight.HIGH));
					player.getInventory().delete(item.getId(), 1);
					player.getPacketSender().sendMessage("You bake the pie");
					player.getInventory()
							.add(itemId == 2317 ? 2323 : itemId == 2319 ? 2327 : itemId == 2321 ? 2325 : -1, 1);
				} else
					player.getPacketSender().sendMessage("This spell can only be cast on an uncooked pie.");
				break;
			default:
				break;
			}
			player.getClickDelay().reset();
			player.getInventory().refreshItems();
		}
	}
}
