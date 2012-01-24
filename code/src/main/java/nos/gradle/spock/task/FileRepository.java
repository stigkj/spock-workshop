package nos.gradle.spock.task;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.common.io.Files;

/**
 * Simple file repository
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
public class FileRepository implements Repository {
    private File rootFolder;

    public FileRepository(File rootFolder) {
        rootFolder.delete();

        if (!rootFolder.mkdirs()) {
            throw new IllegalStateException("Could not create directory");
        }

        this.rootFolder = rootFolder;
    }

    public void persist(String key, String data) {
        try {
            String newlinedData = String.format("%s%n", data);
            Files.append(newlinedData, new File(rootFolder, key), Charset.defaultCharset());
        } catch (Exception e) {
            throw new IllegalStateException("Could not write data", e);
        }
    }

    public SortedSet<String> load(String key) {
        try {
            return new TreeSet<String>(Files.readLines(new File(rootFolder, key), Charset.defaultCharset()));
        } catch (Exception e) {
            throw new IllegalStateException("Could not read data", e);
        }
    }
}
