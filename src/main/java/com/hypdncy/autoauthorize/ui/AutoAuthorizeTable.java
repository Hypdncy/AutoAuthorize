package com.hypdncy.autoauthorize.ui;

/*
  ClassName: TabbedPaneExample
  Package: com.hypdncy.ui
  Description:

  @Author Hypdncy
 * @Create 2025/2/21 17:46
 * @Version 1.0
 */


import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ComponentEvent;
import java.util.Arrays;

public class AutoAuthorizeTable extends JTable {
    private final double[] Proportions = new double[]{1, 2, 5, 3, 3, 3, 2};
    // {"#", "类型", "URL", "原始长度", "低权限长度", "未授权长度", "结果"};

    public AutoAuthorizeTable(AutoAuthorizeTableModel model) {
        setModel(model);
        setProportionalWidth();
        // 设置 TableRowSorter 来支持排序
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(this.getModel());
        setRowSorter(sorter);
    }

    // @Override
    // public void changeSelection(int row, int col, boolean toggle, boolean extend) {}

    private void setProportionalWidth() {

        if (Proportions.length < getColumnCount()) {
            throw new IllegalArgumentException("The length of the proportions array must match the number of columns in the ");
        }
        double total = Arrays.stream(Proportions).sum();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int totalWidth = getWidth(); // 获取表格的总宽度
                TableColumnModel columnModel = getColumnModel();

                for (int i = 0; i < getColumnCount(); i++) {
                    TableColumn column = columnModel.getColumn(i);
                    double proportion = Proportions[i] / total; // 计算当前列的比例
                    int width = Math.max(15, (int) (totalWidth * proportion)); // 确保最小宽度至少为15像素
                    column.setPreferredWidth(width);
                }
            }
        });
        // 初始化时立即调用一次以设置初始宽度
        // revalidate();
        // repaint();

        // 初始化时立即调用一次以设置初始宽度
        // ComponentListener[] listeners = getComponentListeners();
        // for (ComponentListener listener : listeners) {
        //     if (listener instanceof java.awt.event.ComponentAdapter) {
        //         listener.componentResized(new ComponentEvent(table, ComponentEvent.COMPONENT_RESIZED));
        //     }
        // }
    }

    // 清空所有选项卡，并确保数据被清空
    public void clearAllTabsAndData() {
        // 清空表格数据
        // 删除所有选项卡
        removeAll();
    }

    public void flashAll() {

    }
}
