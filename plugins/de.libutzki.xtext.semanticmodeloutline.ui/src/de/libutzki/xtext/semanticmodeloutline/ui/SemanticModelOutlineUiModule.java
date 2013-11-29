package de.libutzki.xtext.semanticmodeloutline.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Binder;

import de.libutzki.xtext.semanticmodeloutline.ui.content.SemanticModelOutlineLabelProvider;
import de.libutzki.xtext.semanticmodeloutline.ui.content.SemanticModelOutlineTreeProvider;
import de.libutzki.xtext.semanticmodeloutline.ui.preferences.LanguageIndependentPreferenceStoreAccessImpl;

public class SemanticModelOutlineUiModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(AbstractUIPlugin.class).toInstance(SemanticModelOutlineActivator.getInstance());
	}
	
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return OutlinePage.class;
	}
	
	public Class<? extends IOutlineTreeProvider> bindIOutlineTreeProvider() {
		return SemanticModelOutlineTreeProvider.class;
	}
	
	public Class<? extends ILabelProvider> bindILabelProvider() {
		return SemanticModelOutlineLabelProvider.class;
	}

	
	public Class<? extends IPreferenceStoreAccess> bindIPreferenceStoreAccess() {
		return LanguageIndependentPreferenceStoreAccessImpl.class;
	}

	public Class<? extends IImageHelper> bindIImageHelper() {
		return PluginImageHelper.class;
	}
	
	public Class<? extends IImageDescriptorHelper> bindIImageDescriptorHelper() {
		return PluginImageHelper.class;
	}

//	public void configureToggleLinkWithEditorOutlineContribution(Binder binder) {
//		binder.bind(IOutlineContribution.class).annotatedWith(IOutlineContribution.LinkWithEditor.class)
//				.to(LinkWithEditorOutlineContribution.class);
//	}
//	
//	public void configureFilterOperationsOutlineContribution(Binder binder) {
//		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterHiddenLeafsContribution")).to(FilterHiddenLeafsContribution.class);
//	} 
	
	
}
