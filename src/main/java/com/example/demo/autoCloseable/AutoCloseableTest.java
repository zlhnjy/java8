package com.example.demo.autoCloseable;

import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @Auther: zhangliang
 * @Date: 2019/5/6 16:25
 * @Description:
 */
public class AutoCloseableTest {

    public static void main(String[] args) {
        test1();    //没有关闭流
        System.out.println("-----------");
        test2();    //try-finally方式关闭流
        System.out.println("-----------");
        test3();    //try-with-resources方式自动关闭流，inputstream实现了AutoCloseable接口，在try后面跟上需要使用的资源，则会自动关闭，java7的特性
        System.out.println("-----------");
        test4();    //File没有实现AutoCloseable接口无法写到try后面，try后面可以写多个需要自动关闭的流
    }

    public static void test1() {
        MyFileInputStream myFileInputStream = null;
        try {
            myFileInputStream = new MyFileInputStream(ResourceUtils.getFile("classpath:a.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void test2() {
        MyFileInputStream myFileInputStream = null;
        try {
            myFileInputStream = new MyFileInputStream(ResourceUtils.getFile("classpath:a.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (myFileInputStream != null) {
                    myFileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void test3() {
        try(MyFileInputStream myFileInputStream = new MyFileInputStream(ResourceUtils.getFile("classpath:a.txt"))) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void test4() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:a.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try(MyFileInputStream myFileInputStream = new MyFileInputStream(file);
            MyFileOutputStream myFileOutputStream = new MyFileOutputStream(file)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MyFileInputStream extends FileInputStream {

        public MyFileInputStream(String name) throws FileNotFoundException {
            super(name);
        }

        public MyFileInputStream(File file) throws FileNotFoundException {
            super(file);
        }

        public MyFileInputStream(FileDescriptor fdObj) {
            super(fdObj);
        }

        @Override
        public void close() throws IOException {
            System.out.println("myFileInputStream close");
            super.close();
        }
    }

    private static class MyFileOutputStream extends FileOutputStream {

        public MyFileOutputStream(String name) throws FileNotFoundException {
            super(name);
        }

        public MyFileOutputStream(String name, boolean append) throws FileNotFoundException {
            super(name, append);
        }

        public MyFileOutputStream(File file) throws FileNotFoundException {
            super(file);
        }

        public MyFileOutputStream(File file, boolean append) throws FileNotFoundException {
            super(file, append);
        }

        public MyFileOutputStream(FileDescriptor fdObj) {
            super(fdObj);
        }

        @Override
        public void close() throws IOException {
            System.out.println("myFileOutputStream close");
            super.close();
        }
    }

}
