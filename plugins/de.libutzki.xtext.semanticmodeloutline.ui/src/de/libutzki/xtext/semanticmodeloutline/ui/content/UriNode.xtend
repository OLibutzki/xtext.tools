package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode

public class UriNode extends EObjectNode {

	new(EObject eObject, IOutlineNode parent, Image image) {
		super(eObject, parent, image, null, true)
	}

	
	new(EObject eObject, IOutlineNode parent,
			ImageDescriptor imageDescriptor) {
		super(eObject, parent, imageDescriptor, null, true)
	}
	
	override getText() {
		EObjectURI.toString
	}

}
