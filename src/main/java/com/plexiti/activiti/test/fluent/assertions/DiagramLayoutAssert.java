package com.plexiti.activiti.test.fluent.assertions;


import org.camunda.bpm.engine.repository.DiagramLayout;
import org.fest.assertions.api.AbstractAssert;
import org.fest.assertions.core.Condition;

/**
 * Fluent assertions for {@link org.camunda.bpm.engine.repository.DiagramLayout}
 *
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class DiagramLayoutAssert extends AbstractAssert<DiagramLayoutAssert, DiagramLayout> {

    protected DiagramLayoutAssert(DiagramLayout actual) {
        super(actual, DiagramLayoutAssert.class);
    }

    public static DiagramLayoutAssert assertThat(DiagramLayout actual) {
        return new DiagramLayoutAssert(actual);
    }

    public DiagramLayoutAssert isContainingNode(final String nodeId) {
        return assertThat(actual).is(new Condition<DiagramLayout>() {
            public boolean matches(DiagramLayout actual) {
                return actual.getNode(nodeId) != null;
            };
        });
    }

}
