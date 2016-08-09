package com.muarine.maven;

/**
 * Hello world!
 *
 * JDK1.8 新特性
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
//
//        Collections.sort(names , (String a , String b)-> b.compareTo(a));
//
//
//        Predicate<String> isEmpty = String::isEmpty;
//        System.out.println(isEmpty);
//        Consumer<App> foo2 = App::foo2;
//        foo2.accept(new App());
        String ss = "wxfbea9e80126c504f      oq1Gkt42JBRJejUEJRbfWh4QoXk4    1010010011186327        何怡敏  33028219880617368X      18210535043";
        System.out.println(ss.replaceAll(" " , ""));

    }


    public void foo(String name , String value){

        System.out.println("foo");

    }

    public void foo2(){
        System.out.println("f002");
    }

}
