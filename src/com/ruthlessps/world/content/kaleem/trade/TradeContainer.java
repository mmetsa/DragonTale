package com.ruthlessps.world.content.kaleem.trade;


import com.ruthlessps.model.Item;
import com.ruthlessps.world.entity.impl.player.Player;
import com.ruthlessps.world.entity.impl.player.PsuedoPlayer;

public class TradeContainer extends ItemContainer {

    private final TradeEntry entry;

    public TradeContainer(TradeEntry entry) {
        super(28);
        this.entry = entry;
    }

    @Override
    public boolean isAddable(Item item) {
        return super.isAddable(item);
    }

    @Override
    protected void refresh() {
        entry.getSession().forEach(entry -> {

            TradeEntry otherEntry = entry.getSession().getOther(entry);

            PsuedoPlayer psuedoPlayer = entry.getPlayer();
            Player castedPlayer =(Player) psuedoPlayer;

            castedPlayer.getPacketSender().sendItemContainer(otherEntry.getContainer(), 3416);
            castedPlayer.getPacketSender().sendItemContainer(entry.getContainer(), 3415);

            castedPlayer.getPacketSender().sendItemContainer(castedPlayer.getInventory(), 3322);
        });

    }


}