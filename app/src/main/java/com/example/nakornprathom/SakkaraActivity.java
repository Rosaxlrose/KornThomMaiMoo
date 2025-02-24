package com.example.nakornprathom;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Locale;
import android.view.WindowManager;

public class SakkaraActivity extends AppCompatActivity {

    private TextView titleText, subtitleText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private Button startButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        loadLanguage(); // ✅ โหลดค่าภาษาก่อน UI

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakkara);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // เชื่อมโยง UI
        titleText = findViewById(R.id.title);
        subtitleText = findViewById(R.id.title2);
        langTH = findViewById(R.id.langTH);
        langCH = findViewById(R.id.langCH);
        langEN = findViewById(R.id.langEN);
        startButton = findViewById(R.id.startButton);

        // ตั้งค่าคลิกเปลี่ยนภาษา
        langTH.setOnClickListener(view -> changeLanguage("th"));
        langCH.setOnClickListener(view -> changeLanguage("zh"));
        langEN.setOnClickListener(view -> changeLanguage("en"));

        // ✨ เพิ่มเมนู Sidebar ✨
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuButton = findViewById(R.id.menuButton);

        // คลิกปุ่มเมนูเพื่อเปิด Drawer
        menuButton.setOnClickListener(view -> drawerLayout.openDrawer(navigationView));

        // คลิกเลือกเมนูใน Sidebar
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_home) {
                    intent = new Intent(SakkaraActivity.this, HomeActivity.class);
                } else if (item.getItemId() == R.id.nav_about) {
                    intent = new Intent(SakkaraActivity.this, AboutActivity.class);
                } else if (item.getItemId() == R.id.nav_sakkara) {
                    intent = new Intent(SakkaraActivity.this, SakkaraActivity.class);
                } else if (item.getItemId() == R.id.nav_festival) {
                    intent = new Intent(SakkaraActivity.this, FestivalActivity.class);
                } else if (item.getItemId() == R.id.nav_contact) {
                    intent = new Intent(SakkaraActivity.this, ContactActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    finish(); // ปิดหน้าเดิมเมื่อเปิดหน้าใหม่
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        // คลิกปุ่มไปหน้า Sakkara2Activity
        startButton.setOnClickListener(view -> {
            Intent intent = new Intent(SakkaraActivity.this, Sakkara2Activity.class);
            startActivity(intent);
        });

        // ✅ อัปเดต UI ให้ตรงกับค่าภาษาที่เลือก
        updateUI();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        SharedPreferences sharedPreferences = newBase.getSharedPreferences("AppSettings", MODE_PRIVATE);
        String langCode = sharedPreferences.getString("Selected_Language", "th");
        super.attachBaseContext(updateBaseContextLocale(newBase, langCode));
    }

    private static Context updateBaseContextLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        return context.createConfigurationContext(config);
    }

    private void changeLanguage(String langCode) {
        // บันทึกค่าภาษาใน SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Selected_Language", langCode);
        editor.apply();

        // รีโหลดหน้าใหม่
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    private void loadLanguage() {
        String langCode = sharedPreferences.getString("Selected_Language", "th");
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    private void updateUI() {
        titleText.setText(R.string.worship_points);
        subtitleText.setText(R.string.phra_pathom_chedi);
        startButton.setText(R.string.point_2);
    }
}
