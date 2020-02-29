package de.libutzki.xtext.nodemodeloutline.ui.content;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.AbstractOutlineNode;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.collect.Lists;

/**
 * Unfortunately this class contains a lot of copy/paste code.
 * The source is {@link AbstractOutlineNode}.
 * 
 * @author Jan Koehnlein - Author of {@link AbstractOutlineNode}
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class NodeOutlineNode implements IOutlineNode{

	private Image image;

	private Object text;

	private NodeOutlineNode parentOutlineNode;

	private List<IOutlineNode> children;

	private boolean isLeaf = false;

	private ITextRegion textRegion;
	
	protected INode node;

	/**
	 * A {@link BackgroundOutlineTreeProvider} must use
	 * {@link #AbstractOutlineNode(IOutlineNode, ImageDescriptor, Object, boolean)} instead.
	 */
	protected NodeOutlineNode(INode node, IOutlineNode parent, Image image, Object text, boolean isLeaf) {
		this.node = node;
		this.text = text == null ? "<unnamed>" : text;
		this.image = image;
		this.isLeaf = isLeaf;
		setParent(parent);
		textRegion = ITextRegion.EMPTY_REGION;
	}

	protected void setParent(IOutlineNode newParent) {
		Assert.isLegal(newParent == null || newParent instanceof NodeOutlineNode);
		if (parentOutlineNode != null)
			parentOutlineNode.removeChild(this);
		parentOutlineNode = (NodeOutlineNode) newParent;
		if (parentOutlineNode != null)
			parentOutlineNode.addChild(this);
	}

	protected boolean addChild(IOutlineNode outlineNode) {
		if (children == null)
			children = Lists.newArrayList();
		isLeaf = false;
		return children.add(outlineNode);
	}

	protected boolean removeChild(IOutlineNode outlineNode) {
		if (children == null)
			return false;
		return children.remove(outlineNode);
	}

	public List<IOutlineNode> getChildren() {
		if (isLeaf)
			return Collections.emptyList();
		if (children == null) {
			INodeModelOutlineTreeStructureProvider treeProvider = getTreeProvider();
			if (treeProvider != null) {
				treeProvider.createChildren(this, node);
			}
			if (children == null) {
				// tree provider did not create any child
				isLeaf = true;
				return Collections.emptyList();
			}
		}
		return Collections.unmodifiableList(children);
		

	}

	public IOutlineNode getParent() {
		return parentOutlineNode;
	}

	public boolean hasChildren() {
		return !isLeaf;
	}

	public Object getText() {
		return text;
	}

	public void setText(Object text) {
		this.text = text;
	}

	/**
	 * @deprecated use {@link #getImageDescriptor()} instead.
	 */
	@Deprecated
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}


	public IXtextDocument getDocument() {
		if (parentOutlineNode != null) {
			return parentOutlineNode.getDocument();
		}
		return null;
	}

	public INodeModelOutlineTreeStructureProvider getTreeProvider() {
		if (parentOutlineNode != null) {
			return parentOutlineNode.getTreeProvider();
		}
		return null;
	}

	public void setTextRegion(ITextRegion textRegion) {
		this.textRegion = textRegion;
	}

	public ITextRegion getFullTextRegion() {
		return textRegion;
	}

	public ITextRegion getSignificantTextRegion() {
		return textRegion;
	}

	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + "] " + text.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getAdapter(Class adapterType) {
		return Platform.getAdapterManager().getAdapter(this, adapterType);
	}

	protected URI getEObjectURI() {
		return EcoreUtil.getURI(node.getSemanticElement());
	}

	@Override
	public <T> T readOnly(final IUnitOfWork<T, EObject> work) {
		if (getEObjectURI() != null) {
			return getDocument().readOnly(new IUnitOfWork<T, XtextResource>() {
				public T exec(XtextResource state) throws Exception {
					EObject eObject;
					if (state.getResourceSet() != null)
						eObject = state.getResourceSet().getEObject(getEObjectURI(), true);
					else
						eObject = state.getEObject(getEObjectURI().fragment());
					return work.exec(eObject);
				}

			});
		} else {
			return null;
		}
	}
	
	public INode getNode() {
		return node;
	}

}
