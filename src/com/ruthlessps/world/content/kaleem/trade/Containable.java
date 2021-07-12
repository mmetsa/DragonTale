package com.ruthlessps.world.content.kaleem.trade;

import com.ruthlessps.model.Item;

public interface Containable {
    public int getCapacity();
    public Item[] getItems();

    default int capacity() {
        return getCapacity();
    }
}
