package com.ruthlessps.world.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ruthlessps.engine.task.Task;
import com.ruthlessps.engine.task.TaskManager;
import com.ruthlessps.world.entity.impl.player.Player;

public class Polls {
	private int chosen = 0;
	static List<String> list1 = new ArrayList<String>();
	static List<String> list2 = new ArrayList<String>();
	static List<String> list3 = new ArrayList<String>();
	static List<String> list4 = new ArrayList<String>();
	public static int votedyes1, votedyes2, votedyes3, votedyes4;
	public static int votedno1, votedno2, votedno3, votedno4;


	public Polls(Player player) {
	}
	
	public static final File DATADIR = new File("./data/polls/");
	
	
	public static String getYesPrecent(Player player, int pollId) throws IOException {
		File pollFile = new File("./data/poll data "+pollId+".txt");
		int precent = 0;
		if(!pollFile.exists()) {
			return "";
		}
		List<String> list = list1;
		switch(pollId) {
		case 1:
			list = list1;
			if(votedyes1+votedno1 == 0) {
				precent = 0;
				break;
			}
			precent = (votedyes1/(votedyes1+votedno1))*100;
			break;
		case 2:
			list = list2;
			if(votedyes2+votedno2 == 0) {
				precent = 0;
				break;
			}
			precent = (votedyes2/(votedyes2+votedno2))*100;
			break;
		case 3:
			list = list3;
			if(votedyes3+votedno3 == 0) {
				precent = 0;
				break;
			}
			precent = (votedyes3/(votedyes3+votedno3))*100;
			break;
		case 4:
			list = list4;
			if(votedyes4+votedno4 == 0) {
				precent = 0;
				break;
			}
			precent = (votedyes4/(votedyes4+votedno4))*100;
			break;
		}
		return "Yes: "+Integer.toString(precent)+"%";
		/*
		BufferedReader br = new BufferedReader(new FileReader(pollFile));
	    String line="";
	    String yes = "";
	    String no = "";
		if(player.getPolls().pollActive(pollId) && list.contains(player.getSerialNumber())) {
		    while((line=br.readLine())!=null){
		        if(line.startsWith("Yes")){
		            yes = line.substring(13, line.length());
		        }
		    }
		}
		return "Yes: "+yes;*/
	}
	
	public static String getNoPrecent(Player player, int pollId) throws IOException {
		File pollFile = new File("./data/poll data "+pollId+".txt");
		int precent = 0;
		if(!pollFile.exists()) {
			return "";
		}
		List<String> list = list1;
		switch(pollId) {
		case 1:
			list = list1;
			if(votedyes1+votedno1 == 0) {
				precent = 0;
				break;
			}
			precent = (votedno1/(votedyes1+votedno1))*100;
			break;
		case 2:
			list = list2;
			if(votedyes2+votedno2 == 0) {
				precent = 0;
				break;
			}
			precent = (votedno2/(votedyes2+votedno2))*100;
			break;
		case 3:
			list = list3;
			if(votedyes3+votedno3 == 0) {
				precent = 0;
				break;
			}
			precent = (votedno3/(votedyes3+votedno3))*100;
			break;
		case 4:
			list = list4;
			if(votedyes4+votedno4 == 0) {
				precent = 0;
				break;
			}
			precent = (votedno4/(votedyes4+votedno4))*100;
			break;
		}
		return "No: "+Integer.toString(precent)+"%";
		/*@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(pollFile));
	    String line="";
	    String no = "";
		if(player.getPolls().pollActive(pollId) && list.contains(player.getSerialNumber())) {
		    while((line=br.readLine())!=null){
		        if(line.startsWith("No")) {
		        	no = line.substring(12, line.length());
		        }
		    }
		}
		return "No: "+no;*/
	}
	
	public static void showPolls(Player player, int id) throws IOException {
		clearPolls(player);
		int pollsAmount = new File("./data/polls/").list().length;
		int j = 0;
		switch(id) {
		case 59000:
			j = 59067;
			break;
		case 59500:
			j = 59565;
			break;
		case 59800:
			j = 59865;
			break;
		case 61200:
			j = 61265;
			break;
		case 61500:
			j = 61565;
			break;
		}
		for(int i = 1; i <= pollsAmount; i++) {
			File file = new File(DATADIR+File.separator+Integer.toString(i)+".txt");
			if(!file.exists()) {
				continue;
			}
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st; 
			int s = 0;
			while ((st = br.readLine()) != null) {
				player.getPacketSender().sendString(j, st);
				j++;
				s++;
			}
			j+=4-s;
			br.close();
		}
		player.getPacketSender().sendString(59059, getYesPrecent(player, 1));
		player.getPacketSender().sendString(59061, getYesPrecent(player, 2));
		player.getPacketSender().sendString(59063, getYesPrecent(player, 3));
		player.getPacketSender().sendString(59065, getYesPrecent(player, 4));
		player.getPacketSender().sendString(59060, getNoPrecent(player, 1));
		player.getPacketSender().sendString(59062, getNoPrecent(player, 2));
		player.getPacketSender().sendString(59064, getNoPrecent(player, 3));
		player.getPacketSender().sendString(59066, getNoPrecent(player, 4));
		
		player.getPacketSender().sendString(59557, getYesPrecent(player, 1));
		player.getPacketSender().sendString(59559, getYesPrecent(player, 2));
		player.getPacketSender().sendString(59561, getYesPrecent(player, 3));
		player.getPacketSender().sendString(59563, getYesPrecent(player, 4));
		player.getPacketSender().sendString(59558, getNoPrecent(player, 1));
		player.getPacketSender().sendString(59560, getNoPrecent(player, 2));
		player.getPacketSender().sendString(59562, getNoPrecent(player, 3));
		player.getPacketSender().sendString(59564, getNoPrecent(player, 4));
		
		player.getPacketSender().sendString(59857, getYesPrecent(player, 1));
		player.getPacketSender().sendString(59859, getYesPrecent(player, 2));
		player.getPacketSender().sendString(59861, getYesPrecent(player, 3));
		player.getPacketSender().sendString(59863, getYesPrecent(player, 4));
		player.getPacketSender().sendString(59858, getNoPrecent(player, 1));
		player.getPacketSender().sendString(59860, getNoPrecent(player, 2));
		player.getPacketSender().sendString(59862, getNoPrecent(player, 3));
		player.getPacketSender().sendString(59864, getNoPrecent(player, 4));
		
		player.getPacketSender().sendString(61257, getYesPrecent(player, 1));
		player.getPacketSender().sendString(61259, getYesPrecent(player, 2));
		player.getPacketSender().sendString(61261, getYesPrecent(player, 3));
		player.getPacketSender().sendString(61263, getYesPrecent(player, 4));
		player.getPacketSender().sendString(61258, getNoPrecent(player, 1));
		player.getPacketSender().sendString(61260, getNoPrecent(player, 2));
		player.getPacketSender().sendString(61262, getNoPrecent(player, 3));
		player.getPacketSender().sendString(61264, getNoPrecent(player, 4));
		
		player.getPacketSender().sendString(61557, getYesPrecent(player, 1));
		player.getPacketSender().sendString(61559, getYesPrecent(player, 2));
		player.getPacketSender().sendString(61561, getYesPrecent(player, 3));
		player.getPacketSender().sendString(61563, getYesPrecent(player, 4));
		player.getPacketSender().sendString(61558, getNoPrecent(player, 1));
		player.getPacketSender().sendString(61560, getNoPrecent(player, 2));
		player.getPacketSender().sendString(61562, getNoPrecent(player, 3));
		player.getPacketSender().sendString(61564, getNoPrecent(player, 4));
		player.getPacketSender().sendInterface(id);
	}



	private static void clearPolls(Player player) {

		for(int j = 59067; j<=59083; j++ ) {
			player.getPacketSender().sendString(j, " ");
		}
		for(int j = 59567; j<=59583; j++ ) {
			player.getPacketSender().sendString(j, " ");
		}
		for(int j = 59867; j<=59883; j++ ) {
			player.getPacketSender().sendString(j, " ");
		}
		for(int j = 61267; j<=61283; j++ ) {
			player.getPacketSender().sendString(j, " ");
		}
		for(int j = 61567; j<=61583; j++ ) {
			player.getPacketSender().sendString(j, " ");
		}
		
	}
	
	public void voteYes(Player player, int pollId) throws IOException {
		File pollFile = new File("./data/polls/"+pollId+".txt");
		if(!pollFile.exists()) {
			return;
		}
		if(player.getPolls().getChosen() == 0) {
			player.getPacketSender().sendMessage("Please Choose a poll to vote for!");
			return;
		}
		switch(pollId) {
		case 1:
			if(list1.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list1.add(player.getSerialNumber());
			votedyes1+=1;
			player.getPacketSender().sendMessage("You voted Yes on this poll.");
			break;
		case 2:
			if(list2.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list2.add(player.getSerialNumber());
			votedyes2+=1;
			player.getPacketSender().sendMessage("You voted Yes on this poll.");
			break;
		case 3:
			if(list3.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list3.add(player.getSerialNumber());
			votedyes3+=1;
			player.getPacketSender().sendMessage("You voted Yes on this poll.");
			break;
		case 4:
			if(list4.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list4.add(player.getSerialNumber());
			votedyes4+=1;
			player.getPacketSender().sendMessage("You voted Yes on this poll.");
			break;
		}
		
		
		
		
	}
	
	public boolean pollActive(int pollId) {
		File pollFile = new File("./data/polls/"+pollId+".txt");
		if(!pollFile.exists()) {
			return false;
		}
		return true;
	}
	
	public void voteNo(Player player, int pollId) {
		File pollFile = new File("./data/polls/"+pollId+".txt");
		if(!pollFile.exists()) {
			return;
		}
		if(getChosen() == 0) {
			player.getPacketSender().sendMessage("Please Choose a poll to vote for!");
			return;
		}
		switch(pollId) {
		case 1:
			if(list1.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list1.add(player.getSerialNumber());
			votedno1+=1;
			player.getPacketSender().sendMessage("You voted No on this poll.");
			break;
		case 2:
			if(list2.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list2.add(player.getSerialNumber());
			votedno2+=1;	
			player.getPacketSender().sendMessage("You voted No on this poll.");
			break;
		case 3:
			if(list3.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list3.add(player.getSerialNumber());
			votedno3+=1;
			player.getPacketSender().sendMessage("You voted No on this poll.");
			break;
		case 4:
			if(list4.contains(player.getSerialNumber())) {
				player.getPacketSender().sendMessage("You have already voted for this poll!");
				return;
			}
			list4.add(player.getSerialNumber());
			votedno4+=1;
			player.getPacketSender().sendMessage("You voted No on this poll.");
			break;
		}
		
		
		
		
	}
	
	public static void saveData(int pollId) {
		List<String> list = new ArrayList<String>();
		Path path = Paths.get("./data/", "poll data "+pollId + ".txt");
		File file = path.toFile();
		file.getParentFile().setWritable(true);
		int votedyes = 0;
		int votedno = 0;
		switch(pollId){
		case 1:
			votedyes = votedyes1;
			votedno = votedno1;
			list = list1;
			break;
		case 2:
			votedyes = votedyes2;
			votedno = votedno2;
			list = list2;
			break;
			
		case 3:
			votedyes = votedyes3;
			votedno = votedno3;
			list = list3;
			break;
			
		case 4:
			votedyes = votedyes4;
			votedno = votedno4;
			list = list4;
			break;
			
		}
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(file));
			br.write("Poll "+pollId+" Results:");
			br.newLine();
			br.write("Total votes: "+(votedyes+votedno));
			br.newLine();
			br.write("Voted Yes: "+votedyes);
			br.newLine();
			br.write("Voted No: "+votedno);
			br.newLine();
			if(votedyes+votedno == 0) {
				br.write("Yes precent: 0%");
				br.newLine();
				br.write("No precent: 0%");
			} else {
				br.write("Yes precent: "+(votedyes/(votedyes+votedno))*100+"%");
				br.newLine();
				br.write("No precent: "+(votedno/(votedyes+votedno))*100+"%");
			}
			br.newLine();
			br.write("Voted players: ");
			br.newLine();
			br.write(".;");
			for(String sr : list) {
				br.write(sr+";");
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void init() throws IOException {
		BufferedReader br;
		for(int i = 1; i<5; i++) {
			Path path = Paths.get("./data/", "poll data "+i + ".txt");
			File file = path.toFile();
			file.createNewFile();
			br = new BufferedReader(new FileReader(file));
			String ln;
			switch(i) {
			case 1:
				while((ln = br.readLine()) != null) {
					if(ln.startsWith("Voted Yes")) {
						votedyes1 = Integer.parseInt(ln.substring(11, ln.length()));
					} else if(ln.startsWith("Voted No")) {
						votedno1 = Integer.parseInt(ln.substring(10, ln.length()));
					} else if(ln.startsWith("Voted players")) {
						ln = br.readLine();
						list1 = new ArrayList<String>(Arrays.asList(ln.split(";")));;
					}
				}
				break;
			case 2:
				while((ln = br.readLine()) != null) {
					if(ln.startsWith("Voted Yes")) {
						votedyes2 = Integer.parseInt(ln.substring(11, ln.length()));
					} else if(ln.startsWith("Voted No")) {
						votedno2 = Integer.parseInt(ln.substring(10, ln.length()));
					} else if(ln.startsWith("Voted players")) {
						ln = br.readLine();
						list2 = new ArrayList<String>(Arrays.asList(ln.split(";")));;
					}
				}
				break;
			case 3:
				while((ln = br.readLine()) != null) {
					if(ln.startsWith("Voted Yes")) {
						votedyes3 = Integer.parseInt(ln.substring(11, ln.length()));
					} else if(ln.startsWith("Voted No")) {
						votedno3 = Integer.parseInt(ln.substring(10, ln.length()));
					} else if(ln.startsWith("Voted players")) {
						ln = br.readLine();
						list3 = new ArrayList<String>(Arrays.asList(ln.split(";")));
					}
				}
				break;
			case 4:
				while((ln = br.readLine()) != null) {
					if(ln.startsWith("Voted Yes")) {
						votedyes4 = Integer.parseInt(ln.substring(11, ln.length()));
					} else if(ln.startsWith("Voted No")) {
						votedno4 = Integer.parseInt(ln.substring(10, ln.length()));
					} else if(ln.startsWith("Voted players")) {
						ln = br.readLine();
						list4 = new ArrayList<String>(Arrays.asList(ln.split(";")));;
					}
				}
				break;
			}
		}
		
	}


	public int getChosen() {
		return chosen;
	}
	public void setChosen(int i) {
		this.chosen = i;
		
	}
	
	
	
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
