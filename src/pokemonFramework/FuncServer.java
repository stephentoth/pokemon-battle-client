package pokemonFramework;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FuncServer {
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private int port;
	
	public FuncServer(ObjectInputStream in, ObjectOutputStream out, Socket socket, int port) {
		super();
		this.in = in;
		this.out = out;
		this.socket = socket;
		this.port = port;
	}

	public List<Server> findIPs() {
		List<Server> ips = new ArrayList<Server>();
		for (int i = 0; i <= 255; i++) {
			try {
				socket = new Socket();
				socket.connect(new InetSocketAddress("192.168.1." + i, port), 50);
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				ips.add(getServerInfo());
				System.out.println("Yep");
				socket.close();
				socket = null;
			} catch (IOException e) {
				System.out.println("Nope");
				socket = null;
			}
		}
		return ips;
	}

	public void connect(String Ip, int Port) {
		try {
			socket = new Socket(Ip, Port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("No I/O");
		}
	}

	public Server getServerInfo() {
		Object input = null;
		sendData(new String("info"));
		try {
			input = in.readObject();
			while (input == null || !(input instanceof Server)) {
				input = in.readObject();
				System.out.println("null");
			}
			System.out.println("Info");
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			System.out.println("nope");
		}
		return (Server) input;
	}
	
	public void sendMove(Bundle clientBundle, Move move) {
		System.out.println("Sending Client Bundle");
		sendData(clientBundle);
	}

	// data

	public void sendData(String send) {
		try {
			out.writeObject(new String(send));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendData(Bundle bundle) {
		try {
			out.writeObject(bundle);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
