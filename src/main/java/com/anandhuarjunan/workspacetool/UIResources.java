package com.anandhuarjunan.workspacetool;

import com.anandhuarjunan.workspacetool.controller.MainController.StatusManager;

public class UIResources {

	 private UIResources() {}
	 private static UIResources suppliers = null;
	 private  StatusManager statusManager = null;

	 public static synchronized UIResources getInstance()
	    {
	        if (suppliers == null)
	        	suppliers = new UIResources();

	        return suppliers;
	    }

	public StatusManager getStatusManager() {
		return statusManager;
	}

	public void setStatusManager(StatusManager statusManager) {
		this.statusManager = statusManager;
	}



}
