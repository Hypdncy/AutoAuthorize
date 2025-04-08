package com.hypdncy.autoauthorize;

/**
 * ClassName: CustomScanChecks
 * Package: com.hypdncy
 * Description:
 *
 * @Author Hypdncy
 * @Create 2025/2/18 20:44
 * @Version 1.0
 */

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.hypdncy.autoauthorize.ui.GUI;
import com.hypdncy.autoauthorize.utils.Config;

public class AutoAuthorize implements BurpExtension {

    public static String Version = "1.0";

    @Override
    public void initialize(MontoyaApi api) {


        api.extension().setName("AutoAuthorize Scanner");
        GUI gui = new GUI(api);
        api.userInterface().registerSuiteTab("AutoAuthorize", gui);
        api.scanner().registerScanCheck(new AutoAuthorizeScanCheck(api, gui));
        api.logging().logToOutput("""
                [*]
                [*] ###################################################
                [*] Author: Hypdncy
                [*] Email:  Hypdncy@outlook.com
                [*] Github: https://github.com/Hypdncy/AutoAuthorize
                [*] 感谢: https://github.com/F6JO/RouteVulScan
                [*] 感谢: https://github.com/Tsojan/TsojanScan
                [*] ###################################################
                [*]
                """
        );
    }
}