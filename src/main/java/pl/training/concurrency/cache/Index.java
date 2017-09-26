package pl.training.concurrency.cache;

public class Index<Key> implements Comparable<Index<Key>> {

    private Long accessCounter = 1L;
    private Long created = System.currentTimeMillis();
    private Key key;

    public Index(Key key) {
        this.key = key;
    }

    private Long getAccessCounter() {
        return accessCounter;
    }

    private Long getCreated() {
        return created;
    }

    public Key getKey() {
        return key;
    }

    public boolean hasKey(Key key) {
        return this.key.equals(key);
    }

    public void incrementAccessCounter() {
        accessCounter++;
    }

    @Override
    public int compareTo(Index<Key> otherIndex) {
        int result = otherIndex.getAccessCounter().compareTo(accessCounter);
        return result != 0 ? result : otherIndex.getCreated().compareTo(created);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Index<?> index = (Index<?>) o;

        if (accessCounter != null ? !accessCounter.equals(index.accessCounter) : index.accessCounter != null)
            return false;
        if (created != null ? !created.equals(index.created) : index.created != null) return false;
        return key != null ? key.equals(index.key) : index.key == null;
    }

    @Override
    public int hashCode() {
        int result = accessCounter != null ? accessCounter.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Index{" +
                "accessCounter=" + accessCounter +
                ", created=" + created +
                ", key=" + key +
                '}';
    }

}
