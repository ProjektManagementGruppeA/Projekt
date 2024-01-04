package business.export;
import java.io.File;
import java.io.IOException;

public abstract class FileFactory {
    public ExternalFile create(File fileName, String[]input ) throws IOException {
        ExternalFile file = createExternalFile(fileName,input);
        file.export();
        return file;

    }
    protected abstract ExternalFile createExternalFile(File fileName, String[]input);
}
