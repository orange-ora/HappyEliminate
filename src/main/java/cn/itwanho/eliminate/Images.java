package cn.itwanho.eliminate;

import javax.swing.*;

/**
 * 图片类
 */
public class Images {
    public static ImageIcon background;   // 背景图
    public static ImageIcon bear;         // 小熊图
    public static ImageIcon bird;         // 小鸟图
    public static ImageIcon fox;          // 狐狸图
    public static ImageIcon frog;         // 青蛙
    public static ImageIcon[] bombs;      // 爆炸图数组

    // 加载图片
    static {
        background = new ImageIcon("img/background.png");
        bear = new ImageIcon("img/bear.png");
        bird = new ImageIcon("img/bird.png");
        fox = new ImageIcon("img/fox.png");
        frog = new ImageIcon("img/frog.png");
        bombs = new ImageIcon[4];
        for (int i = 0; i < bombs.length; i++) {
            bombs[i] = new ImageIcon("img/bom" + (i + 1) + ".png");
        }
    }

}
