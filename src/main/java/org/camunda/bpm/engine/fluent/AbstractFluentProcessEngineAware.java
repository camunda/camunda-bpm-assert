package org.camunda.bpm.engine.fluent;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class AbstractFluentProcessEngineAware {
    
    protected FluentProcessEngine engine;

    protected AbstractFluentProcessEngineAware(FluentProcessEngine engine) {
        this.engine = engine;
    }

    public FluentProcessEngine getEngine() {
        return engine;
    }
    
}
