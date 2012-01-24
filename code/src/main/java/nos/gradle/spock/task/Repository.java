package nos.gradle.spock.task;

import java.util.SortedSet;

/**
 * Simple repository
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
public interface Repository {
    void persist(String key, String data);
    SortedSet<String> load(String key);
}
