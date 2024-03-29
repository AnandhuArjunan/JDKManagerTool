package com.anandhuarjunan.workspacetool.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.airhacks.afterburner.views.FXMLView;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SimpleViewSwitcher {

	private List<ViewSwitcherBean> list = new ArrayList<>();
	private Pane toggleBar = null;
	private Pane contentPane = null;
	private FXMLView loadingView = null;


	public void addView(ViewSwitcherBean bean) {
		list.add(bean);
	}

	public void show() {
		list.forEach(e->{
			if(Objects.nonNull(e)) {
				
				if(e.isDefault()) {
					e.getActionButton().setSelected(true);
					loadViewAsync(e);
				}
				Objects.requireNonNull(toggleBar,"togglebar is mandatory").getChildren().add(e.getActionButton());
				e.getActionButton().setOnAction(ev->loadViewAsync(e));

			}
		});


	}
	
	public void switchTo(FXMLView fxmlView) {
		Optional<ViewSwitcherBean> listt = list.stream().filter(ew->ew.getView().equals(fxmlView)).findFirst();
		if(listt.isPresent()) {
			loadViewAsync(listt.get());
			
		}
	}
	public void switchTo(int index) {
		if(list.size()>index) {
			ViewSwitcherBean switcherBean = list.get(index);
			switcherBean.getActionButton().setSelected(true);
			loadViewAsync(switcherBean);
		}

	}
	
	public void switchToAndEnableButton(int index) {
		if(list.size()>index) {
			ViewSwitcherBean switcherBean = list.get(index);
			switcherBean.getActionButton().setSelected(true);
			switcherBean.getActionButton().setDisable(false);
			loadViewAsync(switcherBean);
		}

	}
	private void loadViewAsync(ViewSwitcherBean bean) {
		if(Objects.nonNull(loadingView)) {
			Objects.requireNonNull(contentPane,"ContentPane is mandatory").getChildren().setAll(loadingView.getView());
		}
		bean.getView().getViewAsync(contentPane.getChildren()::setAll);
	}

	public void addToggleBar(Pane toggleBar) {
		this.toggleBar = toggleBar;
	}

	public void addContentPane(Pane contentPane) {
		this.contentPane = contentPane;
	}

	public void addLoadingView(FXMLView loadingView) {
		this.loadingView = loadingView;
	}

}
