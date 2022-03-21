package com.ruthlessps;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.ruthlessps.util.Stopwatch;

public class CharacterBackup {
	private static final int TIME = 28800000; //8 hours
	public static Stopwatch timer = new Stopwatch().reset();
	public CharacterBackup() {
		
		File f1 = new File("./data/saves/characters/");
		File f2 = new File("./data/saves/Backup "
				+ getDate() + ".zip");
		if (!f2.exists()) {
			try {
				System.out
						.println("[Auto-Backup] The mainsave has been automatically backed up.");
				zipDirectory(f1, f2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out
					.println("[Auto-Backup] The mainsave has already been backed up today. Backup aborted.");
		}
	}
	
	public static void sequence() {
		if(timer.elapsed(TIME)) {
			timer.reset();
			{
				File f1 = new File("./data/saves/characters/");
				File f2 = new File("./data/saves/Backup "
						+ getDate() + ".zip");
				if (!f2.exists()) {
				try {
					System.out
							.println("[BACKUP] Characters successfully backed up.");
					zipDirectory(f1, f2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out
						.println("[BACKUP] Characters already backed up, backup canceled..");
			}
		}
	}
	}
	

	public static void zipDirectory(File f, File zf) throws IOException {
		ZipOutputStream z = new ZipOutputStream(new FileOutputStream(zf));
		zip(f, f, z);
		z.close();
	}

	private static void zip(File directory, File base, ZipOutputStream zos)
			throws IOException {
		File[] files = directory.listFiles();
		byte[] buffer = new byte[8192];
		int read;
		for (int i = 0, n = Objects.requireNonNull(files).length; i < n; i++) {
			if (files[i].isDirectory()) {
				zip(files[i], base, zos);
			} else {
				FileInputStream in = new FileInputStream(files[i]);
				ZipEntry entry = new ZipEntry(files[i].getPath().substring(
						base.getPath().length() + 1));
				zos.putNextEntry(entry);
				while (-1 != (read = in.read(buffer))) {
					zos.write(buffer, 0, read);
				}
				in.close();
			}
		}
	}

	public static String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("MM dd yyyy");
		Date date = new Date();
		return dateFormat.format(date);
		
	}
}