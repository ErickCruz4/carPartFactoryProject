package testers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import data_structures.BasicHashFunction;
import data_structures.HashTableSC;
import interfaces.List;
import interfaces.Map;
import interfaces.Stack;
import main.CarPart;
import main.CarPartFactory;
import main.Order;
import main.PartMachine;

public class StudentTester {

    private Order order;
    private CarPart part;
    private CarPart part2;
    private CarPart part3;
    private PartMachine machine1;
    private CarPartFactory factory;
    
    @Nested
    @DisplayName("CarPart Tests")
    public class TestCarPart {
    	@Test
    	@DisplayName("Testing Car Part constructor 1.")
        public void test1() {
            part = new CarPart(10, "Transmission", 15.0, true);
            assertAll(
                () -> assertEquals(10, part.getId(), "Didn't return correct part id."),
                () -> assertEquals("Transmission", part.getName(), "Didn't return correct part name."),
                () -> assertEquals(15, part.getWeight(), "Didn't return correct weight for part."),
                () -> assertTrue(part.isDefective(), "Assigned incorrect defective status")
            );
        }
    	@Test
    	@DisplayName("Testing Car Part constructor 2.")
        public void test2() {
            part = new CarPart(142, "Motor", 154.0, false);
            assertAll(
                () -> assertEquals(142, part.getId(), "Didn't return correct part id."),
                () -> assertEquals("Motor", part.getName(), "Didn't return correct part name."),
                () -> assertEquals(154, part.getWeight(), "Didn't return correct weight for part."),
                () -> assertTrue(!part.isDefective(), "Assigned incorrect defective status")
            );
        }
    }
    @Nested
    @DisplayName("Order Tests")
    public class TestOrder {
    	@Test
    	@DisplayName("Testing Order constructor 1.")
        public void test1() {
    		Map<Integer, Integer> reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(1, 5);
            Order order = new Order(2, "ABC Company", reqParts, true);
            assertAll(
                () -> assertEquals(2, order.getId(), "Didn't return correct order id."),
                () -> assertEquals("ABC Company", order.getCustomerName(), "Didn't return correct customer name."),
                () -> assertEquals(1, order.getRequestedParts().size(), "Didn't return correct size for requested parts."),
                () -> assertEquals(5, order.getRequestedParts().get(1), "Didn't return correct count for requested part id=1."),
                () -> assertTrue(order.isFulfilled(), "Assigned incorrect fulfilled status")
            );
        }
    	@Test
    	@DisplayName("Testing Order constructor 2.")
        public void test2() {
    		Map<Integer, Integer> reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(5, 6);
            reqParts.put(2, 46);
            reqParts.put(9, 9);
            Order order = new Order(27, "SWA Company", reqParts, false);
            assertAll(
                () -> assertEquals(27, order.getId(), "Didn't return correct order id."),
                () -> assertEquals("SWA Company", order.getCustomerName(), "Didn't return correct customer name."),
                () -> assertEquals(3, order.getRequestedParts().size(), "Didn't return correct size for requested parts."),
                () -> assertEquals(6, order.getRequestedParts().get(5), "Didn't return correct count for requested part id=5."),
                () -> assertEquals(46, order.getRequestedParts().get(2), "Didn't return correct count for requested part id=2."),
                () -> assertEquals(9, order.getRequestedParts().get(9), "Didn't return correct count for requested part id=9."),
                () -> assertTrue(!order.isFulfilled(), "Assigned incorrect fulfilled status")
            );
        }
    }
    @Nested
    @DisplayName("PartMachine Tests")
    public class TestPartMachine {
        @Test
        @DisplayName("Testing construction of PartMachine")
        public void test1() {
            part = new CarPart(10, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 1, 1.2, 5);
            assertAll(
                () -> assertEquals(1, machine1.getId(), "Didn't return correct machine id."),
                () -> assertEquals("Transmission", machine1.getPart().getName(), "Didn't return correct part name."),
                () -> assertEquals(15.0, machine1.getPart().getWeight(), 1.7, "Failed to assign proper weight"),
                () -> assertTrue(part.isDefective(), "Assigned incorrect defective status")
            );
        }
        @Test
        @DisplayName("Testing timer of PartMachine right after initialization")
        public void test2() {
            part = new CarPart(10, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 1, 1.2,5);
            assertAll(
                () -> assertEquals(0, machine1.getTimer().front(), "Didn't start timer on 0."),
                () -> assertEquals(1, machine1.getTimer().size(), "Didn't make timer right size.")
            );
            for (int i = 0; i < machine1.getTimer().size(); i++) {
                if(machine1.getTimer().front() != i)
                    fail("Didn't assign correct initial timer values");
                machine1.getTimer().enqueue(machine1.getTimer().dequeue());
            }
            machine1 = new PartMachine(1, part, 4, 1.2,5);
            assertAll(
                () -> assertEquals(3, machine1.getTimer().front(), "Didn't start timer on period - 1."),
                () -> assertEquals(4, machine1.getTimer().size(), "Didn't make timer right size.")
            );
            for (int i = machine1.getTimer().size() - 1; i >= 0; i--) {
                if(machine1.getTimer().front() != i)
                    fail("Didn't assign correct initial timer values");
                machine1.getTimer().enqueue(machine1.getTimer().dequeue());
            }

        }
        @Test
        @DisplayName("Testing timer of PartMachine after calling tick method")
        public void test3() {
            part = new CarPart(10, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 5, 1.2,5);
            assertAll(
                () -> assertEquals(4, machine1.getTimer().front(), "Didn't start timer on 0."),
                () -> assertEquals(5, machine1.getTimer().size(), "Didn't make timer right size.")
            );
            for(int i = 4; i <=0; i++) {
                if(machine1.getTimer().front() != i)
                    fail("Timer has incorrect values");
                machine1.tickTimer();
            }

        }
        @Test
        @DisplayName("Testing conveyor belt of PartMachine when initialized")
        public void test4() {
            part = new CarPart(10, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 3, 1.2,5);
            assertAll(
                () -> assertEquals(null, machine1.getConveyorBelt().front(), "Didn't start with null"),
                () -> assertEquals(10, machine1.getConveyorBelt().size(), "Didn't make comveyor belt right size.")
            );
            for (int i = 0; i < 10; i++) {
                if(machine1.getConveyorBelt().front() != null)
                    fail("Didn't assign correct initial value");
                machine1.getConveyorBelt().enqueue(machine1.getConveyorBelt().dequeue());
            }
        }
        @Test
        @DisplayName("Testing conveyor belt of PartMachine when in production")
        public void test5() {
            part = new CarPart(9, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 3, 1.2, 5);
           
            //Moves conveyer belt 12 times. 2 ties to take into account it won't start producing until the 3 call
            // and 10 more calls to move it to the front.
            for (int i = 0; i < 12; i++) {
                machine1.produceCarPart();
            }
            for (int i = 0; i < 10; i++) {
                if (i % 3 == 0 && machine1.getConveyorBelt().front() == null)
                    fail("Didn't place products with proper spacing");
                else if(i % 3 != 0 && machine1.getConveyorBelt().front() != null)
                    fail("Didn't place products with proper spacing 2");

                machine1.getConveyorBelt().enqueue(machine1.getConveyorBelt().dequeue());
            }
        }
        @Test
        @DisplayName("Testing parts produced by PartMachine")
        public void test6() {
            part = new CarPart(9, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 1, 1.2, 5);
            for (int i = 0; i < 10; i++) {
                machine1.produceCarPart();
            }
            part = machine1.produceCarPart();
            part2 = machine1.produceCarPart();
            part3 = machine1.produceCarPart();
            
            if(part.getWeight() == 15.0 && part2.getWeight() == 15.0 && part3.getWeight() == 15.0)
                fail("Didn't make weight vary by weight error.");
            assertAll(
                () -> assertEquals(27, part.getId() + part2.getId() + part3.getId(), "Didn't assign correct id to parts."),
                () -> assertEquals(15.0, part.getWeight(), 1.2, "Weight isn't in the proper range"),
                () -> assertEquals(15.0, part2.getWeight(), 1.2, "Weight isn't in the proper range"),
                () -> assertEquals(15.0, part3.getWeight(), 1.2, "Weight isn't in the proper range"),
                () -> assertTrue(part.isDefective(), "First part would be defective with a 0.2 chance"),
                () -> assertTrue(!part2.isDefective(), "Second part would not be defective a 0.2 chance"),
                () -> assertTrue(!part3.isDefective(), "Third part would not be defective with a 0.2 chance"),
                () -> assertEquals("Transmission", part.getName(), "Didn't give correct name."),
                () -> assertEquals("Transmission", part2.getName(), "Didn't give correct name."),
                () -> assertEquals("Transmission", part3.getName(), "Didn't give correct name.")
            );
        }
        @Test
        @DisplayName("Testing defective parts produced by PartMachine")
        public void test7() {
            part = new CarPart(9, "Transmission", 15.0, true);
            machine1 = new PartMachine(1, part, 2, 1.2, 5);
            for (int i = 0; i < 11; i++) {
                machine1.produceCarPart();
            }
            part = machine1.produceCarPart();
            for (int i = 0; i < 9; i++) {
                machine1.produceCarPart();
            }
            part2 = machine1.produceCarPart();
             for (int i = 0; i < 9; i++) {
                machine1.produceCarPart();
            }
            part3 = machine1.produceCarPart();
            
            assertAll(
                
                () -> assertTrue(part.isDefective(), "First part would be defective with a 0.2 chance"),
                () -> assertTrue(part2.isDefective(), "Fifth part would be defective a 0.2 chance"),
                () -> assertTrue(part3.isDefective(), "Tenth part would be defective with a 0.2 chance")
            );
        }
    }
    @Nested
    @DisplayName("CarFactory Tests")
    public class TestCarPartFactory {
        

        @BeforeEach
        public void setUp() throws IOException {
            factory = new CarPartFactory("input/orders.csv", "input/parts.csv");

        }
        @Test
        @DisplayName("Testing the orders after factory creation")
        public void testOrders1() {
            List<Order> orders = factory.getOrders();
            order = orders.get(9);
            assertAll(
                () -> assertEquals(10, order.getId(), "Returned incorrect order id"),
                () -> assertEquals("David Brown", order.getCustomerName(), "Returned incorrect cutomer name"),
                () -> assertEquals(1, order.getRequestedParts().size(), "Has incorrect amount of ordered parts"),
                () -> assertEquals(7, order.getRequestedParts().get(9), "Has incorrect request count")
            );
            order = orders.get(79);
            assertAll(
                () -> assertEquals(80, order.getId(), "Returned incorrect order id"),
                () -> assertEquals("Zoey Ltd.", order.getCustomerName(), "Returned incorrect cutomer name"),
                () -> assertEquals(3, order.getRequestedParts().size(), "Has incorrect amount of ordered parts"),
                () -> assertEquals(1, order.getRequestedParts().get(1), "Has incorrect request count"),
                () -> assertEquals(10, order.getRequestedParts().get(6), "Has incorrect request count"),
                () -> assertEquals(8, order.getRequestedParts().get(5), "Has incorrect request count")
            );
        }
        
        @Test
        @DisplayName("Testing the machines generated in the constructor")
        public void testMachines() {
            List<PartMachine> machines = factory.getMachines();
            assertEquals(20, machines.size(), "Did not make 20 machines with the give input files.");
        }

        @Test
        @DisplayName("Testing inventory on factory creation")
        public void testInventory() {
            Map<Integer, List<CarPart>> inventory = factory.getInventory();
            for (List<CarPart> L : inventory.getValues()) {
                if (!L.isEmpty())
                    fail("Failed initially make empty Lists for inventory.");
            }
            for (PartMachine partMachine : factory.getMachines()) {
                if(inventory.get(partMachine.getPart().getId()) == null)
                    fail("Not all parts are included in the inventory.");
            }
        }
        @Test
        @DisplayName("Testing catalog on factory creation")
        public void testCatalog() {
            Map<Integer, CarPart> catalog = factory.getPartCatalog();
            for (PartMachine partMachine : factory.getMachines()) {
                part = partMachine.getPart();
                if(catalog.get(part.getId()) == null)
                    fail("Not all parts are included in the catalog.");
                else if(!catalog.get(part.getId()).getName().equals(part.getName()) 
                        && catalog.get(part.getId()).getId() != part.getId())
                    fail("Didn't add correct part to correct id.");
            }
        }
        
        
        @Test
        @DisplayName("Testing production bin after run factory")
        public void testProductionBin1() {
            factory.runFactory(1, 30);
            assertEquals(0, factory.getProductionBin().size(), "Should be empty after production");
        }
        @Test
        @DisplayName("Testing production bin after store inventory")
        public void testProductionBin2() {
            Stack<CarPart> bin = factory.getProductionBin();
            bin.push(new CarPart(1, "Test Part1", 10, false));
            bin.push(new CarPart(1, "Test Part1", 10, true));
            bin.push(new CarPart(2, "Test Part1", 10, false));
            bin.push(new CarPart(2, "Test Part1", 10, false));
            bin.push(new CarPart(3, "Test Part1", 10, false));
            bin.push(new CarPart(11, "Test Part1", 10, false));
            bin.push(new CarPart(4, "Test Part1", 10, true));
            factory.storeInInventory();
            assertAll(
                () -> assertEquals(1, factory.getInventory().get(1).size(), "Didn't add one part with id 1, don't add defectives"),
                () -> assertEquals(2, factory.getInventory().get(2).size(), "Didn't add two parts with id 2"),
                () -> assertEquals(1, factory.getInventory().get(3).size(), "Didn't add one part with id 3"),
                () -> assertEquals(1, factory.getInventory().get(11).size(), "Didn't add one part with id 11"),
                () -> assertEquals(0, factory.getInventory().get(4).size(), "Added defective part.")
            );    
        }
       
        @Test
        @DisplayName("Testing process orders")
        public void testProcessOrders() {
            factory.getOrders().clear();
            Map<Integer, Integer> reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(1, 5);
            factory.getOrders().add(new Order(1, "Ben", reqParts, false));
            reqParts = new HashTableSC<>(2, new BasicHashFunction());
            reqParts.put(2, 2);
            reqParts.put(1, 7);
            reqParts.put(5, 1);
            factory.getOrders().add(new Order(2, "Jerry", reqParts, false));
            reqParts = new HashTableSC<>(2, new BasicHashFunction());
            reqParts.put(20, 15);
            reqParts.put(1, 1);
            factory.getOrders().add(new Order(3, "Louis", reqParts, false));
            reqParts = new HashTableSC<>(1, new BasicHashFunction());
            reqParts.put(1, 5);
            reqParts.put(13, 1);
            factory.getOrders().add(new Order(4, "John", reqParts, false));
            factory.runFactory(1, 30);
            
            assertAll(
                () -> assertTrue(factory.getOrders().get(0).isFulfilled(), "There should be enough parts to fulfil this order"),
                () -> assertTrue(factory.getOrders().get(1).isFulfilled(), "There should be enough parts to fulfil this order"),
                () -> assertTrue(!factory.getOrders().get(2).isFulfilled(), "There should be enough parts to fulfil this order"),
                () -> assertTrue(factory.getOrders().get(3).isFulfilled(), "There should be enough parts to fulfil this order"),
                () -> assertEquals(7, factory.getInventory().get(1).size(), "Has wrong inventory amount after fulfilling orders."),
                () -> assertEquals(10, factory.getInventory().get(2).size(), "Has wrong inventory amount after fulfilling orders."),
                () -> assertEquals(3, factory.getInventory().get(5).size(), "Has wrong inventory amount after fulfilling orders."),
                () -> assertEquals(0, factory.getInventory().get(13).size(), "Has wrong inventory amount after fulfilling orders.")
            );    
        }
    }
    

}
