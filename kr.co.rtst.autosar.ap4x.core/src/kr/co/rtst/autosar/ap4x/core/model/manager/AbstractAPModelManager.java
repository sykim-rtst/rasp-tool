package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.Collections;
import java.util.List;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.preferences.IAutosarWorkspacePreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sphinx.emf.util.EcorePlatformUtil;
import org.eclipse.sphinx.emf.util.WorkspaceEditingDomainUtil;

import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public abstract class AbstractAPModelManager implements IAPModelManager {

	private final List<IAPTypeDescriptor> apTypeDescriptors;
	
	AbstractAPModelManager() {
		apTypeDescriptors = Collections.unmodifiableList(generateAPTypeDescriptors());
	}
	
	abstract protected List<IAPTypeDescriptor> generateAPTypeDescriptors();
	
	@Override
	public List<IAPTypeDescriptor> getAPTypeDescriptors() {
		return apTypeDescriptors;
	}
	
	protected GAUTOSAR load(IFile arxmlFile) {
		AutosarReleaseDescriptor releaseDescription = (AutosarReleaseDescriptor)IAutosarWorkspacePreferences.AUTOSAR_RELEASE.get(arxmlFile.getProject());
		org.eclipse.emf.transaction.TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(arxmlFile.getProject(), releaseDescription);
		EObject object = EcorePlatformUtil.loadModelRoot(editingDomain, arxmlFile);
		if(object instanceof GAUTOSAR) {
			return (GAUTOSAR)object;
		}
		return null;
	}
	
}
