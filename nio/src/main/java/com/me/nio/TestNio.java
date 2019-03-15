package com.me.nio;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by llb on 2018/10/8.
 */
public class TestNio {
    public static void main(String[] args) throws Exception {
        TestNio testNio = new TestNio();
        System.out.println(testNio.getCurrentPath());
//        testNio.createNullFile();
//        testNio.setFileSize();
        testNio.setFileSize4();
    }

    public String getCurrentPath() {
        return new File("testnio").getAbsolutePath();
    }

    public boolean createFile() throws IOException {
        return new File("testnio").createNewFile();
    }

    public void createNullFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("testnio");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setFileSize() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("testnio",true);
            FileChannel fileChannel = fileOutputStream.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileSize2() {
        try {
            createFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile("testnio","r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFileSize3() {
        try {
            createFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile("testnio","r");
            FileChannel fileChannel = randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setFileSize4() {
        try {
            createFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile("testnio.txt","rw");
            FileChannel fileChannel = randomAccessFile.getChannel();
            //传建一个1k的文件，内存映射文件，
            // 申请的内核的文件系统页（用来管理磁盘等硬件存储设备的空间，页对齐1k）和
            // 用户空间申请的虚拟内存页共享同一段物理内存（物理内存页和虚拟内存页大小一致）
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 10);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
