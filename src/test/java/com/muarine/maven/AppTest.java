package com.muarine.maven;

import com.muarine.annotation.Around;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( false );
    }

    public void preMethod(){

        System.out.println("pre method execute.");
    }

    public void afterMethod(){

        System.out.println("after method execute.");
    }

    public void testAOP() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        // 装饰器 不改变函数执行结果前提下,对函数执行前后进行包装

        Class<?> clazz = App.class;
//        Method method = clazz.getMethod("foo", String.class, String.class);
//        method.invoke(clazz.newInstance() , String.class.newInstance() , String.class.newInstance());
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            List<Object> list = new ArrayList<Object>();
            for (Class paramClazz : method.getParameterTypes()){
                System.out.println("param name:" + paramClazz.getName());
//                System.out.println("param type:" + paramClazz.getTypeName());
                list.add(paramClazz.getClass().getName());
            }


            Annotation annotation = method.getAnnotation(Around.class);
            if(annotation != null){
                this.preMethod();
                Object obj = method.invoke(clazz.newInstance() , list.toArray());
                System.out.println(obj);
                this.afterMethod();
            }else{

                Object obj = method.invoke(clazz.newInstance() , list.toArray());
                System.out.println(obj);
            }
        }

    }
}
