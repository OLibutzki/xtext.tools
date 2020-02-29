package de.libutzki.xtext.semanticmodeloutline.ui.content

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.jface.resource.JFaceResources
import org.eclipse.jface.viewers.ILabelProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.resource.IResourceServiceProvider
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider

class SemanticModelOutlineLabelProvider extends DeclarativeLabelProvider {
	
	@Inject IResourceServiceProvider.Registry reg
	@Inject
	extension DerivedHelper
	
	//@Inject IImageDescriptorHelper imageDescriptorHelper
	
	def Object text(EObject object) {
		val langLabelProvider = reg.getResourceServiceProvider(object.eResource.URI).get(ILabelProvider)
		val label = langLabelProvider.getText(object)
		getLabel(object, label)
	}
	
	def Object text(JvmGenericType jvmType) {
		val label = jvmType.qualifiedName
		getLabel(jvmType, label)
	}
	
	def protected getLabel(EObject eObject, String label) {
		val typePrefix = '''[«eObject.eClass.name»]'''
		if (eObject.derived) {
			val labelStyledString = new StyledString(label, [it.font = JFaceResources.getFontRegistry().getItalic(JFaceResources.DEFAULT_FONT)])
			new StyledString('''«typePrefix» ''').append(labelStyledString)
		} else {
			'''«typePrefix» «label»'''.toString
		}

	}
	
	def String image(EObject eObject) {
		if (eObject.derived) {
			"derivedEClass.gif"
		} else {		
			"eClass.gif"
		}
	}
	
	
	
}