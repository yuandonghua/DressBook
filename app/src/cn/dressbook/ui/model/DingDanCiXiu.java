package cn.dressbook.ui.model;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * @description: 订单中的刺绣信息
 * @author:ydh
 * @data:2016-5-4上午11:48:16
 */
public class DingDanCiXiu implements Parcelable {
	private String words;
	private String colorPic;
	private String fontPic;
	public DingDanCiXiu() {
	}


	public String getWords() {
		return words;
	}


	public void setWords(String words) {
		this.words = words;
	}


	public String getColorPic() {
		return colorPic;
	}


	public void setColorPic(String colorPic) {
		this.colorPic = colorPic;
	}


	public String getFontPic() {
		return fontPic;
	}


	public void setFontPic(String fontPic) {
		this.fontPic = fontPic;
	}


	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(words);
		out.writeString(colorPic);
		out.writeString(fontPic);

	}

	private DingDanCiXiu(Parcel in) {
		words = in.readString();
		colorPic = in.readString();
		fontPic= in.readString();
	}

	public static final Creator<DingDanCiXiu> CREATOR = new Creator<DingDanCiXiu>() {
		@Override
		public DingDanCiXiu createFromParcel(Parcel in) {
			return new DingDanCiXiu(in);
		}

		@Override
		public DingDanCiXiu[] newArray(int size) {
			return new DingDanCiXiu[size];
		}
	};

}
