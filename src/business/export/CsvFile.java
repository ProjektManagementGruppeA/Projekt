package business.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFile implements ExternalFile {

    protected String fileName;
    protected String[][] input;
    public CsvFile(String fileName, String[][]input) {
        this.fileName = fileName;
        this.input = input;
    }
    @Override
    public void export() throws IOException {
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


}
