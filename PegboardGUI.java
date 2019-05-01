import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.List;
class PegboardGUI extends JPanel
{
    private static int GUIWidth = 500;
    private static int GUIHeight = 500;
    private Pegboard pb = new Pegboard(new int[] {1});
    private Peg[] pegs = initializePegs();
    private Peg selectedPeg;
    private boolean isLoser = false;
    private boolean isWinner = false;
    public PegboardGUI(Color backColor) {
        setBackground(backColor);
        addMouseListener(new PanelListener());
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 5F));
        g.drawString("Hint", 0, 50);
        for(Peg p : pegs) {
            p.draw(g);
        }
        if(pb.isLoser()) {
            g.drawString("You lose!", 0, getHeight()/2);
        } else if(pb.isWinner()) {
            g.drawString("You win!", 0, getHeight()/2);
        }
    }
    public void printWinPath(Pegboard pb) {
        try {
            List<int[]> path = PegboardSolver.getWinPaths(pb).get(0);
            System.out.println(path.get(0)[0] + " : " + path.get(0)[1]);
        } catch(Exception e) {
            System.out.println("No paths found.");
        }
        System.out.print("\n");
    }
    public static void main(String[] args) {
        JFrame theGUI = new JFrame();
        theGUI.setTitle("Peg GUI");
        theGUI.setSize(GUIWidth, GUIHeight);
        theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PegboardGUI panel = new PegboardGUI(Color.white);
        theGUI.getContentPane().add(panel);
        theGUI.setVisible(true);
    }
    public void clickPeg(Peg p) {
        switch(p.getMode()) {
            case FILLED: {
                selectedPeg = p;
                setPegs(PegMode.SELECTED, PegMode.FILLED);
                setPegs(PegMode.MOVE, PegMode.EMPTY);
                p.setMode(PegMode.SELECTED);
                for(int i : pb.getPossibleMoves(p.num)) {
                    pegs[i - 1].setMode(PegMode.MOVE);
                }
                break;
            }
            case EMPTY: {
                break;
            }
            case SELECTED: {
                p.setMode(PegMode.FILLED);
                setPegs(PegMode.MOVE, PegMode.EMPTY);
                break;
            }
            case MOVE: {
                pb.movePeg(selectedPeg.num, p.num);
                p.setMode(PegMode.FILLED);
                updatePegs();
                break;
            }
        }
    }
    public void updatePegs() {
        for(Pegboard.Peg p : pb.getHoles()) {
            pegs[p.getNum() - 1].setMode(p.isEmpty() ? PegMode.EMPTY : PegMode.FILLED);
        }
    }
    public void setPegs(PegMode fromMode, PegMode toMode) {
        for(Peg p : pegs) {
            if(p.getMode() == fromMode) p.setMode(toMode);
        }
    }
    private Peg[] initializePegs() {
        int pegX = GUIWidth/2;
        int pegY = 30;
        int xIter = GUIWidth/9;
        int yIter = GUIWidth/5;

        Peg[] pegs =  new Peg[] {
                new Peg(1,  pegX,           pegY),
                new Peg(2,  pegX - xIter,   pegY + yIter),
                new Peg(3,  pegX + xIter,   pegY + yIter),
                new Peg(4,  pegX - xIter*2, pegY + yIter*2),
                new Peg(5,  pegX,           pegY + yIter*2),
                new Peg(6,  pegX + xIter*2, pegY + yIter*2),
                new Peg(7,  pegX - xIter*3, pegY + yIter*3),
                new Peg(8,  pegX - xIter,   pegY + yIter*3),
                new Peg(9,  pegX + xIter,   pegY + yIter*3),
                new Peg(10, pegX + xIter*3, pegY + yIter*3),
                new Peg(11, pegX - xIter*4, pegY + yIter*4),
                new Peg(12, pegX - xIter*2, pegY + yIter*4),
                new Peg(13, pegX,           pegY + yIter*4),
                new Peg(14, pegX + xIter*2, pegY + yIter*4),
                new Peg(15, pegX + xIter*4, pegY + yIter*4)
        };

        for(Pegboard.Peg p : pb.getHoles()) {
            pegs[p.getNum() - 1].setMode(p.isEmpty() ? PegMode.EMPTY : PegMode.FILLED);
        }

        return pegs;
    }
    public void showHintWindow() {
        String hint;
        if(pb.getOpenHoles().size() <= 3)
            hint = "Keep going!";
        else
            try {
                int[] path = PegboardSolver.getWinPaths(pb).get(0).get(0);
                hint = "You can move peg " + path[0] + " to hole " + path[1];
            } catch(Exception e) {
                hint = "You cannot solve this board.";
            }
        JOptionPane.showMessageDialog(null, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);
    }
    private class PanelListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if(e.getX() < 100 && e.getY() < 50)
                showHintWindow();
            for(Peg p : pegs) {
                if(p.containsPoint(e.getX(), e.getY())) {
                    clickPeg(p);
                }
            }
        }
    }
    private enum PegMode {
        FILLED, EMPTY, SELECTED, MOVE
    }
    private class Peg extends Circle {
        private int x, y, num;
        private PegMode pegMode;
        private boolean filled;
        public Peg(int num, int x, int y) {
            super(x, y, 25, Color.blue);
            this.x = x;
            this.y = y;
            this.num = num;
        }
        public void draw(Graphics g) {
            switch (pegMode) {
                case FILLED:
                    setRadius(25);
                    setColor(Color.blue);
                    super.fill(g);
                    break;
                case EMPTY:
                    setRadius(10);
                    setColor(Color.gray);
                    super.draw(g);
                    break;
                case SELECTED:
                    setRadius(30);
                    setColor(Color.blue);
                    super.fill(g);
                    break;
                case MOVE:
                    setRadius(15);
                    setColor(Color.green);
                    super.fill(g);
            }
        }
        public void setMode(PegMode pm) {
            pegMode = pm;
            repaint();
        }
        public PegMode getMode() {
            return pegMode;
        }
    }
}