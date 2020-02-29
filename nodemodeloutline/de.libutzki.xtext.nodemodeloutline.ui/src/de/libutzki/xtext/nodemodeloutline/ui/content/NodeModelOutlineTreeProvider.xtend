package de.libutzki.xtext.nodemodeloutline.ui.content

import com.google.inject.Inject
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.nodemodel.ICompositeNode
import org.eclipse.xtext.nodemodel.INode
import org.eclipse.xtext.ui.editor.model.IXtextDocument
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider
import org.eclipse.xtext.util.PolymorphicDispatcher
import org.eclipse.xtext.util.TextRegion

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class NodeModelOutlineTreeProvider implements IOutlineTreeProvider, INodeModelOutlineTreeStructureProvider {
	
	@Inject
	protected ILabelProvider labelProvider
	
	protected PolymorphicDispatcher<Void> createChildrenDispatcher = PolymorphicDispatcher.createForSingleTarget(
			"_createChildren", 2, 2, this);

	protected PolymorphicDispatcher<Void> createNodeDispatcher = PolymorphicDispatcher.createForSingleTarget(
			"_createNode", 2, 2, this);
	
	protected PolymorphicDispatcher<Object> textDispatcher = PolymorphicDispatcher.createForSingleTarget("_text", 1, 1,
			this);

	protected PolymorphicDispatcher<Image> imageDispatcher = PolymorphicDispatcher.createForSingleTarget("_image", 1,
			1, this);

	protected PolymorphicDispatcher<Boolean> isLeafDispatcher = PolymorphicDispatcher.createForSingleTarget("_isLeaf",
			1, 1, this);
	
	override createRoot(IXtextDocument document) {
		document.readOnly[
			val node = parseResult.rootNode
			new RootNodeOutlineNode(node, imageDispatcher.invoke(node),
				textDispatcher.invoke(node), document, this) => [
					textRegion = new TextRegion(0, document.length)
				]
		]
	}
	
	override createChildren(IOutlineNode parentNode, INode node) {
		if (node !== null && parentNode.hasChildren())
			createChildrenDispatcher.invoke(parentNode, node);
	}

	def protected NodeOutlineNode createOutlineNode(IOutlineNode parentNode, INode node) {
		
		return createOutlineNode(parentNode, node, imageDispatcher.invoke(node),
				textDispatcher.invoke(node), isLeafDispatcher.invoke(node));
	}
	
	def protected NodeOutlineNode createOutlineNode(IOutlineNode parentNode, INode node, Image image, Object text,
			boolean isLeaf) {
		 new NodeOutlineNode(node, parentNode, image, text, isLeaf) => [
		 	textRegion = new TextRegion(node.offset, node.length)
		 ]
	}
	
	def protected void createNode(IOutlineNode parent, INode node) {
		createNodeDispatcher.invoke(parent, node);
	}
	
	def protected void _createNode(IOutlineNode parentNode, INode node) {
		val text = textDispatcher.invoke(node);
		val isLeaf = isLeafDispatcher.invoke(node);
		if (text === null && isLeaf)
			return
		val image = imageDispatcher.invoke(node);
		createOutlineNode(parentNode, node, image, text, isLeaf);
	}
	
	/**
	 * Default for isLeafDispatcher
	 */
	def protected boolean _isLeaf(Object node) {
		return true;
	}

	/**
	 * Default for textDispatcher
	 */
	def protected Object _text(Object node) {
		switch labelProvider {
			IStyledLabelProvider : labelProvider.getStyledText(node)
			default : labelProvider.getText(node)
		}
	}

	/**
	 * Default for imageDispatcher
	 */
	def protected Image _image(Object node) {
		return labelProvider.getImage(node);
	}
	
	
	
	def protected _createNode(RootNodeOutlineNode rootNode, ICompositeNode node) {
		createOutlineNode(rootNode, node) => [
			textRegion = rootNode.significantTextRegion
		]
	}
	def protected _createChildren(RootNodeOutlineNode rootNode, ICompositeNode node) {
		rootNode.createNode(node)
	}
	def protected _createChildren(NodeOutlineNode parentNode, ICompositeNode node) {

		node.children.forEach[parentNode.createNode(it)]
	}
	
	def boolean _isLeaf(ICompositeNode compositeNode) {
		false
	}


	
}