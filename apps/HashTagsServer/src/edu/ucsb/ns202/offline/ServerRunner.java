package edu.ucsb.ns202.offline;

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
import edu.ucsb.ns202.graph.HashtagGraph;
import edu.ucsb.ns202.graph.SortedHashtagGraph;

public class ServerRunner implements IServerRunner{
	
	private static HashtagQueryProcessor hashtagQueryProcessor;
	private static HashtagGraphBuilder hashtagGraphBuilder;
	private static SortedHashtagGraph hashtagGraph;

	public void run() {
		HttpServer server;
		ServerRunner.hashtagGraphBuilder = new HashtagGraphBuilder();
		
		System.out.println("Building Hashtag graph. Please Wait...");
		ServerRunner.hashtagGraph = hashtagGraphBuilder.buildGraph();
		System.out.println("Hashtag graph built.");
		System.out.println("Launching web server.");
		
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
	    	byte[] answerForClient;
	    	String query = t.getRequestURI().getQuery();
	    	// Will get a map with as a key the parameter and as a value the value
	    	// This serves the requests like ?param1=value1&param2=value2 ... 
	    	Map<String, String> params = ParamUtilities.queryToMap(query);
	    	
	    	hashtagQueryProcessor = new HashtagQueryProcessor(hashtagGraph, params.get("hashtag"));
	    	JSONObject jsonResponse;
			
	    	jsonResponse = hashtagQueryProcessor.query();
			
	    	String strJsonResponse = jsonResponse.toString();
	    	answerForClient = strJsonResponse.getBytes();
	    	
	    	Headers h = t.getResponseHeaders();
	    	h.add("Content-Type", "application/json");
	    	
			t.sendResponseHeaders(200, answerForClient.length);
	    	
	    	OutputStream os = t.getResponseBody();
	    	
	    	os.write(answerForClient);
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
			else if (path.equals("/visualization.js") || path.equals("/base.js")) {
				
				InputStream htmlFileInputStream = ServerRunner.class.getClassLoader().getResourceAsStream("resources" + path);
				
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
			else if (path.equals("/style.css") || path.equals("/style_hashtag_graph.css")) {
				
				InputStream htmlFileInputStream = ServerRunner.class.getClassLoader().getResourceAsStream("resources" + path);
				
				Headers h = t.getResponseHeaders();
				h.add("Content-Type", "text/css");
				
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
