package pokemonFramework;

import java.io.Serializable;
import java.net.InetAddress;

public class Server implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = -5083127980262387416L;
private String serverName;
private InetAddress serverip;
private String gamemode;


public Server(String serverName, String gamemode, InetAddress serverip) {
	super();
	this.gamemode = gamemode;
	this.serverName = serverName;
	this.setServerip(serverip);
}

public String getGamemode() {
	return gamemode;
}
public void setGamemode(String gamemode) {
	this.gamemode = gamemode;
}

public InetAddress getServerip() {
	return serverip;
}


public void setServerip(InetAddress serverip) {
	this.serverip = serverip;
}

public String getServerName() {
	return serverName;
}

public void setServerName(String serverName) {
	this.serverName = serverName;
}

}
