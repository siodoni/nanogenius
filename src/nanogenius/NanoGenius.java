package nanogenius;

import javax.microedition.lcdui.*;
import java.util.Random;

public class NanoGenius extends Canvas implements CommandListener {

    private Main game;
    private Command cmdSair, cmdLoop;
    private int altura, largura, larguraAlt, percBorda, curBlock, curSample = 0, emJogo = 0, percDesloc;
    private StringBuffer sequencia = new StringBuffer();
    private static Random random = new Random();

    public NanoGenius(Main midlet) {
        altura = getHeight();
        largura = getWidth();
        percDesloc = (largura * 20) / 100;
        larguraAlt = largura - percDesloc;
        percBorda = (largura * 10) / 100;
        curBlock = 0;
        Escala.oitava = 36;
        //Escala.volume = 0;

        //setFullScreenMode(true);
        this.game = midlet;
        cmdSair = new Command("Sair", Command.EXIT, 0);
        addCommand(cmdSair);
        cmdLoop = new Command("Novo", Command.ITEM, 0);
        addCommand(cmdLoop);
        setCommandListener(this);
    }

    public void paint(Graphics g) {
        g.setColor(Cor.BRANCO);
        g.fillRect(0, 0, largura, altura);

        g.setColor(Cor.PRETO);
        g.fillArc(percDesloc / 2, percDesloc, larguraAlt, larguraAlt, 0, 360);

        g.setColor(Cor.PRETO);
        g.fillRect(0, 0, largura, percDesloc / 2);

        g.setColor(Cor.BRANCO);
        g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        if (sequencia.length() == 0) {
            g.drawString("", largura / 2, 2, Graphics.TOP | Graphics.HCENTER);
        } else {
            g.drawString("Jogada " + sequencia.length(), largura / 2, 2, Graphics.TOP | Graphics.HCENTER);
        }

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
        g.fillArc(percDesloc / 2, percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 90, 90);

        g.setColor(curBlock == 2 ? Cor.VERMELHO : Cor.VERMELHO_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 360, 90);

        g.setColor(curBlock == 3 ? Cor.AMARELO : Cor.AMARELO_ESCURO);
        g.fillArc(percDesloc / 2, percBorda + percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 180, 90);

        g.setColor(curBlock == 4 ? Cor.AZUL : Cor.AZUL_ESCURO);
        g.fillArc(percBorda + percDesloc / 2, percBorda + percDesloc, larguraAlt - percBorda, larguraAlt - percBorda, 360, -90);

        g.setColor(Cor.PRETO);
        g.fillArc(((larguraAlt / 4) - (percBorda / 2)) + percDesloc / 2, ((larguraAlt / 4) - (percBorda / 2)) + percDesloc, (larguraAlt / 2) + percBorda, (larguraAlt / 2) + percBorda, 0, 360);

        g.setColor(curBlock == 5 ? Cor.ROXO : Cor.ROXO_ESCURO);
        g.fillArc((larguraAlt / 3) + percDesloc / 2, (larguraAlt / 3) + percDesloc, larguraAlt / 3, larguraAlt / 3, 0, 360);
    }

    protected void pausa(int tempo) {
        try {
            Thread.currentThread().sleep(tempo);
        } catch (InterruptedException ex) {
        }
    }

    protected void piscaBloco(int bloco, int tempo) {
        curBlock = bloco;
        repaint();
        serviceRepaints();
        pausa(tempo);
        curBlock = 0;
        repaint();
        serviceRepaints();
    }

    protected void keyPressed(int keyCode) {

        int tecla = getGameAction(keyCode);

        if (emJogo == 0) {
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
            } else {

                if (sequencia.length() > 0) {
                    if (((keyCode == KEY_NUM1) && (sequencia.charAt(curSample) == KEY_NUM1)) //
                            || ((keyCode == KEY_NUM3) && (sequencia.charAt(curSample) == KEY_NUM2)) //
                            || ((keyCode == KEY_NUM5) && (sequencia.charAt(curSample) == KEY_NUM5)) //
                            || ((keyCode == KEY_NUM7) && (sequencia.charAt(curSample) == KEY_NUM3)) //
                            || ((keyCode == KEY_NUM9) && (sequencia.charAt(curSample) == KEY_NUM4))) {
                        curSample++;
                        if (curSample == sequencia.length()) {
                            emJogo=1;
                            pausa(1000);
                            jogar();
                        }
                    } else {
                        emJogo=1;
                        for (int i = 0; i < 3; i++) {
                            for (int x = 1; x < 6; x++) {
                                piscaBloco(x, 100);
                            }
                        }
                        pausa(2000);
                        novoJogo();
                    }
                }
            }
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdSair) {
            emJogo = 0;
            game.destroyApp(false);
        }
        if (c == cmdLoop) {
            novoJogo();
        }
    }

    private void novoJogo() {
        sequencia = new StringBuffer();
        jogar();
    }

    private void jogar() {
        emJogo = 1;
        sequencia.append(random.nextInt(5) + 1);
        for (int i = 0; i < sequencia.length(); i++) {
            piscaBloco(sequencia.charAt(i) - 48, 600);
        }
        emJogo = 0;
        curSample = 0;
    }
}
