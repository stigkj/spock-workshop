package nos.gradle.spock.example.interaction

import spock.lang.Specification

/**
 * Unit test of {@link Publisher}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
class PublisherSpec extends Specification {
    Publisher publisher
    Subscriber sub1
    Subscriber sub2

    def setup() {
        publisher = new Publisher()
        sub1 = Mock()
        sub2 = Mock()
        publisher.subscribers << sub1 << sub2
    }

    def 'mocking'() {
        when: 'publishing an event'
        publisher.send(event)

        then: 'the subscribers should have been called with this event'
        1 * sub1.receive(_)
        1 * sub2.receive(!null)

        where:
        event << ["test", "fails"]
    }

    def 'stubbing'() {
        given: 'sub2 which returns an error status when input too long'
        sub2.receive(_) >> { msgs ->
            msgs[0].size() > 4 ? 'fail' : 'ok'
        }
            
        when: 'publishing events'
        publisher.send('test')
        publisher.send('fails')

        then: 'exception is thrown after second call of sub2'
        (1.._) * sub1.receive('test')
        IllegalStateException e = thrown()
        e.message == 'Subscriber failed'
    }

    def 'both'() {
        when: 'publishing events'
        publisher.send('test')
        publisher.send('test')
        publisher.send('more')

        then: 'the subscribers are called with this event'
        2 * sub1.receive('test') >> ['N/A', 'N/A']
        2 * sub2.receive('test') >> ['ok', 'ok']
        1 * sub1.receive({it.contains('o')}) >> 'ok'
        1 * _./rec.*/('more') >> 'fail'
        IllegalStateException e = thrown()
        e.message == 'Subscriber failed'
    }
}
