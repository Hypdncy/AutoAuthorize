package com.hypdncy.autoauthorize.ui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpHeader;
import com.hypdncy.autoauthorize.entity.AutoAuthorizeTableEntity;
import com.hypdncy.autoauthorize.utils.Config;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * ClassName: GUI
 * Package:    com.hypdncy.autoauthorize.ui
 * Description:
 *
 * @Author Hypdncy
 * @Create 2024/10/10 11:51
 * @Version 1.0
 */
public class GUI extends JPanel {

    public MontoyaApi api;
    AutoAuthorizeTableModel autoAuthorizeTableModel;

    public void addRow(AutoAuthorizeTableEntity entity) {
        autoAuthorizeTableModel.addRow(entity);
    }

    public GUI(MontoyaApi api) {
        this.api = api;
        initGUI();
    }


    public GUI() {
        initGUI();
    }

    // 保存选项卡标题
    public void initGUI() {
        autoAuthorizeTableModel = new AutoAuthorizeTableModel();

        AutoAuthorizeTable autoAuthorizeTable = new AutoAuthorizeTable(autoAuthorizeTableModel);
        AutoAuthorizeHttpViewer autoAuthorizeHttpViewer = new AutoAuthorizeHttpViewer(api);


        autoAuthorizeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        autoAuthorizeTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (!e.getValueIsAdjusting()) {
                for (HttpHeader header : Config.reAuthorizedHeaders.getUpdateHeaders()) {
                    api.logging().logToError(header.toString());

                }
                autoAuthorizeHttpViewer.setHttpRequestResponse(autoAuthorizeTableModel.getRow(autoAuthorizeTable.getSelectedRow()));
            }
        });


        // 整合左面
        JSplitPane leftPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        leftPanel.setLeftComponent(new JScrollPane(autoAuthorizeTable));
        leftPanel.setRightComponent(autoAuthorizeHttpViewer);


        ConfigPanel configPanel = new ConfigPanel();
        configPanel.clearJButton.addActionListener(e -> autoAuthorizeTableModel.clearList());

        JSplitPane globalPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        globalPanel.setLeftComponent(leftPanel);
        globalPanel.setRightComponent(configPanel);

        leftPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                leftPanel.setDividerLocation(0.5);
            }
        });
        globalPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                globalPanel.setDividerLocation(0.7);
            }
        });
        // setLayout();
        this.setLayout(new BorderLayout());
        this.add(globalPanel, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Full Screen Panel Example");
        // 设置关闭行为
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置全屏
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false); // 根据需要调整
        GUI gui = new GUI();
        gui.initGUI();

        // 示例：添加一个按钮，让它占据整个窗口

        frame.add(gui);
        // frame.add(new JButton("ssss"));
        frame.setVisible(true);
    }
}
