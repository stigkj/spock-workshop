package nos.gradle.spock.example.state

import org.junit.Rule
import spock.lang.Specification
import org.junit.rules.TemporaryFolder

/**
 * Simple test showing off JUnit @Rules and Groovy's collection and file support
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
class CollectionSpec extends Specification {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder()

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
