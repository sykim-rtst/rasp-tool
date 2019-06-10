package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.adaptiveplatform.deployment.serviceinstance.TransportLayerProtocolEnum;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipMethodDeployment;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.swcomponent.portinterface.ClientServerOperation;
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

public class SomeipMethodDeploymentSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textMethod;
	private Button buttonAddMethod;
	private Button buttonEraseMethod;
	
	private Text textMethodId;
	private Text textMaximumSegmentLengthRequest;
	private Text textMaximumSegmentLengthResponse;
	private Text textSeparationTimeRequest;
	private Text textSeparationTimeResponse;
	private Combo comboTransportProtocol;
	
	public SomeipMethodDeploymentSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_METHOD_DEPLOYMENT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		SomeipMethodDeployment input = getCastedInputDetail();
		if(input != null) {
			if(input.getTransportProtocol() != null) {
				if(input.getTransportProtocol().equals(TransportLayerProtocolEnum.TCP)) {
					comboTransportProtocol.select(0);
				} else if(input.getTransportProtocol().equals(TransportLayerProtocolEnum.UDP)) {
					comboTransportProtocol.select(1);
				}
			}
			
			textMethodId.setText(String.valueOf(input.getMethodId()));
			textMaximumSegmentLengthRequest.setText(String.valueOf(input.getMaximumSegmentLengthRequest()));
			textMaximumSegmentLengthResponse.setText(String.valueOf(input.getMaximumSegmentLengthResponse()));
			textSeparationTimeRequest.setText(String.valueOf(input.getSeparationTimeRequest()));
			textSeparationTimeResponse.setText(String.valueOf(input.getSeparationTimeResponse()));
			
			setMethodText(input);
		}
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		// TODO Auto-generated method stub
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Someip method deployment");
		sectionPart.getSection().setDescription("Someip method deployment desc");
		
		textMethod = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Method*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddMethod = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseMethod = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textMethodId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Method id*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textMaximumSegmentLengthRequest = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Maximum segment length request: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textMaximumSegmentLengthResponse = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Maximum segment length response: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textSeparationTimeRequest = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Separation time request: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textSeparationTimeResponse = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Separation time response: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		comboTransportProtocol = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Transport protocol*:", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "tcp", "udp");
		hookTextListener();
	}
	
	private void hookTextListener() {
		textMethodId.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipMethodDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setMethodId(Long.parseLong(textMethodId.getText()));
							}catch(NumberFormatException e) {
								input.setMethodId(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textMaximumSegmentLengthRequest.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipMethodDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setMaximumSegmentLengthRequest(Long.parseLong(textMaximumSegmentLengthRequest.getText()));
							}catch(NumberFormatException e) {
								input.setMaximumSegmentLengthRequest(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textMaximumSegmentLengthResponse.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipMethodDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setMaximumSegmentLengthResponse(Long.parseLong(textMaximumSegmentLengthResponse.getText()));
							}catch(NumberFormatException e) {
								input.setMaximumSegmentLengthResponse(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textSeparationTimeRequest.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipMethodDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setSeparationTimeRequest(Double.parseDouble(textSeparationTimeRequest.getText()));
							}catch(NumberFormatException e) {
								input.setSeparationTimeRequest(0d);
							}
						}
						return model;
					}
				});
			}
		});
		
		textSeparationTimeResponse.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipMethodDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setSeparationTimeResponse(Double.parseDouble(textSeparationTimeResponse.getText()));
							}catch(NumberFormatException e) {
								input.setSeparationTimeResponse(0d);
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
		if(e.getSource().equals(comboTransportProtocol)) {
			final SomeipMethodDeployment input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboTransportProtocol.getSelectionIndex()) {
						case 0:input.setTransportProtocol(TransportLayerProtocolEnum.TCP);break;
						case 1:input.setTransportProtocol(TransportLayerProtocolEnum.UDP);break;
						}
						return model;
					}
				});
			}
		}else if(e.getSource().equals(buttonAddMethod)) {
			final SomeipMethodDeployment input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof SomeipServiceInterfaceDeployment) {
					final ClientServerOperation selectedType = ReferenceChoiceDelegator.choiceSingleMethod(buttonAddMethod.getShell(), apProject, (ServiceInterface)((SomeipServiceInterfaceDeployment)parent).getServiceInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setMethod(selectedType);
								return model;
							}
						});
						
						setMethodText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseMethod)) {
			final SomeipMethodDeployment input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setMethod(null);
						return model;
					}
				});
				
				setMethodText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private void setMethodText(SomeipMethodDeployment model) {
		if(model.getMethod() != null) {
			textMethod.setText(model.getMethod().getShortName());
		}else {
			textMethod.setText("");
		}
	}
	
	private SomeipMethodDeployment getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipMethodDeployment) {
			return (SomeipMethodDeployment)getInputDetail();
		}
		return null;
	}
	
	private SomeipServiceInterfaceDeployment getCastedInputObject() {
		return (SomeipServiceInterfaceDeployment)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}

}
