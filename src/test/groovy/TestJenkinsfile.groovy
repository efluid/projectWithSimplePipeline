import com.lesfurets.jenkins.unit.BasePipelineTest
import org.junit.Before
import org.junit.Test

import static com.lesfurets.jenkins.unit.MethodCall.callArgsToString
import static org.assertj.core.api.AssertionsForClassTypes.assertThat

class TestJenkinsfile extends BasePipelineTest {

    @Override
    @Before
    void setUp() throws Exception {
        super.setUp()
        binding.setVariable('scm', [$class: 'GitSCM'])
        binding.setVariable('env', [PATH: "/bin", "CLEAN":"false"])

        helper.registerAllowedMethod("tool", [Map.class], { c -> '/bin/gradle' })
        helper.registerAllowedMethod("tool", [String.class], { c -> '/bin/gradle' })
        helper.registerAllowedMethod("withEnv", [ArrayList.class, Closure.class], { a, c -> c.call() })
        helper.registerAllowedMethod("fileExists", [String.class], { c -> false })
    }


    @Test
    void should_execute_without_errors_and_gradle_build() throws Exception {
        runScript("Jenkinsfile")
        printCallStack()
        assertThat(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.any { call ->
            callArgsToString(call).contains("gradle build")
        }).isTrue()
        assertJobStatusSuccess()
    }

    @Test
    void should_execute_without_errors_and_gradle_clean_build() throws Exception {
        binding.setVariable('env', [PATH: "/bin", "CLEAN":"true"])
        runScript("Jenkinsfile")
        printCallStack()
        assertThat(helper.callStack.findAll { call ->
            call.methodName == "sh"
        }.any { call ->
            callArgsToString(call).contains("gradle clean build")
        }).isTrue()
        assertJobStatusSuccess()
    }
}