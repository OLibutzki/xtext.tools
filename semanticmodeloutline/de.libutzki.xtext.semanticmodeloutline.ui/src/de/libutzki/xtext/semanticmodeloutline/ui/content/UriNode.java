package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;

public class UriNode extends EObjectNode {
  public UriNode(EObject eObject, IOutlineNode parent, Image image) {
    super(eObject, parent, image, null, true);
  }

  @Override
  public Object getText() {
    return getEObjectURI().toString();
  }
}
