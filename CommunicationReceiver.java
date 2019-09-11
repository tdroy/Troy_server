import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunicationReceiver {
	
	public static void main(String[] args) throws Exception {
		
		 System.out.println("************************************");
		 System.out.println("****      Troy Chat Client     ****");
		 System.out.println("************************************");
			
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 
	     Socket sock = new Socket("localhost", 7777);
		 System.out.println(dateFormat.format(new Date()) + " Client connected to 7777 \n");
		 
	     InputStream istream = sock.getInputStream();
	     BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
	     
	     String receiveMessage;               
	     while(true)
	     {
	        if((receiveMessage = receiveRead.readLine()) != null) //receive from server
	        {
	            System.out.println(receiveMessage);
	        }         
	     }     
	}
}