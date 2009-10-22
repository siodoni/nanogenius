package nanogenius;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class Main extends MIDlet {

    private Canvas canvas;

    public Main() {
        canvas = new NanoGenius(this);
    }

    public void startApp() {
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        notifyDestroyed();
    }
}
