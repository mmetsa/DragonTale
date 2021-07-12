package com.ruthlessps.net.packet.impl;

import com.ruthlessps.engine.task.impl.WalkToTask;
import com.ruthlessps.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruthlessps.model.Locations;
import com.ruthlessps.model.Locations.Location;
import com.ruthlessps.net.packet.Packet;
import com.ruthlessps.net.packet.PacketListener;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.gambling.GamblingManager.GambleStage;
import com.ruthlessps.world.content.kaleem.trade.TradeManager;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * This packet listener is called when a player accepts a trade offer, whether
 * it's from the chat box or through the trading player menu.
 * 
 * @author relex lawl
 */

public class TradeInvitationPacketListener implements PacketListener {

	public static final int TRADE_OPCODE = 39;

	public static final int CHATBOX_TRADE_OPCODE = 139;

	@Override
	public void handleMessage(Player player, Packet packet) {
		if (player.getConstitution() <= 0)
			return;
		if (player.isTeleporting())
			return;
		player.getSkillManager().stopSkilling();
		if (player.getLocation() == Location.NOMAD) {
			player.getPacketSender().sendMessage("You cannot trade other players here.");
			return;
		}
		int index = packet.getOpcode() == TRADE_OPCODE ? (packet.readShort() & 0xFF) : packet.readLEShort();
		if (index < 0 || index > World.getPlayers().capacity())
			return;
		Player target = World.getPlayers().get(index);
		if(target.getGambling().getStage() != GambleStage.OFFLINE)
			return;
		if (target == null || !Locations.goodDistance(player.getPosition(), target.getPosition(), 13))
			return;
		player.setWalkToTask(
				new WalkToTask(player, target.getPosition(), target.getSize(), new FinalizedMovementTask() {
					@Override
					public void execute() {
						if (target.getIndex() != player.getIndex())
							//TradeManager.SINGLETON.request(player, target);
							player.getTrading().requestTrade(target);
					}
				}));
	}
}
