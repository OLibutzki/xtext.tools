package de.libutzki.xtext.nodemodeloutline.ui.actions;

import com.google.inject.Inject;
import de.libutzki.xtext.nodemodeloutline.ui.content.NodeOutlineNode;
import java.util.Arrays;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class FilterHiddenLeafsContribution extends AbstractFilterOutlineContribution {
  public final static String PREFERENCE_KEY = "ui.outline.filterNodes";
  
  @Inject
  private IImageDescriptorHelper imageHelper;
  
  protected boolean _apply(final IOutlineNode outlineNode) {
    return true;
  }
  
  protected boolean _apply(final NodeOutlineNode outlineNode) {
    INode _node = outlineNode.getNode();
    boolean _not = (!(_node instanceof HiddenLeafNode));
    return _not;
  }
  
  protected void configureAction(final Action action) {
    action.setText("Hide hidden nodes");
    action.setDescription("Hide hidden nodes");
    action.setToolTipText("Hide hidden nodes");
    ImageDescriptor _imageDescriptor = this.imageHelper.getImageDescriptor("hiddennode.png");
    action.setImageDescriptor(_imageDescriptor);
  }
  
  public String getPreferenceKey() {
    return FilterHiddenLeafsContribution.PREFERENCE_KEY;
  }
  
  protected boolean apply(final IOutlineNode outlineNode) {
    if (outlineNode instanceof NodeOutlineNode) {
      return _apply((NodeOutlineNode)outlineNode);
    } else if (outlineNode != null) {
      return _apply(outlineNode);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(outlineNode).toString());
    }
  }
}
