package org.camunda.bdd.examples.simple.bdd;

import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;
import static org.jbehave.core.reporters.Format.*;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.camunda.bdd.examples.simple.steps.SimpleProcessSteps;
import org.camunda.bpm.bdd.steps.CamundaSteps;
import org.camunda.bpm.test.CamundaSupportInjectionProvider;
import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.needle.UsingNeedle;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.needle.NeedleAnnotatedEmbedderRunner;
import org.jbehave.core.reporters.PrintStreamStepdocReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.PrintStreamStepMonitor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(NeedleAnnotatedEmbedderRunner.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = true, ignoreFailureInStories = false, ignoreFailureInView = true, verboseFailures = true)
@Configure(stepMonitor = PrintStreamStepMonitor.class, pendingStepStrategy = FailingUponPendingStep.class, stepdocReporter = PrintStreamStepdocReporter.class, storyReporterBuilder = SimpleBehaviorTest.RichReporterBuilder.class)
@UsingSteps(instances = { SimpleProcessSteps.class, CamundaSteps.class })
@UsingNeedle(provider = { CamundaSupportInjectionProvider.class })
public class SimpleBehaviorTest extends InjectableEmbedder {

  @Override
  @Test
  public void run() {
    injectedEmbedder().runStoriesAsPaths(storyPaths());
  }

  /**
   * Report builder.
   */
  public static class RichReporterBuilder extends StoryReporterBuilder {
    public RichReporterBuilder() {
      withDefaultFormats().withViewResources(getViewResources()).withFormats(CONSOLE, HTML, XML).withFailureTrace(true).withFailureTraceCompression(true);
    }

    /**
     * Retrieves the configuration of the view.
     */
    final static Properties getViewResources() {
      final Properties viewResources = new Properties();
      viewResources.put("decorateNonHtml", "false");
      return viewResources;
    }

  }

  /**
   * Retrieves the location of the stories.
   */
  protected List<String> storyPaths() {
    return new StoryFinder().findPaths(getStoryLocation(), "**/*.story", "");
  }

  /**
   * Retrieves the location of the stories.
   * 
   * @return location of the stories to looc for.
   */
  public static URL getStoryLocation() {
    return codeLocationFromPath("src/test/resources");
  }
}
