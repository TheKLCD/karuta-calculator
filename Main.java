import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Get the input file
		File spreadsheet = new File("db17cc4cb25fa9a0c0b95b660286231b.csv");
		Scanner reader = new Scanner(spreadsheet);
		reader.nextLine();
		
		//Set up the output file
		File outputFile = new File("Prices.csv");

		if(!outputFile.createNewFile()){
			outputFile.delete();
			outputFile.createNewFile();
		}
		
		//Make the parallel arrays for the data
		ArrayList<Integer> values = new ArrayList<>(); 
		ArrayList<String> codes = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		
		//Get the data for each card
		while(reader.hasNextLine()) {
			//Split on ",", WARNING: index 0 still starts with a " and last index still ends in "
			String[] card = reader.nextLine().split("\",\"");
			
			//Get values of the card
			int print = Integer.parseInt(card[1]);
			int wish = Integer.parseInt(card[16]);
			int edition = Integer.parseInt(card[2]);

			//Find the value in tickets
			int value = 0;
			
			if(print < 100) {
				value = 9999;
			}
			else if(print < 1000) {
				switch(edition) {
					case 1:
						value = wish/55;
						break;
					case 2:
					case 4:
					case 5:
						value = wish/70;
						break;
					case 3:
						value = wish/60;
						break;
					default:
						value = 9999;
				}
			}
			else {
				switch(edition) {
					case 1:
					case 2:
						value = wish/1100;
						break;
					case 3:
					case 4:
						value = wish/750;
						break;
					case 5:
						value = wish/370;
						break;
					default:
						value = 9999;
				}
			}
			
			//Add card data to their respective array
			values.add(value);
			
			String code = card[0].substring(1);
			
			codes.add(code);
			
			String name = card[3];
			
			names.add(name);
		}
		
		//Sort the cards by their price, descending
		boolean sorted = false;
		
		while(!sorted) {
			sorted = true;
			
			for(int i = 0; i < values.size()-1; i++) {
				for(int j = i+1; j < values.size(); j++) {
					if(values.get(i) < values.get(j)) {
						int tempValue = values.get(i);
						String tempCode = codes.get(i);
						String tempName = names.get(i);
						
						values.set(i, values.get(j));
						codes.set(i, codes.get(j));
						names.set(i, names.get(j));
						
						values.set(j, tempValue);
						codes.set(j, tempCode);
						names.set(j, tempName);
						
						sorted = false;
					}
				}
			}
		}
		
		//Print the data to the output file
		PrintStream writer = new PrintStream(outputFile);

		for(int i = 0; i < values.size(); i++) {
			String cardData = "\""+values.get(i)+"\",\""+codes.get(i)+"\",\""+names.get(i)+"\"";
			
			if(i!= values.size()-1){
				writer.println(cardData);
			}
			else{
				writer.print(cardData);
			}
		}

		reader.close();
		writer.close();
	}
}