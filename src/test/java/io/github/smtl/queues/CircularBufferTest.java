package io.github.smtl.queues;

import org.junit.Test;
import static org.junit.Assert.*;

public class CircularBufferTest {

    private CircularBuffer<Integer> intCircularBuffer;
    private CircularBuffer<String> strCircularBuffer;

    public CircularBufferTest(){
        intCircularBuffer  = new CircularBuffer<Integer>(2);
        strCircularBuffer = new CircularBuffer<String>(2);
    }

    @Test
    public void shouldAddAnItemOfInteger(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.put(2));

        assertTrue(strCircularBuffer.put("1"));
        assertTrue(strCircularBuffer.put("2"));
    }

    @Test
    public void shouldNotAddItemWhenFull(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.put(2));
        assertFalse(intCircularBuffer.put(3));

        assertTrue(strCircularBuffer.put("1"));
        assertTrue(strCircularBuffer.put("2"));
        assertFalse(strCircularBuffer.put("3"));
    }

    @Test
    public void shouldTakeOldestItem(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.put(2));

        assertTrue(intCircularBuffer.take().equals(1));
    }

    @Test
    public void shouldTakeNextItem(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.put(2));

        assertTrue(intCircularBuffer.take().equals(1));
        assertTrue(intCircularBuffer.take().equals(2));
    }

    @Test
    public void shouldRemoveTakenItems(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.put(2));

        assertTrue(intCircularBuffer.take().equals(1));
        assertTrue(intCircularBuffer.take().equals(2));

        assertNull(intCircularBuffer.take());
    }

    @Test
    public void shouldBeAbleToAddMoreItemsAfterTake(){
        assertTrue(intCircularBuffer.put(1));
        assertTrue(intCircularBuffer.take().equals(1));

        assertTrue(intCircularBuffer.put(2));
        assertTrue(intCircularBuffer.put(3));

        assertTrue(intCircularBuffer.take().equals(2));
        assertTrue(intCircularBuffer.take().equals(3));

        assertTrue(intCircularBuffer.put(4));
        assertTrue(intCircularBuffer.put(5));

        assertTrue(intCircularBuffer.take().equals(4));
    }
}
