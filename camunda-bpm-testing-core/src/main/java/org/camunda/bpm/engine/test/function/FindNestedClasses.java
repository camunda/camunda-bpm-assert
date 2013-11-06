package org.camunda.bpm.engine.test.function;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.util.Collection;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

public enum FindNestedClasses implements Function<Class<?>, Collection<Class<?>>> {
  PUBLIC_STATIC {

    @Override
    public Collection<Class<?>> apply(final Class<?> parent) {

      return filter(newArrayList(parent.getDeclaredClasses()), new Predicate<Class<?>>() {

        @Override
        public boolean apply(final Class<?> type) {
          final int modifiers = type.getModifiers();
          return isPublic(modifiers) && isStatic(modifiers);
        }
      });
    }
  };

}
