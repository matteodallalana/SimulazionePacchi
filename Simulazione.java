import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Simulazione {

    public static void main(String[] args) throws InterruptedException {
        int pacchi      = args.length > 0 ? Integer.parseInt(args[0]) : 120;
        int operatori   = args.length > 1 ? Integer.parseInt(args[1]) : 50;
        long ritardo    = args.length > 2 ? Long.parseLong(args[2]) : 0;
        long lavoro     = args.length > 3 ? Long.parseLong(args[3]) : 2;

        CentroSmistamento centro = new CentroSicuro(pacchi, ritardo);
        Statistiche stats = new Statistiche();

        List<Operatore> addetti = new ArrayList<>();
        List<Thread> thread = new ArrayList<>();
        
        for (int i = 1; i <= operatori; i++) {
            Operatore o = new Operatore(i, centro, stats, lavoro);
            addetti.add(o);
            thread.add(new Thread(o, "Operatore-" + i));
        }

        for (Thread t : thread) t.start();
        for (Thread t : thread) t.join(); // Attende la conclusione di tutti gli operatori

        stampaReport(centro, stats, addetti);
    }

    private static void stampaReport(CentroSmistamento c, Statistiche s, List<Operatore> addetti) {
        List<Integer> reg = c.getRegistroAssegnazioni();
        Set<Integer> distinti = new HashSet<>(reg);
        int duplicati = reg.size() - distinti.size();
        int sommaPersonali = addetti.stream().mapToInt(Operatore::getPacchiAssegnati).sum();

        System.out.println("=== REPORT FINALE (SIMULAZIONE CORRETTA) ===");
        System.out.println("Pacchi iniziali              : " + c.getPacchiIniziali());
        System.out.println("Pacchi assegnati             : " + c.getPacchiAssegnati());
        System.out.println("Pacchi rimanenti             : " + c.getPacchiRimanenti());
        System.out.println("Tentativi effettuati         : " + s.getTentativi());
        System.out.println("Assegnazioni riuscite        : " + s.getRiuscite());
        System.out.println("Assegnazioni fallite         : " + s.getFallite());
        System.out.println("--- Verifiche di coerenza ---");
        System.out.println("Assegnati <= iniziali        : " + (c.getPacchiAssegnati() <= c.getPacchiIniziali()));
        System.out.println("Assegnati + rimanenti = ini  : " + (c.getPacchiAssegnati() + c.getPacchiRimanenti() == c.getPacchiIniziali()));
        System.out.println("Riuscite + fallite = tentat. : " + (s.getRiuscite() + s.getFallite() == s.getTentativi()));
        System.out.println("Riuscite = somma personale   : " + (s.getRiuscite() == sommaPersonali));
        System.out.println("Pacchi assegnati due volte   : " + duplicati);
    }
}