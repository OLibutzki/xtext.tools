package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class FilterDerivedElementsOutlineContribution extends AbstractFilterOutlineContribution {
  public final static String PREFERENCE_KEY = "ui.outline.filterDerivedElements";
  
  @Inject
  private IImageDescriptorHelper imageHelper;
  
  protected boolean apply(final IOutlineNode outlineNode) {
    boolean _xblockexpression = false;
    {
      final IOutlineNode parent = outlineNode.getParent();
      boolean _or = false;
      boolean _not = (!(parent instanceof DocumentRootNode));
      if (_not) {
        _or = true;
      } else {
        List<IOutlineNode> _children = parent.getChildren();
        int _indexOf = _children.indexOf(outlineNode);
        boolean _equals = (_indexOf == 0);
        _or = (_not || _equals);
      }
      _xblockexpression = (_or);
    }
    return _xblockexpression;
  }
  
  protected void configureAction(final Action action) {
    action.setText("Hide derived elements");
    action.setDescription("Hide derived elements");
    action.setToolTipText("Hide derived elements");
    ImageDescriptor _imageDescriptor = this.imageHelper.getImageDescriptor("filterDerivedEClass.gif");
    action.setImageDescriptor(_imageDescriptor);
  }
  
  public String getPreferenceKey() {
    return FilterDerivedElementsOutlineContribution.PREFERENCE_KEY;
  }
}
