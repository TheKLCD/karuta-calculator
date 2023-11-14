import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File spreadsheet = new File("C:\\Users\\mattf\\eclipse-workspace\\KarutaCalculator\\src\\db17cc4cb25fa9a0c0b95b660286231b.csv");
		Scanner reader = new Scanner(spreadsheet);
		reader.nextLine();
		
		ArrayList<Integer> values = new ArrayList<>(); 
		ArrayList<String> codes = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		
		while(reader.hasNextLine()) {
			String[] card = reader.nextLine().split("\",\"");
			
			int print = Integer.parseInt(card[1]);
			int wish = Integer.parseInt(card[16]);
			int edition = Integer.parseInt(card[2]);
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
			
			values.add(value);
			
			String code = card[0].substring(1);
			
			codes.add(code);
			
			String name = card[3];
			
			names.add(name);
		}
		
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
		
		for(int i = 0; i < values.size(); i++) {
			System.out.println(values.get(i)+","+codes.get(i)+","+names.get(i));
		}
	}
}