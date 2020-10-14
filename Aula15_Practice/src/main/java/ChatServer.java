import java.io.IOException;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session; 

import java.util.logging.Logger;
import java.util.logging.Level;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;

import java.util.ArrayList;
import java.util.List;


@ServerEndpoint("/chat")
public class ChatServer
{
	static List<Session> sessions = new ArrayList<Session>();
	Logger LOGGER = Logger.getLogger(ChatServer.class.getName());

	@OnOpen
	public void openSession(Session userSession)
	{
		sessions.add(userSession);
		LOGGER.log(Level.INFO, "usuario logado com id " + userSession.getId());
	}

	@OnClose
	public void closeSession(Session userSession)
	{
		sessions.remove(userSession);
		LOGGER.log(Level.INFO, "A conexão com o usuario " + userSession.getId() + " foi fechada");
	}

	@OnMessage
	public void handleMessage(String message) throws IOException
	{
		for(int i=0; i<sessions.size(); i++)
		{
			sessions.get(i).getBasicRemote().sendText(message);
		}
	}
}
