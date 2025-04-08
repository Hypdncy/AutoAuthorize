package com.hypdncy.autoauthorize;

/**
 * ClassName: MyScanCheck
 * Package: com.hypdncy
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/2/17 11:08
 * @Version 1.0
 */
/*
 * Copyright (c) 2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */


import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import com.hypdncy.autoauthorize.entity.AutoAuthorizeTableEntity;
import com.hypdncy.autoauthorize.ui.GUI;
import com.hypdncy.autoauthorize.utils.Config;

import static burp.api.montoya.scanner.AuditResult.auditResult;
import static burp.api.montoya.scanner.ConsolidationAction.KEEP_BOTH;
import static burp.api.montoya.scanner.ConsolidationAction.KEEP_EXISTING;
import static com.hypdncy.autoauthorize.entity.WhiteHostEntity.checkIn;
import static java.util.Collections.emptyList;

class AutoAuthorizeScanCheck implements ScanCheck {
    private final GUI gui;
    private final MontoyaApi api;

    AutoAuthorizeScanCheck(MontoyaApi montoyaApi, GUI gui) {
        api = montoyaApi;
        this.gui = gui;
    }


    @Override
    public AuditResult activeAudit(HttpRequestResponse httpRequestResponse, AuditInsertionPoint auditInsertionPoint) {
        return null;
    }


    private void runScanOnce(HttpRequestResponse baseRequestResponse) {

        HttpRequest reAuthorizedRequest = baseRequestResponse.request()
                .withUpdatedHeaders(Config.reAuthorizedHeaders.getUpdateHeaders())
                .withRemovedHeaders(Config.reAuthorizedHeaders.getDeleteHeaders())
                .withAddedParameters(Config.reAuthorizedHeaders.getAddedParameters())
                .withAddedParameters(Config.reAuthorizedHeaders.getDeleteParameters());

        HttpRequestResponse reAuthorizedResponse = api.http().sendRequest(reAuthorizedRequest);
        HttpRequest unAuthorizedRequest = baseRequestResponse.request()
                .withUpdatedHeaders(Config.unAuthorizedHeaders.getUpdateHeaders())
                .withRemovedHeaders(Config.unAuthorizedHeaders.getDeleteHeaders())
                .withAddedParameters(Config.unAuthorizedHeaders.getAddedParameters())
                .withAddedParameters(Config.unAuthorizedHeaders.getDeleteParameters());

        HttpRequestResponse unAuthorizedResponse = api.http().sendRequest(unAuthorizedRequest);

        gui.addRow(new AutoAuthorizeTableEntity(baseRequestResponse, reAuthorizedResponse, unAuthorizedResponse));
    }


    private boolean isNeedScan(HttpRequestResponse baseRequestResponse) {
        if (Config.EnabledStatus) {
            String host = baseRequestResponse.httpService().host();
            if (Config.EnabledWhiteStatus) {
                if (checkIn(Config.whiteHostEntity, host)) {
                    Config.whiteHostHashSet.add(host);
                    return true;
                }

            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {

        if (isNeedScan(baseRequestResponse)) {
            runScanOnce(baseRequestResponse);
        }
        return auditResult(emptyList());
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return existingIssue.name().equals(newIssue.name()) ? KEEP_EXISTING : KEEP_BOTH;
    }

}