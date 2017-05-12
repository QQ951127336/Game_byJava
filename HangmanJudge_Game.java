package myswing;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.pow;

/**
 * Created by Administrator on 5/12/2017.
 */
public class HangmanJudge_Game extends JFrame{
    private JButton button;
    private JTextArea inputtext;
    private MyDraw myDraw;
    private JTextArea outputtext;
    private String answer;
    private String[] words = {"hello","love","angel","family","storage","tooth","men","can","wait", "but","they","always"," say","women","wait","life"};
    private String input_answer = "";
    Random random;
    int wrong_times = 0;

    static HangmanJudge_Game game;
    private ArrayList<Character> arrayList = new ArrayList<>();
    public HangmanJudge_Game(){

        super("HangmanJudge");



        random = new Random((new Date().getTime())%100);

        System.out.println((new Date().getTime())%100);
        answer = words[random.nextInt(words.length)];
        for (int i=0;i<answer.length();i++)
        {
            arrayList.add(answer.charAt(i));
        }
        inputtext = new JTextArea();
        outputtext = new JTextArea();
        myDraw = new MyDraw();
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);


        outputtext.setText("游戏开始，您可以按下键盘猜词语，猜中的字母将显示在这里。");
        outputtext.setFocusable(false);
        add(outputtext);
        add(myDraw);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String a = String.valueOf(e.getKeyChar());
                System.out.println(answer);
                inputtext.setText("");
                if (a.length()>1)
                {

                    JOptionPane.showMessageDialog(null,"一次只能输入一个字母","提示",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if (a.length()<1)
                    {
                        wrong_times++;
                        myDraw.repaint();
                    }
                    else if (arrayList.contains(a.charAt(0)))
                    {
                        input_answer = input_answer+" "+ a;
                        outputtext.setText(input_answer);
                        arrayList.remove(new Character(a.charAt(0)));
                    }
                    else
                    {
                        wrong_times++;
                        myDraw.repaint();
                    }
                    if (arrayList.isEmpty())
                    {
                        outputtext.setText("YOU WIN!!!"+ " The aswer is "+answer);
                        JOptionPane.showMessageDialog(null,"恭喜，你猜出了词语","congratulation",JOptionPane.INFORMATION_MESSAGE);
                       refreshData();

                    }
                    if (wrong_times>=6)
                    {
                        outputtext.setText("YOU Die!!!"+ " The aswer is "+answer);
                        JOptionPane.showMessageDialog(null,"你死了","congratulation",JOptionPane.INFORMATION_MESSAGE);
                        refreshData();

                    }
                }


            }
        });
        this.setFocusable(true);
        GridBagConstraints constraints =new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth=0;
        constraints.weightx = 1;
        constraints.weighty = 0;
        layout.setConstraints(outputtext,constraints);
        constraints.gridwidth = 0;

        constraints.weightx=1;
        constraints.weighty =1 ;
        layout.setConstraints(myDraw,constraints);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);
        setVisible(true);


    }

    public static void main(String[] args)
    {
        try {
            URL musicUrl = new URL("file:"
                    + System.getProperty("user.dir").toString()
                    + "\\src\\myswing\\background.wav");

//            File d = new File("");

////            URL musicUrl  = new URL(  d.getAbsoluteFile()+"\\background.wav");
//            System.out.println("\n"+d.getCanonicalPath());
            AudioClip ac = Applet.newAudioClip(musicUrl);
            ac.loop();


//        } catch (MalformedURLException e) {
//            e.printStackTrace();
        } catch (Exception e)
        {
            System.out.println("music error");
        }


         game = new HangmanJudge_Game();
    }
    class MyDraw extends JPanel
    {
        @Override
        public void paintComponent(Graphics graphics)
        {
            super.paintComponent(graphics);
            int maxWidth =getWidth();
            int maxHeight = getHeight();

            int[][] head = drawCircle(30,220,260);

            int count = 60;
            int radius = count/2;
            int[] x=new int[count],y=new int[count],y_= new int[count];
            for (int i = 0;i<count;i++)
            {
                x[i] = i+200;
                y[i] = (int)pow(radius*radius - (i-radius)*(i-radius),0.5)+radius+200;
                y_[i] =-(int)pow(radius*radius - (i-radius)*(i-radius),0.5)+radius +200;
            }

            int y_foot = y[radius]+70;
            int scaffold_foot_y = y_foot+200;
            int scaffold_foot_x = x[radius]-100;
            int scaffold_head_y = scaffold_foot_y-400;
            int scaffold_head_x = scaffold_foot_x+80;
            Graphics2D g2 = (Graphics2D)graphics;
            g2.setStroke(new BasicStroke(4.0f));


            g2.drawLine(scaffold_foot_x,scaffold_foot_y,scaffold_foot_x,scaffold_head_y);
            g2.drawLine(scaffold_foot_x-5,scaffold_head_y+30,scaffold_foot_x+30,scaffold_head_y-5);
            g2.drawLine(scaffold_foot_x,scaffold_head_y,x[radius]-10,scaffold_head_y);
            g2.setStroke(new BasicStroke(0.5f));
            if (wrong_times<1) {
                g2.drawLine(x[radius] - 10, scaffold_head_y, x[radius], y[radius]);
                g2.drawPolyline(head[0],head[1],30);
                g2.drawPolyline(head[0],head[2],30);
            }
            else
                g2.drawLine(x[radius]-10,scaffold_head_y,x[radius],y_[radius]);
            g2.drawLine(x[radius]+3,y[radius]-1,x[radius]-3,y[radius]+1);
            g2.drawLine(x[radius]-3,y[radius]+1,x[radius]+3,y[radius]-1);



            if(wrong_times>=1) {
                g2.drawPolyline(x, y, count);
                g2.drawPolyline(x, y_, count);
            }
            if (wrong_times>=2)
            g2.drawLine(x[radius],y[radius],x[radius]-50,y[radius]+50);
            if (wrong_times>=3)
            g2.drawLine(x[radius],y[radius],x[radius]+50,y[radius]+50);
            if (wrong_times>=4)
            g2.drawLine(x[radius],y[radius],x[radius],y_foot);
            if (wrong_times>=5)
            g2.drawLine(x[radius],y_foot,x[radius]-30,y_foot+80);
            if (wrong_times>=6) {
                g2.setColor(Color.red);
                g2.drawPolyline(x, y, count);
                g2.drawPolyline(x, y_, count);
                g2.drawLine(x[radius],y[radius],x[radius]-50,y[radius]+50);
                g2.drawLine(x[radius],y[radius],x[radius]+50,y[radius]+50);
                g2.drawLine(x[radius],y_foot,x[radius]-30,y_foot+80);
                g2.drawLine(x[radius],y[radius],x[radius],y_foot);
                g2.drawLine(x[radius], y_foot, x[radius] + 30, y_foot + 80);
            }

        }
        public int[][] drawCircle(int c,int x,int y)
        {
            int count = c;
            int radius = count/2;
            int answer[][] = new int[3][count];
            for (int i = 0;i<count;i++)
            {
                answer[0][i] = i+x;
                answer[1][i] = (int)pow(radius*radius - (i-radius)*(i-radius),0.5)+radius+y;
                answer[2][i] =-(int)pow(radius*radius - (i-radius)*(i-radius),0.5)+radius+y;



            }
            return answer;
        }
    }
    public void refreshData()
    {
        wrong_times = 0;
        answer = words[random.nextInt(words.length)];
        outputtext.setText("游戏开始");
        input_answer = "";
        game.repaint();
        arrayList.clear();
        for (int i=0;i<answer.length();i++)
        {

            arrayList.add(answer.charAt(i));
        }
    }

}