package app;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Coordinator {
    private static int version = 0;
    private static int lamport = 0;
    private static List<String> log = new ArrayList<>();
    private static List<PrintWriter> nodes = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws Exception {
        int port = 5000;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--port")) port = Integer.parseInt(args[i+1]);
        }
        ServerSocket server = new ServerSocket(port);
        System.out.println("Coordinator rodando na porta " + port);
        while (true) {
            Socket sock = server.accept();
            new Thread(() -> handle(sock)).start();
        }
    }

    private static void handle(Socket sock) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            nodes.add(out);
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("REQUEST")) {
                    lamport++;
                    String[] parts = line.split(" ", 2);
                    String nodeId = parts[1];
                    version++;
                    String entry = "Node " + nodeId + " incrementou contador (v" + version + ")";
                    log.add(entry);
                    broadcast("UPDATE " + version + " " + entry);
                    out.println("REPLY " + version);
                } else if (line.startsWith("SNAPSHOT")) {
                    sendSnapshotToAll();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro no coordenador: " + e.getMessage());
        }
    }

    private static void broadcast(String msg) {
        for (PrintWriter node : nodes) {
            try {
                node.println(msg);
            } catch (Exception e) {}
        }
    }

    private static void sendSnapshotToAll() {
        String payload = String.join("|", log);
        String msg = "SNAPSHOT " + version + " " + payload;
        broadcast(msg);
        System.out.println("Snapshot enviado para todos os nós. Versão " + version);
    }
}
