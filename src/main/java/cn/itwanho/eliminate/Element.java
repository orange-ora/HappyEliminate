package cn.itwanho.eliminate;

import javax.swing.*;
import java.awt.*;


/**
 * 元素
 */
public abstract class Element {
    private int x;     // x 坐标
    private int y;     // y 坐标
    private boolean selected;    // 是否选中
    private boolean eliminated;  // 是否可消
    private int eliminatedIndex; // 爆炸动画图起始下标（爆炸图有四张，存储在数组中）

    /**
     * 初始构造方法
     * @param x 横坐标
     * @param y 纵坐标
     */
    public Element(int x, int y){
        this.x = x;
        this.y = y;
        this.selected = false;   // 默认没有选中
        eliminated = false;  // 默认不可消除
        eliminatedIndex = 0;  // 默认起始下标从0开始
    }


    /**
     * 获取图片
     */
    public abstract ImageIcon getImage();

    /**
     * 画元素
     * g 画笔
     */
    public void paintElement(Graphics g){
        if (isSelected()){          // 如果是选中的  用一个 green 矩形框圈住
            g.setColor(Color.GREEN);  // 设置画笔颜色为 green
            g.fillRect(x, y, World.ELEMENT_SIZE, World.ELEMENT_SIZE); // 填充矩形
            this.getImage().paintIcon(null, g, this.x, this.y); // 画图片
        }else if(isEliminated()){   // 如果是可消除的
            if(eliminatedIndex < Images.bombs.length){  // 如果未到最后一张爆破图
                Images.bombs[eliminatedIndex++].paintIcon(null, g, x, y);  // 取出图片并画于面板上
            }

            /*
            *                           index = 0
            *            bombs[0]画     index = 1
            * sleep      bombs[1]画     index = 2
            * sleep      bombs[2]画     index = 3
            * sleep      bombs[3]画     index = 4
            * sleep
            * */


        }else {  // 正常画的
            this.getImage().paintIcon(null, g, this.x, this.y);
        }
    }

    // 设置 x 坐标
    public void setX(int x){
        this.x = x;
    }

    // 获取 x 坐标
    public int getX(){
        return x;
    }

    // 设置 y 坐标
    public void setY(int y){
        this.y = y;
    }

    // 获取 y 坐标
    public int getY(){
        return y;
    }

    /**
     * 检查元素是否被选中
     * @return choose status
     */
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

    /**
     * 检查元素是否可消去
     * @return 消去状态
     */
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

}
