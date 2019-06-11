package kr.co.rtst.autosar.ap4x.ide.consts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

/**
 * 문자 타입을 상수로 정의해둔 클래스이다.
 * @author ETRI
 *
 */
public abstract class APIDEFont {

	/**
	 * 일반 폰트 상수.
	 */
	public static final Font DEFAULT_TEXT = 
			new Font(null, "Courier New", 10, 0);
	
	/**
	 * 일반 폰트 상수.
	 */
	public static final Font CHECK_TEXT = 
			new Font(null, "Courier New", 10, SWT.BOLD);
	
	/**
	 * 작은 글씨(주로 시간 눈금 등 숫자를 표현하는데 사용한다.)
	 */
	public static final Font SMALL_TEXT = 
			new Font(null, "Courier New", 8, 0);

}
