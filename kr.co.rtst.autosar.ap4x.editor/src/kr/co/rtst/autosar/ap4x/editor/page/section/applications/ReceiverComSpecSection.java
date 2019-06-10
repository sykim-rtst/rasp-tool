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

import autosar40.adaptiveplatform.applicationdesign.comspec.ReceiverCapabilityEnum;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.swcomponent.communication.QueuedReceiverComSpec;
import autosar40.swcomponent.communication.ReceiverComSpec;
import autosar40.swcomponent.components.RPortPrototype;
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
 * ReceiverComSpec
 * QueuedReceiverComSpec
 * @author thkim
 *
 */
public class ReceiverComSpecSection extends AbstractContentGUISection implements SelectionListener {
	
	private Text textDataElement;
	private Button buttonAddDataElement;
	private Button buttonEraseDataElement;
	private Text textDataUpdatePeriod;
	private Combo comboReceiverCapability;
	private Text textQueueLength;
	
	public ReceiverComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_RECEIVER_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Receiver com spec");
		sectionPart.getSection().setDescription("Receiver com spec desc");
		
		textDataElement = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data element*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseDataElement = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textDataUpdatePeriod = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Data update period: ", CLIENT_CONTENT_COLUMN-1, true);
		comboReceiverCapability = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Receiver capability: ", CLIENT_CONTENT_COLUMN-1, 0, this, "willReceive", "wontReceive");
		
		textQueueLength = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Queue length: ", CLIENT_CONTENT_COLUMN-1, true);
		
		textDataUpdatePeriod.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						QueuedReceiverComSpec input = getCastedInputDetail();
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
		
		textQueueLength.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						QueuedReceiverComSpec input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setQueueLength(Long.parseLong(textQueueLength.getText()));
							}catch(NumberFormatException e) {
								input.setQueueLength(0l);
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
		QueuedReceiverComSpec input = getCastedInputDetail();
		if(input != null) {
			textDataUpdatePeriod.setText(String.valueOf(input.getDataUpdatePeriod().longValue()));
			
			if(input.getReceiverCapability() != null) {
				if(input.getReceiverCapability().equals(ReceiverCapabilityEnum.WILL_RECEIVE)) {
					comboReceiverCapability.select(0);
				} else if(input.getReceiverCapability().equals(ReceiverCapabilityEnum.WONT_RECEIVE)) {
					comboReceiverCapability.select(1);
				}
			} 
			
			textQueueLength.setText(input.getQueueLength().toString());
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboReceiverCapability)) {
			final ReceiverComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboReceiverCapability.getSelectionIndex()) {
						case 0:input.setReceiverCapability(ReceiverCapabilityEnum.WILL_RECEIVE);break;
						case 1:input.setReceiverCapability(ReceiverCapabilityEnum.WONT_RECEIVE);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(buttonAddDataElement)) {
			final QueuedReceiverComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof RPortPrototype) {
					final AutosarDataPrototype selectedType = ReferenceChoiceDelegator.choiceSingleDataElement(buttonAddDataElement.getShell(), apProject, (ServiceInterface)((RPortPrototype)parent).getRequiredInterface());
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
			final QueuedReceiverComSpec input = getCastedInputDetail();
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
	
	private QueuedReceiverComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof QueuedReceiverComSpec) {
			return (QueuedReceiverComSpec)getInputDetail();
		}
		return null;
	}
	
	private void setDataElementText(QueuedReceiverComSpec model) {
		if(model.getDataElement() != null) {
			textDataElement.setText(model.getDataElement().getShortName());
		}else {
			textDataElement.setText("");
		}
	}

}
