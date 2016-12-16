package quarantine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;

public class Quarantine {

    private static final int NUMBER_OF_STINGS = 50;
    private static final String INPUT_FILENAME = "input.txt";
    private static final String OUTPUT_FILENAME = "output.txt";
    public static FirstBuf<String> fBuf = new FirstBuf<>(5);
    public static LinkedList<String> sBuf = new LinkedList<>();
    public static int numberOfValid = 0;
    public static HashMap<Integer, TimeIO> Log = new HashMap<>();
    final CyclicBarrier gate = new CyclicBarrier(3);

    public static void main(String[] args) {
        /*Step 1. Generation of input file and clearing output file*/
        InputFileGenerator fileGen = new InputFileGenerator(NUMBER_OF_STINGS, INPUT_FILENAME, OUTPUT_FILENAME);
        fileGen.start();

        /*Step 2. Start reading thread and start handling thread*/
        ReadThread read = new ReadThread(INPUT_FILENAME);
        read.setName("ReadThread");
        HandleThread handle = new HandleThread();
        handle.setName("HandleThread");
        read.start();
        handle.start();

//        while (handle.isAlive()) {
//        }
        synchronized (handle){
            try {
                handle.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (Log) {
            Iterator<Map.Entry<Integer, TimeIO>> itr = Log.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry<Integer, TimeIO> currentStr = itr.next();
                System.out.print(currentStr.getKey() + ": " + currentStr.getValue() + "\n");
            }
        }
    }
}
