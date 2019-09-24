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

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ServiceinstancedeploymentFactory;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipProvidedEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdServerEventGroupTimingConfig;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.RequestResponseDelay;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class SomeipProvidedEventGroupSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textEventGroup;
	private Button buttonChooseEventGroup;
	private Button buttonEraseEventGroup;
	private Text textMulticastThreshold;
	private Text textRequestResponseMaxValue;
	private Text textRequestResponseMinValue;
	
	public SomeipProvidedEventGroupSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ProvidedServiceInstanceModelManager.TYPE_NAME_EVENT_GROUP);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Provided Event Group");
		sectionPart.getSection().setDescription("Someip Provided Event Group Des");
		
		textEventGroup = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Event group: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, true);
		buttonChooseEventGroup = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseEventGroup = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textMulticastThreshold = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Multicast threshold*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMaxValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request resoonse max value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMinValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request response min value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}

	private void hookTextListener()
	{
		textMulticastThreshold.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipProvidedEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							try {
								input.setMulticastThresholdData(new PositiveIntegerDatatype(textMulticastThreshold.getText()));
							}catch(NumberFormatException e)
							{
								input.setMulticastThresholdData(new PositiveIntegerDatatype(0l));
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
						SomeipProvidedEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							SomeipSdServerEventGroupTimingConfig timingConfig = input.getSdServerEventGroupTimingConfig();
							
							if(timingConfig == null)
							{
								timingConfig = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipSdServerEventGroupTimingConfig();
								input.setSdServerEventGroupTimingConfig(timingConfig);
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
						SomeipProvidedEventGroup input = getCastedInputDetail();
						if(input != null)
						{
							SomeipSdServerEventGroupTimingConfig timingConfig = input.getSdServerEventGroupTimingConfig();
							
							if(timingConfig == null)
							{
								timingConfig = ServiceinstancedeploymentFactory.eINSTANCE.createSomeipSdServerEventGroupTimingConfig();
								input.setSdServerEventGroupTimingConfig(timingConfig);
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
		final SomeipProvidedEventGroup input = getCastedInputDetail();
		
		if(input != null) {
			
			setEventGroupText(input);
			setMulticastThresholdText(input);
			setRequestResponseMaxValueText(input);
			setRequestResponseMinValueText(input);

		}
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonChooseEventGroup)) { //EventGroup 선택버튼
			final SomeipProvidedEventGroup input = getCastedInputDetail();
			final ProvidedSomeipServiceInstance parentInput = getCastedInputObject();
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
			final SomeipProvidedEventGroup input = getCastedInputDetail();
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
	
	private SomeipProvidedEventGroup getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipProvidedEventGroup) {
			return (SomeipProvidedEventGroup)getInputDetail();
		}
		return null;
	}

	private ProvidedSomeipServiceInstance getCastedInputObject()
	{
		return (ProvidedSomeipServiceInstance)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}
	
	private void setEventGroupText(SomeipProvidedEventGroup model)
	{
		if(model.getEventGroup() != null) {
			textEventGroup.setText(model.getEventGroup().getEventGroupId().toString());
		}else {
			textEventGroup.setText("");
		}
	}
	
	private void setMulticastThresholdText(SomeipProvidedEventGroup model)
	{
		if(model.getMulticastThresholdData()!= null) {
			textMulticastThreshold.setText(model.getMulticastThresholdData().toString());
		}else {
			textMulticastThreshold.setText("");
		}
	}
	
	private void setRequestResponseMaxValueText(SomeipProvidedEventGroup model)
	{
		if(model.getSdServerEventGroupTimingConfig() != null) {
			textRequestResponseMaxValue.setText(model.getSdServerEventGroupTimingConfig().getRequestResponseDelay().getMaxValue().toString());
		}else {
			textRequestResponseMaxValue.setText("");
		}
	}
	
	private void setRequestResponseMinValueText(SomeipProvidedEventGroup model)
	{
		if(model.getSdServerEventGroupTimingConfig() != null) {
			textRequestResponseMinValue.setText(model.getSdServerEventGroupTimingConfig().getRequestResponseDelay().getMinValue().toString());
		}else {
			textRequestResponseMinValue.setText("");
		}
	}
}
