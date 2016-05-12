
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel {

    private ArrayList<Block> blocks = null;
    private ImageIcon imageIcon1 = new ImageIcon(this.getClass().getResource("/1.gif"));
    private ImageIcon imageIcon2 = new ImageIcon(this.getClass().getResource("/2.gif"));
    private ImageIcon imageIcon3 = new ImageIcon(this.getClass().getResource("/3.gif"));
    private ImageIcon imageIcon4 = new ImageIcon(this.getClass().getResource("/4.gif"));
    private Image image[] = new Image[4];

    private boolean foundArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i] == arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void drawPiece(Piece piece) {

        blocks = piece.blocks;

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= 4; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);

        for (int i = 0; i < blocks.size() && i < 4; i++) {
            if (blocks.get(i) != null) {
                if (list.get(i) == 1) {
                    image[i] = imageIcon1.getImage();
                } else if (list.get(i) == 2) {
                    image[i] = imageIcon2.getImage();
                } else if (list.get(i) == 3) {
                    image[i] = imageIcon3.getImage();
                } else if (list.get(i) == 4) {
                    image[i] = imageIcon4.getImage();
                }
                this.getGraphics().drawImage(image[i], blocks.get(i).x * 45, blocks.get(i).y * 45, 45, 45, null);
            }
        }
    }
}