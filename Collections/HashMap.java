import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HashMap implements MapInterface {

    private static final int INITIAL_CAPACITY = 16;
    private Entry[] array = new Entry[INITIAL_CAPACITY];
    private final double LOAD_FACTOR = 0.75;

    private int size = 0;

    @Override
    public void put(CarOwner key, Car value) {
        if (size >= (array.length)*LOAD_FACTOR) {
            increaseArray();
        }
        boolean put = put(key, value, array);
        if (put) {
            size++;
        }
    }

    @Override
    public Car get(CarOwner key) {
        int position = getElementPosition(key, array.length);
        Entry existedElement = array[position];
        while (existedElement != null) {
            if (existedElement.key.equals(key)) {
                return existedElement.value;
            }
            existedElement = existedElement.next;
        }
        return null;
    }

    @Override
    public Set<CarOwner> keySet() {
        Set<CarOwner> result = new HashSet<>();
        for (Entry entry : array) {
            Entry existedElement = entry;
            while (existedElement != null) {
                result.add(existedElement.key);
                existedElement = existedElement.next;
            }
        }
        return null;
    }

    @Override
    public List<Car> values() {
        List<Car> result = new ArrayList<>();
        for (Entry entry : array) {
            Entry existedElement = entry;
            while (existedElement != null) {
                result.add(existedElement.value);
                existedElement = existedElement.next;
            }
        }
        return null;
    }

    @Override
    public boolean remove(CarOwner key) {
        int position = getElementPosition(key, array.length);
        Entry existedElement = array[position];
        if (existedElement != null && existedElement.key.equals(key)) {
            array[position] = existedElement.next;
            size--;
            return true;
        } else {
            while (existedElement != null) {
                Entry nextElement = existedElement.next;
                if (nextElement == null) return false;
                if (nextElement.key.equals(key)) {
                    existedElement.next = nextElement.next;
                    size--;
                    return true;
                }
                existedElement = existedElement.next;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        array = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    private int getElementPosition(CarOwner carOwner, int arrayLength) {
        return Math.abs(carOwner.hashCode() % arrayLength);
    }

    private  static class Entry{
        private CarOwner key;
        private Car value;
        private Entry next;

        public Entry(CarOwner key, Car value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private void increaseArray() {
        Entry[] newArray = new Entry[array.length*2];
        for (Entry entry:array) {
            Entry existedElement = entry;
            while (existedElement != null) {
                put(existedElement.key, existedElement.value, newArray);
                existedElement = existedElement.next;
            }
        }
        array = newArray;
    }

    private boolean put(CarOwner key, Car value, Entry[] dst) {
        int position = getElementPosition(key, dst.length);
        Entry existedElement = dst[position];
        if (existedElement == null) {
            Entry entry = new Entry(key, value, null);
            dst[position] = entry;
            return true;
        } else {
            while (true) {
                if (existedElement.key.equals(key)) {
                    existedElement.value = value;
                    return false;
                }
                if (existedElement.next == null) {
                    existedElement.next = new Entry(key, value, null);
                    return true;
                }
                existedElement = existedElement.next;
            }
        }
    }
}
