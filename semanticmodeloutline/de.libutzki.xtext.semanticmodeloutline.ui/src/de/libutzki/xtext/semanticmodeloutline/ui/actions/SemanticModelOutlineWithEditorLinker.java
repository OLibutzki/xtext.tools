package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.ui.editor.outline.actions.OutlineWithEditorLinker;

import de.libutzki.xtext.semanticmodeloutline.ui.view.SemanticModelOutline;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class SemanticModelOutlineWithEditorLinker extends OutlineWithEditorLinker {

	@Override
	protected boolean isOutlineViewActive() {
		IWorkbenchPart activePart = outlinePage.getSite().getPage().getActivePart();
		return activePart instanceof SemanticModelOutline;
	}
}
