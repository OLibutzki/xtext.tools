package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.xtext.util.concurrent.IUnitOfWork
import org.eclipse.emf.ecore.EObject

class SemanticModelOutlineRootNode extends DocumentRootNode {
	
	new(Image image, Object text, IXtextDocument document, IOutlineTreeStructureProvider treeProvider) {
		super(image, text, document, treeProvider)
	}
	
	new(ImageDescriptor imageDescriptor, Object text, IXtextDocument document, IOutlineTreeStructureProvider treeProvider) {
		super(imageDescriptor, text, document, treeProvider)
	}
	
	override <T> readOnly(IUnitOfWork<T, EObject> work) {
		document.readOnly[ resource |
			if (!resource?.contents.nullOrEmpty) {
				resource.contents.forEach[
					work.exec(it)
				]
			}
			null
		]	
	}
	
}