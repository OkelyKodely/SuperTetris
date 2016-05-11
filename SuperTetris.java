import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class SuperTetris implements MouseListener, KeyListener {

    
    private JTextField host = new JTextField();

    private JButton con = new JButton("Connect");

    private ServerSocket serverSocket = null;

    private Socket clientSocket = null;

    private Socket socket = null;

    private String hostName = "x.x.x.x";

    private int port = 5000;

    public boolean connect = false;

    public boolean accept = false;

    private DataOutputStream dos = null;

    private String thepiece = "";

    private String thegetpiece = "";

    private int lines = 0;

    private int oppLines = 0;

    private JLabel linesLbl = new JLabel("Lines: " + lines);

    private JLabel oppLinesLbl = new JLabel("Lines: " + oppLines);

    private int board[][] = new int[16][10];

    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    private ArrayList<Piece> oppPieces = new ArrayList<Piece>();

    private Piece piece;

    private Piece oppPiece;

    private int delay = 2000;

    private JFrame gameFrame = new JFrame("SuperTetris (Open/forward port 5000 in/on the firewall/router)");

    private JPanel mainPanel = new JPanel();

    private GamePanel gamePanel = new GamePanel();

    private GamePanel oppGamePanel = new GamePanel();

    private JPanel panel = new JPanel();

    private JPanel oppPanel = new JPanel();

    public SuperTetris() {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int width = dimension.width;

        gameFrame.setLayout(null);
        gameFrame.setLocation(width/2-600, 30);
        gameFrame.setSize(1200, 745);

        linesLbl.setLocation(10, 0);
        linesLbl.setSize(150, 20);

        panel.add(linesLbl);
        panel.setLayout(null);
        panel.setLocation(0, 0);
        panel.setBackground(new Color(145, 22, 22));
        panel.setSize(150, 745);

        gameFrame.add(panel);

        gamePanel.setLocation(0, 0);
        gamePanel.setSize(450, 745);

        mainPanel.add(gamePanel);

        oppPanel.setLocation(450, 0);
        oppPanel.setBackground(new Color(175, 52, 52));
        oppPanel.setSize(150, 745);
        oppPanel.setLayout(null);

        JLabel oppHostName = new JLabel("Opp hostName");
        oppHostName.setSize(120, 20);
        oppHostName.setLocation(10, 0);

        host.setSize(120, 20);
        host.setLocation(10, 20);

        con.setSize(120, 20);
        con.setLocation(10, 40);
        con.addMouseListener(this);

        oppPanel.add(oppHostName);
        oppPanel.add(host);
        oppPanel.add(con);

        oppLinesLbl.setLocation(10, 60);
        oppLinesLbl.setSize(150, 20);

        oppPanel.add(oppLinesLbl);

        mainPanel.add(oppPanel);

        oppGamePanel.setLocation(600, 0);
        oppGamePanel.setSize(450, 745);

        mainPanel.add(oppGamePanel);
        mainPanel.setLayout(null);
        mainPanel.setLocation(150, 0);
        mainPanel.setSize(1050, 745);

        gameFrame.add(mainPanel);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.addKeyListener(this);
        gameFrame.setVisible(true);
    }

    private boolean isNotDown(Piece piece) {

        if(piece.blocks.get(0).y == 15 ||
           piece.blocks.get(1).y == 15 ||
           piece.blocks.get(2).y == 15 ||
           piece.blocks.get(3).y == 15) {

            return false;
        }

        return true;
    }
    
    public boolean juxtaposedLeftSideways(ArrayList<Piece> pieces) {
        for(Piece piece : pieces) {
            if(piece.setType.equals("current")) {
                for(int i=0; i<piece.blocks.size(); i++) {
                    for(Piece p : pieces) {
                        if(p.setType.equals("")) {
                            for(int j=0; j<p.blocks.size(); j++) {
                                if((piece.blocks.get(i).x + 1 == p.blocks.get(j).x && piece.blocks.get(i).y == p.blocks.get(j).y)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean juxtaposedRightSideways(ArrayList<Piece> pieces) {
        for(Piece piece : pieces) {
            if(piece.setType.equals("current")) {
                for(int i=0; i<piece.blocks.size(); i++) {
                    for(Piece p : pieces) {
                        if(p.setType.equals("")) {
                            for(int j=0; j<p.blocks.size(); j++) {
                                if((piece.blocks.get(i).x == p.blocks.get(j).x + 1 && piece.blocks.get(i).y == p.blocks.get(j).y)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean juxtaposedTopways(ArrayList<Piece> pieces) {
        for(Piece piece : pieces) {
            if(piece.setType.equals("current")) {
                for(int i=0; i<piece.blocks.size(); i++) {
                    for(Piece p : pieces) {
                        if(p.setType.equals("")) {
                            for(int j=0; j<p.blocks.size(); j++) {
                                if((piece.blocks.get(i).y + 1 == p.blocks.get(j).y && piece.blocks.get(i).x == p.blocks.get(j).x) || (piece.blocks.get(i).y == p.blocks.get(j).y + 1 && piece.blocks.get(i).x == p.blocks.get(j).x)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void getOpponentPieces() {
        try {

            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        getPieces();
                    }
                }
            });
            t1.start();
            
        } catch(Exception e) {
        }
    }

    public void startGame() {
        if(connect && accept) {
            getOpponentPieces();
            playTheGame();
        }
    }

    public void playTheGame() {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                play();
            }
        });

        t1.start();
    }

    public void connect() {
        try {

            hostName = host.getText().trim();
            socket = new Socket(hostName, port);
            dos = new DataOutputStream(socket.getOutputStream());
            connect = true;

        } catch(Exception e) {
            e.printStackTrace();
        }

        startGame();
    }

    public void waitForAccept() {
        try {

            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            accept = true;

        } catch(Exception e) {
            e.printStackTrace();
        }

        startGame();
    }

    public void putPiece() {
        try {

            thepiece = "";
            for(int i=0; i<pieces.size(); i++) {
                String val = "|";
                if(i == pieces.size() - 1) {
                    thepiece += String.valueOf(pieces.get(i).blocks.get(0).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(0).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(1).x) + "," + String.valueOf(pieces.get(i).blocks.get(1).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(2).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(2).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(3).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(3).y).trim() + ";" + pieces.get(i).direction.toString().trim() + ";" + pieces.get(i).getType().trim() + ";" + String.valueOf(lines).trim();
                } else {
                    thepiece += String.valueOf(pieces.get(i).blocks.get(0).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(0).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(1).x) + "," + String.valueOf(pieces.get(i).blocks.get(1).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(2).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(2).y).trim() + ";" + String.valueOf(pieces.get(i).blocks.get(3).x).trim() + "," + String.valueOf(pieces.get(i).blocks.get(3).y).trim() + ";" + pieces.get(i).direction.toString().trim() + ";" + pieces.get(i).getType().trim() + ";" + String.valueOf(lines).trim() + val;
                }
            }
            dos.write(thepiece.getBytes());
            dos.flush();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getOppPiece() {
        try {

            byte[] messageByte = new byte[10000]; // /
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            int bytesRead = in.read(messageByte);
            thegetpiece = new String(messageByte, 0, bytesRead);
            
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public void play() {

        linesLbl.setText("Lines: " + lines);

        for(int i=0; i<this.board.length; i++) {

            for(int j=0; j<this.board[i].length; j++) {

                this.board[i][j] = 0;

            }

        }

        String type = null;

        Random rnd = new Random();

        int val = rnd.nextInt(7);

        switch(val) {

            case 0:

                type = "line";

                break;

            case 1:

                type = "square";

                break;
            case 2:

                type = "LArm";

                break;
            case 3:

                type = "RArm";

                break;
            case 4:

                type = "Hat";

                break;
            case 5:

                type = "LShoulder";

                break;
            case 6:

                type = "RShoulder";

                break;

        }


        piece = new Piece( "" + type );

        piece.setLocation(4, 0);

        piece.setType("current");

        gamePanel.drawPiece(piece);

        pieces.add(piece);

        while(true) {

            try {

                Thread.sleep(delay);

            } catch(Exception e) {}

            putPiece();

            if(isNotDown(piece) && !juxtaposedTopways(pieces)) {

                piece.moveDown();

            }

            if(!isNotDown(piece)

                    ||

                    juxtaposedTopways(pieces) || reachedTopThePiece()) {

                if(reachedTopThePiece()) {
                    delay = 2000;
                    lines = 0;
                    for(int i=0; i<this.board.length; i++) {
                        for(int j=0; j<this.board[i].length; j++) {
                            this.board[i][j] = 0;
                        }
                    }
                    pieces.removeAll(pieces);
                    pieces.trimToSize();
                }


                String _type = null;


                Random _rnd = new Random();

                int _val = _rnd.nextInt(7);


                switch(_val) {

                    case 0:

                        _type = "line";

                        break;

                    case 1:

                        _type = "square";

                        break;
                    case 2:

                        _type = "LArm";

                        break;
                    case 3:

                        _type = "RArm";

                        break;
                    case 4:

                        _type = "Hat";

                        break;
                    case 5:

                        _type = "LShoulder";

                        break;
                    case 6:

                        _type = "RShoulder";

                        break;


                }

                this.board[piece.blocks.get(0).y][piece.blocks.get(0).x] = 1;

                this.board[piece.blocks.get(1).y][piece.blocks.get(1).x] = 1;

                this.board[piece.blocks.get(2).y][piece.blocks.get(2).x] = 1;

                this.board[piece.blocks.get(3).y][piece.blocks.get(3).x] = 1;

                piece.setType("");

                piece = new Piece( "" + _type );

                piece.setLocation(4, 0);

                piece.setType("current");

                pieces.add(piece);

            }

            clearBlocksWhenBlocksAreALine();

            linesLbl.setText("Lines: " + lines);

        }
    }

    private void getPieces() {

        getOppPiece();

        oppPieces.clear();

        String thethepiece = "";

        StringTokenizer stringTokenizer = new StringTokenizer(thegetpiece, "|");

        while(stringTokenizer.hasMoreElements()) {

             try {
            thethepiece = stringTokenizer.nextToken().trim();

            StringTokenizer st = new StringTokenizer(thethepiece, ";");
            String block1x = st.nextToken().trim() + "";
            StringTokenizer st2 = new StringTokenizer(block1x, ",");

            block1x = st2.nextToken().trim() + "";
            String block1y = "" + st2.nextToken().trim() + "";
            String block2x = "" + st.nextToken().trim() + "";
            st2 = new StringTokenizer(block2x, ",");

            block2x = "" + st2.nextToken().trim() + "";
            String block2y = "" + st2.nextToken().trim() + "";
            String block3x = "" + st.nextToken().trim() + "";
            st2 = new StringTokenizer(block3x, ",");

            block3x = "" + st2.nextToken().trim() + "";
            String block3y = "" + st2.nextToken().trim() + "";
            String block4x = "" + st.nextToken().trim() + "";
            st2 = new StringTokenizer(block4x, ",");

            block4x = "" + st2.nextToken().trim() + "";
            String block4y = "" + st2.nextToken().trim() + "";

            String direction = "" + st.nextToken().trim() + "";
            String thetype = "" + st.nextToken().trim() + "";
            String opp_lines = "" + st.nextToken().trim() + "";
//
            opp_lines = thegetpiece.substring(thegetpiece.length()-2, thegetpiece.length());
            if(opp_lines.charAt(0) == ';') {
                if(opp_lines.length() == 2)
                    opp_lines = String.valueOf(opp_lines.charAt(1));
            }
            if(opp_lines.charAt(0) == '0') {
                if(opp_lines.length() == 2)
                    opp_lines = String.valueOf(opp_lines.charAt(1));
            }
            oppLines = Integer.valueOf(opp_lines);

            oppLinesLbl.setText("Lines: " + oppLines);

            oppPiece = new Piece( "" + thetype );

            oppPiece.setLocation(Integer.valueOf(block1x), Integer.valueOf(block1y));

            oppPiece.blocks.clear();

            Block block = new Block();
            block.x = Integer.valueOf(block1x);
            block.y = Integer.valueOf(block1y);
            oppPiece.blocks.add(block);

            block = new Block();
            block.x = Integer.valueOf(block2x);
            block.y = Integer.valueOf(block2y);
            oppPiece.blocks.add(block);

            block = new Block();
            block.x = Integer.valueOf(block3x);
            block.y = Integer.valueOf(block3y);
            oppPiece.blocks.add(block);

            block = new Block();
            block.x = Integer.valueOf(block4x);
            block.y = Integer.valueOf(block4y);
            oppPiece.blocks.add(block);

            oppPiece.setType("current");

            oppPieces.add(oppPiece);

            direction = direction.trim();

            if(direction.equals("up")) {
                oppPiece.direction = Piece.Direction.UP;
            } else if(direction.equals("down")) {
                oppPiece.direction = Piece.Direction.DOWN;
            } else if(direction.equals("left")) {
                oppPiece.direction = Piece.Direction.LEFT;
            } else if(direction.equals("right")) {
                oppPiece.direction = Piece.Direction.RIGHT;
            }
         } catch(Exception e) {
         }

        }

    }

    private boolean reachedTopThePiece() {
        for(int i=0; i<piece.blocks.size(); i++) {
            for(int j=0; j<pieces.size(); j++) {
                for(int k=0; k<pieces.get(j).blocks.size(); k++) {
                    if(piece.blocks.get(0).y < 5 && pieces.get(j).setType.equals("") && pieces.get(j).blocks.get(k).y == piece.blocks.get(i).y) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void clearBlocksWhenBlocksAreALine() {
        try {

            boolean needToClearLine = true;
            ArrayList<Integer> linesToClear = new ArrayList<Integer>();

            for(int y=0; y<16; y++) {
                for(int x=0; x<10; x++) {

                    if(this.board[y][x] != 1) {
                        needToClearLine = false;
                        break;
                    } else
                        needToClearLine = true;

                }
                if(needToClearLine == true) {

                    for(int i=0; i<linesToClear.size(); i++) {
                        if(linesToClear.get(i) == y) {
                            break;
                        }
                        else if(i == linesToClear.size() - 1) {
                            linesToClear.add(y);
                            lines++;
                            if(lines % 2 == 0)delay=delay-10;
                            if(delay < 600)delay=600;
                        }
                    }

                    if(linesToClear.isEmpty()) {

                        linesToClear.add(y);
                        lines++;
                        if(lines % 2 == 0)delay=delay-10;
                        if(delay < 600)delay = 600;
                    }

                }
            }

            for(int h=0; h<linesToClear.size(); h++) {
                for(int i=0; i<pieces.size(); i++) {

                    for(int j=0; j<pieces.get(i).blocks.size(); j++) {
                        if(pieces.get(i).blocks.get(j).y == linesToClear.get(h)) {

                            pieces.get(i).blocks.get(j).y = 10000;

                        }
                    }
                }
                for(int i=0; i<pieces.size(); i++) {

                    for(int j=0; j<pieces.get(i).blocks.size(); j++) {
                        if(pieces.get(i).blocks.get(j).y < linesToClear.get(h)) {

                            pieces.get(i).blocks.get(j).y++;

                        }
                    }

                }
            }

            for(int i=0; i<16; i++) {
                for(int j=0; j<10; j++) {
                    this.board[i][j] = 0;
                }
            }
            for(int i=0; i<pieces.size(); i++) {
                for(int j=0; j<pieces.get(i).blocks.size(); j++) {
                    if(pieces.get(i).blocks.get(j).y != 10000) {
                        this.board[pieces.get(i).blocks.get(j).y][pieces.get(i).blocks.get(j).x] = 1;
                    }
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException aioobe) {
        }
    }

    private void animate() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    ImageIcon imageIcon1 = new ImageIcon(this.getClass().getResource("/tetris.jpg"));
                    Image image1 = imageIcon1.getImage();
                    ImageIcon imageIcon2 = new ImageIcon(image1);
                    Image image2 = imageIcon2.getImage();
                    Graphics g1 = gamePanel.getGraphics();
                    Graphics g2 = oppGamePanel.getGraphics();
                    int x1 = gamePanel.getWidth();
                    int x2 = 0;
                    while(true) {
                        redrawBlocks();
                        redrawOppBlocks();
                        Thread.sleep(30);
                        g1.drawImage(image1, x1, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
                        g1.drawImage(image2, x2, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);
                        g2.drawImage(image1, x1, 0, oppGamePanel.getWidth(), oppGamePanel.getHeight(), null);
                        g2.drawImage(image2, x2, 0, oppGamePanel.getWidth(), oppGamePanel.getHeight(), null);
                        x1--;
                        x2--;
                        if(x1 == 0 && x2 == -gamePanel.getWidth()) {
                            x1 = gamePanel.getWidth();
                            x2 = 0;
                        }
                    }
                } catch(Exception e) {}
            }
        });
        t1.start();
    }

    public void redrawBlocks() {

        for(int i=0; i<pieces.size(); i++) {

            gamePanel.drawPiece(pieces.get(i));
        }
    }

    public void redrawOppBlocks() {

        for(int i=0; i<oppPieces.size(); i++) {

            oppGamePanel.drawPiece(oppPieces.get(i));
        }
    }

    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {

            case KeyEvent.VK_LEFT :

                if(!juxtaposedRightSideways(pieces) && piece.blocks.get(0).x > 0 && piece.blocks.get(1).x > 0 && piece.blocks.get(2).x > 0 && piece.blocks.get(3).x > 0) {

                    piece.moveLeft();
                    this.redrawBlocks();

                    putPiece();
                }

                break;

            case KeyEvent.VK_RIGHT :

                if(!juxtaposedLeftSideways(pieces) && piece.blocks.get(0).x < 9 && piece.blocks.get(1).x < 9 && piece.blocks.get(2).x < 9 && piece.blocks.get(3).x < 9) {

                    piece.moveRight();
                    this.redrawBlocks();

                    putPiece();
                }

                break;

            case KeyEvent.VK_DOWN :

                if(isNotDown(piece)) {

                    if(!juxtaposedTopways(pieces)) {

                        piece.moveDown();
                        this.redrawBlocks();

                        putPiece();
                    
                    }
                }

                break;

            case KeyEvent.VK_UP :

                piece.flip();
                this.redrawBlocks();
                putPiece();

                break;

            case KeyEvent.VK_ESCAPE :
                System.exit(0);
                break;
        }
    }

    public void mousePressed(MouseEvent me) {

        gameFrame.requestFocus();

        if(me.getSource() == con) {

            connect();
        }

    }

    public void startTetris(SuperTetris tetris) {
        
        tetris.panel.setVisible(true);

        animate();

        tetris.gameFrame.setVisible(true);

        tetris.waitForAccept();
    }

    public void keyTyped(KeyEvent e){}public void keyReleased(KeyEvent e){}
    public void mouseClicked(MouseEvent me){}public void mouseReleased(MouseEvent me){}public void mouseEntered(MouseEvent me){}public void mouseExited(MouseEvent me){}

    public static void main(String args[]) {
        SuperTetris tetris = new SuperTetris();
        tetris.startTetris(tetris);
    }
}