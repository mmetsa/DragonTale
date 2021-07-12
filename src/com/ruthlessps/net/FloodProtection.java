/*package com.ruthlessps.net;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Andre with SiteSpace.io
 * This will log all incoming connections to see if its flooding and if its interfering with the game server
 *
 
public class FloodProtection {
	
	private static final String FILE_LOCATION = System.getProperty("user.home").replace('\\', '/') + "/connectionLog.txt";
	
	private static Map<String, Integer> activeConnections = new HashMap<String, Integer>();
	
	private static FileWriter write = null;
	
	public static void add(String ip) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		if (activeConnections.containsKey(ip)) {
			int activeAmt = activeConnections.get(ip);
			activeConnections.put(ip, activeAmt + 1);
			if (activeAmt >= 5) {
				write(dateFormat.format(date) + " - EXCESSIVE CONNECTIONS COMING FROM IP: " + ip + ", Amount: " + activeAmt);
			} else
				write(dateFormat.format(date) + " - Connection added to existing: " + ip + ", Total connections " + activeAmt);
		} else {
			activeConnections.put(ip, 1);
			write(dateFormat.format(date) + " - " + ip + " connection added");
		}
	}
	
	public static void remove(String ip) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		if (activeConnections.containsKey(ip)) {
			int oldAmt = activeConnections.get(ip);
			if ((oldAmt - 1) <= 0) {
				activeConnections.remove(ip);
				write(dateFormat.format(date) + " - REMOVED ALL SESSIONS FOR IP: " + ip);
			} else {
				activeConnections.put(ip, oldAmt - 1);
				write(dateFormat.format(date) + " - Removing one session from ip: " + ip + ", Total active sessions: " + (oldAmt - 1));
			}
		}
	}
	
	public static int getAmountOfConnections(String ip) {
		return activeConnections.get(ip) == null ? 0 : activeConnections.get(ip);
	}
	
	public static void write(String line) {
		try {
			write.write(line + System.lineSeparator());
			write.flush();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void init() {
		try {
			write = new FileWriter(FILE_LOCATION, true);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
*/