package com.anandhuarjunan.workspacetool.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
				if(Objects.nonNull(loadingView)) {
					Objects.requireNonNull(contentPane,"ContentPane is mandatory").getChildren().setAll(loadingView.getView());
				}
				if(e.isDefault()) {
					e.getActionButton().setSelected(true);
					e.getView().getViewAsync(contentPane.getChildren()::setAll);
				}
				Objects.requireNonNull(toggleBar,"togglebar is mandatory").getChildren().add(e.getActionButton());
				e.getActionButton().setOnAction(ev->{
						e.getView().getViewAsync(contentPane.getChildren()::setAll);

						//contentPane.getChildren().setAll(e.getView());
					});

			}
		});


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
