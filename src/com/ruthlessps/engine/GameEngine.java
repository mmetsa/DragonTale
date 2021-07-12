package com.ruthlessps.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.clan.ClanChatManager;

/**
 * 
 * @author lare96
 * @author Gabriel Hannason
 */
public final class GameEngine implements Runnable {

	private enum EngineState {
		PACKET_PROCESSING, GAME_PROCESSING;
	}

	private static final int PROCESS_GAME_TICK = 2;

	/** STATIC **/

	public static ScheduledExecutorService createLogicService() {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
		executor.setRejectedExecutionHandler(new CallerRunsPolicy());
		executor.setThreadFactory(new ThreadFactoryBuilder().setNameFormat("LogicServiceThread").build());
		executor.setKeepAliveTime(45, TimeUnit.SECONDS);
		executor.allowCoreThreadTimeOut(true);
		return Executors.unconfigurableScheduledExecutorService(executor);
	}

	private final ScheduledExecutorService logicService = GameEngine.createLogicService();

	private EngineState engineState = EngineState.PACKET_PROCESSING;

	private int engineTick = 0;

	private EngineState next() {
		if (engineTick == PROCESS_GAME_TICK) {
			engineTick = 0;
			return EngineState.GAME_PROCESSING;
		}
		engineTick++;
		return EngineState.PACKET_PROCESSING;
	}

	@Override
	public void run() {
		try {
			switch (engineState) {
			case PACKET_PROCESSING:
				World.getPlayers().forEach($it -> $it.getSession().handlePrioritizedMessageQueue());
				break;
			case GAME_PROCESSING:
				TaskManager.sequence();
				World.sequence();
				break;
			}
			engineState = next();
		} catch (Throwable e) {
			e.printStackTrace();
			World.savePlayers();
			ClanChatManager.save();
		}
	}

	public void submit(Runnable t) {
		try {
			logicService.execute(t);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}