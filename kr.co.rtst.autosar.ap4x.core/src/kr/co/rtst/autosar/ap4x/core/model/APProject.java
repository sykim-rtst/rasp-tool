package kr.co.rtst.autosar.ap4x.core.model;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.preferences.IAutosarWorkspacePreferences;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sphinx.emf.util.EcorePlatformUtil;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

public class APProject implements IAPProject{
	
	private final IProject project;
	private final IAPVirtualElement types;
	private final IAPVirtualElement applications;
	private final IAPVirtualElement services;
	private final IAPVirtualElement machines;
	private final IAPVirtualElement[] topVirtualElements;
	
	public APProject(IProject project) {
		super();
		this.project = project;
		
		types = new APTopVitualElement(this, IAPVirtualElement.VE_NAME_TYPE, 0, "icons/types/types.png");
		applications = new APTopVitualElement(this, IAPVirtualElement.VE_NAME_APPLICATION, 1, "icons/applications/applications.png");
		services = new APTopVitualElement(this, IAPVirtualElement.VE_NAME_SERVICE, 2, "icons/services/services.png");
		machines = new APTopVitualElement(this, IAPVirtualElement.VE_NAME_SYSTEM, 3, "icons/system/machines.png");
		
		topVirtualElements = new IAPVirtualElement[4];
		topVirtualElements[0] = types;
		topVirtualElements[1] = applications;
		topVirtualElements[2] = services;
		topVirtualElements[3] = machines;
		
		IAPVirtualElement[] children = null;
		
		children = new IAPVirtualElement[2];
		children[0] = new APSubVirtualElement(types, IAPVirtualElement.VE_NAME_TYPE_IMPL, 0, "icons/types/implementation.png");
		children[1] = new APSubVirtualElement(types, IAPVirtualElement.VE_NAME_TYPE_APP, 1, "icons/types/application.png");
		types.setChildren(children);
		
		children = new IAPVirtualElement[2];
		children[0] = new APSubVirtualElement(applications, IAPVirtualElement.VE_NAME_APPLICATION_SWC, 0, "icons/applications/swc.png");
		children[1] = new APSubVirtualElement(applications, IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM, 1, "icons/applications/platform.png");
		applications.setChildren(children);
		
		children = new IAPVirtualElement[4];
		children[0] = new APSubVirtualElement(services, IAPVirtualElement.VE_NAME_SERVICE_INTERFACE, 0, "icons/services/interface.png");
		children[1] = new APSubVirtualElement(services, IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT, 1, "icons/services/deployment.png");
		children[2] = new APSubVirtualElement(services, IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP, 2, "icons/services/deployment.png");
		children[3] = new APSubVirtualElement(services, IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP, 3, "icons/services/deployment.png");
		services.setChildren(children);
		
		children = new IAPVirtualElement[2];
		children[0] = new APSubVirtualElement(machines, IAPVirtualElement.VE_NAME_MACHINE_DESIGN, 0, "icons/system/machine_design.png");
		children[1] = new APSubVirtualElement(machines, IAPVirtualElement.VE_NAME_MACHINE, 1, "icons/system/machine.png");
		machines.setChildren(children);
	}
	
	@Override
	public IAPVirtualElement[] getTopElements() {
		return topVirtualElements;
	}

	public IProject getProject() {
		return project;
	}

	public IAPVirtualElement getTypes() {
		return types;
	}

	public IAPVirtualElement getApplications() {
		return applications;
	}

	public IAPVirtualElement getServices() {
		return services;
	}

	public IAPVirtualElement getMachines() {
		return machines;
	}
	
	/**
	 * 현재 액션이 실행된 컨텍스트의 프로젝트 최상위 요소를 반환한다.
	 * @return
	 */
	public EObject getRootObject() {
		if(getProject() != null) {
			AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(getProject());
			org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(getProject(), releaseDescription);
			EObject eObject = EcorePlatformUtil.loadModelRoot(editingDomain, getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME));
			if(eObject == null) {
				// TODO 강제 로딩이 필요함
			}
			return eObject;
		}
		return null;
	}
}
