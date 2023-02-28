import java.io.File; //Imports the File Classes
import java.io.FileNotFoundException;
import java.io.IOException; //Imports the IOException class to handle errors
import java.util.Scanner;  //Imports the Scanner classs to read files
public class LEDScanner {
	
	//Variables
	String panelNumber;
	int[] auditPanels;
	int[] imagPanels;
	int[] toAudit;
//	File auditFile = new File("ToAudit.txt");

	
	public static void main(String[] args) {
		
	}

	public void readFile() {
		try {
			File auditFile = new File("ToAudit.txt");
			Scanner fileReader = new Scanner(auditFile);
			int i = 0;
			while(fileReader.hasNextLine()) {
				toAudit[i] = fileReader.nextInt();
			}
			fileReader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("An error occured reading the file.");
			e.printStackTrace();
		}
		
			
		
		 
	}
	
	
	
	
	

	public String getPanelNumber() {
		return panelNumber;
	}


	public void setPanelNumber(String panelNumber) {
		this.panelNumber = panelNumber;
	}


	public int[] getAuditPanels() {
		return auditPanels;
	}


	public void setAuditPanels(int[] auditPanels) {
		this.auditPanels = auditPanels;
	}


	public int[] getImagPanels() {
		return imagPanels;
	}


	public void setImagPanels(int[] imagPanels) {
		this.imagPanels = imagPanels;
	}
	

}
