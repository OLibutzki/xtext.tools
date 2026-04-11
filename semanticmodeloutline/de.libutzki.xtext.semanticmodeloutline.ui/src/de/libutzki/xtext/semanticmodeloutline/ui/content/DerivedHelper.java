package de.libutzki.xtext.semanticmodeloutline.ui.content;

import org.eclipse.emf.ecore.EObject;

public class DerivedHelper {
  public boolean isDerived(EObject eObject) {
    EObject currentElement = eObject;
    while (currentElement.eContainer() != null) {
      currentElement = currentElement.eContainer();
    }
    return currentElement.eResource().getContents().indexOf(currentElement) > 0;
  }
}
