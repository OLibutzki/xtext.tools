package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;

@SuppressWarnings("all")
public class CrossReferenceNode extends EStructuralFeatureNode {
  private URI _targetURI;
  
  public URI getTargetURI() {
    return this._targetURI;
  }
  
  public void setTargetURI(final URI targetURI) {
    this._targetURI = targetURI;
  }
  
  public CrossReferenceNode(final EObject owner, final EStructuralFeature feature, final IOutlineNode parent, final Image image, final Object text, final URI targetURI) {
    super(owner, feature, parent, image, text, true);
    this._targetURI = targetURI;
  }
  
  /**
   * @since 2.4
   */
  public CrossReferenceNode(final EObject owner, final EStructuralFeature feature, final IOutlineNode parent, final ImageDescriptor imageDescriptor, final Object text, final URI targetURI) {
    super(owner, feature, parent, imageDescriptor, text, true);
    this._targetURI = targetURI;
  }
}
