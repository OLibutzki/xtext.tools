package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.UriNode;
import java.util.Arrays;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class FilterUriOutlineContribution extends AbstractFilterOutlineContribution {
  public final static String PREFERENCE_KEY = "ui.outline.filterUri";
  
  @Inject
  private IImageDescriptorHelper imageHelper;
  
  protected boolean _apply(final IOutlineNode outlineNode) {
    return true;
  }
  
  protected boolean _apply(final UriNode outlineNode) {
    return false;
  }
  
  protected void configureAction(final Action action) {
    action.setText("Hide URI");
    action.setDescription("Hide URI");
    action.setToolTipText("Hide URI");
    ImageDescriptor _imageDescriptor = this.imageHelper.getImageDescriptor("filterUri.gif");
    action.setImageDescriptor(_imageDescriptor);
  }
  
  public String getPreferenceKey() {
    return FilterUriOutlineContribution.PREFERENCE_KEY;
  }
  
  protected boolean apply(final IOutlineNode outlineNode) {
    if (outlineNode instanceof UriNode) {
      return _apply((UriNode)outlineNode);
    } else if (outlineNode != null) {
      return _apply(outlineNode);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(outlineNode).toString());
    }
  }
}
