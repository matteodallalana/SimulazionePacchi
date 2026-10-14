import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Versione VOLUTAMENTE SCORRETTA, solo a scopo dimostrativo (Fase 3 e 5).
 * Il metodo e' un "check-then-act" non protetto: tra la lettura delle
 * disponibilita' e il decremento un altro thread puo' inserirsi.
 */
public class CentroNonSicuro implements CentroSmistamento {
    private final int iniziali;
    private int disponibili;      // dato condiviso NON protetto
    private int assegnati;        // dato condiviso NON protetto
    private final List<Integer> registro = Collections.synchronizedList(new ArrayList<>());
    private final long ritardoMs;

    public CentroNonSicuro(int pacchi, long ritardoMs) {
        this.iniziali = pacchi;
        this.disponibili = pacchi;
        this.ritardoMs = ritardoMs;
    }

    @Override
    public Pacco assegnaPacco() {
        if (disponibili > 0) {                    // 1. verifica
            pausa();                              //    <-- un altro thread entra qui
            int id = iniziali - disponibili;      // 2. "sceglie" il pacco
            disponibili--;                        // 3. aggiorna (non atomico)
            assegnati++;
            registro.add(id);
            return new Pacco(id);
        }
        return null;
    }

    private void pausa() {
        if (ritardoMs > 0) {
            try { Thread.sleep(ritardoMs); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        }
    }

    @Override public int getPacchiIniziali() { return iniziali; }
    @Override public int getPacchiAssegnati() { return assegnati; }
    @Override public int getPacchiRimanenti() { return disponibili; }
    @Override public List<Integer> getRegistroAssegnazioni() { return new ArrayList<>(registro); }
}
