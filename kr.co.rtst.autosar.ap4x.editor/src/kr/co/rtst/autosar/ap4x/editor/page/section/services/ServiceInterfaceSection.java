package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelInitializer;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class ServiceInterfaceSection extends ShortNameContentGUISection {

	private Text textNamespace;
	
	public ServiceInterfaceSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_SECVICE_INTERFACE);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText(EditorText.SECTION_TITLE_SERVICE_INTERFACE);
		sectionPart.getSection().setDescription(EditorText.SECTION_DESC_SERVICE_INTERFACE);
		
		textNamespace = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(EditorText.SECTION_SERVICE_INTERFACE_ATTR_NAMESPACE), EditorText.SECTION_SERVICE_INTERFACE_ATTR_NAMESPACE, AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textNamespace.addModifyListener(e->{
//			if(getInputDetail() instanceof ServiceInterface) {
//				APModelAgent.getNamespace(this, (ServiceInterface)getInputDetail()).setSymbol(textNamespace.getText());
//			}
			doTransactionalOperation(new IAPTransactionalOperation() {
				@Override
				public GARObject doProcess(GARObject model) throws Exception {
					if(model instanceof ServiceInterface) {
						APModelInitializer.initializeNamespace(ServiceInterfaceSection.this, (ServiceInterface)model).setSymbol(textNamespace.getText());
					}
					return model;
				}
			});
		});
		
//		textNamespace.addModifyListener(new ModifyListener() {
//			
//			@Override
//			public void modifyText(ModifyEvent e) {
//				if(getInputDetail() instanceof ServiceInterface) {
//					final ServiceInterface detailObject = (ServiceInterface)getInputDetail();
//					System.out.println("RES:"+detailObject.eResource().getURI().devicePath().substring("/resource/".length()));
//					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(detailObject.eResource().getURI().devicePath().substring("/resource/".length())));
//					System.out.println("IFile:"+file+", EXIST:"+file.exists());
//					if(file.exists()) {
//						IAPProject apProject = APProjectManager.getInstance().getAPProject(file.getProject());
//						org.eclipse.emf.transaction.TransactionalEditingDomain domain = WorkspaceEditingDomainUtil.getEditingDomain(
//								apProject.getProject(), 
//								apProject.getAutosarReleaseDescriptor());
//						if (domain != null) {
//							domain.getCommandStack().execute(new RecordingCommand(domain) {
//								@Override
//								protected void doExecute() {
//									detailObject.eContents().add(arg0)(textShortName.getText().trim());
//								}
//							});
//						}
//						getAPEditorPage().getAPFormEditor().setDirty();
//					}
//				}
//			}
//		});
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		ServiceInterface input = getCastedInputDetail();
		if(input != null) {
			textNamespace.setText(APModelInitializer.initializeNamespace(this, (ServiceInterface)getInputDetail()).getSymbol());
		}
	}
	
	private ServiceInterface getCastedInputDetail() {
		if(getInputDetail() instanceof ServiceInterface) {
			return (ServiceInterface)getInputDetail();
		}
		return null;
	}

}
