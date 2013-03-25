package auction;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.service.AuctionService;
import com.camunda.showcase.auction.service.TwitterPublishService;
import org.camunda.bpm.engine.test.fluent.FluentProcessEngineTestCase;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static org.camunda.bpm.engine.test.fluent.FluentProcessEngineTests.*;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@camunda.com>
 * @author Rafael Cordones <rafael.cordones@camunda.com>
 */
public class AuctionProcessTest extends FluentProcessEngineTestCase {

    @Mock
    public AuctionService auctionService;

    @Mock
    public TwitterPublishService twitterPublishService;

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testProcessDeployment() {
       assertThat(processDefinition("Auction Process")).isDeployed();
    }

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testWalkThroughProcess() throws Exception {

        // Set up test fixtures

        final Auction auction = new Auction();
        auction.setName("Cheap Ferrari!");
        auction.setDescription("Ferrari Testarossa on sale!");
        auction.setEndTime(new Date()); // TODO show here that the auction ends in the future

        Mocks.register("auction", auction); // TODO Wrap into a fluent method like e.g. "registerWithName"

        when(auctionService.createAuction((Auction) anyObject()))
            .thenAnswer(new Answer() {
                public Object answer(InvocationOnMock invocation) {
                    auction.setId(1L);
                    newProcessInstance("auction-process")
                            .withVariable("auctionId", auction.getId())
                            .start();
                    return auction.getId();
                }
            });

        doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                auction.setAuthorized(true);
                processTask().complete();
                return null;
            }
        }).when(auctionService).authorizeAuction(anyString(), anyBoolean());

        when(auctionService.locateHighestBidId(anyLong())).thenReturn(1L);

        // Execute the test

        auctionService.createAuction(auction);

        assertThat(processInstance()).isStarted().isWaitingAt("authorizeAuction");
        assertThat(processVariable("auctionId")).exists().isDefined().asLong().isEqualTo(1);

        auctionService.authorizeAuction(processTask().getId(), true);

        assertThat(processInstance()).isWaitingAt("IntermediateCatchEvent_1");

        processJob().execute(); // TODO show here that the auction is still waiting and then fast forward to auction end and execute another time

        assertThat(processInstance()).isWaitingAt("UserTask_2");

        processTask().complete();

        assertThat(processInstance()).isFinished();

    }

}
