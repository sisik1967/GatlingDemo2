import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit5.Karate;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectRunner {
    @Karate.Test
    Karate testAll() {
        // run command line: mvn test -Dtest="ProjectRunner#testAll"
        // mvn clean test -Dtest="Runner#testAll" "-Dkarate.options=--tags @regression"
        return Karate.run("classpath:features").relativeTo(getClass()).reportDir("target");
    }

    @Karate.Test
    Karate testSmoke() {
        // run command line: mvn test -Dtest="ProjectRunner#testSmoke"
        return Karate.run("classpath:features").tags("@smoke").relativeTo(getClass()).reportDir("target");
    }

    @Karate.Test
    Karate testSystemProperty() {
        return Karate.run("classpath:features")
                .tags("@smoke")
                .karateEnv("qa")
                .systemProperty("foo", "bar");
    }

    @Test
    void testParallelConduit() {
        // run command line: mvn clean test -Dtest="ProjectRunner#testParallelPetstore" "-Dkarate.options=--tags @smoke" -Dcount=10
        String threadCount = System.getProperty("count");
        int count = (threadCount == null) ? 5 : parseInt(threadCount);
        Results results = Runner.path("classpath:features/conduit").outputCucumberJson(true).parallel(count);
        generateReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    @Test
    void testParallelConduitSmoke() {
        // run command line: mvn clean test -Dtest="ProjectRunner#testParallelPetstoreSmoke"  -Dcount=10
        String threadCount = System.getProperty("count");
        int count = (threadCount == null) ? 5 : parseInt(threadCount);
        Results results = Runner.path("classpath:features/conduit").tags("@smoke").outputCucumberJson(true).parallel(count);
        generateReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    @Test
    public void testParallel() {
        // run command line: mvn test -Dtest="ProjectRunner#testParallel"
        Results results = Runner.path("classpath:features")
                .tags("@smoke")
                .outputCucumberJson(true)
                .karateEnv("env")
                .parallel(5);

        generateReport(results.getReportDir());

        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    public static void generateReport(String cucumberReport) {
        Collection<File> jsonFiles = FileUtils.listFiles(new File(cucumberReport), new String[]{"json"}, true);
        List<String> jsonPaths = new ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file -> jsonPaths.add(file.getAbsolutePath()));
        Configuration config = new Configuration(new File("target"), "Cucumber-Report");
        ReportBuilder reportBuilder = new ReportBuilder(jsonPaths, config);
        reportBuilder.generateReports();
    }
}
