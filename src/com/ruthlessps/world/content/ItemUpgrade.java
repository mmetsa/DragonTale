package com.ruthlessps.world.content;

import com.ruthlessps.model.Item;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.world.entity.impl.player.Player;

public class ItemUpgrade {

    public enum ItemData {

        ITEM1("First item", new Item[] {new Item(3626, 1)}, new Item(3628, 1)),
        ITEM2("Ares Aura LvL 2", new Item[] {new Item(3622, 1)}, new Item(3090, 1)),
        ;



        ItemData(String name, Item[] required_items, Item new_item) {
            this.name = name;
            this.required_items = required_items;
            this.new_item = new_item;
        }

        private String name;
        private Item[] required_items;
        private Item new_item;
    }


    public static void clearInterface(Player player) {
        int id = 43513;
        for (int i = 0; i < 20; i++) {
            player.getPA().sendItemOnInterface(id, 0, 1);
            id++;
        }
        player.getPA().sendItemOnInterface(43537, 0, 1);
        id = 28000;
        for (int i = 0; i < 85; i++) {
            player.getPA().sendString(id, "");
            id++;
        }
        id = 28000;
        for (int i = 0; i < ItemData.values().length; i++) {
            player.getPA().sendString(id, ItemData.values()[i].name);
            id++;
        }
        player.getPA().sendInterface(43500);
    }

    public static void sendItems(Player player, int id) {
        int itemId = id - 28000;
        int interfaceId = 43513;
        for (int i = 0; i < ItemData.values()[itemId].required_items.length; i++) {
            player.getPA().sendItemOnInterface(interfaceId, ItemData.values()[itemId].required_items[i].getId(), ItemData.values()[itemId].required_items[i].getAmount());
        }
        player.getPA().sendItemOnInterface(43537, ItemData.values()[itemId].new_item.getId(), ItemData.values()[itemId].new_item.getAmount());
    }

    public static void upgrade(Player player, int upgradeChoice) {
        int id = upgradeChoice - 28000;
        int new_item_id = ItemData.values()[id].new_item.getId();
        for (int i = 0; i < ItemData.values()[id].required_items.length; i++) {
            if(!player.getInventory().contains(ItemData.values()[id].required_items[i].getId())) {
                player.getPA().sendMessage("You are missing " +ItemData.values()[id].required_items[i].getAmount() + "x " + ItemDefinition.forId(ItemData.values()[id].required_items[i].getId()).getName());
                return;
            }
            player.getInventory().delete(ItemData.values()[id].required_items[i].getId(), ItemData.values()[id].required_items[i].getAmount());
        }
        player.getInventory().add(new Item(new_item_id, ItemData.values()[id].new_item.getAmount()));
        player.getPA().sendMessage("You successfully merge the items to receive " + ItemDefinition.forId(new_item_id).getName() + ".");
    }
}
