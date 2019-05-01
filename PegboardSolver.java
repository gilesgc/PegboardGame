import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
public class PegboardSolver
{
    public static boolean isWinnable(Pegboard pb) {

        if(pb.getOpenHoles().size() <= 3) return true;

        List<Pegboard> pegboards = new ArrayList<>();
        Pegboard tempBoard;

        pegboards.add(pb);
        for(int a = 0; a < pegboards.size(); a++) {
            tempBoard = pegboards.get(a);
            for (int peg : tempBoard.getMoveablePegs()) {
                for (int i : tempBoard.getPossibleMoves(peg)) {
                    pegboards.add(duplicateAndMove(tempBoard, peg, i));
                }
            }
        }
        for(Pegboard p : pegboards) {
            if(p.isWinner()) {
                return true;
            }
        }
        return false;
    }

    public static List<List<int[]>> getWinPaths(Pegboard pb) {
        List<PegPath> pegboards = new ArrayList<>();
        List<List<int[]>> paths = new ArrayList<>();
        Pegboard tempBoard;

        pegboards.add(new PegPath(pb));

        for(int a = 0; a < pegboards.size(); a++) {
            tempBoard = pegboards.get(a).getPegboard();
            for(int peg : tempBoard.getMoveablePegs()) {
                for(int i : tempBoard.getPossibleMoves(peg)) {
                    pegboards.add(new PegPath(pegboards.get(a), peg, i));
                }
            }
        }

        for(PegPath pp : pegboards) {
            if(pp.getPegboard().isWinner()) {
                paths.add(pp.getPath());
            }
        }

        return paths;
    }

    private static Pegboard duplicateAndMove(Pegboard pb, int peg, int hole) {
        Pegboard dupe = new Pegboard(pb.getOpenHoles());
        dupe.movePeg(peg, hole);
        return dupe;
    }

    private static class PegPath {
        List<int[]> path;
        Pegboard pb;
        public PegPath(Pegboard pb) {
            path = new ArrayList<>();
            this.pb = pb;
        }
        public PegPath(PegPath prevPegPath, int peg, int hole) {
            path = new ArrayList<>();
            path.addAll(prevPegPath.getPath());
            this.pb = duplicateAndMove(prevPegPath.getPegboard(), peg, hole);
            path.add(new int[] {peg, hole});
        }
        public List<int[]> getPath() {
            return path;
        }
        public Pegboard getPegboard() {
            return pb;
        }
    }
}