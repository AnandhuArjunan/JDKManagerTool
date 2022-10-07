package com.anandhuarjunan.workspacetool.views.common;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.inject.Inject;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import com.google.common.collect.Lists;

public class PaginatedFlowPanePresenter implements Initializable {

	private int pageSize = 5;
	private int pageIndex = 0;
	private List<Node> nodes = new ArrayList<>();


    @FXML
    private FlowPane ides;

    @FXML
    private MFXButton next;

    @FXML
    private MFXButton prev;

    @Inject
    private List<Node> initNodes = null;


    @Override
	public void initialize(URL location, ResourceBundle resources) {

    	resetAndAddAll(initNodes);

		next.setOnAction(ev->{
			pageIndex = pageIndex+1;
			refreshButtons();
			ides.getChildren().clear();
			ides.getChildren().addAll(getRecords());
		});
		prev.setOnAction(ev->{
			pageIndex = pageIndex-1;
			refreshButtons();
			ides.getChildren().clear();
			ides.getChildren().addAll(getRecords());
		});
	}


    public void resetAndAddAll(List<Node> node) {
    	clear();
    	nodes.addAll(node);
    	ides.getChildren().addAll(getRecords());
    	refreshButtons();
    }
    private void refreshButtons() {
    	int sizeOfRecords = nodes.size();
    	if(0 < sizeOfRecords) {
    		if(sizeOfRecords/pageSize > 1) {
    			int totalIndex = (int)Math.ceil(sizeOfRecords/(double)pageSize);
    			if((pageIndex+1) == 1) {
    				next.setDisable(false);
        			prev.setDisable(true);
    			}
    			else if((pageIndex+1)<totalIndex) {
    				next.setDisable(false);
        			prev.setDisable(false);
    			}else if((pageIndex+1)==totalIndex) {
    				next.setDisable(true);
        			prev.setDisable(false);
    			}

    		}else {
    			next.setDisable(true);
    			prev.setDisable(true);
    		}
    	}
	}
	public void clear() {
    	nodes.clear();
    	ides.getChildren().clear();
    	pageIndex = 0;
    }

    public List<Node> getRecords() {
    	return Lists.partition(nodes, pageSize).get(pageIndex);
    }




    public List<Node> getAll() {
    	return nodes;
    }

}
