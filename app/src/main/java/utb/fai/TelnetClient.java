package utb.fai;

import java.io.*;
import java.net.Socket;

public class TelnetClient {

    private String serverIp;
    private int port;
    private Socket socket;

    public TelnetClient(String serverIp, int port) {
        this.serverIp = serverIp;
        this.port = port;
    }

    public void run() {
        try {
            socket = new Socket(serverIp, port);

            Thread readerThread = new Thread(this::readFromServer);
            Thread writerThread = new Thread(this::writeToServer);
            readerThread.start();
            writerThread.start();

            writerThread.join();
            readerThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readFromServer() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        } finally {
            close();
        }
    }

    private void writeToServer() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("QUIT")) {
                    break;
                }
                out.println(line);
            }
        } catch (IOException e) {
            // ignore
        } finally {
            close();
        }
    }

    private void close() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}