import com.abramova.test.MyHashMap;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by alina on 24.02.15.
 */

public class HashMapTest {

    /**
    * Tests putting and getting simple entries and returning old value, when put entry with same key.
    */
    @Test
    public  void testInput(){
        final MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(1,891);
        myHashMap.put(49, 31);

        assertEquals(31,myHashMap.get(49),0);
        assertEquals(891,myHashMap.get(1),0);
        assertEquals(31,myHashMap.put(49,12),0);
    }
    /**
    * Tests double putting entries with same keys
    */
    @Test
    public void testDoublePut(){
        final MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(1,121);
        myHashMap.put(2,144);

        assertEquals(121,myHashMap.get(1),0);
        assertEquals(144,myHashMap.get(2),0);

        myHashMap.put(1,120);
        myHashMap.put(2,143);

        assertEquals(120,myHashMap.get(1),0);
        assertEquals(143,myHashMap.get(2),0);

    }
    /**
    * Tests getting element if the specified key not found.
    */
    @Test(expected = NoSuchElementException.class)
    public void testException(){
        final MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(2,2);
        myHashMap.get(1);
    }

     /**
     * Tests resizing map when putting elements.
     */
    @Test
    public void testSize(){
        final MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(11,121);
        myHashMap.put(12,144);
        assertEquals(2,myHashMap.size(),0);
        myHashMap.put(12,169);
        assertEquals(2,myHashMap.size(),0);
        myHashMap.put(3,169);
        assertEquals(3,myHashMap.size(),0);
    }

    /**
     * Tests map with negative keys and values.
     */
    @Test
    public void testNegative(){
        final MyHashMap myHashMap = new MyHashMap();
        myHashMap.put(-1,12);
        assertEquals(12,myHashMap.get(-1),0);
        myHashMap.put(12,-1);
        assertEquals(-1,myHashMap.get(12),0);
    }

    /**
     * Tests execution of the program with big number elements.
     * Output time of the execution.
     */
    @Test
    public void testBigNumber(){
        final MyHashMap myHashMap = new MyHashMap();
        long t0 = System.currentTimeMillis();
        int maxI = 100000;
        for (int i = 0 ; i < maxI; i++){
            myHashMap.put(i,i*100);
        }
        long t1 = System.currentTimeMillis();
        System.out.println("Added "+maxI+" elements for "+(t1-t0)+" ms");
        for (int i = 0 ; i< maxI;i++ ){
            assertEquals(i*100,myHashMap.get(i),0);
        }

    }

    /**
     * Tests correctness overriding methods hashCode() and equals().
     * Checks equivalence relation for equals().
     */
    @Test
    public void testEquals(){
        final MyHashMap myHashMap1 = new MyHashMap();
        final MyHashMap myHashMap2 = new MyHashMap();
        final MyHashMap myHashMap3 = new MyHashMap();

        myHashMap1.put(1,1);
        myHashMap2.put(1, 1);
        myHashMap3.put(1, 1);


        assertEquals(myHashMap1.hashCode(),myHashMap2.hashCode(),0);

        assertTrue(myHashMap1.equals(myHashMap1));

        assertTrue(myHashMap2.equals(myHashMap1));


        assertTrue(myHashMap1.equals(myHashMap2));
        assertTrue(myHashMap1.equals(myHashMap3));
        assertTrue(myHashMap3.equals(myHashMap2));


        assertFalse(myHashMap1.equals(null));
    }


    /**
     * Test hashCode() and equals() if arises collision.
     */
    @Test
    public void testCollision(){
        final MyHashMap myHashMap1 = new MyHashMap();
        final MyHashMap myHashMap2 = new MyHashMap();

        myHashMap1.put(1,2);
        myHashMap2.put(1,1);

        assertEquals(myHashMap1.hashCode(),myHashMap2.hashCode());
        assertFalse(myHashMap1.equals(myHashMap2));
    }






}
