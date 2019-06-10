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

import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMapping;
import autosar40.adaptiveplatform.deployment.machine.ProcessToMachineMappingSet;
import autosar40.adaptiveplatform.deployment.process.CommandLineOptionKindEnum;
import autosar40.adaptiveplatform.deployment.process.ProcessFactory;
import autosar40.adaptiveplatform.deployment.process.StartupOption;
import autosar40.commonstructure.modedeclaration.ModedeclarationFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class ProcessTableStartupConfigSection extends AbstractContentGUISection implements SelectionListener {
	
	public static final String[] OPTION_KIND_VALUE 				= {
			"commandLineLongForm",
			"commandLineShortForm",
			"commandLineSimpleForm",
	};

	public static final String[] TABLE_COLUMN				 	= {
			"",
			"Option argument",
			"Option kind",
			"Option name",
	}; 
	public static final int[] TABLE_COLUMN_WIDTH			 	= {
			0, 120, 120, 120
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 	= {
			SWT.LEFT , SWT.LEFT , SWT.LEFT , SWT.LEFT
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER		 	= {
			false, false, false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE		= {
			false, true, true, true
	}; 
	
	private TableViewer tableStartupOption;
	private Button buttonAddStartupOption;
	private Button buttonRemoveStartupOption;
	
	public ProcessTableStartupConfigSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_PROCESS_TO_MACHINE_MAPPING);
	}

	@Override
	public void initUIContents() {
		ProcessToMachineMapping input = getCastedInputDetail();
		if(input != null) {
			tableStartupOption.setInput(getTableInput());
			tableStartupOption.refresh();
		}
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		sectionPart.getSection().setText("Startup option");
		sectionPart.getSection().setDescription("Startup option desc");
		
		tableStartupOption = new TableViewer(APSectionUIFactory.createTable(
				parent, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableStartupOption.setContentProvider(new ModelContentProvider());
		tableStartupOption.setLabelProvider(new ModelLabelProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddStartupOption = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveStartupOption = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		hookListeners();

	}
	
	private ProcessToMachineMapping getCastedInputDetail() {
		if(getInputDetail() instanceof ProcessToMachineMapping) {
			return (ProcessToMachineMapping)getInputDetail();
		}
		return null;
	}
	
	private EList<StartupOption> getTableInput() {
		return getCastedInputDetail().getProcess().getModeDependentStartupConfigs().get(0).getStartupConfig().getStartupOptions();
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddStartupOption)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						StartupOption option = ProcessFactory.eINSTANCE.createStartupOption();
						getTableInput().add(option);
						return model;
					}
				});
				
				tableStartupOption.refresh();
			}
		} else if(e.getSource().equals(buttonRemoveStartupOption)) {
			final ProcessToMachineMapping input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						getTableInput().removeAll(tableStartupOption.getStructuredSelection().toList());
						return model;
					}
				});
				
				tableStartupOption.refresh();
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
	}
	
	protected void hookListeners() {
		tableStartupOption.setColumnProperties(TABLE_COLUMN);
		tableStartupOption.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableStartupOption.getTable()),
				new ComboBoxCellEditor(tableStartupOption.getTable(), OPTION_KIND_VALUE),
				new TextCellEditor(tableStartupOption.getTable()),
		});
		tableStartupOption.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final StartupOption model = (StartupOption)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(TABLE_COLUMN[1].equals(property)) {
							model.setOptionArgument((String)value);
						}else if(TABLE_COLUMN[2].equals(property)) {
							switch( ((Integer)value) ) {
							case 0:
								model.setOptionKind(CommandLineOptionKindEnum.COMMAND_LINE_LONG_FORM);
								break;
							case 1:
								model.setOptionKind(CommandLineOptionKindEnum.COMMAND_LINE_SHORT_FORM);
								break;
							case 2:
								model.setOptionKind(CommandLineOptionKindEnum.COMMAND_LINE_SIMPLE_FORM);
								break;
							}
						}else if(TABLE_COLUMN[3].equals(property)) {
							model.setOptionName((String)value);
						}
						return model;
					}
				});
				tableStartupOption.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				StartupOption model = (StartupOption)element;
				if(TABLE_COLUMN[1].equals(property)) {
					return model.getOptionArgument();
				}else if(TABLE_COLUMN[2].equals(property)) {
					if(CommandLineOptionKindEnum.COMMAND_LINE_SHORT_FORM.equals(model.getOptionKind())) {
						return 1;
					} else if(CommandLineOptionKindEnum.COMMAND_LINE_SIMPLE_FORM.equals(model.getOptionKind())) {
						return 2;
					} else {
						return 0;
					}
				}else if(TABLE_COLUMN[3].equals(property)) {
					return model.getOptionName();
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
			if(element instanceof StartupOption) {
				StartupOption model = (StartupOption)element;
				switch(columnIndex) {
//				case 0: return "";
				case 1: return model.getOptionArgument();
				case 2: {
					if(CommandLineOptionKindEnum.COMMAND_LINE_SHORT_FORM.equals(model.getOptionKind())) {
						return OPTION_KIND_VALUE[1];
					} else if(CommandLineOptionKindEnum.COMMAND_LINE_SIMPLE_FORM.equals(model.getOptionKind())) {
						return OPTION_KIND_VALUE[2];
					} else {
						return OPTION_KIND_VALUE[0];
					}
				}
				case 3: return model.getOptionName();
				}
			}
			return "";
		}
	}

}
