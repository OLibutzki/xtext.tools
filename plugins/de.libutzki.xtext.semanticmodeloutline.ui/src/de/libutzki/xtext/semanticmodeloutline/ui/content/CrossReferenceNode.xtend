package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode
import org.eclipse.emf.common.util.URI

class CrossReferenceNode extends EStructuralFeatureNode {
	
	@Property
	URI targetURI
	
	new(EObject owner, EStructuralFeature feature, IOutlineNode parent, Image image, Object text, URI targetURI) {
		super(owner, feature, parent, image, text, true)
		_targetURI = targetURI
	}

	/**
	 * @since 2.4
	 */
	new(EObject owner, EStructuralFeature feature, IOutlineNode parent, ImageDescriptor imageDescriptor, Object text, URI targetURI) {
		super(owner, feature, parent, imageDescriptor, text, true);
		_targetURI = targetURI
	}
}