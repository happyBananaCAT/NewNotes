package wuziqi;

import javazoom.jl.decoder.JavaLayerException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyJFrame extends JFrame implements MouseListener{

    Data dt = new Data();

    int qx = dt.getQx(), qy = dt.getQy(), qw = dt.getQw(), qh = dt.getQh();
    int bw = dt.getBw(), bh = dt.getBh(), bx = dt.getBx(), by = dt.getBy();
    public int x = 0, y = 0;
    public int[][] GameMap = new int[15][15];
    int qc = 1;//记录白棋=2，黑棋=1
    int qn = 0;//判断棋子是否重复，继而得知是谁的回合
    boolean canplay = true;
    String go = "目前是黑棋的回合";
    int bq = 0, hq = 0;//黑子数  白子数


    public void myJFrame() throws FileNotFoundException, JavaLayerException {
        ImageValue.init();
        this.setTitle("five-in-a-row");
        this.setSize(800, 550);
        this.setResizable(false);
        this.setDefaultCloseOperation(MyJFrame.EXIT_ON_CLOSE); //3

        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setLocation((width - 800) / 2, (height - 600) / 2);

        this.addMouseListener(this);

        this.setVisible(true);
        BgMusic music = new BgMusic();
    }

    public class Position {
        //鼠标点击位置
        int listx;
        int listy;
    }

    public class chessUI extends JPanel {
        public Position[] ps = new Position[300];
        int i;
    }

    chessUI ui = new chessUI();
    Position p = new Position();

    //重写paint方法
    public void paint(Graphics g) {

        BufferedImage bi = new BufferedImage(800, 550, BufferedImage.TYPE_INT_ARGB);
        Graphics g2 = bi.createGraphics();

        //获取图片路径
        BufferedImage image = null;
        try {
            File directory = new File("");
            image = ImageIO.read(new File(directory.getAbsolutePath() + "/image/5.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(image, 10, 10, this);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("华文行楷", 10, 50));
        g2.drawString("五子棋", 570, 100);

        //棋盘
        g2.setColor(new Color(209,146,17));
        g2.fillRect(dt.getQx(), dt.getQy(), dt.getQw(), dt.getQh());

        //开始
        g2.setColor(Color.WHITE);
        g2.fillRect(dt.getBx(), dt.getBy(), dt.getBw(), dt.getBh());
        g2.setFont(new Font("华文行楷", 10, 30));
        g2.setColor(Color.black);
        g2.drawString("开始", 615, 185);

        //悔棋
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(bx, by + 60, bw, bh);
        g2.setFont(new Font("华文行楷", 10, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("悔棋", 615, 245);

        //认输
        g2.setColor(Color.GRAY);
        g2.fillRect(bx, by + 120, bw, bh);
        g2.setFont(new Font("华文行楷", 10, 30));
        g2.setColor(Color.WHITE);
        g2.drawString("认输", 615, 305);

        g2.setColor(Color.getHSBColor(30, (float) 0.10, (float) 0.90));
        g2.fillRect(550, 350, 200, 150);
        g2.setColor(Color.black);
        g2.setFont(new Font("黑体", 10, 20));
        g2.drawString("游戏信息", 610, 380);
        g2.drawString(go, 570, 410);

        g2.setColor(Color.BLACK);

        //绘制棋盘格线
        for (int x = 0; x <= qw; x += 35) {
            g2.drawLine(qx, x + qy, qw + qx, x + qy); //绘制一条横线
            g2.drawLine(x + qx, qy, x + qx, qh + qy); //绘制一条竖线
        }

        //绘制标注点
        for (int i = 3; i <= 11; i += 4) {
            for (int y = 3; y <= 11; y += 4) {
                g2.fillOval(35 * i + qx - 3, 35 * y + qy - 3, 6, 6); //绘制实心圆
            }
        }

        //绘制棋子
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (GameMap[i][j] == 1) //黑子
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;
                    g2.drawImage(ImageValue.blackImage,sx-15,sy-15,30,30,null);
                    hq++;//黑棋数
                }
                if (GameMap[i][j] == 2) //白子
                {
                    int sx = i * 35 + qx;
                    int sy = j * 35 + qy;
                    g2.drawImage(ImageValue.whiteImage,sx-15,sy-15,30,30,null);
                    bq++;//白棋数
                }
            }
        }
        g.drawImage(bi, 0, 0, this);
    }

    //判断输赢
    private boolean WinLose() {
        boolean flag = false;
        int count = 1;
        int color = GameMap[x][y]; //记录棋子颜色

        //横向
        int i = 1;
        while (color == GameMap[x + i][y]) {
            count++;
            i++;
        }

        i = 1;
        while (color == GameMap[x - i][y]) {
            count++;
            i++;
        }
        if (count >= 5) {
            flag = true;
        }

        //纵向
        count = 1;
        i = 1;
        while (color == GameMap[x][y + i]) {
            count++;
            i++;
        }

        i = 1;
        while (color == GameMap[x][y - i]) {
            count++;
            i++;
        }
        if (count >= 5) {
            flag = true;
        }

        //斜向（左上右下）
        count = 1;
        i = 1;
        while (color == GameMap[x - i][y - i]) {
            count++;
            i++;
        }

        i = 1;
        while (color == GameMap[x + i][y + i]) {
            count++;
            i++;
        }
        if (count >= 5) {
            flag = true;
        }

        //斜向（左下右上）
        count = 1;
        i = 1;
        while (color == GameMap[x + i][y - i]) {
            count++;
            i++;
        }

        i = 1;
        while (color == GameMap[x - i][y + i]) {
            count++;
            i++;
        }
        if (count >= 5) {
            flag = true;
        }

        return flag;
    }

    //初始化
    public void Init() {
        //黑白子数置0
        bq = 0;
        hq = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                GameMap[i][j] = 0;
            }
        }

        //黑子先行
        qc = 1;
        go = "目前是黑棋的回合";
    }

    @Override //鼠标点击
    public void mouseClicked(MouseEvent e) {

    }

    @Override //鼠标按下
    public void mousePressed(MouseEvent e) {
        if (canplay) {
            //鼠标点击位置
            x = e.getX();
            y = e.getY();

            ui.ps[ui.i] = p;
            //是否在棋盘内
            if (x > qx && x < qx + qw && y > qy && y < qy + qh) {
                if ((x - qx) % 35 > 17) {
                    x = (x - qx) / 35 + 1;
                } else {
                    x = (x - qx) / 35;
                }
                if ((y - qy) % 35 > 17) {
                    y = (y - qy) / 35 + 1;
                } else {
                    y = (y - qy) / 35;
                }
                //单纯的记录下棋子的位置
                ui.ps[ui.i].listx = x;
                ui.ps[ui.i].listy = y;
                System.out.println(x+" "+y);
                ui.i++;

                if (GameMap[x][y] == 0) {
                    GameMap[x][y] = qc;//记录白棋=2，黑棋=1
                    qn = 0;
                } else {
                    qn = 1;
                }

                //切换棋子
                if (qn == 0) {
                    if (qc == 1) {
                        qc = 2;
                        go = "目前是白棋的回合";
                    } else {
                        qc = 1;
                        go = "目前是黑棋的回合";
                    }
                }

                this.repaint();

                boolean wl = this.WinLose();
                if (wl) {
                    //看最后下的是谁的棋子
                    JOptionPane.showMessageDialog(this, "游戏结束，" +
                            (GameMap[x][y] == 1 ? "黑方赢了" : "白方赢了")); //弹出提示对话框
                    canplay = false;
                }

                //弹平局
                if (bq + hq == 255) {
                    JOptionPane.showMessageDialog(this, "游戏结束，平局！");
                    canplay = false;
                }
            }
        }

        //开始
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by && e.getY() < by + bh) {
            if (!canplay) {
                canplay = true;
                JOptionPane.showMessageDialog(this, "游戏开始");
                Init();
                this.repaint();

            } else {
                JOptionPane.showMessageDialog(this, "重新开始");
                Init();

                this.repaint();
            }
        }

        //悔棋
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 60 && e.getY() < by + 60 + bh) {
            if (canplay) {
                int z = 0;//遍历棋盘上是否有棋子
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        if (GameMap[i][j] != 0) {
                            z++;
                        }
                    }
                }
                if (z != 0) {
                    int result = JOptionPane.showConfirmDialog(this,
                            "确认要悔棋吗？");
                    if (result == 0) {
                        int x = ui.ps[ui.i - 1].listx;
                        int y = ui.ps[ui.i - 1].listy;

                        if (GameMap[x][y] == 0){
                            JOptionPane.showMessageDialog(this,
                                    "已悔过一次棋了！");
                        }else{
                            if (GameMap[x][y] == 1) {
                                qc = 1;
                                go = "轮到黑子";
                            } else if (GameMap[x][y] == 2){
                                qc = 2;
                                go = "轮到白子";
                            }
                            GameMap[x][y] = 0;
                            ui.i--;
                            this.repaint();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(this, "棋盘上已无棋子");
                }

            } else {
                JOptionPane.showMessageDialog(this, "请先开始游戏");
            }
        }

        //认输
        if (e.getX() > bx && e.getX() < bx + bw && e.getY() > by + 120 && e.getY() < by + 120 + bh) {
            if (canplay) {
                //qc判断是谁认输
                if (qc == 1) {
                    JOptionPane.showMessageDialog(this,
                            "黑方认输，白方获胜");
                    canplay = false;
                } else if (qc == 2) {
                    JOptionPane.showMessageDialog(this,
                            "白方认输，黑方获胜");
                    canplay = false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "请先开始游戏");
            }
        }
    }

    @Override//鼠标抬起
    public void mouseReleased(MouseEvent e) {

    }

    @Override//鼠标进入
    public void mouseEntered(MouseEvent e) {

    }

    @Override//鼠标离开
    public void mouseExited(MouseEvent e) {

    }
}
