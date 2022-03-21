package com.ruthlessps.world.content.kaleem.trade;

import com.ruthlessps.model.GameMode;
import com.ruthlessps.world.entity.impl.player.PsuedoPlayer;

import java.util.*;

/**
 * Managers global trades
 *
 * @author Kaleem
 */
public final class TradeManager {

    public static final TradeManager SINGLETON = new TradeManager();

    private final Map<Integer, Integer> requests = new HashMap<>();

    private final Set<TradeSession> sessionSet = new HashSet<>();

    public void request(PsuedoPlayer player, PsuedoPlayer other) {
        getSession(player).ifPresent(session -> session.end(player));
        if(!(player.getGameMode() == GameMode.IRONMAN)) {
	        requests.put(player.getIndex(), other.getIndex());
	
	        if (requests.getOrDefault(other.getIndex(), -1) == player.getIndex()) {
	            requests.remove(other.getIndex());
	            requests.remove(player.getIndex());
	            create(other, player);
	            return;
	        }
	        	player.sendMessage("Sending trade request...");
	        	other.sendMessage(player.getUsername() + ":tradereq:");
        } else {
        	player.sendMessage("You're an ironman, Ironmen can't trade you silly!");
        }
    }

    public Optional<TradeSession> create(PsuedoPlayer requester, PsuedoPlayer requested) {
        if (contains(requester) || contains(requested)) {
            return Optional.empty();
        }
        TradeSession session = new TradeSession(requester, requested);

        sessionSet.add(session);
        session.init();

        return Optional.of(session);
    }

    protected void remove(TradeSession session) {
        sessionSet.remove(session);
    }

    public Optional<TradeSession> getSession(PsuedoPlayer player) {
        return sessionSet.stream().filter(session -> session.contains(player)).findAny();
    }

    public boolean contains(PsuedoPlayer player) {
        return getSession(player).isPresent();
    }

    public boolean hasRequested(PsuedoPlayer player, PsuedoPlayer other) {
        return requests.getOrDefault(player.getIndex(), -1) == other.getIndex();
    }
}