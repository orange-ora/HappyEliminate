package cn.itwanho.eliminate;

import javax.swing.*;

/**
 * 青蛙
 */
public class Frog extends Element {
    public Frog(int x, int y) {
        super(x, y);
    }

    /**
     *
     * @return 返回青蛙图
     */
    @Override
    public ImageIcon getImage() {
        return Images.frog;
    }
}
