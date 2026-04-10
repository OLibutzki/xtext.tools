package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.CrossReferenceNode;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

public class NavigateToTargetHandler extends AbstractHandler {
  @Inject
  private IURIEditorOpener editorOpener;

  @Override
  public Object execute(ExecutionEvent event) throws ExecutionException {
    CrossReferenceNode node = getCrossReferenceNode(event);
    if (node != null) {
      editorOpener.open(node.getTargetURI(), true);
    }
    return null;
  }

  private CrossReferenceNode getCrossReferenceNode(ExecutionEvent event) {
    ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
    if (currentSelection instanceof IStructuredSelection) {
      Object firstElement = ((IStructuredSelection) currentSelection).getFirstElement();
      if (firstElement instanceof CrossReferenceNode) {
        return (CrossReferenceNode) firstElement;
      }
    }
    return null;
  }
}
