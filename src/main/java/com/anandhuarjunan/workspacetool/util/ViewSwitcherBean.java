package com.anandhuarjunan.workspacetool.util;

import java.net.URL;

import com.airhacks.afterburner.views.FXMLView;

import io.github.palexdev.materialfx.utils.others.loader.MFXLoaderBean.Builder;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class ViewSwitcherBean {

	private boolean isDefault = false;
	private FXMLView fxmlView = null;
	private ToggleButton actionButton = null;

	public static Builder builder() {
		return new Builder();
	}
	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public FXMLView getView() {
		return fxmlView;
	}


	public void setView(FXMLView fxmlView) {
		this.fxmlView = fxmlView;
	}


	public ToggleButton getActionButton() {
		return actionButton;
	}


	public void setActionButton(ToggleButton actionButton) {
		this.actionButton = actionButton;
	}

	public static class Builder {
		private final ViewSwitcherBean bean;

		public Builder() {
			this.bean = new ViewSwitcherBean();
		}

		public Builder ofDefault(boolean isDefault) {
			bean.setDefault(isDefault);
			return this;
		}

		public Builder ofView(FXMLView node) {
			bean.setView(node);
			return this;
		}

		public Builder ofActionButton(ToggleButton actionButton) {
			bean.setActionButton(actionButton);
			return this;
		}

		public ViewSwitcherBean get() {
			return bean;
		}
	}


}
