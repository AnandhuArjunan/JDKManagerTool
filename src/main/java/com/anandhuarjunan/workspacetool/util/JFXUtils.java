package com.anandhuarjunan.workspacetool.util;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class JFXUtils {


		public static void addNodeIfNotExists(Pane container,Node node) {
			if(!container.getChildren().contains(node)) {
				container.getChildren().add(node);
			}
		}

		private static void disableNodes(boolean disable,Node ...nodes) {
			for(Node node : nodes) {
				node.setDisable(disable);
			}
		}

		public static void disableNodes(Node ...nodes) {
			disableNodes(true, nodes);

		}

		public static void enableNodes(Node ...nodes) {
			disableNodes(false, nodes);
		}


}
