import java.util.Comparator;

public class MinHeapPriorityQueue<K, V> extends AbstractPriorityQueue<K, V> {
    public MinHeapPriorityQueue() {
        super();
    }

    public MinHeapPriorityQueue(Comparator<K> comp) {
        super(comp);
    }
}
