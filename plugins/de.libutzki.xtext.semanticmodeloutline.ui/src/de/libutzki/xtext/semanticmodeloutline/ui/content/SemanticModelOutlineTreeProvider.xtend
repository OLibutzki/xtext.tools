package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EDataType
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EEnum
import java.util.List
import com.google.inject.Inject
import org.eclipse.xtext.ui.IImageHelper

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
				val listSize = (object as List).size
				createEStructuralFeatureNode(parentNode, modelElement, eReference, imageHelper.getImage("containment.gif"), '''«eReference.name» («listSize»)'''.toString, false)
			} else {
				val label = labelProvider.getText(object)
				createEObjectNode(parentNode, object as EObject, imageHelper.getImage("containment.gif"), '''«eReference.name» = «label»'''.toString, false)
			}


	}
	
	def protected createReferenceNode(IOutlineNode parentNode, EObject modelElement, EReference eReference) {
		val object = modelElement.eGet(eReference)
			if (eReference.many) {
				val eObjectList = (object as List).filter(EObject)
				val listSize = eObjectList.size
				val parent = createEStructuralFeatureNode(parentNode, modelElement, eReference, imageHelper.getImage("reference.gif"), '''«eReference.name» («listSize»)'''.toString, false)
				eObjectList.forEach[
					val label = labelProvider.getText(it)
					createEStructuralFeatureNode(parent, modelElement, eReference, null, '''-> «label»'''.toString, true)
				]
			} else {
				val label = labelProvider.getText(object)
				createEStructuralFeatureNode(parentNode, modelElement, eReference, imageHelper.getImage("reference.gif"), '''«eReference.name» -> «label»'''.toString, true)
				// createEObjectNode(parentNode, object as EObject, null, '''«eReference.name» = «label»'''.toString, false)
			}
		
		val value = labelProvider.getText(object)
//		val eType = eReference.EType
//		val value = 
//			switch eType {
//				EDataType : {
//					logger.error('''Cannot determine value for «object.class.name»''') 
//					""
//				}
//				default : {
//					logger.error('''Cannot determine value for «object.class.name»''') 
//					""
//				}
//			}
		val String label = '''«eReference.name»«IF !value.nullOrEmpty» -> «value»«ENDIF»'''
		
		
	}
	
//	def protected void _createChildren(EClassStructuralFeatureNode parentNode, EObject modelElement) {
//		val values = modelElement.eClass.eGet(parentNode.EStructuralFeature)
//		val allFeatures = EcoreUtil2.typeSelect( values as List<?>, EStructuralFeature)
//		allFeatures.forEach[
//			createEStructuralFeatureNode(parentNode, modelElement, it, null, it.name, true)
//		]
		
		
//		Object values = modelElement.eGet(parentNode.getEStructuralFeature());
//		if (values != null) {
//			if (parentNode.getEStructuralFeature().isMany()) {
//				for (EObject value : EcoreUtil2.typeSelect((List<?>) values, EObject.class)) {
//					createNode(parentNode, value);
//				}
//			} else {
//				if (values instanceof EObject)
//					createNode(parentNode, (EObject) values);
//			}
//		}
//	}
	
	override protected _isLeaf(EObject modelElement) {
		false
	}
	
//	def private createAttributesNode(IOutlineNode parentNode, EObject eObject) {
//		new EClassStructuralFeatureNode(eObject, EcorePackage.Literals.ECLASS__EALL_ATTRIBUTES, parentNode, null as Image, "Attributes", false)
//	}
	
	
}