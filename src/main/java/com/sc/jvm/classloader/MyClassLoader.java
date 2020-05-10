package com.sc.jvm.classloader;

import java.io.*;

/**
 * 1. 自定义类MyClassLoader继承自ClassLoader
 * 2. 重写findClass方法，该方法会根据给定的（路径+全限定类名）加载类的二进制文件，生成class对象
 */
public class MyClassLoader extends ClassLoader {

    // path: 一个类完整的class路径
    public void setPath(String path) {
        this.path = path;
    }

    private String loaderName;

    private String path;

    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent, String loaderName) {
        super(parent);
        this.loaderName = loaderName;
    }

    public MyClassLoader(String loaderName) {
        this.loaderName = loaderName;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classByte = loadClassData(name);
        return defineClass(name, classByte, 0, classByte.length);
    }

    private byte[] loadClassData(String name) {
        // load the class data from the connection
        String fullName = path + name.replace(".", "\\") + ".class";
        System.out.println("---------------------------------");
        System.out.println(fullName);
        File file = new File(fullName);
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch = 0;
        try {
            fis = new FileInputStream(file);
            while ((ch = fis.read()) != -1) {
                baos.write(ch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }
}
