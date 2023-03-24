package LockAwait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerAwait {

    public static void main(String[] args) {

        SharedProduct product = new SharedProduct();
        Producer t1 = new Producer("producer", product);
        Consumer c1 = new Consumer("Cosumer", product);

        c1.start();
        t1.start();

    }
}

class SharedProduct {
    private static final int CAPACITY = 5;
    List<String> productList = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isFull = lock.newCondition();

    public void add(String item) {
        lock.lock();
        try {
            if (productList.size() == CAPACITY) {
                System.out.println("List if full, waiting for release of some items");
                isFull.await();

            } else {
                System.out.println("added the item: " + item);
                productList.add(item);
                isEmpty.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void remove() {
        lock.lock();
        try {
            if (productList.isEmpty()) {
                System.out.println("Product list is empty, waiting for addition of some items");
                isEmpty.await();
            } else {
                String item = productList.get(productList.size() - 1);
                productList.remove(productList.size() - 1);
                System.out.println("consumed the item: " + item);
                isFull.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class Producer extends Thread {

    SharedProduct product;

    Producer(String name, SharedProduct product){
        super(name);
        this.product = product;
    }

    @Override
    public void run() {

        String []items = new String[] {"item1", "item2", "item3", "item4", "item5", "item6", "item7", "item8", "item9", "item10"};

        for (int i = 0; i < 10; i++) {
            product.add(items[i]);
        }
    }
}


class Consumer extends Thread {

    SharedProduct product;

    Consumer(String name, SharedProduct product){
        super(name);
        this.product = product;
    }

    @Override
    public void run() {
        while (true) {
            product.remove();
        }
    }
}
