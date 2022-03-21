package com.ruthlessps.world.content.kaleem.trade;


import com.ruthlessps.model.Item;
import com.ruthlessps.model.container.ItemContainer;
import com.ruthlessps.model.container.impl.Inventory;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.entity.impl.player.PsuedoPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents a singular player entry within a {@link TradeSession}
 *
 * @author Kaleem
 */
public final class TradeEntry {

    protected static TradeEntry create(PsuedoPlayer player) {
        return new TradeEntry(player);
    }

    private final PsuedoPlayer player;

    private final TradeContainer container;

    private TradeSession session;
    private TradeEntry other;

    private boolean accepted;

    private TradeEntry(PsuedoPlayer player) {
        this.player = player;
        this.container = new TradeContainer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeEntry that = (TradeEntry) o;

        return player.equals(that.player);
    }

    @Override
    public String toString() {
        return "TradeEntry{" +
                "player=" + player +
                ", accepted=" + accepted +
                '}';
    }

    @Override
    public int hashCode() {
        return player.hashCode();
    }

    public PsuedoPlayer getPlayer() {
        return player;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public boolean tryContainerClick(int interfaceId, int slot, int itemId, int number) {
        if (interfaceId == 3322) {
            ItemContainer inventory = player.getInventory();
            Item containedSlot = inventory.get(slot);
            if (containedSlot.getId() != itemId) {
                return false;
            }
            if (containedSlot.getAmount() == 0) {
                return false;
            }
            if(!containedSlot.tradeable()) {
            	player.sendMessage("You can not trade this item because it's untradeable!");
            	return false;
            }
            if (containedSlot.getAmount() < number) {
                number = containedSlot.getAmount();
            }
            containedSlot.decrementAmountBy(number);
            container.add(new Item(itemId, number));
            inventory.refreshItems();
            container.refresh();
            return true;
        }

        if (interfaceId == 3415) { //Removing items

            ItemContainer inventory = player.getInventory();
            int containedAmount = container.getAmount(itemId);
            if (containedAmount == 0) {
                return true;
            }
            System.out.println("containedAmount:" + containedAmount + ", number:" + number);
            if (number == -69 || containedAmount < number) {
                number = containedAmount;
            }
            container.remove(new Item(itemId, number));
            inventory.add(itemId, number);
            inventory.refreshItems();
            container.refresh();
            return true;
        }

        return false;
    }

    public boolean tryClick(int id) {
        if (id == -32301 || id == -31304) { //Accept
            setAccepted(true);
            return true;
        }

        if (id == -32300 || id == -31303) { //Decline
            session.end(player);
            return true;
        }

        if (id == -32304) { //Withdraw from inventory
            WrappedContainer inventory = new WrappedContainer(player.getInventory().getValidItems()).copy();
            inventory.forEach(container::add);
            inventory.forEach(player.getInventory()::delete);
            container.refresh();
            return true;
        }

        if (id == -32303) { //Deposit trade to inventory
            container.forEach(player.getInventory()::add);
            container.clear();
            return true;
        }
        player.sendMessage("trying to click " + id);
        return false;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;

        PsuedoPlayer otherPlayer = session.getOther(this).getPlayer();

        TradeState state = session.getState();

        if (accepted) {

            if (state == TradeState.FIRST_SCREEN) {
                otherPlayer.sendStringOnInterface("Other player has\\naccepted.", 33240);
                player.sendStringOnInterface("Waiting for other\\nplayer...", 33240);
            } else {
                otherPlayer.sendStringOnInterface("Other player has accepted.", 34236);
                player.sendStringOnInterface("Waiting for other player...", 34236);
            }
        }

        if (session.bothAccepted()) {
            if (state == TradeState.FIRST_SCREEN) {
                session.setState(TradeState.SECOND_SCREEN);
            } else {
                session.end(true);
                //TODO: Transaction
                //TODO: End trade
            }
        }

    }

    protected boolean hasEnoughSpace(boolean message) {
        List<Item> newItems = session.getNewInventory(this);
        if (newItems.size() > 28) {
            if (message) {
                player.sendMessage("You do not have enough inventory slots for this trade to take place.");
                other.getPlayer().sendMessage("Other user does not have enough inventory slots for the trade.");
            }
            return false;
        }
        return true;
    }

    protected String getTradeString() {
        List<Item> items = getContainer().copyItems();
        if (items.isEmpty()) {
            return "Absolutely nothing!";
        }
        return items.stream().map(this::format).collect(Collectors.joining("\\n"));
    }

    private String format(Item item) {

        String formattedAmount = Misc.format(item.getAmount());
        int amount = item.getAmount();

        if (amount >= 1000 && amount < 1000000) {
            formattedAmount = "@cya@" + (amount / 1000) + "K @whi@(" + formattedAmount + ")";
        } else if (amount >= 1000000) {
            formattedAmount = "@gre@" + (amount / 1000000) + " M @whi@(" + formattedAmount + ")";
        }

        String itemName = item.getDefinition().getName().replaceAll("_", " ");
        if (item.getDefinition().isStackable()) {
            return itemName + " x " + formattedAmount;
        } else {
            return itemName;
        }
        /*
        if (Count == 0) {
            SendTrade = item.getDefinition().getName().replaceAll("_", " ");
        } else
            SendTrade = SendTrade + "\\n" + item.getDefinition().getName().replaceAll("_", " ");
        if (item.getDefinition().isStackable())
            SendTrade = SendTrade + " x " + SendAmount;*/
    }

    public TradeSession getSession() {
        return session;
    }

    protected void setSession(TradeSession session) {
        if (this.session != null) {
            System.err.println("Warning. Session being overriden [" + this.session + ", " + session + "]");
        }
        this.session = session;
        this.other = session.getOther(this);
    }

    public TradeContainer getContainer() {
        return container;
    }
}