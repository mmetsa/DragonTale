package com.ruthlessps.world.content.kaleem.trade;

import com.ruthlessps.model.Item;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ItemContainer implements Containable{

  protected final Item[] items;

  private final List<Consumer<Item>> onAdd = new ArrayList<>();
  private final List<Consumer<Item>> onRemove = new ArrayList<>();

  public ItemContainer(int length) {
    this.items = new Item[length];
  }

  public final void onAdd(Consumer<Item> action) {
    onAdd.add(action);
  }

  public final void onAdd(Runnable action) {
    onAdd.add((item) -> action.run());
  }

  public final void onTransaction(Runnable action) {
    onAdd(action);
    onRemove(action);
  }

  public final void onRemove(Runnable action) {
    onRemove.add((item) -> action.run());
  }

  public final void onRemove(Consumer<Item> action) {
    onRemove.add(action);
  }

  public boolean isAddable(Item item) {
    return true;
  }

  public boolean add(Item item) {
    if (item.getAmount() <= 0) {
      return false;
    }

    if (!isStackable(item) && item.getAmount() > 1) {
      return IntStream.range(0, item.getAmount()).mapToObj(i -> new Item(item.getId(), 1)).allMatch(this::add);
    }
    if (!isAddable(item)) {
      return false;
    }

    OptionalInt containedSlot = getSlot(item.getId(), 1);
    if (containedSlot.isPresent() && isStackable(item)) {
      int slot = containedSlot.getAsInt();
      Item contained = items[slot];

      long newAmount = contained.getAmount() + item.getAmount();

      if (newAmount > Integer.MAX_VALUE) {
        return false;
      }

      Item newItem = contained.copy().setAmount((int) newAmount);
      items[slot] = newItem;
      fireAdd(newItem);
      return true;
    }

    OptionalInt freeSlot = getFreeSlot();
    if (freeSlot.isPresent()) {
      int slot = freeSlot.getAsInt();
      items[slot] = item;
      fireAdd(item);
      return true;
    }
    return false;
  }

  private void fireAdd(Item newItem) {
    onAdd.forEach(consumer -> consumer.accept(newItem));
    refresh();
  }

  private void fireRemove(Item newItem) {
    onRemove.forEach(consumer -> consumer.accept(newItem));
    refresh();
  }

  public boolean remove(Item item) {

    if (!isStackable(item) && item.getAmount() > 1) {
      return IntStream.range(0, item.getAmount()).mapToObj(i -> new Item(item.getId(), 1)).allMatch(this::remove);
    }
    OptionalInt containedSlot = getSlot(item);

    if (containedSlot.isPresent()) {
      int slot = containedSlot.getAsInt();

      if (isStackable(item)) {
        Item contained = items[slot];

        int newAmount = contained.getAmount() - item.getAmount();
        if (newAmount <= 0) {
          clearSlot(slot);
          fireRemove(null);
          return true;
        }

        Item newItem = item.copy().setAmount(newAmount);
        items[slot] = newItem;
        fireRemove(newItem);
      } else {
        items[slot] = null;
        fireRemove(null);
      }
      return true;
    }
    return false;
  }

  public void clearSlot(int index) {
    items[index] = null;
    refresh();
  }

  public void forEach(Consumer<Item> action) {
    Arrays.stream(items).filter(Objects::nonNull).forEach(action::accept);
  }

  public void clear() {
    IntStream.range(0, capacity()).forEach(this::clearSlot);
    fireRemove(null);
  }

  public List<Item> copyItems() {
    return Arrays.stream(items).filter(Objects::nonNull).filter(item -> item.getAmount() > 0).collect(Collectors.toList());
  }

  protected abstract void refresh();

  public boolean exists(int slot) {
    return items[slot] != null;
  }

  public boolean isFree(int slot) {
    return !exists(slot);
  }

  public int getAmount(int itemId) {
    return Arrays.stream(items).filter(Objects::nonNull).filter(item -> item.getId() == itemId).mapToInt(Item::getAmount).sum();
  }

  public Optional<Item> lookup(int slot) {
    return Optional.ofNullable(items[slot]);
  }

  public OptionalInt getFreeSlot() {
    return IntStream.range(0, getLength()).filter(this::isFree).findFirst();
  }

  public OptionalInt getSlot(Item item) {
    return getSlot(item.getId(), item.getAmount());
  }

  public OptionalInt getSlot(int itemId, int amount) {
    for (int i = 0; i < getLength(); i++) {
      Item item = items[i];
      if (item == null) {
        continue;
      }
      if (item.getId() == itemId && amount <= item.getAmount()) {
        return OptionalInt.of(i);
      }
    }
    return OptionalInt.empty();
  }

  public int getLength() {
    return items.length;
  }

  public int getTakenSlots() {
    return IntStream.range(0, getLength()).filter(this::exists).sum();
  }

  public int getFreeSlots() {
    return getLength() - getTakenSlots();
  }

  private boolean isStackable(Item item) {
    boolean stackable = item.getDefinition().isStackable();
    return stackable; //TODO
  }

  @Override
  public int getCapacity() {
    return getLength();
  }

  @Override
  public Item[] getItems() {
    return items;
  }
}