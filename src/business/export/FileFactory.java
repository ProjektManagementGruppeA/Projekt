package business.export;
import java.io.IOException;

public abstract class FileFactory {
    public externalFile create(String fileName, String[][]input ) throws IOException {
        externalFile file = createExternalFile(fileName,input);
        file.export();
        return file;

    }
    protected abstract externalFile createExternalFile(String fileName, String[][]input);
}
