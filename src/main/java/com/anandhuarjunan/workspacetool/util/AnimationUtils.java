package com.anandhuarjunan.workspacetool.util;

import java.util.function.Supplier;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtils {


	 public static void addAnimating(final Action action,final Supplier<Animation> animationCreator) {
		    action.action();
		    animationCreator.get().play();
		  }

		  public static void removeAnimating(final Action action,final Supplier<Animation> animationCreator) {
		      final Animation animation = animationCreator.get();
		      animation.setOnFinished(finishHim -> {
		    	  action.action();
		      });
		      animation.play();


		  }

		  public static Supplier<Animation> fadeInAnimationSupplier(Node affected){
			  	final FadeTransition transition = new FadeTransition(Duration.millis(250), affected);
			    transition.setFromValue(0);
			    transition.setToValue(1);
			    transition.setInterpolator(Interpolator.EASE_IN);
			    return ()->transition;

		  }


		  public static Supplier<Animation> fadeOutAnimationSupplier(Node affected){
			  final FadeTransition transition = new FadeTransition(Duration.millis(250), affected);
	             transition.setFromValue(affected.getOpacity());
	             transition.setToValue(0);
	             transition.setInterpolator(Interpolator.EASE_BOTH);
	             return ()->transition;
		  }
}
