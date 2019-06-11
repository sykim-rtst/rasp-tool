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

import autosar40.adaptiveplatform.machinemanifest.ProcessToMachineMapping;
import autosar40.commonstructure.modedeclaration.ModeDeclaration;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class ProcessTableAppModeSection extends AbstractContentGUISection implements SelectionListener {

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
	
	private TableViewer tableAppModeMachine;
	private Button buttonAddAppModeMachine;
	private Button buttonRemoveAppModeMachine;
	
	public ProcessTableAppModeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_PROCESS_TO_MACHINE_MAPPING);
	}

	@Override
	public void initUIContents() {
		ProcessToMachineMapping input = getCastedInputDetail();
		if(input != null) {
			tableAppModeMachine.setInput(getTableInput());
			tableAppModeMachine.refresh();
		}
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		sectionPart.getSection().setText("Application mode machine");
		sectionPart.getSection().setDescription("Application mode machine desc");

		tableAppModeMachine = new TableViewer(APSectionUIFactory.createTable(
				parent, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableAppModeMachine.setLabelProvider(new ModelLabelProvider());
		tableAppModeMachine.setContentProvider(new ModelContentProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddAppModeMachine = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveAppModeMachine = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		hookListeners();
	}
	
	private ProcessToMachineMapping getCastedInputDetail() {
		if(getInputDetail() instanceof ProcessToMachineMapping) {
			return (ProcessToMachineMapping)getInputDetail();
		}
		return null;
	}
	
	private EList<ModeDeclaration> getTableInput() {
		return getCastedInputDetail().getProcess().getProcessStateMachine().getType().getModeDeclarations();
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddAppModeMachine)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						ModeDeclaration md = ModedeclarationFactory.eINSTANCE.createModeDeclaration();
						md.setShortName(getCastedInputDetail().getShortName()+IAPModelManager.SHORT_NAME_SEPARATOR);
						getTableInput().add(md);
						return model;
					}
				});
				
				tableAppModeMachine.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveAppModeMachine)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						getTableInput().removeAll(tableAppModeMachine.getStructuredSelection().toList());
						return model;
					}
				});
				
				tableAppModeMachine.refresh();
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
	
	protected void hookListeners() {
		tableAppModeMachine.setColumnProperties(TABLE_COLUMN);
		tableAppModeMachine.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableAppModeMachine.getTable()),
				new TextCellEditor(tableAppModeMachine.getTable()),
				new ComboBoxCellEditor(tableAppModeMachine.getTable(), APSectionUIFactory.COMBO_BOOLEAN_VALUE),
		});
		tableAppModeMachine.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final ModeDeclaration model = (ModeDeclaration)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(TABLE_COLUMN[1].equals(property)) {
							model.setShortName(getCastedInputDetail().getShortName() + IAPModelManager.SHORT_NAME_SEPARATOR + (String)value);
						}else if(TABLE_COLUMN[2].equals(property)) {
							model.setValue(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[3].equals(property)) {
							if( ((Integer)value) == 0 ) {
								getCastedInputDetail().getProcess().getProcessStateMachine().getType().setInitialMode(model);
							}else {
								if(model.equals(getCastedInputDetail().getProcess().getProcessStateMachine().getType().getInitialMode())){
									getCastedInputDetail().getProcess().getProcessStateMachine().getType().setInitialMode(null);
								}
							}
						}
						return model;
					}
				});
				tableAppModeMachine.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				ModeDeclaration model = (ModeDeclaration)element;
				if(TABLE_COLUMN[1].equals(property)) {
					if(model.getShortName().equals(getCastedInputDetail().getShortName()+IAPModelManager.SHORT_NAME_SEPARATOR)) {
						return "";
					}else if(model.getShortName().startsWith(getCastedInputDetail().getShortName()+IAPModelManager.SHORT_NAME_SEPARATOR)) {
						return model.getShortName().substring(getCastedInputDetail().getShortName().length()+1);
					}else {
						return model.getShortName();
					}
//					return model.getShortName();
				}else if(TABLE_COLUMN[2].equals(property)) {
					return Long.toString(model.getValue());
				}else if(TABLE_COLUMN[3].equals(property)) {
					return model.equals(getCastedInputDetail().getProcess().getProcessStateMachine().getType().getInitialMode())?0:1;
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
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if(element instanceof ModeDeclaration) {
				ModeDeclaration model = (ModeDeclaration)element;
				switch(columnIndex) {
//				case 0: return "";
				case 1: //return model.getShortName();
					if(model.getShortName().equals(getCastedInputDetail().getShortName()+IAPModelManager.SHORT_NAME_SEPARATOR)) {
						return "";
					}else if(model.getShortName().startsWith(getCastedInputDetail().getShortName()+IAPModelManager.SHORT_NAME_SEPARATOR)) {
						return model.getShortName().substring(getCastedInputDetail().getShortName().length()+1);
					}else {
						return model.getShortName();
					}
				case 2: return String.valueOf(model.getValue());
				case 3: return model.equals(getCastedInputDetail().getProcess().getProcessStateMachine().getType().getInitialMode())?APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE]:APSectionUIFactory.COMBO_BOOLEAN_VALUE[APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE];
				}
			}
			return "";
		}
	}

}
