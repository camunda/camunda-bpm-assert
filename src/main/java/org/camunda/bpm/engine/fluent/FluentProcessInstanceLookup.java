package org.camunda.bpm.engine.fluent;

import org.camunda.bpm.engine.repository.ProcessDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
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
    public static FluentProcessInstance newProcessInstance(String processDefinitionId) {
        if (!getTestProcessInstances().containsKey(processDefinitionId)) {
            getTestProcessInstances().put(processDefinitionId, new FluentProcessInstanceImpl(processDefinitionId));
        }
        return getTestProcessInstances().get(processDefinitionId);
    }

    public static FluentProcessInstance processInstance() {
        assertThat(getTestProcessInstances()).hasSize(1);
        return getTestProcessInstances().values().iterator().next();
    }

    public static ProcessDefinition processDefinition(String processDefinitionId) {
        List<ProcessDefinition> definitions = FluentLookups.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionName(processDefinitionId).list();

        assertThat(definitions)
                .overridingErrorMessage("Unable to find a deployed processInstance definition with name '%s'", processDefinitionId)
                .hasSize(1);

        return definitions.get(0);
    }

}
