package kr.co.rtst.autosar.ap4x.core.util;

public class ValueUtil {

	/**
	 * 16진수로 표현된 문자열을 받아 10진수로 변화하여 반환한다.
	 * @param hexString
	 * @return
	 */
	public static byte parseHex2Byte(String hexString) {
		if(hexString == null || hexString.trim().length()==0 || hexString.trim().length()>2/*바이트 범위를 넘어선다.*/){
			return 0;
		}
		hexString = hexString.toUpperCase();
		byte value = convertHexToDec(hexString.charAt(0));
		for (int i = 1; i < hexString.length(); i++) {
			value = (byte)(value * 16 + convertHexToDec(hexString.charAt(i)));
		}
		return value;
	}
	
	/**
	 * 16진수로 표현된 문자열을 받아 10진수로 변화하여 반환한다.
	 * @param hexString
	 * @return
	 */
	public static int parseHex2Int(String hexString) {
		if(hexString == null || hexString.trim().length()==0){
			return 0;
		}
		hexString = hexString.toUpperCase();
		int value = convertHexToDec(hexString.charAt(0));
		for (int i = 1; i < hexString.length(); i++) {
			value = value * 16 + convertHexToDec(hexString.charAt(i));
		}
		return value;
	}
	
	/**
	 * 16진수 한문자를 받아 10진수로 변환하여 반환한다.
	 * @param ch
	 * @return
	 */
	private static byte convertHexToDec(char ch) {
		if (ch == 'A') {
			return 10;
		} else if (ch == 'B') {
			return 11;
		} else if (ch == 'C') {
			return 12;
		} else if (ch == 'D') {
			return 13;
		} else if (ch == 'E') {
			return 14;
		} else if (ch == 'F') {
			return 15;
		} else if (ch <= '9' && ch >= '0') {
			return (byte)(ch - '0');
		} else {
			throw new NumberFormatException("Wrong character: " + ch);
		}
	}

	/**
	 * 주어진 문자열이 IP Address에 합당한지 검사하여 그 결과를 바환한다.
	 * @param checkValue
	 * @return
	 */
	public static boolean isIPv4Address(String checkValue){
		if(checkValue != null){
			return checkValue.matches("(((\\d{1,2}|(1\\d{2})|(2[0-4]\\d)|(25[0-5]))\\.){3}((\\d{1,2})|(1\\d{2})|(2[0-4]\\d)|(25[0-5])))");
		}
		return false;
	}

	public static boolean isIPv6Address(String checkValue){
			if(checkValue != null){
				return checkValue.matches("^(?:[A-F0-9]{1,4}:){7}[A-F0-9]{1,4}$");
	//			return true;
			}
			return false;
		}
	
}
