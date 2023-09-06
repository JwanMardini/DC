
import java.io.*;
import java.net.*;
public class AreaServer{
    private static final int PORT = 8000;
    private ServerSocket serverSocket;
    public static void main ( String [] args ){
        int port = PORT;
        if ( args.length == 1 )
            port = Integer.parseInt( args[0] );
        new AreaServer( port );
    }
    public AreaServer( int port ){
// create a server socket
        try {
            serverSocket = new ServerSocket( port );
        } catch ( IOException e ) {
            System.err.println( "Error in creation of the server socket");
            System.exit( 0 );
        }
        while ( true ) {
            try {
// listen for a connection
                Socket socket = serverSocket.accept( ) ;
// create data input/output streams
                DataInputStream inputFromClient = new
                        DataInputStream( socket.getInputStream( ));
                DataOutputStream outputToClient = new
                        DataOutputStream( socket.getOutputStream( ) );
// loop to service a client
                while ( true ){
//receive radius from client
                    double radius = inputFromClient.readDouble();
//compute area = pi * radius ^2
                    double area = Math.PI * radius * radius;
//send area to client
                    outputToClient.writeDouble( area ); outputToClient.flush( );
                }
            } catch ( IOException e ){
                System.err.println( e );
            }
        }
    }
}
