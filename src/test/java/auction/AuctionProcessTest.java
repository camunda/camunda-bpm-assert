package auction;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.service.AuctionService;
import com.plexiti.activiti.test.fluent.FluentBpmnTestCase;
import com.plexiti.activiti.test.fluent.engine.FluentBpmnProcessInstanceImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.List;

import static com.plexiti.activiti.test.fluent.FluentBpmnTests.*;

import static org.mockito.Mockito.*;

/*
 * @author Nico Rehwaldt <nico.rehwaldt@camunda.com>
 */
public class AuctionProcessTest extends FluentBpmnTestCase {

    @Mock
    public AuctionService auctionService;

    /*
     * Added since some of the processInstance expressions use ${auction. ...}
     */
    @Mock(name = "auction")
    public Auction theAuction = new Auction("Auction name", "Auction description", new Date());

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testProcessDeployment() {

       assertThat(processDefinition("Auction Process")).isDeployed();
    }

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testWalkThroughProcess() throws Exception {

        /*
         * Set up the test fixtures and expectations
         */
        Auction auction = new Auction("Cheap Ferrari!", "Ferrari Testarossa on sale!", new Date());

        /*
         * Set up the expectations for the auctionService using Mockito
         */
        when(auctionService.createAuction(auction))
             .thenAnswer(new Answer() {
                 public Object answer(InvocationOnMock invocation) {
                     Auction auction = (Auction) invocation.getArguments()[0];

                     // Assign an id to the auction
                     auction.setId(new Long(1));

                     // start the processInstance
                     newProcessInstance("auction-process")
                          .withVariable("auctionId", auction.getId())
                     .start();

                     return auction.getId();
                 }
             });

        /*
         * Start the test
         */
         auctionService.createAuction(auction);

         /*
          * The processInstance should have been started from the service implementation
          */
         assertThat(processExecution()).isStarted();

         // The processInstance processVariable "auctionId" must exist
         assertThat(processVariable("auctionId"))
                 .exists()
                 .isDefined()
                 // The ID of the auction should also have been set!
                 .asLong().isEqualTo(1);

         assertThat(processExecution()).isWaitingAt("authorizeAuction");
//         Task authorizeAuctionTask = findTaskByTaskId("authorizeAuction");
         auctionService.authorizeAuction(processTask().getId(), true);

         // end complete processTask 1 ///////////////

         // wait for auction end //////////////

         // wait for 6 seconds
         Thread.sleep(6000);

         // end wait for auction end //////////

         // complete processTask 2 ///////////////////
         ProcessInstance pi = processInstance().getDelegate();
         List<Task> tasksAfterTimer = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
         Assert.assertEquals(1,tasksAfterTimer.size());

         Task billingAndShippingTask = tasksAfterTimer.get(0);

         // complete processTask
         taskService.complete(billingAndShippingTask.getId());

         // end complete processTask 2 ///////////////

         // check if processInstance instance is really ended

         long runningInstancesCount = runtimeService
                 .createProcessInstanceQuery()
                 .processInstanceId(pi.getId())
                 .count();

         Assert.assertEquals(0,runningInstancesCount);
     }
}
