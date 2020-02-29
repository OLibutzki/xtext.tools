package de.libutzki.xtext.semanticmodeloutline.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.actions.LinkWithEditorOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.actions.OutlineWithEditorLinker;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Binder;
import com.google.inject.name.Names;

import de.libutzki.xtext.semanticmodeloutline.ui.actions.FilterAttributesOutlineContribution;
import de.libutzki.xtext.semanticmodeloutline.ui.actions.FilterDerivedElementsOutlineContribution;
import de.libutzki.xtext.semanticmodeloutline.ui.actions.FilterUriOutlineContribution;
import de.libutzki.xtext.semanticmodeloutline.ui.actions.SemanticModelOutlineWithEditorLinker;
import de.libutzki.xtext.semanticmodeloutline.ui.content.SemanticModelOutlineLabelProvider;
import de.libutzki.xtext.semanticmodeloutline.ui.content.SemanticModelOutlineTreeProvider;
import de.libutzki.xtext.semanticmodeloutline.ui.page.SemanticModelOutlinePage;
import de.libutzki.xtext.semanticmodeloutline.ui.preferences.LanguageIndependentPreferenceStoreAccessImpl;

public class SemanticModelOutlineUiModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(AbstractUIPlugin.class).toInstance(SemanticModelOutlineActivator.getInstance());
	}
	
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return SemanticModelOutlinePage.class;
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

	public Class<? extends OutlineWithEditorLinker> bindOutlineWithEditorLinker() {
		return SemanticModelOutlineWithEditorLinker.class;
	}
	
	
	public void configureToggleLinkWithEditorOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(IOutlineContribution.LinkWithEditor.class)
				.to(LinkWithEditorOutlineContribution.class);
	}
	
	public void configureFilterAttributesOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterAttributesOutlineContribution")).to(FilterAttributesOutlineContribution.class);
	} 
	
	public void configureFilterUriOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterUriOutlineContribution")).to(FilterUriOutlineContribution.class);
	} 
	
	public void configureFilterDerivedElementsOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterDerivedElementsOutlineContribution")).to(FilterDerivedElementsOutlineContribution.class);
	} 
	
	public Class<? extends org.eclipse.xtext.ui.editor.outline.impl.OutlineNodeElementOpener> bindOutlineNodeElementOpener() {
		return SemanticModelOutlineNodeElementOpener.class;
	}
	
}
