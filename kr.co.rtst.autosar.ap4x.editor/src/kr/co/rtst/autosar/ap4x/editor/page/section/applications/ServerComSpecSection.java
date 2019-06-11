package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.emf.ecore.EObject;
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

import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.swcomponent.communication.ServerComSpec;
import autosar40.swcomponent.components.PPortPrototype;
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
 * ServerComSpec
 * @author thkim
 *
 */
public class ServerComSpecSection extends AbstractContentGUISection implements SelectionListener{
	
	private Text textQueueLength;
	private Text textOperation;
	private Button buttonAddOperation;
	private Button buttonEraseOperation;

	public ServerComSpecSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_SERVER_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Server com spec");
		sectionPart.getSection().setDescription("Server com spec desc");
		
		textQueueLength = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Queue length: ", CLIENT_CONTENT_COLUMN-1, true);
		textOperation = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Operation: ", CLIENT_CONTENT_COLUMN-3, false);
		buttonAddOperation = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseOperation = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this); 
		
		textQueueLength.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						ServerComSpec input = getCastedInputDetail();
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
		ServerComSpec input = getCastedInputDetail();
		if(input != null) {
			textQueueLength.setText(String.valueOf(input.getQueueLength().longValue()));
			
			setOperationText(input);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		 if(e.getSource().equals(buttonAddOperation)) {
		 	ServerComSpec input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				EObject parent = input.eContainer();
				
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR && parent instanceof PPortPrototype) {
//				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final ClientServerOperation selectedType = ReferenceChoiceDelegator.choiceSingleMethod(buttonAddOperation.getShell(), apProject, (ServiceInterface)((PPortPrototype)parent).getProvidedInterface());
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
		 	ServerComSpec input = getCastedInputDetail();
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

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	private ServerComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof ServerComSpec) {
			return (ServerComSpec)getInputDetail();
		}
		return null;
	}

	private void setOperationText(ServerComSpec model){
		if(model.getOperation() != null) {
			textOperation.setText(model.getOperation().getShortName());
		}else {
			textOperation.setText("");
		}
	}
}
