import java.util.concurrent.atomic.AtomicInteger;

public class Statistiche {
    private final AtomicInteger tentativi = new AtomicInteger();
    private final AtomicInteger riuscite = new AtomicInteger();
    private final AtomicInteger fallite = new AtomicInteger();

    public void registraTentativo()  { tentativi.incrementAndGet(); }
    public void registraSuccesso()   { riuscite.incrementAndGet(); }
    public void registraFallimento() { fallite.incrementAndGet(); }

    public int getTentativi() { return tentativi.get(); }
    public int getRiuscite()  { return riuscite.get(); }
    public int getFallite()   { return fallite.get(); }
}