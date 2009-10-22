package nanogenius;

import javax.microedition.lcdui.*;

public class NanoGenius extends Canvas implements CommandListener {

    private Main chart;
    private Command cmdSair;
    private Command cmdLoop;
    private int largura, altura, percBorda, curBlock;

    public NanoGenius(Main midlet) {
        largura = getWidth();
        altura = getHeight();
        percBorda = (getWidth() * 10) / 100;
        curBlock = 0;

        //setFullScreenMode(true);
        this.chart = midlet;
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

    protected void keyPressed(int keyCode) {
        if (keyCode == KEY_NUM0) {
            curBlock = 1;
            repaint();
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdSair) {
            chart.destroyApp(false);
        }
        if (c == cmdLoop) {
            try {
                curBlock = 1;
                repaint();
                serviceRepaints();
                Escala.tocaNota(Escala.aNat);
                Thread.sleep(500);

                curBlock = 4;
                repaint();
                serviceRepaints();
                Thread.sleep(500);

                curBlock = 2;
                repaint();
                serviceRepaints();
                Thread.sleep(500);

                curBlock = 3;
                repaint();
                serviceRepaints();
                Thread.sleep(500);

                curBlock = 1;
                repaint();
                serviceRepaints();
                Thread.sleep(500);

                curBlock = 5;
                repaint();
                serviceRepaints();
                Thread.sleep(500);

                curBlock = 0;
                repaint();
                serviceRepaints();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
