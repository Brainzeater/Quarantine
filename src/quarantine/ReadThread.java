package quarantine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadThread extends Thread {

    private FileReader myFileReader;
    private BufferedReader myBufferedReader;
    private String fileName;
    /*String number's counter*/
    private int counter;

    public ReadThread(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            myFileReader = new FileReader(this.fileName);
            myBufferedReader = new BufferedReader(myFileReader);

            counter = 0;
            do {
                if (!Thread.interrupted())    //Проверка прерывания
                {
                    try {
                        synchronized (Quarantine.fBuf) {
                            /*Reading by two lines*/
                            for (int i = 0; i < 2; i++) {
                                String line = myBufferedReader.readLine();
                                if (line != null) {
                                    /*Adding line to first buffer (adding its number as last char)*/
                                    if (counter < 10) {
                                        Quarantine.fBuf.add(line + "0" + counter);
                                    } else {
                                        Quarantine.fBuf.add(line + counter);
                                    }
                                    /*Note the time of string's income*/
                                    synchronized (Quarantine.Log) {
                                        Quarantine.Log.put(counter, new TimeIO(System.currentTimeMillis()));
                                    }
                                } else {
//                                    System.out.println(("I am done " + Thread.currentThread().getName()));
                                    Thread.currentThread().interrupt();
                                }

                                counter++;
                            }
//                            System.out.println("[[[[    ");
//                            for(String str : Quarantine.fBuf){
//                                System.out.println(str);
//                            }
//                            System.out.println("     ]]]]");
                        }
                    } catch (IOException ioe) {
                    }
                } else {
                    return;        //Завершение потока
                }
                try {
                    /*DON"T TOUCH IT*/
                    Thread.sleep(20);        //Приостановка потока на 1 сек.
                } catch (InterruptedException e) {
                    return;    //Завершение потока после прерывания
                }
            }
            while (true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                myBufferedReader.close();
                myFileReader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
