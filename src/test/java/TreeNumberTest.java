import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TreeNumberTest {

    @Test
    public void test01(){
        List<Object> t = Arrays.asList("Test", "Test");

        List<Object> t2 = Arrays.asList("1", "2", "3", Arrays.asList("4", "5"), "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16","17", "18", "19", "20");

        List<Object> t3 = Arrays.asList("Test", Arrays.asList("Test", "Test", "Test"), "Test");

        //System.out.println(new Tree().getTreeFormat(t, 0));
        //System.out.println(new Tree().getTreeFormat(t2, 0));

        System.out.println(new Tree().getStump(t2));
        new Tree().displayTree(t2);
    }

    @Test
    public void NestingTest(){
        List<Object> t = Arrays.asList("Erste Ebene", Arrays.asList("Zweite Ebene", "Zweite Ebene"), "Erste Ebene", Arrays.asList("Zweite Ebene", Arrays.asList("Dritte Ebene", Arrays.asList("Vierte Ebene"))), "Erste Ebene");
        printRoundTrip(t);
    }

    private void printRoundTrip(List<Object> tree){
        Tree t = new Tree();
        System.out.println(tree.toString());
        System.out.println(t.getStump(tree));
        System.out.println(t.displayTree(tree));
    }
}
