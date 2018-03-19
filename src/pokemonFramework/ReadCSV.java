package pokemonFramework;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class ReadCSV {

	@SuppressWarnings({ "rawtypes", "unchecked" })

	public static ArrayList[] read(String file) {
		ArrayList[] outString = null;
		if (file == "pokemon.csv") {
			outString = new ArrayList[14];
			for (int i = 0; i < outString.length; i++) {
				outString[i] = new ArrayList();
			}
		}else if (file == "moves.csv") {
			outString = new ArrayList[8];
			for (int i = 0; i < outString.length; i++) {
				outString[i] = new ArrayList();
			}
		}
		CSVReader reader = null;
		try {
			// Get the CSVReader instance with specifying the delimiter to be
			// used
			reader = new CSVReader(new FileReader("src/data/" + file), ',');
			String[] nextLine;
			// Read one line at a time
			int y = 0;
			while ((nextLine = reader.readNext()) != null) {

				for (int i = 0; i < nextLine.length; i++) {
					// Print all tokens
					outString[i].add(y, nextLine[i]);
				}
				y++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outString;
	}
}