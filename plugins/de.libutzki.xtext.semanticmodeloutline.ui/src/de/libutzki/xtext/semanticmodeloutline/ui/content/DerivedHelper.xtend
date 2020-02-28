package de.libutzki.xtext.semanticmodeloutline.ui.content

import org.eclipse.emf.ecore.EObject

class DerivedHelper {
	def isDerived(EObject eObject) {
		var currentElement = eObject
		while (currentElement.eContainer !== null) {
			currentElement = currentElement.eContainer
		}
		currentElement.eResource.contents.indexOf(currentElement) > 0
	}
}