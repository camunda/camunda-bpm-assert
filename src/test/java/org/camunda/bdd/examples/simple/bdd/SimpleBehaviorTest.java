package org.camunda.bdd.examples.simple.bdd;

import static org.jbehave.core.io.CodeLocations.codeLocationFromPath;

import java.util.List;

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
import org.jbehave.core.steps.PrintStreamStepMonitor;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(NeedleAnnotatedEmbedderRunner.class)
@Configure(stepMonitor = PrintStreamStepMonitor.class, pendingStepStrategy = FailingUponPendingStep.class)
@UsingEmbedder(embedder = Embedder.class, generateViewAfterStories = false, ignoreFailureInStories = false, ignoreFailureInView = true, verboseFailures = true)
@UsingSteps(instances = { SimpleProcessSteps.class, CamundaSteps.class })
@UsingNeedle(provider = { CamundaSupportInjectionProvider.class })
public class SimpleBehaviorTest extends InjectableEmbedder {

  @Override
  @Test
  public void run() {
    injectedEmbedder().runStoriesAsPaths(storyPaths());
  }

  protected List<String> storyPaths() {
    return new StoryFinder().findPaths(codeLocationFromPath("src/test/resources"), "**/*.story", "");
  }

}
