import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Fase 4: confronto tra incremento semplice (non protetto), atomico e synchronized.
 * 50 thread x 100000 incrementi: il valore atteso e' 5.000.000.
 */
public class DemoContatori {
    static int semplice = 0;
    static final AtomicInteger atomico = new AtomicInteger();
    static int sincronizzato = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        final int T = 50, N = 100_000;
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < T; i++) {
            Thread t = new Thread(() -> {
                for (int k = 0; k < N; k++) {
                    semplice++;
                    atomico.incrementAndGet();
                    synchronized (lock) { sincronizzato++; }
                }
            });
            ts.add(t);
            t.start();
        }
        for (Thread t : ts) t.join();
        System.out.println("Atteso        : " + (long) T * N);
        System.out.println("int semplice  : " + semplice);
        System.out.println("AtomicInteger : " + atomico.get());
        System.out.println("synchronized  : " + sincronizzato);
    }
}
