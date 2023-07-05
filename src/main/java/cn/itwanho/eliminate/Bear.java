package cn.itwanho.eliminate;

import javax.swing.*;

/**
 * 熊
 */
public class Bear extends Element{
    public Bear(int x, int y) {
        super(x, y);
    }

    /**
     * 重写getImage()获取图片
     * @return 返回小熊图片
     */
    @Override
    public ImageIcon getImage() {
        return Images.bear;
    }
}
