package pl.training.concurrency.ex017;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class UpdatePriceTask extends RecursiveAction {

    private List<Product> products;
    private long changeValue;
    private int startIndex;
    private int endIndex;
    private int chunk;

    public UpdatePriceTask(List<Product> products, long changeValue, int startIndex, int endIndex, int chunk) {
        this.products = products;
        this.changeValue = changeValue;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.chunk = chunk;
    }

    @Override
    protected void compute() {
        if (endIndex - startIndex <= chunk) {
            //System.out.printf("%d - %d\n", startIndex, endIndex);
            updatePrice();
        } else {
            int middle = (startIndex + endIndex) / 2;
            UpdatePriceTask firstTask = new UpdatePriceTask(products, changeValue, startIndex, middle,  chunk);
            UpdatePriceTask secondTask = new UpdatePriceTask(products, changeValue, middle + 1, endIndex,  chunk);
            firstTask.fork();
            secondTask.fork();
            //invokeAll(firstTask, secondTask);
        }
    }

    private void updatePrice() {
        for (int index = startIndex; index < endIndex; index++) {
            products.get(index).increasePrice(changeValue);
        }
    }

}
