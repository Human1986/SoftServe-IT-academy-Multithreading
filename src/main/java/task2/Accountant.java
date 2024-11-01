package task2;

import java.util.function.BinaryOperator;

//You have class from task #1 ParallelCalculator
class ParallelCalculator implements Runnable {
    int result;
    private BinaryOperator<Integer> binaryOperator;
    private int operand1;
    private int operand2;

    public ParallelCalculator(BinaryOperator<Integer> binaryOperator, int operand1, int operand2) {
        this.binaryOperator = binaryOperator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    @Override
    public void run() {
        result = binaryOperator.apply(operand1, operand2);
    }
}


class Accountant {

    public static int sum(int x, int y) {
        BinaryOperator<Integer> binaryOperator = Integer::sum;
        ParallelCalculator parallelCalculator = new ParallelCalculator(binaryOperator, x, y);

        Thread myThread = new Thread(parallelCalculator);
        myThread.start();

        try {
            myThread.join();
        } catch (InterruptedException e) {
            new RuntimeException(e);
        }

        return parallelCalculator.result;

    }
}