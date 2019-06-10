package kr.co.rtst.autosar.ap4x.editor;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.sphinx.emf.editors.forms.BasicTransactionalFormEditor;
import org.eclipse.sphinx.emf.ui.util.EcoreUIUtil;
import org.eclipse.sphinx.platform.util.PlatformLogUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.IWorkbenchPartConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.swcomponent.datatype.datatypes.ApplicationDataType;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.observer.APModelChangeActionHandler;
import kr.co.rtst.autosar.ap4x.core.model.observer.IAPModelChangeActionObserver;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.AdaptiveApplicationSwComponentTypeEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.ApplicationDataTypeEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.ImplementationDataTypeEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.MachineDesignEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.MachineEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.ServiceInterfaceEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.SomeipServiceInterfaceDeploymentEditorPage;

public class APFormEditor extends BasicTransactionalFormEditor implements IAPModelChangeActionObserver, IPersistableEditor {
	
	public static final String ID = "kr.co.rtst.autosar.ap4x.editor.AdaptiveAUTOSARForm";

	// Show names and types => true ; Show names => false
	private static final String MODEL_ELEMENT_APPEARANCE_PROPERTY = "model_element_appearance_property"; //$NON-NLS-1$
	
	public APFormEditorInput getAPFormEditorInput() {
		return (APFormEditorInput)getEditorInput();
	}
	
	public void setDirty(){
		for (Object listener : getListeners()) {
			System.out.println("Listener::"+listener);
		}
		firePropertyChange(IWorkbenchPartConstants.PROP_DIRTY);
	}
	
//	protected void firePropertyChange(final int propertyId) {
//		for (Object listener : getListeners()) {
//			final IPropertyListener propertyListener = (IPropertyListener) listener;
//            try {
//				propertyListener.propertyChanged(WorkbenchPart.this, propertyId);
//            } catch (RuntimeException e) {
//                WorkbenchPlugin.log(e);
//            }
//        }
//    }
	
	@Override
	protected void addPages() {
		try {
//			Object input = getEditorInputObject(); //getModelRoot();
//			if (input instanceof EObject) {
//				// Add overview page if editor input is an instance of Identifiable
//				if (IdentifiableUtil.isIdentifiable(input)) {
//					addPage(new OverviewPage(this));
//				}
//
//				addPage(new APEditPage(this));
//			}
			
			Object input = getEditorInputObject();
			if(input instanceof ImplementationDataType) {
				addPage(new ImplementationDataTypeEditorPage(this));
			} else if(input instanceof ApplicationDataType) {
				addPage(new ApplicationDataTypeEditorPage(this));
			} else if(input instanceof AdaptiveApplicationSwComponentType) {
				addPage(new AdaptiveApplicationSwComponentTypeEditorPage(this));
			} else if(input instanceof ServiceInterface) {
				addPage(new ServiceInterfaceEditorPage(this));
			} else if(input instanceof SomeipServiceInterfaceDeployment) {
				addPage(new SomeipServiceInterfaceDeploymentEditorPage(this));
			} else if(input instanceof MachineDesign) {
				addPage(new MachineDesignEditorPage(this));
			} else if(input instanceof Machine) {
				addPage(new MachineEditorPage(this));
			}
		} catch (PartInitException ex) {
			PlatformLogUtil.logAsError(AP4xEditorActivator.getDefault(), ex);
		}
	}

	@Override
	protected String getEditorInputName() {
		// Has editor been opened on an AUTOSAR root object?
		Object modelRoot = getModelRoot();
		if (modelRoot instanceof GAUTOSAR) {
			// Use name defined by editor input descriptor (but not AUTOSAR root object label) as editor input name
			return getEditorInput().getName();
		}

		// Has object on which editor has been opened become unavailable?
		if (modelRoot == null) {
			// Does editor input descriptor yield a URI pointing at some AUTOSAR object?
			URI editorInputURI = EcoreUIUtil.getURIFromEditorInput(getEditorInput());
			if (editorInputURI != null) {
				// Try to use last segment of absolute qualified name of that AUTOSAR object as editor input name
				String fragment = editorInputURI.fragment();
				if (fragment != null) {
					return AutosarURIFactory.getTrailingAbsoluteQualifiedNameSegment(fragment);
				}
			}
		}

		return super.getEditorInputName();
	}

	@Override
	protected Image getEditorInputImage() {
		// Has editor been opened on an AUTOSAR root object?
		Object modelRoot = getModelRoot();
		if (modelRoot instanceof GAUTOSAR) {
			// Use icon of underlying AUTOSAR XML file (but not that of AUTOSAR root object) as editor input image
			IFile file = EcoreUIUtil.getFileFromEditorInput(getEditorInput());
			if (file != null) {
				IContentType contentType = IDE.getContentType(file);
				ImageDescriptor imageDescriptor = PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(file.getName(), contentType);
				return ExtendedImageRegistry.getInstance().getImage(imageDescriptor);
			}
		}

		return super.getEditorInputImage();
	}

	@Override
	public String getTitleToolTip() {
		// Has editor been opened on some AUTOSAR object?
		IEditorInput editorInput = getEditorInput();
		if (editorInput instanceof URIEditorInput) {
			// Make sure that tool tip yields workspace-relative (but not platform:/resource) URI to AUTOSAR object
			URI uri = EcoreUIUtil.getURIFromEditorInput(editorInput);
			if (uri.isPlatform()) {
				String path = uri.toPlatformString(true);
				return new Path(path).makeRelative().toString();
			}
		}

		return super.getTitleToolTip();
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput editorInput) {
		System.out.println("---------------------------=========-----------"+editorInput.getClass());
		if(!(editorInput instanceof APFormEditorInput) && editorInput instanceof URIEditorInput) {
			APFormEditorInput newInput = new APFormEditorInput((URIEditorInput)editorInput);
			super.init(site, newInput);
		}else {
			super.init(site, editorInput);
		}
		APModelChangeActionHandler.apINSTANCE.addObserver(APProjectManager.getInstance().getAPProject((GARObject)getEditorInputObject()), this);
	}
	
	@Override
	public void dispose() {
		APModelChangeActionHandler.apINSTANCE.removeObserver(APProjectManager.getInstance().getAPProject((GARObject)getEditorInputObject()), this);
		super.dispose();
	}
	
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public boolean isShowTypeName() {
		return AP4xEditorActivator.getDefault().getDialogSettings().getBoolean(MODEL_ELEMENT_APPEARANCE_PROPERTY);
	}

	public void setShowTypeName(boolean showTypeName) {
		AP4xEditorActivator.getDefault().getDialogSettings().put(MODEL_ELEMENT_APPEARANCE_PROPERTY, showTypeName);
	}
	
//	public IAPProject getAPProject() {
//		if(getEditorInputObject() instanceof GARObject) {
//			final GARObject detailObject = (GARObject)getEditorInputObject();
//			System.out.println("RES:"+detailObject.eResource().getURI().devicePath().substring("/resource/".length()));
//			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(detailObject.eResource().getURI().devicePath().substring("/resource/".length())));
//			
//			return APProjectManager.getInstance().getAPProject(file.getProject());
//		}
//		return null;
//	}
	
	@Override
	public void preModelChange() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postModelChange() {
		if(((AbstractAPEditorPage)getActivePageInstance()) != null) {
			((AbstractAPEditorPage)getActivePageInstance()).refreshInputObjectTree();
		}
	}
	
//	public static void refresh(IEditorInput input) {
//		IEditorPart editor = AP4xEditorActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().findEditor(input);
//		if(editor != null) {
//			APFormEditor apEditor = (APFormEditor)editor;
//			AbstractAPEditorPage page = (AbstractAPEditorPage)apEditor.getActivePageInstance();
//			page.refresh();
//		}
//	}
}
