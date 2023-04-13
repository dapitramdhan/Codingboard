package com.github.dapitkeyboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.dapitkeyboard.theme.IOnFocusListenable;

/**
 * Created by dapitramdhan on 02/04/2023.
 */
public class MainActivity extends AppCompatActivity {

	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupOptionsMenu();
		Bundle extras = getIntent().getExtras();
		SettingsFragment frag = new SettingsFragment();
		frag.setArguments(extras);
		getSupportFragmentManager().beginTransaction().replace(R.id.settings_container, frag).commit();
		//debug only
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.settings_container);
		if (currentFragment instanceof IOnFocusListenable) {
			((IOnFocusListenable) currentFragment).onWindowFocusChanged(hasFocus);
		}
	}

	private void setupOptionsMenu() {
		toolbar = findViewById(R.id.toolbar);
		toolbar.inflateMenu(R.menu.toolbar_menu);
		toolbar.setOnMenuItemClickListener(item -> {
			int id = item.getItemId();

			if (id == R.id.open_source) {
				startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(MPConstants.GITHUB_REPO_URL)));
				return true;
			}
			return false;
		});
	}

}
