package com.hypdncy.autoauthorize.ui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import com.hypdncy.autoauthorize.entity.AutoAuthorizeTableEntity;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

/**
 * ClassName: AutoAuthorizeHttpViewer
 * Package: com.hypdncy.authorize.ui
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/3/31 12:39
 * @Version 1.0
 */
public class AutoAuthorizeHttpViewer extends JTabbedPane {
    public MontoyaApi api;
    private final String[] titles = {"原始请求", "低权限请求", "未授权请求"};

    private final java.util.List<HttpRequestEditor> HttpRequestEditors = new ArrayList<>();
    private final java.util.List<HttpResponseEditor> HttpResponseEditors = new ArrayList<>();

    public AutoAuthorizeHttpViewer(MontoyaApi montoyaApi) {
        api = montoyaApi;
        UserInterface userInterface = api.userInterface();

        for (String title : titles) {
            JSplitPane leftSouthPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            HttpRequestEditor requestEditor = userInterface.createHttpRequestEditor();
            HttpResponseEditor responseEditor = userInterface.createHttpResponseEditor();

            HttpRequestEditors.add(requestEditor);
            HttpResponseEditors.add(responseEditor);

            leftSouthPanel.setLeftComponent(requestEditor.uiComponent());
            leftSouthPanel.setRightComponent(responseEditor.uiComponent());
            this.add(title, leftSouthPanel);

            leftSouthPanel.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    leftSouthPanel.setDividerLocation(0.5);
                }
            });
        }
    }


    public void setHttpRequestResponse(AutoAuthorizeTableEntity autoAuthorizeTableEntity) {
        HttpRequestEditors.getFirst().setRequest(autoAuthorizeTableEntity.getOriginRequestResponse().request());
        HttpResponseEditors.getFirst().setResponse(autoAuthorizeTableEntity.getOriginRequestResponse().response());

        HttpRequestEditors.get(1).setRequest(autoAuthorizeTableEntity.getReAuthRequestResponse().request());
        HttpResponseEditors.get(1).setResponse(autoAuthorizeTableEntity.getReAuthRequestResponse().response());


        HttpRequestEditors.getLast().setRequest(autoAuthorizeTableEntity.getUnAuthRequestResponse().request());
        HttpResponseEditors.getLast().setResponse(autoAuthorizeTableEntity.getUnAuthRequestResponse().response());

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
