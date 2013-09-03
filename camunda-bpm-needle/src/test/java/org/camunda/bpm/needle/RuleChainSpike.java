package org.camunda.bpm.needle;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.InOrder;

public class RuleChainSpike {

    private final TestRule rule1 = mock(TestRule.class);
    private final TestRule rule2 = mock(TestRule.class);
    private final TestRule rule3 = mock(TestRule.class);

    private final RuleChain ruleChain = RuleChain.outerRule(rule1).around(rule2).around(rule3);

    /**
     * Conclusion: RuleChain is executed from right to left. Innermost first.
     * @throws Exception
     */
    @Test
    public void shouldCallRulesInOrder() throws Exception {
        final InOrder inOrder = inOrder(rule3, rule2, rule1);

        ruleChain.apply(mock(Statement.class), mock(Description.class));

        inOrder.verify(rule3).apply(any(Statement.class), any(Description.class));
        inOrder.verify(rule2).apply(any(Statement.class), any(Description.class));
        inOrder.verify(rule1).apply(any(Statement.class), any(Description.class));

        inOrder.verifyNoMoreInteractions();
    }

}
