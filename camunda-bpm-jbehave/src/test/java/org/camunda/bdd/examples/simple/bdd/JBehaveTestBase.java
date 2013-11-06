package org.camunda.bdd.examples.simple.bdd;

import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.XML;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.jbehave.core.InjectableEmbedder;
import org.jbehave.core.annotations.Configure;
import org.jbehave.core.annotations.UsingEmbedder;
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
public abstract class JBehaveTestBase extends InjectableEmbedder {

  /**
   * Retrieves the location of the stories. <br>
   * This method is intended to be overwritten on divergent location for stories
   * than src/test/resources
   * 
   * @return location of the stories to look for.
   */
  protected URL getStoryLocation() {
    return codeLocationFromPath("src/test/resources");
  }

  /**
   * Retrieves the location of the stories.
   */
  protected List<String> storyPaths() {
    return new StoryFinder().findPaths(getStoryLocation(), "**/*.story", "");
  }

  @Override
  @Test
  public void run() {
    injectedEmbedder().runStoriesAsPaths(storyPaths());
  }

  /**
   * Report builder.
   */
  public static class RichReporterBuilder extends StoryReporterBuilder {

    /**
     * Constructs the builder.
     */
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

}