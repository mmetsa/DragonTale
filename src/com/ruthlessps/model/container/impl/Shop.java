package com.ruthlessps.model.container.impl;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruthlessps.GameSettings;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.engine.task.impl.ShopRestockTask;
import com.ruthlessps.model.GameMode;
import com.ruthlessps.model.Item;
import com.ruthlessps.model.Skill;
import com.ruthlessps.model.container.ItemContainer;
import com.ruthlessps.model.container.StackType;
import com.ruthlessps.model.definitions.ItemDefinition;
import com.ruthlessps.model.input.impl.EnterAmountToBuyFromShop;
import com.ruthlessps.model.input.impl.EnterAmountToSellToShop;
import com.ruthlessps.util.JsonLoader;
import com.ruthlessps.util.Misc;
import com.ruthlessps.world.World;
import com.ruthlessps.world.content.ZoneTasks;
import com.ruthlessps.world.content.minigames.impl.RecipeForDisaster;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Messy but perfect Shop System
 * 
 * @author Gabriel Hannason
 */

public class Shop extends ItemContainer {

	public static class ShopManager {

		private static Map<Integer, Shop> shops = new HashMap<Integer, Shop>();

		public static Object[] getCustomShopData(int shop, int item) {
			if (shop == SKILL_STORE) {
				switch(item) {
				case 11998:
					return new Object[] { 10, "Skill points" };
				}
 			}
			if (shop == AFK_FISHING_STORE) {
				switch(item) {
				case 16142:
					return new Object[] { 10000, "@blu@Blufish" };
				case 16143:
					return new Object[] { 10000, "@blu@Blufish" };
				case 16144:
					return new Object[] { 10000, "@blu@Blufish" };
				case 16145:
					return new Object[] { 10000, "@blu@Blufish" };
				case 16146:
					return new Object[] { 20000, "@blu@Blufish" };
				case 16147:
					return new Object[] { 30000, "@blu@Blufish" };
				case 16148:
					return new Object[] { 20000, "@blu@Blufish" };
				case 16149:
					return new Object[] { 30000, "@blu@Blufish" };
				case 16150:
					return new Object[] { 100000, "@blu@Blufish" };
				}
 			}
			if (shop == AFK_MINING_STORE) {
				switch(item) {
				case 16361:
					return new Object[] { 10000, "@blu@Crystal dust" };
				case 16363:
					return new Object[] { 15000, "@blu@Crystal dust" };
				case 16365:
					return new Object[] { 20000, "@blu@Crystal dust" };
				case 16367:
					return new Object[] { 25000, "@blu@Crystal dust" };
					
				}
 			}
			if (shop == AFK_WOODCUTTING_STORE) {
				switch(item) {
				case 3933:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 3934:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 3935:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 3936:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 3937:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 3938:
					return new Object[] { 20000, "@blu@Dream logs" };
				case 15750:
					return new Object[] { 35000, "@blu@Dream logs" };
				}
 			}
			if (shop == 79) {
				switch(item) {
				case 11998:
					return new Object[] { 10, "Donation Points" };
				}
 			}
			if (shop == VOTING_REWARDS_STORE) {
				switch (item) {
				case 6199:
					return new Object[] { 10, "Voting points" };
				case 15501:
					return new Object[] { 100, "Voting points" };
				case 6930:
					return new Object[] { 250, "Voting points" };
				case 11530:
					return new Object[] { 100, "Voting points" };
				case 11531:
					return new Object[] { 100, "Voting points" };
				case 11532:
					return new Object[] { 100, "Voting points" };
				case 989:
					return new Object[] { 1, "Voting points" };
				case 295:
					return new Object[] { 200, "Voting points" };
				case 421:
					return new Object[] { 500, "Voting points" };
				case 2633:
					return new Object[] { 250, "Voting points" };
				case 11586:
					return new Object[] { 50, "Voting points" };
				case 11587:
					return new Object[] { 50, "Voting points" };
				case 11588:
					return new Object[] { 50, "Voting points" };
				case 11589:
					return new Object[] { 50, "Voting points" };
				case 11590:
					return new Object[] { 50, "Voting points" };
				case 11591:
					return new Object[] { 50, "Voting points" };
				case 20692:
					return new Object[] { 20, "Voting points" };
				case 2175:
				case 1835:
				case 14000:
				case 1834:
				case 1837:
				case 1838:
				case 1839:
				case 14915:
					return new Object[] { 100, "Voting points" };
				case 1009:
					return new Object[] { 1000, "Voting points" };
				case 4703:
					return new Object[] { 100, "Voting points" };
				}
			} else if (shop == SKILLING_SHARD_STORE) {
				switch (item) {
				case 6808:
					return new Object[] { 15000, "Skilling Shards" };
				case 6949:
					return new Object[] { 5000, "Skilling Shards" };
				case 918:
					return new Object[] { 4000, "Skilling Shards" };
				case 919:
					return new Object[] { 4000, "Skilling Shards" };
				case 920:
					return new Object[] { 4000, "Skilling Shards" };
				case 3818:
					return new Object[] { 5000, "Skilling Shards" };
				case 3819:
					return new Object[] { 20000, "Skilling Shards" };
				case 3822:
					return new Object[] { 5000, "Skilling Shards" };
				}
			} else if (shop == ENERGY_FRAGMENT_STORE) {
				switch (item) {
				case 5509:
					return new Object[] { 400, "energy fragments" };
				case 5510:
					return new Object[] { 750, "energy fragments" };
				case 5512:
					return new Object[] { 1100, "energy fragments" };
				}
			} else if (shop == AGILITY_TICKET_STORE) {
				switch (item) {
				case 14936:
				case 14938:
					return new Object[] { 60, "agility tickets" };
				case 10941:
				case 10939:
				case 10940:
				case 10933:
					return new Object[] { 20, "agility tickets" };
				case 13661:
					return new Object[] { 100, "agility tickets" };
				}
			} else if (shop == GRAVEYARD_STORE) {
				switch (item) {
				case 18337:
					return new Object[] { 350, "zombie fragments" };
				case 7592:
				case 7593:
				case 7594:
				case 7595:
				case 7596:
					return new Object[] { 25, "zombie fragments" };
				case 3092:
					return new Object[] { 1500, "zombie fragments" };
				case 3279:
					return new Object[] { 5000, "zombie fragments" };
				case 3081:
					return new Object[] { 10000, "zombie fragments" };
				case 3277:
					return new Object[] { 12500, "zombie fragments" };
				case 3281:
					return new Object[] { 750, "zombie fragments" };
				case 3282:
					return new Object[] { 500, "zombie fragments" };
				case 3283:
					return new Object[] { 300, "zombie fragments" };

				case 601:
					return new Object[] { 22000, "zombie fragments" }; // thunder gun
				case 423:
					return new Object[] { 28000, "zombie fragments" };

				}
			} else if (shop == RUTHLESS_POINT_STORE) {
				switch (item) {
				case 15501:
					return new Object[] { 50000, GameSettings.SERVER_NAME + " points"};
				case 19824:
					return new Object[] { 700, GameSettings.SERVER_NAME + " points"};
				case 17897:
					return new Object[] { 900, GameSettings.SERVER_NAME + " points"};
				case 3271:
					return new Object[] { 3000, GameSettings.SERVER_NAME + " points"};
				case 3272:
					return new Object[] { 3000, GameSettings.SERVER_NAME + " points"};
				case 3273:
					return new Object[] { 3000, GameSettings.SERVER_NAME + " points"};
				case 3274:
					return new Object[] { 2000, GameSettings.SERVER_NAME + " points"};
				case 3275:
					return new Object[] { 2000, GameSettings.SERVER_NAME + " points"};
				case 3276:
					return new Object[] { 6000, GameSettings.SERVER_NAME + " points"};
				case 3647:
					return new Object[] { 4000, GameSettings.SERVER_NAME + " points" };
				case 3648:
					return new Object[] { 4000, GameSettings.SERVER_NAME + " points" };
				case 3649:
					return new Object[] { 4000, GameSettings.SERVER_NAME + " points" };
				case 3650:
					return new Object[] { 2250, GameSettings.SERVER_NAME + " points" };
				case 3651:
					return new Object[] { 2250, GameSettings.SERVER_NAME + " points" };
				case 3652:
					return new Object[] { 5000, GameSettings.SERVER_NAME + " points" };
				case 3653:
					return new Object[] { 7500, GameSettings.SERVER_NAME + " points" };
				case 895:
					return new Object[] { 10000, GameSettings.SERVER_NAME + " points" };
				case 82:
					return new Object[] { 12000, GameSettings.SERVER_NAME + " points" };
				case 894:
					return new Object[] { 14000, GameSettings.SERVER_NAME + " points" };
				case 3083:
					return new Object[] { 1500, GameSettings.SERVER_NAME + " points" };
				case 3287:
					return new Object[] { 25, GameSettings.SERVER_NAME + " points" };
				case 3288:
					return new Object[] { 1200, GameSettings.SERVER_NAME + " points" };
				case 3291:
					return new Object[] { 1500, GameSettings.SERVER_NAME + " points" };
				case 3284:
					return new Object[] { 1750, GameSettings.SERVER_NAME + " points" };
				case 3242:
					return new Object[] { 800, GameSettings.SERVER_NAME + " points" };
				case 3293:
					return new Object[] { 550, GameSettings.SERVER_NAME + " points" };
				case 3292:
					return new Object[] { 500, GameSettings.SERVER_NAME + " points" };
				case 3285:
					return new Object[] { 30000, GameSettings.SERVER_NAME + " points" };
				case 3286:
					return new Object[] { 450, GameSettings.SERVER_NAME + " points" };
				case 3640:
					return new Object[] { 10000, GameSettings.SERVER_NAME + " points" };
				case 3637:
					return new Object[] { 10000, GameSettings.SERVER_NAME + " points" };
				case 3638:
					return new Object[] { 10000, GameSettings.SERVER_NAME + " points" };
				case 3639:
					return new Object[] { 10000, GameSettings.SERVER_NAME + " points" };
				case 3290:
					return new Object[] { 400, GameSettings.SERVER_NAME + " points" };
				case 3080:
					return new Object[] { 8000, GameSettings.SERVER_NAME + " points" };
				case 8677:
					return new Object[] { 1350, GameSettings.SERVER_NAME + " points" };
				case 8675:
					return new Object[] { 3250, GameSettings.SERVER_NAME + " points" };
				case 3298:
					return new Object[] { 1850, GameSettings.SERVER_NAME + " points" };
				case 667:
					return new Object[] { 200, GameSettings.SERVER_NAME + " points" };
				case 989:
					return new Object[] { 100, GameSettings.SERVER_NAME + " points" };
				case 13664:
					return new Object[] { 250, GameSettings.SERVER_NAME + " points" };
				case 6199:
					return new Object[] { 1500, GameSettings.SERVER_NAME + " points" };
				case 18355:
					return new Object[] { 5000, GameSettings.SERVER_NAME + " points" };
				case 2437:
					return new Object[] { 100, GameSettings.SERVER_NAME + " points" };
				case 2441:
					return new Object[] { 100, GameSettings.SERVER_NAME + " points" };
				case 2443:
					return new Object[] { 100, GameSettings.SERVER_NAME + " points" };
				case 2445:
					return new Object[] { 150, GameSettings.SERVER_NAME + " points" };
				case 2435:
					return new Object[] { 200, GameSettings.SERVER_NAME + " points" };
				case 3025:
					return new Object[] { 200, GameSettings.SERVER_NAME + " points" };
				case 15332:
					return new Object[] { 1000, GameSettings.SERVER_NAME + " points" };
				}
			} else if (shop == TOKKUL_EXCHANGE_STORE) {
				switch (item) {
				case 11978:
					return new Object[] { 300000, "tokkul" };
				case 438:
				case 436:
					return new Object[] { 10, "tokkul" };
				case 440:
					return new Object[] { 25, "tokkul" };
				case 453:
					return new Object[] { 30, "tokkul" };
				case 442:
					return new Object[] { 30, "tokkul" };
				case 444:
					return new Object[] { 40, "tokkul" };
				case 447:
					return new Object[] { 70, "tokkul" };
				case 449:
					return new Object[] { 120, "tokkul" };
				case 451:
					return new Object[] { 250, "tokkul" };
				case 1623:
					return new Object[] { 20, "tokkul" };
				case 1621:
					return new Object[] { 40, "tokkul" };
				case 1619:
					return new Object[] { 70, "tokkul" };
				case 1617:
					return new Object[] { 150, "tokkul" };
				case 1631:
					return new Object[] { 1600, "tokkul" };
				case 6571:
					return new Object[] { 50000, "tokkul" };
				case 11128:
					return new Object[] { 22000, "tokkul" };
				case 6522:
					return new Object[] { 20, "tokkul" };
				case 6524:
				case 6523:// this the item id
				case 6526:
					return new Object[] { 5000, "tokkul" };// these will be the
															// prices
				case 6528:
				case 6568:
					return new Object[] { 800, "tokkul" };
				}
			} else if (shop == DUNGEONEERING_STORE) {
				switch (item) {
				case 18351:
				case 18349:
				case 18353:
				case 18357:
				case 18355:
				case 18359:
					return new Object[] { 200000, "Dungeoneering tokens" };
				case 18335:
					return new Object[] { 75000, "Dungeoneering tokens" };
				}
			} else if (shop == DONATION_STORE) {
				switch (item) {
				case 932:
				case 936:
				case 996:
				case 939:
				case 3248:
					return new Object[] { 250, "Donation points" };
				case 665:
					return new Object[] { 250, "Donation points" };
				case 669:
				case 670:
				case 666:
				case 671:
				case 672:
				case 673:
					return new Object[] { 150, "Donation points" };
				case 3626:
					return new Object[] { 750, "Donation points" };
				case 3628:
				case 3629:
				case 3627:
				case 3630:
				case 3631:
				case 3632:
					return new Object[] { 350, "Donation points" };
				case 1840:
				case 1841:
				case 1842:
					return new Object[] { 500, "Donation points" };

				case 5234:
				case 19919:
					return new Object[] { 4000, "Donation points" };
				case 1849:
					return new Object[] { 2250, "Donation points" };
				case 20907:
					return new Object[] { 15000, "Donation points" };
				case 1843:
				case 1845:
				case 1846:
				case 1848:
				case 1850:
				case 20652:
					return new Object[] { 500, "Donation points" };
				case 8817:
				case 8818:
				case 8821:
				case 8822:
				case 8820:
				case 8816:
					return new Object[] { 850, "Donation points" };

				}
			} else if (shop == DONATION_STORE2) {
				switch (item) {
				case 85:
				case 84:
					return new Object[] { 250, "Donation points" };
				case 3135:
					return new Object[] { 150, "Donation points" };
				case 2572:
					return new Object[] { 300, "Donation points" };
				case 275:
				case 4204:
					return new Object[] { 500, "Donation points" };
				case 293:
					return new Object[] { 1500, "Donation points" };
				case 596:
					return new Object[] { 100, "Donation points" };
				case 3280:
					return new Object[] { 75, "Donation points" };
				case 1009:
				case 894:
					return new Object[] { 500, "Donation points" };
				case 6199:
					return new Object[] { 10, "Donation points" };
				case 15501:
					return new Object[] { 40, "Donation points" };
				case 2800:
					return new Object[] { 150, "Donation points" };
				case 2801:
					return new Object[] { 50, "Donation points" };
				case 3082:
					return new Object[] { 300, "Donation points" };
				case 3278:
					return new Object[] { 500, "Donation points" };
				case 601:
					return new Object[] { 1000, "Donation points" };
				case 423:
				case 589:
					return new Object[] { 1500, "Donation points" };
				case 20202:
					return new Object[] { 3500, "Donation points" };
				case 421:
					return new Object[] { 150, "Donation points" };
				case 295:
					return new Object[] { 75, "Donation points" };
				case 7671:
					return new Object[] { 500, "Donation points" };
				case 7673:
					return new Object[] { 500, "Donation points" };
				case 4657:
					return new Object[] { 500, "Donation points" };
				case 9104:
					return new Object[] { 250, "Donation points" };
				case 1840:
					return new Object[] { 1500, "Donation points" };
				case 1841:
					return new Object[] { 1500, "Donation points" };
				case 1842:
					return new Object[] { 1500, "Donation points" };

				}
			} else if (shop == PRESTIGE_STORE) {
				switch (item) {
				case 11599:
					return new Object[] { 2000, "Prestige points" };
				case 11600:
					return new Object[] { 2000, "Prestige points" };
				case 11601:
				case 11602:
					return new Object[] { 2000, "Prestige points" };
				case 11603:
					return new Object[] { 2000, "Prestige points" };
				case 14018:
					return new Object[] { 250, "Prestige points" };
				case 19335:
					return new Object[] { 30, "Prestige points" };
				case 18405:
					return new Object[] { 3000, "Prestige points" };
				case 15220:
				case 15020:
				case 15019:
				case 15018:
					return new Object[] { 20, "Prestige points" };
				case 12521:
				case 12519:
				case 12520:
					return new Object[] { 2000, "Prestige points" };
				case 6570:
					return new Object[] { 215, "Prestige points" };
				case 19111:
					return new Object[] { 500, "Prestige points" };
				case 13855:
				case 13848:
				case 13856:
				case 13854:
				case 13853:
				case 138352:
				case 13851:
				case 13850:
				case 13849:
				case 13852:
				case 13857:
					return new Object[] { 5, "Prestige points" };
				case 10400:
				case 10402:
				case 10416:
				case 10418:
				case 10408:
				case 10410:
				case 10412:
				case 10414:
				case 10404:
				case 10406:
					return new Object[] { 2, "Prestige points" };
				case 14595:
				case 14603:
					return new Object[] { 5, "Prestige points" };
				case 14602:
				case 14605:
					return new Object[] { 3, "Prestige points" };
				case 21636:
					return new Object[] { 250, "Prestige points" };
				case 695:
					return new Object[] { 1500, "Prestige points" };
				case 21077:
					return new Object[] { 1500, "Prestige points" };
				}

			} else if (shop == PRESTIGE_STORE2) {
				switch (item) {
				case 18406:
					return new Object[] { 6000, "Prestige points" };
				case 18407:
					return new Object[] { 9000, "Prestige points" };
				case 195:
					return new Object[] { 100, "Prestige points" };
				case 4061:
					return new Object[] { 400, "Prestige points" };
				case 4062:
					return new Object[] { 500, "Prestige points" };
				case 4063:
					return new Object[] { 400, "Prestige points" };
				case 1171:
					return new Object[] { 2500, "Prestige points" };
				case 2597:
					return new Object[] { 1500, "Prestige points" };
				}
			} else if (shop == SLAYER_STORE) {
				switch (item) {
				case 13263:
					return new Object[] { 200, "Slayer points" };
				case 18829:
					return new Object[] { 5000, "Slayer points" };
				case 19864:
					return new Object[] { 1, "Slayer points" };
				case 6070:
					return new Object[] { 28000, "Slayer points" };
				case 11593: // guard
					return new Object[] { 1000, "Slayer points" };
				case 11596: // gloves
				case 11597: // boots
					return new Object[] { 1000, "Slayer points" };
				case 11598: // wings
					return new Object[] { 1000, "Slayer points" };
				case 13281:
					return new Object[] { 5, "Slayer points" };
				case 11604:
					return new Object[] { 300, "Slayer points" };
				case 11605:
					return new Object[] { 350, "Slayer points" };
				case 11606:
				case 11607:
					return new Object[] { 300, "Slayer points" };
				case 11608:
					return new Object[] { 350, "Slayer points" };
				case 84:
					return new Object[] { 4000, "Slayer points" };
				case 4204:
					return new Object[] { 10000, "Slayer points" };
				case 15403:
				case 11730:
				case 10887:
				case 15241:
					return new Object[] { 175, "Slayer points" };
				case 11235:
				case 15486:
					return new Object[] { 150, "Slayer points" };
				case 15243:
					return new Object[] { 2, "Slayer points" };
				case 10551:
					return new Object[] { 125, "Slayer points" };
				case 21082:
				case 21083:
				case 21084:
					return new Object[] { 350, "Slayer points" };
				case 1321:
					return new Object[] { 250, "Slayer points" };
				case 2572:
					return new Object[] { 5000, "Slayer points" };
				case 21077:
					return new Object[] { 350, "Slayer points" };
				case 4703:
					return new Object[] { 1000, "Slayer points" };
				case 3667:
				case 6707:
					return new Object[] { 2000, "Slayer points" };
				case 14927:
				case 14925:
				case 14926:
				case 11540:
				case 11541:
				case 4480:
					return new Object[] { 40000, "Slayer points" };
				case 9121:
					return new Object[] { 75000, "Slayer points" };
				}
			} else if (shop == UNWANTED_DROP_STORE) {
				switch (item) {
				case 20250:
				case 20252:
				case 20253:
				case 20251:
				case 14556:
				case 14557:
				case 14558:
					return new Object[] { 4, "@gre@" + GameSettings.SERVER_NAME + " Bucks" };
				}
			} else if (shop == UNWANTED_DROP_STORE2) {
				switch (item) {
				case 14559:
				case 14561:
				case 14562:
				case 14565:
				case 14560:
				case 14563:
				case 14564:
					return new Object[] { 10,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE3) {
				switch (item) {
				case 14566:
				case 14568:
				case 14569:
				case 14567:
				case 14572:
				case 14570:
				case 14571:
					return new Object[] { 16,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE4) {
				switch (item) {
				case 20690:
				case 14582:
				case 14583:
				case 14581:
				case 14586:
				case 14584:
				case 14585:
					return new Object[] { 25,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE5) {
				switch (item) {
				case 14587:
				case 3314:
				case 14590:
				case 14588:
				case 80:
				case 14591:
				case 79:
					return new Object[] { 55,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE6) {
				switch (item) {
				case 81:
				case 14596:
				case 14597:
				case 14619:
				case 18742:
				case 14598:
				case 14599:
					return new Object[] { 65,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE7) {
				switch (item) {
				case 3619:
				case 3621:
				case 3622:
				case 3620:
				case 3625:
				case 3623:
				case 3624:
					return new Object[] { 80,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE8) {
				switch (item) {
				case 625:
				case 623:
				case 621:
				case 624:
				case 618:
				case 620:
				case 619:
					return new Object[] { 230,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE9) {
				switch (item) {
				case 20691:
				case 20694:
					return new Object[] { 2,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				case 20697:
				case 20695:
				case 3660:
				case 3661:
					return new Object[] { 4,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				case 3654:
				case 3655:
				case 3656:
				case 3657:
				case 3658:
				case 3659:
					return new Object[] { 4,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE10) {
				switch (item) {
				case 20250:
				case 20252:
				case 20253:
				case 20251:
				case 14556:
				case 14557:
					return new Object[] { 10,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE11) {
				switch (item) {
				case 20250:
				case 20252:
				case 20253:
				case 20251:
				case 14556:
				case 14557:
					return new Object[] { 10,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE12) {
				switch (item) {
				case 20250:
				case 20252:
				case 20253:
				case 20251:
				case 14556:
				case 14557:
					return new Object[] { 10,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE13) {
				switch (item) {
				case 4450:
				case 4451:
				case 4452:
				case 4453:
				case 4454:
					return new Object[] { 5,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE14) {
				switch (item) {
				case 4553:
				case 4554:
				case 4555:
				case 4556:
				case 4557:
					return new Object[] { 12,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE15) {
				switch (item) {
				case 3999:
				case 4000:
				case 4001:
				case 4002:
				case 4003:
					return new Object[] { 24,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE16) {
				switch (item) {
				case 934:
				case 935:
				case 937:
				case 940:
				case 2884:
					return new Object[] { 40,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE17) {
				switch (item) {
				case 933:
				case 938:
				case 979:
				case 3246:
				case 14453:
					return new Object[] { 70,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE18) {
				switch (item) {
				case 932:
				case 936:
				case 939:
				case 942:
				case 3248:
					return new Object[] { 125,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE19) {
				switch (item) {
				case 7040:
				case 7046:
				case 7152:
				case 7619:
				case 20458:
				case 20460:
				case 20462:
					return new Object[] { 5,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE20) {
				switch (item) {
				case 7041:
				case 7047:
				case 7153:
				case 7621:
				case 20300:
				case 20526:
				case 20528:
					return new Object[] { 12,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE21) {
				switch (item) {
				case 20834:
				case 20840:
				case 20838:
				case 20832:
				case 20836:
				case 7042:
				case 19730:
					return new Object[] { 24,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE22) {
				switch (item) {
				case 7043:
				case 7048:
				case 7155:
				case 7670:
				case 20786:
				case 20733:
				case 20732:
					return new Object[] { 40,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE23) {
				switch (item) {
				case 7044:
				case 7049:
				case 7154:
				case 7672:
				case 20301:
				case 20302:
				case 20310:
					return new Object[] { 70,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			} else if (shop == UNWANTED_DROP_STORE24) {
				switch (item) {
				case 7045:
				case 7151:
				case 20922:
				case 20924:
				case 20926:
				case 20932:
				case 20934:
					return new Object[] { 125,  "@gre@" + GameSettings.SERVER_NAME + " Bucks"  };
				}
			}
			return null;
		}

		public static Map<Integer, Shop> getShops() {
			return shops;
		}

		public static JsonLoader parseShops() {
			return new JsonLoader() {
				@Override
				public String filePath() {
					return "./data/def/json/world_shops.json";
				}

				@Override
				public void load(JsonObject reader, Gson builder) {
					int id = reader.get("id").getAsInt();
					String name = reader.get("name").getAsString();
					Item[] items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
					Item currency = new Item(reader.get("currency").getAsInt());
					shops.put(id, new Shop(null, id, name, currency, items));
				}
			};
		}
	}

	/**
	 * The shop interface id.
	 */
	public static final int INTERFACE_ID = 3824;

	/**
	 * The starting interface child id of items.
	 */
	public static final int ITEM_CHILD_ID = 3900;

	/**
	 * The interface child id of the shop's name.
	 */
	public static final int NAME_INTERFACE_CHILD_ID = 3901;

	/**
	 * The inventory interface id, used to set the items right click values to
	 * 'sell'.
	 */
	public static final int INVENTORY_INTERFACE_ID = 3823;

	/*
	 * Declared shops
	 */
	public static final int GENERAL_STORE = 12;

	public static final int RECIPE_FOR_DISASTER_STORE = 36;

	private static final int VOTING_REWARDS_STORE = 27;

	private static final int SKILLING_SHARD_STORE = 26;

	private static final int ENERGY_FRAGMENT_STORE = 33;

	private static final int AGILITY_TICKET_STORE = 39;

	private static final int GRAVEYARD_STORE = 42;

	private static final int TOKKUL_EXCHANGE_STORE = 43;

	private static final int SKILLCAPE_STORE_1 = 8;

	private static final int SKILLCAPE_STORE_2 = 9;

	private static final int SKILLCAPE_STORE_3 = 10;

	private final static int UNWANTED_DROP_STORE = 49;

	private final static int UNWANTED_DROP_STORE2 = 51;
	private final static int UNWANTED_DROP_STORE3 = 52;
	private final static int UNWANTED_DROP_STORE4 = 53;
	private final static int UNWANTED_DROP_STORE5 = 54;
	private final static int UNWANTED_DROP_STORE6 = 55;
	private final static int UNWANTED_DROP_STORE7 = 56;
	private final static int UNWANTED_DROP_STORE8 = 57;
	private final static int UNWANTED_DROP_STORE9 = 58;
	private final static int UNWANTED_DROP_STORE10 = 59;
	private final static int UNWANTED_DROP_STORE11 = 60;
	private final static int UNWANTED_DROP_STORE12 = 61;
	private final static int UNWANTED_DROP_STORE13 = 62;
	private final static int UNWANTED_DROP_STORE14 = 63;
	private final static int UNWANTED_DROP_STORE15 = 64;
	private final static int UNWANTED_DROP_STORE16 = 65;
	private final static int UNWANTED_DROP_STORE17 = 66;
	private final static int UNWANTED_DROP_STORE18 = 67;
	private final static int UNWANTED_DROP_STORE19 = 74;
	private final static int UNWANTED_DROP_STORE20 = 69;
	private final static int UNWANTED_DROP_STORE21 = 70;
	private final static int UNWANTED_DROP_STORE22 = 71;
	private final static int UNWANTED_DROP_STORE23 = 72;
	private final static int UNWANTED_DROP_STORE24 = 73;

	public static final int RUTHLESS_POINT_STORE = 29;

	private static final int DUNGEONEERING_STORE = 44;

	private static final int PRESTIGE_STORE = 46;
	private static final int PRESTIGE_STORE2 = 68;
	private static final int SKILL_STORE = 75;
	private static final int AFK_FISHING_STORE = 76;
	private static final int AFK_MINING_STORE = 77;
	private static final int AFK_WOODCUTTING_STORE = 78;

	private static final int SLAYER_STORE = 47;

	private static final int DONATION_STORE = 48;
	private static final int DONATION_STORE2 = 50;
	private static final int DONATION_STORE3 = 79;

	/**
	 * Checks if a player has enough inventory space to buy an item
	 * 
	 * @param item
	 *            The item which the player is buying
	 * @return true or false if the player has enough space to buy the item
	 */
	public static boolean hasInventorySpace(Player player, Item item, int currency, int pricePerItem) {
		if (player.getInventory().getFreeSlots() >= 1) {
			return true;
		}
		if (item.getDefinition().isStackable()) {
			if (player.getInventory().contains(item.getId())) {
				return true;
			}
		}
		if (currency != -1) {
			if (player.getInventory().getFreeSlots() == 0
					&& player.getInventory().getAmount(currency) == pricePerItem) {
				return true;
			}
		}
		return false;
	}

	public static boolean shopBuysItem(int shopId, Item item) {
		if (shopId == GENERAL_STORE)
			return true;
		if (shopId == DUNGEONEERING_STORE || shopId == SKILLING_SHARD_STORE || shopId == RUTHLESS_POINT_STORE
				|| shopId == VOTING_REWARDS_STORE || shopId == RECIPE_FOR_DISASTER_STORE
				|| shopId == ENERGY_FRAGMENT_STORE || shopId == AGILITY_TICKET_STORE || shopId == GRAVEYARD_STORE
				|| shopId == TOKKUL_EXCHANGE_STORE || shopId == PRESTIGE_STORE || shopId == SLAYER_STORE
				|| shopId == DONATION_STORE || shopId == DONATION_STORE2 || shopId == PRESTIGE_STORE2 || shopId == SKILL_STORE|| shopId == AFK_FISHING_STORE|| shopId == AFK_MINING_STORE|| shopId == AFK_WOODCUTTING_STORE)
			return false;
		Shop shop = ShopManager.getShops().get(shopId);
		if (shop != null && shop.getOriginalStock() != null) {
			for (Item it : shop.getOriginalStock()) {
				if (it != null && it.getId() == item.getId())
					return true;
			}
		}
		return false;
	}

	private final int id;

	private String name;

	private Item currency;

	private Item[] originalStock;

	private boolean restockingItems;

	/*
	 * The shop constructor
	 */
	public Shop(Player player, int id, String name, Item currency, Item[] stockItems) {
		super(player);
		if (stockItems.length > 42)
			throw new ArrayIndexOutOfBoundsException(
					"Stock cannot have more than 40 items; check shop[" + id + "]: stockLength: " + stockItems.length);
		this.id = id;
		this.name = name.length() > 0 ? name : "General Store";
		this.currency = currency;
		this.originalStock = new Item[stockItems.length];
		for (int i = 0; i < stockItems.length; i++) {
			Item item = new Item(stockItems[i].getId(), stockItems[i].getAmount());
			add(item, false);
			this.originalStock[i] = item;
		}
	}

	@Override
	public Shop add(Item item, boolean refresh) {
		super.add(item, false);
		if (id != RECIPE_FOR_DISASTER_STORE)
			publicRefresh();
		return this;
	}

	@Override
	public int capacity() {
		return 42;
	}

	/**
	 * Checks a value of an item in a shop
	 * 
	 * @param player
	 *            The player who's checking the item's value
	 * @param slot
	 *            The shop item's slot (in the shop!)
	 * @param sellingItem
	 *            Is the player selling the item?
	 */
	public void checkValue(Player player, int slot, boolean sellingItem) {
		this.setPlayer(player);
		Item shopItem = new Item(getItems()[slot].getId());
		if (!player.isShopping()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		Item item = sellingItem ? player.getInventory().getItems()[slot] : getItems()[slot];
		if (item.getId() == 995)
			return;
		if (sellingItem) {
			if (!shopBuysItem(id, item)) {
				player.getPacketSender().sendMessage("You cannot sell this item to this store.");
				return;
			}

		}
		int finalValue = 0;
		String finalString = sellingItem ? "" + ItemDefinition.forId(item.getId()).getName() + ": shop will buy for "
				: "" + ItemDefinition.forId(shopItem.getId()).getName() + " currently costs ";
		if (getCurrency().getId() != -1) {
			finalValue = ItemDefinition.forId(item.getId()).getValue();
			String s = currency.getDefinition().getName().toLowerCase().endsWith("s")
					? currency.getDefinition().getName().toLowerCase()
					: currency.getDefinition().getName().toLowerCase() + "s";
			/** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
			if (id == UNWANTED_DROP_STORE || id == UNWANTED_DROP_STORE2 || id == UNWANTED_DROP_STORE3
					|| id == UNWANTED_DROP_STORE4 || id == UNWANTED_DROP_STORE5 || id == UNWANTED_DROP_STORE6
					|| id == UNWANTED_DROP_STORE7 || id == UNWANTED_DROP_STORE8 || id == UNWANTED_DROP_STORE9
					|| id == UNWANTED_DROP_STORE10 || id == UNWANTED_DROP_STORE11 || id == UNWANTED_DROP_STORE12
					|| id == UNWANTED_DROP_STORE13 || id == UNWANTED_DROP_STORE14 || id == UNWANTED_DROP_STORE15
					|| id == UNWANTED_DROP_STORE16 || id == UNWANTED_DROP_STORE17 || id == UNWANTED_DROP_STORE18
					|| id == UNWANTED_DROP_STORE19 || id == UNWANTED_DROP_STORE20 || id == UNWANTED_DROP_STORE21
					|| id == UNWANTED_DROP_STORE22 || id == UNWANTED_DROP_STORE23 || id == UNWANTED_DROP_STORE24
					|| id == TOKKUL_EXCHANGE_STORE || id == ENERGY_FRAGMENT_STORE || id == RUTHLESS_POINT_STORE
					|| id == AGILITY_TICKET_STORE || id == SKILLING_SHARD_STORE || id == GRAVEYARD_STORE|| id == AFK_FISHING_STORE|| id == AFK_MINING_STORE|| id == AFK_WOODCUTTING_STORE) {
				Object[] obj = ShopManager.getCustomShopData(id, item.getId());
				if (obj == null)
					return;
				finalValue = (int) obj[0];
				s = (String) obj[1];
			}
			if (sellingItem) {
				if (finalValue != 1) {
					finalValue = (int) (finalValue * 0.85);
				}
			}
			finalString += "" + finalValue + " " + s + "" + shopPriceEx(finalValue) + ".";
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, item.getId());
			if (obj == null)
				return;
			finalValue = (int) obj[0];
			if (sellingItem) {
				if (finalValue != 1) {
					finalValue = (int) (finalValue * 0.85);
				}
			}
			finalString += "" + finalValue + " " + (String) obj[1] + ".";
		}
		if (player != null && finalValue > 0) {
			player.getPacketSender().sendMessage(finalString);
			return;
		}
	}

	public void fireRestockTask() {
		if (isRestockingItems() || fullyRestocked())
			return;
		setRestockingItems(true);
		TaskManager.submit(new ShopRestockTask(this));
	}

	@Override
	public Shop full() {
		getPlayer().getPacketSender().sendMessage("The shop is currently full. Please come back later.");
		return this;
	}

	public boolean fullyRestocked() {
		if (id == GENERAL_STORE) {
			return getValidItems().size() == 0;
		} else if (id == RECIPE_FOR_DISASTER_STORE) {
			return true;
		}
		if (getOriginalStock() != null) {
			for (int shopItemIndex = 0; shopItemIndex < getOriginalStock().length; shopItemIndex++) {
				if (getItems()[shopItemIndex].getAmount() != getOriginalStock()[shopItemIndex].getAmount())
					return false;
			}
		}
		return true;
	}

	public Item getCurrency() {
		return currency;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Item[] getOriginalStock() {
		return this.originalStock;
	}

	public boolean isRestockingItems() {
		return restockingItems;
	}

	/**
	 * Opens a shop for a player
	 * 
	 * @param player
	 *            The player to open the shop for
	 * @return The shop instance
	 */
	public Shop open(Player player) {
		setPlayer(player);
		getPlayer().getPacketSender().sendInterfaceRemoval().sendClientRightClickRemoval();
		getPlayer().setShop(ShopManager.getShops().get(id)).setInterfaceId(INTERFACE_ID).setShopping(true);
		refreshItems();
		return this;
	}

	/**
	 * Refreshes a shop for every player who's viewing it
	 */
	public void publicRefresh() {
		Shop publicShop = ShopManager.getShops().get(id);
		if (publicShop == null)
			return;
		publicShop.setItems(getItems());
		for (Player player : World.getPlayers()) {
			if (player == null)
				continue;
			if (player.getShop() != null && player.getShop().id == id && player.isShopping())
				player.getShop().setItems(publicShop.getItems());
		}
	}

	@Override
	public Shop refreshItems() {
		if (id == RECIPE_FOR_DISASTER_STORE) {
			RecipeForDisaster.openRFDShop(getPlayer());
			return this;
		}
		for (Player player : World.getPlayers()) {
			if (player == null || !player.isShopping() || player.getShop() == null || player.getShop().id != id)
				continue;
			player.getPacketSender().sendItemContainer(player.getInventory(), INVENTORY_INTERFACE_ID);
			player.getPacketSender().sendItemContainer(ShopManager.getShops().get(id), ITEM_CHILD_ID);
			player.getPacketSender().sendString(NAME_INTERFACE_CHILD_ID, name);
			if (player.getInputHandling() == null || !(player.getInputHandling() instanceof EnterAmountToSellToShop
					|| player.getInputHandling() instanceof EnterAmountToBuyFromShop))
				player.getPacketSender().sendInterfaceSet(INTERFACE_ID, INVENTORY_INTERFACE_ID - 1);
		}
		return this;
	}

	public void sellItem(Player player, int slot, long amountToSell) {
		this.setPlayer(player);
		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		Item itemToSell = player.getInventory().getItems()[slot];
		if (!itemToSell.sellable()) {
			player.getPacketSender().sendMessage("This item cannot be sold.");
			return;
		}
		if (!shopBuysItem(id, itemToSell)) {
			player.getPacketSender().sendMessage("You cannot sell this item to this store.");
			return;
		}
		if (!player.getInventory().contains(itemToSell.getId()) || itemToSell.getId() == 995)
			return;
		if (this.full(itemToSell.getId()))
			return;
		if (player.getInventory().getAmount(itemToSell.getId()) < amountToSell)
			amountToSell = player.getInventory().getAmount(itemToSell.getId());
		if (amountToSell == 0)
			return;
		int itemId = itemToSell.getId();
		boolean customShop = this.getCurrency().getId() == -1;
		boolean inventorySpace = customShop ? true : false;
		if (!customShop) {
			if (!itemToSell.getDefinition().isStackable()) {
				if (!player.getInventory().contains(this.getCurrency().getId()))
					inventorySpace = true;
			}
			if (player.getInventory().getFreeSlots() <= 0
					&& player.getInventory().getAmount(this.getCurrency().getId()) > 0)
				inventorySpace = true;
			if (player.getInventory().getFreeSlots() > 0
					|| player.getInventory().getAmount(this.getCurrency().getId()) > 0)
				inventorySpace = true;
		}
		long itemValue = 0;
		if (getCurrency().getId() > 0 && getCurrency().getId() != 13664) {
			itemValue = ItemDefinition.forId(itemToSell.getId()).getValue();
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, itemToSell.getId());
			if (obj == null)
				return;
			itemValue = (int) obj[0];
		}
		if (itemValue <= 0)
			return;
		if (this.id != UNWANTED_DROP_STORE)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE2)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE3)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE4)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE5)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE6)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE7)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE8)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE9)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE10)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE11)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE12)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE19)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE20)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE21)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE22)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE23)
			itemValue = (int) (itemValue * 1);
		if (this.id != UNWANTED_DROP_STORE24)
			itemValue = (int) (itemValue * 1);
		if (itemValue <= 0) {
			itemValue = 1;
		}
		for (int i = (int) amountToSell; i > 0; i--) {
			itemToSell = new Item(itemId);
			if (this.full(itemToSell.getId()) || !player.getInventory().contains(itemToSell.getId())
					|| !player.isShopping())
				break;
			if (!itemToSell.getDefinition().isStackable()) {
				if (inventorySpace) {
					super.switchItem(player.getInventory(), this, itemToSell.getId(), -1);
					if (!customShop) {
						if (this.id == GENERAL_STORE) {
							player.setMoneyInPouch(player.getMoneyInPouch() + (itemValue));
							player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
						} else {
							player.getInventory().add(new Item(getCurrency().getId(), (int) itemValue), false);
						}
					} else {
						// Return points here
					}
				} else {
					player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
					break;
				}
			} else {
				if (inventorySpace) {
					super.switchItem(player.getInventory(), this, itemToSell.getId(), (int) amountToSell);
					if (!customShop) {
						if (this.id == GENERAL_STORE) {
							long totalToAdd = (long) (amountToSell * itemValue); // Oh wait.
							System.out.println("long max value: " + Long.MAX_VALUE);
							System.out.println("Sell value: " + (amountToSell * itemValue));
							System.out.println("Item value: " + itemValue + " amount to sell: " + amountToSell);
							player.setMoneyInPouch(player.getMoneyInPouch() + totalToAdd);
							player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
						} else {
							player.getInventory().add(new Item(getCurrency().getId(), (int) (itemValue * amountToSell)),
									false);
						}
					} else {
						// Return points here
					}
					break;
				} else {
					player.getPacketSender().sendMessage("Please free some inventory space before doing that.");
					break;
				}
			}
			amountToSell--;
		}
		if (customShop) {
			player.getPointsManager().refreshPanel();
		}
		player.getInventory().refreshItems();
		fireRestockTask();
		refreshItems();
		publicRefresh();
	}

	public Shop setCurrency(Item currency) {
		this.currency = currency;
		return this;
	}

	public Shop setName(String name) {
		this.name = name;
		return this;
	}

	public void setRestockingItems(boolean restockingItems) {
		this.restockingItems = restockingItems;
	}

	public String shopPriceEx(int shopPrice) {
		String ShopAdd = "";
		if (shopPrice >= 1000 && shopPrice < 1000000) {
			ShopAdd = " (" + shopPrice / 1000 + "K)";
		} else if (shopPrice >= 1000000) {
			ShopAdd = " (" + shopPrice / 1000000 + " million)";
		}
		return ShopAdd;
	}

	private boolean shopSellsItem(Item item) {
		return contains(item.getId());
	}

	@Override
	public StackType stackType() {
		return StackType.STACKS;
	}

	/**
	 * Buying an item from a shop
	 */
	@Override
	public Shop switchItem(ItemContainer to, Item item, int slot, boolean sort, boolean refresh) {
		final Player player = getPlayer();
		if (player == null)
			return this;
		if (!player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return this;
		}

		if (this.id == UNWANTED_DROP_STORE) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE2) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE3) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE4) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE5) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE6) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE7) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE8) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE9) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE10) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE11) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE12) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE13) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE14) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE15) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE16) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE17) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE18) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE19) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE20) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE21) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE22) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE23) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == UNWANTED_DROP_STORE24) {
			player.getPacketSender().sendMessage("You can only sell items to this store.");
			return this;
		}
		if (this.id == GENERAL_STORE) {
			if (player.getGameMode() == GameMode.IRONMAN) {
				player.getPacketSender()
						.sendMessage("Ironman-players are not allowed to buy items from the general-store.");
				return this;
			}

		}
		if (!shopSellsItem(item))
			return this;
		if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {
			player.getPacketSender().sendMessage("The shop has run out of stock for this item.");
			return this;
		}
		if (item.getAmount() > getItems()[slot].getAmount())
			item.setAmount(getItems()[slot].getAmount());
		int amountBuying = item.getAmount();
		if (amountBuying == 0)
			return this;
		if (amountBuying > 5000) {
			player.getPacketSender().sendMessage(
					"You can only buy 5000 " + ItemDefinition.forId(item.getId()).getName() + "s at a time.");
			return this;
		}
		boolean customShop = getCurrency().getId() == -1;
		boolean usePouch = false;
		long playerCurrencyAmount = 0;
		int value = ItemDefinition.forId(item.getId()).getValue();
		String currencyName = "";
		if (getCurrency().getId() != -1) {
			playerCurrencyAmount = player.getInventory().getAmount(currency.getId());
			currencyName = ItemDefinition.forId(currency.getId()).getName().toLowerCase();
			if (currency.getId() == 995) {

				if (player.getMoneyInPouch() >= value) {
					playerCurrencyAmount = player.getMoneyInPouch();
					if (!(player.getInventory().getFreeSlots() == 0
							&& player.getInventory().getAmount(currency.getId()) == value)) {
						usePouch = true;
					}
				}
			} else {
				/** CUSTOM CURRENCY, CUSTOM SHOP VALUES **/
				if (id == TOKKUL_EXCHANGE_STORE || id == ENERGY_FRAGMENT_STORE || id == SKILLING_SHARD_STORE || id == RUTHLESS_POINT_STORE
						|| id == AGILITY_TICKET_STORE || id == GRAVEYARD_STORE|| id == AFK_FISHING_STORE|| id == AFK_MINING_STORE|| id == AFK_WOODCUTTING_STORE) {
					value = (int) ShopManager.getCustomShopData(id, item.getId())[0];
				}
			}
		} else {
			Object[] obj = ShopManager.getCustomShopData(id, item.getId());
			if (obj == null)
				return this;
			value = (int) obj[0];
			currencyName = (String) obj[1];
			if (id == VOTING_REWARDS_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("voting");
			} else if (id == DUNGEONEERING_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("dung");
			} else if (id == RUTHLESS_POINT_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints(GameSettings.SERVER_NAME);
			} else if (id == PRESTIGE_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("prestige");
			} else if (id == PRESTIGE_STORE2) {
				playerCurrencyAmount = player.getPointsManager().getPoints("prestige");
			} else if (id == SLAYER_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("slayer");
			} else if (id == DONATION_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("donation");
			} else if (id == DONATION_STORE2) {
				playerCurrencyAmount = player.getPointsManager().getPoints("donation");
			} else if (id == SKILL_STORE) {
				playerCurrencyAmount = player.getPointsManager().getPoints("skill");
			}
		}
		if (value <= 0) {
			return this;
		}
		if (!hasInventorySpace(player, item, getCurrency().getId(), value)) {
			player.getPacketSender().sendMessage("You do not have any free inventory slots.");
			return this;
		}
		if (playerCurrencyAmount <= 0 || playerCurrencyAmount < value) {
			player.getPacketSender().sendMessage("You do not have enough "
					+ (currencyName.endsWith("s") ? currencyName : currencyName + "s") + " to purchase this item.");
			return this;
		}
		if (id == SKILLCAPE_STORE_1 || id == SKILLCAPE_STORE_2 || id == SKILLCAPE_STORE_3) {
			for (int i = 0; i < item.getDefinition().getRequirement().length; i++) {
				int req = item.getDefinition().getRequirement()[i];
				if ((i == 3 || i == 5) && req == 99)
					req *= 10;
				if (req > player.getSkillManager().getMaxLevel(i)) {
					player.getPacketSender().sendMessage("You need to have at least level 99 in "
							+ Misc.formatText(Skill.forId(i).toString().toLowerCase()) + " to buy this item.");
					return this;
				}
			}
		}

		if (item.getDefinition().isStackable()) {

			if (playerCurrencyAmount >= value && hasInventorySpace(player, item, getCurrency().getId(), value)) {

				long canBeBought = playerCurrencyAmount / value;

				if (canBeBought >= amountBuying) {
					canBeBought = amountBuying;
				}

				long invAmount = player.getInventory().getAmount(item.getId());

				if (invAmount + canBeBought > Integer.MAX_VALUE) {
					canBeBought = (int) (Integer.MAX_VALUE - invAmount);
				}

				if (!customShop) {
					if (usePouch) {
						player.setMoneyInPouch(player.getMoneyInPouch() - (value * canBeBought));
						player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
					} else {
						player.getInventory().delete(currency.getId(), (int)(value * canBeBought), false);
					}

				} else {

					if (id == VOTING_REWARDS_STORE) {
						player.getPointsManager().decreasePoints("voting", (int) (value * canBeBought));
					} else if (id == DUNGEONEERING_STORE) {
						player.getPointsManager().decreasePoints("dung", (int) (value * canBeBought));
					} else if (id == RUTHLESS_POINT_STORE) {
						player.getPointsManager().decreasePoints( GameSettings.SERVER_NAME, (int) (value * canBeBought));
					} else if (id == PRESTIGE_STORE) {
						player.getPointsManager().decreasePoints("prestige", (int) (value * canBeBought));
					} else if (id == PRESTIGE_STORE2) {
						player.getPointsManager().decreasePoints("prestige", (int) (value * canBeBought));
					} else if (id == SLAYER_STORE) {
						player.getPointsManager().decreasePoints("slayer", (int) (value * canBeBought));
					} else if (id == DONATION_STORE) {
						player.getPointsManager().decreasePoints("donation", (int) (value * canBeBought));
					} else if (id == DONATION_STORE2) {
						player.getPointsManager().decreasePoints("donation", (int) (value * canBeBought));
					} else if (id == SKILL_STORE) {
						player.getPointsManager().decreasePoints("skill", (int) (value * canBeBought));
					}

				}
				if(item.getId() == 894) {
					ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.POINTZONE, 0, 1);
				}

				super.switchItem(to, new Item(item.getId(), (int) canBeBought), slot, false, false);

			}

		} else {

			for (int i = amountBuying; i > 0; i--) {

				if (!shopSellsItem(item)) {
					break;
				}

				if (getItems()[slot].getAmount() <= 1 && id != GENERAL_STORE) {
					player.getPacketSender().sendMessage("The shop has run out of stock for this item.");
					break;
				}

				if (!item.getDefinition().isStackable()) {
					if (playerCurrencyAmount >= value
							&& hasInventorySpace(player, item, getCurrency().getId(), value)) {

						if (!customShop) {
							if (usePouch) {
								player.setMoneyInPouch(player.getMoneyInPouch() - value);
								player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
							} else {
								player.getInventory().delete(currency.getId(), value, false);
							}
						} else {
							if (id == VOTING_REWARDS_STORE) {
								player.getPointsManager().decreasePoints("voting", value);
							} else if (id == DUNGEONEERING_STORE) {
								player.getPointsManager().decreasePoints("dung", value);
							} else if (id == RUTHLESS_POINT_STORE) {
								player.getPointsManager().decreasePoints(GameSettings.SERVER_NAME, value);
							} else if (id == PRESTIGE_STORE) {
								player.getPointsManager().decreasePoints("prestige", value);
							} else if (id == PRESTIGE_STORE2) {
								player.getPointsManager().decreasePoints("prestige", value);
							} else if (id == SLAYER_STORE) {
								player.getPointsManager().decreasePoints("slayer", value);
							} else if (id == DONATION_STORE) {
								player.getPointsManager().decreasePoints("donation", value);
							} else if (id == DONATION_STORE2) {
								player.getPointsManager().decreasePoints("donation", value);
							} else if (id == SKILL_STORE) {
								player.getPointsManager().decreasePoints("skill", value);
							}
						}

						super.switchItem(to, new Item(item.getId(), 1), slot, false, false);
						if(item.getId() == 894) {
							ZoneTasks.doTaskProgress(player, ZoneTasks.ZoneData.POINTZONE, 0, 1);
						}
						playerCurrencyAmount -= value;
					} else {
						break;
					}
				}
				amountBuying--;

			}

		}

		if (!customShop) {
			if (usePouch) {
				player.getPacketSender().sendString(8135, "" + player.getMoneyInPouch());
			}
		} else {
			player.getPointsManager().refreshPanel();
		}

		player.getInventory().refreshItems();
		fireRestockTask();
		refreshItems();
		publicRefresh();
		return this;
	}
}
