import java.util.LinkedList;
import java.util.List;

/**
 * Created by Simon M. Lucas
 * sml@essex.ac.uk
 * Date: 03-Dec-2010
 * Time: 21:51:31
 */
public class TreeNodeTest {
    public static void main(String[] args) {
        TreeNode tn = new TreeNode();
        System.out.println(tn.r.nextDouble());
        System.out.println(tn.nActions);
        System.out.println(tn.epsilon);
        tn.selectAction(); // 通过MCTS决定落第1子使得在当前局面下胜率最高.
        System.out.println(tn.children.length);
        tn.selectAction(); // 通过MCTS决定落第2子使得在当前局面下胜率最高.
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        tn.selectAction();
        System.out.println(tn.children.length);
        // 由于已经充分探索了下一步引起的各种连锁局面即每下一步之前已经YY了N步.(人可能下一步前想5步,而这里是下一步前执行了几次selectAction就相当于探索了几步).所以每下一步都是尽量在当前局面下胜率最高的选择.
        // 随着游戏进行树会越来越短.

        TreeView tv = new TreeView(tn);
        tv.showTree(null);
    }
}