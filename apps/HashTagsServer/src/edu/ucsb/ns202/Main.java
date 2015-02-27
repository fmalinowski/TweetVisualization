package edu.ucsb.ns202;

public class Main {
	
	public static void main(String[] args) {
		IServerRunner serverRunner;
		String mode = "offline";
		
		for(int i = 0; i < args.length; i++) {
			if (args[i].indexOf("=") >= 0) {
				String param = args[i].split("=")[0];
				String value = args[i].split("=")[1];
				
				switch(param) {
					case "--mode":
						mode = value;
						break;
				}
			}
		}
		
		serverRunner = mode.equals("online") ?  new edu.ucsb.ns202.online.ServerRunner() :
			new edu.ucsb.ns202.offline.ServerRunner();
		
		serverRunner.run();
	}

}
