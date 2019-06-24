package com.lxb.test;

import java.lang.reflect.*;

/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-03-02 21:43
 **/
public class ClassTest {
    public static void main(String[] args) {
        try {
           // FieldClass fClass = new FieldClass(10, 20);
            //System.out.println(fClass.getX());
            Class clazz = Class.forName("com.lxb.test.FieldClass");
            Method[] method = clazz.getMethods();
            for(Method method1 : method){
                System.out.print(" "+method1.getName()+"/n");
            }
            /*System.out.println(method);
            //反射成员变量
            Field field1 = fClass.getClass().getField("x");
            Field field2 = fClass.getClass().getField("y");
            //指定对象
            System.out.println(field1.get(fClass));
            System.out.println(field2.get(fClass));*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
