package quarantine;

import java.io.*;
import java.util.*;

public class InputFileGenerator {

    private static final int MAX = 126;
    private static final int MIN = 32;
    private static final int STRING_MAX_LENGTH = 4000;

    private FileWriter myFileWriter;
    private BufferedWriter myBufferedWriter;
    private int numberOfStrings;
    private String fileName;
    private String outputFile;

    public InputFileGenerator(int numberOfStrings, String fileName, String outputFile){
        this.numberOfStrings = numberOfStrings;
        this.fileName = fileName;
        this.outputFile = outputFile;
    }

    public void start() {
        writeFile(generateRandomStrings(numberOfStrings), fileName);
        clearOutputFile(outputFile);

    }

    private void writeFile(ArrayList<String> stringArray, String fileName) {
        try {
            myFileWriter = new FileWriter(fileName);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            for (String str : stringArray) {
                myBufferedWriter.write(str + "\r\n");
            }
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

    private ArrayList<String> generateRandomStrings(int length) {
        ArrayList<String> generatedStrings = new ArrayList<>();
        /*Here seed is set*/
        Random random = new Random();
        int randomCharNumber;
        int i = 0;
        while (i < length) {
            int j = 0;
            String currentStr = "";
            while (j < STRING_MAX_LENGTH) {
                randomCharNumber = random.nextInt(MAX - MIN + 1) + MIN;
                currentStr += (char) randomCharNumber;
                j++;
            }
            generatedStrings.add(currentStr);
            i++;
        }
        return generatedStrings;
    }
    private void clearOutputFile(String fileName) {
        try {
            myFileWriter = new FileWriter(fileName);
            myBufferedWriter = new BufferedWriter(myFileWriter);
            myBufferedWriter.write("");
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
