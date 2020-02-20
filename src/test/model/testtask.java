package model;

import model.exceptions.Invalidprogressexception;
import model.exceptions.NegativeInputException;
import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskPhase3 {
    // TODO: design tests for new behaviour added to Task class
    Task testtask1;
    Task testtask2;
    Task testtask3;
    Task testtask4;
    Task testtask5;
    Tag testtag1;
    Tag testtag3;

    @BeforeEach
    public void setuptests(){
        testtask1 = new Task("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask2 = new Task("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask3 = new Task("Don't register for course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask4 = new Task("Don't register for course. ## cpsc210; ##cpsc310;  tomorrow; important; urgent; in progress");
        testtask5 = new Task("Don't register for course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtag1 = new Tag("testtag");
        testtag3 = new Tag("faketesttag");
    }

    @Test
    public void testequaltasks(){

        assertEquals(testtask1, testtask2);
    }

    @Test
    public void testdifferentdescriptions(){
        assertFalse(testtask2.equals(testtask3));
    }

    @Test
    public void testextratags(){
        assertFalse(testtask3.equals(testtask4));
    }

    @Test
    public void testaddtask(){
        testtask1.addTag(testtag1);
        assertEquals(testtask1.getTags().size(), 2);
        assertEquals(testtag1.getTasks().size(), 1);
    }
    @Test
    public void testremovetask(){
        testtask1.addTag(testtag1);
        testtask1.addTag(testtag3);
        testtask1.removeTag(testtag1);
        assertEquals(testtask1.getTags().size(),2);
        assertEquals(testtag1.getTasks().size(),0);
        assertEquals(testtag3.getTasks().size(),1);
    }
    @Test
    public void testgetstatus() {
        assertEquals(testtask1.getStatus(), testtask2.getStatus());
    }
    @Test
    public void testsetstatus() {
        testtask1.setStatus(Status.IN_PROGRESS);
        assertEquals(testtask1.getStatus(), Status.IN_PROGRESS);
    }
    @Test
    public void testgetdescription() {
        assertEquals("Register for the course. ", testtask1.getDescription());
    }
    @Test
    public void testsetESTtimecomplete(){
        testtask1.setEstimatedTimeToComplete(1);
        assertEquals(1,testtask1.getEstimatedTimeToComplete());
    }
    @Test
    public void testsetProgress(){
        testtask2.setProgress(10);
        assertEquals(testtask2.getProgress(), 10);
    }
    @Test
    public void testNegativeinputexception() {
        try {
            testtask1.setEstimatedTimeToComplete(-1);
            fail("Shoulda thrown ");
        } catch (NegativeInputException e){
            System.out.println("Caught the trap");
        }
    }
    @Test
    public void testtaskconstructor(){
        try {
            testtask1.setStatus(null);
            fail("Cant reach here plzzz");
        } catch (NullArgumentException e) {
            System.out.println("Good Catch");
        }
    }
    @Test
    public void testdescription(){
        try {
            testtask1.setDescription(null);
            fail("Shouldnt have reached");
        } catch (EmptyStringException e) {
            System.out.println("Caught the exception");
        }
    }
    @Test
    public void testremovetag() {

        testtask1.addTag(new Tag("CPSC310"));
        testtask1.removeTag("CPSC310");
        assertEquals(testtask1.getTags().size(), 1 );
    }
    @Test
    public void testcontainstag(){
        assertTrue(testtask1.containsTag("cpsc210"));
    }
    @Test
    public void testcontainstagexception(){
        try {
            testtask1.containsTag("");
            fail("Shoulda thorwn excpt");
        } catch (EmptyStringException e) {
            System.out.println("Nicely caught expt");
        }
    }
    @Test
    public void testcontainstagexcept(){
        Tag testTag = null;
        try{
            testtask1.containsTag(testTag);
            fail("Shoulda caught");
        } catch (NullArgumentException e){
            System.out.println("good catch mate");
        }
    }
    @Test
    public void testequalsmeth(){
        assertTrue(testtask1.equals(testtask1));
    }
    @Test
    public void testequalsmethfalse(){
        assertFalse(testtask1.equals(testtag1));
    }
    @Test
    public void testsetprogexcpt(){
        try {
            testtask1.setProgress(-5);
            fail("Trash");
        }catch (Invalidprogressexception e) {
            System.out.println("CAUGHT FOOL");

        }
    }
    @Test
    public void testesttimecompletegetter(){
        testtask1.setEstimatedTimeToComplete(1);
        assertEquals(testtask1.getestimatedcompletiontime(),1);
    }


}
