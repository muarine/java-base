package com.muarine.concurrent.collections;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TestLinkedQueue
 *
 * Lock.unlock
 * Semaphore.acquire
 * Semaphore.release
 * CountDownLatch.countDown
 *
 *
 * @author Muarine<maoyun@rtmap.com>
 * @date 2016 10/14/16 14:13
 * @since 0.1
 */
public class TestLinkedQueue {

    private static ConcurrentLinkedDeque deques;

    public static void main(String[] args) throws InterruptedException {
        ConcurrentLinkedQueue<Map<String , Object>> queues = new ConcurrentLinkedQueue<>();

        Map<String,Object> tempMap = null;
        // init
        for(int i =0 ; i < 1000 ; i++){
            tempMap = new HashMap<>(2 , 0.75f);
            tempMap.put("key" + i , "value" + i);
            queues.add(tempMap);
        }
        System.out.println(queues.size());

        Thread.sleep(3000L);
    }

    static class ConRunable implements Runnable{

        private ConcurrentLinkedQueue<Map<String,Object>> queue;

        public ConRunable(ConcurrentLinkedQueue<Map<String,Object>> queue) {

            this.queue = queue;
        }

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

            while (true){
                Map<String , Object> map = queue.peek();
                if(map == null){
                    break;
                }
                Iterator<Map.Entry<String,Object>> it = map.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry entry = it.next();

                    System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
                }
            }

        }
    }

}
