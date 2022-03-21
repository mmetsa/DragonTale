package com.ruthlessps.world.entity.impl.player;

import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.container.ItemContainer;

import java.util.List;

public interface PsuedoPlayer {
    public String getUsername();

    public ItemContainer getInventory();

    public void sendStringOnInterface(String string, int child);

    public default void sendStringOnInterface(int child, String string) {
        sendStringOnInterface(string, child);
    }

    public void sendMessage(String message);

    public void sendInterfaceSet(int interfaceId, int sidebarInterfaceId);


    public int getIndex();

    public void closeInterface();

	public GameMode getGameMode();
}
