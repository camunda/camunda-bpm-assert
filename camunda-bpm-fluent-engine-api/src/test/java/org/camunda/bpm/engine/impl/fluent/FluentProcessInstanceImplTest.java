package org.camunda.bpm.engine.impl.fluent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import org.camunda.bpm.engine.fluent.FluentProcessEngine;
import org.junit.Test;

public class FluentProcessInstanceImplTest {

    @Test
    public void shouldReturnIsActive() {
        final FluentProcessInstanceImpl instance = spy(new FluentProcessInstanceImpl(mock(FluentProcessEngine.class)));

        setEnded(instance, false);
        setSuspended(instance, false);
        assertTrue(instance.isActive());

        setEnded(instance, true);
        setSuspended(instance, false);
        assertFalse(instance.isActive());

        setEnded(instance, false);
        setSuspended(instance, true);
        assertFalse(instance.isActive());

    }

    private void setEnded(final FluentProcessInstanceImpl instance, final boolean ended) {
        doReturn(ended).when(instance).isEnded();
    }

    private void setSuspended(final FluentProcessInstanceImpl instance, final boolean suspended) {
        doReturn(suspended).when(instance).isSuspended();
    }

}
