package quarantine.modules;

import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class DataTypeModule {
    private static final int SPACE = 32;
    private static final int SYMB_FIRST_START = 33;
    private static final int NUM_START = 48;
    private static final int SYMB_SECOND_START = 58;
    private static final int UPCASE_START = 65;
    private static final int SYMB_THIRD_START = 91;
    private static final int LOWCASE_START = 97;
    private static final int SYMB_FORTH_START = 123;
    private static final int SYMB_FORTH_END = 126;
    private int spaceAmout = 0;
    private int symbolsAmout = 0;
    private int numberAmout = 0;
    private int validAmout = 0;
    private int allAmout = 0;

    public LinkedList<Integer> handleString(String string) {
        LinkedList<Integer> numbersOfValidValues = new LinkedList<>();
        char[] symbols = string.toCharArray();
        int i = 0;
        for (char ch : symbols) {
            int curCharNum = (int) ch;
            if (curCharNum == SPACE) {
                numbersOfValidValues.add(i);
                spaceAmout++;
            } else if ((curCharNum >= SYMB_FIRST_START && curCharNum < NUM_START) ||
                    (curCharNum >= SYMB_SECOND_START && curCharNum < UPCASE_START) ||
                    (curCharNum >= SYMB_THIRD_START && curCharNum < LOWCASE_START) ||
                    (curCharNum >= SYMB_FORTH_START && curCharNum <= SYMB_FORTH_END)) {
                symbolsAmout++;
            } else if (curCharNum >= NUM_START && curCharNum < SYMB_SECOND_START) {
                numberAmout++;
            } else if ((curCharNum >= UPCASE_START && curCharNum < SYMB_THIRD_START) ||
                    (curCharNum >= LOWCASE_START && curCharNum < SYMB_FORTH_START)) {
                validAmout++;
                numbersOfValidValues.add(i);
            }
            i++;
        }

        return numbersOfValidValues;
    }
}
