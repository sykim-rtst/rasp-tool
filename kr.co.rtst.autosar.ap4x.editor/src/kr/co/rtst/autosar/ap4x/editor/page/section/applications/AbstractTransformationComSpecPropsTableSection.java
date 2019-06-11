package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

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

import autosar40.system.transformer.EndToEndTransformationComSpecProps;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

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
public abstract class AbstractTransformationComSpecPropsTableSection extends AbstractContentGUISection implements SelectionListener {

	public static final String[] TABLE_COLUMN				 = {
			"",
			"Disable end to end check",
			"Max delta counter",
			"Max error state init",
			"Max error state invalid",
			"Max error state valid",
			"Min ok state init",
			"Min ok state invalid",
			"Min ok state valid",
			"Window size",

	}; 
	public static final int[] TABLE_COLUMN_WIDTH				 = {
			0, 80 , 80 , 80 , 80, 80 , 80 , 80 , 80 , 80
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT , SWT.CENTER , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , 
			SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT , SWT.RIGHT
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
			false, false, false, false, false, 
			false, false, false, false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
			false, true, true, true, true,
			true, true, true, true, true
	}; 
	
	protected TableViewer tableTransformationComSpecProps;
	protected Button buttonAddTransformationComSpecProps;
	protected Button buttonRemoveTransformationComSpecProps;
	
	public AbstractTransformationComSpecPropsTableSection(AbstractAPEditorPage formPage, int style,
			String sectionTypeName) {
		super(formPage, style, sectionTypeName);
		// TODO Auto-generated constructor stub
	}

	protected void createTable(Composite parent) {
		tableTransformationComSpecProps = new TableViewer(APSectionUIFactory.createTable(
				parent, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				CLIENT_CONTENT_COLUMN-2,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableTransformationComSpecProps.setLabelProvider(new ModelLabelProvider());
		tableTransformationComSpecProps.setContentProvider(new ModelContentProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddTransformationComSpecProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveTransformationComSpecProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
	}
	
	protected void hookListeners() {
		tableTransformationComSpecProps.setColumnProperties(TABLE_COLUMN);
		tableTransformationComSpecProps.setCellEditors(new CellEditor[] {
				null,
				new ComboBoxCellEditor(tableTransformationComSpecProps.getTable(), APSectionUIFactory.COMBO_BOOLEAN_VALUE),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
//				new DialogCellEditor(tableTransformationComSpecProps.getTable()) {
//					
//					@Override
//					protected Object openDialogBox(Control cellEditorWindow) {
//						FileDialog fd = new FileDialog(cellEditorWindow.getShell(), SWT.MULTI|SWT.OPEN);
//						if(fd.open() != null) {
//							
//						}
//						return null;
//					}
//				},
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
				new TextCellEditor(tableTransformationComSpecProps.getTable()),
		});
		tableTransformationComSpecProps.setCellModifier(new ICellModifier() {
			
			@Override
			public void modify(final Object element, final String property, final Object value) {
				final EndToEndTransformationComSpecProps model = (EndToEndTransformationComSpecProps)((TableItem)element).getData();
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject arg) throws Exception {
						if(TABLE_COLUMN[1].equals(property)) {
							if( ((Integer)value) == 0 ) {
								model.setDisableEndToEndCheck(true);
							}else {
								model.setDisableEndToEndCheck(false);
							}
						}else if(TABLE_COLUMN[2].equals(property)) {
							model.setMaxDeltaCounter(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[3].equals(property)) {
							model.setMaxErrorStateInit(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[4].equals(property)) {
							model.setMaxErrorStateInvalid(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[5].equals(property)) {
							model.setMaxErrorStateValid(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[6].equals(property)) {
							model.setMinOkStateInit(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[7].equals(property)) {
							model.setMinOkStateInvalid(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[8].equals(property)) {
							model.setMinOkStateValid(Long.parseLong((String)value));
						}else if(TABLE_COLUMN[9].equals(property)) {
							model.setWindowSize(Long.parseLong((String)value));
						}
						return model;
					}
				});
//				if(TABLE_COLUMN[1].equals(property)) {
//					if( ((Integer)value) == 0 ) {
//						model.setDisableEndToEndCheck(true);
//					}else {
//						model.setDisableEndToEndCheck(false);
//					}
//				}else if(TABLE_COLUMN[2].equals(property)) {
//					model.setMaxDeltaCounter(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[3].equals(property)) {
//					model.setMaxErrorStateInit(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[4].equals(property)) {
//					model.setMaxErrorStateInvalid(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[5].equals(property)) {
//					model.setMaxErrorStateValid(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[6].equals(property)) {
//					model.setMinOkStateInit(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[7].equals(property)) {
//					model.setMinOkStateInvalid(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[8].equals(property)) {
//					model.setMinOkStateValid(Long.parseLong((String)value));
//				}else if(TABLE_COLUMN[9].equals(property)) {
//					model.setWindowSize(Long.parseLong((String)value));
//				}
				tableTransformationComSpecProps.refresh();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				EndToEndTransformationComSpecProps model = (EndToEndTransformationComSpecProps)element;
				if(TABLE_COLUMN[1].equals(property)) {
					return model.isSetDisableEndToEndCheck()?0:1;
				}else if(TABLE_COLUMN[2].equals(property)) {
					return Long.toString(model.getMaxDeltaCounter());
				}else if(TABLE_COLUMN[3].equals(property)) {
					return Long.toString(model.getMaxErrorStateInit());
				}else if(TABLE_COLUMN[4].equals(property)) {
					return Long.toString(model.getMaxErrorStateInvalid());
				}else if(TABLE_COLUMN[5].equals(property)) {
					return Long.toString(model.getMaxErrorStateValid());
				}else if(TABLE_COLUMN[6].equals(property)) {
					return Long.toString(model.getMinOkStateInit());
				}else if(TABLE_COLUMN[7].equals(property)) {
					return Long.toString(model.getMinOkStateInvalid());
				}else if(TABLE_COLUMN[8].equals(property)) {
					return Long.toString(model.getMinOkStateValid());
				}else if(TABLE_COLUMN[9].equals(property)) {
					return Long.toString(model.getWindowSize());
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
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
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
			if(element instanceof EndToEndTransformationComSpecProps) {
				EndToEndTransformationComSpecProps model = (EndToEndTransformationComSpecProps)element;
				switch(columnIndex) {
//				case 0: return "";
				case 1: return String.valueOf(model.isSetDisableEndToEndCheck());
				case 2: return String.valueOf(model.getMaxDeltaCounter());
				case 3: return String.valueOf(model.getMaxErrorStateInit());
				case 4: return String.valueOf(model.getMaxErrorStateInvalid());
				case 5: return String.valueOf(model.getMaxErrorStateValid());
				case 6: return String.valueOf(model.getMinOkStateInit());
				case 7: return String.valueOf(model.getMinOkStateInvalid());
				case 8: return String.valueOf(model.getMinOkStateValid());
				case 9: return String.valueOf(model.getWindowSize());
				}
			}
			return "";
		}
	}
}
