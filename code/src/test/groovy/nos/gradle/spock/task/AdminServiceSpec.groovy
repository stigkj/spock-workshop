package nos.gradle.spock.task;


import spock.lang.Specification
import spock.lang.Unroll

/**
 * Simple test of {@link AdminService}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
class AdminServiceSpec extends Specification {
	
	AdminService service
	
	Repository repo 

	def setup() {
		repo = Mock()
		service = new AdminService(repo)
	}
	
	
    def 'mocking and stubbing repository'() {
		given: 'mock of load'
		repo.load(_) >> { msgs ->
			msgs[0] == "user" ? ["lasse"] as SortedSet : null
		}
		
		when:
		service.addUser("lasse")
		
		then:
		1 * repo.persist("user", "lasse")
		
		when:
		Set<String> users = service.listUsers()
		
		then:
		users == ["lasse"] as Set
    }

    def 'check for exception thrown'() {
		given: 'mock of load'
		repo.load(_) >> { 
			throw new IllegalArgumentException("Yepp!")
		}
		
		when:
		service.listUsers()
		
		then:
		IllegalArgumentException e2 = thrown()
		e2.getMessage() == "Yepp!"
    }

	@Unroll({"$name is $result"})
    def 'validation with several entries and @Unroll'() {
		when:
		def valid = service.validate(name)
		
		then:
		valid == result
		
		where:
		name | result
		"lasselur" | false
		"sigrid" | true
		"true" | false
    }
}
