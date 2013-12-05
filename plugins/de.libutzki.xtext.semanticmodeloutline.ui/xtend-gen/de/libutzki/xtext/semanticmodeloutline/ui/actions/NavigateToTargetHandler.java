package de.libutzki.xtext.semanticmodeloutline.ui.actions;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.CrossReferenceNode;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

@SuppressWarnings("all")
public class NavigateToTargetHandler extends AbstractHandler {
  @Inject
  private IURIEditorOpener editorOpener;
  
  public Object execute(final ExecutionEvent event) throws ExecutionException {
    Object _xblockexpression = null;
    {
      final CrossReferenceNode node = this.getCrossReferenceNode(event);
      boolean _notEquals = (!Objects.equal(node, null));
      if (_notEquals) {
        URI _targetURI = node.getTargetURI();
        this.editorOpener.open(_targetURI, true);
      }
      _xblockexpression = (null);
    }
    return _xblockexpression;
  }
  
  private CrossReferenceNode getCrossReferenceNode(final ExecutionEvent event) {
    CrossReferenceNode _xblockexpression = null;
    {
      final ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
      CrossReferenceNode _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (currentSelection instanceof IStructuredSelection) {
          final IStructuredSelection _iStructuredSelection = (IStructuredSelection)currentSelection;
          _matched=true;
          Object _firstElement = _iStructuredSelection.getFirstElement();
          _switchResult = ((CrossReferenceNode) _firstElement);
        }
      }
      _xblockexpression = (_switchResult);
    }
    return _xblockexpression;
  }
}
