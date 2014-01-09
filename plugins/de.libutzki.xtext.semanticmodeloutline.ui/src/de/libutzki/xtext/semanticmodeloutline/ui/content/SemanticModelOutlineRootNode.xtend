package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.ecore.EObject
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider
import org.eclipse.xtext.util.concurrent.IUnitOfWork

class SemanticModelOutlineRootNode extends DocumentRootNode {
	
	new(Image image, Object text, IXtextDocument document, IOutlineTreeStructureProvider treeProvider) {
		super(image, text, document, treeProvider)
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