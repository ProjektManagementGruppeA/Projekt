package business.export;

import java.io.File;

public class CsvFactory extends FileFactory{
    @Override
    protected ExternalFile createExternalFile(File fileName, String[][]input ) {
        return new CsvFile(fileName,input);
    }

}
