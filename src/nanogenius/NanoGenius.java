package nanogenius;

import javax.microedition.lcdui.*;
import java.util.Random;

public class NanoGenius extends Canvas implements CommandListener {

    private Main game;
    private Command cmdSair;
    private Command cmdLoop;
    private int largura, altura, percBorda, curBlock, curLayout = 0, curSample=0, emJogo=0;
    private StringBuffer sequencia = new StringBuffer();
    public static Random random = new Random();

    public NanoGenius(Main midlet) {
        largura = getWidth();
        altura = getHeight();
        percBorda = (getWidth() * 10) / 100;
        curBlock = 0;
        Escala.oitava = 12;

        //setFullScreenMode(true);
        this.game = midlet;
        cmdSair = new Command("Sair", Command.EXIT, 0);
        addCommand(cmdSair);
        cmdLoop = new Command("Samplear", Command.ITEM, 0);
        addCommand(cmdLoop);
        setCommandListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Cor.BRANCO);
        g.fillRect(0, 0, largura, altura);

        g.setColor(Cor.PRETO);
        g.fillArc(0, 0, largura, largura, 0, 360);

        if (curBlock == 1) {
            Escala.tocaNota(Escala.cNat);
        }
        if (curBlock == 2) {
            Escala.tocaNota(Escala.eNat);
        }
        if (curBlock == 3) {
            Escala.tocaNota(Escala.fNat);
        }
        if (curBlock == 4) {
            Escala.tocaNota(Escala.gNat);
        }
        if (curBlock == 5) {
            Escala.tocaNota(Escala.aNat);
        }

        g.setColor(curBlock == 1 ? Cor.VERDE : Cor.VERDE_ESCURO);
        g.fillArc(0, 0, largura - percBorda, largura - percBorda, 90, 90);

        g.setColor(curBlock == 2 ? Cor.VERMELHO : Cor.VERMELHO_ESCURO);
        g.fillArc(percBorda, 0, largura - percBorda, largura - percBorda, 360, 90);

        g.setColor(curBlock == 3 ? Cor.AMARELO : Cor.AMARELO_ESCURO);
        g.fillArc(0, percBorda, largura - percBorda, largura - percBorda, 180, 90);

        g.setColor(curBlock == 4 ? Cor.AZUL : Cor.AZUL_ESCURO);
        g.fillArc(percBorda, percBorda, largura - percBorda, largura - percBorda, 360, -90);

        g.setColor(Cor.PRETO);
        g.fillArc((largura / 4) - (percBorda / 2), (largura / 4) - (percBorda / 2), (largura / 2) + percBorda, (largura / 2) + percBorda, 0, 360);

        g.setColor(curBlock == 5 ? Cor.ROXO : Cor.ROXO_ESCURO);
        g.fillArc(largura / 3, largura / 3, largura / 3, largura / 3, 0, 360);
    }

    protected void pausa(int tempo) {
            try {
                Thread.sleep(tempo);
            } catch (InterruptedException ex) {}
    }

    protected void piscaBloco(int bloco, int tempo) {
            curBlock=bloco;
            repaint();
            serviceRepaints();
            pausa(tempo);
            curBlock = 0;
            repaint();
            serviceRepaints();
    }

    protected void keyPressed(int keyCode) {

        int tecla = getGameAction(keyCode);

        if ( emJogo == 0 ) {
            if (keyCode == KEY_NUM1) {
                piscaBloco(1, 600);
            }
            if (keyCode == KEY_NUM3) {
                piscaBloco(2, 600);
            }
            if (keyCode == KEY_NUM5) {
                piscaBloco(5, 600);
            }
            if (keyCode == KEY_NUM7) {
                piscaBloco(3, 600);
            }
            if (keyCode == KEY_NUM9) {
                piscaBloco(4, 600);
            }

            if (tecla == Canvas.FIRE) {
                novoJogo();
            }
    
            if ( sequencia.length() > 0 ) {
                if ( ( ( keyCode == KEY_NUM1 ) && (sequencia.charAt(curSample) == 49 ) )
                  || ( ( keyCode == KEY_NUM3 ) && (sequencia.charAt(curSample) == 50 ) )
                  || ( ( keyCode == KEY_NUM5 ) && (sequencia.charAt(curSample) == 53 ) )
                  || ( ( keyCode == KEY_NUM7 ) && (sequencia.charAt(curSample) == 51 ) )
                  || ( ( keyCode == KEY_NUM9 ) && (sequencia.charAt(curSample) == 52 ) ) ) {
                  curSample++;
                  if ( curSample == sequencia.length() ) {
                      jogar();
                  }
                } else {
                    for( int i=0; i<2; i++ ) {
                        for( int x=1; x<6; x++ ) piscaBloco(x,250);
                    }
                    pausa(2000);
                    novoJogo();
                }
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdSair) {
            game.destroyApp(false);
        }
        if (c == cmdLoop) {
            novoJogo();
        }
    }

    private void novoJogo() {
        sequencia=new StringBuffer();
        jogar();
    }

    private void jogar() {
        emJogo=1;

        sequencia.append(random.nextInt(5) + 1);
        for (int i = 0; i < sequencia.length(); i++) {
          piscaBloco(sequencia.charAt(i)-48, 1000);
        }
        emJogo=0;
        curSample=0;
    }
}