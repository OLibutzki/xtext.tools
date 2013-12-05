package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import java.util.Arrays;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class FilterAttributesOutlineContribution extends AbstractFilterOutlineContribution {
  public final static String PREFERENCE_KEY = "ui.outline.filterAttributes";
  
  @Inject
  private IImageDescriptorHelper imageHelper;
  
  protected boolean _apply(final IOutlineNode outlineNode) {
    return true;
  }
  
  protected boolean _apply(final EStructuralFeatureNode outlineNode) {
    EStructuralFeature _eStructuralFeature = outlineNode.getEStructuralFeature();
    boolean _not = (!(_eStructuralFeature instanceof EAttribute));
    return _not;
  }
  
  protected void configureAction(final Action action) {
    action.setText("Hide attributes");
    action.setDescription("Hide attributes");
    action.setToolTipText("Hide attributes");
    ImageDescriptor _imageDescriptor = this.imageHelper.getImageDescriptor("filterAttributes.gif");
    action.setImageDescriptor(_imageDescriptor);
  }
  
  public String getPreferenceKey() {
    return FilterAttributesOutlineContribution.PREFERENCE_KEY;
  }
  
  protected boolean apply(final IOutlineNode outlineNode) {
    if (outlineNode instanceof EStructuralFeatureNode) {
      return _apply((EStructuralFeatureNode)outlineNode);
    } else if (outlineNode != null) {
      return _apply(outlineNode);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(outlineNode).toString());
    }
  }
}
