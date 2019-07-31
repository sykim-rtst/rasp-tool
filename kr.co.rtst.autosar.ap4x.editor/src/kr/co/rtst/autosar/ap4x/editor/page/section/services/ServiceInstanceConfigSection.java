package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.artop.aal.common.datatypes.PositiveIntegerDatatype;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdServerServiceInstanceConfig;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.InitialSdDelayConfig;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.RequestResponseDelay;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class ServiceInstanceConfigSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textOfferCyclicDelay;				//Optional
	private Text textServiceOfferTimeToLive;		
	private Text textInitialDelayMaxValue;			//Optional
	private Text textInitialDelayMinValue;			//Optional
	private Text textInitialRepetitionsBaseDelay;
	private Text textInitalRepetitionsMax;
	private Text textRequestResponseMaxValue;
	private Text textRequestResponseMinValue;
	
	public ServiceInstanceConfigSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ProvidedServiceInstanceModelManager.TYPE_NAME_SERVICE_INSTANCE_SERVER_CONFIG);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Service Instance Config");
		sectionPart.getSection().setDescription("Someip SD Client Service Instance Config");
		
		textOfferCyclicDelay = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Offer cyclic delay: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textServiceOfferTimeToLive = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service offer time to live: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialDelayMaxValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial delay max value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialDelayMinValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial delay min value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialRepetitionsBaseDelay = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial repetitions base delay: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitalRepetitionsMax = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Inital repetitions max: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMaxValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request response max value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textRequestResponseMinValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Request response min value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}

	private void hookTextListener()
	{
		textOfferCyclicDelay.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							try {
								input.setOfferCyclicDelay(Double.parseDouble(textOfferCyclicDelay.getText()));
							}catch(NumberFormatException e)
							{
								input.setOfferCyclicDelay(0d);
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
		//final ProvidedSomeipServiceInstance upperInput = getCastedInputObject();
		final SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
		
		if(input != null) {
			
			//setOfferCyclicDeleayText(input);
			//setServiceOfferTimeToLiveText(input);
			//setInitialSdDelayConfigText(input);
			//setRequestResponseDelayConfigText(input);
		}
	}
	
	private SomeipSdServerServiceInstanceConfig getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipSdServerServiceInstanceConfig) {
			return (SomeipSdServerServiceInstanceConfig)getInputDetail();
		}
		return null;
	}
	
	private ProvidedSomeipServiceInstance getCastedInputObject() {
		return (ProvidedSomeipServiceInstance)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
//	private void setOfferCyclicDeleayText(SomeipSdServerServiceInstanceConfig model)
//	{
//		if(model.getOfferCyclicDelay() != null) {
//			textOfferCyclicDelay.setText(model.getOfferCyclicDelay().toString());
//		}else {
//			textOfferCyclicDelay.setText("");
//		}
//	}
//	
//	private void setServiceOfferTimeToLiveText(SomeipSdServerServiceInstanceConfig model)
//	{
//		if(model.getServiceOfferTimeToLiveData() != null) {
//			textServiceOfferTimeToLive.setText(model.getServiceOfferTimeToLiveData().toString());
//		}else {
//			textServiceOfferTimeToLive.setText("");
//		}
//	}
//	
//	private void setInitialSdDelayConfigText(SomeipSdServerServiceInstanceConfig model)
//	{
//		if(model.getInitialOfferBehavior() != null) {
//			textInitialDelayMaxValue.setText(model.getInitialOfferBehavior().getInitialDelayMaxValue().toString());
//			textInitialDelayMinValue.setText(model.getInitialOfferBehavior().getInitialDelayMinValue().toString());
//			textInitialRepetitionsBaseDelay.setText(model.getInitialOfferBehavior().getInitialRepetitionsBaseDelay().toString());
//			textInitalRepetitionsMax.setText(model.getInitialOfferBehavior().getInitialRepetitionsMaxData().toString());
//		}else {
//			textInitialDelayMaxValue.setText("");
//			textInitialDelayMinValue.setText("");
//			textInitialRepetitionsBaseDelay.setText("");
//			textInitalRepetitionsMax.setText("");
//		}
//	}
//	
//	private void setRequestResponseDelayConfigText(SomeipSdServerServiceInstanceConfig model)
//	{
//		if(model.getRequestResponseDelay() != null) {
//			textRequestResponseMaxValue.setText(model.getRequestResponseDelay().getMaxValue().toString());
//			textRequestResponseMinValue.setText(model.getRequestResponseDelay().getMinValue().toString());
//		}else {
//			textRequestResponseMaxValue.setText("");
//			textRequestResponseMinValue.setText("");
//		}
//	}
}
