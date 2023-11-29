package business.export;

public class CsvFactory extends FileFactory{
    @Override
    protected externalFile createExternalFile(String fileName, String[][]input ) {
        return new csvFile(fileName,input);
    }

}
