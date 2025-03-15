import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPriorityQueue<K, V> implements PriorityQueue<K, V> {
    protected static class PQEntry<K, V> implements Entry<K, V> {
        private K k;  // Key (Öncelik)
        private V v;  // Value (Veri)

        public PQEntry(K key, V value) {
            k = key;
            v = value;
        }

        public K getKey() { return k; }
        public V getValue() { return v; }

        protected void setKey(K key) { k = key; }
        protected void setValue(V value) { v = value; }
    }

    private Comparator<K> comp;
    private List<Entry<K, V>> heap;

    protected AbstractPriorityQueue(Comparator<K> c) { 
        comp = c;
        heap = new ArrayList<>();
    }

    protected AbstractPriorityQueue() { 
        this(new DefaultComparator<K>()); 
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            return (comp.compare(key, key) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Geçersiz anahtar türü.");
        }
    }

    @Override
    public boolean isEmpty() { 
        return heap.isEmpty();
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
        checkKey(key);
        Entry<K, V> entry = new PQEntry<>(key, value);
        heap.add(entry);
        upHeap(heap.size() - 1);
        return entry;
    }

    @Override
    public Entry<K, V> min() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    @Override
    public Entry<K, V> removeMin() {
        if (heap.isEmpty()) return null;
        Entry<K, V> min = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        downHeap(0);
        return min;
    }

    private void upHeap(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (compare(heap.get(index), heap.get(parentIndex)) >= 0) break;
            swap(index, parentIndex);
            index = parentIndex;
        }
    }

    private void downHeap(int index) {
        int leftChild, rightChild, smallest;
        while (index < heap.size() / 2) {
            leftChild = 2 * index + 1;
            rightChild = leftChild + 1;
            smallest = leftChild;

            if (rightChild < heap.size() && compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
                smallest = rightChild;
            }

            if (compare(heap.get(smallest), heap.get(index)) >= 0) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Entry<K, V> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
