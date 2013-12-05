package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.eclipse.xtext.ui.editor.outline.impl.IOutlineTreeStructureProvider;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class SemanticModelOutlineRootNode extends DocumentRootNode {
  public SemanticModelOutlineRootNode(final Image image, final Object text, final IXtextDocument document, final IOutlineTreeStructureProvider treeProvider) {
    super(image, text, document, treeProvider);
  }
  
  public SemanticModelOutlineRootNode(final ImageDescriptor imageDescriptor, final Object text, final IXtextDocument document, final IOutlineTreeStructureProvider treeProvider) {
    super(imageDescriptor, text, document, treeProvider);
  }
  
  public <T extends Object> T readOnly(final IUnitOfWork<T,EObject> work) {
    IXtextDocument _document = this.getDocument();
    final IUnitOfWork<T,XtextResource> _function = new IUnitOfWork<T,XtextResource>() {
      public T exec(final XtextResource resource) throws Exception {
        T _xblockexpression = null;
        {
          EList<EObject> _contents = null;
          if (resource!=null) {
            _contents=resource.getContents();
          }
          boolean _isNullOrEmpty = IterableExtensions.isNullOrEmpty(_contents);
          boolean _not = (!_isNullOrEmpty);
          if (_not) {
            EList<EObject> _contents_1 = resource.getContents();
            final Procedure1<EObject> _function = new Procedure1<EObject>() {
              public void apply(final EObject it) {
                try {
                  work.exec(it);
                } catch (Throwable _e) {
                  throw Exceptions.sneakyThrow(_e);
                }
              }
            };
            IterableExtensions.<EObject>forEach(_contents_1, _function);
          }
          _xblockexpression = (null);
        }
        return _xblockexpression;
      }
    };
    T _readOnly = _document.<T>readOnly(_function);
    return _readOnly;
  }
}
