package cn.itwanho.eliminate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * 窗口类
 */
public class World extends JPanel {
    public static final int WIDTH = 429;   // 窗口的宽
    public static final int HEIGHT = 570;  // 窗口的高
    public static final int ROWS = 8;      // 8行
    public static final int COLS = 6;      // 6列
    public static final int ELEMENT_SIZE = 60;  // 元素大小（所有元素的宽高60px）
    public static final int OFFSET = 30;    // 偏移量
    public static Element[][] elements = new Element[ROWS][COLS]; // 元素数组（8行6列）


    public World(){

        // 注册鼠标监听器
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                // 遍历所有元素，检查是否鼠标点击在元素范围内
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        Element element = elements[i][j];
                        if (element.contains(mouseX, mouseY)) {
                            element.handleClick();  // 处理鼠标点击事件
                            repaint();  // 重新绘制界面
                            return;  // 返回，只选中一个元素
                        }
                    }
                }
            }
        });
    }



    // 界面显示
    @Override
    public void paintComponent(Graphics g) {
        // 显示背景
        super.paintComponent(g);
        g.drawImage(Images.background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);

        // 遍历并显示元素
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Element element = elements[i][j];
                int x = OFFSET + j * ELEMENT_SIZE;
                int y = OFFSET + i * ELEMENT_SIZE;
                g.drawImage(element.getImage(), x, y, ELEMENT_SIZE, ELEMENT_SIZE, this);
            }
        }

    }





    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();//创建窗口中的那一堆对象
        world.setFocusable(true);// 将控件设置成可获取焦点状态
        frame.add(world);
        //设置用户在此窗体上发起 "close" 时默认执行的操作，EXIT_ON_CLOSE：使用 System exit 方法退出应用程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT+17);
        frame.setLocationRelativeTo(null);//使窗口显示在屏幕中央
        frame.setVisible(true);//自动调用paint()方法

        // 设置标题
        frame.setTitle("开心消消乐");

        // 将图片存入数组中
        ArrayList<Image> image = new ArrayList<>();
        image.add(Images.bear.getImage());
        image.add(Images.bird.getImage());
        image.add(Images.fox.getImage());
        image.add(Images.frog.getImage());


        Random random = new Random();


        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int index = random.nextInt(image.size());
                Image image1 = image.get(index);
                elements[i][j] = new Element(image1, i, j);
            }
        }





    }

}
