package de.libutzki.xtext.nodemodeloutline.ui.page;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;

public class NodeModelOutlinePage extends OutlinePage {

	private static final String MENU_ID = "de.libutzki.xtext.nodemodeloutline.ui";
	
	private static final String CONTEXT_MENU_ID = "NodeModelOutlinePageContextMenu";
	
	@Override
	protected void configureContextMenu() {
		MenuManager menuManager = new MenuManager(CONTEXT_MENU_ID, CONTEXT_MENU_ID);
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menuManager.setRemoveAllWhenShown(true);
		
		Menu contextMenu = menuManager.createContextMenu(getTreeViewer().getTree());
		getTreeViewer().getTree().setMenu(contextMenu);
		getSite().registerContextMenu(MENU_ID, menuManager, getTreeViewer());
	}
}
