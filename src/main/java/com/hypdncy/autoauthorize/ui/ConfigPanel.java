package com.hypdncy.autoauthorize.ui;

import com.hypdncy.autoauthorize.AutoAuthorize;
import com.hypdncy.autoauthorize.utils.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: ConfigPanel
 * Package: com.hypdncy.autoauthorize.ui
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/3/31 13:04
 * @Version 1.0
 */
public class ConfigPanel extends JPanel {
    private final String ControlButton = "ControlButton";
    public final JButton clearJButton = new JButton("清空列表");

    public ConfigPanel() {

        JLabel jLabel0 = new JLabel("插件作者：Hypdncy");
        JLabel jLabel1 = new JLabel("插件地址：https://github.com/");
        JLabel jLabel2 = new JLabel("插件版本：xia Yue V" + AutoAuthorize.Version);
        JLabel jLabel3 = new JLabel("感谢名单：Moonlit、算命縖子");
        JLabel jLabel4 = new JLabel("插件名称：AutoAuthorize");

        clearJButton.setName(ControlButton); // 设置名称作为标记
        JCheckBox enableCheckBox = new JCheckBox("启动插件");
        enableCheckBox.setName(ControlButton); // 设置名称作为标记
        JTextField whiteTextField = new JTextField();
        JCheckBox whiteCheckBox = new JCheckBox("启动白名单:填写白名单域名,如果需要多个域名加白请用,隔开");
        // 创建下面的
        JLabel reAuthJlabel = new JLabel("越权：填写低权限认证信息,将会替换或新增");
        JLabel unAuthJlabel = new JLabel("越权：填写未授权认证信息,将会替换或新增");

        // RawEditor reAuthRawEditor = userInterface.createRawEditor();
        // RawEditor unAuthRawEditor = userInterface.createRawEditor();
        JTextArea reAddJTextArea = new JTextArea("Cookie: JSESSIONID=test;UUID=1; isAdmin=0");
        JTextArea unAddJTextArea = new JTextArea("Cookie: JSESSIONID=test;UUID=1");

        JLabel reDelJlabel = new JLabel("删除请求头：Cookie");
        JLabel unDelJlabel = new JLabel("删除请求头");
        JTextArea reDelHeaderJTextArea = new JTextArea();
        JTextArea unDelHeaderJTextArea = new JTextArea("Cookie");

        JLabel reParamsLabel = new JLabel("参数：isAdmin=1&isSystem=1");
        JLabel unParamsLabel = new JLabel("参数");
        JTextArea reParamsJTextArea = new JTextArea();
        JTextArea unParamsJTextArea = new JTextArea("");

        List<? extends JComponent> jComponents = Arrays.asList(
                jLabel0, jLabel1, jLabel2, jLabel3, jLabel4,
                clearJButton, enableCheckBox, whiteTextField, whiteCheckBox,
                reAuthJlabel, reAddJTextArea, reDelJlabel, reDelHeaderJTextArea, reParamsLabel, reParamsJTextArea,
                unAuthJlabel, unAuthJlabel, unAddJTextArea, unDelJlabel, unDelHeaderJTextArea, unParamsLabel, unParamsJTextArea
        );
        // setLayout(new GridBagLayout());
        setJPanelCols(jComponents);
        enableCheckBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Config.EnabledStatus = true;
                disableAllComponents(ConfigPanel.this, false);
                Config.reAuthorizedHeaders.setData(reAddJTextArea.getText(), reDelHeaderJTextArea.getText());
                Config.unAuthorizedHeaders.setData(unAddJTextArea.getText(), unDelHeaderJTextArea.getText());
                Config.whiteHostEntity.setData(whiteTextField.getText());
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                Config.EnabledStatus = false;
                disableAllComponents(ConfigPanel.this, true);
            }
        });
    }

    private void getConfig() {

    }

    public void disableAllComponents(Container container, boolean status) {
        for (Component component : container.getComponents()) {

            if (!Objects.equals(component.getName(), ControlButton)) {
                component.setEnabled(status); // 禁用当前组件
            }
            // 如果组件本身是一个容器，则递归禁用其内部的所有组件
            if (component instanceof Container) {
                disableAllComponents((Container) component, status);
            }
        }
    }

    public void setJPanelCols(List<? extends JComponent> jComponents) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        // 设置外边距
        gbc.insets = new Insets(1, 1, 1, 1);
        // 填充整个单元格
        gbc.fill = GridBagConstraints.BOTH;
        // gbc.anchor = GridBagConstraints.WEST; // 让组件从左上角开始排列
        // gbc.gridx = 0; // 起始列
        // gbc.gridy jadx= 0; // 起始行


        gbc.gridx = 0;  // 所有组件都在第 0 列
        gbc.fill = GridBagConstraints.HORIZONTAL; // 默认让组件填满水平空间
        gbc.weightx = 1; // 让组件水平扩展
        int row = 0;
        for (JComponent jComponent : jComponents) {
            System.out.println(jComponent.getClass().getSimpleName());
            if (jComponent.getClass().getSimpleName().equals("JTextArea")) {
                row += 3;
                gbc.gridheight = 1; // 占据 3 行的高度
                gbc.fill = GridBagConstraints.BOTH; // 让文本区域同时填满水平和垂直空间
                gbc.weighty = 1; // 让文本区域垂直扩展
            } else {
                gbc.gridheight = 1; // 其他组件只占据 1 行
                gbc.fill = GridBagConstraints.HORIZONTAL; // 只填满水平空间
                gbc.weighty = 0; // 不需要垂直扩展
                row += 1;
            }
            // gbc.gridy = row; // 计算行索引（自动换行）
            gbc.gridy = row; // 计算行索引（自动换行）

            this.add(jComponent, gbc);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Full Screen Panel Example");
        // 设置关闭行为
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置全屏
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false); // 根据需要调整
        ConfigPanel gui = new ConfigPanel();


        // 示例：添加一个按钮，让它占据整个窗口

        frame.add(gui);
        // frame.add(new JButton("ssss"));
        frame.setVisible(true);
    }
}
