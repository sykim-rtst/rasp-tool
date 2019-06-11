package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.comspec.ClientCapabilityEnum;
import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.swcomponent.communication.ClientComSpec;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.portinterface.ClientServerOperation;
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
 * ClientComSpec
 * @author thkim
 *
 */
public class ClientComSpecSection extends AbstractContentGUISection implements SelectionListener{
	
	private Combo comboClientCapability;
	private Text textGetter;
	private Button buttonAddGetter;
	private Button buttonEraseGetter;
	private Text textSetter;
	private Button buttonAddSetter;
	private Button buttonEraseSetter;
	private Text textOperation;
	private Button buttonAddOperation;
	private Button buttonEraseOperation;
	
	public ClientComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_CLIENT_COM_SPEC);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Client com spec");
		sectionPart.getSection().setDescription("Client com spec desc");
		
		comboClientCapability = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Client Capability: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "willCall", "wontCall");
		
		textGetter = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Getter: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddGetter = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseGetter = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textSetter = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Setter: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddSetter = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseSetter = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textOperation = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Opeartion: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddOperation = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseOperation = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
	}
	
	@Override
	public void initUIContents() {
		ClientComSpec input = getCastedInputDetail();
		if(input != null) {
			if(input.getClientCapability() != null) {
				if(input.getClientCapability().equals(ClientCapabilityEnum.WILL_CALL)) {
					comboClientCapability.select(0);
				} else if(input.getClientCapability().equals(ClientCapabilityEnum.WONT_CALL)) {
					comboClientCapability.select(1);
				}
			} 
			
			setGetterText(input);
			setSetterText(input);
			setOperationText(input);
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboClientCapability)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboClientCapability.getSelectionIndex()) {
						case 0:input.setClientCapability(ClientCapabilityEnum.WILL_CALL);break;
						case 1:input.setClientCapability(ClientCapabilityEnum.WONT_CALL);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(buttonAddGetter)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof RPortPrototype) {
//				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final Field selectedType = ReferenceChoiceDelegator.choiceSingleField(buttonAddGetter.getShell(), apProject, (ServiceInterface)((RPortPrototype)parent).getRequiredInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setGetter(selectedType);
								return model;
							}
						});
						
						setGetterText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseGetter)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setGetter(null);
						return model;
					}
				});
				
				setGetterText(input);
			}
		} else if(e.getSource().equals(buttonAddSetter)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof RPortPrototype) {
//				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final Field selectedType = ReferenceChoiceDelegator.choiceSingleField(buttonAddSetter.getShell(), apProject, (ServiceInterface)((RPortPrototype)parent).getRequiredInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setSetter(selectedType);
								return model;
							}
						});
						
						setSetterText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseSetter)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setSetter(null);
						return model;
					}
				});
				
				setSetterText(input);
			}
		} else if(e.getSource().equals(buttonAddOperation)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof RPortPrototype) {
//				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final ClientServerOperation selectedType = ReferenceChoiceDelegator.choiceSingleMethod(buttonAddOperation.getShell(), apProject, (ServiceInterface)((RPortPrototype)parent).getRequiredInterface());
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setOperation(selectedType);
								return model;
							}
						});
						
						setOperationText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseOperation)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setOperation(null);
						return model;
					}
				});
				
				setOperationText(input);
			}
		}
		
	}
	
	private ClientComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof ClientComSpec) {
			return (ClientComSpec)getInputDetail();
		}
		return null;
	}
	
	private void setGetterText(ClientComSpec model){
		if(model.getGetter() != null) {
			textGetter.setText(model.getGetter().getShortName());
		}else {
			textGetter.setText("");
		}
	}
	
	private void setSetterText(ClientComSpec model){
		if(model.getSetter() != null) {
			textSetter.setText(model.getSetter().getShortName());
		}else {
			textSetter.setText("");
		}
	}
	
	private void setOperationText(ClientComSpec model){
		if(model.getOperation() != null) {
			textOperation.setText(model.getOperation().getShortName());
		}else {
			textOperation.setText("");
		}
	}
}
