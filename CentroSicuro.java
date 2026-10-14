import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class CentroSicuro implements CentroSmistamento {
    private final int iniziali;
    private final Deque<Pacco> disponibili = new ArrayDeque<>();
    private final List<Integer> registro = new ArrayList<>();
    private final long ritardoMs;

    public CentroSicuro(int pacchi, long ritardoMs) {
        this.iniziali = pacchi;
        this.ritardoMs = ritardoMs;
        for (int i = 0; i < pacchi; i++) {
            disponibili.add(new Pacco(i));
        }
    }

    @Override
    public synchronized Pacco assegnaPacco() {
        if (disponibili.isEmpty()) return null;
        Pacco p = disponibili.pollFirst();
        pausa();
        registro.add(p.id());
        return p;
    }

    private void pausa() {
        if (ritardoMs > 0) {
            try { Thread.sleep(ritardoMs); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    @Override public int getPacchiIniziali() { return iniziali; }
    @Override public synchronized int getPacchiAssegnati() { return registro.size(); }
    @Override public synchronized int getPacchiRimanenti() { return disponibili.size(); }
    @Override public synchronized List<Integer> getRegistroAssegnazioni() { return new ArrayList<>(registro); }
}