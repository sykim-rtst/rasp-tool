package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipFieldDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
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

public class SomeipFieldDeploymentSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textField;
	private Button buttonAddField;
	private Button buttonEraseField;
	
	public SomeipFieldDeploymentSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_FIELD_DEPLOYMENT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		SomeipFieldDeployment input = getCastedInputDetail();
		if(input != null) {
			
			setFieldText(input);
		}
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		// TODO Auto-generated method stub
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Someip field deployment");
		sectionPart.getSection().setDescription("Someip field deployment desc");
		
		textField = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Field*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddField = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseField = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		hookTextListener();
	}
	
	private void hookTextListener() {
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddField)) {
			final SomeipFieldDeployment input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof SomeipServiceInterfaceDeployment) {
					final Field selectedType = ReferenceChoiceDelegator.choiceSingleField(buttonAddField.getShell(), apProject, (ServiceInterface)((SomeipServiceInterfaceDeployment)parent).getServiceInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setField(selectedType);
								return model;
							}
						});
						
						setFieldText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseField)) {
			final SomeipFieldDeployment input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setField(null);
						return model;
					}
				});
				
				setFieldText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private void setFieldText(SomeipFieldDeployment model) {
		if(model.getField() != null) {
			textField.setText(model.getField().getShortName());
		}else {
			textField.setText("");
		}
	}
	
	private SomeipFieldDeployment getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipFieldDeployment) {
			return (SomeipFieldDeployment)getInputDetail();
		}
		return null;
	}
	
	private SomeipServiceInterfaceDeployment getCastedInputObject() {
		return (SomeipServiceInterfaceDeployment)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}

}
