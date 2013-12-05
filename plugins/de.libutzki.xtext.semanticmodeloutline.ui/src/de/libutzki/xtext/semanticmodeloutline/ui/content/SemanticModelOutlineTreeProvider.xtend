package de.libutzki.xtext.semanticmodeloutline.ui.content

import com.google.inject.Inject
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EEnum
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.swt.graphics.Image
import org.eclipse.xtext.ui.IImageHelper
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider

class SemanticModelOutlineTreeProvider extends DefaultOutlineTreeProvider {
	
	private static Logger logger = Logger.getLogger(SemanticModelOutlineTreeProvider)
	
	@Inject
	IImageHelper imageHelper
	
	override _createChildren(IOutlineNode parentNode, EObject modelElement) {
		modelElement.eClass.EAllStructuralFeatures.forEach[ feature |
			if (modelElement.eIsSet(feature)) {
				switch feature {
					EAttribute : createAttributeNode(parentNode, modelElement, feature)
					EReference case feature.containment  : createContainmentNode(parentNode, modelElement, feature)
					EReference case !feature.containment  : createReferenceNode(parentNode, modelElement, feature)
					default : logger.error('''Unexpected type in createChildren: «feature.class.name»''') 
				}
			}
		]
	}
	
	def protected createAttributeNode(IOutlineNode parentNode, EObject modelElement, EAttribute eAttribute) {
		val object = modelElement.eGet(eAttribute)
		val eType = eAttribute.EAttributeType
		val value = 
			switch eType {
				EEnum : {
					object.toString
				}
				EDataType : {
					object.toString
				}
			}
		val String label = '''«eAttribute.name»«IF !value.nullOrEmpty» = «value»«ENDIF»'''
		
		createEStructuralFeatureNode(parentNode, modelElement, eAttribute, imageHelper.getImage("attribute.gif"), label, true)
	}
	
	
	def protected createContainmentNode(IOutlineNode parentNode, EObject modelElement, EReference eReference) {
		
		val object = modelElement.eGet(eReference)

			if (eReference.many) {
				val listSize = (object as List<?>).size
				createEStructuralFeatureNode(parentNode, modelElement, eReference, imageHelper.getImage("containment.gif"), '''«eReference.name» («listSize»)'''.toString, false)
			} else {
				val label = labelProvider.getText(object)
				createEObjectNode(parentNode, object as EObject, imageHelper.getImage("containment.gif"), '''«eReference.name» = «label»'''.toString, false)
			}


	}
	
	def protected createReferenceNode(IOutlineNode parentNode, EObject modelElement, EReference eReference) {
		val object = modelElement.eGet(eReference)
			if (eReference.many) {
				val eObjectList = (object as List<?>).filter(EObject)
				val listSize = eObjectList.size
				val parent = createEStructuralFeatureNode(parentNode, modelElement, eReference, imageHelper.getImage("reference.gif"), '''«eReference.name» («listSize»)'''.toString, false)
				eObjectList.forEach[
					val label = labelProvider.getText(it)
					createCrossReferenceNode(parent, modelElement, eReference, null, '''-> «label»'''.toString, it)
				]
			} else {
				val label = labelProvider.getText(object)
				createCrossReferenceNode(parentNode, modelElement, eReference, imageHelper.getImage("reference.gif"), '''«eReference.name» -> «label»'''.toString, object as EObject)
				// createEObjectNode(parentNode, object as EObject, null, '''«eReference.name» = «label»'''.toString, false)
			}	
	}
	
	
	def protected CrossReferenceNode createCrossReferenceNode(IOutlineNode parentNode, EObject owner,
			EStructuralFeature feature, Image image, Object text, EObject targetObject) {
		val isFeatureSet = owner.eIsSet(feature)
		val targetURI = EcoreUtil.getURI(targetObject)
		val crossReferenceNode = new CrossReferenceNode(owner, feature, parentNode, image, text, targetURI)
		if (isFeatureSet) {
			val region = 
				if (feature.many) {
					val collection = (owner.eGet(feature) as List<?>)
					val index = collection.indexOf(targetObject)
					locationInFileProvider.getFullTextRegion(owner, feature, index)
				} else {
					locationInFileProvider.getFullTextRegion(owner, feature, 0)
				}
			crossReferenceNode.textRegion = region
		}
		crossReferenceNode
	}
	
	override protected _isLeaf(EObject modelElement) {
		false
	}
}