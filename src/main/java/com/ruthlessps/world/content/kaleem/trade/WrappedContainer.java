package com.ruthlessps.world.content.kaleem.trade;

import com.ruthlessps.model.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WrappedContainer extends ItemContainer {

    public WrappedContainer(List<Item> items) {
        super(28);
        items.forEach(this::add);
    }

    @Override
    protected void refresh() {

    }

    public WrappedContainer copy() {
        List<Item> copiedItems = Arrays.stream(items).filter(Objects::nonNull).map(Item::copy).collect(Collectors.toList());
        WrappedContainer container = new WrappedContainer(copiedItems);
        return container;
    }
}
