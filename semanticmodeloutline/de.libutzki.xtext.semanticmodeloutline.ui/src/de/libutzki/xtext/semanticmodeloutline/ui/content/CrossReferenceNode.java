package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

public class CrossReferenceNode extends EStructuralFeatureNode {
  private URI targetURI;

  public CrossReferenceNode(
      EObject owner,
      EStructuralFeature feature,
      IOutlineNode parent,
      Image image,
      Object text,
      URI targetURI) {
    super(owner, feature, parent, image, text, true);
    this.targetURI = targetURI;
  }

  public URI getTargetURI() {
    return targetURI;
  }

  public void setTargetURI(URI targetURI) {
    this.targetURI = targetURI;
  }
}
