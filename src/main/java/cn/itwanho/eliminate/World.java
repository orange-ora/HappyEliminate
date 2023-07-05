package cn.itwanho.eliminate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

/**
 * 窗口类
 */
public class World extends JPanel {
    public static final int WIDTH = 429;   // 窗口的宽
    public static final int HEIGHT = 570;  // 窗口的高
    public static int ROWS = 8;      // 8行
    public static int COLS = 6;      // 6列
    public static final int ELEMENT_SIZE = 60;  // 元素大小（所有元素的宽高60px）
    public static final int OFFSET = 30;    // 偏移量
    private final Element[][] elements = new Element[ROWS][COLS]; // 元素数组（8行6列）
    // 元素类型
    public static final int ELEMENT_TYPE_BEAR = 0;  // 熊
    public static final int ELEMENT_TYPE_BIRD = 1;  // 鸟
    public static final int ELEMENT_TYPE_FOX = 2;   // 狐狸
    public static final int ELEMENT_TYPE_FROG = 3;  // 青蛙
    // 元素可消状态
    public static final int ELIMINATE_NON = 0;  // 不可消
    public static final int ELIMINATE_ROW = 1;  // 行可消
    public static final int ELIMINATE_COL = 2;  // 列可消

    private boolean canInteractive = true; // 可交互状态（默认为可交互）
    private int selectedNumber = 0;  // 已经选中的元素个数
    private int firstRow = 0; // 第一个选中元素的ROW
    private int firstCol = 0; // 第一个选中元素的COL
    private int secondRow = 0; // 第二个选中元素的ROW
    private int secondCol = 0; // 第二个选中元素的COL


    /**
     * 界面显示
     * @param g 画笔
     */
    public void paint(Graphics g) {
        // 显示背景
        Images.background.paintIcon(null, g, 0, 0);

        // 遍历 显示每个元素
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // 根据行、列获取元素
                Element e = elements[i][j];
                // 如果元素不是 null
                if (e != null){
                    // 画元素
                    e.paintElement(g);
                }
            }
        }

    }

    /**
     * 创建元素(根据行列计算x,y坐标，然后创建对象)
     * @return 元素对象
     */
    public Element createElement(int row, int col){
        // 横坐标
        int x = OFFSET + col * ELEMENT_SIZE;
        // 纵坐标
        int y = OFFSET + row * ELEMENT_SIZE;
        // 随机数对象
        Random random = new Random();
        int type = random.nextInt(4);  // 0-3之间
        // 根据 type 值的不同生成对象
        switch (type){
            case ELEMENT_TYPE_BEAR:
                return new Bear(x, y);  // 熊
            case ELEMENT_TYPE_BIRD:
                return new Bird(x, y);  // 鸟
            case ELEMENT_TYPE_FOX:
                return new Fox(x, y);  // 狐狸
            default:
                return new Frog(x, y);  // 青蛙
        }
    }


    /**
     * 检查元素是否可消，返回元素的可消状态
     * @param row  行
     * @param col  列
     * @return  status  0：不可消  1：行可消  2：列可消
     */
    public int canEliminate(int row, int col){
        // 获取当前元素
        Element e = elements[row][col];
        // 判断横向是否可消
        if (col >= 2){
            // 获取当前元素的前第一个元素
            Element e1 = elements[row][col-1];
            // 获取当前元素的前第二个元素
            Element e2 = elements[row][col-2];
            // 若元素都不是 null
            if(e != null && e1 != null && e2 != null){
                // 当前元素和前面两个元素都相同 行消
                if (e.getClass().equals(e1.getClass()) && e.getClass().equals(e2.getClass())){
                    // 返回 1 表示行消
                    return ELIMINATE_ROW;
                }
            }
        }

        // 判断纵向是否可消
        if (row >= 2){
            // 获取当前元素上面的第一个元素
            Element e1 = elements[row-1][col];
            // 获取当前元素上面的第二个元素
            Element e2 = elements[row-2][col];
            // 若元素都不是 null
            if(e != null && e1 != null && e2 != null){
                // 当前元素和上面的两个元素都相同 列消
                if (e.getClass().equals(e1.getClass()) && e.getClass().equals(e2.getClass())){
                    // 返回 1 表示列可消
                    return ELIMINATE_COL;
                }
            }

        }

        // 返回 0 表示不可消
        return ELIMINATE_NON;
    }

    /**
     * 填充所有元素
     */
    public void fillAllElements(){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                do{
                    // 创建元素
                    Element e = createElement(row, col);
                    // 将元素存入数组
                    elements[row][col] = e;
                }while (canEliminate(row, col) != ELIMINATE_NON); // 如果可消，则重新生成
            }
        }
    }

    /**
     * 判断两个元素是否能交换（相邻可交换，否则不能交换）
     */
    public boolean isAdjacent(){
        // 若 行相邻，列相等， 列相邻，行相等  相差 1
        // 相邻
        // 不相邻
        return (Math.abs(firstRow - secondRow) == 1 && firstCol == secondCol) || Math.abs(firstCol - secondCol) == 1 && firstRow == secondRow;

    }

    /**
     * 移动两个元素（动画移动）
     */
    public void moveElement() {
        // 如果行号相同，则左右移动
        if (firstRow == secondRow) {
            // 选中的第一个元素的横坐标
            int firstX = OFFSET + firstCol * ELEMENT_SIZE;
            // 选中的第二个元素的横坐标
            int secondX = OFFSET + secondCol * ELEMENT_SIZE;
            // 步长（控制左右），值越大速度越快
            int step = firstX < secondX ? 4 : -4;
            // 走15次（15 * 4 = 60）
            for (int i = 0; i < 15; i++) {
                try {
                    // 每次循环休息 10ms
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 第一个 x 加
                firstX += step;
                // 第二个 x 减
                secondX -= step;
                // 修改元素坐标
                elements[firstRow][firstCol].setX(firstX);
                elements[secondRow][secondCol].setX(secondX);
                // 重画
                repaint();
            }

        }

        // 如果列号相同，则上下移动
        if (firstCol == secondCol) {
            // 选中的第一个元素的纵坐标
            int firstY = OFFSET + firstRow * ELEMENT_SIZE;
            // 选中的第二个元素的横坐标
            int secondY = OFFSET + secondRow * ELEMENT_SIZE;
            // 步长（控制左右），值越大速度越快
            int step = firstY < secondY ? 4 : -4;
            // 走15次（15 * 4 = 60）
            for (int i = 0; i < 15; i++) {
                try {
                    // 每次循环休息 10ms
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 第一个 y 加
                firstY += step;
                // 第二个 y 减
                secondY -= step;
                // 修改元素坐标
                elements[firstRow][firstCol].setY(firstY);
                elements[secondRow][secondCol].setY(secondY);
                // 重画
                repaint();
            }
        }
    }

    /**
     * 交换两个元素
     */
    public void exchangeElement(){
        // 获取第一个元素
        Element e1 = elements[firstRow][firstCol];
        // 获取第二个元素
        Element e2 = elements[secondRow][secondCol];
        // 交换元素
        elements[firstRow][firstCol] = e2;
        elements[secondRow][secondCol] = e1;
    }

    /**
     * 扫描并删除元素
     * @return 可消状态
     */
    public boolean eliminateElement(){
        // 是否有元素被消除了  默认不可消
        boolean haveEliminate = false;
        // 遍历所有元素
        for(int row = ROWS - 1; row >= 0; row--){
            for (int col = COLS - 1; col >= 0; col--){
                // 获取当前元素
                Element e = elements[row][col];
                // 若元素为 null,则跳过
                if (e == null){
                    continue;
                }

                // 1.查找一行中连续的个数，查找一列中连续的个数
                // 行不变，列相邻--当前元素相邻的行元素的连续重复个数
                int colRepeat = 0;
                // 倒着遍历当前元素前面的所有列
                for (int pc = col - 1; pc >= 0; pc--){
                    // 如果前面的元素为 null，则直接退出
                    if (elements[row][pc] == null){
                        break;
                    }

                    // 若遍历元素与当前元素的类型相同，则重复个数增1，否则 break 退出
                    if (elements[row][pc].getClass() == e.getClass()){
                        colRepeat++;
                    }else {
                        // 只要有一个不同的，则无需再比较
                        break;
                    }
                }

                // 列不变，行相邻--当前元素相邻的列元素的连续重复个数
                int rowRepeat = 0;
                // 倒着遍历当前元素上面的所有行
                for (int pr = row - 1; pr >= 0; pr--){
                    // 如果前面的元素为 null，则直接退出
                    if (elements[pr][col] == null){
                        break;
                    }

                    // 若遍历元素与当前元素的类型相同，则重复个数增1，否则 break 退出
                    if (elements[pr][col].getClass() == e.getClass()){
                        rowRepeat++;
                    }else {
                        // 只要有一个不同的，则无需再比较
                        break;
                    }
                }

                // 2.将连续（可消除）的元素设置为可消除状态
                // 行不变，列相邻 可消
                if (colRepeat >= 2){
                    // 设置当前元素可消
                    elements[row][col].setEliminated(true);
                    for (int i = 1; i <= colRepeat; i++) {
                        // 行不变，列前元素设置为可消
                        elements[row][col - i].setEliminated(true);
                    }
                }
                // 列不变，行相邻  可消
                if (rowRepeat >= 2){
                    // 设置当前元素可消
                    elements[row][col].setEliminated(true);
                    for (int i = 1; i <= rowRepeat; i++) {
                        // 列不变，行上元素设置为可消
                        elements[row - i][col].setEliminated(true);
                    }
                }
                // 3.将可消除状态的元素绘制爆炸动画
                // 如果有可消除元素
                if (colRepeat >= 2 || rowRepeat >= 2){
                    // 遍历所有爆破图
                    for (int i = 0; i < Images.bombs.length; i++) {
                        // 重画，依次显示4张爆破图
                        repaint();
                        // 每执行一次循环休眠 50 ms
                        try {
                            Thread.sleep(50);
                        }catch (InterruptedException ex){
                            ex.printStackTrace();
                        }
                    }
                }
                // 4.将可消状态元素设置为 null，以等待其它元素的下落
                if (colRepeat >= 2){
                    // 将当前元素设置为 null
                    elements[row][col] = null;
                    for (int i = 1; i <= colRepeat; i++) {
                        // 行不变，列前元素设为 null
                        elements[row][col - i] = null;
                    }
                    // 有可消元素被消除了
                    haveEliminate = true;
                }

                // 列不变，行相邻
                if (rowRepeat >= 2){
                    // 将当前元素设置为 null
                    elements[row][col] = null;
                    for (int j = 1; j <= rowRepeat; j++) {
                        // 列不变，行上元素设为 null
                        elements[row - j][col] = null;
                    }
                    // 有可消元素被消除了
                    haveEliminate = true;
                }

            }
        }
        return haveEliminate;
    }

    /**
     * 下落元素
     */
    public void dropElement(){
        // 遍历所有行
        for (int row = ROWS - 1; row >= 0; row--){
            // 只要有 null 元素，将他上面的元素下落，若没有 null 元素，则结束当前行操作
            while (true){
                // 当前行为 null 的列
                int[] nullCols = {};
                // 查找 null 列
                for (int col = COLS - 1; col >= 0; col--){
                    // 获取当前元素
                    Element e = elements[row][col];
                    // 若当前元素为 null
                    if (e == null){
                        // 扩容
                        nullCols = Arrays.copyOf(nullCols, nullCols.length + 1);
                        // 将 null 元素列号填充到最后一个元素上
                        nullCols[nullCols.length - 1] = col;
                    }
                }
                // 当前行有为 null 的列
                if (nullCols.length > 0){
                    // 下落元素，并生成新元素
                    // 以 4 为步长，移动 15 次
                    for (int count = 0; count < 15; count++) {
                        // 所有 null 元素都要落
                        for (int nullNum : nullCols) {
                            // 获取 null 列号
                            // 从当前行的上一行开始下落元素
                            for (int dr = row - 1; dr >= 0; dr--) {
                                // 获取上方的元素
                                Element e = elements[dr][nullNum];
                                // 如果元素不是 null
                                if (e != null) {
                                    // 设置元素的坐标为 y + 步长4  循环15次
                                    e.setY(e.getY() + 4);
                                }
                            }
                        }
                        try {
                            // 每次循环休息 10ms
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 重画
                        repaint();
                    }

                    // 让上方元素下落
                    // 遍历所有 null 列号
                    for (int nullNum : nullCols) {
                        // 获取当前 null 列号
                        // 从当前行开始，一直到最上面（不包括第 0 行）
                        for (int nr = row; nr > 0; nr--) {
                            // 将当前元素赋值为它上一个元素
                            elements[nr][nullNum] = elements[nr - 1][nullNum];
                        }
                        // 当前行最上面生成一个新元素进行填充
                        elements[0][nullNum] = createElement(0, nullNum);
                    }
                }else {
                    // 当前行没有 null 列，则处理上一行
                    break;
                }
            }
        }
    }

    /**
     * 程序启动执行
     */
    public void start(){
        // 填充所有元素
        fillAllElements();

        // 设计鼠标侦听器
        MouseAdapter adapter = new MouseAdapter() {
            /**
             * 重写鼠标点击事件
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                // 如果不可交互，不响应鼠标事件
                if (!canInteractive){
                    return;
                }

                // 若选中元素，则修改状态无法交互
                canInteractive = false;

                // 获取选中元素的 ROW
                int row = (e.getY() - OFFSET)/ELEMENT_SIZE;
                // 获取选中元素的 COL
                int col = (e.getX() - OFFSET)/ELEMENT_SIZE;

                // 选中元素个数 +1
                selectedNumber++;

                if (selectedNumber == 1){  // 第一次选中
                    // 记录第一个元素的 ROW
                    firstRow = row;
                    // 记录第一个元素的 COL
                    firstCol = col;

                    // 设置选中状态为 true
                    elements[firstRow][firstCol].setSelected(true);
                    // 可交互
                    canInteractive = true;
                }else if(selectedNumber == 2){ // 第二次选中
                    // 记录第二个元素的 ROW
                     secondRow = row;
                    // 记录第二个元素的 COL
                    secondCol = col;
                    // 设置选中状态为 true
                    elements[secondRow][secondCol].setSelected(true);
                    if (isAdjacent()){ // 若相邻
                        // 自动执行
                        new Thread(() -> {
                            // 取消选中状态
                            elements[firstRow][firstCol].setSelected(false);
                            // 取消选中状态
                            elements[secondRow][secondCol].setSelected(false);
                            //移动两个元素(动画移动)
                            moveElement();
                            // 交换两个元素
                            exchangeElement();
                            // 若有可消元素被消除了
                            if(eliminateElement()){
                                do{
                                    // 下落新元素
                                    dropElement();
                                    try {
                                        // 每次循环休息 10ms
                                        Thread.sleep(10);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                }while (eliminateElement());// 扫描若有可消元素则继续消并下落

                            }else {
                                //移动两个元素(动画移动)
                                moveElement();
                                // 交换两个元素
                                exchangeElement();
                            }
                            // 可交互
                            canInteractive = true;
                        }).start(); // 启动线程
                    }else {  // 若不相邻
                        // 取消选中状态
                        elements[firstRow][firstCol].setSelected(false);
                        // 取消选中状态
                        elements[secondRow][secondCol].setSelected(false);
                        // 可交互
                        canInteractive = true;
                    }
                    // 选中个数归 0
                    selectedNumber = 0;
                }
                // 重画
                repaint();
            }
        };

        // 添加鼠标侦听
        this.addMouseListener(adapter);
    }

    /**
     * 主函数
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("开心消消乐");
        World world = new World();//创建窗口中的那一堆对象
        world.setFocusable(true);// 将控件设置成可获取焦点状态
        frame.add(world);
        //设置用户在此窗体上发起 "close" 时默认执行的操作，EXIT_ON_CLOSE：使用 System exit 方法退出应用程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH,HEIGHT+17);
        frame.setLocationRelativeTo(null);//使窗口显示在屏幕中央
        frame.setVisible(true);//自动调用paint()方法
        world.start(); // 启动程序
    }

}
