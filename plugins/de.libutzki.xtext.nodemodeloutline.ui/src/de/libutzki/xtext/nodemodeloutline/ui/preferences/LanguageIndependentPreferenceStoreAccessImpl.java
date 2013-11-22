package de.libutzki.xtext.nodemodeloutline.ui.preferences;

import org.eclipse.xtext.ui.editor.preferences.PreferenceStoreAccessImpl;

import com.google.inject.Inject;

/**
 * @author Oliver Libutzki <oliver@libutzki.de>
 *
 */
public class LanguageIndependentPreferenceStoreAccessImpl extends
		PreferenceStoreAccessImpl {

	@Override
	@Inject(optional=true)
	public void setLanguageNameAsQualifier(String languageName) {
		// do nothing
	}
	
	@Override
	protected String getQualifier() {
		return "nodemodeloutline";
	}
}
