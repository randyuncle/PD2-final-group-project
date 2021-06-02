package net;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The connection for client server to main server.
 * It is important that this connects to localhost instead of the author's computer,
 * which means that you need to have a mysql database and a server in use :)
 * @author randy_uncle at NCKU
 *
 */

public class MainClientServer extends Thread
{
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private String command;
	
	private String serverIP = ClientServer.SERVER_IP;
	private int serverPort = ClientServer.SERVERPORT;
	
	public MainClientServer(String command) 
	{
		this.command = command;
		
		try 
		{
			System.out.println("Connecting to server");
			socket = new Socket(serverIP, serverPort);
			
			System.out.println("Connection succsess");
		} 
		catch (IOException e) 
		{	 
			e.printStackTrace();	
		}
	}
	
	@Override
	public void run() 
	{
		try 
		{	
			while(socket != null) 
			{	
				output = new PrintWriter(socket.getOutputStream());
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
				
				if(command.equals("quit")) 
				{
					socket.close();
					break;
				}
				else 
				{
					if(command.equals("Login")) 
					{
						output.println(this.command);
				    	output.println(ClientServer.NAME);
				    	output.println(ClientServer.PASSWORD);
				    	output.flush();
				    	
				    	ClientServer.ID = Integer.parseInt(input.readLine());
				    	ClientServer.MAX_POINT = Integer.parseInt(input.readLine());
				    	
				    	output.println("quit");
				    	output.flush();
				    	output.close();
				    	socket.close();
					}
					else if(command.equals("Set Account")) 
					{
						output.println(this.command);
				    	output.flush();
				    	
				    	output.println(ClientServer.NAME);
				    	output.println(ClientServer.PASSWORD);
				    	output.flush();
				    	
				    	output.println("quit");
				    	output.flush();
				    	output.close();
				    	socket.close();
					}
					else if(command.equals("Upload Grades")) 
					{
						output.println(this.command);
				    	output.flush();
				    	
				    	output.println(ClientServer.ID);				    	
				    	output.println(ClientServer.MAX_POINT);
				    	output.flush();
				    	
				    	
				    	output.println("quit");
				    	output.flush();
				    	output.close();
				    	socket.close();
					}
				}
			}
		} 
		catch(IOException e)
		{
			System.out.println("Connection quit");
			//e.printStackTrace(); 
		}
	}
	
	

}
