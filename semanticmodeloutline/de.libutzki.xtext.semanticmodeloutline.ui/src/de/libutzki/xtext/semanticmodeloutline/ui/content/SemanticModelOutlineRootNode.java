package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

public class SemanticModelOutlineRootNode extends DocumentRootNode {
  public SemanticModelOutlineRootNode(
      Image image, Object text, IXtextDocument document, IOutlineTreeStructureProvider treeProvider) {
    super(image, text, document, treeProvider);
  }

  @Override
  public <T> T readOnly(final IUnitOfWork<T, EObject> work) {
    return getDocument().readOnly(new IUnitOfWork<T, XtextResource>() {
      @Override
      public T exec(XtextResource resource) throws Exception {
        if (resource != null && resource.getContents() != null && !resource.getContents().isEmpty()) {
          for (EObject object : resource.getContents()) {
            work.exec(object);
          }
        }
        return null;
      }
    });
  }
}
