package de.libutzki.xtext.nodemodeloutline.ui.content

import org.eclipse.jface.viewers.StyledString
import org.eclipse.swt.graphics.RGB
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.ILeafNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode
import org.eclipse.xtext.nodemodel.impl.RootNode
import org.eclipse.xtext.ui.editor.utils.EditorUtils
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider
import org.eclipse.swt.graphics.Color
import org.eclipse.xtext.nodemodel.util.NodeModelUtils

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class NodeModelOutlineLabelProvider extends DeclarativeLabelProvider {
	
	private Color hiddenLeafColor;
	
	def String text(RootNode rootNode) {
		"Root node"		
	}
	
	def String text(ICompositeNode node) {
		"composite"
	}
	
	def StyledString text(HiddenLeafNode hiddenLeafNode) {
		new StyledString("hidden", [foreground = getHiddenLeafColor])
	}
	
	def private getHiddenLeafColor() {
		if (hiddenLeafColor == null) {
			hiddenLeafColor = EditorUtils.colorFromRGB(new RGB(125, 125, 125))
		}
		hiddenLeafColor
	}
	
	def String text(INode leafNode) {
		NodeModelUtils.getTokenText(leafNode)
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