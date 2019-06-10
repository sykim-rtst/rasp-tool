package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

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

import autosar40.adaptiveplatform.applicationdesign.comspec.SenderCapabilityEnum;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.genericstructure.varianthandling.attributevaluevariationpoints.AttributevaluevariationpointsFactory;
import autosar40.genericstructure.varianthandling.attributevaluevariationpoints.BooleanValueVariationPoint;
import autosar40.swcomponent.communication.CommunicationFactory;
import autosar40.swcomponent.communication.SenderComSpec;
import autosar40.swcomponent.communication.TransmissionAcknowledgementRequest;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.datatype.dataprototypes.AutosarDataPrototype;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * SenderComSpec
 * @author thkim
 *
 */
public class SenderComSpecSection extends AbstractContentGUISection implements SelectionListener{
	
	private Text textDataElement;
	private Button buttonAddDataElement;
	private Button buttonEraseDataElement;
	
	private Text textDataUpdatePeriod;
	private Combo combosenderCapability;
	private Text textTransmissionAcknowledge;
	private Combo comboUsesEndToEndProtection;
	
	public SenderComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_SENDER_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Sender com spec");
		sectionPart.getSection().setDescription("Sender com spec desc");
		
		textDataElement = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data element*: ", CLIENT_CONTENT_COLUMN-3, false);
		buttonAddDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textDataUpdatePeriod = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data update period: ", CLIENT_CONTENT_COLUMN-1, true);
		
		combosenderCapability = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Sender capability: ", CLIENT_CONTENT_COLUMN-1, 0, this, "willSend", "wontSend");
		
		textTransmissionAcknowledge = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Transmission acknowledge: ", CLIENT_CONTENT_COLUMN-1, true);
		
		comboUsesEndToEndProtection = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Uses end to end protection", CLIENT_CONTENT_COLUMN-1, 0, this);
		
		textDataUpdatePeriod.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SenderComSpec input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setDataUpdatePeriod(Double.parseDouble(textDataUpdatePeriod.getText()));
							}catch(NumberFormatException e) {
								input.setDataUpdatePeriod(0d);
							}
						}
						return model;
					}
				});
			}
		});
		
		textTransmissionAcknowledge.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SenderComSpec input = getCastedInputDetail();
						if(input != null) {
							try {
								TransmissionAcknowledgementRequest ta = CommunicationFactory.eINSTANCE.createTransmissionAcknowledgementRequest();
								ta.setTimeout(Double.parseDouble(textTransmissionAcknowledge.getText()));
								input.setTransmissionAcknowledge(ta);
							}catch(NumberFormatException e) {
								input.setDataUpdatePeriod(0d);
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
		SenderComSpec input = getCastedInputDetail();
		if(input != null) {
			setDataElementText(input);
			
			textDataUpdatePeriod.setText(String.valueOf(input.getDataUpdatePeriod().longValue()));
			
			if(input.getSenderCapability() != null) {
				if(input.getSenderCapability().equals(SenderCapabilityEnum.WILL_SEND)) {
					combosenderCapability.select(0);
				} else if(input.getSenderCapability().equals(SenderCapabilityEnum.WONT_SEND)) {
					combosenderCapability.select(1);
				}
			} 
			
			if(input.getTransmissionAcknowledge() != null) {
				textTransmissionAcknowledge.setText(String.valueOf(input.getTransmissionAcknowledge().getTimeout().longValue()));
			}else {
				textTransmissionAcknowledge.setText("");
			}
			
			if(input.getUsesEndToEndProtection() == null) {
				final BooleanValueVariationPoint bvp = AttributevaluevariationpointsFactory.eINSTANCE.createBooleanValueVariationPoint();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setUsesEndToEndProtection(bvp);
						return model;
					}
				});
			}
			// TODO
//			comboUsesEndToEndProtection.select(input.getUsesEndToEndProtection().isSetBlueprintValue()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(combosenderCapability)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(combosenderCapability.getSelectionIndex()) {
						case 0:input.setSenderCapability(SenderCapabilityEnum.WILL_SEND);break;
						case 1:input.setSenderCapability(SenderCapabilityEnum.WONT_SEND);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(comboUsesEndToEndProtection)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						if(input.getUsesEndToEndProtection() == null) {
							BooleanValueVariationPoint bvp = AttributevaluevariationpointsFactory.eINSTANCE.createBooleanValueVariationPoint();
							input.setUsesEndToEndProtection(bvp);
						}
						// TODO
//						input.getUsesEndToEndProtection().setBlueprintValue(arg0);(Boolean.parseBoolean(comboUsesEndToEndProtection.getText()));
						return model;
					}
				});
			}
		} else if(e.getSource().equals(buttonAddDataElement)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof PPortPrototype) {
					final AutosarDataPrototype selectedType = ReferenceChoiceDelegator.choiceSingleDataElement(buttonAddDataElement.getShell(), apProject, (ServiceInterface)((PPortPrototype)parent).getProvidedInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setDataElement(selectedType);
								return model;
							}
						});
						setDataElementText(input);
					}
				}
				
			}
		} else if(e.getSource().equals(buttonEraseDataElement)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setDataElement(null);
						return model;
					}
				});
				setDataElementText(input);
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	private SenderComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof SenderComSpec) {
			return (SenderComSpec)getInputDetail();
		}
		return null;
	}
	
	private void setDataElementText(SenderComSpec model) {
		if(model.getDataElement() != null) {
			textDataElement.setText(model.getDataElement().getShortName());
		}else {
			textDataElement.setText("");
		}
	}
	
}
