package com.example.springframework.boot.async.config.thread;

import java.util.concurrent.*;

public class ThreadPool {

    private static ThreadPoolExecutor executor;

    static {
        //核心线程数10：线程池创建时候初始化的线程数
        int corePoolSize = 10;
        //最大线程数20：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        int maxPoolSize = 20;
        //缓冲队列200：用来缓冲执行任务的队列
        int queueCapacity = 200;
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        int keepAliveSeconds = 60;
        //线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        //defaultThreadFactory线程池名的前缀为 "pool-" + poolNumber + "-thread-"
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingQueue<>(queueCapacity), threadFactory, callerRunsPolicy);
    }

    private ThreadPool() {
    }

    public static ExecutorService getExecutorService() {
        return executor;
    }
}
