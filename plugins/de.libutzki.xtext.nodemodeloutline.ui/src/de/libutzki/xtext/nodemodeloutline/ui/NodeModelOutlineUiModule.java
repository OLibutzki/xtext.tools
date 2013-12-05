package de.libutzki.xtext.nodemodeloutline.ui;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.actions.IOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.actions.LinkWithEditorOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.actions.OutlineWithEditorLinker;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Binder;
import com.google.inject.name.Names;

import de.libutzki.xtext.nodemodeloutline.ui.actions.DummyURIEditorOpener;
import de.libutzki.xtext.nodemodeloutline.ui.actions.FilterHiddenLeafsContribution;
import de.libutzki.xtext.nodemodeloutline.ui.actions.NodeModelOutlineWithEditorLinker;
import de.libutzki.xtext.nodemodeloutline.ui.content.NodeModelOutlineLabelProvider;
import de.libutzki.xtext.nodemodeloutline.ui.content.NodeModelOutlineTreeProvider;
import de.libutzki.xtext.nodemodeloutline.ui.page.NodeModelOutlinePage;
import de.libutzki.xtext.nodemodeloutline.ui.preferences.LanguageIndependentPreferenceStoreAccessImpl;

public class NodeModelOutlineUiModule extends AbstractGenericModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(AbstractUIPlugin.class).toInstance(NodeModelOutlineActivator.getInstance());
	}
	
	public Class<? extends IContentOutlinePage> bindIContentOutlinePage() {
		return NodeModelOutlinePage.class;
	}
	
	public Class<? extends IOutlineTreeProvider> bindIOutlineTreeProvider() {
		return NodeModelOutlineTreeProvider.class;
	}
	
	public Class<? extends ILabelProvider> bindILabelProvider() {
		return NodeModelOutlineLabelProvider.class;
	}

	
	public Class<? extends IPreferenceStoreAccess> bindIPreferenceStoreAccess() {
		return LanguageIndependentPreferenceStoreAccessImpl.class;
	}
	
	public Class<? extends IURIEditorOpener> bindIURIEditorOpener() {
		return DummyURIEditorOpener.class;
	}
	
	
	public Class<? extends OutlineWithEditorLinker> bindOutlineWithEditorLinker() {
		return NodeModelOutlineWithEditorLinker.class;
	}
	
	public Class<? extends IImageHelper> bindIImageHelper() {
		return PluginImageHelper.class;
	}
	
	public Class<? extends IImageDescriptorHelper> bindIImageDescriptorHelper() {
		return PluginImageHelper.class;
	}

	public void configureToggleLinkWithEditorOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(IOutlineContribution.LinkWithEditor.class)
				.to(LinkWithEditorOutlineContribution.class);
	}
	
	public void configureFilterOperationsOutlineContribution(Binder binder) {
		binder.bind(IOutlineContribution.class).annotatedWith(Names.named("FilterHiddenLeafsContribution")).to(FilterHiddenLeafsContribution.class);
	} 
	
	
}
