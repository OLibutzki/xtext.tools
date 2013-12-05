package de.libutzki.xtext.semanticmodeloutline.ui.content

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper
import org.eclipse.xtext.common.types.JvmGenericType

class SemanticModelOutlineLabelProvider extends DeclarativeLabelProvider {
	
	@Inject IResourceServiceProvider.Registry reg
	
	//@Inject IImageDescriptorHelper imageDescriptorHelper
	
	def String text(EObject object) {
		val langLabelProvider = reg.getResourceServiceProvider(object.eResource.URI).get(ILabelProvider)
		val label = langLabelProvider.getText(object)
		'''[«object.eClass.name»] «label»'''
	}
	
	def String text(JvmGenericType jvmType) {
		val label = jvmType.qualifiedName
		'''[«jvmType.eClass.name»] «label»'''
	}
	
	def String image(EObject eObject) {
		"eClass.gif"
	}
	
	
	
}