package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.UriNode;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;

public class FilterUriOutlineContribution extends AbstractFilterOutlineContribution {
  public static final String PREFERENCE_KEY = "ui.outline.filterUri";

  @Inject
  private IImageHelper.IImageDescriptorHelper imageHelper;

  @Override
  protected boolean apply(IOutlineNode outlineNode) {
    return !(outlineNode instanceof UriNode);
  }

  @Override
  protected void configureAction(Action action) {
    action.setText("Hide URI");
    action.setDescription("Hide URI");
    action.setToolTipText("Hide URI");
    action.setImageDescriptor(imageHelper.getImageDescriptor("filterUri.gif"));
  }

  @Override
  public String getPreferenceKey() {
    return PREFERENCE_KEY;
  }
}
