package kr.co.rtst.autosar.ap4x.ide.perspective;

public interface PerspectiveConstants {

	String LEFT = "left"; //$NON-NLS-1$
	String TOP_LEFT = "topLeft"; //$NON-NLS-1$
	String BOTTOM_RIGHT = "bottomRight"; //$NON-NLS-1$

	/***************************
	 * --- Perspective IDs --- *
	 ***************************/

	/**
	 * The id for the Artop perspective.
	 */
	String ID_ARTOP_PERSPECTIVE = "org.artop.aal.examples.common.ui.perspectives.artop"; //$NON-NLS-1$

	/**
	 * The id for the Eclipse Resource perspective.
	 */
	String ID_RESOURCE_PERSPECTIVE = "org.eclipse.ui.resourcePerspective"; //$NON-NLS-1$

	/*********************
	 * --- Views IDs --- *
	 *********************/

	/**
	 * The view id for the Eclipse Problems view.
	 */
	String ID_PROBLEMS_VIEW = "org.eclipse.ui.views.ProblemView";//$NON-NLS-1$

	/**
	 * The view id for the Eclipse Error Log view.
	 */
	String ID_ERROR_LOG_VIEW = "org.eclipse.pde.runtime.LogView"; //$NON-NLS-1$

	/**
	 * The view id for the Eclipse Console view.
	 */
	String ID_CONSOLE_VIEW = "org.eclipse.ui.console.ConsoleView"; //$NON-NLS-1$

	/**
	 * The view id for the Artop AUTOSAR Explorer view.
	 */
	String ID_AUTOSAR_EXPLORER = "org.artop.aal.examples.explorer.views.autosarExplorer"; //$NON-NLS-1$

	/**
	 * The view id for the Artop Validation view.
	 */
	String ID_VALIDATION_VIEW = "org.artop.aal.examples.validation.ui.views.validation"; //$NON-NLS-1$

	/***********************
	 * --- Wizards IDs --- *
	 ***********************/

	/**
	 * The wizard id for the Artop new AUTOSAR project wizard.
	 */
	String ID_AUTOSAR_NEW_PROJECT = "org.artop.aal.examples.common.ui.newWizards.autosarProject"; //$NON-NLS-1$

	/**
	 * The wizard id for the Artop new AUTOSAR file wizard.
	 */
	String ID_AUTOSAR_NEW_FILE = "org.artop.aal.examples.common.ui.newWizards.autosarFile"; //$NON-NLS-1$

	/**
	 * The wizard id for the Artop new linked file wizard.
	 */
	String ID_NEW_LINKED_FILE = "org.artop.aal.examples.common.ui.newWizards.linkedFile";//$NON-NLS-1$

	/**
	 * The wizard id for the Artop new linked folder wizard.
	 */
	String ID_NEW_LINKED_FOLDER = "org.artop.aal.examples.common.ui.newWizards.linkedFolder";//$NON-NLS-1$

	/**
	 * The wizard id for the Eclipse new file wizard.
	 */
	String ID_ECLIPSE_NEW_FILE = "org.eclipse.ui.wizards.new.file"; //$NON-NLS-1$

	/**
	 * The wizard id for the Eclipse new folder wizard.
	 */
	String ID_ECLIPSE_NEW_FOLDER = "org.eclipse.ui.wizards.new.folder"; //$NON-NLS-1$

	/**************************
	 * --- ActionSets IDs --- *
	 **************************/
	String ID_TEAM_ACTIONSET = "org.eclipse.team.ui.actionSet";//$NON-NLS-1$
	String ID_LAUNCH_ACTIONSET = "org.eclipse.debug.ui.launchActionSet";//$NON-NLS-1$

	/**************************
	 * --- Preference IDs --- *
	 **************************/
	String ID_AUTOSAR_PREFERENCE_PAGE = "org.artop.aal.workspace.ui.preferences.autosar"; //$NON-NLS-1$
}
