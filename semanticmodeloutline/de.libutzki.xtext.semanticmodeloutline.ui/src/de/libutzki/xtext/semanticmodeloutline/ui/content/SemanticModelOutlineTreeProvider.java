package de.libutzki.xtext.semanticmodeloutline.ui.content;

import com.google.inject.Inject;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.AbstractOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;

public class SemanticModelOutlineTreeProvider extends DefaultOutlineTreeProvider {
  private static final Logger LOGGER = Logger.getLogger(SemanticModelOutlineTreeProvider.class);

  @Inject
  private IImageHelper imageHelper;

  @Inject
  private IResourceServiceProvider.Registry reg;

  @Inject
  private DerivedHelper derivedHelper;

  @Override
  public IOutlineNode createRoot(IXtextDocument document) {
    SemanticModelOutlineRootNode documentNode = new SemanticModelOutlineRootNode(
        labelProvider.getImage(document),
        labelProvider.getText(document),
        document,
        this);
    documentNode.setTextRegion(new TextRegion(0, document.getLength()));
    return documentNode;
  }

  @Override
  public void _createChildren(IOutlineNode parentNode, EObject modelElement) {
    createUriNode(parentNode, modelElement);
    for (EStructuralFeature feature : modelElement.eClass().getEAllStructuralFeatures()) {
      if (!modelElement.eIsSet(feature)) {
        continue;
      }
      if (feature instanceof EAttribute) {
        createAttributeNode(parentNode, modelElement, (EAttribute) feature);
      } else if (feature instanceof EReference && ((EReference) feature).isContainment()) {
        createContainmentNode(parentNode, modelElement, (EReference) feature);
      } else if (feature instanceof EReference) {
        createReferenceNode(parentNode, modelElement, (EReference) feature);
      } else {
        LOGGER.error("Unexpected type in createChildren: " + feature.getClass().getName());
      }
    }
  }

  protected void createUriNode(IOutlineNode parentNode, EObject modelElement) {
    UriNode uriNode = new UriNode(modelElement, parentNode, imageHelper.getImage("uri.gif"));
    ICompositeNode parserNode = NodeModelUtils.getNode(modelElement);
    if (parserNode != null) {
      uriNode.setTextRegion(new TextRegion(parserNode.getOffset(), parserNode.getLength()));
    }
    if (isLocalElement(parentNode, modelElement)) {
      uriNode.setShortTextRegion(locationInFileProvider.getSignificantTextRegion(modelElement));
    }
  }

  protected EStructuralFeatureNode createAttributeNode(
      IOutlineNode parentNode, EObject modelElement, EAttribute eAttribute) {
    Object object = modelElement.eGet(eAttribute);
    EDataType eType = eAttribute.getEAttributeType();
    String value;
    if (eType instanceof EEnum) {
      value = object.toString();
    } else {
      value = object.toString();
    }
    StringBuilder label = new StringBuilder(eAttribute.getName());
    if (value != null && !value.isEmpty()) {
      label.append(" = ").append(value);
    }
    return createEStructuralFeatureNode(
        parentNode,
        modelElement,
        eAttribute,
        imageHelper.getImage("attribute.gif"),
        label.toString(),
        true);
  }

  protected AbstractOutlineNode createContainmentNode(
      IOutlineNode parentNode, EObject modelElement, EReference eReference) {
    Object object = modelElement.eGet(eReference);
    if (eReference.isMany()) {
      int listSize = ((List<?>) object).size();
      return createEStructuralFeatureNode(
          parentNode,
          modelElement,
          eReference,
          imageHelper.getImage("containment.gif"),
          eReference.getName() + " (" + listSize + ")",
          false);
    }

    String label = labelProvider.getText(object);
    return createEObjectNode(
        parentNode,
        (EObject) object,
        imageHelper.getImage("containment.gif"),
        eReference.getName() + " = " + label,
        false);
  }

  protected CrossReferenceNode createReferenceNode(
      IOutlineNode parentNode, EObject modelElement, EReference eReference) {
    Object object = modelElement.eGet(eReference);
    if (eReference.isMany()) {
      List<?> values = (List<?>) object;
      int listSize = 0;
      for (Object value : values) {
        if (value instanceof EObject) {
          listSize++;
        }
      }
      EStructuralFeatureNode parent = createEStructuralFeatureNode(
          parentNode,
          modelElement,
          eReference,
          imageHelper.getImage("reference.gif"),
          eReference.getName() + " (" + listSize + ")",
          false);
      for (Object value : values) {
        if (value instanceof EObject) {
          EObject target = (EObject) value;
          String label = labelProvider.getText(target);
          createCrossReferenceNode(parent, modelElement, eReference, null, "-> " + label, target);
        }
      }
      return null;
    }

    String label = labelProvider.getText(object);
    return createCrossReferenceNode(
        parentNode,
        modelElement,
        eReference,
        imageHelper.getImage("reference.gif"),
        eReference.getName() + " -> " + label,
        (EObject) object);
  }

  protected CrossReferenceNode createCrossReferenceNode(
      IOutlineNode parentNode,
      EObject owner,
      EStructuralFeature feature,
      Image image,
      Object text,
      EObject targetObject) {
    boolean isFeatureSet = owner.eIsSet(feature);
    URI targetURI = EcoreUtil.getURI(targetObject);
    CrossReferenceNode crossReferenceNode =
        new CrossReferenceNode(owner, feature, parentNode, image, text, targetURI);
    if (isFeatureSet) {
      ITextRegion region;
      if (feature.isMany()) {
        List<?> collection = (List<?>) owner.eGet(feature);
        int index = collection.indexOf(targetObject);
        region = locationInFileProvider.getFullTextRegion(owner, feature, index);
      } else {
        region = locationInFileProvider.getFullTextRegion(owner, feature, 0);
      }
      crossReferenceNode.setTextRegion(region);
    }
    setTextRegionForDerivedElement(parentNode, crossReferenceNode, owner);
    return crossReferenceNode;
  }

  @Override
  protected EObjectNode createEObjectNode(
      IOutlineNode parentNode, EObject modelElement, Image image, Object text, boolean isLeaf) {
    EObjectNode eObjectNode = super.createEObjectNode(parentNode, modelElement, image, text, isLeaf);
    setTextRegionForDerivedElement(parentNode, eObjectNode, modelElement);
    return eObjectNode;
  }

  private void setTextRegionForDerivedElement(
      IOutlineNode parentNode, AbstractOutlineNode currentNode, EObject modelElement) {
    if (!derivedHelper.isDerived(modelElement)) {
      return;
    }
    IJvmModelAssociations modelAssociations = reg
        .getResourceServiceProvider(modelElement.eResource().getURI())
        .get(IJvmModelAssociations.class);
    if (modelAssociations == null) {
      return;
    }
    EObject sourceElement = modelAssociations.getPrimarySourceElement(modelElement);
    if (sourceElement == null) {
      return;
    }
    ICompositeNode parserNode = NodeModelUtils.getNode(sourceElement);
    if (parserNode != null) {
      currentNode.setTextRegion(new TextRegion(parserNode.getOffset(), parserNode.getLength()));
    }
    if (currentNode instanceof EObjectNode && isLocalElement(parentNode, sourceElement)) {
      ((EObjectNode) currentNode)
          .setShortTextRegion(locationInFileProvider.getSignificantTextRegion(sourceElement));
    }
  }

  @Override
  protected boolean _isLeaf(EObject modelElement) {
    return false;
  }
}
