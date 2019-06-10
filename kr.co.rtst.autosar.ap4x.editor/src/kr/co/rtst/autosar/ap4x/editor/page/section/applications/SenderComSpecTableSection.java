package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.swcomponent.communication.CommunicationFactory;
import autosar40.swcomponent.communication.CompositeNetworkRepresentation;
import autosar40.swcomponent.communication.SenderComSpec;
import autosar40.swcomponent.portinterface.instancerefs.ApplicationCompositeElementInPortInterfaceInstanceRef;
import autosar40.swcomponent.portinterface.instancerefs.InstancerefsFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

/**
 * CommunicationFactory
 * CompositeNetworkRepresentation
 * @author thkim
 *
 */
public class SenderComSpecTableSection extends AbstractContentGUISection implements SelectionListener{
	
	public static final String[] TABLE_COLUMN				 = {
			"",
			"Leaf element",

	}; 
	public static final int[] TABLE_COLUMN_WIDTH				 = {
			0, 500
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT , SWT.CENTER
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
			false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
			false, true
	}; 
	
	private TableViewer tableCompositeNetworkRepresentation;
	private Button buttonAddCompositeNetworkRepresentation;
	private Button buttonRemoveCompositeNetworkRepresentation;
	
	public SenderComSpecTableSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_SENDER_COM_SPEC);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		sectionPart.getSection().setText("Composite network representation");
		sectionPart.getSection().setDescription("Composite network representation desc");
		
		tableCompositeNetworkRepresentation = new TableViewer(APSectionUIFactory.createTable(
				parent, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableCompositeNetworkRepresentation.setLabelProvider(new ModelLabelProvider());
		tableCompositeNetworkRepresentation.setContentProvider(new ModelContentProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddCompositeNetworkRepresentation = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveCompositeNetworkRepresentation = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
	}
	
	@Override
	public void initUIContents() {
		SenderComSpec input = getCastedInputDetail();
		if(input != null) {
			tableCompositeNetworkRepresentation.setInput(input.getCompositeNetworkRepresentations());
		}
	}
	
	private class ModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class ModelLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if(element instanceof CompositeNetworkRepresentation) {
				
			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof CompositeNetworkRepresentation) {
				switch(columnIndex) {
				case 1: return (((CompositeNetworkRepresentation) element).getLeafElement()!=null&&((CompositeNetworkRepresentation) element).getLeafElement().getTargetDataPrototype()!=null)?((CompositeNetworkRepresentation) element).getLeafElement().getTargetDataPrototype().getShortName():"none";
				}
			}
			return "";
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddCompositeNetworkRepresentation)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
//						CommunicationFactory
//						CompositeNetworkRepresentation
						CompositeNetworkRepresentation compositeNetworkRepresentation = CommunicationFactory.eINSTANCE.createCompositeNetworkRepresentation();
						ApplicationCompositeElementInPortInterfaceInstanceRef leafElement = InstancerefsFactory.eINSTANCE.createApplicationCompositeElementInPortInterfaceInstanceRef();
//						leafElement.setTargetDataPrototype(); // TODO
						compositeNetworkRepresentation.setLeafElement(leafElement);
//						compositeNetworkRepresentation.set
						input.getCompositeNetworkRepresentations().add(compositeNetworkRepresentation);
						return model;
					}
				});
				
				tableCompositeNetworkRepresentation.refresh();
				
				System.out.println("추가 후 개수:"+input.getCompositeNetworkRepresentations().size());
			}
		} else if(e.getSource().equals(buttonRemoveCompositeNetworkRepresentation)) {
			final SenderComSpec input = getCastedInputDetail();
			if(input != null) {
				if(!tableCompositeNetworkRepresentation.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getCompositeNetworkRepresentations().removeAll(tableCompositeNetworkRepresentation.getStructuredSelection().toList());
							return model;
						}
					});
					
					tableCompositeNetworkRepresentation.refresh();
					
					System.out.println("삭제 후 개수:"+input.getCompositeNetworkRepresentations().size());
				}
			}
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private SenderComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof SenderComSpec) {
			return (SenderComSpec)getInputDetail();
		}
		return null;
	}

}
