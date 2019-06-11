package kr.co.rtst.autosar.ap4x.core.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

import gautosar.ggenericstructure.ginfrastructure.GARObject;

/**
 * 이클립스 IProject 와 AP프로젝트 객체를 매핑하는 클래스
 * @author thkim
 *
 */
public class APProjectManager {

private static APProjectManager instance;
	
	private Map<IProject, IAPProject> coreModelRegistry;
	
	public static APProjectManager getInstance() {
		if(instance == null) {
			instance = new APProjectManager();
		}
		return instance;
	}
	
	private APProjectManager() {
		coreModelRegistry = new HashMap<>();
	}
	
	public IAPProject getAPProject(IProject project) {
		if(!coreModelRegistry.containsKey(project)) {
			coreModelRegistry.put(project, new APProject(project));
		}
		return coreModelRegistry.get(project);
	}
	
	public IAPProject getAPProject(GARObject childObject) {
		if(childObject != null) {
//			System.out.println("RES:"+ownerObject.eResource().getURI().devicePath().substring("/resource/".length()));
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(childObject.eResource().getURI().devicePath().substring("/resource/".length())));
			
			return APProjectManager.getInstance().getAPProject(file.getProject());
		}
		return null;
	}
	
}
