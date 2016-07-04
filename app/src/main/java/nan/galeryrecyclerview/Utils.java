package nan.galeryrecyclerview;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public class Utils {

	private Context _context;

	// Конструктор
	public Utils(Context context) {
		this._context = context;
	}

	/*
	 * Чтение filePach из папки
	 */
	public ArrayList<String> getFilePaths() {
		ArrayList<String> filePaths = new ArrayList<String>();

		File directory = new File(
				android.os.Environment.getExternalStorageDirectory()
						+ File.separator + AppConstant.PHOTO_ALBUM);

		// проверка директории
		if (directory.isDirectory()) {
			File[] listFiles = directory.listFiles();

			// Проверка вывода
			if (listFiles.length > 0) {

				for (int i = 0; i < listFiles.length; i++) {

					String filePath = listFiles[i].getAbsolutePath();

					// Проверка на поддержку фаилов
					if (IsSupportedFile(filePath)) {
						// Добовление imagePach в arreylist
						filePaths.add(filePath);
					}
				}
			} else {
				Toast.makeText(
						_context,
						AppConstant.PHOTO_ALBUM
								+ " директория пуста. Загрузите изображения  ",
						Toast.LENGTH_LONG).show();
			}

		} else {

			AlertDialog.Builder alert = new AlertDialog.Builder(_context);
			alert.setTitle("Внимание!");
			alert.setMessage(AppConstant.PHOTO_ALBUM
					+ " создана пожалуйста наполните её изображениями.");
			alert.setPositiveButton("OK", null);
			directory.mkdir();
			alert.show();
		}

		return filePaths;
	}


	private boolean IsSupportedFile(String filePath) {
		String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
				filePath.length());

		if (AppConstant.FILE_EXTN
				.contains(ext.toLowerCase(Locale.getDefault())))
			return true;
		else
			return false;

	}


	public int getScreenWidth() {
		int columnWidth;
		WindowManager wm = (WindowManager) _context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();

		final Point point = new Point();
		try {
			display.getSize(point);
		} catch (NoSuchMethodError ignore) { // Для старых девайсов
			point.x = display.getWidth();
			point.y = display.getHeight();
		}
		columnWidth = point.x;
		return columnWidth;
	}
}
