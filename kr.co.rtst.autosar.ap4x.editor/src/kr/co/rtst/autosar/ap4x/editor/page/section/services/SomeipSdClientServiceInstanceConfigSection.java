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

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdClientServiceInstanceConfig;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyFactory;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.InitialSdDelayConfig;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.RequiredServiceInstanceModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class SomeipSdClientServiceInstanceConfigSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textServiceFindTimeToLive;
	private Text textInitialDelayMaxValue;			//Optional
	private Text textInitialDelayMinValue;			//Optional
	private Text textInitialRepetitionsBaseDelay;	//Optional
	private Text textInitialRepetitionsMax;			//Optional
	
	public SomeipSdClientServiceInstanceConfigSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, RequiredServiceInstanceModelManager.TYPE_NAME_SERVICE_INSTANCE_CLIENT_CONFIG);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Sd Client Service Instance Config");
		sectionPart.getSection().setDescription("Someip SD Client Service Instance Config");
		
		textServiceFindTimeToLive = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Service offer time to live: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialDelayMaxValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial delay max value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialDelayMinValue = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial delay min value: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialRepetitionsBaseDelay = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Initial repetitions base delay: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textInitialRepetitionsMax = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Inital repetitions max: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}

	private void hookTextListener()
	{
		textServiceFindTimeToLive.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							try {
								input.setServiceFindTimeToLiveData(new PositiveIntegerDatatype(textServiceFindTimeToLive.getText()));
							}catch(NumberFormatException e)
							{
								input.setServiceFindTimeToLiveData(new PositiveIntegerDatatype(0l));
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
						SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							
							InitialSdDelayConfig delayConfig = input.getInitialFindBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialDelayMaxValue(Double.parseDouble(textInitialDelayMaxValue.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialDelayMaxValue(Double.parseDouble(textInitialDelayMaxValue.getText()));
									input.setInitialFindBehavior(delayConfig);
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
						SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialFindBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialDelayMinValue(Double.parseDouble(textInitialDelayMinValue.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialDelayMinValue(Double.parseDouble(textInitialDelayMinValue.getText()));
									input.setInitialFindBehavior(delayConfig);
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
						SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialFindBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialRepetitionsBaseDelay(Double.parseDouble(textInitialRepetitionsBaseDelay.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialRepetitionsBaseDelay(Double.parseDouble(textInitialRepetitionsBaseDelay.getText()));
									input.setInitialFindBehavior(delayConfig);
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
		
		textInitialRepetitionsMax.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
						if(input != null)
						{
							InitialSdDelayConfig delayConfig = input.getInitialFindBehavior();
							try {
								
								if(delayConfig != null)
								{
									delayConfig.setInitialRepetitionsMaxData(new PositiveIntegerDatatype(textInitialRepetitionsMax.getText()));
								}else
								{
									delayConfig = EthernettopologyFactory.eINSTANCE.createInitialSdDelayConfig();
									delayConfig.setInitialRepetitionsMaxData(new PositiveIntegerDatatype(textInitialRepetitionsMax.getText()));
									input.setInitialFindBehavior(delayConfig);
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
	}

	@Override
	public void initUIContents() {
		super.initUIContents();
		final SomeipSdClientServiceInstanceConfig input = getCastedInputDetail();
		
		if(input != null) {
			setServieFindTimeToLiveText(input);
			setInitialSdDelayConfigText(input);
		}
	}
	
	private SomeipSdClientServiceInstanceConfig getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipSdClientServiceInstanceConfig) {
			return (SomeipSdClientServiceInstanceConfig)getInputDetail();
		}
		return null;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void setServieFindTimeToLiveText(SomeipSdClientServiceInstanceConfig model)
	{
		if(model.getServiceFindTimeToLiveData() != null) {
			textServiceFindTimeToLive.setText(model.getServiceFindTimeToLiveData().toString());
		}else {
			textServiceFindTimeToLive.setText("");
		}
	}
	
	private void setInitialSdDelayConfigText(SomeipSdClientServiceInstanceConfig model)
	{
		if(model.getInitialFindBehavior() != null) {
			textInitialDelayMaxValue.setText(model.getInitialFindBehavior().getInitialDelayMaxValue().toString());
			textInitialDelayMinValue.setText(model.getInitialFindBehavior().getInitialDelayMinValue().toString());
			textInitialRepetitionsBaseDelay.setText(model.getInitialFindBehavior().getInitialRepetitionsBaseDelay().toString());
			textInitialRepetitionsMax.setText(model.getInitialFindBehavior().getInitialRepetitionsMaxData().toString());
		}else {
			textInitialDelayMaxValue.setText("");
			textInitialDelayMinValue.setText("");
			textInitialRepetitionsBaseDelay.setText("");
			textInitialRepetitionsMax.setText("");
		}
	}

}
