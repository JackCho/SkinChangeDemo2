package com.example.skinchangedemo2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * 
 * Description：使用assets下的资源实现换肤，演示了换肤的图片、文字大小、颜色、内容的切换。
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button mSkin1Btn, mSkin2Btn;
	private ImageView mImageView;
	private TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSkin1Btn = (Button) findViewById(R.id.btn_skin1);
		mSkin2Btn = (Button) findViewById(R.id.btn_skin2);
		mSkin1Btn.setOnClickListener(this);
		mSkin2Btn.setOnClickListener(this);
		
		mImageView = (ImageView) findViewById(R.id.image);
		mTextView = (TextView) findViewById(R.id.text);
	}

	private Map<String, SoftReference<Bitmap>> bmps = new HashMap<String, SoftReference<Bitmap>>();
	/**
	 * 通过assets资源路径获取位图
	 * @param path
	 *        资源地址
	 * @return
	 */
	private Bitmap getBitmap(String path) {
		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = getAssets().open(path);
			bitmap = BitmapFactory.decodeStream(is);
			bmps.put(path, new SoftReference<Bitmap>(bitmap));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	/**
	 * 通过assets资源路径获取Drawable
	 * @param path
	 *        资源地址
	 * @return
	 */
	private Drawable getDrawable(String path) {
		InputStream is = null;
		Drawable drawable = null;
		try {
			is = getAssets().open(path);
			drawable = Drawable.createFromStream(is, "");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return drawable;
	}

	private Properties properties = null;
	/**
	 * 初始化Properties
	 * @param path
	 *        properties的路径
	 * @return
	 */
	private Properties initProperties(String path) {
		try {
			InputStream is = getAssets().open(path);
			if (is != null) {
				properties = new Properties();
				properties.load(is);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 获取properties中的字体颜色
	 * @param name
	 * @return
	 */
	private String getTextColor(String name) {
		return getString(name);
	}
	
	/**
	 * 获取properties中的字体大小
	 * @param name
	 * @return
	 */
	private float getTextSize(String name) {
		return Float.parseFloat(getString(name));
	}
	
	/**
	 * 获取properties中的资源属性
	 * 使用前，必须进行初始化
	 * @param name
	 * @return
	 */
	private String getString(String name) {
		if (properties == null) {
			throw new RuntimeException("please call initProperties method before use");
		}
		return properties.getProperty(name);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_skin1:
			initProperties("skin1/res.properties");
			mImageView.setImageBitmap(getBitmap("skin1/skin_img.jpeg"));
			mTextView.setTextSize(getTextSize("test_size"));
			mTextView.setTextColor(Color.parseColor(getTextColor("text_color")));
			mTextView.setText(getString("text"));
			break;
		case R.id.btn_skin2:
			initProperties("skin2/res.properties");
			mImageView.setImageBitmap(getBitmap("skin2/skin_img.jpeg"));
			mTextView.setTextSize(getTextSize("test_size"));
			mTextView.setTextColor(Color.parseColor(getTextColor("text_color")));
			mTextView.setText(getString("text"));
			break;

		default:
			break;
		}
	}

}
