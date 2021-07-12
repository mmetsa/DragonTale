package com.ruthlessps;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Objects;

/**
 * 
 * @author Archie
 *
 */
public class EcoReset {
	
	public static File charDir = new File("data/saves/characters/");
	public static void main(String[] args) {
        if(charDir.exists() && charDir.isDirectory()) {
        	File[] charFiles = charDir.listFiles();
			for (File charFile : Objects.requireNonNull(charFiles)) {
				resetEcoChar(charFile);
				System.out.println("Reset the Player Economy for: " + charFile.toString());
			}
        }
	}
		@SuppressWarnings("deprecation")
		public static void resetEcoChar(File charFile) {
			try {
                String tempData, tempAdd;
				File tempCharFile = new File(charDir.toString()+"CLEAN_ECO$TEMPORARY");
				DataInputStream fileStream = new DataInputStream(new FileInputStream(charFile));
				BufferedWriter tempOut = new BufferedWriter(new FileWriter(tempCharFile));
				while((tempData = fileStream.readLine()) != null)
                {
                	if(tempData.trim().startsWith("\"shop-earnings\":")) {
						tempAdd = "\"shop-earnings\": "+"0,";
                        tempOut.write(tempAdd); tempOut.newLine();
					} else if (tempData.trim().startsWith("\"id\": 13664")) {
						tempAdd = "\"id\": "+"8851,";
						tempOut.write(tempAdd); tempOut.newLine();
					} else {
						tempOut.write(tempData); tempOut.newLine();
					}
                }
				fileStream.close(); tempOut.close();
				charFile.delete();
				tempCharFile.renameTo(charFile);
                        }
                catch(Exception e) { e.printStackTrace(); 
                }
        }
	}
