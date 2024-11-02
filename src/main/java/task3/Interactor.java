package task3;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

class Interactor {
    private final Object LOCK = new Object();
    volatile int x;
    private volatile boolean isInitialized;

    public void serve(UnaryOperator<Integer> uo, int initializer) throws InterruptedException {
        System.out.println("Serving thread running");

        synchronized (LOCK) {
            System.out.println("Serving thread initializes the key");

            x = uo.apply(initializer);

            System.out.println("key = " + x);

            LOCK.notifyAll();
        }
    }


    public void consume(BinaryOperator<Integer> bo, int operand2) throws InterruptedException {

        synchronized (LOCK) {
            while (! isInitialized) {
                LOCK.wait(3000);
                isInitialized = true;
            }

            if (x != 0) {
                doConsume(bo, operand2);
                System.out.println("Serving thread resumed");
            } else {
                doConsume(bo, operand2);
            }
        }

    }

    private void doConsume(BinaryOperator<Integer> bo, int operand2) {
        System.out.println("Consuming thread received the key. key = " + x);
        x = bo.apply(x, operand2);

        System.out.println("Consuming thread changed the key. key = " + x);
    }
}