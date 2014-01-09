package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode

class CrossReferenceNode extends EStructuralFeatureNode {
	
	@Property
	URI targetURI
	
	new(EObject owner, EStructuralFeature feature, IOutlineNode parent, Image image, Object text, URI targetURI) {
		super(owner, feature, parent, image, text, true)
		_targetURI = targetURI
	}

}