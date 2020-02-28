package de.libutzki.xtext.nodemodeloutline.ui.content

import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.viewers.StyledString
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.RGB
import org.eclipse.xtext.AbstractElement
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.Action
import org.eclipse.xtext.CrossReference
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.RuleCall
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode
import org.eclipse.xtext.ui.editor.utils.EditorUtils
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class NodeModelOutlineLabelProvider extends DeclarativeLabelProvider {
	
	Color hiddenLeafColor;
	
//	def String text(INode leafNode) {
//		NodeModelUtils.getTokenText(leafNode)
//	}	
//	
//	def String text(RootNode rootNode) {
//		"Root node"		
//	}
//	
//	def String text(ICompositeNode node) {
//		"composite"
//	}
	
	def StyledString text(HiddenLeafNode hiddenLeafNode) {
		new StyledString(hiddenLeafNode.grammarElement.text, [foreground = getHiddenLeafColor])
	}
	
	def private getHiddenLeafColor() {
		if (hiddenLeafColor === null) {
			hiddenLeafColor = EditorUtils.colorFromRGB(new RGB(125, 125, 125))
		}
		hiddenLeafColor
	}
	
	def String text(INode node) {
		node.grammarElement.text
	}
	def String text(EObject eObject) {
		val typePrefix = '''[«eObject.eClass.name»]'''
		val label = eObject.label
		'''«typePrefix» «label»'''
		
	}
	
	
	def private dispatch String  getLabel(AbstractElement element) {
		"<unnamed>"
	}
	
	def private dispatch String  getLabel(Keyword keyword) {
		keyword.value
	}
	
	def private dispatch String  getLabel(CrossReference crossReference) {
		'''«crossReference.type.classifier.name»'''
	}
	
	def private dispatch String  getLabel(RuleCall ruleCall) {
		'''«ruleCall.rule.name»'''
	}
	
	def private dispatch String  getLabel(Action action) {
		action.type.classifier.name
	}
	
	def private dispatch String  getLabel(AbstractRule rule) {
		rule.name
	}
	

	

	
	def String image(ICompositeNode compositeNode) {
		"compositenode.png"
	}
	
	def String image(HiddenLeafNode hiddenLeafNode) {
		"hiddennode.png"
	}
	
	def String image(ILeafNode leafNode) {
		"leafnode.png"
	}
	
}