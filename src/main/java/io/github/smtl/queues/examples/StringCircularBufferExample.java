package io.github.smtl.queues.examples;

import io.github.smtl.queues.CircularBuffer;
import java.util.concurrent.TimeUnit;

public class StringCircularBufferExample {

    public static void main(String[] args) throws InterruptedException {

        final int inputDataCount = 400;
        final String[] inputData = new String[inputDataCount];
        final int bufferLength = 100;
        CircularBuffer<String> StringCircularBuffer = new CircularBuffer<String>(bufferLength);


        for(int i = 0; i<inputDataCount; i++){
            inputData[i] = String.valueOf(Math.floor(Math.random() * 1000));
        }

        System.out.println("Input items: ");
        for(String item : inputData) {
            System.out.print(item+ ", ");
        }

        System.out.println();
        System.out.println("Items retrieved from buffer: ");
        /*
            Every 10 seconds an adder will try add 5 items
            and a reader will take 2 items. The adder will try
            to put a full batchSize into the buffer but will not
            be able to as the reader is slower.
         */
        int inputIndex = 0;
        final int batchSize = 50;
        int inputBatch;
        String itemTaken = null;
        while(inputIndex < inputData.length || itemTaken != null) {
            inputBatch = inputIndex + batchSize;
            // Attempt to add a batch to the buffer
            while ((inputIndex < inputData.length) &&
                    (inputIndex < inputBatch) &&
                    StringCircularBuffer.put(inputData[inputIndex])) {
                inputIndex++;
            }
            // "Reader" takes a number items
            for(int i = 0; i<75; i++){
                itemTaken = StringCircularBuffer.take();
                if(itemTaken != null) System.out.print(itemTaken + ", ");
            }


            TimeUnit.SECONDS.sleep(10);
        }
    }
}
