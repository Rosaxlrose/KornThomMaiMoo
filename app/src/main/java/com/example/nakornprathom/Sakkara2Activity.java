package com.example.nakornprathom;
import android.content.Intent;
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

public class Sakkara2Activity extends AppCompatActivity {

    private TextView titleText, subtitleText;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton menuButton;
    private TextView langTH, langCH, langEN;
    private Button startButton; // ปุ่มที่ใช้ไปหน้า Sakkara2Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sakkara2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // เชื่อมโยง UI เดิม
        langTH = findViewById(R.id.langTH);
        langCH = findViewById(R.id.langCH);
        langEN = findViewById(R.id.langEN);

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
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    // เปิด HomeActivity
                    startActivity(new Intent(Sakkara2Activity.this, HomeActivity.class));
                } else if (id == R.id.nav_about) {
                    // เปิด AboutActivity
                    startActivity(new Intent(Sakkara2Activity.this, AboutActivity.class));
                } else if (id == R.id.nav_sakkara) {
                    // เปิด prayerActivity
                    startActivity(new Intent(Sakkara2Activity.this, PrayerActivity.class));
                }else if (id == R.id.nav_festival) {
                    // เปิด festivalActivity
                    startActivity(new Intent(Sakkara2Activity.this, FestivalActivity.class));
                }else if (id == R.id.nav_contact) {
                    // เปิด contactActivity
                    startActivity(new Intent(Sakkara2Activity.this, ContactActivity.class));
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

        // เชื่อมโยงปุ่ม startButton กับ Intent ไปยังหน้า Sakkara2Activity
        startButton = findViewById(R.id.startButton); // ตั้งค่าไอดีให้ตรงกับใน layout ของคุณ
        startButton.setOnClickListener(view -> {
            // สร้าง Intent ไปยัง Sakkara3Activity
            Intent intent = new Intent(Sakkara2Activity.this, Sakkara3Activity.class);
            startActivity(intent); // เริ่ม Activity ใหม่
        });
        // เชื่อมโยงปุ่ม startButton กับ Intent ไปยังหน้า Sakkara2Activity
        startButton = findViewById(R.id.startButton2); // ตั้งค่าไอดีให้ตรงกับใน layout ของคุณ
        startButton.setOnClickListener(view -> {
            // สร้าง Intent ไปยัง Sakkara2Activity
            Intent intent = new Intent(Sakkara2Activity.this, SakkaraActivity.class);
            startActivity(intent); // เริ่ม Activity ใหม่
        });
    }

    private void changeLanguage(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // อัปเดตข้อความใหม่
        updateUI();
    }

    private void updateUI() {
        titleText.setText(R.string.app_title);
        subtitleText.setText(R.string.app_subtitle);
        startButton.setText(R.string.start_button);
    }
}
