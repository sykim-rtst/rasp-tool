package kr.co.rtst.autosar.ap4x.editor.dialog;

import java.net.Inet6Address;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CellEditor;
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
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import autosar40.system.fibex.fibex4ethernet.ethernettopology.Ipv6Configuration;
import kr.co.rtst.autosar.ap4x.core.util.ValueUtil;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class DnsServerIPv6Dialog extends Dialog implements SelectionListener {
	
	private class AddressModel {
		String address;

		public AddressModel(String address) {
			this.address = address;
		}
		
	}
	
	public static final String[] TABLE_COLUMN				 = {
			"",
			"Address",

	}; 
	public static final int[] TABLE_COLUMN_WIDTH				 = {
			0, 300
	}; 
	public static final int[] TABLE_COLUMN_ALIGN			 = {
			SWT.LEFT , SWT.LEFT
	}; 
	public static final boolean[] TABLE_COLUMN_LISTENER			 = {
			false, false
	}; 
	public static final boolean[] TABLE_COLUMN_RESIZABLE			 = {
			false, true
	}; 
	
	private TableViewer tableDnsAddress;
	private Button buttonAddDnsAddress;
	private Button buttonRemoveDnsAddress;
	private List<AddressModel> dnsList;

	public DnsServerIPv6Dialog(Shell parentShell, Ipv6Configuration ipv6Configuration) {
		super(parentShell);
		this.dnsList = new ArrayList<>();
		for (Inet6Address inet6Address : ipv6Configuration.getDnsServerAddress()) {
			dnsList.add(new AddressModel(inet6Address.getHostAddress()));
		}
	}
	
	@Override
	protected Point getInitialSize() {
//		Point p = new Point(350, super.getInitialSize().y);
//		return p;
		return super.getInitialSize();
	}
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		super.create();
		initContent();
		setPageComplete();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("IPv6 DNS Configuration");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(APUILayoutFactory.getGridLayoutDefault(2, false));
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		tableDnsAddress = new TableViewer(APSectionUIFactory.createTable(
				comp, ToolTipFactory.get(""), 
				SWT.MULTI | SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL| SWT.H_SCROLL,
				1,
				TABLE_COLUMN, 
				TABLE_COLUMN_WIDTH, 
				TABLE_COLUMN_ALIGN, 
				TABLE_COLUMN_LISTENER, 
				TABLE_COLUMN_RESIZABLE, 
				null));
		tableDnsAddress.setLabelProvider(new ModelLabelProvider());
		tableDnsAddress.setContentProvider(new ModelContentProvider());
		Composite compButton = new Composite(comp, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddDnsAddress = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveDnsAddress = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		tableDnsAddress.setColumnProperties(TABLE_COLUMN);
		tableDnsAddress.setCellEditors(new CellEditor[] {
				null,
				new TextCellEditor(tableDnsAddress.getTable()),
		});
		
		tableDnsAddress.setCellModifier(new ICellModifier() {
			@Override
			public void modify(final Object element, final String property, final Object value) {
				if(TABLE_COLUMN[1].equals(property)) {
					((AddressModel)((TableItem)element).getData()).address = (String)value;
				}
				tableDnsAddress.refresh();
				setPageComplete();
			}
			
			@Override
			public Object getValue(Object element, String property) {
				if(TABLE_COLUMN[1].equals(property)) {
					return ((AddressModel)element).address;
				}
				return null;
			}
			
			@Override
			public boolean canModify(Object element, String property) {
				if(TABLE_COLUMN[1].equals(property)) {
					return true;
				}
				return false;
			}
		});
		return comp;
	}
	
	private void initContent() {
		tableDnsAddress.setInput(dnsList);
	}
	
	private void setPageComplete() {
		Button button = getButton(IDialogConstants.OK_ID);
		if(button != null && !button.isDisposed()) {
			button.setEnabled(validate());
		}
	}
	
	private boolean validate() {
		for(TableItem item:tableDnsAddress.getTable().getItems()) {
			if(!ValueUtil.isIPv6Address(item.getText(1))) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddDnsAddress)) {
			dnsList.add(new AddressModel("0000:0000:0000:0000:0000:0000:0000:0000"));
			tableDnsAddress.refresh();
			setPageComplete();
		} else if(e.getSource().equals(buttonRemoveDnsAddress)) {
			dnsList.removeAll(tableDnsAddress.getStructuredSelection().toList());
			tableDnsAddress.refresh();
			setPageComplete();
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	public List<String> getDnsList() {
		List<String> result = new ArrayList<>();
		for (AddressModel m : dnsList) {
			result.add(m.address);
		}
		return result;
	}
	
	private class ModelContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof List) {
				return ((List) inputElement).toArray();
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
			if(element instanceof AddressModel) {
				switch(columnIndex) {
				case 1: return ((AddressModel) element).address;
				}
			}
			return "";
		}
	}
	
}
