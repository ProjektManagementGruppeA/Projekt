package business.export;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.export.FileFactory;
import business.export.CsvFile;

class CsvExportTest {
	
	private static File fileName = new File("test_file.csv"); 
	
	@BeforeEach
	void setup() {
		fileName = new File("test_file.csv"); // Doppelt hält besser oder so...
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		File file = new File(fileName); 
	    if (file.delete()) { 
	      System.out.println("Deleted the file: " + file.getName());
	    } else {
	      System.out.println("Failed to delete the file: " + file.getName());
	    } 
	}

	@Test
	void correctFormatShouldBeEqual() {
		
		String [] [] fileContent = {
            {"Name", "Age", "City", "Occupation", "Salary", "Education"},
            {"John Doe", "28", "New York", "Software Engineer", "90000", "Bachelor's"}
            };
		
		ExternalFile file = new CsvFile(fileName,fileContent);

		try {
			file.export();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String [][] resultContent = readCsv(fileName);
		assertAll("CsvExport", 
				() -> {
					for(int i=0;i<fileContent.length;i++) {
						for(int j=0;j<fileContent[i].length;j++) {
							assertEquals(fileContent[i][j], resultContent[i][j]);
						}
					}
				});
		
	}
	
	@Test
	void badPlacedCommatarShouldBeRemoved() {
		// Dateiinhalt mit falsch platziertem Komma
		String [] [] fileContent = {
	            {"Na,me", "Age"},
	            {"John Doe", "28"}
	            };
		
		// Schreibe Datei
		ExternalFile file = new CsvFile(fileName,fileContent);
		
		try {
			file.export();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		// Lese Datei
		String [][] resultContent = readCsv(fileName);
		
		// Beginne Test
		assertAll("CsvExport", 
				() -> {
					for(int i=0;i<fileContent.length;i++) {
						assertEquals(fileContent[i].length,resultContent[i].length);
					}
				});
	}
	
	@Test
	void dataHasInconsitentColumnSize() {
		String [] [] fileContent = {
	            {"Name", "Age", "City", "Occupation", "Salary", "Education"},
	            {"John Doe", "28", "New York", "Software Engineer", "90000", "Bachelor's"},
	            {"John Doe", "28", "New York", "Software Engineer", "90000", "Bachelor's","column shouldn't exist"}
	            };
		
		// Schreibe Datei
		ExternalFile file = new CsvFile(fileName,fileContent);
		
		try {
			file.export();
		} catch (IOException e) {	
			e.printStackTrace();
		}
		
		// Lese Datei
		String [][] resultContent = readCsv(fileName);
		
		// Beginne Test
		assertAll("CsvExport", 
				() -> {
					int headerSize = fileContent[0].length;
					for(int i=0;i<fileContent.length;i++) {
						assertEquals(headerSize,resultContent[i].length);
					}
				});
	}
	
	
	public static String [][] readCsv(String fileName){
        List<String[]> rows = new ArrayList<>();

        // Build String Array just like input [][]
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    values[i] = values[i].replaceAll("^\"|\"$", "");
                }
                rows.add(values);
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return rows.toArray(new String[0][]);
    }

}
