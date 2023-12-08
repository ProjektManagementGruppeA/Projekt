package business.export;
import java.io.IOException;

public abstract class FileFactory {
    public ExternalFile create(String fileName, String[][]input ) throws IOException {
        ExternalFile file = createExternalFile(fileName,input);
        file.export();
        return file;

    }
    protected abstract ExternalFile createExternalFile(String fileName, String[][]input);
}
