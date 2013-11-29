package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode

class EClassStructuralFeatureNode extends EStructuralFeatureNode {
	
	
	new(EObject owner, EStructuralFeature feature, IOutlineNode parent, Image image, Object text,
			boolean isLeaf) {
		super(owner, feature, parent, image, text, isLeaf);
	}

	/**
	 * @since 2.4
	 */
	new(EObject owner, EStructuralFeature feature, IOutlineNode parent, ImageDescriptor imageDescriptor, Object text,
			boolean isLeaf) {
		super(owner, feature, parent, imageDescriptor, text, isLeaf);
	}
}