import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TreeNode {
    static Random r = new Random();
    static int nActions = 3; //落子位置
    static double epsilon = 1e-6; //尝试次数

    TreeNode[] children;
    double nVisits, totValue;

    public void selectAction() {
        List<TreeNode> visited = new LinkedList();
        TreeNode cur = this;
        visited.add(this);
        while (!cur.isLeaf()) { // 如果游戏没结束就继续落子
            cur = cur.select(); // 对应MCTS第1步Selection: 如果接下来每种落子方案都尝试过就选通过UCT选择值得再次尝试的方案
            visited.add(cur);
        }
        cur.expand(); // 生成新落一子后所有可能的局面
        TreeNode newNode = cur.select(); // 对应MCTS第2步Expansion: 新落一子使棋局进入上面某一种局面
        visited.add(newNode); // 保存新局面
        double value = rollOut(newNode); // 对应MCTS第3步Simulation: 从新局面开始乱下评估新局面,可知如果局面有优势即使接下来SB去下也容易获胜
        for (TreeNode node : visited) { // 对应MCTS第4步Backpropagation: 更新到目前为止每一步的导致的局面优势
            // would need extra logic for n-player game
            // System.out.println(node);
            node.updateStats(value);
        }
    }

    public void expand() {
        children = new TreeNode[nActions];
        for (int i=0; i<nActions; i++) {
            children[i] = new TreeNode();
        }
    }

    private TreeNode select() {
        TreeNode selected = null;
        double bestValue = Double.MIN_VALUE;
        for (TreeNode c : children) {
            double uctValue =
                    c.totValue / (c.nVisits + epsilon) +
                            Math.sqrt(Math.log(nVisits+1) / (c.nVisits + epsilon)) +
                            r.nextDouble() * epsilon;
            // small random number to break ties randomly in unexpanded nodes
            // System.out.println("UCT value = " + uctValue);
            if (uctValue > bestValue) {
                selected = c;
                bestValue = uctValue;
            }
        }
        // System.out.println("Returning: " + selected);
        return selected;
    }

    public boolean isLeaf() {
        return children == null;
    }

    public double rollOut(TreeNode tn) {
        // ultimately a roll out will end in some value
        // assume for now that it ends in a win or a loss
        // and just return this at random
        return r.nextInt(2);
    }

    public void updateStats(double value) {
        nVisits++;
        totValue += value;
    }

    public int arity() {
        return children == null ? 0 : children.length;
    }
}