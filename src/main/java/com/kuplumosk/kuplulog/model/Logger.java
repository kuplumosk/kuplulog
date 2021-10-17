package com.kuplumosk.kuplulog.model;

import com.kuplumosk.kuplulog.annotation.LogMe;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class Logger {

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.instantiate("com.kuplumosk.kuplulog.test");
    }

    public void instantiate(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        String path = basePackage.replace('.', '/');
        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File file = null;
            try {
                file = new File(resource.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            for(File classFile : file.listFiles()){
                String fileName = classFile.getName();
                if(fileName.endsWith(".class")){
                    String className = fileName.substring(0, fileName.lastIndexOf("."));
                    try {
                        Class classObject = Class.forName(basePackage + "." + className);
                        if(classObject.isAnnotationPresent(LogMe.class)){
                            System.out.println("Component: " + classObject);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
