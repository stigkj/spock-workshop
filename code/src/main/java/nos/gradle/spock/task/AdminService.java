package nos.gradle.spock.task;

import java.util.SortedSet;

/**
 * Simple service
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
public class AdminService {
    private Repository repository;

    public AdminService(Repository repository) {
        this.repository = repository;
    }

    public void addUser(String userName) {
        repository.persist("user", userName);
    }

    public SortedSet<String> listUsers() {
        return repository.load("user");
    }

    public boolean validate(String userName) {
        return userName.contains("ig");
    }
}
