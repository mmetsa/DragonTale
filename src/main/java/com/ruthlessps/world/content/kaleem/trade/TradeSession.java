package com.ruthlessps.world.content.kaleem.trade;


import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PsuedoPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a session between two players
 *
 * @author Kaleem
 */
public class TradeSession {

    private final TradeEntry requester;
    private final TradeEntry requested;

    private TradeState state = TradeState.FIRST_SCREEN;

    public TradeSession(PsuedoPlayer requester, PsuedoPlayer requested) {
        this.requester = TradeEntry.create(requester);
        this.requested = TradeEntry.create(requested);
        forEach(entry -> entry.setSession(this));
    }

    protected void init() {
        setState(TradeState.FIRST_SCREEN);
        forEach(this::init);
    }

    private void init(TradeEntry entry) {
        TradeContainer container = entry.getContainer();
        PsuedoPlayer player = entry.getPlayer();

        container.onTransaction(this::declineBoth); //Accept is turned off

        if (player.getUsername().equalsIgnoreCase("kaleem")) {
            container.add(new Item(10, 1));
        }

        refresh(entry);
        container.onTransaction(() -> {
            refresh(entry);
            refresh(getOther(entry));
        });

        sendInitialStrings(entry);
        container.refresh();
    }

    private void refresh(TradeEntry entry) {
        TradeContainer container = entry.getContainer();
        PsuedoPlayer player = entry.getPlayer();
        TradeEntry otherEntry = getOther(entry);
        PsuedoPlayer other = otherEntry.getPlayer();


        player.getInventory().refreshItems();

        Player castedPlayer =(Player) player;


        castedPlayer.getPacketSender().sendItemContainer(otherEntry.getContainer(), 3416);
        castedPlayer.getPacketSender().sendItemContainer(container, 3415);

        player.sendStringOnInterface(3431, "");


        int freeSlots = player.getInventory().getFreeSlots();

        other.sendStringOnInterface(player.getUsername() + "\\nhas @red@"
                + freeSlots + " free\\ninventory slots.", 33239);
        other.sendStringOnInterface(34236, "");

        sendInitialStrings(entry);
    }

    private void sendInitialStrings(TradeEntry entry) {
        PsuedoPlayer player = entry.getPlayer();
        PsuedoPlayer other = getOther(entry).getPlayer();

        other.sendStringOnInterface(3451, player.getUsername());
        other.sendStringOnInterface(3417, "Trading with: " + player.getUsername());

        player.sendStringOnInterface(3451, other.getUsername());
        player.sendStringOnInterface(3417, "Trading with: " + other.getUsername());
        player.sendStringOnInterface(3431, "");
        player.sendStringOnInterface(33240, "");
        player.sendStringOnInterface(34236, "There is NO WAY to reverse a trade if you change your mind");
        player.sendStringOnInterface(33238, other.getUsername());
        player.sendInterfaceSet(33230, 3321);


       // player.sendItemContainer(Arrays.asList(player.getInventory().getCopiedItems()), 3322);
    }



    private void declineBoth() {
        forEach(entry -> entry.setAccepted(false));
    }


    protected void forEach(Consumer<TradeEntry> action) {
        getEach().forEach(action::accept);
    }

    protected void forEachPlayer(Consumer<PsuedoPlayer> action) {
        getEach().stream().map(TradeEntry::getPlayer).forEach(action::accept);
    }

    protected List<TradeEntry> getEach() {
        return Stream.of(requester, requested).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TradeSession session = (TradeSession) o;

        if (!requester.equals(session.requester)) return false;
        return requested.equals(session.requested);
    }

    @Override
    public int hashCode() {
        int result = requester.hashCode();
        result = 31 * result + requested.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TradeSession{" +
                "requester=" + requester +
                ", requested=" + requested +
                ", state=" + state +
                '}';
    }

    public boolean contains(PsuedoPlayer player) {
        return getEach().stream().map(TradeEntry::getPlayer).anyMatch(contained -> contained == player);
    }

    public boolean bothAccepted() {
        return getEach().stream().allMatch(TradeEntry::isAccepted);
    }


    public void end(PsuedoPlayer ender) {
        TradeEntry entry = get(ender);
        TradeEntry other = getOther(entry);

        ender.sendMessage("Trade declined.");
        other.getPlayer().sendMessage("Other played declined trade.");

        end(false);
    }

    protected void end(boolean transact) {
        TradeManager.SINGLETON.remove(this);

        forEachPlayer(PsuedoPlayer::closeInterface);

        if (transact) {
            transact();
        } else {
            returnItems();
        }
    }

    private void returnItems() {
        forEach(entry -> {
            PsuedoPlayer player = entry.getPlayer();
            entry.getContainer().forEach(player.getInventory()::add);
        });
    }


    private void transact() {

        if (getEach().stream().allMatch(entry -> entry.hasEnoughSpace(true))) {
            forEach(entry -> {
                Player player = (Player) entry.getPlayer();
                player.getPacketSender().sendMessage("The Trade was cancelled due to the lack of inventory space!");
            });
            returnItems();
            return;
        } //Enough space check

        forEach(entry -> {
            List<Item> newInventory = getNewInventory(entry);
            entry.getPlayer().getInventory().clear();
            newInventory.forEach(item -> {
                //entry.getPlayer().sendMessage("Adding " + item.getAmount() + "x " + item.getDefinition().getName());
                entry.getPlayer().getInventory().add(item);
            });
            entry.getPlayer().getInventory().refreshItems();
        });
        //TODO: Transfer items
        forEachPlayer(player -> player.sendMessage("Trade accepted."));
    }

    protected List<Item> getNewInventory(TradeEntry entry) {
        WrappedContainer inventory = new WrappedContainer(entry.getPlayer().getInventory().getValidItems()).copy();

       // entry.getContainer().forEach(inventory::remove);
        getOther(entry).getContainer().forEach(inventory::add);

        return inventory.copyItems();
    }

    public void setState(TradeState state) {
        this.state = state;
        forEach(entry -> entry.setAccepted(false));

        if (state == TradeState.SECOND_SCREEN) {
            forEach(this::secondScreenInit);
        }
    }

    private void secondScreenInit(TradeEntry entry) {
        TradeEntry otherEntry = getOther(entry);
        PsuedoPlayer otherPlayer = otherEntry.getPlayer();
        PsuedoPlayer player = entry.getPlayer();

        player.sendStringOnInterface(34238, entry.getTradeString());
        player.sendStringOnInterface(34267, otherEntry.getTradeString());
        player.sendStringOnInterface(34235, otherPlayer.getUsername());
        player.sendInterfaceSet(34230, 3321);

        Player castedPlayer = (Player) player;
        castedPlayer.getPacketSender().sendItemContainer(player.getInventory(), 3322);
    }




    protected TradeEntry getOther(TradeEntry input) {
        if (input == requester) {
            return requested;
        } else if (input == requested) {
            return requester;
        }
        throw new UnsupportedOperationException("Cannot find other for input " + input + " for session " + this);
    }

    public TradeEntry get(PsuedoPlayer input) {
        if (requester.getPlayer() == input) {
            return requester;
        } else if (requested.getPlayer() == input) {
            return requested;
        }
        throw new UnsupportedOperationException("Cannot find other for input " + input + " for session " + this);
    }


    public TradeEntry getRequester() {
        return requester;
    }

    public TradeEntry getRequested() {
        return requested;
    }

    public TradeState getState() {
        return state;
    }


}