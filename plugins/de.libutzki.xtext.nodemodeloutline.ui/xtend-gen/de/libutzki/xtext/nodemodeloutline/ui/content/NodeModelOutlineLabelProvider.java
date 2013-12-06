package de.libutzki.xtext.nodemodeloutline.ui.content;

import com.google.common.base.Objects;
import java.util.Arrays;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TypeRef;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 */
@SuppressWarnings("all")
public class NodeModelOutlineLabelProvider extends DeclarativeLabelProvider {
  private Color hiddenLeafColor;
  
  public StyledString text(final HiddenLeafNode hiddenLeafNode) {
    EObject _grammarElement = hiddenLeafNode.getGrammarElement();
    String _text = this.text(_grammarElement);
    final Styler _function = new Styler() {
      @Override
      public void applyStyles(final TextStyle it) {
        Color _hiddenLeafColor = NodeModelOutlineLabelProvider.this.getHiddenLeafColor();
        it.foreground = _hiddenLeafColor;
      }
    };
    StyledString _styledString = new StyledString(_text, _function);
    return _styledString;
  }
  
  private Color getHiddenLeafColor() {
    Color _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.hiddenLeafColor, null);
      if (_equals) {
        RGB _rGB = new RGB(125, 125, 125);
        Color _colorFromRGB = EditorUtils.colorFromRGB(_rGB);
        this.hiddenLeafColor = _colorFromRGB;
      }
      _xblockexpression = (this.hiddenLeafColor);
    }
    return _xblockexpression;
  }
  
  public String text(final INode node) {
    EObject _grammarElement = node.getGrammarElement();
    String _text = this.text(_grammarElement);
    return _text;
  }
  
  public String text(final EObject eObject) {
    String _xblockexpression = null;
    {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("[");
      EClass _eClass = eObject.eClass();
      String _name = _eClass.getName();
      _builder.append(_name, "");
      _builder.append("]");
      final String typePrefix = _builder.toString();
      final String label = this.getLabel(eObject);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append(typePrefix, "");
      _builder_1.append(" ");
      _builder_1.append(label, "");
      _xblockexpression = (_builder_1.toString());
    }
    return _xblockexpression;
  }
  
  private String _getLabel(final AbstractElement element) {
    return "<unnamed>";
  }
  
  private String _getLabel(final Keyword keyword) {
    String _value = keyword.getValue();
    return _value;
  }
  
  private String _getLabel(final CrossReference crossReference) {
    StringConcatenation _builder = new StringConcatenation();
    TypeRef _type = crossReference.getType();
    EClassifier _classifier = _type.getClassifier();
    String _name = _classifier.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  private String _getLabel(final RuleCall ruleCall) {
    StringConcatenation _builder = new StringConcatenation();
    AbstractRule _rule = ruleCall.getRule();
    String _name = _rule.getName();
    _builder.append(_name, "");
    return _builder.toString();
  }
  
  private String _getLabel(final Action action) {
    TypeRef _type = action.getType();
    EClassifier _classifier = _type.getClassifier();
    String _name = _classifier.getName();
    return _name;
  }
  
  private String _getLabel(final AbstractRule rule) {
    String _name = rule.getName();
    return _name;
  }
  
  public String image(final ICompositeNode compositeNode) {
    return "compositenode.png";
  }
  
  public String image(final HiddenLeafNode hiddenLeafNode) {
    return "hiddennode.png";
  }
  
  public String image(final ILeafNode leafNode) {
    return "leafnode.png";
  }
  
  private String getLabel(final EObject action) {
    if (action instanceof Action) {
      return _getLabel((Action)action);
    } else if (action instanceof CrossReference) {
      return _getLabel((CrossReference)action);
    } else if (action instanceof Keyword) {
      return _getLabel((Keyword)action);
    } else if (action instanceof RuleCall) {
      return _getLabel((RuleCall)action);
    } else if (action instanceof AbstractElement) {
      return _getLabel((AbstractElement)action);
    } else if (action instanceof AbstractRule) {
      return _getLabel((AbstractRule)action);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(action).toString());
    }
  }
}
