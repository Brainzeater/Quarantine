package quarantine;

import quarantine.modules.DataTypeModule;
import quarantine.modules.LogModule;
import quarantine.modules.ValidValuesModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class HandleThread extends Thread {
    private static final String FILENAME = "output.txt";
    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;

    @Override
    public void run() {
//        try {
//            /*Delay*/
//            Thread.sleep(20);
//        } catch (InterruptedException ie) {
//
//        }
        do {
            if (!Thread.interrupted())    //Проверка прерывания
            {
                /*To recognize type of incoming data*/
                DataTypeModule dTMod = new DataTypeModule();
                /*To sort valid values*/
                ValidValuesModule vVMod = new ValidValuesModule();
                /**/
                LogModule lMod = new LogModule();
                String currentString;
                synchronized (Quarantine.fBuf) {
                    try {
                        currentString = Quarantine.fBuf.pollFirst();
                        /*Keep the number of string*/
                        /*Ooops! Remember, if you would take numbers as valid value, then
                        * you should cut this strings last two chars*/
                        String strNumber = currentString.substring(currentString.length() - 2);
                        LinkedList<Integer> validNumbers = dTMod.handleString(currentString);
                        System.out.println(validNumbers);
                        System.out.println(currentString);
                        currentString = vVMod.handleString(currentString, validNumbers);
                        System.out.println(currentString);
                        /*Add the number of string*/
                        currentString += strNumber;
                        lMod.handleString(currentString, System.currentTimeMillis());
                        Quarantine.sBuf.add(currentString);
                        writeFile(Quarantine.sBuf.pollFirst(), FILENAME);
                        /*When the file is over*/
                    } catch (NullPointerException npe) {
                        System.out.println("I am done " + Thread.currentThread().getName());
                        Thread.currentThread().interrupt();
                        synchronized (this){
                            this.notify();
                        }
                    }
                }

            } else
                return;        //Завершение потока
            try {
                Thread.sleep(10);        //Приостановка потока
            } catch (InterruptedException e) {
                return;    //Завершение потока после прерывания
            }
        }
        while (true);
    }

    private void writeFile(String str, String fileName) {
        try {
            myFileWriter = new FileWriter(fileName, true);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            myBufferedWriter.write(str + "\r\n");
//            myBufferedWriter.newLine();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                myBufferedWriter.flush();
                myBufferedWriter.close();
                myFileWriter.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


}
