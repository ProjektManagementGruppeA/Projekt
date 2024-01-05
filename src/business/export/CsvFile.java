package business.export;

import business.sonderwunsch.Sonderwunsch;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public void setInput(String[][] input) {
        this.input = input;
    }



    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String[][] getInput() {
        return input;
    }

    public String getFileName() {
        return fileName;
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
