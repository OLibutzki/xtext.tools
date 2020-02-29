package de.libutzki.xtext.semanticmodeloutline.ui.actions

import com.google.inject.Inject
import de.libutzki.xtext.semanticmodeloutline.ui.content.UriNode
import org.eclipse.jface.action.Action
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class FilterUriOutlineContribution extends AbstractFilterOutlineContribution {
	
	public static final String PREFERENCE_KEY = "ui.outline.filterUri";
	
	@Inject
	IImageDescriptorHelper imageHelper
	
	def dispatch protected apply(IOutlineNode outlineNode) {
		true
	}
	
	def dispatch protected apply(UriNode outlineNode) {
		false
	}
	
	override protected configureAction(Action action) {
		action.setText("Hide URI");
		action.setDescription("Hide URI");
		action.setToolTipText("Hide URI");
		action.setImageDescriptor(imageHelper.getImageDescriptor("filterUri.gif"));
	}
	
	override getPreferenceKey() {
		PREFERENCE_KEY
	}
	
}