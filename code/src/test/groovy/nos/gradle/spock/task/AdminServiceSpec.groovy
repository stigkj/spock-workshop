package nos.gradle.spock.task;


import spock.lang.Specification
import spock.lang.Unroll

/**
 * Simple test of {@link AdminService}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
class AdminServiceSpec extends Specification {
    AdminService adminService
    Repository repository

    def setup() {
        repository = Mock()
        adminService = new AdminService(repository)
    }

    def 'mocking and stubbing'() {
        when:
        adminService.addUser('me')

        and:
        def users = adminService.listUsers()

        then:
        1 * repository.persist('user', 'me')
        1 * repository.load('user') >> result()
        users == ['me'] as Set
    }

    def 'throwing exception'() {
        when:
        adminService.addUser('throws')

        then:
        1 * repository.persist('user', 'throws') >> {throw new IllegalStateException('problems')}
        thrown(IllegalStateException)
    }

    @Unroll({"${name} contains 'ig' --> ${result}"})
    def 'validation'() {
        expect:
        adminService.validate(name) == result

        where:
        name     | result
        'bob'    | false
        'iggy'   | true
        'marley' | false
        'pop'    | false

    }

    SortedSet<String> result() {
        ['me'] as SortedSet
    }
}
