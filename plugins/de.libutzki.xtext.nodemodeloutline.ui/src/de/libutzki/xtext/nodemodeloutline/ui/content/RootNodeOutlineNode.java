package de.libutzki.xtext.nodemodeloutline.ui.content;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class RootNodeOutlineNode extends NodeOutlineNode {

	
	private INodeModelOutlineTreeStructureProvider treeProvider;
	private IXtextDocument document;

	public RootNodeOutlineNode(INode node, Image image, Object text, IXtextDocument document,
			INodeModelOutlineTreeStructureProvider treeProvider) {
		super(node, null, image, text, false);
		this.document = document;
		this.treeProvider = treeProvider;
	}

	public RootNodeOutlineNode(INode node, ImageDescriptor imageDescriptor, Object text, IXtextDocument document,
			INodeModelOutlineTreeStructureProvider treeProvider) {
		super(node, null, imageDescriptor, text, false);
		this.document = document;
		this.treeProvider = treeProvider;
	}
	
	
	@Override
	public INodeModelOutlineTreeStructureProvider getTreeProvider() {
		return treeProvider;
	}
	
	@Override
	public IXtextDocument getDocument() {
		return document;
	}

}
