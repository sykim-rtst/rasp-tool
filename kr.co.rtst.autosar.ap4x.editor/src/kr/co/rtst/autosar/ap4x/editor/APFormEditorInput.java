package kr.co.rtst.autosar.ap4x.editor;

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sphinx.emf.ui.util.EcoreUIUtil;
import org.eclipse.sphinx.emf.ui.util.ExtendedURIEditorInput;
import org.eclipse.sphinx.emf.util.EcorePlatformUtil;

import autosar40.genericstructure.generaltemplateclasses.arobject.ARObject;

public class APFormEditorInput extends ExtendedURIEditorInput {

	private ARObject detailObject;
	private Object masterObject;
	
	public APFormEditorInput(URIEditorInput input) {
		this(input.getURI(), null);
	}
	
	public APFormEditorInput(URI uri, ARObject initialDetailObject) {
		super(uri);
		this.detailObject = initialDetailObject;
	}

	public ARObject getDetailObject() {
		return detailObject;
	}

	public void setDetailObject(ARObject detailObject) {
		this.detailObject = detailObject;
	}
	
	public Object getMasterObject() {
		return masterObject;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		if(o instanceof APFormEditorInput) {
//			return ((APFormEditorInput) o).getMasterObject().equals(getMasterObject());
//		}
//		return false; //super.equals(o);
//	}
//	
//	@Override
//	public int hashCode() {
//		return getMasterObject().hashCode();
//	}
//	
//	public Object getEditorInputObject()
//    {
//        if(masterObject == null || isEditorInputObjectStale())
//        {
//            org.eclipse.emf.common.util.URI editorInputURI = EcoreUIUtil.getURIFromEditorInput(this);
//            if(editorInputURI != null)
//            {
//                if(editorInputURI.hasFragment())
//                    masterObject = EcorePlatformUtil.getEObject(editorInputURI);
//                else
//                    masterObject = EcorePlatformUtil.getResource(editorInputURI);
//            }
//        }
//        return masterObject;
//    }
//	
//	protected boolean isEditorInputObjectStale()
//    {
//        if(masterObject instanceof EObject)
//        {
//            EObject editorInputEObject = (EObject)masterObject;
//            if(editorInputEObject.eIsProxy() || editorInputEObject.eResource() == null || !editorInputEObject.eResource().isLoaded())
//                return true;
//        }
//        if(masterObject instanceof Resource)
//        {
//            Resource editorInputResource = (Resource)masterObject;
//            if(!editorInputResource.isLoaded() || editorInputResource.getResourceSet() == null)
//                return true;
//        }
//        return false;
//    }
	
}
