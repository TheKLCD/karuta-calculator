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
		ArrayList<String> cardInfo = new ArrayList<>();
		
		
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
			
			if(print < 10){
				value = 9999;
			}
			else if(print < 100) {
				switch(edition){
					case 6:
						value = 9999;
						break;
					default:
						value = wish/5;
				}
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
			
			//Format the data for selling
			String price = "\""+value+":tickets:\",";
			String wl = "\"♡"+wish+"\",";
			String code = "\""+card[0].substring(1)+"\",";

			String quality = "";
			
			//☆ ★
			switch(card[5]){
				case "0":
					quality = "\"☆☆☆☆\",";
					break;
				case "1":
					quality = "\"★☆☆☆\",";
					break;
				case "2":
					quality = "\"★★☆☆\",";
					break;
				case "3":
					quality = "\"★★★☆\",";
					break;
				case "4":
					quality = "\"★★★★\",";
					break;
			}

			String number = "\"#"+print+"\",";
			String ed = "\"◈"+edition+"\",";
			String series = "\""+card[4]+"\",";
			String character = "\""+card[3]+"\"";
			
			String cardFormatted = price+wl+code+quality+number+ed+series+character;
			cardInfo.add(cardFormatted);
		}
		
		//Sort the cards by their price, descending
		boolean sorted = false;
		
		while(!sorted) {
			sorted = true;
			
			for(int i = 0; i < values.size()-1; i++) {
				for(int j = i+1; j < values.size(); j++) {
					if(values.get(i) < values.get(j)) {
						int tempValue = values.get(i);
						String tempInfo = cardInfo.get(i);
						
						values.set(i, values.get(j));
						cardInfo.set(i, cardInfo.get(j));
						
						values.set(j, tempValue);
						cardInfo.set(j, tempInfo);
						
						sorted = false;
					}
				}
			}
		}
		
		//Print the data to the output file
		PrintStream writer = new PrintStream(outputFile);
		writer.println("\"Price\",\"Wishlist\",\"Code\",\"Quality\",\"Print\",\"Edition\",\"Series\",\"Character\"");

		for(int i = 0; i < cardInfo.size(); i++) {
			if(i!= values.size()-1){
				writer.println(cardInfo.get(i));
			}
			else{
				writer.print(cardInfo.get(i));
			}
		}

		reader.close();
		writer.close();
	}
}