package kr.co.rtst.autosar.common.ui.util;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

public class APUILayoutFactory {
	
	/**
	 * 편집기의 섹션을 위한 GridLayout 객체를 생성하고 반환한다.
	 * @param numColumns
	 * @param makeColumnsEqualWidth
	 * @return
	 */
	public static GridLayout getGridLayoutForSectionContent(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		return layout;
	}

	/**
	 * 기본 속성을 가진 GridLayout 객체를 생성하고 반환한다.
	 * @param numColumns
	 * @param makeColumnsEqualWidth
	 * @return
	 */
	public static GridLayout getGridLayoutDefault(int numColumns, boolean makeColumnsEqualWidth) {
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		return layout;
	}
	
	/**
	 * 
	 * @param numColumns
	 * @param makeColumnsEqualWidth
	 * @param margin
	 * @return
	 */
	public static GridLayout getGridLayoutWithMargin(int numColumns, boolean makeColumnsEqualWidth, int margin) {
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		layout.marginBottom = margin;
		layout.marginHeight = margin;
		layout.marginLeft = margin;
		layout.marginRight = margin;
		layout.marginTop = margin;
		layout.marginWidth = margin;
		return layout;
	}
	
	/**
	 * Margin 값이 모두 0으로 설정된 GridLayout 객체를 생성하고 반환한다.
	 * @param numColumns
	 * @param makeColumnsEqualWidth
	 * @return
	 */
	public static GridLayout getGridLayoutNoMargin(int numColumns, boolean makeColumnsEqualWidth) {
		return getGridLayoutWithMargin(numColumns, makeColumnsEqualWidth, 0);
	}
	
	/**
	 * 
	 * @param parent
	 * @param style
	 * @param columnTitle
	 * @param colomnWidth
	 * @param align
	 * @param applyColumnListener
	 * @param listener
	 * @return
	 */
	public static Table createTable(Composite parent, int style, String[] columnTitle, int[] colomnWidth, int[] align, boolean[] applyColumnListener, SelectionListener listener){
		Table table = new Table(parent, style);
	
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn column = null;
		for (int i = 0; i < columnTitle.length; i++) {
			column = new TableColumn(table, align[i]);
			column.setText(columnTitle[i]);
			column.setWidth(colomnWidth[i]);
			column.setResizable(true);
			if(listener != null && applyColumnListener[i])
				column.addSelectionListener(listener);
		}
		
		return table;
	}
	
//	/**
//	 * GUI에 나타내기 위한 테이블을 생성해 주는 메소드 (TableViewer에서 활용될 것이다.)
//	 * 만들어진 테이블의 컬럼의 넓이는 고정된다. 또한 각 컬럼에는 정렬을 위한 액션 리스너의 등록이 필요하다.
//	 * 거의 모든 UI가 테이블을 포함하고 있으므로 이 메소드는 기능성으로 만들어 재사용하도록 설계되었다.
//	 * @param parent
//	 * @param columnTitle
//	 * @param colomnWidth
//	 * @param listener
//	 * @return
//	 */
//	public static Table createTable(Composite parent, int style, String[] columnTitle, int[] colomnWidth, int[] align, boolean[] applyColumnListener, boolean[] columnResizable, SelectionListener listener){
//		Table table = new Table(parent, style);
//	
//		table.setHeaderVisible(true);
//		table.setLinesVisible(true);
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
//		return table;
//	}
	
//	public static Tree createTree(Composite parent, int style, String[] columnTitle, int[] colomnWidth, int[] align, boolean[] applyColumnListener, boolean[] columnResizable, SelectionListener listener){
//		Tree tree = new Tree(parent, style);
//	
//		tree.setHeaderVisible(true);
//		tree.setLinesVisible(true);
//		
//		TreeColumn column = null;
//		for (int i = 0; i < columnTitle.length; i++) {
//			column = new TreeColumn(tree, align[i]);
//			column.setText(columnTitle[i]);
//			column.setWidth(colomnWidth[i]);
//			column.setResizable(columnResizable[i]);
//			if(listener != null && applyColumnListener[i])
//				column.addSelectionListener(listener);
//		}
//		
//		return tree;
//	}
	
}
