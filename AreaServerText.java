/* a sequential server
   protocol
   client:   radius CRLF
         send radius as a text string in line
		 negative to tell the end of service
	 server:   area CRLF
    author: Eric Chen
*/	 
import java.io.*;
import java.net.*;
import java.util.*;

// message format:  text string CRLF
public class AreaServerText{
   private static final int PORT = 8000; //default port number
	private ServerSocket serverSocket;
	
	public static void main ( String [] args ){
	   int port = PORT;
		if ( args.length == 1 )
		   port = Integer.parseInt( args[0] );  //user given port number
		new AreaServerText( port );
	}
	
	public AreaServerText( int port ){
	  // create a server socket
	  try {
	  	  serverSocket = new ServerSocket( port );
	  } catch ( IOException e ) {
	     System.err.println( "Error in creation of the server socket");
		  System.exit( 0 );
	  }
	  
	  for( ; ; ) {  // infinite loop to serve clients, one by one
        try {
	       // listen for a connection, wait for the client request
		    Socket socket = serverSocket.accept( ) ;
			 
			// create input/output streams
			//use readLine( ) to read each message from the client
			BufferedReader inputFromClient = new BufferedReader( new InputStreamReader( socket.getInputStream( )));
			
			// used for printing the area in a text line to the client
		    PrintStream outputToClient = new PrintStream(new BufferedOutputStream( socket.getOutputStream( ) ));
		   
			// loop to service a client, until negative number received; or connection closed
		    while ( true ){
			    //receive radius from client
				//String cmd;
				double radius;
				try {
		            String cmd= inputFromClient.readLine(); //read the radius as a text string
				    radius = Double.parseDouble( cmd ) ; //convert it to double
					if (radius < 0.0 ){ //protocol: negative to terminate 
					   System.out.println("Negative, complete the service");
					   break;
					}
		        } catch (  NullPointerException e){
				   System.out.println("completed service for " + socket.getInetAddress().toString());
				   break;
				}
		      //compute area = pi * radius ^2
		      double area = Math.PI * radius * radius;
		  
		      //send area to client
		      outputToClient.print( area + "\r\n" ); //defined by the protocol
				outputToClient.flush( );
			 } 
			
			} catch ( IOException e ){
			  System.err.println( e );
			}
		}
	}
}