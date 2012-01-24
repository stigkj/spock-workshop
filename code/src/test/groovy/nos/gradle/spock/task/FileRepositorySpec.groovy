package nos.gradle.spock.task

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Ignore
import spock.lang.Specification

/**
 * Simple test of {@link FileRepository}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
class FileRepositorySpec extends Specification {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder()
    FileRepository fileRepository

    def 'what is persisted should be loaded'() {
        given:
        fileRepository = new FileRepository(temporaryFolder.root)

        when:
        input.each {fileRepository.persist('user', it)}

        then:
        fileRepository.load('user') == output as Set

        where:
        input                    | output
        ['uid3', 'uid2', 'uid1'] | ['uid1', 'uid2', 'uid3']
        ['uid3', 'uid1', 'uid2'] | ['uid1', 'uid2', 'uid3']
    }
    
    def 'null as root folder'() {
        when:
        new FileRepository(null)
        
        then:
        thrown(NullPointerException)
    }

    @Ignore('Do not know how to trigger this')
    def 'illegal root folder'() {
        when:
        new FileRepository(new File("!!<>@@??.åøæ."))

        then:
        thrown(IllegalStateException)
    }

    def 'illegal key'() {
        given:
        fileRepository = new FileRepository(temporaryFolder.root)

        when:
        fileRepository.persist(null, 'data')

        then:
        thrown(IllegalStateException)

        when:
        fileRepository.load(null)

        then:
        thrown(IllegalStateException)
    }

}
