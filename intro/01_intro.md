!SLIDE full-page
# Smarter testing with Spock
## A practical introduction

!SLIDE content full-page incremental
# Why Spock?
## Spock can...

* reduce the lines of test code
  * removes noise with concise syntax
* make tests more readable (structural blocks)
* turn tests into specs
* be extended in powerful ways
* be run like JUnit test (good tool support)

!SLIDE content full-page
# **

    @@@ java
    class MathSpec extends Specification {
        @Unroll({"Max of $x and $y should be $result"})
        def 'using table driven test'() {
            when:
            def max = Math.max(x, y)

            then:
            max == result
            notThrown(IllegalStateException)

            where:
            x | y | result
            1 | 3 | 3
            4 | 2 | 4
        }
    }

!SLIDE content full-page
# **

    @@@ java
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

!SLIDE content full-page
# **

    @@@ java
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

!SLIDE content full-page
# **

    @@@ java
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

!SLIDE content full-page
# **

    @@@ java
    Condition not satisfied:

    e.message == 'Subscriber faile'
    | |       |
    | |       false
    | |       1 difference (94% similarity)
    | |       Subscriber faile(d)
    | |       Subscriber faile(-)
    | Subscriber failed
    java.lang.IllegalStateException: Subscriber failed

!SLIDE content full-page
# **

    @@@ java
    class CollectionSpec extends Specification {
        @Rule
        public TemporaryFolder folder =
                                  new TemporaryFolder()

        def 'easy collections and file handling'() {
            given: 'a folder with 3 files'
            folder.newFile('file1') << 'file1'
            folder.newFile('file2') << 'file2'
            folder.newFile('file3') << 'file3'

            when: 'listing files in this folder'
            def files = folder.root.list()

            then: 'number of files should be 3'
            files.size() == 3

            and: "one of the files should be 'file3'"
            files.any {it.contains('file3')}
        }
    }
