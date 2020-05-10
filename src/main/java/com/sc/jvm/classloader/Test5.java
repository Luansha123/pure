package com.sc.jvm.classloader;

public class Test5 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {


        String path = "C:\\Users\\Luans\\Desktop\\";
        String className = "com.sc.jvm.classloader.Test1";

        String fullClassName = path + className.replace(".", "\\") + ".class";

        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath(path);
        Class<?> clazz = loader1.loadClass(className);

        clazz.newInstance();

    }
}
