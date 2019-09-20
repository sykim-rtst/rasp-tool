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

public class SomeipSdServerServiceInstanceConfigSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textOfferCyclicDelay;				//Optional
	private Text textServiceOfferTimeToLive;		
	private Text textInitialDelayMaxValue;			//Optional
	private Text textInitialDelayMinValue;			//Optional
	private Text textInitialRepetitionsBaseDelay;
	private Text textInitalRepetitionsMax;
	private Text textRequestResponseMaxValue;
	private Text textRequestResponseMinValue;
	
	public SomeipSdServerServiceInstanceConfigSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ProvidedServiceInstanceModelManager.TYPE_NAME_SD_SERVER_SERVICE_INSTANCE_CONFIG);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Sd Server Service Instance Config");
		sectionPart.getSection().setDescription("Someip SD Server Service Instance Config");
		
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
		

		
		textServiceOfferTimeToLive.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							try {
								input.setServiceOfferTimeToLiveData(new PositiveIntegerDatatype(textServiceOfferTimeToLive.getText()));
							}catch(NumberFormatException e)
							{
								input.setServiceOfferTimeToLiveData(new PositiveIntegerDatatype(0l));
							}
						}
						return model;
					}
				});
				
			}
		});
		
		
		textInitialDelayMaxValue.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialOfferBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialDelayMaxValue(Double.parseDouble(textInitialDelayMaxValue.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialDelayMaxValue(Double.parseDouble(textInitialDelayMaxValue.getText()));
									input.setInitialOfferBehavior(delayConfig);
								}
								
							}catch(NumberFormatException e)
							{
								delayConfig.setInitialDelayMaxValue(0d);
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textInitialDelayMinValue.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialOfferBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialDelayMinValue(Double.parseDouble(textInitialDelayMinValue.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialDelayMinValue(Double.parseDouble(textInitialDelayMinValue.getText()));
									input.setInitialOfferBehavior(delayConfig);
								}
								
							}catch(NumberFormatException e)
							{
								delayConfig.setInitialDelayMinValue(0d);
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textInitialRepetitionsBaseDelay.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialOfferBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialRepetitionsBaseDelay(Double.parseDouble(textInitialRepetitionsBaseDelay.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialRepetitionsBaseDelay(Double.parseDouble(textInitialRepetitionsBaseDelay.getText()));
									input.setInitialOfferBehavior(delayConfig);
								}
								
							}catch(NumberFormatException e)
							{
								delayConfig.setInitialRepetitionsBaseDelay(0d);
							}
						}
						return model;
					}
				});
				
			}
		});
		
		textInitalRepetitionsMax.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialOfferBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialRepetitionsMaxData(new PositiveIntegerDatatype(textInitalRepetitionsMax.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialRepetitionsMaxData(new PositiveIntegerDatatype(textInitalRepetitionsMax.getText()));
									input.setInitialOfferBehavior(delayConfig);
								}
								
							}catch(NumberFormatException e)
							{
								delayConfig.setInitialRepetitionsMaxData(new PositiveIntegerDatatype(0l));
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
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							RequestResponseDelay requestResponseDelay = input.getRequestResponseDelay();
							try {
								
								if(requestResponseDelay != null)
								{
									requestResponseDelay.setMaxValue(Double.parseDouble(textRequestResponseMaxValue.getText()));
								}else
								{
									requestResponseDelay = EthernettopologyFactory.eINSTANCE.createRequestResponseDelay();
									requestResponseDelay.setMaxValue(Double.parseDouble(textRequestResponseMaxValue.getText()));
									input.setRequestResponseDelay(requestResponseDelay);
								}
								
							}catch(NumberFormatException e)
							{
								requestResponseDelay.setMaxValue(0d);
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
						SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							RequestResponseDelay requestResponseDelay = input.getRequestResponseDelay();
							try {
								
								if(requestResponseDelay != null)
								{
									requestResponseDelay.setMinValue(Double.parseDouble(textRequestResponseMinValue.getText()));
								}else
								{
									requestResponseDelay = EthernettopologyFactory.eINSTANCE.createRequestResponseDelay();
									requestResponseDelay.setMinValue(Double.parseDouble(textRequestResponseMinValue.getText()));
									input.setRequestResponseDelay(requestResponseDelay);
								}
								
							}catch(NumberFormatException e)
							{
								requestResponseDelay.setMinValue(0d);
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
		final SomeipSdServerServiceInstanceConfig input = getCastedInputDetail();
		
		if(input != null) {
			
			setOfferCyclicDeleayText(input);
			setServiceOfferTimeToLiveText(input);
			setInitialSdDelayConfigText(input);
			setRequestResponseDelayConfigText(input);
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
	
	private void setOfferCyclicDeleayText(SomeipSdServerServiceInstanceConfig model)
	{
		if(model.getOfferCyclicDelay() != null) {
			textOfferCyclicDelay.setText(model.getOfferCyclicDelay().toString());
		}else {
			textOfferCyclicDelay.setText("");
		}
	}
	
	private void setServiceOfferTimeToLiveText(SomeipSdServerServiceInstanceConfig model)
	{
		if(model.getServiceOfferTimeToLiveData() != null) {
			textServiceOfferTimeToLive.setText(model.getServiceOfferTimeToLiveData().toString());
		}else {
			textServiceOfferTimeToLive.setText("");
		}
	}
	
	private void setInitialSdDelayConfigText(SomeipSdServerServiceInstanceConfig model)
	{
		if(model.getInitialOfferBehavior() != null) {
			textInitialDelayMaxValue.setText(model.getInitialOfferBehavior().getInitialDelayMaxValue().toString());
			textInitialDelayMinValue.setText(model.getInitialOfferBehavior().getInitialDelayMinValue().toString());
			textInitialRepetitionsBaseDelay.setText(model.getInitialOfferBehavior().getInitialRepetitionsBaseDelay().toString());
			textInitalRepetitionsMax.setText(model.getInitialOfferBehavior().getInitialRepetitionsMaxData().toString());
		}else {
			textInitialDelayMaxValue.setText("");
			textInitialDelayMinValue.setText("");
			textInitialRepetitionsBaseDelay.setText("");
			textInitalRepetitionsMax.setText("");
		}
	}
	
	private void setRequestResponseDelayConfigText(SomeipSdServerServiceInstanceConfig model)
	{
		if(model.getRequestResponseDelay() != null) {
			textRequestResponseMaxValue.setText(model.getRequestResponseDelay().getMaxValue().toString());
			textRequestResponseMinValue.setText(model.getRequestResponseDelay().getMinValue().toString());
		}else {
			textRequestResponseMaxValue.setText("");
			textRequestResponseMinValue.setText("");
		}
	}
}
