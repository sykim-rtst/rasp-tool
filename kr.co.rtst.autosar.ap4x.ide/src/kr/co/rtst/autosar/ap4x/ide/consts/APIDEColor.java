package kr.co.rtst.autosar.ap4x.ide.consts;

import org.eclipse.swt.SWT; 
import org.eclipse.swt.graphics.Color; 
import org.eclipse.swt.graphics.RGB; 
import org.eclipse.swt.widgets.Display; 

/**
 * 프로파일링 및 모니터링 도구에서 사용될 색상 객체를 상수로 정의한 클래스이다.
 * @author ETRI
 *
 */
public abstract class APIDEColor {
	
	public static final Color DARK_RED				 					= Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED);
	public static final Color ORANGE			 					= new Color(null, 0xFF , 0x99 , 0x00); 

	public static final Color BLACK 			 = 
			Display.getDefault().getSystemColor(SWT.COLOR_BLACK); 
	public static final Color WHITE 			 = 
			Display.getDefault().getSystemColor(SWT.COLOR_WHITE); 
	public static final Color DARK_GRAY 		 = 
			Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY); 
	public static final Color VERY_DARK_GRAY	 = 
			new Color(null , new RGB(64 , 64 , 64)); 
	public static final Color GRAY 				 = 
			Display.getDefault().getSystemColor(SWT.COLOR_GRAY); 
	
	public static final Color BLUE 				 = 
			Display.getDefault().getSystemColor(SWT.COLOR_BLUE); 
	public static final Color LIGHT_BLUE							= new Color(null , new RGB(0x10 , 0x10 , 0xFF));
	public static final Color GREEN				 = 
			Display.getDefault().getSystemColor(SWT.COLOR_GREEN); 
	public static final Color DARK_GREEN		 = 
			Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN); 
	public static final Color RED				 = 
			Display.getDefault().getSystemColor(SWT.COLOR_RED); 
	
	public static final Color YELLOW			 = 
			Display.getDefault().getSystemColor(SWT.COLOR_YELLOW); 
	public static final Color MAGENTA			 = 
			Display.getDefault().getSystemColor(SWT.COLOR_MAGENTA); 
	
	public static final Color SKY_BLUE							= new Color(null , new RGB(0x0 , 0xFF , 0xFF));
	public static final Color PINK							= new Color(null, 0xFE, 0x2E, 0xF7);
	
	/**
	 * 이벤트 프로파일링 그래프의 프로세스 영역 배경 색상.
	 */
	public static final Color PROCESS_NAME_BG	 = 
			new Color(Display.getDefault() , 0xFF , 0xFF , 0xEE); 
	
	/**
	 *  이벤트 정보 창의 배경 색상.
	 */
	public static final Color EVENT_INFO_TEXT	 = PROCESS_NAME_BG;
	
	/**
	 * 오버뷰에 그려지는 그래프 라인 색상.
	 */
	public static final Color OVERVIEW_LINE		 = new Color(null, 0x56, 0x56, 0xCC);
	/**
	 * 라인마커의 라인 색상
	 */
	public static final Color LINEMARK			 = PINK;
	/**
	 * 라인마커의 끝점 색상
	 */
	public static final Color LINEMARK_POINT	 = PINK;
}
