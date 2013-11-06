package org.camunda.bpm.engine.test.function;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class FindNestedClassesTest {

  private final FindNestedClasses function = FindNestedClasses.PUBLIC_STATIC;

  public static final class A {
  }

  public static class B {
  }

  private static final class C {
  }

  public class D {
  }

  @Test
  public void shouldFindAandB() {
    final Collection<Class<?>> nested = function.apply(getClass());
    assertThat(nested, hasSize(2));
    assertThat(nested, hasItems(A.class, B.class));
  }

  @Test
  public void shouldNotFindC() {
    final Collection<Class<?>> nested = function.apply(getClass());
    assertThat(nested, not(hasItem(C.class)));
  }
}
