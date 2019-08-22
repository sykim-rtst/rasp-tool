package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.RequiredSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ServiceinstancedeploymentFactory;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipMethodProps;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceInterfaceDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceMethodDeployment;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipMethodDeployment;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.RequiredServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class RequiredServiceInstanceSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textRequiredServiceInstanceId; //Optioanl
	private Text textServiceInterface;
	private Button buttonChooseInterface;
	private Button buttonEraseInterface;
	
	private ListViewer listMethodRequestProps;
	private Text textRequireMinorVersion;		//Optional
	
	public RequiredServiceInstanceSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, RequiredServiceInstanceModelManager.TYPE_NAME_REQUIRED_SERVICE_INSTANCE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Required Service Instance");
		sectionPart.getSection().setDescription("Required Service Instance desc");
		
		textServiceInterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service interface*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonChooseInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textRequiredServiceInstanceId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service instance ID: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		listMethodRequestProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Method request\r\nprops: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1);
		listMethodRequestProps.setContentProvider(new RequiredServiceInstanceContentProvider());
		listMethodRequestProps.setLabelProvider(new SomeipMethodPropsLabelProvider());
		
		textRequireMinorVersion = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Required minor version: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}
	
	private void hookTextListener()
	{
		textRequiredServiceInstanceId.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						RequiredSomeipServiceInstance input = getCastedInputObject();
						if(input != null)
						{
							try {
								input.setRequiredServiceInstanceId(textRequiredServiceInstanceId.getText());
							}catch(NumberFormatException e)
							{
								input.setRequiredServiceInstanceId("0");
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textRequireMinorVersion.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						RequiredSomeipServiceInstance input = getCastedInputObject();
						if(input != null)
						{
							try {
								input.setRequiredMinorVersion(textRequireMinorVersion.getText());
							}catch(NumberFormatException e)
							{
								input.setRequiredMinorVersion("");
							}
						}
						return model;
					}
				});
				
			}
		});
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final RequiredSomeipServiceInstance input = getCastedInputObject();
		final ServiceInterfaceDeployment serviceInterface = input.getServiceInterface();
		
		if(input != null) {
			
			setServiceInstanceIDText(input);
			setRequiredMinorVersionText(input);
			listMethodRequestProps.setInput(input.getMethodRequestProps());
			
			if(serviceInterface != null) {
				textServiceInterface.setText(serviceInterface.gGetShortName());
				listMethodRequestProps.refresh();
			}
		}
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonChooseInterface)) { //ServiceInterface 선택버튼
			final RequiredSomeipServiceInstance input = getCastedInputObject();
			if(input != null)
			{
				IAPProject apProject = getAPProject();
				
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR)
				{
					final ServiceInterfaceDeployment selectedType = ReferenceChoiceDelegator.choiceSingleServiceInterfaceDeployment(buttonChooseInterface.getShell(), apProject);
					
					if(selectedType != null)
					{
						EList<ServiceMethodDeployment> methodDeployments = selectedType.getMethodDeployments();
						EList<SomeipMethodProps> someipMethodProps = new BasicEList<SomeipMethodProps>();
						
						SomeipMethodProps singleMethodProps;
						for(ServiceMethodDeployment md: methodDeployments)
						{
							singleMethodProps = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipMethodProps();
							singleMethodProps.setMethod((SomeipMethodDeployment)md);
							someipMethodProps.add(singleMethodProps);
						}
						
						doTransactionalOperation(new IAPTransactionalOperation() {
							
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.getMethodRequestProps().clear();
								input.getMethodRequestProps().addAll(someipMethodProps);
								
								input.setServiceInterface(selectedType);
								
								return null;
							}
						});
						
						textServiceInterface.setText(selectedType.getShortName());
						listMethodRequestProps.refresh();
						
					}
				}
			}
		}else if(e.getSource().equals(buttonEraseInterface)) { //Service interface 선택 취소 버튼
			final RequiredSomeipServiceInstance input = getCastedInputObject();
			if(input != null)
			{
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setServiceInterface(null);
						
						return null;
					}
				});
				textRequiredServiceInstanceId.setText("");
				listMethodRequestProps.refresh();
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	private RequiredSomeipServiceInstance getCastedInputObject()
	{
		return (RequiredSomeipServiceInstance)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}
	
	private void setServiceInstanceIDText(RequiredSomeipServiceInstance model)
	{
		if(model.getRequiredServiceInstanceId() != null) {
			textRequiredServiceInstanceId.setText(model.getRequiredServiceInstanceId());
		}else {
			textRequiredServiceInstanceId.setText("");
		}
	}
	
	private void setRequiredMinorVersionText(RequiredSomeipServiceInstance model)
	{
		if(model.getRequiredMinorVersion() != null) {
			textRequireMinorVersion.setText(model.getRequiredMinorVersion());
		}else {
			textRequireMinorVersion.setText("");
		}
	}
	
	private class RequiredServiceInstanceContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class SomeipMethodPropsLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public SomeipMethodPropsLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof SomeipMethodProps)
			{
				return ((SomeipMethodProps)element).getMethod().getShortName();
			}else
			{
				return "";
			}
		}
		
		@Override
		public Image getImage(Object element) {
			Image img = apModelLabelProvider.getImage(element);
			if(img == null) {
				img = AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
			}
			return img;
		}
	}
}
