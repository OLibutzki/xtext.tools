package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

public class FilterAttributesOutlineContribution extends AbstractFilterOutlineContribution {
  public static final String PREFERENCE_KEY = "ui.outline.filterAttributes";

  @Inject
  private IImageHelper.IImageDescriptorHelper imageHelper;

  @Override
  protected boolean apply(IOutlineNode outlineNode) {
    if (!(outlineNode instanceof EStructuralFeatureNode)) {
      return true;
    }
    return !(((EStructuralFeatureNode) outlineNode).getEStructuralFeature() instanceof EAttribute);
  }

  @Override
  protected void configureAction(Action action) {
    action.setText("Hide attributes");
    action.setDescription("Hide attributes");
    action.setToolTipText("Hide attributes");
    action.setImageDescriptor(imageHelper.getImageDescriptor("filterAttributes.gif"));
  }

  @Override
  public String getPreferenceKey() {
    return PREFERENCE_KEY;
  }
}
