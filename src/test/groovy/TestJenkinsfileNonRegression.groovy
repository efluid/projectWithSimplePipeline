import com.lesfurets.jenkins.unit.BaseRegressionTest
import org.junit.Before
import org.junit.Test

class TestJenkinsfileNonRegression extends BaseRegressionTest {

    @Override
    @Before
    void setUp() throws Exception {
        super.setUp()
        binding.setVariable('scm', [$class: 'GitSCM'])
        binding.setVariable('env', [PATH: "/bin", "CLEAN": "false"])

        helper.registerAllowedMethod("tool", [Map.class], { c -> '/bin/gradle' })
        helper.registerAllowedMethod("tool", [String.class], { c -> '/bin/gradle' })
        helper.registerAllowedMethod("withEnv", [ArrayList.class, Closure.class], { a, c -> c.call() })
        helper.registerAllowedMethod("fileExists", [String.class], { c -> true })
    }


    @Test
    void testNonReg() throws Exception {
        runScript("Jenkinsfile")
        super.testNonRegression('example')
    }

}