import java.util.List;
import java.util.ArrayList;
public class Pegboard
{
    private Peg[] holes;
    public Pegboard() {
        holes = initializeHoles();
    }
    public Pegboard(int openHole) {
        this();
        getHole(openHole).setFilled(false);
    }
    public Pegboard(int[] openHoles) {
        this();
        for(int i : openHoles)
            getHole(i).setFilled(false);
    }
    public Pegboard(List<Integer> openHoles) {
        this();
        for(int i : openHoles)
            getHole(i).setFilled(false);
    }
    public Peg getHole(int num) {
        return holes[num - 1];
    }
    public boolean movePeg(int pegNum, int holeNum) {
        Peg peg = getHole(pegNum);
        Peg hole = getHole(holeNum);
        if(!isMoveable(pegNum, holeNum)) return false;
        for(int[] move : peg.getMoves()) {
            if(move[0] == holeNum) {
                hole.setFilled(true);
                peg.setFilled(false);
                getHole(move[1]).setFilled(false);
                return true;
            }
        }
        return false;
    }
    public boolean isWinner() {
        int pegs = 0;
        for(Peg p : holes) if(p.isFilled()) pegs++;
        return pegs == 1;
    }
    public boolean isLoser() {
        if(isWinner())
            return false;
        for(Peg p : holes) {
            if(isMoveable(p.num)) return false;
        }
        return true;
    }
    public List<Integer> getOpenHoles() {
        List<Integer> openHoles = new ArrayList<>();
        for(Peg p : holes)
            if(p.isEmpty()) openHoles.add(p.num);
        return openHoles;
    }
    public List<Integer> getMoveablePegs() {
        List<Integer> moveable = new ArrayList<>();
        for(Peg p : holes) {
            if(isMoveable(p.num)) moveable.add(p.num);
        }
        return moveable;
    }
    public List<Integer> getPossibleMoves(int peg) {
        List<Integer> possibleMoves = new ArrayList<>();
        for (Peg p : holes) {
            if(isMoveable(peg, p.num))
                possibleMoves.add(p.num);
        }
        return possibleMoves;
    }
    public boolean isMoveable(int peg) {
        Peg selectedPeg = getHole(peg);
        if(selectedPeg.isEmpty()) return false;
        for(int[] move : selectedPeg.moves) {
            if(getHole(move[0]).isEmpty() && getHole(move[1]).isFilled())
                return true;
        }
        return false;
    }
    public boolean isMoveable(int peg, int hole) {
        Peg selectedPeg = getHole(peg);
        if(selectedPeg.isEmpty()) return false;
        for(int[] move : selectedPeg.moves) {
            if(move[0] == hole && getHole(move[0]).isEmpty() && getHole(move[1]).isFilled())
                return true;
        }
        return false;
    }
    public static boolean arePegboardsEqual(Pegboard p1, Pegboard p2) {
        List<Integer> holesp1 = p1.getOpenHoles();
        List<Integer> holesp2 = p2.getOpenHoles();
        for(int i = 0; i < holesp1.size(); i++) {
            if(holesp1.get(i) != holesp2.get(i)) {
                return false;
            }
        }
        return true;
    }
    private Peg[] initializeHoles() {
        /*initialize peg with int pairs separated by colons. first int
        is hole it can go to, and second is the hole it jumps over.
        object automatically converts string to a list of integer pairs*/
        return new Peg[] {
                new Peg(1,  true,   "4:2,   6:3"),
                new Peg(2,  true,   "7:4,   9:5"),
                new Peg(3,  true,   "8:5,   10:6"),
                new Peg(4,  true,   "1:2,   6:5,    11:7,   13:8"),
                new Peg(5,  true,   "12:8,  14:9"),
                new Peg(6,  true,   "1:3,   4:5,    13:9,   15:10"),
                new Peg(7,  true,   "2:4,   9:8"),
                new Peg(8,  true,   "3:5,   10:9"),
                new Peg(9,  true,   "2:5,   7:8"),
                new Peg(10, true,   "8:9,   3:6"),
                new Peg(11, true,   "4:7,   13:12"),
                new Peg(12, true,   "14:13, 5:8"),
                new Peg(13, true,   "4:8,   6:9,    11:12,  15:14"),
                new Peg(14, true,   "5:9,   12:13"),
                new Peg(15, true,   "6:10,  13:14")
        };
    }
    public Peg[] getHoles() {
        return holes;
    }
    public String toString() {
        String board = "";
        for(Peg p : holes) {
            board += p.isFilled() + "   ";
        }
        return board;
    }
    class Peg {
        private int num;
        private boolean filled;
        private List<int[]> moves;
        public Peg(int num, boolean filled, String moves) {
            this.num = num;
            this.filled = filled;
            this.moves = moveConvert(moves);
        }
        public void setFilled(boolean f) {
            filled = f;
        }
        public int getNum() {
            return num;
        }
        public List<int[]> getMoves() {
            return moves;
        }
        public boolean isFilled() {
            return filled;
        }
        public boolean isEmpty() {
            return !filled;
        }
        private List<int[]> moveConvert(String m) {
            List<int[]> moves = new ArrayList<>();
            String[] pairs = m.replaceAll("\\s+","").split(",");
            String[] pair;
            for(String num : pairs) {
                pair = num.split(":");
                moves.add(new int[] {Integer.parseInt(pair[0]), Integer.parseInt(pair[1])});
            }
            return moves;
        }
    }
}