package cn.itwanho.eliminate;

/**
 * 元素
 */
public class Element {
    private int x;     // x 坐标
    private int y;     // y 坐标
    private boolean selected;    // 是否选中
    private boolean eliminated;  // 是否可消
    private int eliminatedIndex; // 爆炸动画图起始下标（爆炸图有四张，存储在数组中）

    public Element(int x, int y){
        this.x = x;
        this.y = y;


        selected = false;   // 默认没有选中
        eliminated = false;  // 默认不可消除
        eliminatedIndex = 0;  // 默认起始下标从0开始
    }

}
