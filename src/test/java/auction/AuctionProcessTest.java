package auction;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.service.AuctionService;
import com.camunda.showcase.auction.service.TwitterPublishService;
import com.plexiti.activiti.test.fluent.FluentBpmnTestCase;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.mock.Mocks;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;

import static com.plexiti.activiti.test.fluent.FluentBpmnTests.*;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 * @author Rafael Cordones <rafael.cordones@plexiti.com>
 */
public class AuctionProcessTest extends FluentBpmnTestCase {

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
        auction.setEndTime(new Date());

        Mocks.register("auction", auction);

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

        assertThat(processInstance()).isStarted();
        assertThat(processInstance()).isWaitingAt("authorizeAuction");
        assertThat(processVariable("auctionId")).exists().isDefined().asLong().isEqualTo(1);

        auctionService.authorizeAuction(processTask().getId(), true);

        assertThat(processInstance()).isWaitingAt("IntermediateCatchEvent_1");

        processJob().execute();

        assertThat(processInstance()).isWaitingAt("UserTask_2");

        processTask().complete();

        assertThat(processInstance()).isFinished();

    }

}
