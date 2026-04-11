package de.libutzki.xtext.nodemodeloutline.ui.content;

import com.google.inject.Inject;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

public class NodeModelOutlineTreeProvider implements IOutlineTreeProvider, INodeModelOutlineTreeStructureProvider {
  @Inject
  protected ILabelProvider labelProvider;

  protected PolymorphicDispatcher<Void> createChildrenDispatcher =
      PolymorphicDispatcher.createForSingleTarget("_createChildren", 2, 2, this);

  protected PolymorphicDispatcher<Void> createNodeDispatcher =
      PolymorphicDispatcher.createForSingleTarget("_createNode", 2, 2, this);

  protected PolymorphicDispatcher<Object> textDispatcher =
      PolymorphicDispatcher.createForSingleTarget("_text", 1, 1, this);

  protected PolymorphicDispatcher<Image> imageDispatcher =
      PolymorphicDispatcher.createForSingleTarget("_image", 1, 1, this);

  protected PolymorphicDispatcher<Boolean> isLeafDispatcher =
      PolymorphicDispatcher.createForSingleTarget("_isLeaf", 1, 1, this);

  @Override
  public IOutlineNode createRoot(final IXtextDocument document) {
    return document.readOnly(new IUnitOfWork<RootNodeOutlineNode, XtextResource>() {
      @Override
      public RootNodeOutlineNode exec(XtextResource resource) {
        ICompositeNode node = resource.getParseResult().getRootNode();
        RootNodeOutlineNode root = new RootNodeOutlineNode(
            node,
            imageDispatcher.invoke(node),
            textDispatcher.invoke(node),
            document,
            NodeModelOutlineTreeProvider.this);
        root.setTextRegion(new TextRegion(0, document.getLength()));
        return root;
      }
    });
  }

  @Override
  public void createChildren(IOutlineNode parentNode, INode node) {
    if (node != null && parentNode.hasChildren()) {
      createChildrenDispatcher.invoke(parentNode, node);
    }
  }

  protected NodeOutlineNode createOutlineNode(IOutlineNode parentNode, INode node) {
    return createOutlineNode(
        parentNode,
        node,
        imageDispatcher.invoke(node),
        textDispatcher.invoke(node),
        isLeafDispatcher.invoke(node).booleanValue());
  }

  protected NodeOutlineNode createOutlineNode(
      IOutlineNode parentNode, INode node, Image image, Object text, boolean isLeaf) {
    NodeOutlineNode outlineNode = new NodeOutlineNode(node, parentNode, image, text, isLeaf);
    outlineNode.setTextRegion(new TextRegion(node.getOffset(), node.getLength()));
    return outlineNode;
  }

  protected void createNode(IOutlineNode parent, INode node) {
    createNodeDispatcher.invoke(parent, node);
  }

  protected void _createNode(IOutlineNode parentNode, INode node) {
    Object text = textDispatcher.invoke(node);
    boolean isLeaf = isLeafDispatcher.invoke(node).booleanValue();
    if (text == null && isLeaf) {
      return;
    }
    createOutlineNode(parentNode, node, imageDispatcher.invoke(node), text, isLeaf);
  }

  protected boolean _isLeaf(Object node) {
    return true;
  }

  protected Object _text(Object node) {
    if (labelProvider instanceof DelegatingStyledCellLabelProvider.IStyledLabelProvider) {
      return ((DelegatingStyledCellLabelProvider.IStyledLabelProvider) labelProvider).getStyledText(node);
    }
    return labelProvider.getText(node);
  }

  protected Image _image(Object node) {
    return labelProvider.getImage(node);
  }

  protected NodeOutlineNode _createNode(RootNodeOutlineNode rootNode, ICompositeNode node) {
    NodeOutlineNode outlineNode = createOutlineNode(rootNode, node);
    outlineNode.setTextRegion(rootNode.getSignificantTextRegion());
    return outlineNode;
  }

  protected void _createChildren(RootNodeOutlineNode rootNode, ICompositeNode node) {
    createNode(rootNode, node);
  }

  protected void _createChildren(NodeOutlineNode parentNode, ICompositeNode node) {
    for (INode child : node.getChildren()) {
      createNode(parentNode, child);
    }
  }

  public boolean _isLeaf(ICompositeNode compositeNode) {
    return false;
  }
}
