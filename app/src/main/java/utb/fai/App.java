package utb.fai;

public class App {

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int port = 23;

        if (args.length >= 1) {
            ip = args[0];
        }
        if (args.length >= 2) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port: " + args[1]);
                return;
            }
        }

        TelnetClient telnetClient = new TelnetClient(ip, port);
        telnetClient.run();
    }
}