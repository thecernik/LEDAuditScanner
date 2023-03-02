import java.io.File; //Imports the File Classes
import java.io.FileNotFoundException;
import java.io.IOException; //Imports the IOException class to handle errors
import java.util.Scanner; //Imports the Scanner class to read files

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.ArrayList;


public class LEDScanner {

	// Variables
	String panelNumber;
	int[] auditPanels;
	int[] imagPanels;
	static int success = 0;

	
	//Array lists of parsed audit lists
	private static ArrayList<String> mc7Audit = new ArrayList<String>();
	private static ArrayList<String> dm2Audit = new ArrayList<String>();
	private static ArrayList<String> bo2Audit = new ArrayList<String>();
	private static ArrayList<String> bp2Audit = new ArrayList<String>();

	//Paths of CSV files
	private static String mc7List = "mc7List.csv";
	private static String dm2List = "dm2List.csv";
	private static String bo2List = "bo2List.csv";
	private static String bp2List = "bp2List.csv";
	
	private static String bingPath = "bing.wav";
	private static String bongPath = "bong.wav";
	
	private static AudioPlayer bing;
	private static AudioPlayer bong;
	
	static String ID;
	static boolean searchActive = true;


	public static void main(String[] args) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {

		
		
		setAuditLists();
		setBingAudio();
		setBongAudio();
		System.out.println(mc7Audit);
		System.out.println(dm2Audit);
		System.out.println(bo2Audit);
		System.out.println(bp2Audit);
		//System.out.println(auditSearch("BP280M107596"));
		

		Scanner input = new Scanner(System.in);
		while(searchActive == true) {
			
			System.out.println("Please enter serial number or 'quit': ");
			String userIn = input.next();
			System.out.println(userIn);
			if(userIn.equalsIgnoreCase("End") || userIn.equalsIgnoreCase("quit")) {
				searchActive = false;
			}else {
			auditSearch(userIn);
			bing.stop();
			bong.stop();
		}
		}
		System.out.println("Program closing...");
		Thread.sleep(1000);
		input.close();
		
		
		
		

	}

	public static ArrayList<String> readFile(String filename) {

		ArrayList<String> toAudit = new ArrayList<String>();
		try {
			File auditFile = new File(filename);
			Scanner fileReader = new Scanner(auditFile);
			fileReader.useDelimiter(",");
			while (fileReader.hasNextLine()) {
				toAudit.add(fileReader.nextLine());
			}
			fileReader.close();
			System.out.println(filename + " successfully parsed.");
			success++;
			return toAudit;
		} catch (FileNotFoundException e) {
			System.out.println("An error occured reading the file.");
			e.printStackTrace();
			return null;
		}

	}

	public static void setAuditLists() throws InterruptedException {
		System.out.println("Reading CSV files...");
		Thread.sleep(250);
		mc7Audit = readFile(mc7List);
		Thread.sleep(100);
		dm2Audit = readFile(dm2List);
		dm2Audit.add("DM260M009329");
		Thread.sleep(100);
		bo2Audit = readFile(bo2List);
		Thread.sleep(100);
		bp2Audit = readFile(bp2List);
		Thread.sleep(100);
		System.out.println("Successfully parsed " + success + " out of 4 files.");
		Thread.sleep(100);
	}

	public static boolean auditSearch(String serialNumber) throws InterruptedException, UnsupportedAudioFileException, IOException, LineUnavailableException {
		ArrayList<String> auditList = new ArrayList<String>();

		if(serialNumber.length() != 12) {
			System.out.println("Invalid serial number entered.");
			return false;
		}
		
		
		else {
		
			if(serialNumber.charAt(0) == 'm') {
				auditList = mc7Audit;
			}
			else if (serialNumber.charAt(0) == 'D') {
				System.out.println("in D");
				auditList = dm2Audit;
			}
			else if (serialNumber.charAt(0) == 'B') {
				if(serialNumber.charAt(1) == 'O') {
					auditList = bo2Audit;
				}
				else if(serialNumber.charAt(1) == 'P'){
					auditList = bp2Audit;
				}
			}
			else {
				System.out.println("Invalid serial number entered.");
				return false;
			}
		}
		int j = 0;
		for (int i = j; i < auditList.size(); i++) {
			if (auditList.contains(serialNumber)) {
				bing.stop();
				bing.resetAudioStream();
				System.out.println("Found " + serialNumber + "!");
				bing.play();
				Thread.sleep(1100);
				bing.stop();
				bing.resetAudioStream();
				return true;
			}
		}
		bong.stop();
		bong.resetAudioStream();
		bong.play();
		Thread.sleep(750);
		bong.stop();
		
		System.out.println("Not found");
		return false;
	}
	

	public static AudioPlayer getBing() {
		return bing;
	}

	public static void setBing(AudioPlayer audio) {
		bing = audio;
	}

	public static AudioPlayer getBong() {
		return bong;
	}

	public static void setBong(AudioPlayer audio) {
		bong = audio;
	}

	public static void setBingAudio() throws InterruptedException {
		try {
			AudioPlayer bing = new AudioPlayer(bingPath);
			setBing(bing);
			System.out.println("Bing initialised...");
			Thread.sleep(100);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Audio file is unsupported");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not find the specified audio file");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("Error with playing sound");
			e.printStackTrace();
		}
		
		
	}
	
	public static void setBongAudio() throws InterruptedException {
		try {
			AudioPlayer bong = new AudioPlayer(bongPath);
			setBong(bong);
			System.out.println("Bong initialised...");
			Thread.sleep(100);
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Audio file is unsupported");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not find the specified audio file");
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			System.out.println("Error with playing sound");
			e.printStackTrace();
		}
		
	}
	
	

}
