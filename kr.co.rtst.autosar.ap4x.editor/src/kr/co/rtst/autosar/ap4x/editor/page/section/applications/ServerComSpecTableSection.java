package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.swcomponent.communication.ServerComSpec;
import autosar40.system.transformer.EndToEndTransformationComSpecProps;
import autosar40.system.transformer.TransformerFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;

/**
 * TransformationComSpecProps
 * 
 * EndToEndTransformationComSpecProps (이것이 사용됨)
 * TransformerFactory
 * 
 * UserDefinedTransformationComSpecProps
 * CommunicationFactory
 * @author thkim
 *
 */
public class ServerComSpecTableSection extends AbstractTransformationComSpecPropsTableSection/*AbstractContentGUISection implements SelectionListener*/{
	
//	public static final String[] TABLE_COLUMN				 = {
//			"",
//			"Disable end to end check",
//			"Max delta counter",
//			"Max error state init",
//			"Max error state invalid",
//			"Max error state valid",
//			"Min ok state init",
//			"Min ok state invalid",
//			"Min ok state valid",
//			"Window size",
//
//	}; 
//	public static final int[] TABLE_COLUMN_WIDTH				 = {
//			20, 80 , 80 , 80 , 80, 80 , 80 , 80 , 80 , 80
//	}; 
//	public static final int[] TABLE_COLUMN_ALIGN			 = {
//			SWT.LEFT , SWT.CENTER , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , 
//			SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT
//	}; 
//	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
//			false, false, false, false, false, 
//			false, false, false, false, false
//	}; 
//	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
//			false, true, true, true, true,
//			true, true, true, true, true
//	}; 
//	
//	private TableViewer tableTransformationComSpecProps;
//	private Button buttonAddTransformationComSpecProps;
//	private Button buttonRemoveTransformationComSpecProps;

	public ServerComSpecTableSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_SERVER_COM_SPEC);
	}
	
//	protected SectionPart createSectionPart(Composite parent, FormToolkit toolkit) {
//		SectionPart part = new SectionPart(parent, toolkit, Section.EXPANDED|Section.TWISTIE|Section.SHORT_TITLE_BAR|Section.DESCRIPTION|Section.TITLE_BAR);
////		GridData gData = new GridData();
////		gData.verticalAlignment = SWT.TOP;
////		part.getSection().setLayoutData(gData);
//		return part;
////		return new SectionPart(parent, toolkit, Section.TWISTIE|Section.SHORT_TITLE_BAR|Section.DESCRIPTION|Section.TITLE_BAR);
//	}

	@Override
	public void initUIContents() {
		ServerComSpec input = getCastedInputDetail();
		if(input != null) {
			tableTransformationComSpecProps.setInput(input.getTransformationComSpecProps());
		}
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		// TODO Auto-generated method stub
		sectionPart.getSection().setText("Transformation com spec props");
		sectionPart.getSection().setDescription("Transformation com spec props desc");
		
		createTable(parent);
		
		hookListeners();
		
//		tableTransformationComSpecProps = new TableViewer(APSectionUIFactory.createTable(
//				parent, ToolTipFactory.get(""), 
//				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
//				CLIENT_CONTENT_COLUMN-2,
//				TABLE_COLUMN, 
//				TABLE_COLUMN_WIDTH, 
//				TABLE_COLUMN_ALIGN, 
//				TABLE_COLUMN_LISTENER, 
//				TABLE_COLUMN_RESIZABLE, 
//				null));
//		tableTransformationComSpecProps.setLabelProvider(new ModelLabelProvider());
//		tableTransformationComSpecProps.setContentProvider(new ModelContentProvider());
//		Composite compButton = new Composite(parent, SWT.NONE);
//		compButton.setLayout(APUIFactory.getGridLayoutDefault(1, false));
//		GridData gData = new GridData(GridData.FILL_VERTICAL);
//		gData.verticalAlignment = SWT.TOP;
//		compButton.setLayoutData(gData);
//		buttonAddTransformationComSpecProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
//		buttonRemoveTransformationComSpecProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
	}
	
//	@Override
//	protected void fillSectionToolBarActions(ToolBarManager toolBarManager) {
//		super.fillSectionToolBarActions(toolBarManager);
//		IAction actionPlus = new Action() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//			}
//		};
//		actionPlus.setImageDescriptor(AP4xEditorActivator.getDefault().getEditorImageRegistry().getImageDescriptor(EditorIconPath.PLUS));
//		
//		IAction actionMinus = new Action() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				super.run();
//			}
//		};
//		actionMinus.setImageDescriptor(AP4xEditorActivator.getDefault().getEditorImageRegistry().getImageDescriptor(EditorIconPath.MINUS));
//		toolBarManager.add(actionPlus);
//		toolBarManager.add(actionMinus);
//	}
	
	private ServerComSpec getCastedInputDetail() {
		if(getInputDetail() instanceof ServerComSpec) {
			return (ServerComSpec)getInputDetail();
		}
		return null;
	}

//	private class ModelContentProvider implements IStructuredContentProvider {
//
//		@Override
//		public Object[] getElements(Object inputElement) {
//			if(inputElement instanceof EList) {
//				return ((EList) inputElement).toArray();
//			}
//			return new Object[0];
//		}
//		
//	}
//	
//	private class ModelLabelProvider extends LabelProvider implements ITableLabelProvider {
//		private BasicExplorerLabelProvider apModelLabelProvider;
//		
//		public ModelLabelProvider() {
//			apModelLabelProvider = new BasicExplorerLabelProvider();
//		}
//		@Override
//		public Image getColumnImage(Object element, int columnIndex) {
//			if(element instanceof EndToEndTransformationComSpecProps) {
//				if(columnIndex == 0) {
//					return AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
//				}
//			}
//			return null;
//		}
//
//		@Override
//		public String getColumnText(Object element, int columnIndex) {
//			if(element instanceof EndToEndTransformationComSpecProps) {
//				EndToEndTransformationComSpecProps model = (EndToEndTransformationComSpecProps)element;
//				switch(columnIndex) {
////				case 0: return "";
//				case 1: return String.valueOf(model.isSetDisableEndToEndCheck());
//				case 2: return String.valueOf(model.getMaxDeltaCounter());
//				case 3: return String.valueOf(model.isSetMaxErrorStateInit());
//				case 4: return String.valueOf(model.getMaxErrorStateInvalid());
//				case 5: return String.valueOf(model.getMaxErrorStateValid());
//				case 6: return String.valueOf(model.getMinOkStateInit());
//				case 7: return String.valueOf(model.getMinOkStateInvalid());
//				case 8: return String.valueOf(model.getMinOkStateValid());
//				case 9: return String.valueOf(model.getWindowSize());
//				}
//			}
//			return "";
//		}
//	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddTransformationComSpecProps)) {
			final ServerComSpec input = getCastedInputDetail();
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
			final ServerComSpec input = getCastedInputDetail();
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
//
//	@Override
//	public void widgetDefaultSelected(SelectionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
}
