package de.libutzki.xtext.nodemodeloutline.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

public class NodeModelOutlineExecutableExtensionFactory extends
		AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return NodeModelOutlineActivator.getInstance().getBundle();
	}

	@Override
	protected Injector getInjector() {
		return NodeModelOutlineActivator.getInstance().getInjector();
	}

}
