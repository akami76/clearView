package com.akami.server;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;


@ClientEndpoint
public class CVWebSocketClient extends Endpoint{

	private final String uri="ws://localhost:8080/clearview/cvbroadcasting";
		   private static Session session;

		    CVWebSocketClient(){
		      try{
		    	  System.out.println("1create ......");
		    	 URI clientURI = new URI(uri);
		         WebSocketContainer container=ContainerProvider.getWebSocketContainer();
		         ClientEndpointConfig clientConfiguration = ClientEndpointConfig.Builder.create().build();
		         
		         //session = container.connectToServer(CVWebSocketClient.class, clientConfiguration, clientURI);
		         
		         session = container.connectToServer(CVWebSocketClient.class, clientConfiguration, clientURI);
		         
		         System.out.println("2create ......");
		      }catch(Exception ex){
		    	  ex.printStackTrace();

		      }
		   }

		   @OnOpen
		   public void onOpen(Session session){
		      this.session=session;
		      System.out.println("-----------------asdfasfsfa-" );
		   }

		   @OnMessage
		   public void onMessage(String message, Session session){
		      //clientWindow.writeServerMessage(message);
			   
			   System.out.println("## client:"+session.getContainer());
		   }

		   public void sendMessage(String message){
		      try {
		         session.getBasicRemote().sendText(message);
		      } catch (IOException ex) {

		      }
		   }
	
		   public static void main(String a[]){
			   CVWebSocketClient client  = new CVWebSocketClient();
			   System.out.println("client:"+client);
			   client.sendMessage("asdsaff");
			  // client.
		   }

		@Override
		public void onOpen(Session arg0, EndpointConfig arg1) {
			this.session=session;
		      System.out.println("-----------------asdfasfsfa-" );
			
		}
		
		
		  

}
