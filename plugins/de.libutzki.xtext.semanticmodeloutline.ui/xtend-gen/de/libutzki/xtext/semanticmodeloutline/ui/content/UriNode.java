package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

@SuppressWarnings("all")
public class UriNode extends EObjectNode {
  public UriNode(final EObject eObject, final IOutlineNode parent, final Image image) {
    super(eObject, parent, image, null, true);
  }
  
  public UriNode(final EObject eObject, final IOutlineNode parent, final ImageDescriptor imageDescriptor) {
    super(eObject, parent, imageDescriptor, null, true);
  }
  
  public Object getText() {
    URI _eObjectURI = this.getEObjectURI();
    String _string = _eObjectURI.toString();
    return _string;
  }
}
