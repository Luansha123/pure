package com.sc.jvm.classloader;


/**
 * bootstrap:(根类加载器) 加载 JRE\lib\rt.jar 或者-Xbootclasspath选项指定的jar包
 * extension:(扩展加载器) 加载 JRE\lib\ext\*.jar 或者 -Djava.ext.dirs 指定的jar包
 * application/system:(应用加载器/系统类加载器) 加载 类路径 或 -Djava.classpath所指的目录下的类和包
 * custom:(自定义类加载器) 通过java.lang.ClassLoader 的子类自定义加载class
 */
public class Test4 {

    /**
     * null: 根类加载器
     * sun.misc.Launcher$AppClassLoader@18b4aac2： ApplicationClassLoader 应用类加载器
     * @param args
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println(Class.forName("java.lang.String").getClassLoader());
        System.out.println(Class.forName("com.sc.jvm.classloader.Test4").getClassLoader());
        System.out.println(Class.forName("com.sc.jvm.classloader.C").getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());

    }
}

class C{

}
