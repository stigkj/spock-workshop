package nos.gradle.spock.example.interaction;

/**
 * Simple subscriber
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
public interface Subscriber {
    String receive(String event);
}
