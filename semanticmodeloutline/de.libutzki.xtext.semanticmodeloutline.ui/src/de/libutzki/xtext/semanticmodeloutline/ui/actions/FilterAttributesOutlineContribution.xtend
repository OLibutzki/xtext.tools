package de.libutzki.xtext.semanticmodeloutline.ui.actions

import com.google.inject.Inject
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.jface.action.Action
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class FilterAttributesOutlineContribution extends AbstractFilterOutlineContribution {
	
	public static final String PREFERENCE_KEY = "ui.outline.filterAttributes";
	
	@Inject
	IImageDescriptorHelper imageHelper
	
	def dispatch protected apply(IOutlineNode outlineNode) {
		true
	}
	
	def dispatch protected apply(EStructuralFeatureNode outlineNode) {
		!(outlineNode.EStructuralFeature instanceof EAttribute)
	}
	
	override protected configureAction(Action action) {
		action.setText("Hide attributes");
		action.setDescription("Hide attributes");
		action.setToolTipText("Hide attributes");
		action.setImageDescriptor(imageHelper.getImageDescriptor("filterAttributes.gif"));
	}
	
	override getPreferenceKey() {
		PREFERENCE_KEY
	}
	
}