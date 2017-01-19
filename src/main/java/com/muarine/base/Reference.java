package com.muarine.base;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * Reference
 *
 * 引用篇
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 2017 1/5/17 15:38
 * @since 2.0.0
 */
public class Reference {

    /**
     * 区别于生命周期
     *
     * @param args
     */
    public static void main(String[] args) {
        // 强引用  长生命周期
        Object object = new Object();
        String s = "123";

        // 弱引用  短生命周期
        // 垃圾回收机制强制回收弱引用
        object = null;
        Object object1 = new Object();
        object1 = null;

        // 软引用  较长的生命周期
        // 如果内存的空间足够，软引用就能继续被使用，而不会被垃圾回收器回收，只有在内存不足时，软引用才会被垃圾回收器回收
        // 通过SoftReference创建
        // 应用场景: 数据、图片缓存,解决OOM问题
        SoftReference<String> softReference = new SoftReference<>(s);

        // 虚引用 无生命周期
        // 形同虚设,在任何时候都可能被垃圾回收器回收
        // 应用场景: 主要用来跟踪对象被垃圾回收的活动
        ReferenceQueue<String> queue = new ReferenceQueue<>();
        PhantomReference<String> pr = new PhantomReference<>("hello", queue);
        System.out.println(pr.get()); // null

        int i = 0;
        scan: {
            while (true){
                i++;
                if(i > 5){
                    break scan;
                }
            }
        }

        System.out.println(i);
        System.out.println("finish");

    }



}
