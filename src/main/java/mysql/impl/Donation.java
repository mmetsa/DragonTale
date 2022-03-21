package mysql.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ruthlessps.model.Item;
import com.ruthlessps.world.World;
import com.ruthlessps.world.entity.impl.player.Player;

/**
 * Using this class:
 * To call this class, it's best to make a new thread. You can do it below like so:
 * new Thread(new Donation(player)).start();
 */
public class Donation implements Runnable {

	private static final String HOST = "185.224.138.198"; // website ip address
	private static final String USER = "u257340178_archie";
	private static final String PASS = "TamsaluGymn1";
	private static final String DATABASE = "u257340178_storev2";

	private Player player;
	private Connection conn;
	private Statement stmt;

	/**
	 * The constructor
	 * @param player The player
	 */
	public Donation(Player player) {
		this.player = player;
	}

	@Override
	public void run() {
		try {
			if (!connect()) {
				return;
			}

			String name = player.getUsername().replace("_", " ");
			ResultSet rs = executeQuery("SELECT * FROM payments WHERE player_name='"+name+"' AND status='Completed' AND claimed=0");

			while (rs != null && rs.next()) {
				int item_number = rs.getInt("item_number");
				double paid = rs.getDouble("amount");
				int quantity = rs.getInt("quantity");
				player.incrementAmountDonated((int)paid * quantity);
				switch (item_number) {// add products according to their ID in the ACP

				case 18: // example
					player.getInventory().add(6199, 10 * quantity);
					World.sendDonationMessage(player, "10x Mystery Box");
					break;
				case 19: // example
					player.getInventory().add(15501, quantity);
					World.sendDonationMessage(player, "Mega Mystery Box");
					break;
				case 20: // example
					player.getInventory().add(2801, quantity);
					World.sendDonationMessage(player, "Torva Mystery Box");
					break;
				case 21: // example
					player.getInventory().add(2800, quantity);
					World.sendDonationMessage(player, "Pet Mystery Box");
					break;
				case 22: // example
					player.getInventory().add(6930, quantity);
					World.sendDonationMessage(player, "Donator Mystery Box");
					break;
				case 23: // example
					player.getInventory().add(916, quantity);
					World.sendDonationMessage(player, "Omega PVM Box");
					break;
				case 24: // example
					player.getInventory().add(917, quantity);
					World.sendDonationMessage(player, "Sharpy Box");
					break;
				case 25: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(3626),
							new Item(3627),
							new Item(3628),
							new Item(3629),
							new Item(3630),
							new Item(3631),
							new Item(3632)});
					World.sendDonationMessage(player, "Rainbow Set");
					break;
				case 26: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(20652), // Gloves
							new Item(1843), // Helmet
							new Item(1845), // Legs
							new Item(1846), // Boots
							new Item(1848), // Wings
							new Item(1849), // Sword
							new Item(1850)}); // Body
					World.sendDonationMessage(player, "Cryptic Set");
					break;
				case 27:
					player.getInventory().addItemSet(new Item[] {
							new Item(8675), // Sword
							new Item(18490), // Boots
							new Item(1840), // Helm
							new Item(1841), // Platebody
							new Item(1842), // Platelegs
							new Item(3298), // Wings
							new Item(2912)}); // Gloves
					World.sendDonationMessage(player, "Burst Set");
				case 28: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(8816), // Helm
							new Item(8817), // Platebody
							new Item(8818), // Platelegs
							new Item(8820), // Gloves
							new Item(8821), // Boots
							new Item(8822), // Cape
							new Item(3290)}); // Dagger
					World.sendDonationMessage(player, "Tyrant Set");
					break;
				case 29: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(17855), // Sword
							new Item(3820), // Gloves
							new Item(3821), // Boots
							new Item(3850), // Wings
							new Item(6450), // Helm
							new Item(6451), // Platebody
							new Item(6452)}); // Platelegs
					World.sendDonationMessage(player, "Ziva Set");
					break;
				case 30: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(3271), // Cowl
							new Item(3272), // Body
							new Item(3273), // Chaps
							new Item(3274), // Boots
							new Item(3275), // Vambs
							new Item(3276), // Crossbow
							new Item(6485)}); // Accumulator
					World.sendDonationMessage(player, "Vortex Set");
					break;
				case 31: // example
					player.getInventory().addItemSet(new Item[] {
							new Item(13253), // Teddy
							new Item(15658), // Boots
							new Item(15659), // Gloves
							new Item(16618), // Hood
							new Item(16619), // Robe top
							new Item(16620), // Rope bottom
							new Item(4741)}); // Staff
					World.sendDonationMessage(player, "Galars Set");
					break;
				case 32: // example
					player.getInventory().add(6803, quantity);
					player.incrementAmountDonated(75*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just Donated for "+quantity+" Donator points.");
					break;
				case 33: // example
					player.getInventory().add(6805, quantity);
					player.incrementAmountDonated(140*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just Donated for "+quantity+" Donator points.");
					break;
				case 34: // example
					player.getInventory().add(6807, quantity);
					player.incrementAmountDonated(350*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just Donated for "+quantity+" Donator points.");
					break;
				case 35: // example
				case 36:
					player.getInventory().add(19926, quantity);
					player.incrementAmountDonated(300*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just Donated for "+quantity+" A custom item.");
					break;
				case 37:
					player.getInventory().add(3626, quantity);
					player.incrementAmountDonated(25*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just Donated for "+quantity+" rainbow whip.");
					break;
				case 38:
					player.getInventory().add(10936, quantity);
					player.incrementAmountDonated(10*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought a 10$ Deal");
					break;
				case 39:
					player.getInventory().add(10944, quantity);
					player.incrementAmountDonated(50*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought a 50$ Deal");
					break;
				case 40:
					player.getInventory().add(12421, quantity);
					player.incrementAmountDonated(100*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought a 100$ Deal");
					break;
				case 41:
					player.getInventory().add(15356, quantity);
					player.incrementAmountDonated(200*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought a 200$ Deal");
					break;
				case 42:
					player.getInventory().add(916, quantity);
					player.incrementAmountDonated(10*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought an Omega PVM Box");
					break;
				case 43:
					player.getInventory().add(917, quantity);
					player.incrementAmountDonated(20*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought a Sharpy Box");
					break;
				case 44:
					player.getInventory().add(916, quantity);
					player.incrementAmountDonated(10*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought 10 Omega PVM Boxes");
					break;
				case 45:
					player.getInventory().add(917, quantity);
					player.incrementAmountDonated(20*quantity);
					World.sendMessage("<img=10><col=FF7400><shad=0>"+player.getUsername()+" has just bought 10 Sharpy Boxes");
					break;


				}
				player.saveData();
				rs.updateInt("claimed", 1); // do not delete otherwise they can reclaim!
				rs.updateRow();
			}

			destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @return true if connected
	 */
	private boolean connect() {
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://"+ Donation.HOST +":3306/"+ Donation.DATABASE, Donation.USER, Donation.PASS);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Failing connecting to database!");
			return false;
		}
	}

	/**
	 * Disconnects from the MySQL server and destroy the connection
	 * and statement instances
	 */
	private void destroy() {
        try {
    		conn.close();
        	conn = null;
        	if (stmt != null) {
    			stmt.close();
        		stmt = null;
        	}
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

	/**
	 * Executres a query on the database
	 * @param query - the query
	 * @return the results, never null
	 */
	private ResultSet executeQuery(String query) {
        try {
        	this.stmt = this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			return stmt.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
