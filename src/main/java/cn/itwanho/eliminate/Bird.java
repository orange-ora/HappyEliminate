package cn.itwanho.eliminate;

import javax.swing.*;

/**
 * 鸟
 */
public class Bird extends Element{

    public Bird(int x, int y) {
        super(x, y);
    }

    /**
     *
     * @return 返回小鸟图
     */
    @Override
    public ImageIcon getImage() {
        return Images.bird;
    }
}
