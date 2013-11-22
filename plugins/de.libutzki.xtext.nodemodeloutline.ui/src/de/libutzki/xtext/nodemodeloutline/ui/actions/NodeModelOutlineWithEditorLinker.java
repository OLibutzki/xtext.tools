package de.libutzki.xtext.nodemodeloutline.ui.actions;

import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xtext.ui.editor.outline.actions.OutlineWithEditorLinker;

import de.libutzki.xtext.nodemodeloutline.ui.view.NodeModelOutline;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class NodeModelOutlineWithEditorLinker extends OutlineWithEditorLinker {

	@Override
	protected boolean isOutlineViewActive() {
		IWorkbenchPart activePart = outlinePage.getSite().getPage().getActivePart();
		return activePart instanceof NodeModelOutline;
	}
}
