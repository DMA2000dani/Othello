package domini.shared;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

    public static Map<String, Integer> PARTIDA_MAP;

    static {
        PARTIDA_MAP = new HashMap<>();
        PARTIDA_MAP.put("idPartida", 0);
        PARTIDA_MAP.put("idJugadorN", 1);
        PARTIDA_MAP.put("idJugadorB", 2);
        PARTIDA_MAP.put("acabada", 11);
        PARTIDA_MAP.put("temps", 12);
        PARTIDA_MAP.put("torn", 13);
        PARTIDA_MAP.put("regles", 14);
        PARTIDA_MAP.put("millorJugada", 15);
        PARTIDA_MAP.put("mode", 16);
    }

}


