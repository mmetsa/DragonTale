package com.ruthlessps.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.NumberFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import org.jboss.netty.buffer.ChannelBuffer;

import com.ruthlessps.model.Item;
import com.ruthlessps.model.Position;
import com.ruthlessps.world.content.combat.CombatContainer.CombatHit;
import com.ruthlessps.world.entity.impl.player.Player;

public class Misc {
	public static final String sendCashToString(long j) {
		if (j >= 0 && j < 10000)
			return String.valueOf(j);
		else if (j >= 10000 && j < 10000000)
			return j / 1000 + "K";
		else if (j >= 10000000 && j < 999999999)
			return j / 1000000 + "M";
		else
			return Long.toString(j);
	}

	/** Random instance, used to generate pseudo-random primitive types. */
	public static final Random RANDOM = new Random(System.currentTimeMillis());

	public static final int HALF_A_DAY_IN_MILLIS = 43200000;

	public static byte[] directionDeltaX = new byte[]{0, 1, 1, 1, 0, -1, -1, -1};

	public static byte[] directionDeltaY = new byte[]{1, 1, 0, -1, -1, -1, 0, 1};

	public static byte[] xlateDirectionToClient = new byte[]{1, 2, 4, 7, 6, 5, 3, 0};

	private static char[] decodeBuf = new char[4096];

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	private static char[] xlateTable = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c',
			'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '\243', '$',
			'%', '"', '[', ']'};

	private static final String[] BLOCKED_WORDS = {
			"devious", "firepk", "ventrilica", "ventrili",
			"hade5", "hades5", "pvplegacy", "junglepk", "runeinsanit",
			"n3t", "n e t", "n et", "ne t", "c  0 m", "c 0 m", "C 0  m",
			"c  o  m", "runeinsanity", "runeinsanit", "runeinsan",
			"rune-insanity", "rune-insanit", ".c0", ".c0m",
			"legendsdomain", "createaforum", "vampirez", "-scape",  "os-base",
			"rswebclients", "pvplegacy", "junglepk", ".n3t", ".c0m", "c0m",
			"n3t", ".tk", ",net", ",runescape", "pvpmaster", "pvpmasters",
			"PvPMasters.", ",org", ",com", ",be", ",nl", ",info", "dspk",
			".info", ".org", ".tk", ".net", ".com", ".co.uk", ".be", ".nl",
			".dk", ".co.cz", ".co", ".us", ".biz", ".eu", ".de", ".cc", ". n e  t",
			".i n f o", ".o r g", ".t k", ".n e t", ".c o m", ".c o . u k",
			".b e", ".n l", ".d k", ".c o . c z", ".c o", ".u s", ".b i z",
			".e u", ".d e", ".c c", ".(c)om", "(.)", "kandarin", "o r g", "www", 
			"w w w", "w.w.w", "voidrsps", "void-ps", "desolace", "ikov", "soulsplit", 
			"soulspawn", "atiloc", "<img", "@cr", "i k o v", "ik0v", "<img=", "@cr", ":tradereq:", ":duelreq:",
			"<col=", "<shad=", "hostilityps", "hostilityp", "hostility ps"
			, "0s-base", "os -base", "os - base", "o s b a s e", "o s - b a s e" };


		public static boolean blockedWord(String string) {
			for(String s : BLOCKED_WORDS) {
				if(string.contains(s)) {
					return true;
				}
			}
			return false;
		}
		
		public static String filterMessage(Player player, String message) {
			for (String auto : BLOCKED_WORDS) {
				if (message.toLowerCase().contains(auto) && !player.getRights().isStaff()) {
					message = message.replaceAll(auto, "*".repeat(auto.length()));
				}
			}
			return message;
		}
		
		public static String anOrA(String s) {
		s = s.toLowerCase();
		if (s.equalsIgnoreCase("anchovies") || s.equalsIgnoreCase("soft clay") || s.equalsIgnoreCase("cheese")
				|| s.equalsIgnoreCase("ball of wool") || s.equalsIgnoreCase("spice")
				|| s.equalsIgnoreCase("steel nails") || s.equalsIgnoreCase("snape grass") || s.equalsIgnoreCase("coal"))
			return "some";
		if (s.startsWith("a") || s.startsWith("e") || s.startsWith("i") || s.startsWith("o") || s.startsWith("u"))
			return "an";
		return "a";
	}


	public static boolean isSuccessful(double value) {
		return ((Math.random() * 1000) < value);
	}

	public static CombatHit[] concat(CombatHit[] a, CombatHit[] b) {
		int aLen = a.length;
		int bLen = b.length;
		CombatHit[] c = new CombatHit[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	public static Item[] concat(Item[] a, Item[] b) {
		int aLen = a.length;
		int bLen = b.length;
		Item[] c = new Item[aLen + bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}

	public static Position delta(Position a, Position b) {
		return new Position(b.getX() - a.getX(), b.getY() - a.getY());
	}

	public static int direction(int srcX, int srcY, int x, int y) {
		double dx = (double) x - srcX, dy = (double) y - srcY;
		double angle = Math.atan(dy / dx);
		angle = Math.toDegrees(angle);
		if (Double.isNaN(angle))
			return -1;
		if (Math.signum(dx) < 0)
			angle += 180.0;
		return (int) ((((90 - angle) / 22.5) + 16) % 16);
		/*
		 * int changeX = x - srcX; int changeY = y - srcY; for (int j = 0; j <
		 * directionDeltaX.length; j++) { if (changeX == directionDeltaX[j] && changeY
		 * == directionDeltaY[j]) return j;
		 * 
		 * } return -1;
		 */
	}

	/**
	 * Returns a pseudo-random {@code int} value between inclusive <tt>0</tt> and
	 * exclusive <code>range</code>.
	 * 
	 * <br>
	 * <br>
	 * This method is thread-safe. </br>
	 * 
	 * @param range
	 *            The exclusive range.
	 * @return The pseudo-random {@code int}.
	 * @throws IllegalArgumentException
	 *             If the specified range is less <tt>0</tt>
	 */
	public static int exclusiveRandom(int range) {
		return exclusiveRandom(0, range);
	}

	/**
	 * Returns a pseudo-random {@code int} value between inclusive <code>min</code>
	 * and exclusive <code>max</code>.
	 * 
	 * <br>
	 * <br>
	 * This method is thread-safe. </br>
	 * 
	 * @param min
	 *            The minimum inclusive number.
	 * @param max
	 *            The maximum exclusive number.
	 * @return The pseudo-random {@code int}.
	 * @throws IllegalArgumentException
	 *             If the specified range is less <tt>0</tt>
	 */
	public static int exclusiveRandom(int min, int max) {
		if (max <= min) {
			max = min + 1;
		}
		return RANDOM.nextInt((max - min)) + min;
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static String format(int num) {
		return NumberFormat.getInstance().format(num);
	}

	public static String formatPlayerName(String str) {
		String str1 = Misc.ucFirst(str);
		str1.replace("_", " ");
		return str1;
	}

	public static String formatText(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (i == 0) {
				s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
			}
			if (!Character.isLetterOrDigit(s.charAt(i))) {
				if (i + 1 < s.length()) {
					s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)),
							s.substring(i + 2));
				}
			}
		}
		return s.replace("_", " ");
	}

	public static byte[] getBuffer(File f) throws Exception {
		if (!f.exists())
			return null;
		byte[] buffer = new byte[(int) f.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(f));
		dis.readFully(buffer);
		dis.close();
		byte[] gzipInputBuffer = new byte[999999];
		int bufferlength = 0;
		GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(buffer));
		do {
			if (bufferlength == gzipInputBuffer.length) {
				System.out.println("Error inflating data.\nGZIP buffer overflow.");
				break;
			}
			int readByte = gzip.read(gzipInputBuffer, bufferlength, gzipInputBuffer.length - bufferlength);
			if (readByte == -1)
				break;
			bufferlength += readByte;
		} while (true);
		byte[] inflated = new byte[bufferlength];
		System.arraycopy(gzipInputBuffer, 0, inflated, 0, bufferlength);
		buffer = inflated;
		if (buffer.length < 10)
			return null;
		return buffer;
	}

	public static Player getCloseRandomPlayer(List<Player> plrs) {
		int index = Misc.getRandom(plrs.size() - 1);
		if (index > 0)
			return plrs.get(index);
		return null;
	}

	public static List<Player> getCombinedPlayerList(Player p) {
		List<Player> plrs = new LinkedList<Player>();
		for (Player localPlayer : p.getLocalPlayers()) {
			if (localPlayer != null)
				plrs.add(localPlayer);
		}
		plrs.add(p);
		return plrs;
	}

	public static String getCurrentServerTime() {
		ZonedDateTime zonedDateTime = ZonedDateTime.now();
		int hour = zonedDateTime.getHour();
		String hourPrefix = hour < 10 ? "0" + hour + "" : "" + hour + "";
		int minute = zonedDateTime.getMinute();
		String minutePrefix = minute < 10 ? "0" + minute + "" : "" + minute + "";
		return "" + hourPrefix + ":" + minutePrefix + "";
	}

	public static int getDayOfYear() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int days = 0;
		int[] daysOfTheMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
			daysOfTheMonth[1] = 29;
		}
		days += c.get(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < daysOfTheMonth.length; i++) {
			if (i < month) {
				days += daysOfTheMonth[i];
			}
		}
		return days;
	}

	public static int getElapsed(int day, int year) {
		if (year < 2013) {
			return 0;
		}

		int elapsed = 0;
		int currentYear = Misc.getYear();
		int currentDay = Misc.getDayOfYear();

		if (currentYear == year) {
			elapsed = currentDay - day;
		} else {
			elapsed = currentDay;

			for (int i = 1; i < 5; i++) {
				if (currentYear - i == year) {
					elapsed += 365 - day;
					break;
				} else {
					elapsed += 365;
				}
			}
		}

		return elapsed;
	}

	public static String getHoursPlayed(long totalPlayTime) {
		final int sec = (int) (totalPlayTime / 1000), h = sec / 3600;
		return (h < 10 ? "0" + h : h) + "h";
	}

	public static int getMinutesElapsed(int minute, int hour, int day, int year) {
		Calendar i = Calendar.getInstance();

		if (i.get(Calendar.YEAR) == year) {
			if (i.get(Calendar.DAY_OF_YEAR) == day) {
				if (hour == i.get(Calendar.HOUR_OF_DAY)) {
					return i.get(Calendar.MINUTE) - minute;
				}
				return (i.get(Calendar.HOUR_OF_DAY) - hour) * 60 + (59 - i.get(12));
			}

			int ela = (i.get(Calendar.DAY_OF_YEAR) - day) * 24 * 60 * 60;
			return ela;
		}

		int ela = getElapsed(day, year) * 24 * 60 * 60;

		return ela;
	}

	public static int getMinutesPassed(long t) {
		int seconds = (int) ((t / 1000) % 60);
		return (int) (((t - seconds) / 1000) / 60);
	}

	public static int getMinutesPlayed(Player p) {
		long totalPlayTime = p.getTotalPlayTime() + (p.getRecordedLogin().elapsed());
		int sec = (int) (totalPlayTime / 1000), h = sec / 3600, m = sec / 60 % 60;
		for (int i = 0; i < h; i++)
			m += 60;
		return m;
	}

	public static int getRandom(int range) {
		return (int) (java.lang.Math.random() * (range + 1));
	}

	public static int getTimeLeft(long start, int timeAmount, TimeUnit timeUnit) {
		start -= timeUnit.toMillis(timeAmount);
		long elapsed = System.currentTimeMillis() - start;
		int toReturn = timeUnit == TimeUnit.SECONDS ? (int) ((elapsed / 1000) % 60) - timeAmount
				: (int) ((elapsed / 1000) / 60) - timeAmount;
		if (toReturn <= 0)
			toReturn = 1;
		return timeAmount - toReturn;
	}

	public static String getTimePlayed(long totalPlayTime) {
		final int sec = (int) (totalPlayTime / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
		return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
	}

	public static String getTotalAmount(int j) {
		if (j >= 10000 && j < 10000000) {
			return j / 1000 + "K";
		} else if (j >= 10000000) {
			return j / 1000000 + "M";
		} else {
			return "" + j + " coins";
		}
	}

	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}

	/**
	 * Converts an array of bytes to an integer.
	 * 
	 * @param data
	 *            the array of bytes.
	 * @return the newly constructed integer.
	 */
	public static int hexToInt(byte[] data) {
		int value = 0;
		int n = 1000;
		for (int i = 0; i < data.length; i++) {
			int num = (data[i] & 0xFF) * n;
			value += num;
			if (n > 1) {
				n = n / 1000;
			}
		}
		return value;
	}

	/**
	 * Returns a pseudo-random {@code int} value between inclusive <tt>0</tt> and
	 * inclusive <code>range</code>.
	 * 
	 * @param range
	 *            The maximum inclusive number.
	 * @return The pseudo-random {@code int}.
	 * @throws IllegalArgumentException
	 *             If {@code max - min + 1} is less than <tt>0</tt>.
	 * @see {@link #exclusiveRandom(int)}.
	 */
	public static int inclusiveRandom(int range) {
		return inclusiveRandom(0, range);
	}

	/**
	 * Returns a pseudo-random {@code int} value between inclusive <code>min</code>
	 * and inclusive <code>max</code>.
	 * 
	 * @param min
	 *            The minimum inclusive number.
	 * @param max
	 *            The maximum inclusive number.
	 * @return The pseudo-random {@code int}.
	 * @throws IllegalArgumentException
	 *             If {@code max - min + 1} is less than <tt>0</tt>.
	 * @see {@link #exclusiveRandom(int)}.
	 */
	public static int inclusiveRandom(int min, int max) {
		if (max < min) {
			max = min + 1;
		}
		return exclusiveRandom((max - min) + 1) + min;
	}

	public static String insertCommasToNumber(String number) {
		return number.length() < 4 ? number
				: insertCommasToNumber(number.substring(0, number.length() - 3)) + ","
						+ number.substring(number.length() - 3);
	}

	public static boolean isWeekend() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		return (day == 1) || (day == 6) || (day == 7);
	}

	public static int random(int range) {
		if (range < 0)
			range = 0;
		return new Random().nextInt(range + 1);
	}

	/**
	 * Picks a random element out of any list type.
	 * 
	 * @param list
	 *            the list to pick the element from.
	 * @return the element chosen.
	 */
	public static <T> T randomElement(List<T> list) {
		return list.get((int) (RANDOM.nextDouble() * list.size()));
	}

	/**
	 * Picks a random element out of any array type.
	 * 
	 * @param array
	 *            the array to pick the element from.
	 * @return the element chosen.
	 */
	public static <T> T randomElement(T[] array) {
		return array[(int) (RANDOM.nextDouble() * array.length)];
	}

	public static int randomMinusOne(int length) {
		return getRandom(length - 1);
	}

	public static byte[] readFile(File s) {
		try {
			FileInputStream fis = new FileInputStream(s);
			FileChannel fc = fis.getChannel();
			ByteBuffer buf = ByteBuffer.allocate((int) fc.size());
			fc.read(buf);
			buf.flip();
			fis.close();
			return buf.array();
		} catch (Exception e) {
			System.out.println("FILE : " + s.getName() + " missing.");
			return null;
		}
	}

	/**
	 * Reads string from a data input stream.
	 * 
	 * @param inputStream
	 *            The input stream to read string from.
	 * @return The String value.
	 */
	public static String readString(ChannelBuffer buffer) {
		StringBuilder builder = null;
		try {
			byte data;
			builder = new StringBuilder();
			while ((data = buffer.readByte()) != 10) {
				builder.append((char) data);
			}
		} catch (IndexOutOfBoundsException e) {

		}
		return builder.toString();
	}

	public static String removeSpaces(String s) {
		return s.replace(" ", "");
	}

	public static String textUnpack(byte packedData[], int size) {
		int idx = 0, highNibble = -1;
		for (int i = 0; i < size * 2; i++) {
			int val = packedData[i / 2] >> (4 - 4 * (i % 2)) & 0xf;
			if (highNibble == -1) {
				if (val < 13)
					decodeBuf[idx++] = xlateTable[val];
				else
					highNibble = val;
			} else {
				decodeBuf[idx++] = xlateTable[((highNibble << 4) + val) - 195];
				highNibble = -1;
			}
		}

		return new String(decodeBuf, 0, idx);
	}

	public static String ucFirst(String str) {
		str = str.toLowerCase();
		if (str.length() > 1) {
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		} else {
			return str.toUpperCase();
		}
		return str;
	}
}