package kr.co.rtst.autosar.ap4x.core.model;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.preferences.IAutosarWorkspacePreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import kr.co.rtst.autosar.ap4x.core.nature.AdaptiveAutosarProjectNature;

public interface IAPProject {
	
	String PROJECT_PREDEFINED_ARXML_TEMPLATE = "templates/types/stdtypes.arxml";
	
	String PROJECT_TEMP_FOLDER	 			= "__ap_temp__";
	String USER_DEFINED_ARXML_NAME 			= "AdaptiveAutosarProject.arxml";
	
	public IProject getProject();
	
	public IAPVirtualElement getTypes();

	public IAPVirtualElement getApplications();

	public IAPVirtualElement getServices();

	public IAPVirtualElement getMachines();
	
	/**
	 * Navigator 상에서 프로젝트 항목 바로 아래에 나타날 가상 엘리먼트들을 반환한다.
	 * @return
	 */
	public IAPVirtualElement[] getTopElements();
	
	public EObject getRootObject();
	
	default public IFile getProjectARXMLFile() {
		return getProject().getFile(USER_DEFINED_ARXML_NAME);
	}
	
	default public TransactionalEditingDomain getTransactionalEditingDomain() {
		return WorkspaceEditingDomainUtil.getEditingDomain(getProject(), getAutosarReleaseDescriptor());
	}
	
	default public AutosarReleaseDescriptor getAutosarReleaseDescriptor() {
		return (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(getProject());
	}
	
	static boolean isAdaptiveAutosarProject(IProject project) {
		try {
			return project.hasNature(AdaptiveAutosarProjectNature.ID);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 주어진 프로젝트에 필요한 네이처 정보를 추가한다.
	 * @param project
	 * @param monitor
	 * @throws CoreException
	 */
	public static void addRTSTAdaptiveAutosarNature(IProject project, IProgressMonitor monitor) throws CoreException {
		String[] natureIds = new String[] {
			"org.artop.aal.workspace.autosarnature",
			AdaptiveAutosarProjectNature.ID,
		};
		
		try {
			if (monitor == null) {
				monitor = new NullProgressMonitor();
			}
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length+natureIds.length];
			
			
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			System.arraycopy(natureIds, 0, newNatures, prevNatures.length, natureIds.length);
			
			description.setNatureIds(newNatures);

			project.setDescription(description, IResource.FORCE, monitor);
			
		} finally {
			monitor.done();
		}
	}
	
}
