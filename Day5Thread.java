import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Day5Thread {

    // Simulating async operation
    static int fetchData(String source) {
        try {
            Thread.sleep(2000); // Simulate delay
            System.out.println("Data fetched from " + source + " on " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 100;
    }

    static void processData(int data) {
        System.out.println("Processing data: " + data + " on " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("===== CompletableFuture Examples =====\n");

        // Example 1: Basic CompletableFuture.supplyAsync
        System.out.println("Example 1: Basic supplyAsync");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> fetchData("API1"));
        System.out.println("Future created, main continues...");
        Integer result1 = future1.get();
        System.out.println("Result: " + result1);

        System.out.println("\n---\n");

        // Example 2: Chaining with thenApply
        System.out.println("Example 2: Chaining with thenApply");
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> fetchData("API2"))
                .thenApply(data -> {
                    System.out.println("Transforming data: " + data);
                    return data * 2;
                });
        System.out.println("Result after transformation: " + future2.get());

        System.out.println("\n---\n");

        // Example 3: Chaining with thenAccept (no return value)
        System.out.println("Example 3: Chaining with thenAccept");
        CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> fetchData("API3"))
                .thenAccept(data -> processData(data));
        future3.get();
        System.out.println("Processing complete");

        System.out.println("\n---\n");

        // Example 4: Combining multiple futures - thenCombine
        System.out.println("Example 4: Combining futures with thenCombine");
        CompletableFuture<Integer> futureA = CompletableFuture.supplyAsync(() -> fetchData("DataSourceA"));
        CompletableFuture<Integer> futureB = CompletableFuture.supplyAsync(() -> fetchData("DataSourceB"));
        
        CompletableFuture<Integer> combined = futureA.thenCombine(futureB, (a, b) -> {
            System.out.println("Combining results: " + a + " and " + b);
            return a + b;
        });
        System.out.println("Combined result: " + combined.get());

        System.out.println("\n---\n");

        // Example 5: Multiple futures - allOf
        System.out.println("Example 5: Multiple futures with allOf");
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 1 running");
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return 10;
        });
        
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 2 running");
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            return 20;
        });
        
        CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Task 3 running");
            try { Thread.sleep(800); } catch (InterruptedException e) {}
            return 30;
        });
        
        CompletableFuture.allOf(f1, f2, f3).get();
        System.out.println("All tasks completed");

        System.out.println("\n---\n");

        // Example 6: Exception handling with exceptionally
        System.out.println("Example 6: Exception handling with exceptionally");
      

        System.out.println("\n---\n");

        // Example 7: handle (similar to exceptionally but gets both result and exception)
        System.out.println("Example 7: handle method");
        CompletableFuture<Integer> futureWithHandle = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            return 50;
        }).handle((result, exception) -> {
            if (exception != null) {
                System.out.println("Error occurred: " + exception.getMessage());
                return 0;
            } else {
                System.out.println("Success with result: " + result);
                return result;
            }
        });
        System.out.println("Final result: " + futureWithHandle.get());

        System.out.println("\n---\n");

        // Example 8: Timeout with orTimeout
        System.out.println("Example 8: Timeout handling");
        try {
            CompletableFuture<Integer> timeoutFuture = CompletableFuture.supplyAsync(() -> {
                try { Thread.sleep(5000); } catch (InterruptedException e) {}
                return 100;
            }).orTimeout(2, TimeUnit.SECONDS);
            timeoutFuture.get();
        } catch (ExecutionException e) {
            System.out.println("Timeout occurred: " + e.getCause().getClass().getSimpleName());
        }

        System.out.println("\n---\n");

        // Example 9: anyOf (returns first completed)
        System.out.println("Example 9: anyOf - returns first completed");
        CompletableFuture<Integer> slow = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
            System.out.println("Slow task completed");
            return 100;
        });
        
        CompletableFuture<Integer> fast = CompletableFuture.supplyAsync(() -> {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
            System.out.println("Fast task completed");
            return 50;
        });
        
        CompletableFuture<Object> firstCompleted = CompletableFuture.anyOf(slow, fast);
        System.out.println("First result: " + firstCompleted.get());

        System.out.println("\n===== All examples completed =====");
    }
}
