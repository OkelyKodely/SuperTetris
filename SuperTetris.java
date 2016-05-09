import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SuperTetris implements MouseListener, KeyListener {

    public SuperTetris() {

        gameFrame.setLayout(null);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int width = dimension.width;

        gameFrame.setLocation(width/2-225, 0);

        gameFrame.setSize(1200, 790);

        panel.setLocation(0, 0);

        panel.setBackground(Color.white);

        panel.setSize(150, 790);

        gameFrame.add(panel);

        gamePanel.setLocation(0, 0);

        gamePanel.setSize(450, 790);

        mainPanel.add(gamePanel);

        oppPanel.setLocation(450, 0);

        oppPanel.setBackground(Color.white);

        oppPanel.setSize(150, 790);

        oppPanel.setLayout(null);

        ip.setSize(120, 20);

        ip.setLocation(10, 20);

        port_.setSize(120, 20);

        port_.setLocation(10, 41);

        port_server.setSize(120, 20);

        port_server.setLocation(10, 62);

        con.setSize(120, 20);

        con.setLocation(10, 83);

        con.addMouseListener(this);

        wait.setSize(120, 20);

        wait.setLocation(10, 104);

        wait.addMouseListener(this);

        oppPanel.add(ip);

        oppPanel.add(port_);

        oppPanel.add(port_server);

        oppPanel.add(con);

        oppPanel.add(wait);

        oppLinesLbl.setLocation(10, 104);

        oppLinesLbl.setSize(150, 20);

        oppPanel.add(oppLinesLbl);

        mainPanel.add(oppPanel);

        oppGamePanel.setLocation(600, 0);

        oppGamePanel.setSize(450, 790);

        mainPanel.add(oppGamePanel);

        mainPanel.setLayout(null);

        mainPanel.setLocation(150, 0);

        mainPanel.setSize(1050, 790);

        gameFrame.add(mainPanel);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameFrame.setResizable(false);

        gameFrame.addKeyListener(this);

        gameFrame.setVisible(true);

        linesLbl.setLocation(10, 70);

        linesLbl.setSize(150, 20);

        JLabel fruits1 = new JLabel("SUPER TETRIS");

        JLabel fruits2 = new JLabel("do do do dodo do do do!");

        fruits1.setLocation(10, 100);

        fruits2.setLocation(10, 120);
        
        fruits1.setFont(new Font("arial", Font.ITALIC, 12));

        fruits2.setFont(new Font("arial", Font.ITALIC, 10));

        panel.add(fruits1);

        panel.add(fruits2);

        panel.add(linesLbl);

    }

    private JTextField ip = new JTextField();
    private JTextField port_ = new JTextField();
    private JTextField port_server = new JTextField();
    private JButton wait = new JButton("wait.");
    private JButton con = new JButton("connect.");

    private ServerSocket serverSocket = null;

    private Socket clientSocket = null;

    private Socket socket = null;

    private String ipAddress = "x.x.x.x";

    private int port = 2000;

    private int serverport = 4000;

    public boolean connect = false;

    public boolean accept = false;

    private DataOutputStream dOut = null;
    
    private String thepiece = "";

    private String thegetpiece = "";

    private int lines = 0;

    private int oppLines = 0;

    private JLabel linesLbl = new JLabel("Lines: " + lines);

    private JLabel oppLinesLbl = new JLabel("Lines: " + oppLines);

    private int board[][] = new int[17][10];

    private ArrayList<Piece> pieces = new ArrayList<Piece>();

    private ArrayList<Piece> oppPieces = new ArrayList<Piece>();

    private Piece piece;

    private Piece oppPiece;

    private int delay = 2000;

    private JFrame gameFrame = new JFrame("SUPER TETRIS");

    private JPanel mainPanel = new JPanel();

    private GamePanel gamePanel = new GamePanel();

    private GamePanel oppGamePanel = new GamePanel();

    private JPanel panel = new JPanel();

    private JPanel oppPanel = new JPanel();

    private boolean isNotDown(Piece piece) {
       if(piece.blocks.get(0).y == 16
                ||
                piece.blocks.get(1).y == 16
                ||
                piece.blocks.get(2).y == 16
                ||
                piece.blocks.get(3).y == 16) {
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

    public void connect() {
        try {
            ipAddress = ip.getText();
            port = Integer.valueOf(port_.getText());
            System.out.println(ipAddress + " " + port);
            socket = new Socket(ipAddress, port);
            dOut = new DataOutputStream(socket.getOutputStream());
            connect = true;
            System.out.println("conn");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForAccept() {
        try {
            System.out.println("waiti1");
            serverport = Integer.valueOf(port_server.getText());
            System.out.println("waiti2");
            serverSocket = new ServerSocket(serverport);
            System.out.println("waiti3");
            clientSocket = serverSocket.accept();
            System.out.println("waiti4");
            accept = true;
            System.out.println("waiti5");
            System.out.println("conn " + connect + " accept " + accept);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void putPiece() {
        try {
            thepiece = "";
            for(int i=0; i<pieces.size(); i++) {
                String val = System.getProperty("line.separator");
                if(i == pieces.size() - 1) {
                    thepiece += pieces.get(i).blocks.get(0).x + "," + pieces.get(i).blocks.get(0).y + ";" + pieces.get(i).blocks.get(1).x + "," + pieces.get(i).blocks.get(1).y + ";" + pieces.get(i).blocks.get(2).x + "," + pieces.get(i).blocks.get(2).y + ";" + pieces.get(i).blocks.get(3).x + "," + pieces.get(i).blocks.get(3).y + ";" + pieces.get(i).direction + ";" + pieces.get(i).getType() + ";" + lines;
                } else {
                    thepiece += pieces.get(i).blocks.get(0).x + "," + pieces.get(i).blocks.get(0).y + ";" + pieces.get(i).blocks.get(1).x + "," + pieces.get(i).blocks.get(1).y + ";" + pieces.get(i).blocks.get(2).x + "," + pieces.get(i).blocks.get(2).y + ";" + pieces.get(i).blocks.get(3).x + "," + pieces.get(i).blocks.get(3).y + ";" + pieces.get(i).direction + ";" + pieces.get(i).getType() + ";" + lines + val;
                }
            }
            System.out.println("put2 " + thepiece);
            dOut.writeBytes(thepiece);
            System.out.println("put " + thepiece);
            dOut.flush();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getPiece() {
        try {
            byte[] messageByte = new byte[1000];
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            int bytesRead = in.read(messageByte);
            thegetpiece = new String(messageByte, 0, bytesRead);
            System.out.println("get " + thegetpiece);
        } catch (Exception e) {
            e.printStackTrace();
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

            getPiece();

            oppPieces.clear();
            String thethepiece = "";
            StringTokenizer stringTokenizer = new StringTokenizer(thegetpiece, System.getProperty("line.separator"));
            //System.out.println(thepiece);
            while(stringTokenizer.hasMoreElements()) {
                thethepiece = stringTokenizer.nextToken();
                StringTokenizer st = new StringTokenizer(thethepiece, ";");
                String block1x = st.nextToken();
                StringTokenizer st2 = new StringTokenizer(block1x, ",");
                block1x = st2.nextToken();
                String block1y = st2.nextToken();
                String block2x = st.nextToken();
                st2 = new StringTokenizer(block2x, ",");
                block2x = st2.nextToken();
                String block2y = st2.nextToken();
                String block3x = st.nextToken();
                st2 = new StringTokenizer(block3x, ",");
                block3x = st2.nextToken();
                String block3y = st2.nextToken();
                String block4x = st.nextToken();
                st2 = new StringTokenizer(block4x, ",");
                block4x = st2.nextToken();
                String block4y = st2.nextToken();
                String direction = st.nextToken();
                String thetype = st.nextToken();
                String opp_lines = st.nextToken();

                oppPiece = new Piece( "" + thetype );
                oppLines = Integer.valueOf(opp_lines);
                oppLinesLbl.setText("Lines: " + oppLines);
                oppPieces.add(oppPiece);

                oppPiece.setLocation(Integer.valueOf(block1x), Integer.valueOf(block1y));

                oppPiece.blocks.get(0).x = Integer.valueOf(block1x);

                oppPiece.blocks.get(0).y = Integer.valueOf(block1y);

                oppPiece.blocks.get(1).x = Integer.valueOf(block2x);

                oppPiece.blocks.get(1).y = Integer.valueOf(block2y);

                oppPiece.blocks.get(2).x = Integer.valueOf(block3x);

                oppPiece.blocks.get(2).y = Integer.valueOf(block3y);

                oppPiece.blocks.get(3).x = Integer.valueOf(block4x);

                oppPiece.blocks.get(3).y = Integer.valueOf(block4y);

                oppPiece.setType("current");

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
            }
            this.redrawOppBlocks();

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

            this.redrawBlocks();

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

            for(int y=0; y<17; y++) {
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

                            pieces.get(i).blocks.get(j).y = 1000;

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

            for(int i=0; i<17; i++) {
                for(int j=0; j<10; j++) {
                    this.board[i][j] = 0;
                }
            }
            for(int i=0; i<pieces.size(); i++) {
                for(int j=0; j<pieces.get(i).blocks.size(); j++) {
                    if(pieces.get(i).blocks.get(j).y != 1000) {
                        this.board[pieces.get(i).blocks.get(j).y][pieces.get(i).blocks.get(j).x] = 1;
                    }
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException aioobe) {
        }
    }

    public void redrawBlocks() {

        gamePanel.paintComponent(gamePanel.getGraphics());

        gamePanel.setPanel(gamePanel);

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/tetris.jpg"));

        Image image = imageIcon.getImage();

        Graphics g = gamePanel.getGraphics();

        g.drawImage(image, 0, 0, gamePanel.getWidth(), gamePanel.getHeight(), null);

        for(int i=0; i<pieces.size(); i++) {

            gamePanel.drawPiece(pieces.get(i));
        }
    }

    public void redrawOppBlocks() {

        gamePanel.paintComponent(oppGamePanel.getGraphics());

        gamePanel.setPanel(oppGamePanel);

        ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/tetris.jpg"));

        Image image = imageIcon.getImage();

        Graphics g = oppGamePanel.getGraphics();

        g.drawImage(image, 0, 0, oppGamePanel.getWidth(), oppGamePanel.getHeight(), null);

        for(int i=0; i<pieces.size(); i++) {

            oppGamePanel.drawPiece(pieces.get(i));
        }
    }

    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {

            case KeyEvent.VK_LEFT :

                if(!juxtaposedRightSideways(pieces) && piece.blocks.get(0).x > 0 && piece.blocks.get(1).x > 0 && piece.blocks.get(2).x > 0 && piece.blocks.get(3).x > 0) {
                    piece.moveLeft();
                    this.redrawBlocks();
                }

                break;

            case KeyEvent.VK_RIGHT :

                if(!juxtaposedLeftSideways(pieces) && piece.blocks.get(0).x < 9 && piece.blocks.get(1).x < 9 && piece.blocks.get(2).x < 9 && piece.blocks.get(3).x < 9) {
                    piece.moveRight();
                    this.redrawBlocks();
                }

                break;

            case KeyEvent.VK_SPACE :

                while(isNotDown(piece) && !juxtaposedTopways(pieces)) {
                    piece.moveDown();
                }
                this.redrawBlocks();

                break;

            case KeyEvent.VK_DOWN :

                if(isNotDown(piece)) {

                    if(!juxtaposedTopways(pieces)) {

                        piece.moveDown();
                        this.redrawBlocks();
                    
                    }
                
                }

                break;

            case KeyEvent.VK_UP :

                piece.flip();
                this.redrawBlocks();

                break;

            case KeyEvent.VK_ESCAPE :

                System.exit(0);
                
                break;
                
        }

    }
    public static void main(String args[]) {
        SuperTetris tetris = new SuperTetris();
        System.out.println("wa iti ng..");
        while(!tetris.accept || !tetris.connect) {
        }
        System.out.println("waafter iting...");
        tetris.play();
    }
    public void keyTyped(KeyEvent e){}public void keyReleased(KeyEvent e){}

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {

        if(me.getSource() == wait) {
            System.out.println("waiting...");
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    waitForAccept();
                    if(accept && connect) {
                        play();
                    }
                }
            });
            t1.start();
        }

        if(me.getSource() == con) {
            System.out.println("connecting...");
            Thread t2 = new Thread(new Runnable() {
                public void run() {
                    connect();
                    if(accept && connect) {
                        play();
                    }
                }
            });
            t2.start();
        }

        gameFrame.requestFocus();

    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }
}