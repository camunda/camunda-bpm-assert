package auction;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.service.AuctionService;
import com.plexiti.activiti.test.fluent.ActivitiFluentTest;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.plexiti.activiti.test.fluent.Assertions.*;

import static org.mockito.Mockito.when;

public class AuctionProcessTest extends ActivitiFluentTest {

    @Mock
    public AuctionService auctionService;

    @Test
    @Deployment(resources = { "com/camunda/showcase/auction/auction-process.bpmn" })
    public void testProcessDeployment() {

       assertThat(processDefinition("Auction Process")).isDeployed();
    }

    @Ignore
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

                     Map<String, Object> processVariables = new HashMap<String, Object>();
                     processVariables.put("auctionId", auction.getId());
                     // start the process
                     startProcessInstanceByKey("auction-process", processVariables);

                     return auction.getId();
                 }
             });

        /*
         * Start the test
         */
         auctionService.createAuction(auction);

         /*
          * The process should have been started from the service implementation
          */
         assertThat(process().execution()).isStarted();

         // The process variable "auctionId" must exist
         assertThat(process().variable("auctionId"))
                 .exists()
                 .isDefined()
                 // The ID of the auction should also have been set!
                 .asLong().isEqualTo(1);

         assertThat(process().execution()).isWaitingAt("authorizeAuction");
         Task authorizeAuctionTask = findTaskByTaskId("authorizeAuction");
         auctionService.authorizeAuction(authorizeAuctionTask.getId(), true);

         // end complete task 1 ///////////////

         // wait for auction end //////////////

         // wait for 6 seconds
         Thread.sleep(6000);

         // end wait for auction end //////////

         // complete task 2 ///////////////////
         ProcessInstance pi = process().getActualProcessInstance();
         List<Task> tasksAfterTimer = taskService.createTaskQuery().processInstanceId(pi.getId()).list();
         Assert.assertEquals(1,tasksAfterTimer.size());

         Task billingAndShippingTask = tasksAfterTimer.get(0);

         // complete task
         taskService.complete(billingAndShippingTask.getId());

         // end complete task 2 ///////////////

         // check if process instance is really ended

         long runningInstancesCount = runtimeService
                 .createProcessInstanceQuery()
                 .processInstanceId(pi.getId())
                 .count();

         Assert.assertEquals(0,runningInstancesCount);
     }
}
