package com.lwn.thread;

import java.util.concurrent.*;

public class ThreadUtils {
    // 阿里发布的 Java开发手册中强制线程池不允许使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险

    /**
     * @description: 它是用来处理大量短时间工作任务的线程池具有几个鲜明特点：
     * 它会试图缓存线程并重用，当无缓存线程可用时，
     * 就会创建新的工作线程；如果线程闲置时间超过60秒，
     * 则被终止并移除缓存；长时间闲置时，这种线程池，
     * 不会消耗什么资源。其内部使用SynchronousQueue作为工作队列
     */
    private static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     * @param: int nThreads
     * @description: 重用指定数目（nThreads）的线程，其背后使用的是无界的工作队列，
     * 任何时候最多有nThreads个工作线程是活动的。这意味着，如果任务数量超过了活动线程数目，
     * 将在工作队列中等待空闲线程出现；如果工作线程退出，将会有新的工作线程被创建，
     * 以补足指定数目nThreads
     */
    private static ExecutorService fixedThreadPool = null;

    /**
     * @description: 它的特点在于工作线程数目限制为1，操作一个无界的工作队列，
     * 所以它保证了所有的任务都是被顺序执行，最多会有一个任务处于活动状态，
     * 并且不予许使用者改动线程池实例，因此可以避免改变线程数目
     */
    private static final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    /**
     * @description: 创建的是个ScheduledExecutorService，
     * 可以进行定时或周期性的工作调度，区别在于单一工作线程还是多个工作线程
     */
    private static ExecutorService scheduleThreadPool = null;// 多个
    private static final ExecutorService singleScheduleThreadPool = Executors.newSingleThreadScheduledExecutor();// 单个

    /**
     * @description: 这是一个经常被人忽略的线程池，
     * Java 8 才加入这个创建方法，其内部会构建ForkJoinPool(叉点池)，
     * 利用Work-Stealing算法，并行地处理任务，不保证处理顺序
     */
    private static final ExecutorService workStealingThreadPool = Executors.newWorkStealingPool();

    // *****************************************************************************************************************

    /**
     * newCachedThreadPool（），它是用来处理大量短时间工作任务的线程池，
     * 具有几个鲜明特点：它会试图缓存线程并重用，当无缓存线程可用时，就会创建新的工作线程；
     * 如果线程闲置时间超过60秒，则被终止并移除缓存；长时间闲置时，这种线程池，不会消耗什么资源。
     * 其内部使用SynchronousQueue作为工作队列
     *
     * @param runnable
     */
    public static void executeCachedThread(Runnable runnable) {

        cachedThreadPool.execute(runnable);
    }

    public static Future<?> submitCachedThread(Runnable runnable) {

        return cachedThreadPool.submit(runnable);
    }

    public static Future<?> submitCachedThread(Callable<?> callable) {

        return cachedThreadPool.submit(callable);
    }

    public static void shutDownCachedThread() {

        // 停止整个线程池
        cachedThreadPool.shutdownNow();
    }

    // *****************************************************************************************************************

    /**
     * newFixedThreadPool（int nThreads），重用指定数目（nThreads）的线程，
     * 其背后使用的是无界的工作队列，任何时候最多有nThreads个工作线程是活动的。
     * 这意味着，如果任务数量超过了活动线程数目，将在工作队列中等待空闲线程出现；
     * 如果工作线程退出，将会有新的工作线程被创建，以补足指定数目nThreads。
     *
     * @param nThreads
     * @param runnable
     */
    public static void executeFixedThread(int nThreads, Runnable runnable) {

        fixedThreadPool = Executors.newFixedThreadPool(nThreads);

        fixedThreadPool.execute(runnable);
    }

    public static Future<?> submitFixedThread(int nThreads, Runnable runnable) {

        fixedThreadPool = Executors.newFixedThreadPool(nThreads);

        return fixedThreadPool.submit(runnable);
    }

    public static Future<?> submitFixedThread(int nThreads, Callable<?> callable) {

        fixedThreadPool = Executors.newFixedThreadPool(nThreads);

        return fixedThreadPool.submit(callable);
    }

    public static void shutDownFixedThread() {

        // 停止整个线程池
        fixedThreadPool.shutdownNow();
    }

    // *****************************************************************************************************************

    /**
     * newSingleThreadExecutor()，它的特点在于工作线程数目限制为1，操作一个无界的工作队列，
     * 所以它保证了所有的任务都是被顺序执行，最多会有一个任务处于活动状态，并且不予许使用者改动线程池实例，因此可以避免改变线程数目。
     *
     * @param runnable
     */
    public static void executeSingleThread(Runnable runnable) {

        singleThreadPool.execute(runnable);
    }

    public static Future<?> submitSingleThread(Runnable runnable) {

        return singleThreadPool.submit(runnable);
    }

    public static Future<?> submitSingleThread(Callable<?> callable) {

        return singleThreadPool.submit(callable);
    }

    public static void shutDownSingleThread() {

        // 停止整个线程池
        singleThreadPool.shutdownNow();
    }

    // *****************************************************************************************************************

    /**
     * newScheduledThreadPool(int corePoolSize)，
     * 创建的是个ScheduledExecutorService，多个 可以进行定时或周期性的工作调度。
     *
     * @param nThreads 多个
     * @param runnable
     */
    public static void executeScheduleThread(int nThreads, Runnable runnable) {

        scheduleThreadPool = Executors.newScheduledThreadPool(nThreads);

        scheduleThreadPool.execute(runnable);
    }

    public static Future<?> submitScheduleThread(int nThreads, Runnable runnable) {

        scheduleThreadPool = Executors.newScheduledThreadPool(nThreads);

        return scheduleThreadPool.submit(runnable);
    }

    public static Future<?> submitScheduleThread(int nThreads, Callable<?> callable) {

        scheduleThreadPool = Executors.newScheduledThreadPool(nThreads);

        return scheduleThreadPool.submit(callable);
    }

    public static void shutDownScheduleThread() {

        // 停止整个线程池
        scheduleThreadPool.shutdownNow();
    }

    // NewSingleScheduleThreadPool

    /**
     * newSingleThreadScheduledExecutor() 单个 可以进行定时或周期性的工作调度的线程
     *
     * @param runnable 单个
     */
    public static void executeSingleScheduleThread(Runnable runnable) {

        singleScheduleThreadPool.execute(runnable);
    }

    public static Future<?> submitSingleScheduleThread(Runnable runnable) {

        return singleScheduleThreadPool.submit(runnable);
    }

    public static Future<?> submitSingleScheduleThread(Callable<?> callable) {

        return singleScheduleThreadPool.submit(callable);
    }

    public static void shutDownSingleScheduleThread() {

        // 停止整个线程池
        singleScheduleThreadPool.shutdownNow();
    }

    // *****************************************************************************************************************

    /**
     * newWorkStealingPool(int parallelism)，这是一个经常被人忽略的线程池，
     * Java 8 才加入这个创建方法，其内部会构建ForkJoinPool，利用Work-Stealing算法，并行地处理任务，不保证处理顺序。
     *
     * @param runnable
     */
    public static void executeWorkThread(Runnable runnable) {

        workStealingThreadPool.execute(runnable);
    }

    public static Future<?> submitWorkThread(Runnable runnable) {

        return workStealingThreadPool.submit(runnable);
    }

    public static Future<?> submitWorkThread(Callable<?> callable) {

        return workStealingThreadPool.submit(callable);
    }

    public static void shutDownWorkThread() {

        // 停止整个线程池
        workStealingThreadPool.shutdownNow();
    }
}
