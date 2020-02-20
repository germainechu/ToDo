package model;

import model.exceptions.Invalidprogressexception;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class testproject {
    // TODO: design tests for new behaviour added to Task class
    Task testtask1;
    Task testtask2;
    Task testtask3;
    Task testtask4;
    Task testtask5;
    Tag testtag1;
    Tag testtag3;
    Project testproject1;
    Project testproject2;

    @BeforeEach
    public void setuptests(){
        testtask1 = new Task("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask2 = new Task("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask3 = new Task("Don't register for course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtask4 = new Task("Don't register for course. ## cpsc210; ##cpsc310;  tomorrow; important; urgent; in progress");
        testtask5 = new Task("Don't register for course. ## cpsc210; tomorrow; important; urgent; in progress");
        testtag1 = new Tag("testtag");
        testtag3 = new Tag("faketesttag");
        testproject1 = new Project("testing project stuff");
        testproject2 = new Project("testing project stuff");
    }

    @Test
    public void testprojectconstructor(){
        Project testproject3 = new Project("hello");
        assertEquals(testproject3.getDescription(),"hello");
        testproject3.add(testtask1);
        assertEquals(testproject3.getNumberOfTasks(), 1);


    }

    @Test
    public void testaddexception(){
        try {
            testproject1.add(null);
            fail("DONT GET HERE ");
        } catch (NullArgumentException e){
            System.out.println("GOOD CATCH");
    }

    }
    @Test
    public void testremove(){
        testproject1.add(testtask1);
        testproject1.add(testtask4);
        testproject1.remove(testtask1);
        assertEquals(testproject1.getNumberOfTasks(),1);

    }
    @Test
    public void testremoveexcpt(){
        try {
            testproject1.remove(null);
            fail("SUPPOSED TO CATCH");
        } catch (NullArgumentException e){
            System.out.println("GOOT");
        }
    }
    @Test
    public void testesttimecomplete() {
        try {
            testtask1.setEstimatedTimeToComplete(1);
            testtask4.setEstimatedTimeToComplete(2);
            testproject1.add(testtask1);
            testproject1.add(testtask4);
            assertEquals(testproject1.getEstimatedTimeToComplete(), 3);
        } catch (NegativeInputException e) {
            System.out.println("WHOOPS NOT SUPPOSED TO CATCH");
        }
    }
    @Test
    public void testgettasksexcpt(){
        try{
            testproject1.getTasks();
        } catch (UnsupportedOperationException e) {
            System.out.println("GOOD CATCH ");
        }
    }
    @Test
    public void testgetprogress(){
        try {
            assertEquals(testproject1.getProgress(), 0);
            testtask1.setProgress(10);
            testtask4.setProgress(20);
            testtask3.setProgress(-1);
            testproject1.add(testtask1);
            testproject1.add(testtask4);
            assertEquals(testproject1.getProgress(), 15);
            testproject1.add(testtask3);
            fail("Should catch exception");
        } catch (Invalidprogressexception e){
            System.out.println("NOT CATCHING PLZ");
        }
    }
    @Test
    public void iscompleted (){
        testtask1.setProgress(100);
        testproject1.add(testtask1);
        assertTrue(testproject1.isCompleted());
    }
    @Test
    public void testcontainsexcept() {
        try {
            testproject1.contains(null);
            fail("NOOOO");
        } catch (NullArgumentException e) {
            System.out.println("NICEEE");
        }

    }
    @Test
    public void testequalsmeth(){
        assertTrue(testproject1.equals(testproject1));
        assertFalse(testproject1.equals(testtask1));
        assertTrue(testproject1.equals(testproject2));


    }
    @Test
    public void testiterator(){
        try {
            Task task1 = new Task("task1");
            Task task2 = new Task("task2");
            Task task3 = new Task("task3");
            Task task4 = new Task("task4");
            Task task5 = new Task("task5");
            task1.setPriority(new Priority(1));
            task2.setPriority(new Priority(2));
            task3.setPriority(new Priority(3));
            task4.setPriority(new Priority(3));
            task5.setPriority(new Priority(4));
            testproject1.add(task1);
            testproject1.add(task2);
            testproject1.add(task3);
            testproject1.add(task4);
            testproject1.add(task5);
            Iterator testIterator = testproject1.iterator();
            assertEquals(testIterator.next(), task1);
            assertEquals(testIterator.next(), task2);
            assertEquals(testIterator.next(), task3);
            assertEquals(testIterator.next(), task4);
            assertEquals(testIterator.next(), task5);
        } catch (NoSuchElementException e) {
            System.out.println("Dont Catch");
        }

    }


}
