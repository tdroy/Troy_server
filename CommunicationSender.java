import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommunicationSender {
	
	public static void main(String[] args) throws Exception {
		
		 System.out.println("************************************");
		 System.out.println("****      Troy Chat Client     ****");
		 System.out.println("************************************");
			
		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 
	     Socket sock = new Socket("localhost", 6666);
		 System.out.println(dateFormat.format(new Date()) + " Sender connected to 6666 \n");
		 
	     BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
	     // sending to client (pwrite object)
	     OutputStream ostream = sock.getOutputStream(); 
	     PrintWriter pwrite = new PrintWriter(ostream, true);
	     
	     System.out.println(dateFormat.format(new Date()) + " Type and press Enter key");
	     
	     String sendMessage;               
	     while(true)
	     {
	        sendMessage = keyRead.readLine();  // keyboard reading
	        pwrite.println(dateFormat.format(new Date()) + " == " + sendMessage);       // sending to server
	        pwrite.flush();
        
	      }
		 
  }
}
