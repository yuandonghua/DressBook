
package cn.dressbook.ui.zxing;

/**
 * Encapsulates some simple formatting logic, to aid refactoring in {@link ContactEncoder}.
 *
 * @author Sean Owen
 */
interface Formatter {

  /**
   * @param value value to format
   * @param index index of value in a list of values to be formatted
   * @return formatted value
   */
  CharSequence format(CharSequence value, int index);
  
}
