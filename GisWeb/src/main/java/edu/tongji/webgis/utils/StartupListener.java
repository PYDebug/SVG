/**
 * 
 */
package edu.tongji.webgis.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Administrator
 *
 */
public class StartupListener implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		System.out.println("starting");
		
		initFileSystem();
		
		System.out.println("started");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	private void initFileSystem(){
		URL fileURL = StartupListener.class.getClassLoader().getResource("/");
		try {
			File classFolder = new File(fileURL.toURI());
			String path = classFolder.getAbsolutePath();
			path = path.substring(0, path.lastIndexOf("\\"));
			path = path + "\\resource\\svg\\files";
			System.out.println(path);
		
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
