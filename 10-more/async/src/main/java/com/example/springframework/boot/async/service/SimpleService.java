package com.example.springframework.boot.async.service;

import com.example.springframework.boot.async.config.thread.ThreadPool;
import com.example.springframework.boot.async.task.SimpleScheduler;
import com.example.springframework.boot.async.task.SimpleTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class SimpleService {

    @Autowired
    private SimpleTask simpleTask;
    @Autowired
    private SimpleScheduler simpleScheduler;

    public void syncDo() {
        //正常同步完成任务
        doServiceOne();
        doServiceTwo();
        doServiceThree();
        log.info("-----正常同步完成任务-----");
    }

    public void asyncDo() {
        //常规异步完成任务
        ThreadPool.getExecutorService().execute(this::doServiceOne);
        ThreadPool.getExecutorService().execute(this::doServiceTwo);
        ThreadPool.getExecutorService().execute(this::doServiceThree);
        log.info("-----线程池获取线程，异步执行，完成任务-----");
    }

    public void springAsyncDo() {
        //使用@Async注解，指定线程，使方法执行方式为异步，实现异步调用
        //注意：@Async注解方法的调用方和方法不能在同一类中，同一类中，仍为同步
        simpleTask.doTaskOne();
        simpleTask.doTaskTwo();
        simpleTask.doTaskThree();
        log.info("-----使用@Async实现异步调用-----");
    }

    /* 测试同/异步执行 */

    private void doService(String taskName) {
        log.info("开始执行" + taskName);
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(new Random().nextInt(10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.info("完成" + taskName + "，耗时：" + (end - start) + "毫秒");
    }

    private void doServiceOne() {
        String taskName = "业务一";
        doService(taskName);
    }

    private void doServiceTwo() {
        doService("任务二");
    }

    private void doServiceThree() {
        doService("任务三");
    }

    /*
    TODO
    测试shutdown影响异步线程方法执行情况
    结论：
        由于配置了spring线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        System.exit(0)，会在线程任务全部结束后再关闭程序
     */

    public void testShutDownTaskDo() {
        for (int i = 0; i < 100; i++) {
            simpleTask.doTaskOne();
            simpleTask.doTaskTwo();
            simpleTask.doTaskThree();

            log.info("执行任务次数：" + i);

            if (i == 50) {
                System.err.println("-----模拟系统异常，关闭程序-----");
                System.exit(0);
            }
        }
    }

    public void testShutDownSchedulerDo() {
        for (int i = 0; i < 100; i++) {
            simpleScheduler.doSchedulerOne();
            simpleScheduler.doSchedulerTwo();
            simpleScheduler.doSchedulerThree();

            log.info("执行任务次数：" + i);

            if (i == 50) {
                System.err.println("-----模拟系统异常，关闭程序-----");
                System.exit(0);
            }
        }
    }

    /* 获取异步执行的Future结果以及定义超时 */

    public void asyncFutureResult() {
        //获取结果
        while (true) {
            /*
            Future的接口方法说明：这里以java.util.concurrent包下的FutureTask实现为例
            get()方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
            get(long timeout, TimeUnit unit)用来指定时间后获取执行结果，如果在指定时间内，还没获取到结果，报错：TimeoutException
            isCancelled方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
            isDone方法表示任务是否已经完成，若任务完成，则返回true；
            cancel方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
             */
            Future<Long> schedulerOneFuture = simpleScheduler.doSchedulerOne();
            try {
                /*
                TODO
                逻辑：5秒后获取结果，如果为null取消任务
                结论：java.util.concurrent.FutureTask
                1.get(long timeout, TimeUnit unit)，指定时间后任务未结束，异常：TimeoutException
                2.cancel取消任务时，任务线程休眠并捕获异常sleep interrupted时,方法不会中断，会继续完成任务，
                3.get，方法未执行完成，取消任务后成功获取任务执行结果，CancellationException撤销异常
                 */

                //注意：指定时间过短，任务未完成，会有异常：TimeoutException
                Long timeGetResult = null;
                try {
                    timeGetResult = schedulerOneFuture.get(5, TimeUnit.SECONDS);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    log.error("指定时间后获取结果异常(time get)：" + e.getMessage());
                }
                log.info("①指定时间内获取结果(time get):" + timeGetResult);

                if (null == timeGetResult) {
                    Thread.sleep(1000);
                    log.info("②-----等待一秒(wait)-----");

                    boolean done = schedulerOneFuture.isDone();
                    log.info("②方法是否已完成(done)：" + done);
                    if (done) {
                        Long getResult = schedulerOneFuture.get();
                        log.info("③获取执行结果(get):" + getResult);
                    } else {
                        boolean cancel = schedulerOneFuture.cancel(true);
                        log.info("③取消任务结果(cancel):" + cancel);
                        boolean cancelled = schedulerOneFuture.isCancelled();
                        log.info("③任务是否被取消成功(cancelled):" + cancelled);

                        //方法未完成，任务取消后后查询执行结果会有异常：CancellationException撤销异常
                        Long getResult = null;
                        try {
                            getResult = schedulerOneFuture.get();
                        } catch (Exception e) {
                            log.error("③任务取消后获取执行结果异常(cancelled get)：" + e.getMessage());
                        }
                        log.info("③获取执行结果(get):" + getResult);
                    }
                } else {
                    boolean done = schedulerOneFuture.isDone();
                    boolean cancel = schedulerOneFuture.cancel(true);
                    boolean cancelled = schedulerOneFuture.isCancelled();
                    log.info("②方法是否已完成(done)：" + done);
                    log.info("②取消任务结果(cancel):" + cancel);
                    log.info("②任务是否被取消成功(cancelled):" + cancelled);
                }

                Thread.sleep(5000);
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* 其他测试 */

    public void test() {
        /*
        测试异步任务取消时若该任务线程休眠并捕获异常，取消不生效，任务会继续执行，不会中断
         */
        while (true) {
            //do
            Future<Long> future = simpleScheduler.doSchedulerOne();

            //sleep
            log.info("等待三秒");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //cancel
            boolean cancel = future.cancel(true);
            boolean cancelled = future.isCancelled();
            log.info("取消任务结果(cancel):" + cancel);
            log.info("任务是否被取消成功(cancelled):" + cancelled);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
