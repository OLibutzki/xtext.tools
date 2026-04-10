package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;

public class FilterDerivedElementsOutlineContribution extends AbstractFilterOutlineContribution {
  public static final String PREFERENCE_KEY = "ui.outline.filterDerivedElements";

  @Inject
  private IImageHelper.IImageDescriptorHelper imageHelper;

  @Override
  protected boolean apply(IOutlineNode outlineNode) {
    IOutlineNode parent = outlineNode.getParent();
    return !(parent instanceof DocumentRootNode) || parent.getChildren().indexOf(outlineNode) == 0;
  }

  @Override
  protected void configureAction(Action action) {
    action.setText("Hide derived elements");
    action.setDescription("Hide derived elements");
    action.setToolTipText("Hide derived elements");
    action.setImageDescriptor(imageHelper.getImageDescriptor("filterDerivedEClass.gif"));
  }

  @Override
  public String getPreferenceKey() {
    return PREFERENCE_KEY;
  }
}
