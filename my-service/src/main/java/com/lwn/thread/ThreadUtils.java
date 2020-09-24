package com.lwn.thread;

import java.util.concurrent.*;

public class ThreadUtils {

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
    private static final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);

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
    private static final ExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(2);// 多个
    private static final ExecutorService singleScheduleThreadPool = Executors.newSingleThreadScheduledExecutor();// 单个

    /**
     * @description: 这是一个经常被人忽略的线程池，
     * Java 8 才加入这个创建方法，其内部会构建ForkJoinPool(叉点池)，
     * 利用Work-Stealing算法，并行地处理任务，不保证处理顺序
     */
    private static final ExecutorService workThreadPool = Executors.newWorkStealingPool();

    void ThreadPoolExecutor(int corePoolSize, //核心线程数量
                            int maximumPoolSize, // 最大线程数量
                            long keepAliveTime, // 存活时间
                            TimeUnit unit, // 时间单位
                            BlockingQueue<Runnable> workQueue, //阻塞队列
                            ThreadFactory threadFactory) {
    }// 拒绝策略

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

        cachedThreadPool.shutdownNow();
    }
}
