package de.libutzki.xtext.nodemodeloutline.ui.content;

import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public interface INodeModelOutlineTreeStructureProvider {

	void createChildren(IOutlineNode parentNode, INode node);
}
