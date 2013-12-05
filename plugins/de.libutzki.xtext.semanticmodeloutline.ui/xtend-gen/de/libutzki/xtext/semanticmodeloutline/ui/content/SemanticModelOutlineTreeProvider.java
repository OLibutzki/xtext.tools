package de.libutzki.xtext.semanticmodeloutline.ui.content;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import de.libutzki.xtext.semanticmodeloutline.ui.content.CrossReferenceNode;
import de.libutzki.xtext.semanticmodeloutline.ui.content.DerivedHelper;
import de.libutzki.xtext.semanticmodeloutline.ui.content.SemanticModelOutlineRootNode;
import de.libutzki.xtext.semanticmodeloutline.ui.content.UriNode;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.IResourceServiceProvider.Registry;
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
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function0;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class SemanticModelOutlineTreeProvider extends DefaultOutlineTreeProvider {
  private static Logger logger = new Function0<Logger>() {
    public Logger apply() {
      Logger _logger = Logger.getLogger(SemanticModelOutlineTreeProvider.class);
      return _logger;
    }
  }.apply();
  
  @Inject
  private IImageHelper imageHelper;
  
  @Inject
  private Registry reg;
  
  @Inject
  @Extension
  private DerivedHelper _derivedHelper;
  
  public IOutlineNode createRoot(final IXtextDocument document) {
    Image _image = this.labelProvider.getImage(document);
    String _text = this.labelProvider.getText(document);
    SemanticModelOutlineRootNode _semanticModelOutlineRootNode = new SemanticModelOutlineRootNode(_image, _text, document, this);
    final SemanticModelOutlineRootNode documentNode = _semanticModelOutlineRootNode;
    int _length = document.getLength();
    TextRegion _textRegion = new TextRegion(0, _length);
    documentNode.setTextRegion(_textRegion);
    return documentNode;
  }
  
  public void _createChildren(final IOutlineNode parentNode, final EObject modelElement) {
    this.createUriNode(parentNode, modelElement);
    EClass _eClass = modelElement.eClass();
    EList<EStructuralFeature> _eAllStructuralFeatures = _eClass.getEAllStructuralFeatures();
    final Procedure1<EStructuralFeature> _function = new Procedure1<EStructuralFeature>() {
      public void apply(final EStructuralFeature feature) {
        boolean _eIsSet = modelElement.eIsSet(feature);
        if (_eIsSet) {
          boolean _matched = false;
          if (!_matched) {
            if (feature instanceof EAttribute) {
              final EAttribute _eAttribute = (EAttribute)feature;
              _matched=true;
              SemanticModelOutlineTreeProvider.this.createAttributeNode(parentNode, modelElement, _eAttribute);
            }
          }
          if (!_matched) {
            if (feature instanceof EReference) {
              final EReference _eReference = (EReference)feature;
              boolean _isContainment = _eReference.isContainment();
              if (_isContainment) {
                _matched=true;
                SemanticModelOutlineTreeProvider.this.createContainmentNode(parentNode, modelElement, _eReference);
              }
            }
          }
          if (!_matched) {
            if (feature instanceof EReference) {
              final EReference _eReference = (EReference)feature;
              boolean _isContainment = _eReference.isContainment();
              boolean _not = (!_isContainment);
              if (_not) {
                _matched=true;
                SemanticModelOutlineTreeProvider.this.createReferenceNode(parentNode, modelElement, _eReference);
              }
            }
          }
          if (!_matched) {
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("Unexpected type in createChildren: ");
            Class<? extends EStructuralFeature> _class = feature.getClass();
            String _name = _class.getName();
            _builder.append(_name, "");
            SemanticModelOutlineTreeProvider.logger.error(_builder);
          }
        }
      }
    };
    IterableExtensions.<EStructuralFeature>forEach(_eAllStructuralFeatures, _function);
  }
  
  protected void createUriNode(final IOutlineNode parentNode, final EObject modelElement) {
    Image _image = this.imageHelper.getImage("filterUri.gif");
    UriNode _uriNode = new UriNode(modelElement, parentNode, _image);
    final UriNode uriNode = _uriNode;
    final ICompositeNode parserNode = NodeModelUtils.getNode(modelElement);
    boolean _notEquals = (!Objects.equal(parserNode, null));
    if (_notEquals) {
      int _offset = parserNode.getOffset();
      int _length = parserNode.getLength();
      TextRegion _textRegion = new TextRegion(_offset, _length);
      uriNode.setTextRegion(_textRegion);
    }
    boolean _isLocalElement = this.isLocalElement(parentNode, modelElement);
    if (_isLocalElement) {
      ITextRegion _significantTextRegion = this.locationInFileProvider.getSignificantTextRegion(modelElement);
      uriNode.setShortTextRegion(_significantTextRegion);
    }
  }
  
  protected EStructuralFeatureNode createAttributeNode(final IOutlineNode parentNode, final EObject modelElement, final EAttribute eAttribute) {
    EStructuralFeatureNode _xblockexpression = null;
    {
      final Object object = modelElement.eGet(eAttribute);
      final EDataType eType = eAttribute.getEAttributeType();
      String _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (eType instanceof EEnum) {
          final EEnum _eEnum = (EEnum)eType;
          _matched=true;
          String _string = object.toString();
          _switchResult = _string;
        }
      }
      if (!_matched) {
        if (eType instanceof EDataType) {
          final EDataType _eDataType = (EDataType)eType;
          _matched=true;
          String _string = object.toString();
          _switchResult = _string;
        }
      }
      final String value = _switchResult;
      StringConcatenation _builder = new StringConcatenation();
      String _name = eAttribute.getName();
      _builder.append(_name, "");
      {
        boolean _isNullOrEmpty = StringExtensions.isNullOrEmpty(value);
        boolean _not = (!_isNullOrEmpty);
        if (_not) {
          _builder.append(" = ");
          _builder.append(value, "");
        }
      }
      final String label = _builder.toString();
      Image _image = this.imageHelper.getImage("attribute.gif");
      EStructuralFeatureNode _createEStructuralFeatureNode = this.createEStructuralFeatureNode(parentNode, modelElement, eAttribute, _image, label, 
        true);
      _xblockexpression = (_createEStructuralFeatureNode);
    }
    return _xblockexpression;
  }
  
  protected AbstractOutlineNode createContainmentNode(final IOutlineNode parentNode, final EObject modelElement, final EReference eReference) {
    AbstractOutlineNode _xblockexpression = null;
    {
      final Object object = modelElement.eGet(eReference);
      AbstractOutlineNode _xifexpression = null;
      boolean _isMany = eReference.isMany();
      if (_isMany) {
        EStructuralFeatureNode _xblockexpression_1 = null;
        {
          final int listSize = ((List<?>) object).size();
          Image _image = this.imageHelper.getImage("containment.gif");
          StringConcatenation _builder = new StringConcatenation();
          String _name = eReference.getName();
          _builder.append(_name, "");
          _builder.append(" (");
          _builder.append(listSize, "");
          _builder.append(")");
          String _string = _builder.toString();
          EStructuralFeatureNode _createEStructuralFeatureNode = this.createEStructuralFeatureNode(parentNode, modelElement, eReference, _image, _string, false);
          _xblockexpression_1 = (_createEStructuralFeatureNode);
        }
        _xifexpression = _xblockexpression_1;
      } else {
        EObjectNode _xblockexpression_2 = null;
        {
          final String label = this.labelProvider.getText(object);
          Image _image = this.imageHelper.getImage("containment.gif");
          StringConcatenation _builder = new StringConcatenation();
          String _name = eReference.getName();
          _builder.append(_name, "");
          _builder.append(" = ");
          _builder.append(label, "");
          String _string = _builder.toString();
          EObjectNode _createEObjectNode = this.createEObjectNode(parentNode, ((EObject) object), _image, _string, false);
          _xblockexpression_2 = (_createEObjectNode);
        }
        _xifexpression = _xblockexpression_2;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  protected CrossReferenceNode createReferenceNode(final IOutlineNode parentNode, final EObject modelElement, final EReference eReference) {
    CrossReferenceNode _xblockexpression = null;
    {
      final Object object = modelElement.eGet(eReference);
      CrossReferenceNode _xifexpression = null;
      boolean _isMany = eReference.isMany();
      if (_isMany) {
        final Iterable<EObject> eObjectList = Iterables.<EObject>filter(((List<?>) object), EObject.class);
        final int listSize = IterableExtensions.size(eObjectList);
        Image _image = this.imageHelper.getImage("reference.gif");
        StringConcatenation _builder = new StringConcatenation();
        String _name = eReference.getName();
        _builder.append(_name, "");
        _builder.append(" (");
        _builder.append(listSize, "");
        _builder.append(")");
        String _string = _builder.toString();
        final EStructuralFeatureNode parent = this.createEStructuralFeatureNode(parentNode, modelElement, eReference, _image, _string, false);
        final Procedure1<EObject> _function = new Procedure1<EObject>() {
          public void apply(final EObject it) {
            final String label = SemanticModelOutlineTreeProvider.this.labelProvider.getText(it);
            StringConcatenation _builder = new StringConcatenation();
            _builder.append("-> ");
            _builder.append(label, "");
            String _string = _builder.toString();
            SemanticModelOutlineTreeProvider.this.createCrossReferenceNode(parent, modelElement, eReference, null, _string, it);
          }
        };
        IterableExtensions.<EObject>forEach(eObjectList, _function);
      } else {
        CrossReferenceNode _xblockexpression_1 = null;
        {
          final String label = this.labelProvider.getText(object);
          Image _image_1 = this.imageHelper.getImage("reference.gif");
          StringConcatenation _builder_1 = new StringConcatenation();
          String _name_1 = eReference.getName();
          _builder_1.append(_name_1, "");
          _builder_1.append(" -> ");
          _builder_1.append(label, "");
          String _string_1 = _builder_1.toString();
          CrossReferenceNode _createCrossReferenceNode = this.createCrossReferenceNode(parentNode, modelElement, eReference, _image_1, _string_1, ((EObject) object));
          _xblockexpression_1 = (_createCrossReferenceNode);
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  protected CrossReferenceNode createCrossReferenceNode(final IOutlineNode parentNode, final EObject owner, final EStructuralFeature feature, final Image image, final Object text, final EObject targetObject) {
    CrossReferenceNode _xblockexpression = null;
    {
      final boolean isFeatureSet = owner.eIsSet(feature);
      final URI targetURI = EcoreUtil.getURI(targetObject);
      CrossReferenceNode _crossReferenceNode = new CrossReferenceNode(owner, feature, parentNode, image, text, targetURI);
      final CrossReferenceNode crossReferenceNode = _crossReferenceNode;
      if (isFeatureSet) {
        ITextRegion _xifexpression = null;
        boolean _isMany = feature.isMany();
        if (_isMany) {
          ITextRegion _xblockexpression_1 = null;
          {
            Object _eGet = owner.eGet(feature);
            final List<? extends Object> collection = ((List<?>) _eGet);
            final int index = collection.indexOf(targetObject);
            ITextRegion _fullTextRegion = this.locationInFileProvider.getFullTextRegion(owner, feature, index);
            _xblockexpression_1 = (_fullTextRegion);
          }
          _xifexpression = _xblockexpression_1;
        } else {
          ITextRegion _fullTextRegion = this.locationInFileProvider.getFullTextRegion(owner, feature, 0);
          _xifexpression = _fullTextRegion;
        }
        final ITextRegion region = _xifexpression;
        crossReferenceNode.setTextRegion(region);
      }
      this.setTextRegionForDerivedElement(parentNode, crossReferenceNode, owner);
      _xblockexpression = (crossReferenceNode);
    }
    return _xblockexpression;
  }
  
  protected EObjectNode createEObjectNode(final IOutlineNode parentNode, final EObject modelElement, final Image image, final Object text, final boolean isLeaf) {
    EObjectNode _xblockexpression = null;
    {
      final EObjectNode eObjectNode = super.createEObjectNode(parentNode, modelElement, image, text, isLeaf);
      this.setTextRegionForDerivedElement(parentNode, eObjectNode, modelElement);
      _xblockexpression = (eObjectNode);
    }
    return _xblockexpression;
  }
  
  private void setTextRegionForDerivedElement(final IOutlineNode parentNode, final AbstractOutlineNode currentNode, final EObject modelElement) {
    boolean _isDerived = this._derivedHelper.isDerived(modelElement);
    if (_isDerived) {
      Resource _eResource = modelElement.eResource();
      URI _uRI = _eResource.getURI();
      IResourceServiceProvider _resourceServiceProvider = this.reg.getResourceServiceProvider(_uRI);
      final IJvmModelAssociations modelAssociations = _resourceServiceProvider.<IJvmModelAssociations>get(IJvmModelAssociations.class);
      boolean _notEquals = (!Objects.equal(modelAssociations, null));
      if (_notEquals) {
        final EObject sourceElement = modelAssociations.getPrimarySourceElement(modelElement);
        boolean _notEquals_1 = (!Objects.equal(sourceElement, null));
        if (_notEquals_1) {
          final ICompositeNode parserNode = NodeModelUtils.getNode(sourceElement);
          boolean _notEquals_2 = (!Objects.equal(parserNode, null));
          if (_notEquals_2) {
            int _offset = parserNode.getOffset();
            int _length = parserNode.getLength();
            TextRegion _textRegion = new TextRegion(_offset, _length);
            currentNode.setTextRegion(_textRegion);
          }
          boolean _matched = false;
          if (!_matched) {
            if (currentNode instanceof EObjectNode) {
              final EObjectNode _eObjectNode = (EObjectNode)currentNode;
              boolean _isLocalElement = this.isLocalElement(parentNode, sourceElement);
              if (_isLocalElement) {
                _matched=true;
                ITextRegion _significantTextRegion = this.locationInFileProvider.getSignificantTextRegion(sourceElement);
                _eObjectNode.setShortTextRegion(_significantTextRegion);
              }
            }
          }
        }
      }
    }
  }
  
  protected boolean _isLeaf(final EObject modelElement) {
    return false;
  }
}
