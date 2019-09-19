import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

public class MineSweeper {

    private JPanel panel;
    private JPanel panel1;
    private JFrame mainFrame;
    private JFrame subFrame;
    private static final int WIDTH=1000;
    private static final int HEIGHT=800;
    private double width;
    private double height;
    private int grade;
    private JButton[][] buttons;
    private int[][] mines;
    private Thread t,t1,t2;
    private JLabel l1,l2;
    private boolean loose;
    private int flag;

    public MineSweeper(){
        mainFrame=new JFrame();
        panel=new JPanel();
        panel1=new JPanel();
        loose=false;
        flag=0;
        mainFrame.setSize(WIDTH,HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        mainFrame.setLocation((int)width/2-WIDTH/2,(int)height/2-HEIGHT/2);
        panel.setLayout(new GridLayout(16,16,1,1));
        panel1.setLayout(new GridLayout(1,2));
        mainFrame.setLayout(new BorderLayout());
        subFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void subFrame(){
        subFrame=new JFrame();
        subFrame.setSize(400,200);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        subFrame.setLocation((int)width/2-200,(int)height/2-100);
        subFrame.setLayout(new GridLayout(3,1));
        JButton b1,b2,b3;
        b1=new JButton("Easy");
        b2=new JButton("Medium");
        b3=new JButton("Hard");
        b1.setFocusPainted(false);
        b1.setContentAreaFilled(false);
        b1.setBorderPainted(false);
        b1.setOpaque(true);
        b1.setBackground(Color.black);
        b2.setFocusPainted(false);
        b2.setContentAreaFilled(false);
        b2.setOpaque(true);
        b2.setBorderPainted(false);
        b2.setBackground(Color.black);
        b3.setFocusPainted(false);
        b3.setContentAreaFilled(false);
        b3.setOpaque(true);
        b3.setBackground(Color.black);
        b3.setBorderPainted(false);
        b1.setFont(new Font("Arial",Font.BOLD,20));
        b2.setFont(new Font("Arial",Font.BOLD,20));
        b3.setFont(new Font("Arial",Font.BOLD,20));
        subFrame.add(b1);
        subFrame.add(b2);
        subFrame.add(b3);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grade=1;
                subFrame.dispose();
                setNumbers();
                createButtons();
                mainFrame.setVisible(true);
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grade=2;
                subFrame.dispose();
                setNumbers();
                createButtons();
                mainFrame.setVisible(true);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grade=3;
                subFrame.dispose();
                setNumbers();
                createButtons();
                mainFrame.setVisible(true);
            }
        });
        subFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        subFrame.setVisible(true);
    }

    public int getCounter(int n1,int n2){
        int counter=0;
        for(int i=n1-1; i<=n1+1; ++i){
            for(int j=n2-1; j<=n2+1; ++j){
                if((i>=0) && (i<=15) && (j>=0) && (j<=15)){
                    if(mines[i][j]==-1){
                        ++counter;
                    }
                }
            }
        }
        return counter;
    }

    public int getMaxCounter(int n1,int n2){
        int counterMax=0;
        for(int i=n1-1; i<=n1+1; ++i){
            for(int j=n2-1; j<=n2+1; ++j){
                if((i>=0) && (i<=15) && (j>=0) && (j<=15)){
                    if(getCounter(i,j)>=counterMax){
                        counterMax=getCounter(i,j);
                    }
                }
            }
        }
        return counterMax;
    }

    public void setNumbers(){
            int counter=0;
            mines=new int[16][16];
            if(grade==1){
                while(counter<=29){
                    Random random=new Random();
                    int choose=random.nextInt(256);
                    if(mines[choose/16][choose%16]!=-1){
                        if(getMaxCounter(choose/16,choose%16)<3){
                            mines[choose/16][choose%16]=-1;
                            ++counter;
                        }
                    }
                }
            }
        if(grade==2){
            while(counter<=59){
                Random random=new Random();
                int choose=random.nextInt(256);
                if(mines[choose/16][choose%16]!=-1){
                    if(getMaxCounter(choose/16,choose%16)<6) {
                        mines[choose/16][choose%16]=-1;
                        ++counter;
                    }
                }
            }
        }
        if(grade==3){
            while(counter<=79){
                Random random=new Random();
                int choose=random.nextInt(256);
                if(mines[choose/16][choose%16]!=-1){
                    mines[choose/16][choose%16]=-1;
                    ++counter;
                }
            }
        }

            for(int i=0; i<=15; ++i){
                for(int j=0; j<=15; ++j){
                    if(mines[i][j]!=-1){
                        int counter1=0;
                        if((i-1>=0) && (j-1>=0)){
                            if(mines[i-1][j-1]==-1){
                                ++counter1;
                            }
                        }
                        if(i-1>=0){
                            if(mines[i-1][j]==-1){
                                ++counter1;
                            }
                        }
                        if((i-1>=0) && (j+1<=15)){
                            if(mines[i-1][j+1]==-1){
                                ++counter1;
                            }
                        }
                        if(j-1>=0){
                            if(mines[i][j-1]==-1){
                                ++counter1;
                            }
                        }
                        if(j+1<=15){
                            if(mines[i][j+1]==-1){
                                ++counter1;
                            }
                        }
                        if((i+1<=15) && (j-1>=0)){
                            if(mines[i+1][j-1]==-1){
                                ++counter1;
                            }
                        }
                        if(i+1<=15){
                            if(mines[i+1][j]==-1){
                                ++counter1;
                            }
                        }
                        if((i+1<=15) && (j+1<=15)){
                            if(mines[i+1][j+1]==-1){
                                ++counter1;
                            }
                        }
                        mines[i][j]=counter1;
                    }
                }
            }
    }

    public void finalFrame(String input){
        JFrame f=new JFrame();
        f.setSize(500,300);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = screenSize.getWidth();
        height = screenSize.getHeight();
        f.setLocation((int)width/2-250,(int)height/2-150);
        JButton b1,b2;
        JLabel l;
        b1=new JButton("Retry");
        b2=new JButton("Quite");
        b1.setFocusPainted(false);
        b2.setFocusPainted(false);
        b1.setFont(new Font("Arial",Font.BOLD,20));
        b2.setFont(new Font("Arial",Font.BOLD,20));
        b1.setBackground(Color.black);
        b2.setBackground(Color.black);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                f.dispose();
                MineSweeper mineSweeper=new MineSweeper();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.dispose();
                f.dispose();
            }
        });
        l=new JLabel();
        l.setText(input);
        l.setFont(new Font("Arial",Font.BOLD,25));
        f.setLayout(new GridLayout(3,1));
        f.add(l);
        f.add(b1);
        f.add(b2);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setAlwaysOnTop(true);
        mainFrame.setEnabled(false);
        f.setVisible(true);
    }

    public void win(){
        int counter=0;
        for(int i=0; i<=15; ++i){
            for(int j=0; j<=15; ++j){
                if(!buttons[i][j].isFocusPainted()){
                    ++counter;
                }
            }
        }
        if(grade==1){
            if(counter==226 && !loose){
                if(t1!=null)
                    t1.stop();
                if(t2!=null)
                    t2.stop();
                finalFrame("                           You win!!!      Time : "+l1.getText());
            }
        }
        if(grade==2){
            if(counter==196 && !loose){
                if(t1!=null)
                    t1.stop();
                if(t2!=null)
                    t2.stop();
                finalFrame("                           You win!!!      Time : "+l1.getText());
            }
        }
        if(grade==3){
            if(counter==176 && !loose){
                if(t1!=null)
                    t1.stop();
                if(t2!=null)
                    t2.stop();
                finalFrame("                           You win!!!      Time : "+l1.getText());
            }
        }
    }

    public void createButtons(){

        buttons=new JButton[16][16];

        for(int i=0; i<=15; ++i){
            for(int j=0; j<=15; ++j){

                final int[] counter = {0};
                buttons[i][j]=new JButton();

                int finalI = i;
                int finalJ = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buttons[finalI][finalJ].setIcon(null);
                        if(mines[finalI][finalJ]==0){
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setFocusPainted(false);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            t=new Thread(){
                                @Override
                                public void run(){
                                    createEmptyButtons(finalI,finalJ);
                                }
                            };
                            t.start();
                            for(int k=0; k<=100; ++k){
                                removeEmptyButtons();
                            }
                            buttons[finalI][finalJ].setEnabled(false);
                        }
                        if(mines[finalI][finalJ]==1){
                            buttons[finalI][finalJ].setText("1");
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(Color.green);
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==2){
                            buttons[finalI][finalJ].setText("2");
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(new Color(0x4543A1));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==3){
                            buttons[finalI][finalJ].setText("3");
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(new Color(0x860F03));
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==4){
                            buttons[finalI][finalJ].setText("4");
                            buttons[finalI][finalJ].setOpaque(true);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setForeground(new Color(0x9C5B82));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==5){
                            buttons[finalI][finalJ].setText("5");
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(new Color(0xC78C19));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==6){
                            buttons[finalI][finalJ].setText("6");
                            buttons[finalI][finalJ].setOpaque(true);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setForeground(new Color(0x3C9E91));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==7){
                            buttons[finalI][finalJ].setText("7");
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(new Color(0x3F000000, true));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                        }
                        if(mines[finalI][finalJ]==8){
                            buttons[finalI][finalJ].setText("8");
                            buttons[finalI][finalJ].setOpaque(true);
                            buttons[finalI][finalJ].setForeground(new Color(0x010101));
                            buttons[finalI][finalJ].setFocusPainted(false);
                            buttons[finalI][finalJ].setContentAreaFilled(false);
                            MouseListener[] mouseListeners=buttons[finalI][finalJ].getMouseListeners();
                            try{
                                buttons[finalI][finalJ].removeMouseListener(mouseListeners[1]);
                            }
                            catch(Exception ex){

                            }
                            buttons[finalI][finalJ].setFont(new Font("Arial",Font.BOLD,25));
                        }
                        if(mines[finalI][finalJ]==-1){
                            if(t1!=null)
                                t1.stop();
                            if(t2!=null)
                                t2.stop();
                            finalFrame("                           You lost!!!      Time : "+l1.getText());
                        }
                        win();
                    }
                });
                buttons[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(e.getButton()==3){
                            if(counter[0]==3)
                                counter[0]=0;
                            ++counter[0];
                            Image img1=null;
                            Image img2=null;
                            try {
                                img1 = ImageIO.read(getClass().getResource("flag.png")).getScaledInstance(75,75,Image.SCALE_SMOOTH);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            try {
                                img2 = ImageIO.read(getClass().getResource("question.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if(counter[0]%2==1 && (counter[0]/2)%2==0){
                                buttons[finalI][finalJ].setIcon(new ImageIcon(img1));
                                l2.setText(Integer.toString(Integer.parseInt(l2.getText())-1));
                                if(mines[finalI][finalJ]==-1){
                                    ++flag;
                                }
                            }
                            if(counter[0]%2==0){
                                buttons[finalI][finalJ].setIcon(new ImageIcon(img2));
                                l2.setText(Integer.toString(Integer.parseInt(l2.getText())+1));
                                if(mines[finalI][finalJ]==-1){
                                    --flag;
                                }
                            }
                            if(counter[0]%2==1 && (counter[0]/2)%2==1){
                                buttons[finalI][finalJ].setIcon(null);
                            }
                            if(grade==1){
                                if(flag==30){
                                    mainFrame.dispose();
                                    finalFrame("                           You win!!!      Time : "+l1.getText());
                                }
                            }
                            if(grade==2){
                                if(flag==60){
                                    mainFrame.dispose();
                                    finalFrame("                           You win!!!      Time : "+l1.getText());
                                }
                            }
                            if(grade==3){
                                if(flag==80){
                                    mainFrame.dispose();
                                    finalFrame("                           You win!!!      Time : "+l1.getText());
                                }
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
                });
                panel.add(buttons[i][j]);
            }
        }


        JPanel panel2,panel3;
        panel1.setBackground(Color.gray);
        panel1.setForeground(Color.gray);
        panel2=new JPanel();
        panel3=new JPanel();
        panel2.setLayout(new GridLayout(1,2));
        panel3.setLayout(new GridLayout(1,2));
        JButton b1,b2;
        b1=new JButton();
        b2=new JButton();
        l1=new JLabel();
        l2=new JLabel();
        l1.setText("0");
        l1.setFont(new Font("Arial",Font.BOLD,25));
        if(grade==1){
            l2.setText("30");
        }
        if(grade==2){
            l2.setText("60");
        }
        if(grade==3){
            l2.setText("80");
        }
        l2.setFont(new Font("Arial",Font.BOLD,25));
        Image img=null;
        Image img1=null;
        try {
            img = ImageIO.read(getClass().getResource("clock.png")).getScaledInstance(75,75,Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            img1 = ImageIO.read(getClass().getResource("bomb.png")).getScaledInstance(75,75,Image.SCALE_SMOOTH);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        b1.setIcon(new ImageIcon(img));
        b1.setContentAreaFilled(false);
        b1.setBorderPainted(false);
        b1.setFocusPainted(false);
        b2.setIcon(new ImageIcon(img1));
        b2.setContentAreaFilled(false);
        b2.setBorderPainted(false);
        b2.setFocusPainted(false);
        panel2.add(b1);
        panel2.add(l1);
        panel3.add(b2);
        panel3.add(l2);
        panel2.setBackground(Color.gray);
        panel3.setBackground(Color.gray);
        panel1.add(panel2);
        panel1.add(panel3);
        panel1.setBackground(Color.gray);
        t1=new Thread(){
            @Override
            public void run(){
                try {
                    while (true){
                        Thread.sleep(1000);
                        l1.setText(Integer.toString(Integer.parseInt(l1.getText())+1));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        mainFrame.add(panel,BorderLayout.CENTER);
        mainFrame.add(panel1,BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public void createEmptyButtons(int n1,int n2){

        boolean flag=true;
        int row=n1;
        int column=n2;
        int counter=1;
        int counter1=0;
        boolean right=true;
        boolean left=true;
        boolean up=true;
        boolean down=true;

        while(flag){

            if(column+counter>=0 && column+counter<=15 && (row>=0) && (row<=15)){
                if((mines[row][column+counter]==0) && (right)){
                    buttons[row][column+counter].setIcon(null);
                    buttons[row][column+counter].setFocusPainted(false);
                    buttons[row][column+counter].setContentAreaFilled(false);
                    MouseListener[] mouseListeners=buttons[row][column+counter].getMouseListeners();
                    try{
                        buttons[row][column+counter].removeMouseListener(mouseListeners[1]);
                    }
                    catch(Exception ex){

                    }
                    buttons[row][column+counter].setEnabled(false);
                    if(row==15){
                        up=false;
                    }
                    if(row==0){
                        down=false;
                    }
                }
                else if(mines[row][column+counter]>0 && (right)){
                    if(counter!=0){
                        right=false;
                    }
                    if(row>n1 && (counter==0)){
                        up=false;
                    }
                    if(row<n1 && (counter==0)){
                        down=false;
                    }
                    buttons[row][column+counter].setIcon(null);
                    buttons[row][column+counter].setOpaque(true);
                    buttons[row][column+counter].setContentAreaFilled(false);
                    buttons[row][column+counter].setFocusPainted(false);
                    MouseListener[] mouseListeners=buttons[row][column+counter].getMouseListeners();
                    try{
                        buttons[row][column+counter].removeMouseListener(mouseListeners[1]);
                    }
                    catch(Exception ex){

                    }
                    buttons[row][column+counter].setFont(new Font("Arial",Font.BOLD,25));
                    buttons[row][column+counter].setText(Integer.toString(mines[row][column+counter]));
                    if(mines[row][column+counter]==1){
                        buttons[row][column+counter].setForeground(Color.green);
                    }
                    if(mines[row][column+counter]==2){
                        buttons[row][column+counter].setForeground(new Color(0x4543A1));
                    }
                    if(mines[row][column+counter]==3){
                        buttons[row][column+counter].setForeground(new Color(0x860F03));
                    }
                    if(mines[row][column+counter]==4){
                        buttons[row][column+counter].setForeground(new Color(0x9C5B82));
                    }
                    if(mines[row][column+counter]==5){
                        buttons[row][column+counter].setForeground(new Color(0xC78C19));
                    }
                    if(mines[row][column+counter]==6){
                        buttons[row][column+counter].setForeground(new Color(0x3C9E91));
                    }
                    if(mines[row][column+counter]==7){
                        buttons[row][column+counter].setForeground(new Color(0x3F000000, true));
                    }
                    if(mines[row][column+counter]==8){
                        buttons[row][column+counter].setForeground(new Color(0x010101));
                    }
                }
            }
            if(column-counter>=0 && column-counter<=15 && (row>=0) && (row<=15)){
                if(mines[row][column-counter]==0 && (left)){
                    buttons[row][column-counter].setIcon(null);
                    buttons[row][column-counter].setFocusPainted(false);
                    MouseListener[] mouseListeners=buttons[row][column-counter].getMouseListeners();
                    try{
                        buttons[row][column-counter].removeMouseListener(mouseListeners[1]);
                    }
                    catch(Exception ex){

                    }
                    buttons[row][column-counter].setContentAreaFilled(false);
                    buttons[row][column-counter].setEnabled(false);
                    if(row==15){
                        up=false;
                    }
                    if(row==0){
                        down=false;
                    }
                }
                else if(mines[row][column-counter]>0 && (left)){
                    if(counter!=0){
                        left=false;
                    }
                    if(row>n1 && (counter==0)){
                        up=false;
                    }
                    if(row<n1 && (counter==0)){
                        down=false;
                    }
                    buttons[row][column-counter].setIcon(null);
                    buttons[row][column-counter].setOpaque(true);
                    buttons[row][column-counter].setContentAreaFilled(false);
                    MouseListener[] mouseListeners=buttons[row][column-counter].getMouseListeners();
                    try{
                        buttons[row][column-counter].removeMouseListener(mouseListeners[1]);
                    }
                    catch(Exception ex){

                    }
                    buttons[row][column-counter].setFocusPainted(false);
                    buttons[row][column-counter].setFont(new Font("Arial",Font.BOLD,25));
                    buttons[row][column-counter].setText(Integer.toString(mines[row][column-counter]));
                    if(mines[row][column-counter]==1){
                        buttons[row][column-counter].setForeground(Color.green);
                    }
                    if(mines[row][column-counter]==2){
                        buttons[row][column-counter].setForeground(new Color(0x4543A1));
                    }
                    if(mines[row][column-counter]==3){
                        buttons[row][column-counter].setForeground(new Color(0x860F03));
                    }
                    if(mines[row][column-counter]==4){
                        buttons[row][column-counter].setForeground(new Color(0x9C5B82));
                    }
                    if(mines[row][column-counter]==5){
                        buttons[row][column-counter].setForeground(new Color(0xC78C19));
                    }
                    if(mines[row][column-counter]==6){
                        buttons[row][column-counter].setForeground(new Color(0x3C9E91));
                    }
                    if(mines[row][column-counter]==7){
                        buttons[row][column-counter].setForeground(new Color(0x3F000000, true));
                    }
                    if(mines[row][column-counter]==8){
                        buttons[row][column-counter].setForeground(new Color(0x010101));
                    }
                }
            }

            if(column+counter==16){
                right=false;
            }
            if(column-counter==-1){
                left=false;
            }
            if(row==16){
                up=false;
            }
            if(row==-1){
                down=false;
            }
            ++counter;
            if((!right) && (!left) && (!up) && (!down)){
                t.stop();
                flag=false;
            }
            if((!right) && (!left)){
                if(up){
                    ++row;
                }
                else if(down){
                    if(row>n1){
                        row=n1;
                    }
                    --row;
                }
                counter=0;
                right=true;
                left=true;
            }
        }
    }

    public void removeEmptyButtons(){

        for(int i=0; i<=15; ++i){
            for(int j=0; j<=15; ++j){

                if(!buttons[i][j].isContentAreaFilled() && (mines[i][j]==0)){

                    for(int k=i-1; k<=i+1; ++k){
                        for(int z=j-1; z<=j+1; ++z){
                            if((k>=0) && (k<=15) && (z>=0) && (z<=15)){
                                if(buttons[k][z].isContentAreaFilled()){
                                    if(mines[k][z]==0){

                                        buttons[k][z].setIcon(null);
                                        buttons[k][z].setOpaque(true);
                                        MouseListener[] mouseListeners=buttons[k][z].getMouseListeners();
                                        try{
                                            buttons[k][z].removeMouseListener(mouseListeners[1]);
                                        }
                                        catch(Exception ex){

                                        }
                                        buttons[k][z].setFocusPainted(false);
                                        buttons[k][z].setContentAreaFilled(false);
                                        int finalK = k;
                                        int finalZ = z;
                                        t=new Thread(){
                                            @Override
                                            public void run(){
                                                createEmptyButtons(finalK, finalZ);
                                            }
                                        };
                                        t.start();
                                        buttons[k][z].setEnabled(false);

                                    }
                                    else if(mines[k][z]!=0 && mines[k][z]!=-1){

                                        buttons[k][z].setIcon(null);
                                        buttons[k][z].setOpaque(true);
                                        buttons[k][z].setContentAreaFilled(false);
                                        buttons[k][z].setFocusPainted(false);
                                        buttons[k][z].setFont(new Font("Arial",Font.BOLD,25));
                                        buttons[k][z].setText(Integer.toString(mines[k][z]));
                                        MouseListener[] mouseListeners=buttons[k][z].getMouseListeners();
                                        try{
                                            buttons[k][z].removeMouseListener(mouseListeners[1]);
                                        }
                                        catch(Exception ex){

                                        }
                                        if(mines[k][z]==1){
                                            buttons[k][z].setForeground(Color.green);
                                        }
                                        if(mines[k][z]==2){
                                            buttons[k][z].setForeground(new Color(0x4543A1));
                                        }
                                        if(mines[k][z]==3){
                                            buttons[k][z].setForeground(new Color(0x860F03));
                                        }
                                        if(mines[k][z]==4){
                                            buttons[k][z].setForeground(new Color(0x9C5B82));
                                        }
                                        if(mines[k][z]==5){
                                            buttons[k][z].setForeground(new Color(0xC78C19));
                                        }
                                        if(mines[k][z]==6){
                                            buttons[k][z].setForeground(new Color(0x3C9E91));
                                        }
                                        if(mines[k][z]==7){
                                            buttons[k][z].setForeground(new Color(0x3F000000, true));
                                        }
                                        if(mines[k][z]==8){
                                            buttons[k][z].setForeground(new Color(0x010101));
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

            }
        }

    }

    public static void main(String[] args) {
        MineSweeper mineSweeper=new MineSweeper();
    }
}
