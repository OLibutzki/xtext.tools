package de.libutzki.xtext.semanticmodeloutline.ui.content;

import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;

public class SemanticModelOutlineLabelProvider extends DeclarativeLabelProvider {
  @Inject
  private IResourceServiceProvider.Registry reg;

  @Inject
  private DerivedHelper derivedHelper;

  public Object text(EObject object) {
    ILabelProvider langLabelProvider =
        reg.getResourceServiceProvider(object.eResource().getURI()).get(ILabelProvider.class);
    return getLabel(object, langLabelProvider.getText(object));
  }

  public Object text(JvmGenericType jvmType) {
    return getLabel(jvmType, jvmType.getQualifiedName());
  }

  protected Object getLabel(EObject eObject, String label) {
    String typePrefix = "[" + eObject.eClass().getName() + "]";
    if (derivedHelper.isDerived(eObject)) {
      StyledString labelStyledString = new StyledString(label, new StyledString.Styler() {
        @Override
        public void applyStyles(TextStyle textStyle) {
          textStyle.font = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT);
        }
      });
      return new StyledString(typePrefix + " ").append(labelStyledString);
    }
    return typePrefix + " " + label;
  }

  public String image(EObject eObject) {
    return derivedHelper.isDerived(eObject) ? "derivedEClass.gif" : "eClass.gif";
  }
}
