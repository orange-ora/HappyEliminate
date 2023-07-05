package cn.itwanho.eliminate;

import javax.swing.*;

/**
 * 狐狸
 */
public class Fox extends Element{
    public Fox(int x, int y) {
        super(x, y);
    }

    /**
     *
     * @return 返回狐狸图
     */
    @Override
    public ImageIcon getImage() {
        return Images.fox;
    }
}
