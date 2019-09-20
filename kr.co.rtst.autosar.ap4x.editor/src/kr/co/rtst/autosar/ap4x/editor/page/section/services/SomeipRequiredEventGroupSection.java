package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.artop.aal.common.datatypes.PositiveIntegerDatatype;
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

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.RequiredSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ServiceinstancedeploymentFactory;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipRequiredEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdClientEventGroupTimingConfig;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.RequestResponseDelay;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.RequiredServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class SomeipRequiredEventGroupSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textEventGroup;					//Optional
	private Button buttonChooseEventGroup;
	private Button buttonEraseEventGroup;
	private Text textRequestResponseTimeToLive;
	private Text textRequestResponseMaxValue;		//Optional
	private Text textRequestResponseMinValue;		//Optional
	
	public SomeipRequiredEventGroupSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, RequiredServiceInstanceModelManager.TYPE_NAME_EVENT_GROUP);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Requireded Event Group");
		sectionPart.getSection().setDescription("Someip Required Event Group Des");
		
		textEventGroup = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Event group: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonChooseEventGroup = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseEventGroup = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textRequestResponseTimeToLive = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request response time to live*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMaxValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request resoonse max value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMinValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request response min value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}
	
	private void hookTextListener()
	{
		textRequestResponseTimeToLive.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipRequiredEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							SomeipSdClientEventGroupTimingConfig timingConfig = input.getSdClientEventGroupTimingConfig();
							if(timingConfig == null)
							{
								timingConfig = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipSdClientEventGroupTimingConfig();
								input.setSdClientEventGroupTimingConfig(timingConfig);
							}
							
							try {
								timingConfig.setTimeToLiveData(new PositiveIntegerDatatype(textRequestResponseTimeToLive.getText()));
							}catch(NumberFormatException e)
							{
								timingConfig.setTimeToLiveData(new PositiveIntegerDatatype(0l));
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textRequestResponseMaxValue.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipRequiredEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							SomeipSdClientEventGroupTimingConfig timingConfig = input.getSdClientEventGroupTimingConfig();
							
							if(timingConfig == null)
							{
								timingConfig = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipSdClientEventGroupTimingConfig();
								input.setSdClientEventGroupTimingConfig(timingConfig);
							}
							
							RequestResponseDelay reqResDelay = timingConfig.getRequestResponseDelay();
							if(reqResDelay == null)
							{
								reqResDelay = EthernettopologyFactory.eINSTANCE.createRequestResponseDelay();
								timingConfig.setRequestResponseDelay(reqResDelay);
							}	
							
							try {
								reqResDelay.setMaxValue(Double.parseDouble(textRequestResponseMaxValue.getText()));
							}catch(NumberFormatException e)
							{
								reqResDelay.setMaxValue(0d);
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textRequestResponseMinValue.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipRequiredEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							SomeipSdClientEventGroupTimingConfig timingConfig = input.getSdClientEventGroupTimingConfig();
							
							if(timingConfig == null)
							{
								timingConfig = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipSdClientEventGroupTimingConfig();
								input.setSdClientEventGroupTimingConfig(timingConfig);
							}
							
							RequestResponseDelay reqResDelay = timingConfig.getRequestResponseDelay();
							if(reqResDelay == null)
							{
								reqResDelay = EthernettopologyFactory.eINSTANCE.createRequestResponseDelay();
								timingConfig.setRequestResponseDelay(reqResDelay);
							}
							
							try {
								reqResDelay.setMinValue(Double.parseDouble(textRequestResponseMinValue.getText()));
								
							}catch(NumberFormatException e)
							{
								reqResDelay.setMinValue(0d);
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
		final SomeipRequiredEventGroup input = getCastedInputDetail();
		
		if(input != null) {
			
			setEventGroupText(input);
			setRequestResponseTimeToLiveTest(input);
			setRequestResponseMaxValueText(input);
			setRequestResponseMinValueText(input);

		}
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonChooseEventGroup)) { //EventGroup 선택버튼
			final SomeipRequiredEventGroup input = getCastedInputDetail();
			final RequiredSomeipServiceInstance parentInput = getCastedInputObject();
			if(input != null)
			{
				IAPProject apProject = getAPProject();
				
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR)
				{
					final SomeipEventGroup selectedType = ReferenceChoiceDelegator.choiceSingleEventGroup(buttonChooseEventGroup.getShell(), apProject, (SomeipServiceInterfaceDeployment) parentInput.getServiceInterface());
					
					if(selectedType != null)
					{
						doTransactionalOperation(new IAPTransactionalOperation() {
							
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setEventGroup(selectedType);
								
								return null;
							}
						});
						
						textEventGroup.setText(selectedType.getShortName());
					}
				}
			}
		}else if(e.getSource().equals(buttonEraseEventGroup)) { //Service insterface 선택 취소 버튼
			final SomeipRequiredEventGroup input = getCastedInputDetail();
			if(input != null)
			{
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setEventGroup(null);
						return null;
					}
				});
				textEventGroup.setText("");
			}
		}

		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	private SomeipRequiredEventGroup getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipRequiredEventGroup) {
			return (SomeipRequiredEventGroup)getInputDetail();
		}
		return null;
	}

	private RequiredSomeipServiceInstance getCastedInputObject()
	{
		return (RequiredSomeipServiceInstance)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}
	
	private void setEventGroupText(SomeipRequiredEventGroup model)
	{
		if(model.getEventGroup() != null) {
			textEventGroup.setText(model.getEventGroup().getEventGroupId().toString());
		}else {
			textEventGroup.setText("");
		}
	}
	
	private void setRequestResponseTimeToLiveTest(SomeipRequiredEventGroup model)
	{
		if(model.getSdClientEventGroupTimingConfig() != null && model.getSdClientEventGroupTimingConfig().getTimeToLiveData() != null) {
			textRequestResponseTimeToLive.setText(model.getSdClientEventGroupTimingConfig().getTimeToLiveData().toString());
		}else {
			textRequestResponseTimeToLive.setText("");
		}
	}
	
	private void setRequestResponseMaxValueText(SomeipRequiredEventGroup model)
	{
		if(model.getSdClientEventGroupTimingConfig() != null && model.getSdClientEventGroupTimingConfig().getRequestResponseDelay() != null) {
			textRequestResponseMaxValue.setText(model.getSdClientEventGroupTimingConfig().getRequestResponseDelay().getMaxValue().toString());
		}else {
			textRequestResponseMaxValue.setText("");
		}
	}
	
	private void setRequestResponseMinValueText(SomeipRequiredEventGroup model)
	{
		if(model.getSdClientEventGroupTimingConfig() != null && model.getSdClientEventGroupTimingConfig().getRequestResponseDelay() != null) {
			textRequestResponseMinValue.setText(model.getSdClientEventGroupTimingConfig().getRequestResponseDelay().getMinValue().toString());
		}else {
			textRequestResponseMinValue.setText("");
		}
	}
}
