package cn.itwanho.eliminate;

import java.awt.*;

import static cn.itwanho.eliminate.World.ELEMENT_SIZE;

/**
 * 元素
 */
public class Element {
    private final Image elementImage;
    private final int x;     // x 坐标
    private final int y;     // y 坐标
    private boolean selected;    // 是否选中
    private boolean eliminated;  // 是否可消
    private int eliminatedIndex; // 爆炸动画图起始下标（爆炸图有四张，存储在数组中）
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Element(Image elementImage, int x, int y){
        this.elementImage = elementImage;
        this.x = x;
        this.y = y;


        selected = false;   // 默认没有选中
        eliminated = false;  // 默认不可消除
        eliminatedIndex = 0;  // 默认起始下标从0开始
    }

    // 获取图片
    public Image getImage(){
        return elementImage;
    }

    // 获取 x 坐标
    public int getX(){
        return x;
    }

    // 获取 y 坐标
    public int getY(){
        return y;
    }

    // 检查元素是否被选中
    public boolean isSelected(){
        return selected;
    }

    // 设置元素是否被选中
    public void setSelected(Boolean selected){
        this.selected = selected;
    }

    // 设置元素是否可消去
    public void setEliminated(Boolean eliminated){
        this.eliminated = eliminated;
    }

    // 检查元素是否可消去
    public boolean isEliminated(){
        return eliminated;
    }

    // 设置爆炸动画的起始下标
    public void setEliminatedIndex(int eliminatedIndex){
        this.eliminatedIndex = eliminatedIndex;
    }

    // 获取爆炸动画的起始下标
    public int getEliminatedIndex(){
        return eliminatedIndex;
    }

    // 检查坐标点(x, y)是否在元素的范围内
    public boolean contains(int x, int y) {
        return x >= this.x && x < this.x + ELEMENT_SIZE &&
                y >= this.y && y < this.y + ELEMENT_SIZE;
    }

    // 处理鼠标点击事件
    public void handleClick() {
        selected = !selected;  // 切换选中状态
        if (selected) {
            color = Color.RED;  // 设置新的背景颜色
            this.setColor(color);
        }
    }




}
