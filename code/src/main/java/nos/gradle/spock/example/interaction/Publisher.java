package nos.gradle.spock.example.interaction;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Simple publisher
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
public class Publisher {
    Set<Subscriber> subscribers = new LinkedHashSet<Subscriber>();

    void send(String event) {
        for (Subscriber subscriber : subscribers) {
            String status = subscriber.receive(event);
            
            if (status != null && status.equals("fail")) {
                throw new IllegalStateException("Subscriber failed");
            }
        }
    }
}
