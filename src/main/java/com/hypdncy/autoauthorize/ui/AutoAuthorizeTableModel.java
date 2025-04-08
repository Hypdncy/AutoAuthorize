package com.hypdncy.autoauthorize.ui;

/**
 * ClassName: CheckBoxInTableExample
 * Package: com.hypdncy.autoauthorize.ui
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/2/25 16:19
 * @Version 1.0
 */

import com.hypdncy.autoauthorize.entity.AutoAuthorizeTableEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class AutoAuthorizeTableModel extends AbstractTableModel {
    private final String[] columnNames = {"#", "类型", "URL", "原始长度", "低权限长度", "未授权长度", "结果"};
    private final List<AutoAuthorizeTableEntity> dataEntries = new ArrayList<>();


    public AutoAuthorizeTableModel() {
    }

    @Override
    public int getRowCount() {
        return dataEntries.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> int.class;
            case 1 -> String.class;
            case 2 -> String.class;
            case 3 -> int.class;
            case 4 -> int.class;
            case 5 -> int.class;
            case 6 -> String.class;
            default -> String.class;

        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AutoAuthorizeTableEntity autoAuthorizeTableEntity = dataEntries.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> rowIndex + 1;
            case 1 -> autoAuthorizeTableEntity.getMethod();
            case 2 -> autoAuthorizeTableEntity.getUri();
            case 3 -> autoAuthorizeTableEntity.getOriginLen();
            case 4 -> autoAuthorizeTableEntity.getOriginLen() - autoAuthorizeTableEntity.getReAuthLen();
            case 5 -> autoAuthorizeTableEntity.getOriginLen() - autoAuthorizeTableEntity.getUnAuthLen();
            case 6 -> autoAuthorizeTableEntity.getResult();
            default -> "";
        };
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 设置是否允许单元格编辑，这里默认不允许编辑
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // 只更新第一列

    }

    public synchronized void addRow(AutoAuthorizeTableEntity AutoAuthorizeTableEntity) {
        int index = dataEntries.size();
        dataEntries.add(AutoAuthorizeTableEntity);
        // 默认刷新行，行从0开始的
        fireTableRowsInserted(index, index);
    }

    public synchronized AutoAuthorizeTableEntity getRow(int rowIndex) {
        return dataEntries.get(rowIndex);
    }

    public synchronized void clearList() {
        dataEntries.clear();
        fireTableDataChanged();
    }

}