package org.mc.gallerydemo;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private ArrayList<Integer> imgList = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		InitImgList();
		Integer[] images = (Integer[])imgList.toArray(new Integer[imgList.size()]);//����ArrayList����������
		FlowImageAdapter adapter = new FlowImageAdapter(this, images, 380, 280);
		adapter.createReflectedImages();

		CoverFlow galleryFlow = (CoverFlow) findViewById(R.id.coverflow);
		galleryFlow.setAdapter(adapter);
		galleryFlow.setSelection(1);
	}
	
	private void InitImgList() {
		// ����ͼƬ���ݣ���demo����ȡ������Դ��ʵ��Ӧ���У����첽�����������ݣ�
		imgList.add(R.drawable.img01);
		imgList.add(R.drawable.img02);
		imgList.add(R.drawable.img03);
		imgList.add(R.drawable.img04);
	}

}
