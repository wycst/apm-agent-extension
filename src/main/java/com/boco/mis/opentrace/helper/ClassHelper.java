package com.boco.mis.opentrace.helper;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.boco.mis.opentrace.plugins.ApmPlugin;
 
public class ClassHelper {
 
	public static void main(String[] args){
//		Set<Class<?>> cls = getClassesFromJar("e:/loadjar.jar");
//	    System.out.println(cls);
	    
	    Set<Class<?>> plugins = ClassHelper.getClasses("com.boco.mis.opentrace.plugins",ApmPlugin.class);
		
	    System.out.println(ApmPlugin.class.isAssignableFrom(ApmPlugin.class));
		System.out.println(plugins);
	    
	}
	
	public static void loadJars(String lib) {
		File jarLib = new File(lib);
		if(jarLib.exists() && jarLib.isDirectory()) {
			for(File file : jarLib.listFiles()) {
				if(!file.isDirectory() && file.getName().toLowerCase().endsWith(".jar")) {
					try {
						loadJar(file);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		}
	}
	
	public static void loadJar(File jarFile) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
		if(!jarFile.exists()) {
			System.out.println( jarFile.getAbsolutePath() + " is not exist ");
			return ;
		}
		URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Method _addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
		_addURL.setAccessible(true);
		_addURL.invoke(loader, jarFile.toURI().toURL());
	}
	
	public static void loadJar(String jarFileName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
		loadJar(new File(jarFileName));
	}
	
	public static Set<Class<?>> getClassesFromJar(File jarFile) {
		return getClassesFromJar(jarFile,Object.class);
	}
	
	public static Set<Class<?>> getClassesFromJar(File jarFile,Class<?> parentClass) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		JarFile jar = null;
		try {
			loadJar(jarFile);
			jar = new JarFile(jarFile);
	        Enumeration<JarEntry> entry = jar.entries();
	 
	        JarEntry jarEntry;
	        String jarEntryName, className;
	        Class<?> clazz;
	        while (entry.hasMoreElements()) {
	            jarEntry = entry.nextElement();
	            jarEntryName = jarEntry.getName();
	            if (jarEntryName.charAt(0) == '/') {
	                jarEntryName = jarEntryName.substring(1);
	            }
	            if (jarEntry.isDirectory() || !jarEntryName.endsWith(".class")) {
	                continue;
	            }
	            className = jarEntryName.substring(0, jarEntryName.length() - 6);
	            clazz = loadClass(className.replace("/", "."));
	            if (clazz != null && parentClass != clazz && parentClass.isAssignableFrom(clazz)) {
	                classes.add(clazz);
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(jar != null) {
				try {
					jar.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return classes;
	}
	
	public static Set<Class<?>> getClassesFromJar(String jarFile) {
		return getClassesFromJar(new File(jarFile));
	}
	
	public static Set<Class<?>> getClasses(String packageName,Class<?> parentClass) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        String pkgDirName = packageName.replace('.', '/');
        try {
            Enumeration<URL> urls = ClassHelper.class.getClassLoader().getResources(pkgDirName);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClasses(packageName, filePath, classes,parentClass);
                } else if ("jar".equals(protocol)) {
                    JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    findClasses(packageName, jar, classes,parentClass);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return classes;
	}
	
    /**
     * 获取packageName下面所有的Class
     *
     * @param packageName
     * @return
     */
    public static Set<Class<?>> getClasses(String packageName) {
       return getClasses(packageName,Object.class);
    }
 
    private static void findClasses(String packageName, String packagePath, Set<Class<?>> classes, Class<?> parentClass) {
        File packageDir = new File(packagePath);
        if (!packageDir.exists() || !packageDir.isDirectory()) {
            return;
        }
        final String classSuf = ".class";
        File[] files = packageDir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.isDirectory() || pathname.getName().endsWith(classSuf);
			}
		});
 
        if (files == null || files.length == 0) {
            return;
        }
        String className;
        for (File file : files) {
            if (file.isDirectory()) {
                findClasses(packageName + "." + file.getName(),packagePath + "/" + file.getName(),classes,parentClass);
                continue;
            }
            className = file.getName();
            className = className.substring(0, className.length() - classSuf.length());
            Class<?> clazz = loadClass(packageName + "." + className);
            if (clazz != null && parentClass != clazz && parentClass.isAssignableFrom(clazz)) {
                classes.add(clazz);
            }
        }
    }
 
    private static void findClasses(String packageName, JarFile jar, Set<Class<?>> classes,Class<?> parentClass) {
        String packageDir = packageName.replace(".", "/");
        Enumeration<JarEntry> entries = jar.entries();
        JarEntry jarEntry ;
        String jarEntryName, className;
        Class<?> clazz;
        while (entries.hasMoreElements()) {
            jarEntry = entries.nextElement();
            jarEntryName = jarEntry.getName();
            if (jarEntryName.charAt(0) == '/') {
                jarEntryName = jarEntryName.substring(1);
            }
            String classSuf = ".class";
            if (jarEntry.isDirectory() || !jarEntryName.startsWith(packageDir) || !jarEntryName.endsWith(classSuf)) {
                continue;
            }
            className = jarEntryName.substring(0, jarEntryName.length() - classSuf.length());
            clazz = loadClass(className.replace("/", "."));
            if (clazz != null && parentClass != clazz && parentClass.isAssignableFrom(clazz)) {
                classes.add(clazz);
            }
        }
    }
    
    private static Class<?> loadClass(String className) {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
 
}
 
