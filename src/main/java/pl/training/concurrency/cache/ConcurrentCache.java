package pl.training.concurrency.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

public class ConcurrentCache<Key, Value> {

    private Map<Key, Value> map = new HashMap<>();
    private TreeSet<Index> indices = new TreeSet<>();
    private int cacheSize;

    public ConcurrentCache(Map<Key, Value> map, int cacheSize) {
        this.map = map;
        this.cacheSize = cacheSize;
    }

    public synchronized void put(Key key, Value value) {
        Index index = getIndexWithKey(key);
        indices.add(index);
        map.put(key, value);
        ensureCapacity();
    }

    public synchronized Optional<Value> get(Key key) {
        getIndexWithKey(key).incrementAccessCounter();
        return Optional.of(map.get(key));
    }

    private void ensureCapacity() {
        if (indices.size() > cacheSize) {
            Index<Key> lastIndex = indices.pollLast();
            map.remove(lastIndex.getKey());
        }
    }

    private Index<Key> getIndexWithKey(Key key) {
        return indices.stream()
                .filter(index -> index.hasKey(key))
                .findFirst()
                .orElse(new Index<>(key));
    }

}