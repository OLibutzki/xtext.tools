package de.libutzki.xtext.semanticmodeloutline.ui.content;

import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.DerivedHelper;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider.Registry;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class SemanticModelOutlineLabelProvider extends DeclarativeLabelProvider {
  @Inject
  private Registry reg;
  
  @Inject
  @Extension
  private DerivedHelper _derivedHelper;
  
  public Object text(final EObject object) {
    Object _xblockexpression = null;
    {
      Resource _eResource = object.eResource();
      URI _uRI = _eResource.getURI();
      IResourceServiceProvider _resourceServiceProvider = this.reg.getResourceServiceProvider(_uRI);
      final ILabelProvider langLabelProvider = _resourceServiceProvider.<ILabelProvider>get(ILabelProvider.class);
      final String label = langLabelProvider.getText(object);
      Object _label = this.getLabel(object, label);
      _xblockexpression = (_label);
    }
    return _xblockexpression;
  }
  
  public Object text(final JvmGenericType jvmType) {
    Object _xblockexpression = null;
    {
      final String label = jvmType.getQualifiedName();
      Object _label = this.getLabel(jvmType, label);
      _xblockexpression = (_label);
    }
    return _xblockexpression;
  }
  
  protected Object getLabel(final EObject eObject, final String label) {
    Object _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      EClass _eClass = eObject.eClass();
      String _name = _eClass.getName();
      _builder.append(_name, "");
      _builder.append("]");
      final String typePrefix = _builder.toString();
      Object _xifexpression = null;
      boolean _isDerived = this._derivedHelper.isDerived(eObject);
      if (_isDerived) {
        StyledString _xblockexpression_1 = null;
        {
          final Styler _function = new Styler() {
            @Override
            public void applyStyles(final TextStyle it) {
              FontRegistry _fontRegistry = JFaceResources.getFontRegistry();
              Font _italic = _fontRegistry.getItalic(JFaceResources.DEFAULT_FONT);
              it.font = _italic;
            }
          };
          StyledString _styledString = new StyledString(label, _function);
          final StyledString labelStyledString = _styledString;
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append(typePrefix, "");
          _builder_1.append(" ");
          StyledString _styledString_1 = new StyledString(_builder_1.toString());
          StyledString _append = _styledString_1.append(labelStyledString);
          _xblockexpression_1 = (_append);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append(typePrefix, "");
        _builder_1.append(" ");
        _builder_1.append(label, "");
        String _string = _builder_1.toString();
        _xifexpression = _string;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public String image(final EObject eObject) {
    String _xifexpression = null;
    boolean _isDerived = this._derivedHelper.isDerived(eObject);
    if (_isDerived) {
      _xifexpression = "derivedEClass.gif";
    } else {
      _xifexpression = "eClass.gif";
    }
    return _xifexpression;
  }
}
