package app;

import java.net.*;
import java.io.*;
import java.util.*;

public class Node {
    private static int id;
    private static int version = 0;
    private static List<String> log = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        id = 1;
        int coordPort = 5000;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--id")) id = Integer.parseInt(args[i+1]);
            if (args[i].equals("--coord-port")) coordPort = Integer.parseInt(args[i+1]);
        }
        Socket coord = new Socket("127.0.0.1", coordPort);
        BufferedReader in = new BufferedReader(new InputStreamReader(coord.getInputStream()));
        PrintWriter out = new PrintWriter(coord.getOutputStream(), true);

        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("UPDATE")) {
                        String[] parts = line.split(" ", 3);
                        version = Integer.parseInt(parts[1]);
                        log.add(parts[2]);
                        System.out.println("Node " + id + " recebeu: " + parts[2]);
                    } else if (line.startsWith("SNAPSHOT")) {
                        String[] parts = line.split(" ", 3);
                        version = Integer.parseInt(parts[1]);
                        if (parts.length > 2 && !parts[2].isEmpty()) {
                            log = new ArrayList<>(Arrays.asList(parts[2].split("\\|")));
                        }
                        System.out.println("Node " + id + " sincronizado com snapshot.");
                    }
                }
            } catch (Exception e) {}
        }).start();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Node " + id + "> ");
            String cmd = sc.nextLine();
            if (cmd.equals("inc")) {
                out.println("REQUEST " + id);
            } else if (cmd.equals("status")) {
                System.out.println("Node " + id + " v" + version + " log=" + log);
            } else if (cmd.equals("snapshot")) {
                out.println("SNAPSHOT " + id);
            } else if (cmd.equals("quit")) {
                break;
            }
        }
        coord.close();
    }
}
