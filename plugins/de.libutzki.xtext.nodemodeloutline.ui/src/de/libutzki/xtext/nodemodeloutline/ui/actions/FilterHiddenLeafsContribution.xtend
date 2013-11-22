package de.libutzki.xtext.nodemodeloutline.ui.actions

import com.google.inject.Inject
import org.eclipse.jface.action.Action
import org.eclipse.xtext.nodemodel.impl.HiddenLeafNode
import org.eclipse.xtext.ui.IImageHelper.IImageDescriptorHelper
import org.eclipse.xtext.ui.editor.outline.IOutlineNode
import org.eclipse.xtext.ui.editor.outline.actions.AbstractFilterOutlineContribution

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
class FilterHiddenLeafsContribution extends AbstractFilterOutlineContribution {
	
	public static final String PREFERENCE_KEY = "ui.outline.filterNodes";
	
	@Inject
	IImageDescriptorHelper imageHelper
	
	def dispatch protected apply(IOutlineNode outlineNode) {
		true
	}
	
	def dispatch protected apply(de.libutzki.xtext.nodemodeloutline.ui.content.NodeOutlineNode outlineNode) {
		!(outlineNode.node instanceof HiddenLeafNode)
	}
	
	override protected configureAction(Action action) {
		action.setText("Hide hidden nodes");
		action.setDescription("Hide hidden nodes");
		action.setToolTipText("Hide hidden nodes");
		action.setImageDescriptor(imageHelper.getImageDescriptor("hiddennode.png"));
	}
	
	override getPreferenceKey() {
		PREFERENCE_KEY
	}
	
}