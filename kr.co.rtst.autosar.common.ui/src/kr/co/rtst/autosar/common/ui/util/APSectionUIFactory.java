package kr.co.rtst.autosar.common.ui.util;

import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import kr.co.rtst.autosar.common.ui.CommonUIActivator;

/**
 * 메소드의 마지막에 붙은 명칭이 반환타입을 의미한다.
 * @author thkim
 *
 */
public class APSectionUIFactory {
	
	public static final String BUTTON_IMAGE_PLUS = "icons/plus.gif";
	public static final String BUTTON_IMAGE_MINUS = "icons/minus.gif";
	public static final String BUTTON_IMAGE_SEARCH = "icons/search.gif";
	public static final String BUTTON_IMAGE_ERASE = "icons/erase.gif";
	
	public static final String COMBO_ITEM_NONE = "NONE";
	public static final int COMBO_BOOLEAN_VALUE_INDEX_TRUE = 0;
	public static final int COMBO_BOOLEAN_VALUE_INDEX_FALSE = 1;
	public static final String[] COMBO_BOOLEAN_VALUE = new String[] {"true", "false"};
	
//	/**
//	 * 메소드의 마지막에 붙은 명칭이 반환타입을 의미한다.
//	 * @param parent
//	 * @param fragment
//	 * @return
//	 */
//	public static Text createShortNameText(Composite parent, String toolTip, int horizontalSpan) {
//		Label label = new Label(parent, SWT.NONE);
//		label.setText("Short name*: ");
//		
//		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
//		gData.horizontalSpan = horizontalSpan;
//		Text text = new Text(parent, SWT.BORDER);
//		text.setLayoutData(gData);
//		
//		return text;
//	}
//	
//	public static Text createShortNameFragmentText(Composite parent, String toolTip, int horizontalSpan) {
//		Label label = new Label(parent, SWT.NONE);
//		label.setText("Short name fragment: ");
//		
//		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
//		gData.horizontalSpan = horizontalSpan;
//		Text text = new Text(parent, SWT.BORDER);
//		text.setLayoutData(gData);
//		
//		return text;
//	}
	
	public static Label createLabel(Composite parent, String labelText, int horizontalSpan) {
		Label label = new Label(parent, SWT.NONE);
		GridData gData = new GridData();
		gData.horizontalSpan = horizontalSpan;
		label.setLayoutData(gData);
		label.setText(labelText);
		
		return label;
	}
	
	public static Text createLabelText(Composite parent, String toolTip, String labelText, int horizontalSpan, boolean editable) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.horizontalSpan = horizontalSpan;
		Text text = new Text(parent, SWT.BORDER);
		if(toolTip != null) {
			text.setToolTipText(toolTip);
		}
		text.setLayoutData(gData);
		
		text.setEditable(editable);
		
		return text;
	}
	
	public static Combo createLabelComboForBoolean(Composite parent, String toolTip, String labelText, int horizontalSpan, int defaultSelection, SelectionListener selectionListener) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.horizontalSpan = horizontalSpan;
		Combo combo = new Combo(parent, SWT.BORDER);
		if(toolTip != null) {
			combo.setToolTipText(toolTip);
		}
		combo.setLayoutData(gData);
		combo.setItems(COMBO_BOOLEAN_VALUE);
		if(defaultSelection>=0) {
			combo.select(defaultSelection);
		}
		
		if(selectionListener != null) {
			combo.addSelectionListener(selectionListener);
		}
		
		return combo;
	}
	
	public static Combo createLabelCombo(Composite parent, String toolTip, String labelText, int horizontalSpan, int defaultSelection, SelectionListener selectionListener, String... items) {
		Label label = new Label(parent, SWT.NONE);
		label.setText(labelText);
		
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.horizontalSpan = horizontalSpan;
		Combo combo = new Combo(parent, SWT.BORDER);
		if(toolTip != null) {
			combo.setToolTipText(toolTip);
		}
		combo.setLayoutData(gData);
		if(items != null) {
			combo.setItems(items);
			if(defaultSelection>=0) {
				combo.select(defaultSelection);
			}
		}
		
		if(selectionListener != null) {
			combo.addSelectionListener(selectionListener);
		}
		
		return combo;
	}
	
//	/**
//	 * 현재 사용되는 곳이 없음
//	 * @param parent
//	 * @param labelText
//	 * @param horizontalSpan
//	 * @param selectionListener
//	 * @return
//	 */
//	public static Button createButtonCheck(Composite parent, String labelText, int horizontalSpan, SelectionListener selectionListener) {
//		Button button = new Button(parent, SWT.CHECK);
//		button.setText(labelText);
//		
//		if(horizontalSpan>1) {
//			GridData gData = new GridData();
//			gData.horizontalSpan = horizontalSpan;
//			button.setLayoutData(gData);
//		}
//		
//		if(selectionListener != null) {
//			button.addSelectionListener(selectionListener);
//		}
//		
//		return button;
//	}
	
	public static Button createButtonImage(Composite parent, String toolTip, int style, String imageFile, int horizontalSpan, SelectionListener selectionListener) {
		Button button = new Button(parent, style);
		button.setImage(CommonUIActivator.getDefault().getCommonImageRegistry().getImage(imageFile));
		
		if(toolTip != null) {
			button.setToolTipText(toolTip);
		}
		
		if(horizontalSpan>1) {
			GridData gData = new GridData();
			gData.horizontalSpan = horizontalSpan;
			button.setLayoutData(gData);
		}
		
		if(selectionListener != null) {
			button.addSelectionListener(selectionListener);
		}
		
		return button;
	}
	
	public static ListViewer createLabelListViewer(Composite parent, String toolTip, String labelText, int horizontalSpan) {
		Label label = new Label(parent, SWT.NONE);
		GridData gData = new GridData();
		gData.verticalAlignment = GridData.BEGINNING;
		label.setLayoutData(gData);
		label.setText(labelText);
		
		gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.horizontalSpan = horizontalSpan;
		gData.heightHint = 80;
		ListViewer list = new ListViewer(parent);
		if(toolTip != null) {
			list.getList().setToolTipText(toolTip);
		}
		list.getList().setLayoutData(gData);
		
		return list;
	}
	
	public static Table createTable(Composite parent, String toolTip, int style, int horizontalSpan, String[] columnTitle, int[] colomnWidth, int[] align, boolean[] applyColumnListener, boolean[] columnResizable, SelectionListener listener){
		Table table = new Table(parent, style);
		if(toolTip != null) {
			table.setToolTipText(toolTip);
		}
		TableColumn column = null;
		for (int i = 0; i < columnTitle.length; i++) {
			column = new TableColumn(table, align[i]);
			column.setText(columnTitle[i]);
			column.setWidth(colomnWidth[i]);
			column.setResizable(columnResizable[i]);
			if(listener != null && applyColumnListener[i])
				column.addSelectionListener(listener);
		}
		
		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
		gData.horizontalSpan = horizontalSpan;
		gData.heightHint = 250;
		gData.widthHint = 500;
		table.setLayoutData(gData);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		return table;
	}
//	/**
//	 * 
//	 * @param parent
//	 * @param style
//	 * @param horizontalSpan
//	 * @param columnTitle
//	 * @param colomnWidth
//	 * @param align
//	 * @param applyColumnListener
//	 * @param columnResizable
//	 * @param listener
//	 * @param labelText
//	 * @param labelLeft
//	 * @return
//	 */
//	public static Table createLabelTable(Composite parent, String toolTip, int style, int horizontalSpan, String[] columnTitle, boolean headerVisible, int[] colomnWidth, int[] align, boolean[] applyColumnListener, boolean[] columnResizable, SelectionListener listener, String labelText, boolean labelLeft){
//		if(labelLeft) {
//			Label label = new Label(parent, SWT.NONE);
//			GridData gData = new GridData();
//			gData.verticalAlignment = GridData.BEGINNING;
//			label.setLayoutData(gData);
//			label.setText(labelText);
//		}else {
//			Label label = new Label(parent, SWT.NONE);
//			GridData gData = new GridData();
//			gData.horizontalSpan = horizontalSpan+2;
//			label.setLayoutData(gData);
//			label.setText(labelText);
//		}
//		
//		Table table = new Table(parent, style);
//		if(toolTip != null) {
//			table.setToolTipText(toolTip);
//		}
//		
//		TableColumn column = null;
//		for (int i = 0; i < columnTitle.length; i++) {
//			column = new TableColumn(table, align[i]);
//			column.setText(columnTitle[i]);
//			column.setWidth(colomnWidth[i]);
//			column.setResizable(columnResizable[i]);
//			if(listener != null && applyColumnListener[i])
//				column.addSelectionListener(listener);
//		}
//		
//		GridData gData = new GridData(GridData.FILL_HORIZONTAL);
//		gData.horizontalSpan = horizontalSpan;
//		gData.heightHint = 150;
//		table.setLayoutData(gData);
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
//		
//		return table;
//	}
	
//	public static Table createLabelTableAsList(Composite parent, String toolTip, int style, int horizontalSpan, String labelText){
//		Label label = new Label(parent, SWT.NONE);
//		GridData gData = new GridData();
//		gData.verticalAlignment = GridData.BEGINNING;
//		label.setLayoutData(gData);
//		label.setText(labelText);
//		
//		Table table = new Table(parent, style);
//		if(toolTip != null) {
//			table.setToolTipText(toolTip);
//		}
//		
//		TableColumn column = new TableColumn(table, SWT.LEFT);
//		column.setText("");
////		column.setWidth(colomnWidth[i]);
////		column.setResizable(columnResizable[i]);
//		
//		gData = new GridData(GridData.FILL_HORIZONTAL);
//		gData.horizontalSpan = horizontalSpan;
//		gData.heightHint = 80;
//		table.setLayoutData(gData);
//		table.setHeaderVisible(false);
//		table.setLinesVisible(false);
//		
//		return table;
//	}
	
}
