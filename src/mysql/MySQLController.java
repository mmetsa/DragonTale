package mysql;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Gabriel Hannason
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MySQLController {

	public enum Database {
		RECOVERY,
	};

	private static class MySQLProcessor {

		private static boolean running;

		public static void process() {
			if (running) {
				return;
			}
			running = true;
			SQL_SERVICE.submit(new Runnable() {
				@Override
				public void run() {
					try {
						while (running) {
							for (MySQLDatabase database : DATABASES) {

								if (!database.active) {
									continue;
								}

								if (database.connectionAttempts >= 5) {
									database.active = false;
								}

								Connection connection = database.getConnection();
								try {
									connection.createStatement().execute("/* ping */ SELECT 1");
								} catch (Exception e) {
									database.createConnection();
								}
							}
							Thread.sleep(25000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		private static void terminate() {
			running = false;
		}
	}

	public static final ExecutorService SQL_SERVICE = Executors.newSingleThreadExecutor();
	private static MySQLController CONTROLLER;
	// private static Store STORE = new Store();

	//private static Motivote VOTE;

	private static MySQLDatabase[] DATABASES = new MySQLDatabase[2];

	public static MySQLController getController() {
		return CONTROLLER;
	}

	// public static Store getStore() {
	// return STORE;
	// }

	/* NON STATIC CLASS START */

	public static void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		CONTROLLER = new MySQLController();
	}

	public MySQLController() {
		/* DATABASES */
		DATABASES = new MySQLDatabase[] { // u dont have auto donation? its everythingrs lolol %10 fee gg, i know... dont have better, kingfox sux kingfox v2 is good
				new MySQLDatabase("123.com", 3306, "123", "123", "{}123"), };

		/* VOTING */
		// VOTE = new Motivote(new Voting(), "http://ruse-ps.com/vote/", "55f65af57");
		// VOTE.start();

		/*
		 * Start the process
		 */
	//	MySQLProcessor.process();
	}

	public MySQLDatabase getDatabase(Database database) {
		return DATABASES[database.ordinal()];
	}
}
