package com.ruthlessps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ruthlessps.drops.NPCDrops;
import com.ruthlessps.engine.GameEngine;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.ServerTimeUpdateTask;
import com.ruthlessps.model.container.impl.Shop.ShopManager;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.model.definitions.NpcDefinition;
import com.ruthlessps.model.definitions.WeaponInterfaces;
//import com.ruthlessps.net.FloodProtection;
import com.ruthlessps.net.PipelineFactory;
import com.ruthlessps.net.packet.interaction.PacketInteractionManager;
import com.ruthlessps.net.security.ConnectionHandler;
import com.ruthlessps.world.World;
import com.ruthlessps.world.clip.region.RegionClipping;
import com.ruthlessps.world.content.Censor;
import com.ruthlessps.world.content.CustomObjects;
import com.ruthlessps.world.content.Deals;
import com.ruthlessps.world.content.DoubleDropWell;
import com.ruthlessps.world.content.HourlyNpc;
import com.ruthlessps.world.content.Lottery;
import com.ruthlessps.world.content.PointsWell;
import com.ruthlessps.world.content.Scoreboards;
import com.ruthlessps.world.content.WellOfGoodwill;
import com.ruthlessps.world.content.WildyTorva;
import com.ruthlessps.world.content.clan.ClanChatManager;
import com.ruthlessps.world.content.combat.effect.CombatPoisonEffect.CombatPoisonData;
import com.ruthlessps.world.content.combat.strategy.CombatStrategies;
import com.ruthlessps.world.content.dialogue.DialogueManager;
import com.ruthlessps.world.content.pos.PlayerOwnedShopManager;
import com.ruthlessps.world.entity.impl.npc.NPC;

import mysql.MySQLController;

/**
 * Credit: lare96, 
 */
public final class GameLoader {

	private final ExecutorService serviceLoader = Executors
			.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("GameLoadingThread").build());
	private final ScheduledExecutorService executor = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder().setNameFormat("GameThread").build());
	private final GameEngine engine;
	private final int port;

	protected GameLoader(int port) {
		this.port = port;
		this.engine = new GameEngine();

	}

	private void executeServiceLoad() {
		/*Motivote<Vote> VOTE = new Motivote<Vote>(new VoteHandler(), "http://xxx.com/vote/", "088ddd3c");
		VOTE.start();*/
		serviceLoader.execute(() -> PlayerOwnedShopManager.loadShops());
		serviceLoader.execute(() -> ItemDefinition.init());
		serviceLoader.execute(() -> NPCDrops.init());
		serviceLoader.execute(() -> ConnectionHandler.init());
		serviceLoader.execute(() -> RegionClipping.init());
		serviceLoader.execute(() -> CustomObjects.init());
		serviceLoader.execute(() -> Lottery.init());
		serviceLoader.execute(() -> Scoreboards.init());
		serviceLoader.execute(() -> WellOfGoodwill.init());
		serviceLoader.execute(() -> DoubleDropWell.init());
		serviceLoader.execute(() -> PointsWell.init());
		serviceLoader.execute(() -> Deals.init());
		serviceLoader.execute(() -> ClanChatManager.init());
		serviceLoader.execute(() -> CombatPoisonData.init());
		serviceLoader.execute(() -> CombatStrategies.init());
		serviceLoader.execute(() -> NpcDefinition.parseNpcs().load());
		serviceLoader.execute(() -> WeaponInterfaces.parseInterfaces().load());
		serviceLoader.execute(() -> ShopManager.parseShops().load());
		serviceLoader.execute(() -> DialogueManager.parseDialogues().load());
		serviceLoader.execute(() -> NPC.init());
		serviceLoader.execute(() -> PacketInteractionManager.init());
		serviceLoader.execute(() -> Censor.init());
		serviceLoader.execute(() -> MySQLController.init());
		System.out.println("Success! Connected to: " + GameSettings.DBHost);
		//	serviceLoader.execute(() -> FloodProtection.init());
		serviceLoader.execute(() -> WildyTorva.initialize());
		serviceLoader.execute(() -> HourlyNpc.init());
		Deals.loadDeals();
	}

	public void finish() throws IOException, InterruptedException {
		if (!serviceLoader.awaitTermination(15, TimeUnit.MINUTES))
			throw new IllegalStateException("The background service load took too long!");
		ExecutorService networkExecutor = Executors.newCachedThreadPool();
		ServerBootstrap serverBootstrap = new ServerBootstrap(
				new NioServerSocketChannelFactory(networkExecutor, networkExecutor));
		serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		serverBootstrap.bind(new InetSocketAddress(port));
		executor.scheduleAtFixedRate(engine, 0, GameSettings.ENGINE_PROCESSING_CYCLE_RATE, TimeUnit.MILLISECONDS);
		TaskManager.submit(new ServerTimeUpdateTask());
	}

	public GameEngine getEngine() {
		return engine;
	}

	public void init() {
		Preconditions.checkState(!serviceLoader.isShutdown(), "The bootstrap has been bound already!");
		executeServiceLoad();
		serviceLoader.shutdown();
	}
}