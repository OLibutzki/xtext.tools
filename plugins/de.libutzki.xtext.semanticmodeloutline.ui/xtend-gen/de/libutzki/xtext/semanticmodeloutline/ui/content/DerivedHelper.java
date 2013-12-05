package de.libutzki.xtext.semanticmodeloutline.ui.content;

import com.google.common.base.Objects;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

@SuppressWarnings("all")
public class DerivedHelper {
  public boolean isDerived(final EObject eObject) {
    boolean _xblockexpression = false;
    {
      EObject currentElement = eObject;
      EObject _eContainer = currentElement.eContainer();
      boolean _notEquals = (!Objects.equal(_eContainer, null));
      boolean _while = _notEquals;
      while (_while) {
        EObject _eContainer_1 = currentElement.eContainer();
        currentElement = _eContainer_1;
        EObject _eContainer_2 = currentElement.eContainer();
        boolean _notEquals_1 = (!Objects.equal(_eContainer_2, null));
        _while = _notEquals_1;
      }
      Resource _eResource = currentElement.eResource();
      EList<EObject> _contents = _eResource.getContents();
      int _indexOf = _contents.indexOf(currentElement);
      boolean _greaterThan = (_indexOf > 0);
      _xblockexpression = (_greaterThan);
    }
    return _xblockexpression;
  }
}
