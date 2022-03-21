/*package mysql.impl;

import com.rspserver.motivote.MotivoteHandler;
import com.rspserver.motivote.Vote;
import com.ruthlessps.GameSettings;
import com.ruthlessps.model.Item;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

public class VoteHandler extends MotivoteHandler<Vote> {

	@Override
	public void onCompletion(Vote arg0) {

		Player player = World.getPlayerByName(arg0.username());

		if (player != null) {

			player.sendMessage("Thank you for voting and supporting our server!");
			player.getInventory().add(new Item(
					GameSettings.VOTE_REWARD_IDS[Misc.exclusiveRandom(GameSettings.VOTE_REWARD_IDS.length)], 1));

			arg0.complete();

		}

	}

}
*/