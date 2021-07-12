package com.ruthlessps.world.content;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Censor {

	public static ArrayList<String> censored = new ArrayList<String>();

	public static boolean contains(String string) {
		for (String s : censored) {
			if (string.contains(s)) {
				return true;
			}
		}
		return false;
	}

	public static void init() {
		String word = null;
		try {
			BufferedReader in = new BufferedReader(new FileReader("./data/def/txt/censor.txt"));
			while ((word = in.readLine()) != null) {
				censored.add(word);
			}
			in.close();
			in = null;
		} catch (Exception e) {
		}
	}
}
