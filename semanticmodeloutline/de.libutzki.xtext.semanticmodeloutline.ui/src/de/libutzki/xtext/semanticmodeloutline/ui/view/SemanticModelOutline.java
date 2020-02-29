package de.libutzki.xtext.semanticmodeloutline.ui.view;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.ISourceViewerAware;
import org.eclipse.xtext.ui.editor.IXtextEditorAware;
import org.eclipse.xtext.ui.editor.XtextEditor;

import com.google.inject.Inject;
import com.google.inject.Provider;


public class SemanticModelOutline extends PageBookView {

	@Inject
	private Provider<IContentOutlinePage> outlinePageProvider;
	
	@Override
	protected IPage createDefaultPage(PageBook book) {
        MessagePage page = new MessagePage();
        initPage(page);
        page.createControl(book);
        page.setMessage("A semantic model outline is not available.");
        return page;
	}

	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		IContentOutlinePage nodeModelOutlinePage = createOutlinePage(part);
		if (nodeModelOutlinePage instanceof IPageBookViewPage) {
			initPage((IPageBookViewPage)nodeModelOutlinePage);		
		}
		nodeModelOutlinePage.createControl(getPageBook());
		return new PageRec(part, nodeModelOutlinePage);

	}
	
	private IContentOutlinePage createOutlinePage(IWorkbenchPart part)  {
		IContentOutlinePage page = null;
		if (outlinePageProvider != null) {
			// can be null, optional injection
			page = outlinePageProvider.get();
			XtextEditor xtextEditor = null;
			if (part instanceof XtextEditor) {
				xtextEditor = (XtextEditor)part;
			}
			
			if (xtextEditor != null && page != null) {
				if (page instanceof ISourceViewerAware) {
					((ISourceViewerAware) page).setSourceViewer(xtextEditor.getInternalSourceViewer());
				}
				if (page instanceof IXtextEditorAware) {
					((IXtextEditorAware) page).setEditor(xtextEditor);
				}
			}
		}
		return page;
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
        IContentOutlinePage page = (IContentOutlinePage) pageRecord.page;
        page.dispose();
        pageRecord.dispose();
	}

	@Override
	protected IWorkbenchPart getBootstrapPart() {
        IWorkbenchPage page = getSite().getPage();
        if (page != null) {
			return page.getActiveEditor();
		}
        return null;
	}

	@Override
	protected boolean isImportant(IWorkbenchPart part) {
        return (part instanceof XtextEditor);
	}

}
