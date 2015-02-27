package edu.ucsb.ns202.online;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import edu.ucsb.ns202.IServerRunner;
import edu.ucsb.ns202.ParamUtilities;

public class ServerRunner implements IServerRunner{
	
	private static HashtagQueryProcessor hashtagQueryProcessor;

	public void run() {
		HttpServer server;
		try {
			server = HttpServer.create(new InetSocketAddress(8000), 0);
			server.createContext("/", new StaticContentHandler());
		    server.createContext("/request", new HashTagRequestHandler());
		    server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
		    server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	class HashTagRequestHandler implements HttpHandler {
	    public void handle(HttpExchange t) throws IOException {
	    	String query = t.getRequestURI().getQuery();
	    	// Will get a map with as a key the parameter and as a value the value
	    	// This serves the requests like ?param1=value1&param2=value2 ... 
	    	Map<String, String> params = ParamUtilities.queryToMap(query);
	    	
	    	hashtagQueryProcessor = new HashtagQueryProcessor(params.get("hashtag"));
	    	JSONObject jsonResponse = null;
	    	
			try {
				jsonResponse = hashtagQueryProcessor.query();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
	    	String strJsonResponse = jsonResponse.toString();
	    	
	    	Headers h = t.getResponseHeaders();
	    	h.add("Content-Type", "application/json");
			t.sendResponseHeaders(200, strJsonResponse.length());
	    	
	    	OutputStream os = t.getResponseBody();
	    	
	    	os.write(strJsonResponse.getBytes());
			os.close();
	    }
	 }
	
	class StaticContentHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			URI uri = t.getRequestURI();
			String path = uri.getPath();
			
			if (path.equals("/")) {
				
				InputStream htmlFileInputStream = ServerRunner.class.getClassLoader().getResourceAsStream("resources/index.html");
					
				t.sendResponseHeaders(200, 0);
				OutputStream os = t.getResponseBody();
				final byte[] buffer = new byte[0x10000];
				int count = 0;
				while ((count = htmlFileInputStream.read(buffer)) >= 0) {
					os.write(buffer,0,count);
				}
				os.close();
				htmlFileInputStream.close();
			}
			else if (path.equals("/visualization.js")) {
				
				InputStream htmlFileInputStream = ServerRunner.class.getClassLoader().getResourceAsStream("resources/visualization.js");
				
				Headers h = t.getResponseHeaders();
				h.add("Content-Type", "application/javascript");
				
				t.sendResponseHeaders(200, 0);
				OutputStream os = t.getResponseBody();
				final byte[] buffer = new byte[0x10000];
				int count = 0;
				while ((count = htmlFileInputStream.read(buffer)) >= 0) {
					os.write(buffer,0,count);
				}
				os.close();
				htmlFileInputStream.close();
			}
		}
	}

}
