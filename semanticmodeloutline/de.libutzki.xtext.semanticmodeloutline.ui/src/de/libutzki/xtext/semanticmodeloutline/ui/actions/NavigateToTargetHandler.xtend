package de.libutzki.xtext.semanticmodeloutline.ui.actions

import com.google.inject.Inject
import de.libutzki.xtext.semanticmodeloutline.ui.content.CrossReferenceNode
import org.eclipse.core.commands.AbstractHandler
import org.eclipse.core.commands.ExecutionEvent
import org.eclipse.core.commands.ExecutionException
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.ui.handlers.HandlerUtil
import org.eclipse.xtext.ui.editor.IURIEditorOpener

class NavigateToTargetHandler extends AbstractHandler {
	
	@Inject
	IURIEditorOpener editorOpener
	
	override execute(ExecutionEvent event) throws ExecutionException {
		val node = event.crossReferenceNode
		if (node !== null) {
			editorOpener.open(node.targetURI, true)
		}
		null
	}
	
	def private CrossReferenceNode getCrossReferenceNode(ExecutionEvent event) {
		val currentSelection = HandlerUtil.getCurrentSelection(event)
		switch currentSelection {
			IStructuredSelection : currentSelection.firstElement as CrossReferenceNode
		}
	}
	
}