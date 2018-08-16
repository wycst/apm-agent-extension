package com.boco.mis.opentrace.utils;

public class ArrayUtils {

	public static boolean contain(Object[] array,Object element) {
		for(Object item : array) {
			if(item == element) {
				return true;
			}
			if(item != null && item.equals(element)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断2个数组是不是子集关系
	 * @param parentArr
	 * @param childArr
	 * @return
	 */
	public static boolean subset(Object[] parentArr,Object[] childArr) {
		return subset(parentArr,childArr,false);
	}
	
	/**
	 * 判断2个数组是不是子集关系
	 * @param parentArr
	 * @param childArr
	 * @param head true 从0开始匹配
	 * @return
	 */
	public static boolean subset(Object[] parentArr,Object[] childArr,boolean head) {
		
		int childArrLen = childArr.length;
		int parentArrLen = parentArr.length;
		if(parentArrLen <  childArrLen ) {
			return false;
		}
		if(head) {
			for(int index = 0; index < childArrLen ; index ++) {
				Object childOfIndex = childArr[index];
				Object parentOfIndex = parentArr[index];
				if(childOfIndex == null) {
					if(parentOfIndex != null) return false;
				} else {
					if(childOfIndex != parentArr[index] && !childOfIndex.equals(parentOfIndex)) {
						return false;
					} 
				}
			}
		} else {
			for(int index = childArrLen - 1; index >= 0 ; index --) {
				Object childOfIndex = childArr[index];
				Object parentOfIndex = parentArr[index + (parentArrLen - childArrLen)];
				if(childOfIndex == null) {
					if(parentOfIndex != null) return false;
				} else {
					if(childOfIndex != parentArr[index] && !childOfIndex.equals(parentOfIndex)) {
						return false;
					} 
				}
			}
		}
		return true;
	}
	
	
}
