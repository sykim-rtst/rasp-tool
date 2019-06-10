package kr.co.rtst.autosar.ap4x.ide.views.filter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import kr.co.rtst.autosar.ap4x.core.model.IAPProject;

public class AdaptiveAutosarProjectFilter extends ViewerFilter {

	public AdaptiveAutosarProjectFilter() {
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof IProject) {
			return IAPProject.isAdaptiveAutosarProject((IProject)element);
		}else if(element instanceof IFile) {
			return !(IAPProject.USER_DEFINED_ARXML_NAME.equals(((IFile) element).getName()));
		}else if(element instanceof IFolder) {
			return !(IAPProject.PROJECT_TEMP_FOLDER.equals(((IFolder) element).getName()));
		}
		return true;
	}

}
