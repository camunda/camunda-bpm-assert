package org.camunda.bpm.engine.test.mock;

// CHECKSTYLE:OFF test class
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.uncapitalize;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * TODO move to base.bpm.camunda.test
 * @author Jan Galinski, Holisticon AG
 */
public class RegisterMock {

    public static void register(final String name, final Object instance) {
        Mocks.register(name, instance);
    }

    public static void register(final Class<?>... types) {
        for (final Class<?> type : types) {
            register(resolveName(type), mock(type));
        }
    }

    public static void register(final Object... instances) {
        for (final Object instance : instances) {
            Mocks.register(resolveName(instance.getClass()), instance);
        }
    }

    private static final class FirstListener {

        public static final String NAME = "firstListener";
    }

    private static final class SecondListener {}

    private static String resolveName(final Class<?> type) {
        checkArgument(type != null, "type must not be null!");

        String name = null;
        try {
            final Field declaredField = type.getDeclaredField("NAME");
            if (Modifier.isStatic(declaredField.getModifiers()) && Modifier.isFinal(declaredField.getModifiers())) {
                name = (String)declaredField.get(type.getClass());
            }
        } catch (final Exception e) {
            // ignore
        }

        return name != null ? name : uncapitalize(type.getSimpleName());

    }

    @Test
    public void shouldResolveNAME() throws Exception {
        assertThat(resolveName(FirstListener.class), is(FirstListener.NAME));
        assertThat(resolveName(SecondListener.class), is("secondListener"));
    }
}
