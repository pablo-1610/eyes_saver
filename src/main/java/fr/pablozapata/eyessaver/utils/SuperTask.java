package fr.pablozapata.eyessaver.utils;

import java.util.concurrent.*;

public abstract class SuperTask implements Runnable {
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void runTask(Runnable run) {
        executorService.submit(run);
    }

    public static void cancelTasks() {
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }

    public ScheduledFuture scheduleAsyncDelayedTask(long delay, TimeUnit timeUnit) {
        return scheduledExecutorService.schedule(this, delay, timeUnit);
    }

    public ScheduledFuture scheduleAsyncRepeatingTask(long start, long period, TimeUnit timeUnit) {
        return scheduledExecutorService.scheduleAtFixedRate(this, start, period, timeUnit);
    }

    public void runTask() {
        SuperTask.runTask(this);
    }
}

