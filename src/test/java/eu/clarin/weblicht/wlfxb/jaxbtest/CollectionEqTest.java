/**
 *
 */
package eu.clarin.weblicht.wlfxb.jaxbtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yana Panchenko
 *
 */
public class CollectionEqTest {

    public static void main(String[] args) {

        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(2);
        list1.add(1);
        list1.add(3);
        Collections.sort(list1);

        List<Integer> list2 = new ArrayList<Integer>();
        list2.add(2);
        list2.add(1);
        list2.add(3);
        Collections.sort(list2);
        list2.add(6);
        list2.remove(list2.size() - 1);

        System.out.println(list1.equals(list2));
    }
}
