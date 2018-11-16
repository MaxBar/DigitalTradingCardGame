package NetworkClient;

import java.io.*;
import java.net.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class NetworkClient {
    private static NetworkClient instance = null;
    
    private DatagramSocket socket;
    private Thread t;
    private int serverPort;
    private final int MSG_SIZE = 512;
    private final int SLEEP_MS = 1000;
    private InetAddress serverAddress;
    private LinkedBlockingDeque<String> msgQueue = new LinkedBlockingDeque<>();
    
    private NetworkClient() throws SocketException, UnknownHostException {
        String hostname = "10.155.90.41";
        int serverPort = 150;
        serverAddress = InetAddress.getByName(hostname);
        this.serverPort = serverPort;
        
        
        final int clientPort = 150+1;
        System.out.println(InetAddress.getByName(hostname));
        socket = new DatagramSocket(clientPort);
        socket.setSoTimeout(500);
        
        startThread();
    }
    
    public static NetworkClient getInstance() {
        if(instance == null) {
            try {
                instance = new NetworkClient();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return instance;
        }
        return instance;
    }
    
    public void stop(){
        if (t !=  null) {
            t.interrupt();
        }
    }
    
    public String pollMessage(){
        try {
            
            return msgQueue.pollFirst(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }
    public void sendMessageToServer(String msg) throws IOException {
        byte[] buffer = msg.getBytes();
        DatagramPacket request = new DatagramPacket(buffer, buffer.length, this.serverAddress, this.serverPort);
        socket.send(request);
    }
    
    private void startThread() {
        Runnable r = () -> {
            try {
                loop();
            } catch (SocketTimeoutException ex) {
                System.out.println(getClass().getSimpleName() + " - Timeout error: " + ex.getMessage());
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println(getClass().getSimpleName() + " - Client error: " + ex.getMessage());
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                // Expected to happen on Quit-event
                System.out.println(getClass().getSimpleName() + " - Thread interrupted.");
            }
        };
        
        t = new Thread(r);
        t.start();
    }
    
    private String getMessageFromServer() throws InterruptedException {
        byte[] buffer = new byte[MSG_SIZE];
        
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        
        try {
            socket.receive(response);
        } catch (Exception ex) {
            return "";
        }
        
        return new String(buffer, 0, response.getLength());
    }
    
    private void loop() throws IOException, InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            String response = getMessageFromServer();
            if (!response.isEmpty()) {
                msgQueue.addLast(response);
            }
        }
    }
    
    
}
