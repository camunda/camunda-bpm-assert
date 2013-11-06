package org.camunda.bdd.examples.simple.bdd;

import org.camunda.bdd.examples.simple.steps.SimpleProcessSteps;
import org.camunda.bpm.bdd.steps.CamundaSteps;
import org.camunda.bpm.test.CamundaSupportInjectionProvider;
import org.jbehave.core.annotations.UsingSteps;
import org.jbehave.core.annotations.needle.UsingNeedle;

/**
 * JBehave tests for the simple process.
 */
@UsingSteps(instances = { SimpleProcessSteps.class, CamundaSteps.class })
@UsingNeedle(provider = { CamundaSupportInjectionProvider.class })
public class SimpleBehaviorTest extends JBehaveTestBase {

}
