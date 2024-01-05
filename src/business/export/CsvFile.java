package business.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFile implements ExternalFile {

    protected File fileName;
    protected String[] input;
    public CsvFile(File fileName, String[]input) {
        this.fileName = fileName;
        this.input = input;
    }
   
   /* public void export() throws IOException {
        File csvOutputFile = new File(fileName);
        FileWriter fileWriter = new FileWriter(csvOutputFile);

        for (String[] data : input) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.length; i++) {
                line.append("\"");
                line.append(data[i].replaceAll("\"","\"\""));
                line.append("\"");
                if (i != data.length - 1) {
                    line.append(',');
                }
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
    }
    */
    
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


}
