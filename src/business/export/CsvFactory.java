package business.export;

public class CsvFactory extends FileFactory{
    @Override
    protected ExternalFile createExternalFile(String fileName, String[][]input ) {
        return new CsvFile(fileName,input);
    }

}
