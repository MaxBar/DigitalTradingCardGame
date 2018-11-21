package server;

import player.Player;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class NetworkServer {
    private static NetworkServer instance = null;
    
    private DatagramSocket socket;
    private List<String> listQuotes = new ArrayList<String>();
    private Thread t;
    private final int MSG_SIZE = 512;
    private final int SLEEP_MS = 1;
    private LinkedBlockingDeque<String> msgQueue = new LinkedBlockingDeque<>();
    private ArrayList<Player> players = new ArrayList<>();
    
    private ArrayList<DatagramPacket> clientIP = new ArrayList<>();
    
    private NetworkServer() throws SocketException{
        int port = 150;
        socket = new DatagramSocket(port);
        socket.setSoTimeout(500);
        
        startThread();
    }
    
    public static NetworkServer getInstance() {
        if(instance == null) {
            try {
                instance = new NetworkServer();
            } catch (SocketException e) {
                e.printStackTrace();
            }
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
    
    // https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/LinkedBlockingDeque.html
    private void startThread()  {
        Runnable r = () -> {
            try {
                loop();
            } catch (SocketException ex) {
                System.out.println(getClass().getSimpleName() + " - Socket error: " + ex.getMessage());
            } catch (IOException ex) {
                System.out.println(getClass().getSimpleName() + " - I/O error: " + ex.getMessage());
            } catch (InterruptedException ex) {
                // Expected to happen on Quit-event
                System.out.println(getClass().getSimpleName() + " - Thread interrupted.");
            }
        };
        
        t = new Thread(r);
        t.start();
    }
    
    //TODO make public so that server can send to client without client sending first
    public void sendMsgToClient(String msg, DatagramPacket clientRequest) throws IOException {
        
        byte[] buffer = msg.getBytes();
        
        InetAddress clientAddress = clientRequest.getAddress();
        final int clientPort = clientRequest.getPort();
        
        DatagramPacket response = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
        socket.send(response);
    }
    
    private void loop() throws IOException, InterruptedException {
        while (!Thread.currentThread().isInterrupted()) {
            DatagramPacket clientRequest = new DatagramPacket(new byte[MSG_SIZE], MSG_SIZE);
            
            try {
                socket.receive(clientRequest);
            } catch (IOException ex) {
                Thread.sleep(SLEEP_MS);
                continue;
            }
            
            handleMessages(clientRequest);
        }
    }
    
    private void handleMessages(DatagramPacket clientRequest) throws IOException, InterruptedException {
        String clientMsg = new String(clientRequest.getData(), 0, clientRequest.getLength());
        
        //TODO FIX NEW WAY TO STORE IP
        if(!clientIP.isEmpty()) {
            for(int i = 0; i < 2; ++i) {//clientIP.size()
                if(!clientIP.get(i).getAddress().equals(clientRequest.getAddress())) {
                    clientIP.add(clientRequest);
                }
            }
        } else {
            clientIP.add(clientRequest);
        }
        
        msgQueue.addLast(clientMsg);
        Server.getInstance().receiveCommand(msgQueue.takeFirst());
        
        System.out.println(clientMsg);
    }
    
    public ArrayList<DatagramPacket> getClientIP() {
        return clientIP;
    }
}