package nos.gradle.spock.example.state

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Simple table driven unit test
 *
 * @author Stig Kleppe-Jorgensen, 2012.01.23
 */
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
