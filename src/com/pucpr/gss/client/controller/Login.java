package com.pucpr.gss.client.controller;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.pucpr.gss.client.view.uibinder.LoginView;

public class Login implements EntryPoint {

    public void onModuleLoad() {
        RootPanel.get().add(new LoginView());
    }
}
