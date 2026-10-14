import java.util.List;

/** Contratto comune per il centro di smistamento. */
public interface CentroSmistamento {
    Pacco assegnaPacco();
    int getPacchiIniziali();
    int getPacchiAssegnati();
    int getPacchiRimanenti();
    List<Integer> getRegistroAssegnazioni();
}