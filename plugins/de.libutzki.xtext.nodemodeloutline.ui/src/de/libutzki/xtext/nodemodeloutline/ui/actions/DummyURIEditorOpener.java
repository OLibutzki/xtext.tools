package de.libutzki.xtext.nodemodeloutline.ui.actions;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

/**
 * We just need something to bind to IURIEditorOpener
 * 
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class DummyURIEditorOpener implements IURIEditorOpener {

	@Override
	public IEditorPart open(URI uri, boolean select) {
		return null;
	}

	@Override
	public IEditorPart open(URI referenceOwnerURI, EReference reference,
			int indexInList, boolean select) {
		return null;
	}

}
