package pl.silver.kryo;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.JsonSerialization;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerThread implements Runnable  {
	public final static int WRITE_BUFFER = 256 * 1024;
	public final static int READ_BUFFER = 256 * 1024;
	public final static int PORT_TCP = 56555;
	public final static int PORT_UDP = 56777;

	private AtomicBoolean ready = new AtomicBoolean(false);
	private Server server;
	private Listener listener;
	
	@Override
	public void run() {
		
		listener = new ServerListener();		
		server = new Server(WRITE_BUFFER, READ_BUFFER, new JsonSerialization());
		server.addListener(listener);
		server.start();
		try {
			server.bind(PORT_TCP, PORT_UDP);
			ready.set(true);
			System.out.println("Ready set to true : " + isReady());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isReady() {
		return ready.get();
	}
	
	class ServerListener extends Listener{
		@Override
		public void received(Connection connection, Object object) {
			System.out.println("Otrzymano od: " + connection.getID() + " obiekt: " + object);
			connection.sendTCP(new Pong());
		}
	}
}
