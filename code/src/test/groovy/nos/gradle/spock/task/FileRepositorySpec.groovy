package nos.gradle.spock.task


import org.junit.Rule
import org.junit.rules.TemporaryFolder

import spock.lang.Specification

/**
 * Simple test of {@link FileRepository}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
class FileRepositorySpec extends Specification {
	
	@Rule
	public TemporaryFolder folder = new TemporaryFolder()
	
    def 'try to persist and then load with a sorted result, trying multiple inputs in a temporary folder'() {
		given:
		FileRepository repo = new FileRepository(folder.root)
		repo.persist("file1", "record9823")
		repo.persist("file1", "record2356")
		repo.persist("file1", "record7263")
		
		when:
		Set<String> lurk = repo.load("file1")
		 
		then: 'number of files should be 3'
		lurk.size() == 3
		
		and: 'records are sorted'
		lurk == ["record2356", "record7263", "record9823"] as Set
    }
    
    def 'null as root folder should throw NPE, not IllegalStateException'() {
		when:
		FileRepository repo = new FileRepository(null)
		
		then:
		notThrown(IllegalStateException)
		NullPointerException e = thrown()
    }

    def 'illegal key (null) should throw ISE both for persist and load'() {
		given:
		FileRepository repo = new FileRepository(folder.root)

		when:
		repo.persist(null, "whocares")
		
		then:
		IllegalStateException e1 = thrown()
		
		when:
		repo.load(null)
		
		then:
		IllegalStateException e2 = thrown()

	}
}
