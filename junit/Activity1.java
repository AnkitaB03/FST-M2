package activities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class Activity1 {

    static ArrayList<String> list;

    @BeforeAll
    public static void setUp(){
        list = new ArrayList<String>();
        list.add("alpha");
        list.add("Beta");
    }

    @Test
    public void insertTest(){
        Assertions.assertEquals(2,list.size(),"expected size should be 2");
        list.add("gamma");
        Assertions.assertEquals(3,list.size(),"expected size should be 3");
        Assertions.assertEquals("alpha",list.get(0),"wrong element");
        Assertions.assertEquals("Beta",list.get(1),"wrong element");
        Assertions.assertEquals("gamma",list.get(2),"wrong element");

    }

    @Test
    public void replaceTest(){
        list.set(1, "gamma");
        Assertions.assertEquals(2,list.size(),"expected size should be 2");
        Assertions.assertEquals("alpha",list.get(0),"wrong element");
        Assertions.assertEquals("gamma",list.get(1),"wrong element");


    }

}
