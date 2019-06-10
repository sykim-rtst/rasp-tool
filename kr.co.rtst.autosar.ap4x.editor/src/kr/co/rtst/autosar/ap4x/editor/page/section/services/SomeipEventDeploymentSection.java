package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.ServiceinterfacedeploymentFactory;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipEventDeployment;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipEventGroup;
import autosar40.adaptiveplatform.deployment.serviceinterfacedeployment.SomeipServiceInterfaceDeployment;
import autosar40.swcomponent.datatype.dataprototypes.VariableDataPrototype;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfaceDeploymentModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.listener.PositiveDecimalVerifier;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class SomeipEventDeploymentSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textEvent;
	private Button buttonAddEvent;
	private Button buttonEraseEvent;
	
	private Text textEventId;
	private Text textEventGroupId;
	private Text textMaximumSegmentLength;
	private Text textSeparationTime;
	private Combo comboTransportProtocol;
	
	public SomeipEventDeploymentSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfaceDeploymentModelManager.TYPE_NAME_SOMEIP_EVENT_DEPLOYMENT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		SomeipEventDeployment input = getCastedInputDetail();
		if(input != null) {
			if(input.getTransportProtocol() != null) {
				if(input.getTransportProtocol().equals(TransportLayerProtocolEnum.TCP)) {
					comboTransportProtocol.select(0);
				} else if(input.getTransportProtocol().equals(TransportLayerProtocolEnum.UDP)) {
					comboTransportProtocol.select(1);
				}
			}
			
			textEventId.setText(String.valueOf(input.getEventId()));
			
			setEventGroupIdText(input);
			
			textMaximumSegmentLength.setText(String.valueOf(input.getMaximumSegmentLength()));
			textSeparationTime.setText(String.valueOf(input.getSeparationTime()));
			
			setEventText(input);
		}
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		// TODO Auto-generated method stub
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Someip event deployment");
		sectionPart.getSection().setDescription("Someip event deployment desc");
		
		textEvent = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Event*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddEvent = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseEvent = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textEventId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Event id*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textEventGroupId = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Event group id*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textMaximumSegmentLength = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Maximum segment length: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textSeparationTime = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Separation time: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		comboTransportProtocol = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Transport protocol*:", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "tcp", "udp");
		hookTextListener();
	}
	
	private void hookTextListener() {
		textEventId.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipEventDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								
								
								input.setEventId(Long.parseLong(textEventId.getText()));
							}catch(NumberFormatException e) {
								input.setEventId(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textEventGroupId.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				SomeipEventDeployment input = getCastedInputDetail();
				SomeipEventGroup eventGroup = null;
				
				for (SomeipEventGroup group : getCastedInputObject().getEventGroups()) {
					if(group.getEventGroupId() == Long.parseLong(textEventGroupId.getText())) {
						eventGroup = group;
						break;
					}
				}
				
				if(eventGroup == null) {
					// 새로운 그룹을 만들어야 한다.
					eventGroup = ServiceinterfacedeploymentFactory.eINSTANCE.createSomeipEventGroup();
					eventGroup.setEventGroupId(Long.parseLong(textEventGroupId.getText()));
					eventGroup.getEvents().add(input);
					
					final SomeipEventGroup newEventGroup = eventGroup;
					
					doTransactionalOperation(new IAPTransactionalOperation() {
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							getCastedInputObject().getEventGroups().add(newEventGroup);
							
							for (SomeipEventGroup group : getCastedInputObject().getEventGroups()) {
								if(group.getEvents().contains(input)) {
									group.getEvents().remove(input);
								}
							}
							return model;
						}
					});
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				
			}
		});
		textEventGroupId.addVerifyListener(new PositiveDecimalVerifier());
		
		textMaximumSegmentLength.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipEventDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setMaximumSegmentLength(Long.parseLong(textMaximumSegmentLength.getText()));
							}catch(NumberFormatException e) {
								input.setMaximumSegmentLength(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textSeparationTime.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipEventDeployment input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setSeparationTime(Double.parseDouble(textMaximumSegmentLength.getText()));
							}catch(NumberFormatException e) {
								input.setSeparationTime(0d);
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
			final SomeipEventDeployment input = getCastedInputDetail();
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
		}else if(e.getSource().equals(buttonAddEvent)) {
			final SomeipEventDeployment input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof SomeipServiceInterfaceDeployment) {
					final VariableDataPrototype selectedType = ReferenceChoiceDelegator.choiceSingleEvent(buttonAddEvent.getShell(), apProject, (ServiceInterface)((SomeipServiceInterfaceDeployment)parent).getServiceInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setEvent(selectedType);
								return model;
							}
						});
						
						setEventText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseEvent)) {
			final SomeipEventDeployment input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setEvent(null);
						return model;
					}
				});
				
				setEventText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	@Override
	protected void notifyShortNameChanged() {
		super.notifyShortNameChanged();
		final SomeipEventDeployment input = getCastedInputDetail();
		
	}
	
	private void setEventText(SomeipEventDeployment model) {
		if(model.getEvent() != null) {
			textEvent.setText(model.getEvent().getShortName());
		}else {
			textEvent.setText("");
		}
	}
	
	private void setEventGroupIdText(SomeipEventDeployment model) {
		for (SomeipEventGroup eventGroup : getCastedInputObject().getEventGroups()) {
			if(eventGroup.getEvents().contains(model)) {
				textEventGroupId.setText(String.valueOf(eventGroup.getEventGroupId()));
				return;
			}
		}
		textEventGroupId.setText("");
	}
	
	private SomeipEventDeployment getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipEventDeployment) {
			return (SomeipEventDeployment)getInputDetail();
		}
		return null;
	}
	
	private SomeipServiceInterfaceDeployment getCastedInputObject() {
		return (SomeipServiceInterfaceDeployment)getAPEditorPage().getAPFormEditor().getEditorInputObject();
	}

}
