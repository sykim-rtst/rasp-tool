package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.commonstructure.modedeclaration.ModeDeclaration;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import autosar40.system.transformer.EndToEndTransformationComSpecProps;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.handler.APModelInitializer;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class MachineTableSection extends AbstractContentGUISection implements SelectionListener {

	public static final String[] TABLE_COLUMN				 = {
			"",
			"Mode name",
			"Mode value",
			"Initial Mode",
	}; 
	public static final int[] TABLE_COLUMN_WIDTH				 = {
			0, 120, 120, 120
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT , SWT.LEFT , SWT.LEFT , SWT.LEFT
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
			false, false, false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
			false, true, true, true
	}; 
	
	private TableViewer tableMachineModeMachine;
	private Button buttonAddMachineModeMachine;
	private Button buttonRemoveMachineModeMachine;
	
	public MachineTableSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_MACHINE);
	}

	@Override
	public void initUIContents() {
		// TODO Auto-generated method stub
		Machine input = getCastedInputDetail();
		if(input != null) {
			if(input.getMachineModeMachines() == null || input.getMachineModeMachines().isEmpty()) {
				APModelInitializer.initializeMachine_ModeDeclarationGroupPrototype(getAPProject(), input);
			}
			tableMachineModeMachine.setInput(input.getMachineModeMachines().get(0).getType().getModeDeclarations());
			tableMachineModeMachine.refresh();
		}
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		sectionPart.getSection().setText("Machine mode machine");
		sectionPart.getSection().setDescription("Machine mode machine desc");
		
		tableMachineModeMachine = new TableViewer(APSectionUIFactory.createTable(
				parent, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableMachineModeMachine.setLabelProvider(new ModelLabelProvider());
		tableMachineModeMachine.setContentProvider(new ModelContentProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddMachineModeMachine = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveMachineModeMachine = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		hookListeners();
	}
	
	private Machine getCastedInputDetail() {
		if(getInputDetail() instanceof Machine) {
			return (Machine)getInputDetail();
		}
		return null;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddMachineModeMachine)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						ModeDeclaration md = ModedeclarationFactory.eINSTANCE.createModeDeclaration();
						input.getMachineModeMachines().get(0).getType().getModeDeclarations().add(md);
						return model;
					}
				});
				
				tableMachineModeMachine.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveMachineModeMachine)) {
			final Machine input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.getMachineModeMachines().get(0).getType().getModeDeclarations().removeAll(tableMachineModeMachine.getStructuredSelection().toList());
						return model;
					}
				});
				
				tableMachineModeMachine.refresh();
			}
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	protected void hookListeners() {
		tableMachineModeMachine.setColumnProperties(TABLE_COLUMN);
		tableMachineModeMachine.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableMachineModeMachine.getTable()),
				new TextCellEditor(tableMachineModeMachine.getTable()),
				new ComboBoxCellEditor(tableMachineModeMachine.getTable(), APSectionUIFactory.COMBO_BOOLEAN_VALUE),
		});
		tableMachineModeMachine.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final ModeDeclaration model = (ModeDeclaration)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(TABLE_COLUMN[1].equals(property)) {
							model.setShortName((String)value);
						}else if(TABLE_COLUMN[2].equals(property)) {
							model.setValue(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[3].equals(property)) {
							if( ((Integer)value) == 0 ) {
								getCastedInputDetail().getMachineModeMachines().get(0).getType().setInitialMode(model);
							}else {
								if(model.equals(getCastedInputDetail().getMachineModeMachines().get(0).getType().getInitialMode())){
									getCastedInputDetail().getMachineModeMachines().get(0).getType().setInitialMode(null);
								}
							}
						}
						return model;
					}
				});
				tableMachineModeMachine.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				ModeDeclaration model = (ModeDeclaration)element;
				if(TABLE_COLUMN[1].equals(property)) {
					return model.getShortName();
				}else if(TABLE_COLUMN[2].equals(property)) {
					return Long.toString(model.getValue());
				}else if(TABLE_COLUMN[3].equals(property)) {
					return model.equals(getCastedInputDetail().getMachineModeMachines().get(0).getType().getInitialMode())?0:1;
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if(property.trim().length()>0) {
					return true;
				}
				return false;
			}
		});
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
//			if(element instanceof EndToEndTransformationComSpecProps) {
//				if(columnIndex == 0) {
//					return AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
//				}
//			}
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof ModeDeclaration) {
				ModeDeclaration model = (ModeDeclaration)element;
				switch(columnIndex) {
//				case 0: return "";
				case 1: return model.getShortName();
				case 2: return String.valueOf(model.getValue());
				case 3: return model.equals(getCastedInputDetail().getMachineModeMachines().get(0).getType().getInitialMode())?APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE]:APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE];
				}
			}
			return "";
		}
	}
}
