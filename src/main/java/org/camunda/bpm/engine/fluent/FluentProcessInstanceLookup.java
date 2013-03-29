package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class FluentProcessInstanceLookup {

    private static ThreadLocal<Map<String, FluentProcessInstance>> testProcessInstances;

    protected static void before(Object junitTest) {
        testProcessInstances = new ThreadLocal<Map<String, FluentProcessInstance>>();
        testProcessInstances.set(new HashMap<String, FluentProcessInstance>());
    }

    protected static void after(Object junitTest) {
        testProcessInstances.set(null);
    }

    protected static Map<String, FluentProcessInstance> getTestProcessInstances() {
        return testProcessInstances.get();
    }

    /**
     * @see org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests#newProcessInstance(String)
     */
    public static FluentProcessInstance newProcessInstance(String processDefinitionKey) {
        if (!getTestProcessInstances().containsKey(processDefinitionKey)) {
            getTestProcessInstances().put(processDefinitionKey, new FluentProcessInstanceImpl(processDefinitionKey));
        }
        return getTestProcessInstances().get(processDefinitionKey);
    }

    /**
     * @see org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests#processInstance()
     */
    public static FluentProcessInstance processInstance() {
        if (testProcessInstances.get().isEmpty())
            throw new IllegalStateException("No process instance has been started yet in the context of the current thread. Call newProcessinstance(String) first.");
        if (testProcessInstances.get().size() > 1)
            throw new IllegalStateException("More than one process instance has been started in the context of the current thread.");
        return testProcessInstances.get().values().iterator().next();
    }
    
    public static ProcessDefinition processDefinition(String processDefinitionKey) {
        List<ProcessDefinition> definitions = FluentLookups.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionKey).list();

        assertThat(definitions)
                .overridingErrorMessage("Unable to find a deployed processInstance definition with name '%s'", processDefinitionKey)
                .hasSize(1);

        return definitions.get(0);
    }

}
