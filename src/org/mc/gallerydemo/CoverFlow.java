package org.mc.gallerydemo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

public class CoverFlow extends Gallery {

	// Camera����ʵ��ImageView�ľ���任
	private Camera mCamera = new Camera();
	// ��ImageView��ת�����Ƕ�
	private int mMaxRotationAngle = 45;
	// ����ImageView���ŵ����ֵ
	private int mMaxZoom = -180;
	// CoverFlow������
	private int mCoveflowCenter;

	public CoverFlow(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		// When this property is set to true, this ViewGroup supports static
		// transformations on children
		this.setStaticTransformationsEnabled(true);
	}

	public CoverFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	public CoverFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);
	}

	// Get the max rotational angle of the image
	public int getMaxRotationAngle() {
		return mMaxRotationAngle;
	}

	// Set the max rotational angle of each image
	public void setMaxRotationAngle(int maxRotationAngle) {
		mMaxRotationAngle = maxRotationAngle;
	}

	// Get the Max zoom of the centre image
	public int getMaxZoom() {
		return mMaxZoom;
	}

	// Set the max zoom of the centre image
	public void setMaxZoom(int maxZoom) {
		mMaxZoom = maxZoom;
	}

	// Get the Centre of the Coverflow
	private int getCenterOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2
				+ getPaddingLeft();
	}

	// Get the Centre of the View
	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}

	protected boolean getChildStaticTransformation(View child, Transformation t) {

		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth();
		int rotationAngle = 0;

		t.clear();
		t.setTransformationType(Transformation.TYPE_MATRIX);

		if (childCenter == mCoveflowCenter) {
			transformImageBitmap((ImageView) child, t, 0);
		} else {
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle) {
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle
						: mMaxRotationAngle;
			}
			transformImageBitmap((ImageView) child, t, rotationAngle);
		}

		return true;
	}

	/**
	 * This is called during layout when the size of this view has changed. If
	 * you were just added to the view hierarchy, you're called with the old
	 * values of 0.
	 * 
	 * @param w
	 *            Current width of this view.
	 * @param h
	 *            Current height of this view.
	 * @param oldw
	 *            Old width of this view.
	 * @param oldh
	 *            Old height of this view.
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Transform the Image Bitmap by the Angle passed
	 * 
	 * @param imageView
	 *            ImageView the ImageView whose bitmap we want to rotate
	 * @param t
	 *            transformation
	 * @param rotationAngle
	 *            the Angle by which to rotate the Bitmap
	 */
	private void transformImageBitmap(ImageView child, Transformation t,
			int rotationAngle) {
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);

		// ��Z���������ƶ�camera���ӽǣ�ʵ��Ч��Ϊ�Ŵ�ͼƬ��
		// �����Y�����ƶ�����ͼƬ�����ƶ���X���϶�ӦͼƬ�����ƶ���
		mCamera.translate(0.0f, 0.0f, 100.0f);

		// As the angle of the view gets less, zoom in
		if (rotation < mMaxRotationAngle) {
			float zoomAmount = (float) (mMaxZoom + (rotation * 1.5));
			mCamera.translate(0.0f, 0.0f, zoomAmount);
		}

		// ��Y������ת����ӦͼƬ�������﷭ת��
		// �����X������ת�����ӦͼƬ�������﷭ת��
		mCamera.rotateY(rotationAngle);
		mCamera.getMatrix(imageMatrix);
		imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
		imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
		mCamera.restore();
	}

}
