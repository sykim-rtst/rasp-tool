package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.swcomponent.communication.ClientComSpec;
import autosar40.swcomponent.communication.ReceiverComSpec;
import autosar40.system.transformer.EndToEndTransformationComSpecProps;
import autosar40.system.transformer.TransformerFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;

public class ClientComSpecTableSection extends AbstractTransformationComSpecPropsTableSection /*extends AbstractContentGUISection implements SelectionListener*/{
	
	public ClientComSpecTableSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_CLIENT_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Transformation com spec props");
		sectionPart.getSection().setDescription("Transformation com spec props desc");

		createTable(parent);
		
		hookListeners();
	}
	
	@Override
	public void initUIContents() {
		ClientComSpec input = getCastedInputDetail();
		if(input != null) {
			tableTransformationComSpecProps.setInput(input.getTransformationComSpecProps());
		}
	}
	
	private ClientComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof ClientComSpec) {
			return (ClientComSpec)getInputDetail();
		}
		return null;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddTransformationComSpecProps)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						EndToEndTransformationComSpecProps item = TransformerFactory.eINSTANCE.createEndToEndTransformationComSpecProps();
						input.getTransformationComSpecProps().add(item);
						return model;
					}
				});
				
				tableTransformationComSpecProps.refresh();
				
				System.out.println("추가 후 개수:"+input.getTransformationComSpecProps().size());
			}
		} else if(e.getSource().equals(buttonRemoveTransformationComSpecProps)) {
			final ClientComSpec input = getCastedInputDetail();
			if(input != null) {
				if(!tableTransformationComSpecProps.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getTransformationComSpecProps().removeAll(tableTransformationComSpecProps.getStructuredSelection().toList());
							return model;
						}
					});
					
					tableTransformationComSpecProps.refresh();
					
					System.out.println("삭제 후 개수:"+input.getTransformationComSpecProps().size());
				}
			}
		}		
	}

}
