package app;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java app.Main [coordinator|node|demo] ...");
            return;
        }
        switch (args[0]) {
            case "coordinator":
                Coordinator.main(slice(args, 1));
                break;
            case "node":
                Node.main(slice(args, 1));
                break;
            case "demo":
                Demo.main(slice(args, 1));
                break;
            default:
                System.out.println("Comando desconhecido: " + args[0]);
        }
    }
    private static String[] slice(String[] arr, int from) {
        if (arr.length <= from) return new String[0];
        String[] res = new String[arr.length - from];
        System.arraycopy(arr, from, res, 0, res.length);
        return res;
    }
}
