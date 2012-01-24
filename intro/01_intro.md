!SLIDE full-page
# Spock
## A practical introduction

!SLIDE content full-page incremental

Fast walk through of the framework

Features to use in tests
* JUnit @Rules, like @TemporaryFolder
* mocks (1 * <call>, etc)
* given -> and -> and -> when -> and -> and -> then -> and
* closures
* collection stuff (...each { it... })
* where stuff (tables with data, input & output, @Unroll)
* extensions (spring, unitils)



Condition not satisfied:

e.message == 'Subscriber faile'
| |       |
| |       false
| |       1 difference (94% similarity)
| |       Subscriber faile(d)
| |       Subscriber faile(-)
| Subscriber failed
java.lang.IllegalStateException: Subscriber failed

code to write tests for
* Repository
  - FileRepository
	--> takes a directory in constructor which is set with TemporaryFolder @Rule
	--> table driven with input and expected output
* X using Repository 
  --> mock/stub the Repository interface
  --> should handle exceptions properly, stop runtime (use notThrown), but not errors --> thrown(IOError)

