package de.libutzki.xtext.semanticmodeloutline.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class SemanticModelOutlineExecutableExtensionFactory extends
		AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return SemanticModelOutlineActivator.getInstance().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return SemanticModelOutlineActivator.getInstance().getInjector();
	}

}
