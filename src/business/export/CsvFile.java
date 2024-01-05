package business.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFile implements ExternalFile {

    protected File fileName;
    protected String[][] input;
    public CsvFile(File fileName, String[][]input) {
        this.fileName = fileName;
        this.input = input;
    }
    
    @Override
    public void export() {
    	 try {
             FileWriter writer = new FileWriter(fileName);

             for (String[] row : input) {
                 for (int i = 0; i < row.length; i++) {
                     writer.append(row[i]);
                     if (i != row.length - 1) {
                         writer.append(",");
                     }
                 }
                 writer.append("\n"); // Move to the next line for the next row
             }

             writer.flush();
             writer.close();
             System.out.println("Data successfully written to CSV file.");
         } catch (IOException e) {
             System.out.println("Error writing to CSV file: " + e.getMessage());
             e.printStackTrace();
         }
    }
    
    /*
    @Override
    public void export() {
    	
        try {
            FileWriter writer = new FileWriter(fileName);

            for (int i = 0; i < input.length; i++) {
                writer.append(input[i]);
                if (i != input.length - 1) {
                    writer.append(",");
                }
            }
            writer.flush();
            writer.close();
            System.out.println("Data successfully written to CSV file.");
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
*/

}
