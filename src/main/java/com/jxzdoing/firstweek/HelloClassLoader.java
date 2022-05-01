package com.jxzdoing.firstweek;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 * @author:jack
 * @date:Create on 2022/4/28 14:57
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class clazz = helloClassLoader.loadClass("Hello");
        Object object = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getDeclaredMethod("hello");
        method.invoke(object);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        String path = this.getClass().getClassLoader().getResource(name + ".xlass").getPath();
        try {
            fis = new FileInputStream(path);
            baos = new ByteArrayOutputStream();
            int b;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            byte[] bytes = baos.toByteArray();
            bytes = convert(bytes);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        } finally {
            close(fis);
            close(baos);
        }
    }

    private byte[] convert(byte[] bytes) {
        byte[] res = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            res[i] = (byte) (255 - bytes[i]);
        }
        return res;
    }

    private void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}


