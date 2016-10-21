package com.muarine.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * TestCallback
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 2016 10/14/16 12:09
 * @since 0.1
 */
public class TestCallback {

    public static void main(String[] args) throws Exception {

        int i = 1;
        DemoThread demoThread = new DemoThread();
        Callable<Integer> callable = Executors.callable(demoThread , i);




        callable.call();

        System.exit(0);

    }

    static class DemoThread implements Runnable{

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println("run");
        }
    }

}
