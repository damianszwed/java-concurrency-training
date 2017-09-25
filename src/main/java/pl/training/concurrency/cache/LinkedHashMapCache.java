package pl.training.concurrency.cache;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

public class LinkedHashMapCache<Key, Value> {

    private static final int LOAD_FACTOR = 1;

    private Map<Key, Value> entries;

    public LinkedHashMapCache(int size) {
        entries = synchronizedMap(new LinkedHashMap<Key, Value>(size, LOAD_FACTOR, true) {

            @Override
            protected boolean removeEldestEntry(Map.Entry<Key, Value> eldest) {
                return size() > size;
            }

        });
    }

    public Value get(Key key) {
        return entries.get(key);
    }

    public Value put(Key key, Value value) {
        return entries.put(key, value);
    }

}
