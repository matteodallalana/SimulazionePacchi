import java.util.concurrent.ThreadLocalRandom;

public class Operatore implements Runnable {
    private final int id;
    private final CentroSmistamento centro;
    private final Statistiche stats;
    private final long lavoroMs;
    private int pacchiAssegnati;

    public Operatore(int id, CentroSmistamento centro, Statistiche stats, long lavoroMs) {
        this.id = id;
        this.centro = centro;
        this.stats = stats;
        this.lavoroMs = lavoroMs;
    }

    @Override
    public void run() {
        while (true) {
            stats.registraTentativo();
            Pacco p = centro.assegnaPacco();
            if (p == null) {
                stats.registraFallimento();
                return; // Termina quando non ci sono più pacchi disponibili
            }
            stats.registraSuccesso();
            pacchiAssegnati++;
            preparaSpedizione();
        }
    }

    private void preparaSpedizione() {
        if (lavoroMs > 0) {
            try { Thread.sleep(ThreadLocalRandom.current().nextLong(lavoroMs + 1)); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    public int getId() { return id; }
    public int getPacchiAssegnati() { return pacchiAssegnati; }
}