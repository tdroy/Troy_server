
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunicationServer {
	
	public static void main(String[] args)  {
		  System.out.println("************************************");
		  System.out.println("****      Troy Chat Server      ****");
		  System.out.println("************************************");
		
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		  
	      try {
			final ServerSocket sersockRecv = new ServerSocket(7777); //Receive socket.
			final ServerSocket sersockSend = new ServerSocket(6666); //Send Socket.
			
			System.out.println(dateFormat.format(new Date()) + " Server  ready for communication  Recive port 7777 and Send on 6666\n");
			
			final SenderReader sr = new SenderReader();
			
			for (int i=0; i < 2; i++)
				{
					new Thread(new Runnable()
					{	@Override
						public void run(){
							sr.Reader(sersockRecv);
						}
					}).start();
				}
			
			new Thread(new Runnable()
			{	@Override
				public void run(){
					sr.Sender(sersockSend);
				}
			}).start();
			
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	      
	}
}

class SenderReader{
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	StringBuffer message = new StringBuffer("");
    private Object lockReader = new Object();
    //private Object lockSender = new Object();
	
		void Reader(ServerSocket serverSocket){  
			System.out.println(dateFormat.format(new Date()) + " <Reader> Receiver " + Thread.currentThread().getId() + " connecting...");
			try {
				  Socket socket = serverSocket.accept();
			      OutputStream ostream = socket.getOutputStream(); 
			      PrintWriter pwrite = new PrintWriter(ostream, true);
			      System.out.println(dateFormat.format(new Date()) + " <Reader> Receiver " + Thread.currentThread().getId() + " connected..");
			      while(true)
			      {
		    		  synchronized (message){
		    		  System.out.println(dateFormat.format(new Date()) +  " <Reader> String message : " + message.toString());
			    	  if (!message.toString().equalsIgnoreCase(""))
			    	  {
			    		pwrite.println(" == " + message);             
			  	        pwrite.flush();
			  	        message.setLength(0);
			  	       }else{
			  	    	 pwrite.println(" == waiting for message.");
			  	    	 message.wait();
			    		  }
			    	  }
			      }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}  
		  
		void Sender(ServerSocket serverSocket){  
			System.out.println(dateFormat.format(new Date()) + " <Sender> Sender " + Thread.currentThread().getId() + " connecting...");
			try {
				  Socket socket = serverSocket.accept();
			      InputStream istream = socket.getInputStream();
			      BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
			      
			      String receiveMessage;
			      
			      while(true)
			      {
			           Thread.sleep(3000);
			    	  synchronized (message){
				       if((receiveMessage = receiveRead.readLine()) != null)  
				        {
				           System.out.println(dateFormat.format(new Date()) +  " <Sender> [ " + receiveMessage + " ]");         
				           message.append(receiveMessage);
				           System.out.println(dateFormat.format(new Date()) +  " <Sender> Sending message : " + message.toString());
				           message.notify();
				        } 
			    	  }
			      }
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
}
