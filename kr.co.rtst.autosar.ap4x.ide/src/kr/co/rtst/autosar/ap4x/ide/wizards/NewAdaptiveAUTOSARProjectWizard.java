package kr.co.rtst.autosar.ap4x.ide.wizards;

import java.net.URI;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.ui.wizards.BasicAutosarProjectWizard;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.sphinx.emf.workspace.jobs.CreateNewModelProjectJob;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import kr.co.rtst.autosar.ap4x.ide.consts.IDEText;
import kr.co.rtst.autosar.ap4x.ide.wizards.job.CreateNewAdaptiveAutosarProjectJob;

public class NewAdaptiveAUTOSARProjectWizard extends BasicAutosarProjectWizard/*BasicNewProjectResourceWizard*/ implements INewWizard {
	
	private final AdaptiveAutosarProjectCreationInfo projectInfo;
	
	private AdaptiveAutosarNewProjectWizardSecondPage page2;
	
	public NewAdaptiveAUTOSARProjectWizard() {
		projectInfo = new AdaptiveAutosarProjectCreationInfo();
	}
	
	@Override
	public String getWindowTitle() {
		return IDEText.WIZARD_NEW_ADAPTIVE_AUTOSAR_PROJECT_TITLE; //super.getWindowTitle();
	}
	
	public AdaptiveAutosarProjectCreationInfo getProjectInfo() {
		return projectInfo;
	}
	
	@Override
	protected WizardNewProjectCreationPage createMainPage() {
		return new AdaptiveAutosarNewProjectWizardMainPage(getProjectInfo());
	}
	
	@Override
	public void addPages() {
		mainPage = createMainPage();
        Assert.isNotNull(mainPage);
        addPage(mainPage);
        
		page2 = new AdaptiveAutosarNewProjectWizardSecondPage(getProjectInfo());
		Assert.isNotNull(page2);
		addPage(page2);
	}
	
	@Override
	protected CreateNewModelProjectJob<AutosarReleaseDescriptor> createCreateNewModelProjectJob(String jobName, IProject project, URI location) {
		CreateNewAdaptiveAutosarProjectJob job = new CreateNewAdaptiveAutosarProjectJob(jobName, project, location, (AutosarReleaseDescriptor)((AdaptiveAutosarNewProjectWizardMainPage)mainPage).getMetaModelVersionDescriptor(), getProjectInfo());
        job.getImportedAutosarLibraries().addAll(((AdaptiveAutosarNewProjectWizardMainPage)mainPage).getImportedAutosarLibraryDescriptors());
        return job;
	}
	
}
