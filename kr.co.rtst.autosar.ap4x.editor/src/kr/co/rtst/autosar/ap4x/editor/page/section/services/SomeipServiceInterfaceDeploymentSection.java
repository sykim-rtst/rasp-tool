package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfaceDeploymentModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class SomeipServiceInterfaceDeploymentSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textServiceInterface;
	private Button buttonAddServiceInterface;
	private Button buttonEraseServiceInterface;
	
	private Text textServiceInterfaceId;
	
	public SomeipServiceInterfaceDeploymentSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_SERVICE_INTERFACE_DEPLOYMENT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initUIContents() {
		// TODO Auto-generated method stub
		super.initUIContents();
		SomeipServiceInterfaceDeployment input = getCastedInputDetail();
		if(input != null) {
			setServiceInterfaceText(input);
			textServiceInterfaceId.setText(String.valueOf(input.getServiceInterfaceId()));
		}
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		// TODO Auto-generated method stub
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Someip service interface deployment");
		sectionPart.getSection().setDescription("Someip service interface deployment desc");
		
		textServiceInterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddServiceInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseServiceInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textServiceInterfaceId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface id*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}
	
	private void hookTextListener() {
		textServiceInterfaceId.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipServiceInterfaceDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setServiceInterfaceId(Long.parseLong(textServiceInterfaceId.getText()));
							}catch(NumberFormatException e) {
								input.setServiceInterfaceId(0l);
							}
						}
						return model;
					}
				});
			}
		});
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddServiceInterface)) {
			final SomeipServiceInterfaceDeployment input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final ServiceInterface selectedType = ReferenceChoiceDelegator.choiceSinglePortInterface(buttonAddServiceInterface.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setServiceInterface(selectedType);
								return model;
							}
						});
						
						setServiceInterfaceText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseServiceInterface)) {
			final SomeipServiceInterfaceDeployment input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setServiceInterface(null);
						return model;
					}
				});
				
				setServiceInterfaceText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private void setServiceInterfaceText(SomeipServiceInterfaceDeployment model) {
		if(model.getServiceInterface() != null) {
			textServiceInterface.setText(model.getServiceInterface().getShortName());
		}else {
			textServiceInterface.setText("");
		}
	}
	
	private SomeipServiceInterfaceDeployment getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipServiceInterfaceDeployment) {
			return (SomeipServiceInterfaceDeployment)getInputDetail();
		}
		return null;
	}

}
