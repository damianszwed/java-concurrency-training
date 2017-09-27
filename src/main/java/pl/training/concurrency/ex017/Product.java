package pl.training.concurrency.ex017;

public class Product {

    private String name;
    private long price;

    public Product(String name, long price) {
        this.name = name;
        this.price = price;
    }

    public void increasePrice(long changeValue) {
        price += changeValue;
    }

}
