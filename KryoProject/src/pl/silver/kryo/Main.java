package pl.silver.kryo;

public class Main {

	public static void main(String[] args) {
		new Main().run();
	}

	private void run() {
		ServerThread server = new ServerThread();
		Thread threadServer = new Thread(server);
		threadServer.setName("Server");
		threadServer.start();
		
		Thread threadClient = new Thread(new ClientThread());
		threadClient.setName("Client");
		threadClient.setDaemon(true);
		while(!server.isReady()){
			Thread.yield();
		}
		threadClient.start();
	}
}
