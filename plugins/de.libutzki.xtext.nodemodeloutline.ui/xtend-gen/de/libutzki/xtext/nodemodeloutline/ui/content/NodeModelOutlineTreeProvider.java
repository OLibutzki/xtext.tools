package de.libutzki.xtext.nodemodeloutline.ui.content;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import de.libutzki.xtext.nodemodeloutline.ui.content.INodeModelOutlineTreeStructureProvider;
import de.libutzki.xtext.nodemodeloutline.ui.content.NodeOutlineNode;
import de.libutzki.xtext.nodemodeloutline.ui.content.RootNodeOutlineNode;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.BidiIterable;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.IOutlineTreeProvider;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class NodeModelOutlineTreeProvider implements IOutlineTreeProvider, INodeModelOutlineTreeStructureProvider {
  @Inject
  protected ILabelProvider labelProvider;
  
  protected PolymorphicDispatcher<Void> createChildrenDispatcher = new Function0<PolymorphicDispatcher<Void>>() {
    public PolymorphicDispatcher<Void> apply() {
      PolymorphicDispatcher<Void> _createForSingleTarget = PolymorphicDispatcher.<Void>createForSingleTarget(
        "_createChildren", 2, 2, NodeModelOutlineTreeProvider.this);
      return _createForSingleTarget;
    }
  }.apply();
  
  protected PolymorphicDispatcher<Void> createNodeDispatcher = new Function0<PolymorphicDispatcher<Void>>() {
    public PolymorphicDispatcher<Void> apply() {
      PolymorphicDispatcher<Void> _createForSingleTarget = PolymorphicDispatcher.<Void>createForSingleTarget(
        "_createNode", 2, 2, NodeModelOutlineTreeProvider.this);
      return _createForSingleTarget;
    }
  }.apply();
  
  protected PolymorphicDispatcher<Object> textDispatcher = new Function0<PolymorphicDispatcher<Object>>() {
    public PolymorphicDispatcher<Object> apply() {
      PolymorphicDispatcher<Object> _createForSingleTarget = PolymorphicDispatcher.<Object>createForSingleTarget("_text", 1, 1, NodeModelOutlineTreeProvider.this);
      return _createForSingleTarget;
    }
  }.apply();
  
  protected PolymorphicDispatcher<Image> imageDispatcher = new Function0<PolymorphicDispatcher<Image>>() {
    public PolymorphicDispatcher<Image> apply() {
      PolymorphicDispatcher<Image> _createForSingleTarget = PolymorphicDispatcher.<Image>createForSingleTarget("_image", 1, 
        1, NodeModelOutlineTreeProvider.this);
      return _createForSingleTarget;
    }
  }.apply();
  
  protected PolymorphicDispatcher<Boolean> isLeafDispatcher = new Function0<PolymorphicDispatcher<Boolean>>() {
    public PolymorphicDispatcher<Boolean> apply() {
      PolymorphicDispatcher<Boolean> _createForSingleTarget = PolymorphicDispatcher.<Boolean>createForSingleTarget("_isLeaf", 
        1, 1, NodeModelOutlineTreeProvider.this);
      return _createForSingleTarget;
    }
  }.apply();
  
  public IOutlineNode createRoot(final IXtextDocument document) {
    final IUnitOfWork<RootNodeOutlineNode,XtextResource> _function = new IUnitOfWork<RootNodeOutlineNode,XtextResource>() {
      public RootNodeOutlineNode exec(final XtextResource it) throws Exception {
        RootNodeOutlineNode _xblockexpression = null;
        {
          IParseResult _parseResult = it.getParseResult();
          final ICompositeNode node = _parseResult.getRootNode();
          Image _invoke = NodeModelOutlineTreeProvider.this.imageDispatcher.invoke(node);
          Object _invoke_1 = NodeModelOutlineTreeProvider.this.textDispatcher.invoke(node);
          RootNodeOutlineNode _rootNodeOutlineNode = new RootNodeOutlineNode(node, _invoke, _invoke_1, document, NodeModelOutlineTreeProvider.this);
          final Procedure1<RootNodeOutlineNode> _function = new Procedure1<RootNodeOutlineNode>() {
            public void apply(final RootNodeOutlineNode it) {
              int _length = document.getLength();
              TextRegion _textRegion = new TextRegion(0, _length);
              it.setTextRegion(_textRegion);
            }
          };
          RootNodeOutlineNode _doubleArrow = ObjectExtensions.<RootNodeOutlineNode>operator_doubleArrow(_rootNodeOutlineNode, _function);
          _xblockexpression = (_doubleArrow);
        }
        return _xblockexpression;
      }
    };
    RootNodeOutlineNode _readOnly = document.<RootNodeOutlineNode>readOnly(_function);
    return _readOnly;
  }
  
  public void createChildren(final IOutlineNode parentNode, final INode node) {
    boolean _and = false;
    boolean _notEquals = (!Objects.equal(node, null));
    if (!_notEquals) {
      _and = false;
    } else {
      boolean _hasChildren = parentNode.hasChildren();
      _and = (_notEquals && _hasChildren);
    }
    if (_and) {
      this.createChildrenDispatcher.invoke(parentNode, node);
    }
  }
  
  protected NodeOutlineNode createOutlineNode(final IOutlineNode parentNode, final INode node) {
    Image _invoke = this.imageDispatcher.invoke(node);
    Object _invoke_1 = this.textDispatcher.invoke(node);
    Boolean _invoke_2 = this.isLeafDispatcher.invoke(node);
    return this.createOutlineNode(parentNode, node, _invoke, _invoke_1, (_invoke_2).booleanValue());
  }
  
  protected NodeOutlineNode createOutlineNode(final IOutlineNode parentNode, final INode node, final Image image, final Object text, final boolean isLeaf) {
    NodeOutlineNode _nodeOutlineNode = new NodeOutlineNode(node, parentNode, image, text, isLeaf);
    final Procedure1<NodeOutlineNode> _function = new Procedure1<NodeOutlineNode>() {
      public void apply(final NodeOutlineNode it) {
        int _offset = node.getOffset();
        int _length = node.getLength();
        TextRegion _textRegion = new TextRegion(_offset, _length);
        it.setTextRegion(_textRegion);
      }
    };
    NodeOutlineNode _doubleArrow = ObjectExtensions.<NodeOutlineNode>operator_doubleArrow(_nodeOutlineNode, _function);
    return _doubleArrow;
  }
  
  protected void createNode(final IOutlineNode parent, final INode node) {
    this.createNodeDispatcher.invoke(parent, node);
  }
  
  protected void _createNode(final IOutlineNode parentNode, final INode node) {
    final Object text = this.textDispatcher.invoke(node);
    final Boolean isLeaf = this.isLeafDispatcher.invoke(node);
    boolean _and = false;
    boolean _equals = Objects.equal(text, null);
    if (!_equals) {
      _and = false;
    } else {
      _and = (_equals && (isLeaf).booleanValue());
    }
    if (_and) {
      return;
    }
    final Image image = this.imageDispatcher.invoke(node);
    this.createOutlineNode(parentNode, node, image, text, (isLeaf).booleanValue());
  }
  
  /**
   * Default for isLeafDispatcher
   */
  protected boolean _isLeaf(final Object node) {
    return true;
  }
  
  /**
   * Default for textDispatcher
   */
  protected Object _text(final Object node) {
    Object _switchResult = null;
    final ILabelProvider labelProvider = this.labelProvider;
    boolean _matched = false;
    if (!_matched) {
      if (labelProvider instanceof IStyledLabelProvider) {
        _matched=true;
        StyledString _styledText = ((IStyledLabelProvider)this.labelProvider).getStyledText(node);
        _switchResult = _styledText;
      }
    }
    if (!_matched) {
      String _text = this.labelProvider.getText(node);
      _switchResult = _text;
    }
    return _switchResult;
  }
  
  /**
   * Default for imageDispatcher
   */
  protected Image _image(final Object node) {
    return this.labelProvider.getImage(node);
  }
  
  protected NodeOutlineNode _createNode(final RootNodeOutlineNode rootNode, final ICompositeNode node) {
    NodeOutlineNode _createOutlineNode = this.createOutlineNode(rootNode, node);
    final Procedure1<NodeOutlineNode> _function = new Procedure1<NodeOutlineNode>() {
      public void apply(final NodeOutlineNode it) {
        ITextRegion _significantTextRegion = rootNode.getSignificantTextRegion();
        it.setTextRegion(_significantTextRegion);
      }
    };
    NodeOutlineNode _doubleArrow = ObjectExtensions.<NodeOutlineNode>operator_doubleArrow(_createOutlineNode, _function);
    return _doubleArrow;
  }
  
  protected void _createChildren(final RootNodeOutlineNode rootNode, final ICompositeNode node) {
    this.createNode(rootNode, node);
  }
  
  protected void _createChildren(final NodeOutlineNode parentNode, final ICompositeNode node) {
    BidiIterable<INode> _children = node.getChildren();
    final Procedure1<INode> _function = new Procedure1<INode>() {
      public void apply(final INode it) {
        NodeModelOutlineTreeProvider.this.createNode(parentNode, it);
      }
    };
    IterableExtensions.<INode>forEach(_children, _function);
  }
  
  public boolean _isLeaf(final ICompositeNode compositeNode) {
    return false;
  }
}
