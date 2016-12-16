package quarantine;

import java.util.LinkedList;

public class FirstBuf<String> extends LinkedList<String> {
    private int limit;

    public FirstBuf(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean add(String o) {
        super.add(o);
        while (size() > limit) {
            super.remove();
        }
        return true;
    }
}
