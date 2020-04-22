package io.github.smtl.queues;

/*
    A circular buffer uses a single fixed size buffer as if it were connected end to end.
    Works well as for a FIFO approach
    This implementation does not allow data in the buffer to be overwritten.
 */
public class CircularBuffer<T> {
    private final T[] buffer;
    private int readCursor = 0;
    private int writeCursor = 0;

    public CircularBuffer(int size){
        this.buffer = (T[]) new Object[size];
    }

    public boolean put(T item){
        // An existing item in the buffer should not be overwritten
        if(buffer[writeCursor] != null) {
            return false;
        }

        buffer[writeCursor] = item;
        writeCursor = next(writeCursor);
        return true;
    }

    public T take(){
        final T itemToGive = buffer[readCursor];

        if (itemToGive != null) {
            // after taking an item remove it from the buffer
            buffer[readCursor] = null;
            readCursor = next(readCursor);
        }

        return itemToGive;
    }

    private int next(int cursorIndex){
        return (cursorIndex+1) % buffer.length;
    }
}
