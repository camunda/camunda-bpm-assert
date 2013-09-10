package util;

import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.ProcessEngineTestCase;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

public class ArquillianUtil {

  public static WebArchive prepareDeployment(String finalName) {

    MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
      .goOffline()
      .loadMetadataFromPom("pom.xml");

    return ShrinkWrap.create(WebArchive.class, finalName)
      .addAsLibraries(resolver.artifact("org.camunda.bpm:camunda-engine-cdi").resolveAsFiles())
      .addAsLibraries(resolver.artifact("org.camunda.bpm.javaee:camunda-ejb-client").resolveAsFiles())
      .addAsLibraries(resolver.artifact("org.easytesting:fest-assert-core").resolveAsFiles())
      .addAsLibraries(resolver.artifact("org.mockito:mockito-core").resolveAsFiles())
        // TODO Once the fluent testing is available via maven then we will just need to add a line here
      .addPackage("org.camunda.bpm.engine.fluent")
      .addPackage("org.camunda.bpm.engine.test.fluent")
      .addPackage("org.camunda.bpm.engine.test.fluent.assertions")
      .addPackage("org.camunda.bpm.engine.test.fluent.mocking")
      .addPackage("org.camunda.bpm.engine.test.fluent.support")
        // TODO: this classes are packaged in org.camunda.bpm:camunda-engine:jar will this JAR be brought in as a transitive dependency?
      .addClass(ProcessEngineTestCase.class)
      .addClass(ProcessEngineRule.class)
        // prepare as process application archive for fox platform
      .addAsWebResource("META-INF/processes.xml", "WEB-INF/classes/META-INF/processes.xml");

  }

}