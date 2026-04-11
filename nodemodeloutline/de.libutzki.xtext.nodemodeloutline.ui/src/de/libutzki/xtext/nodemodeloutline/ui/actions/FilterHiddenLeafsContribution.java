package de.libutzki.xtext.nodemodeloutline.ui.actions;

import com.google.inject.Inject;
import de.libutzki.xtext.nodemodeloutline.ui.content.NodeOutlineNode;
import org.eclipse.jface.action.Action;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution;

public class FilterHiddenLeafsContribution extends AbstractFilterOutlineContribution {
  public static final String PREFERENCE_KEY = "ui.outline.filterNodes";

  @Inject
  private IImageHelper.IImageDescriptorHelper imageHelper;

  @Override
  protected boolean apply(IOutlineNode outlineNode) {
    if (!(outlineNode instanceof NodeOutlineNode)) {
      return true;
    }
    return !(((NodeOutlineNode) outlineNode).getNode() instanceof HiddenLeafNode);
  }

  @Override
  protected void configureAction(Action action) {
    action.setText("Hide hidden nodes");
    action.setDescription("Hide hidden nodes");
    action.setToolTipText("Hide hidden nodes");
    action.setImageDescriptor(imageHelper.getImageDescriptor("filterHiddenNode.png"));
  }

  @Override
  public String getPreferenceKey() {
    return PREFERENCE_KEY;
  }
}
