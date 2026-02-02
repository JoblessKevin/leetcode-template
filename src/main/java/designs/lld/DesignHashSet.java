package designs.lld;

class MyHashSet {
    private int[] buckets;

    public MyHashSet() {
        buckets = new int[31251];
    }

    public void add(int key) {
        int bucketIdx = key >> 5;
        int bitIdx = key & 31;
        buckets[bucketIdx] |= (1 << bitIdx);
    }

    public void remove(int key) {
        int bucketIdx = key >> 5;
        int bitIdx = key & 31;
        buckets[bucketIdx] &= ~(1 << bitIdx);
    }

    public boolean contains(int key) {
        int bucketIdx = key >> 5;
        int bitIdx = key & 31;
        return (buckets[bucketIdx] & (1 << bitIdx)) != 0;
    }
}


public class DesignHashSet {

}
