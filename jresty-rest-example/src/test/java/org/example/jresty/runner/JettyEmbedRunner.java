package org.example.jresty.runner;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyEmbedRunner {

	public static void main(String[] args) throws Exception {
		//configuration settings80
		int webAppPort = Integer.parseInt(System.getProperty("http.runner.port", "8080"));
				
		// WebRoot directory defined in maven-web applications with build path output './target/classes'
		String webAppRootPath = System.getProperty("http.runner.root", "src/main/webapp");
				
		Server server = new Server(webAppPort);

		WebAppContext context = new WebAppContext();
		context.setResourceBase(webAppRootPath);
		context.setContextPath("/");
		// context.setParentLoaderPriority(true);
		server.setHandler(context);

		server.start();
		server.join();
	}
}
