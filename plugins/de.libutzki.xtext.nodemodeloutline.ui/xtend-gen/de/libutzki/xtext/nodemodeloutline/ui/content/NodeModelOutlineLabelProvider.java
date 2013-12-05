package de.libutzki.xtext.nodemodeloutline.ui.content;

import com.google.common.base.Objects;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.nodemodel.impl.RootNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class NodeModelOutlineLabelProvider extends DeclarativeLabelProvider {
  private Color hiddenLeafColor;
  
  public String text(final RootNode rootNode) {
    return "Root node";
  }
  
  public String text(final ICompositeNode node) {
    return "composite";
  }
  
  public StyledString text(final HiddenLeafNode hiddenLeafNode) {
    final Styler _function = new Styler() {
      @Override
      public void applyStyles(final TextStyle it) {
        Color _hiddenLeafColor = NodeModelOutlineLabelProvider.this.getHiddenLeafColor();
        it.foreground = _hiddenLeafColor;
      }
    };
    StyledString _styledString = new StyledString("hidden", _function);
    return _styledString;
  }
  
  private Color getHiddenLeafColor() {
    Color _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.hiddenLeafColor, null);
      if (_equals) {
        RGB _rGB = new RGB(125, 125, 125);
        Color _colorFromRGB = EditorUtils.colorFromRGB(_rGB);
        this.hiddenLeafColor = _colorFromRGB;
      }
      _xblockexpression = (this.hiddenLeafColor);
    }
    return _xblockexpression;
  }
  
  public String text(final INode leafNode) {
    String _tokenText = NodeModelUtils.getTokenText(leafNode);
    return _tokenText;
  }
  
  public String image(final ICompositeNode compositeNode) {
    return "compositenode.png";
  }
  
  public String image(final HiddenLeafNode hiddenLeafNode) {
    return "hiddennode.png";
  }
  
  public String image(final ILeafNode leafNode) {
    return "leafnode.png";
  }
}
