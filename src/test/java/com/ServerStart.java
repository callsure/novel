package com;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.resource.ResourceCollection;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Created by linrunshu on 16/6/13.
 */
public class ServerStart {
	public static final int DEFAULT_PORT = 8888;

	protected ServerStart() {
	}

	public static void main(String[] args) {
		int port = Integer.getInteger("port", DEFAULT_PORT);

		Server server = null;
		try {
			server = createServer(port);
			System.out.println(">>> STARTING JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			while (System.in.available() == 0) {
				Thread.sleep(5000);
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}

	private static Server createServer(int port) throws Exception{
		// use Eclipse JDT compiler
		//System.setProperty("org.apache.jasper.compiler.disablejsr199", "true");

		Server server = new Server(port);
		server.setStopAtShutdown(true);

		Resource[] resources = new Resource[] {
				Resource.newResource("src/main/webapp"),
				Resource.newResource("target/classes")
		};
		WebAppContext context = new WebAppContext();
		context.setBaseResource(new ResourceCollection(resources));
		context.setContextPath("/novel");
//		context.setServer(server);
//		context.setClassLoader(Thread.currentThread().getContextClassLoader());
//		context.setConfigurationDiscovered(true);
//		context.setParentLoaderPriority(true);
		server.setHandler(context);
		return server;
	}
}
