package nos.gradle.spock.task

import spock.lang.Specification

/**
 * Simple test of {@link FileRepository}
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.24
 */
class FileRepositorySpec extends Specification {
    def 'try to persist and then load with a sorted result, trying multiple inputs in a temporary folder'() {
    }
    
    def 'null as root folder should throw NPE, not IllegalStateException'() {
    }

    def 'illegal key (null) should throw ISE both for persist and load'() {
    }
}
