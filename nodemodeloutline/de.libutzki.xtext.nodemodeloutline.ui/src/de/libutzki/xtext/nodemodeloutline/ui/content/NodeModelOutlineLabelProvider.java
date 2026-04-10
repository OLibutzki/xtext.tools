package de.libutzki.xtext.nodemodeloutline.ui.content;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;

public class NodeModelOutlineLabelProvider extends DeclarativeLabelProvider {
  private Color hiddenLeafColor;

  public StyledString text(HiddenLeafNode hiddenLeafNode) {
    return new StyledString(text(hiddenLeafNode.getGrammarElement()), new StyledString.Styler() {
      @Override
      public void applyStyles(TextStyle textStyle) {
        textStyle.foreground = getHiddenLeafColor();
      }
    });
  }

  private Color getHiddenLeafColor() {
    if (hiddenLeafColor == null) {
      hiddenLeafColor = EditorUtils.colorFromRGB(new RGB(125, 125, 125));
    }
    return hiddenLeafColor;
  }

  public String text(INode node) {
    return text(node.getGrammarElement());
  }

  public String text(EObject eObject) {
    return "[" + eObject.eClass().getName() + "] " + getLabel(eObject);
  }

  private String getLabel(EObject eObject) {
    if (eObject instanceof Action) {
      return ((Action) eObject).getType().getClassifier().getName();
    }
    if (eObject instanceof CrossReference) {
      return ((CrossReference) eObject).getType().getClassifier().getName();
    }
    if (eObject instanceof Keyword) {
      return ((Keyword) eObject).getValue();
    }
    if (eObject instanceof RuleCall) {
      return ((RuleCall) eObject).getRule().getName();
    }
    if (eObject instanceof AbstractRule) {
      return ((AbstractRule) eObject).getName();
    }
    if (eObject instanceof AbstractElement) {
      return "<unnamed>";
    }
    throw new IllegalArgumentException("Unhandled parameter type: " + eObject);
  }

  public String image(ICompositeNode compositeNode) {
    return "compositenode.png";
  }

  public String image(HiddenLeafNode hiddenLeafNode) {
    return "hiddennode.png";
  }

  public String image(ILeafNode leafNode) {
    return "leafnode.png";
  }
}
