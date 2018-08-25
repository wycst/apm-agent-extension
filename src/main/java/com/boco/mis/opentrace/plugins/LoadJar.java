package com.boco.mis.opentrace.plugins;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadJar {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
		// TODO Auto-generated method stub
		File file = new File( "xxxxx.jar");
		URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();

        Method _addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        _addURL.setAccessible(true);
        _addURL.invoke(loader, file.toURI().toURL());
	}

}
