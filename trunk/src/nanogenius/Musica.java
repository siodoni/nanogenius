package nanogenius;

import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

/**
 * @author Flavio A. S. Ximenes
 * @author Francisco A. Tristão
 * @author Igor Mori Tristão
 */
public class Musica {

    public static final int MAIOR_OITAVA = 48, MENOR_OITAVA = -24;
    public static int oitava = 0;
    public static int cNat = 60;
    public static int cSus = 61;
    public static int dNat = 62;
    public static int dSus = 63;
    public static int eNat = 64;
    public static int fNat = 65;
    public static int fSus = 66;
    public static int gNat = 67;
    public static int gSus = 68;
    public static int aNat = 69;
    public static int aSus = 70;
    public static int bNat = 71;
    public static int volume = 10;
    public static StringBuffer nomeNota = new StringBuffer("");
    public static String mostraNota = "Sim", mostraTecla = "Sim";

    public static void tocaNota(int nota) {
        try {
            Manager.playTone(nota + oitava, 1, volume * 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tocaMusica(String musica) {
        try {
            InputStream is = getClass().getResourceAsStream(musica);
            Player player = Manager.createPlayer(is, "audio/mpeg");
            player.realize();
            VolumeControl vc = (VolumeControl) player.getControl("VolumeControl");
            if (vc != null) {
                vc.setLevel(100);
            }
            player.prefetch();
            player.start();
        } catch (Exception e) {
        }
    }
}
