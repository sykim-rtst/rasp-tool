package kr.co.rtst.autosar.ap4x.ide.views.provider.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

import kr.co.rtst.autosar.ap4x.core.model.IAPProject;

public class AdaptiveAutosarResourceContentProvider /*extends BaseWorkbenchContentProvider*/ implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
//		System.out.println("-------------------------AdaptiveAutosarResourceContentProvider------------input:"+inputElement);
//		if(inputElement instanceof IProject) {
//			System.out.println("-------------------------AdaptiveAutosarResourceContentProvider------------Project");
//		}
//		return null; //new Object[0];
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
//		if(parentElement instanceof IProject){
//			try {
//				return ((IProject) parentElement).members();
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
//		if(element instanceof IResource) {
//			return ((IResource) element).getParent();
//		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
//		if(element instanceof IContainer) {
//			try {
//				return ((IContainer) element).members().length>0;
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
		return false;
	}
}
